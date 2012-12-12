package de.mxro.server.manager;

import de.mxro.server.ComponentConfiguration;
import de.mxro.server.ServerComponent;
import de.mxro.server.ShutdownCallback;
import de.mxro.server.StartCallback;

public interface ComponentManager {

	public void addComponent(ComponentConfiguration conf);

	/**
	 * Adding a component at a specific index.
	 * 
	 * @param index
	 * @param conf
	 */
	public void addComponent(int index, ComponentConfiguration conf);

	public void startComponent(String componentId, StartCallback callback);

	public void stopComponent(String componentId, ShutdownCallback callback);

	/**
	 * 
	 * @param componentId
	 * @return The index of the removed component in the component list.
	 */
	public int removeComponent(String componentId);

	public ServerComponent getComponent(String componentId);

}
