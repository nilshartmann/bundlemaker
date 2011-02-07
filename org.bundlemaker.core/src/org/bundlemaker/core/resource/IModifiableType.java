package org.bundlemaker.core.resource;


/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
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
}