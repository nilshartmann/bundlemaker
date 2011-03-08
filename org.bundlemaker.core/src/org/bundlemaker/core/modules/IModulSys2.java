package org.bundlemaker.core.modules;

import java.util.Set;

import org.bundlemaker.core.resource.IType;

public interface IModulSys2 {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @return
	 */
	Set<IType> getTypes(String fullyQualifiedName);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @return
	 * @throws AmbiguousDependencyException
	 */
	IType getType(String fullyQualifiedName)
			throws AmbiguousDependencyException;

	/**
	 * <p>
	 * Returns a set of {@link IModule IModules} that contain a type with the
	 * specified name.
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 *            the fully qualified name.
	 * @return a set of {@link IModule IModules} that contain a type with the
	 *         specified name.
	 */
	Set<IModule> getTypeContainingModules(String fullyQualifiedName);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @return
	 * @throws AmbiguousDependencyException
	 */
	IModule getTypeContainingModule(String fullyQualifiedName)
			throws AmbiguousDependencyException;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedPackageName
	 * @return
	 */
	Set<IModule> getPackageContainingModules(String fullyQualifiedPackageName);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedPackageName
	 * @return
	 * @throws AmbiguousDependencyException
	 */
	IModule getPackageContainingModule(String fullyQualifiedPackageName)
			throws AmbiguousDependencyException;
}
