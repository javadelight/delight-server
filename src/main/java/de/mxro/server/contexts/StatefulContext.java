package de.mxro.server.contexts;

import de.mxro.server.ComponentContext;

/**
 * Allows access to two kinds of stateful data for a server: logs and simple
 * key/value properties.
 * 
 * @author Max
 * 
 */
public interface StatefulContext extends ComponentContext {

	public void log(String path, String title, String message,
			LogCallback callback);

	public void setProperty(String path, Object value,
			SetPropertyCallback callback);

	public void getProperty(String path, GetPropertyCallback callback);

}