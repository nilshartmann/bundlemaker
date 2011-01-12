package org.bundlemaker.core.modules;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AmbiguousModuleDependencyException extends Exception {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * Creates a new instance of type {@link AmbiguousModuleDependencyException}
	 * .
	 * </p>
	 * 
	 * @param message
	 */
	public AmbiguousModuleDependencyException(String message) {
		super(message);
	}

}
