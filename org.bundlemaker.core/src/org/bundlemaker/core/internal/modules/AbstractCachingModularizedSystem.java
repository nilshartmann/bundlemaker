package org.bundlemaker.core.internal.modules;

import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.modules.IModule;
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

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Override
	protected void initializeModules() throws Exception {
		
		// step 2: iterate over the file based content
		for (IFileBasedContent fileBasedContent : getProjectDescription()
				.getFileBasedContent()) {

			if (fileBasedContent.isResourceContent()) {

				//
				for (IResource resource : fileBasedContent.getResourceContent()
						.getBinaryResources()) {

					for (IType containedType : resource.getContainedTypes()) {

						if (getTypeNameToTypeCache().getOrCreate(
								containedType.getFullyQualifiedName()).add(
								containedType)) {

							for (IReference reference : containedType
									.getReferences()) {

								getTypeNameToReferringCache().getOrCreate(
										reference.getFullyQualifiedName()).add(
										containedType);
							}

						}
					}

				}

				//
				for (IResource resource : fileBasedContent.getResourceContent()
						.getSourceResources()) {

					for (IType containedType : resource.getContainedTypes()) {

						if (getTypeNameToTypeCache().getOrCreate(
								containedType.getFullyQualifiedName()).add(
								containedType)) {

							for (IReference reference : containedType
									.getReferences()) {

								getTypeNameToReferringCache().getOrCreate(
										reference.getFullyQualifiedName()).add(
										containedType);
							}

						}
					}
				}

			}
		}

		// step 2: initialize the type modules
		for (IModule module : getNonResourceModules()) {

			for (IType type : module.getContainedTypes()) {
				getTypeNameToTypeCache().getOrCreate(
						type.getFullyQualifiedName()).add(type);
			}
		}
	}

	@Override
	protected void postApplyTransformations() {

		
	}
}
