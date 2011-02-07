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
	 * Returns the type of the this type.
	 * </p>
	 * 
	 * @return
	 */
	TypeEnum getType();

	/**
	 * <p>
	 * Returns the name of the referenced type, e.g. <code>'YXY'</code>.
	 * </p>
	 * 
	 * @return the name.
	 */
	String getName();

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
	 * Returns the fully qualified name of the package that contains this type,
	 * e.g. <code>'de.example'</code>.
	 * </p>
	 * 
	 * @return the fully qualified package name.
	 */
	String getPackageName();

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
	 * Returns the associated source resource.
	 * </p>
	 * 
	 * @return the associated source resource.
	 */
	IResource getSourceResource();

	/**
	 * <p>
	 * Returns <code>true</code>, if a associated source resource is set.
	 * </p>
	 * 
	 * @return <code>true</code>, if a associated source resource is set.
	 */
	boolean hasSourceResource();

	/**
	 * <p>
	 * Returns the associated binary resource.
	 * </p>
	 * 
	 * @return the associated binary resource.
	 */
	IResource getBinaryResource();

	/**
	 * <p>
	 * Returns <code>true</code>, if a associated binary resource is set.
	 * </p>
	 * 
	 * @return <code>true</code>, if a associated binary resource is set.
	 */
	boolean hasBinaryResource();
}
