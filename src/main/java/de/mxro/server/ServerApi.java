package de.mxro.server;

import de.mxro.server.internal.DefaultComponentManager;
import de.mxro.server.manager.ComponentFactory;
import de.mxro.server.manager.ComponentManager;

public class ServerApi {

	public static ComponentManager createManager(
			final ComponentContext context, final ComponentFactory factory) {
		return new DefaultComponentManager(context, factory);

	}

}
