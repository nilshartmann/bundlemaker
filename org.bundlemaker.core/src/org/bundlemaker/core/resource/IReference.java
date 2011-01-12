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
	boolean isBytecodeDependency();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	boolean isSourcecodeDependency();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	boolean isIndirectlyReferenced();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	boolean isDirectlyReferenced();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	boolean isUses();
}
