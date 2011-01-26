package org.bundlemaker.core.resource;

import java.util.Set;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IType {

	/**
	 * <p>
	 * Returns the fully qualified name of the referenced type, e.g.
	 * <code>'de.example.YXY'</code>.
	 * </p>
	 * 
	 * @return the fully qualified name.
	 */
	String getFullyQualifiedName();

	/**
	 * <p>
	 * </p>
	 * 
	 * @param type
	 * @return
	 */
	Set<? extends IReference> getReferences();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	IResource getSourceResource();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	IResource getBinaryResource();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	boolean hasSourceResource();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	boolean hasBinaryResource();
}
