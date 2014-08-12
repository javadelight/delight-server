package de.mxro.server.components;

import de.mxro.async.callbacks.SimpleCallback;
import de.mxro.server.ComponentConfiguration;
import de.mxro.server.ComponentContext;
import de.mxro.server.ServerComponent;

/**
 * A basic component implementing all methods. Any methods which shall be used
 * by this component must be overriden.
 * 
 * @author mroh004
 * 
 */
public class BaseServerComponent implements ServerComponent {

	private static final String METHOD_NOT_SUPPORTED = "Method not supported";

	@Override
	public void stop(final de.mxro.service.callbacks.ShutdownCallback callback) {
		throw new RuntimeException(METHOD_NOT_SUPPORTED);
	}

	@Override
	public void start(final SimpleCallback callback) {
		throw new RuntimeException(METHOD_NOT_SUPPORTED);
	}

	@Override
	public void injectConfiguration(final ComponentConfiguration conf) {
		throw new RuntimeException(METHOD_NOT_SUPPORTED);
	}

	@Override
	public void injectContext(final ComponentContext context) {
		throw new RuntimeException(METHOD_NOT_SUPPORTED);
	}

	@Override
	public ComponentConfiguration getConfiguration() {
		throw new RuntimeException(METHOD_NOT_SUPPORTED);
	}

}
