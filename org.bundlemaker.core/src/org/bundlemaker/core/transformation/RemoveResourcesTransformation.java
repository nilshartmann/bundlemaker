package org.bundlemaker.core.transformation;

import java.util.ArrayList;
import java.util.List;

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.transformation.resourceset.ResourceSet;
import org.bundlemaker.core.util.TransformationUtils;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class RemoveResourcesTransformation implements ITransformation {

	/** - */
	private List<ResourceSet> _resourcesToRemove;

	public RemoveResourcesTransformation() {
		_resourcesToRemove = new ArrayList<ResourceSet>();
	}

	public List<ResourceSet> getResourcesToRemove() {
		return _resourcesToRemove;
	}

	@Override
	public void apply(IModifiableModularizedSystem modularizedSystem) {

		//
		for (ResourceSet resourceSet : _resourcesToRemove) {

			//
			IModifiableResourceModule resourceModule = modularizedSystem
					.getModifiableResourceModule(resourceSet
							.getModuleIdentifier());

			List<IResource> resourceStandinsToMove = resourceSet
					.getMatchingResources(resourceModule, ContentType.BINARY);

			TransformationUtils.removeAll(resourceModule,
					resourceStandinsToMove, ContentType.BINARY);

			resourceStandinsToMove = resourceSet.getMatchingResources(
					resourceModule, ContentType.SOURCE);

			TransformationUtils.removeAll(resourceModule,
					resourceStandinsToMove, ContentType.SOURCE);

		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param name
	 * @param version
	 * @param includes
	 * @param excludes
	 */
	public void addResourceSet(RemoveResourcesTransformation transformation,
			String name, String version, String[] includes, String[] excludes) {

		//
		addResourceSet(new ModuleIdentifier(name, version), includes, excludes);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fromModuleIdentifier
	 * @param includes
	 * @param excludes
	 */
	public void addResourceSet(IModuleIdentifier fromModuleIdentifier,
			String[] includes, String[] excludes) {

		//
		ResourceSet resourceSet = new ResourceSet();

		//
		resourceSet.setModuleIdentifier(fromModuleIdentifier);

		//
		if (includes != null) {
			for (String include : includes) {
				resourceSet.getIncludes().add(include);
			}
		}

		//
		if (excludes != null) {
			for (String exclude : excludes) {
				resourceSet.getExcludes().add(exclude);
			}
		}

		//
		_resourcesToRemove.add(resourceSet);
	}
}
