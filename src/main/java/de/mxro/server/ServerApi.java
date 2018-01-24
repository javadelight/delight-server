package de.mxro.server;

import java.util.ArrayList;
import java.util.List;

import de.mxro.server.internal.DefaultComponentManager;
import de.mxro.server.management.ComponentManager;
import de.mxro.service.callbacks.ShutdownCallback;
import delight.async.AsyncCommon;
import delight.async.Operation;
import delight.async.callbacks.SimpleCallback;
import delight.async.callbacks.ValueCallback;
import delight.concurrency.Concurrency;
import delight.concurrency.Concurrent;
import delight.concurrency.wrappers.SimpleAtomicBoolean;
import delight.concurrency.wrappers.SimpleTimer;
import delight.factories.FactoryCollection;
import delight.functional.Success;
import delight.simplelog.Log;

public class ServerApi {


    /**
     * <p>
     * A default manager for components.
     * </p>
     * <p>
     * Beware this manager is not thread safe.
     * </p>
     * 
     * @param factory
     * @return
     */
    public static ComponentManager createManager(final FactoryCollection factories) {
        return new DefaultComponentManager(factories);

    }

    public static void performShutdown(final List<ServerComponent> toShutdown, final int timeout, final Concurrency concurrency, final ShutdownCallback callback) {

    	List<Operation<Success>> list = new ArrayList<Operation<Success>>(toShutdown.size());
    	
    	for (final ServerComponent c: toShutdown) {
    		list.add(new Operation<Success>() {
				
				@Override
				public void apply(final ValueCallback<Success> callback) {
					
					final SimpleAtomicBoolean failed = concurrency.newAtomicBoolean(false);
					final SimpleAtomicBoolean succeeded = concurrency.newAtomicBoolean(false);
					
					final SimpleTimer timer = concurrency.newTimer().scheduleOnce(timeout, new Runnable() {
						
						@Override
						public void run() {
							if (succeeded.get()) {
								return;
							}
							failed.set(true);
							callback.onFailure(new Exception("Component not shut down in timeout: "+c));
						}
					});
					
					c.stop(new SimpleCallback() {
						
						@Override
						public void onFailure(Throwable t) {
							if (failed.get()) {
								Log.warn("Component shutdown failed. Excpetion reported: "+t.getMessage(), t);
								return;
							}
							succeeded.set(true);
							timer.stop();
						}
						
						@Override
						public void onSuccess() {
							if (failed.get()) {
								return;
							}
							succeeded.set(true);
							timer.stop();
							callback.onSuccess(Success.INSTANCE);
							
						}
					});
				}
			});
    	}
    	
    	Concurrent.sequential(list, concurrency, new ValueCallback<List<Success>>() {

			@Override
			public void onFailure(Throwable t) {
				callback.onFailure(t);
			}

			@Override
			public void onSuccess(List<Success> value) {
				callback.onSuccess();
			}
		});
    	
        

    }

}
