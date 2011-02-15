package org.bundlemaker.core.internal.modules;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractModularizedSystem implements
		IModifiableModularizedSystem {

	/** the name of working copy */
	private String _name;

	/** the project description */
	private IBundleMakerProjectDescription _projectDescription;

	/** the list of defined transformations */
	private List<ITransformation> _transformations;

	/** the defined resource modules */
	private Map<IModuleIdentifier, IModifiableResourceModule> _resourceModules;

	/** the defined type modules */
	private Map<IModuleIdentifier, TypeModule> _nonResourceModules;

	/** the execution environment type module */
	private TypeModule _executionEnvironment;

	/**
	 * <p>
	 * Creates a new instance of type {@link AbstractModularizedSystem}.
	 * </p>
	 * 
	 * @param name
	 * @param projectDescription
	 */
	public AbstractModularizedSystem(String name,
			IBundleMakerProjectDescription projectDescription) {

		Assert.isNotNull(name);
		Assert.isNotNull(projectDescription);

		_name = name;

		_projectDescription = projectDescription;

		_transformations = new LinkedList<ITransformation>();
		_resourceModules = new HashMap<IModuleIdentifier, IModifiableResourceModule>();
		_nonResourceModules = new HashMap<IModuleIdentifier, TypeModule>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getName() {
		return _name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final IBundleMakerProjectDescription getProjectDescription() {
		return _projectDescription;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final List<ITransformation> getTransformations() {
		return _transformations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final IModule getExecutionEnvironment() {
		return _executionEnvironment;
	}

	/**
	 * {@inheritDoc}
	 */
	public final Set<IModule> getAllModules() {

		// create the result list
		Set<IModule> result = new HashSet<IModule>(_nonResourceModules.size()
				+ _resourceModules.size());

		// all all modules
		result.addAll(_nonResourceModules.values());
		result.addAll(_resourceModules.values());

		// return an unmodifiable copy
		return Collections.unmodifiableSet(result);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final IModule getNonResourceModule(IModuleIdentifier identifier) {

		//
		Assert.isNotNull(identifier);

		//
		return _nonResourceModules.get(identifier);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Collection<IModule> getNonResourceModules() {

		//
		return Collections
				.unmodifiableCollection((Collection<? extends IModule>) _nonResourceModules
						.values());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final IResourceModule getResourceModule(IModuleIdentifier identifier) {

		//
		return getModifiableResourceModule(identifier);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Collection<IResourceModule> getResourceModules() {

		//
		return Collections
				.unmodifiableCollection((Collection<? extends IResourceModule>) _resourceModules
						.values());
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public final Map<IModuleIdentifier, IModifiableResourceModule> getModifiableResourceModulesMap() {
		return _resourceModules;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param identifier
	 * @return
	 */
	public final IModifiableResourceModule getModifiableResourceModule(
			IModuleIdentifier identifier) {

		Assert.isNotNull(identifier);

		//
		return _resourceModules.get(identifier);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected final Map<IModuleIdentifier, TypeModule> getModifiableNonResourceModulesMap() {
		return _nonResourceModules;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param executionEnvironment
	 */
	protected void setExecutionEnvironment(TypeModule executionEnvironment) {

		Assert.isNotNull(executionEnvironment);

		_executionEnvironment = executionEnvironment;
	}
}
