package de.mxro.server;

/**
 * A factory for a {@link ServerComponent}
 * 
 * @author Max
 * 
 */
public interface ServerComponentFactory<Comp extends ServerComponent, Conf extends ComponentConfiguration> {

	public Comp init(Conf conf);

}
