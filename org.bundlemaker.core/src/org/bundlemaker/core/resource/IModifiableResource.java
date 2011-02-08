package org.bundlemaker.core.resource;

import org.bundlemaker.core.spi.resource.Type;

/**
 * <p>
 * 
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
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
}