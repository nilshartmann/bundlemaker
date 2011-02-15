package org.bundlemaker.core.internal.modules;

import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.GenericCache;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractCachingModularizedSystem extends
		AbstractTransformationAwareModularizedSystem {

	/** - */
	private Set<IResource> _binaryResources;

	/** - */
	private Set<IResource> _sourceResources;

	/** type name -> type */
	private GenericCache<String, Set<IType>> _typeNameToTypeCache;

	/** type name -> referring type */
	private GenericCache<String, Set<IType>> _typeNameToReferringCache;

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
		_sourceResources = new HashSet<IResource>();
		_binaryResources = new HashSet<IResource>();
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
	protected void preApplyTransformations() {
		super.preApplyTransformations();

		// cache all IResources
		for (IFileBasedContent fileBasedContent : getProjectDescription()
				.getFileBasedContent()) {

			if (fileBasedContent.isResourceContent()) {

				_binaryResources.addAll(fileBasedContent.getResourceContent()
						.getBinaryResources());

				_sourceResources.addAll(fileBasedContent.getResourceContent()
						.getSourceResources());
			}
		}
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
			}
		}

		// step 2: cache the resource modules
		for (IResourceModule module : getResourceModules()) {

			//
			for (IType type : module.getContainedTypes()) {

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
