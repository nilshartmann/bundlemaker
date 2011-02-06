package org.bundlemaker.core.resource;

import java.util.Set;

import org.bundlemaker.core.spi.resource.Reference;

public interface IModifiableType extends IType {

	/**
	 * @param fullyQualifiedName
	 * @param referenceType
	 * @param isExtends
	 * @param isImplements
	 * @param isClassAnnotation
	 * @param isCompiletime
	 * @param isRuntime
	 */
	void recordReference(String fullyQualifiedName,
			ReferenceType referenceType, boolean isExtends,
			boolean isImplements, boolean isClassAnnotation,
			boolean isCompiletime, boolean isRuntime);

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Set<Reference> getModifiableReferences();
}