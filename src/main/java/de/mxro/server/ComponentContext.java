package de.mxro.server;

import delight.factories.FactoryCollection;

import de.mxro.async.properties.PropertyNode;
import de.mxro.service.ServiceRegistry;

/**
 * Contextual variables for a Server Component.
 * 
 * @author Max
 * 
 */
public interface ComponentContext {

    public FactoryCollection factories();

    public ServiceRegistry services();

    public PropertyNode metrics();

    public PropertyNode state();

    public PropertyNode logs();

}
