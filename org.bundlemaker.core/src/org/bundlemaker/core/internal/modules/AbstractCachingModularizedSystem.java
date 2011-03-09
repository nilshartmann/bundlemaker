package org.bundlemaker.core.internal.modules;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.modules.AmbiguousDependencyException;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.GenericCache;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractCachingModularizedSystem extends
		AbstractTransformationAwareModularizedSystem {

	/** type name -> type */
	private GenericCache<String, Set<IType>> _typeNameToTypeCache;

	/** type name -> referring type */
	private GenericCache<String, Set<IType>> _typeNameToReferringCache;

	/** - */
	private Map<IResource, IResourceModule> _resourceToResourceModuleMap;

	/** - */
	private Map<IType, IModule> _typeToModuleMap;

	/**
	 * <p>
	 * Creates a new instance of type {@link AbstractCachingModularizedSystem}.
	 * </p>
	 * 
	 * @param name
	 * @param projectDescription
	 */
	public AbstractCachingModularizedSystem(String name,
			IBundleMakerProjectDescription projectDescription) {

		// call the super constructor
		super(name, projectDescription);

		// create type name to type cache
		_typeNameToTypeCache = new GenericCache<String, Set<IType>>() {
			@Override
			protected Set<IType> create(String key) {
				return new HashSet<IType>();
			}
		};

		//
		_typeNameToReferringCache = new GenericCache<String, Set<IType>>() {
			@Override
			protected Set<IType> create(String key) {
				return new HashSet<IType>();
			}
		};

		//
		_resourceToResourceModuleMap = new HashMap<IResource, IResourceModule>();
		_typeToModuleMap = new HashMap<IType, IModule>();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public GenericCache<String, Set<IType>> getTypeNameToTypeCache() {
		return _typeNameToTypeCache;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public GenericCache<String, Set<IType>> getTypeNameToReferringCache() {
		return _typeNameToReferringCache;
	}

	@Override
	public IModule getTypeContainingModule(String fullyQualifiedName)
			throws AmbiguousDependencyException {

		Set<IModule> result = getTypeContainingModules(fullyQualifiedName);

		if (result.isEmpty()) {
			return null;
		}

		if (result.size() > 1) {
			throw new AmbiguousDependencyException(
					"AmbiguousModuleDependencyException: " + fullyQualifiedName);
		}

		return result.toArray(new IModule[0])[0];
	}

	@Override
	public IType getType(String fullyQualifiedName)
			throws AmbiguousDependencyException {

		Assert.isNotNull(fullyQualifiedName);

		// get type modules
		Set<IType> types = getTypeNameToTypeCache().get(fullyQualifiedName);

		// return null if type is unknown
		if (types == null) {
			return null;
		}

		// if multiple type modules exist, throw an exception
		if (types.size() > 1) {

			// TODO
			new AmbiguousDependencyException(fullyQualifiedName);
		}

		// return the type
		return types.toArray(new IType[0])[0];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<IType> getTypes(String fullyQualifiedName) {

		//
		Assert.isNotNull(fullyQualifiedName);
		Assert.isTrue(fullyQualifiedName.trim().length() > 0);

		// get type modules
		Set<IType> types = getTypeNameToTypeCache().get(fullyQualifiedName);
		types = types != null ? types : new HashSet<IType>();

		// return the result
		return Collections.unmodifiableSet(types);
	}

	@Override
	public Set<IModule> getPackageContainingModules(
			String fullyQualifiedPackageName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IModule getPackageContainingModule(String fullyQualifiedPackageName)
			throws AmbiguousDependencyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IModule> getTypeContainingModules(String fullyQualifiedName) {

		//
		if (getTypeNameToTypeCache().containsKey(fullyQualifiedName)) {

			Set<IType> types = getTypeNameToTypeCache().get(fullyQualifiedName);

			Set<IModule> result = new HashSet<IModule>(types.size());

			for (IType type : types) {
				// TODO: direct call
				result.add(type.getModule(this));
			}

			//
			return Collections.unmodifiableSet(result);

		} else {
			return Collections.emptySet();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public IResourceModule getAssociatedResourceModule(IResource resource) {

		Assert.isNotNull(resource);

		if (resource instanceof Resource) {
			resource = ((Resource) resource).getResourceStandin();
		}
		return _resourceToResourceModuleMap.get(resource);
	}

	public IModule getAssociatedModule(IType type) {

		Assert.isNotNull(type);
		return _typeToModuleMap.get(type);
	}

	/**
	 * <p>
	 * </p>
	 */
	@Override
	protected void initializeResourceModules() {
		super.initializeResourceModules();

		getTypeNameToTypeCache().clear();
		getTypeNameToReferringCache().clear();

		// step 1: cache the type modules
		for (IModule module : getNonResourceModules()) {
			for (IType type : module.getContainedTypes()) {
				getTypeNameToTypeCache().getOrCreate(
						type.getFullyQualifiedName()).add(type);
				_typeToModuleMap.put(type, module);
			}
		}

		// step 2: cache the resource modules
		for (IResourceModule module : getResourceModules()) {

			//
			for (IResource resource : module.getResources(ContentType.SOURCE)) {
				_resourceToResourceModuleMap.put(resource, module);
			}
			for (IResource resource : module.getResources(ContentType.BINARY)) {
				_resourceToResourceModuleMap.put(resource, module);
			}

			//
			for (IType type : module.getContainedTypes()) {

				_typeToModuleMap.put(type, module);

				if (getTypeNameToTypeCache().getOrCreate(
						type.getFullyQualifiedName()).add(type)) {

					for (IReference reference : type.getReferences()) {
						getTypeNameToReferringCache().getOrCreate(
								reference.getFullyQualifiedName()).add(type);
					}
				}
			}
		}
	}
}
