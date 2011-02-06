package org.bundlemaker.core.resource;

import java.util.Set;

import org.bundlemaker.core.spi.resource.Reference;
import org.bundlemaker.core.spi.resource.Type;

public interface IModifiableResource extends IResource {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @param referenceType
	 * @param isExtends
	 * @param isImplements
	 * @param isClassAnnotation
	 * @param isCompiletime
	 * @param isRuntime
	 */
	public void recordReference(String fullyQualifiedName,
			ReferenceType referenceType, boolean isExtends,
			boolean isImplements, boolean isClassAnnotation,
			boolean isCompiletime, boolean isRuntime);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @return
	 */
	public Type getOrCreateType(String fullyQualifiedName, TypeEnum typeEnum);

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public Set<Type> getModifiableContainedTypes();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public Set<Reference> getModifiableReferences();

}