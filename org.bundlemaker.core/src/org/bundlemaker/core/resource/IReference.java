package org.bundlemaker.core.resource;

/**
 * <p>
 * Represents a reference from an {@link IResource} to a type or a package.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IReference {

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
	 * Returns the type of this {@link IReference}. A reference can either be a
	 * <i>package</i> reference or <i>type</i> reference.
	 * </p>
	 * 
	 * @return the {@link ReferenceType} of this {@link IReference}.
	 */
	ReferenceType getReferenceType();

	/**
	 * <p>
	 * Returns <code>true</code> if this reference is a compile time reference.
	 * </p>
	 * 
	 * @return <code>true</code> if this reference is a compile time reference.
	 */
	boolean isCompileTimeReference();

	/**
	 * <p>
	 * Returns <code>true</code> if this reference is a runtime reference.
	 * </p>
	 * 
	 * @return <code>true</code> if this reference is a runtime reference.
	 */
	boolean isRuntimeReference();

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
	 * </p>
	 * 
	 * @return
	 */
	boolean isClassAnnotation();

	/**
	 * <p>
	 * Returns the originating {@link IResource} or <code>null</code>, if the
	 * reference does not belong to a resource. In this case, the reference
	 * belongs to a type that is return by method {@link IReference#getType()}.
	 * </p>
	 * <p>
	 * <b>Note:</b> This back reference is set after the model is loaded from
	 * the database. It is <b>not</b> part of the stored model.
	 * </p>
	 * 
	 * @return the originating {@link IResource}.
	 */
	IResource getResource();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	boolean hasAssociatedResource();

	/**
	 * <p>
	 * Returns the originating {@link IType}.
	 * </p>
	 * <p>
	 * <b>Note:</b> This back reference is set after the model is loaded from
	 * the database. It is <b>not</b> part of the stored model.
	 * </p>
	 * 
	 * @return the originating {@link IType}.
	 */
	IType getType();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	boolean hasAssociatedType();
}
