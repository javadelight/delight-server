package de.mxro.server.manager;

import java.util.List;

import de.mxro.server.ComponentConfiguration;
import de.mxro.server.ComponentContext;
import de.mxro.server.ServerComponent;
import de.mxro.service.callbacks.ShutdownCallback;
import delight.async.callbacks.SimpleCallback;

public interface ComponentManager {

	public ServerComponent addComponent(ComponentContext context,
			ComponentConfiguration conf);

	public ServerComponent addRunningComponent(ComponentContext context,
			ServerComponent component);

	/**
	 * Adding a component at a specific index.
	 * 
	 * @param index
	 * @param conf
	 */
	public ServerComponent addComponent(int index, ComponentContext context,
			ComponentConfiguration conf);

	public void startComponent(String componentId, SimpleCallback callback);

	public void stopComponent(String componentId, ShutdownCallback callback);

	public boolean isRunning(ServerComponent component);

	/**
	 * 
	 * @param componentId
	 * @return The index of the removed component in the component list.
	 */
	public int removeComponent(String componentId);

	public ServerComponent getComponent(String componentId);

	public List<ServerComponent> getComponents();

}
