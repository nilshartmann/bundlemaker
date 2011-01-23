package org.bundlemaker.core.resource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IReference {

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	String getFullyQualifiedName();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	ReferenceType getReferenceType();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	boolean isExtends();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	boolean isImplements();
}
