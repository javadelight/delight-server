package de.mxro.server;

import java.util.ArrayList;
import java.util.List;

import de.mxro.factories.Dependencies;
import de.mxro.factories.Factory;
import de.mxro.factories.FactoryCollection;
import de.mxro.server.contexts.LogCallback;
import de.mxro.server.contexts.StatefulContext;
import de.mxro.server.contexts.StatefulContextConfiguration;
import de.mxro.server.internal.DefaultComponentManager;
import de.mxro.server.internal.LocalStatefulContextFactory;
import de.mxro.server.manager.ComponentManager;
import de.mxro.service.callbacks.ShutdownCallback;

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

    public static LogCallback logCallback(final Object source) {
        return new LogCallback() {

            @Override
            public void onLogged() {

            }

            @Override
            public void onFailure(final Throwable t) {
                throw new RuntimeException("Log call failed from: " + source, t);
            }
        };
    }

    public static Factory<? extends StatefulContext, ? extends StatefulContextConfiguration, Dependencies> createLocalStatefulContextFactory() {
        return new LocalStatefulContextFactory();
    }

    public static void performShutdown(final List<ServerComponent> toShutdown, final ShutdownCallback callback) {

        if (toShutdown.size() == 0) {
            callback.onSuccess();
            return;
        }

        final ServerComponent server = toShutdown.get(0);
        toShutdown.remove(0);

        final List<ServerComponent> remainingServers = new ArrayList<ServerComponent>(toShutdown);
        server.stop(new ShutdownCallback() {

            @Override
            public void onSuccess() {
                performShutdown(remainingServers, callback);
            }

            @Override
            public void onFailure(final Throwable t) {
                t.printStackTrace();
                performShutdown(remainingServers, callback);
                callback.onFailure(t);
            }
        });

    }

}
