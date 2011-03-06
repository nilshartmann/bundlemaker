package org.bundlemaker.core.modules;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.transformation.ITransformation;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModularizedSystem extends ICrossReferencer {

	/**
	 * <p>
	 * Returns the name of the modularized system.
	 * </p>
	 * 
	 * @return
	 */
	String getName();

	/**
	 * <p>
	 * Returns the associated {@link IBundleMakerProject}.
	 * </p>
	 * 
	 * @return the associated {@link IBundleMakerProject}.
	 */
	IBundleMakerProject getBundleMakerProject();

	/**
	 * <p>
	 * Returns the project description.
	 * </p>
	 * 
	 * @return the project description
	 */
	IBundleMakerProjectDescription getProjectDescription();

	/**
	 * <p>
	 * Returns the (modifiable) transformation list.
	 * </p>
	 * 
	 * @return the (modifiable) transformation list.
	 */
	List<ITransformation> getTransformations();

	/**
	 * <p>
	 * Applies the transformations.
	 * </p>
	 */
	void applyTransformations();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	IModule getExecutionEnvironment();

	/**
	 * <p>
	 * Returns all modules ({@link IModule ITypeModules} and
	 * {@link IResourceModule IResourceModules}).
	 * </p>
	 * 
	 * @return
	 */
	Set<IModule> getAllModules();

	/**
	 * <p>
	 * Returns all contained {@link IModule ITypeModules}.
	 * </p>
	 * 
	 * @return
	 */
	Collection<IModule> getNonResourceModules();

	/**
	 * <p>
	 * Returns the {@link IModule} with the given identifier.
	 * </p>
	 * 
	 * @param identifier
	 * @return
	 */
	IModule getNonResourceModule(IModuleIdentifier identifier);

	/**
	 * <p>
	 * Returns all contained {@link IResourceModule IResourceModules}.
	 * </p>
	 * 
	 * @return
	 */
	Collection<IResourceModule> getResourceModules();

	/**
	 * <p>
	 * Returns the {@link IResourceModule} with the given identifier.
	 * </p>
	 * 
	 * @param identifier
	 * @return
	 */
	IResourceModule getResourceModule(IModuleIdentifier identifier);

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