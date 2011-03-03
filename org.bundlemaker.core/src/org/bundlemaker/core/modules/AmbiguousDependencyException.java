package org.bundlemaker.core.modules;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
// TODO: NAME
public class AmbiguousDependencyException extends Exception {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * Creates a new instance of type {@link AmbiguousDependencyException}
	 * .
	 * </p>
	 * 
	 * @param message
	 */
	public AmbiguousDependencyException(String message) {
		super(message);
	}

}
