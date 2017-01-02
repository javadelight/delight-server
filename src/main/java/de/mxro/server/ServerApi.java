package de.mxro.server;

import delight.factories.FactoryCollection;

import java.util.ArrayList;
import java.util.List;

import de.mxro.server.internal.DefaultComponentManager;
import de.mxro.server.management.ComponentManager;
import de.mxro.service.callbacks.ShutdownCallback;

public class ServerApi {

    private final static boolean ENABLE_LOG = false;

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

    public static void performShutdown(final List<ServerComponent> toShutdown, final ShutdownCallback callback) {

        if (toShutdown.size() == 0) {
            callback.onSuccess();
            if (ENABLE_LOG) {
                System.out.println(ServerApi.class + ": Shutdown complete.");
            }
            return;
        }

        final ServerComponent server = toShutdown.get(0);

        if (ENABLE_LOG) {
            System.out.println(ServerApi.class + ": Shutting down " + server);
        }
        toShutdown.remove(0);

        final List<ServerComponent> remainingServers = new ArrayList<ServerComponent>(toShutdown);
        server.stop(new ShutdownCallback() {

            @Override
            public void onSuccess() {
                if (ENABLE_LOG) {
                    System.out.println(ServerApi.class + ": Shut  down " + server);
                }
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
