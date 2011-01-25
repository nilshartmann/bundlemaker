package org.bundlemaker.core.resource;

/**
 * <p>
 * Represents a reference from an {@link IResource} to a fully qualified type.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IReference {

	/**
	 * <p>
	 * Returns the fully qualified name of the referenced type, e.g.
	 * <code>de.example.YXY</code>.
	 * </p>
	 * 
	 * @return the fully qualified name.
	 */
	String getFullyQualifiedName();

	/**
	 * <p>
	 * Returns the type of this {@link IReference}.
	 * </p>
	 * 
	 * @return the type of this {@link IReference}.
	 */
	ReferenceType getReferenceType();

	/**
	 * <p>
	 * Returns <code>true</code>, if the this {@link IReference} represents an
	 * extends relationship. This is the case if the originating
	 * {@link IResource} contains a java type that extends the type described by
	 * this dependency (e.g. <code>public class XY extends Z</code>).
	 * </p>
	 * 
	 * @return <code>true</code>, if the this {@link IReference} represents an
	 *         extends relationship, <code>false</code> otherwise.
	 */
	boolean isExtends();

	/**
	 * <p>
	 * Returns <code>true</code>, if the this {@link IReference} represents an
	 * implements relationship. This is the case if the originating
	 * {@link IResource} contains a java type that implements the type described
	 * by this dependency (e.g. <code>public class XY implements Z</code>).
	 * </p>
	 * 
	 * @return <code>true</code>, if the this {@link IReference} represents an
	 *         implements relationship, <code>false</code> otherwise.
	 */
	boolean isImplements();

	/**
	 * <p>
	 * Returns the originating {@link IResource}.
	 * </p>
	 * 
	 * @return the originating {@link IResource}.
	 */
	IResource getResource();
}
