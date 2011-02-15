package org.bundlemaker.core.batch.tk.transformations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.tools.ant.types.selectors.SelectorUtils;
import org.bundlemaker.core.batch.tk.ProductAttributes;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.transformation.resourceset.IResourceSetProcessor;
import org.bundlemaker.core.transformation.resourceset.ResourceSet;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.Assert;

/**
 * 
 */
public class TKeasyResourceSetProcessor implements IResourceSetProcessor {

	/** - */
	private String _sourceDirectory;

	/** - */
	private String _qsSourceDirectory;

	/** - */
	private String _genSourceDirectory;

	/**
	 * @param sourceDirectory
	 * @param qsSourceDirectory
	 * @param genSourceDirectory
	 */
	public TKeasyResourceSetProcessor(String sourceDirectory,
			String qsSourceDirectory, String genSourceDirectory) {

		Assert.isNotNull(sourceDirectory);
		Assert.isNotNull(qsSourceDirectory);
		Assert.isNotNull(genSourceDirectory);

		_sourceDirectory = FileUtils.normalize(sourceDirectory);
		_qsSourceDirectory = FileUtils.normalize(qsSourceDirectory);
		_genSourceDirectory = FileUtils.normalize(genSourceDirectory);
	}

	/**
	 * @see org.bundlemaker.core.transformation.resourceset.IResourceSetProcessor#processResources(org.bundlemaker.core.model.module.modifiablemodule.ModifiableResourceModule,
	 *      org.bundlemaker.core.model.module.modifiablemodule.ModifiableResourceModule,
	 *      org.bundlemaker.core.model.transformation.ResourceSet)
	 */
	@Override
	public void processResources(
			IModifiableResourceModule originResourceModule,
			IModifiableResourceModule targetResourceModule,
			ResourceSet resourceSet) {

		// step 1: normalize the resource set
		normalizeResourceSet(resourceSet);

		// step 2: set the source directory (_qsSourceDirectory vs.
		// _sourceDirectory)
		ProductAttributes attributes = (ProductAttributes) targetResourceModule
				.getUserAttributes().get(ProductAttributes.KEY);
		Assert.isNotNull(attributes);
		String srcDirectory = attributes.isQs() ? _qsSourceDirectory
				: _sourceDirectory;

		// step 3: process
		Set<IResource> originSourceResources = originResourceModule
				.getModifiableSelfResourceContainer()
				.getModifiableResourcesSet(ContentType.SOURCE);

		Set<IResource> originBinaryResources = originResourceModule
				.getModifiableSelfResourceContainer()
				.getModifiableResourcesSet(ContentType.BINARY);

		Set<IResource> targetSourceResources = targetResourceModule
				.getModifiableSelfResourceContainer()
				.getModifiableResourcesSet(ContentType.SOURCE);

		Set<IResource> targetBinaryResources = targetResourceModule
				.getModifiableSelfResourceContainer()
				.getModifiableResourcesSet(ContentType.BINARY);

		// iterate over the source resource iterator
		Iterator<IResource> sourceResourceIterator = originSourceResources
				.iterator();

		// final step: we have to move the associated binary files for the
		// source files
		while (sourceResourceIterator.hasNext()) {

			//
			IResource originSourceResource = (IResource) sourceResourceIterator
					.next();

			//
			String root = FileUtils.normalize(originSourceResource.getRoot());
			if (root.equals(srcDirectory) || root.equals(_genSourceDirectory)) {

				// included?
				if (isIncluded(resourceSet, originSourceResource)) {

					// **********************************
					// step 1: move the source resource
					// **********************************
					targetSourceResources.add(originSourceResource);
					sourceResourceIterator.remove();

					// **********************************
					// step 2: move the associated binary resources
					// **********************************
					if (originSourceResource.containsTypes()) {

						//
						for (IType containedType : originSourceResource
								.getContainedTypes()) {

							if (containedType.hasBinaryResource()) {

								IResource binaryResourceStandin = containedType
										.getBinaryResource();

								targetBinaryResources
										.add(binaryResourceStandin);
								originBinaryResources
										.remove(binaryResourceStandin);

							} else {
								System.out.println("Missing binary resource '"
										+ containedType.getFullyQualifiedName()
										+ "'");
							}
						}
					} else {

						IResource associatedBinaryResource = originResourceModule
								.getResource(originSourceResource.getPath(),
										ContentType.BINARY);

						if (associatedBinaryResource != null) {
							targetBinaryResources.add(associatedBinaryResource);
							originBinaryResources
									.remove(associatedBinaryResource);
						}
					}
				}
			}
		}

		// // step 4: move resources
		//
		// System.out.println("Binary resources to move: "
		// + binaryResourcesToMove.size());
		// System.out.println("Source resources to move: "
		// + sourceResourcesToMove.size());
		//
		// TransformationUtils.addAll(
		// targetResourceModule.getModifiableSelfResourceContainer()
		// .getModifiableResources(ContentType.BINARY),
		// binaryResourcesToMove);
		//
		// System.out.println("step 4: add all binaries ("
		// + stopWatch.getElapsedTime() + ")");
		//
		// TransformationUtils.removeAll(originResourceModule,
		// binaryResourcesToMove, ContentType.BINARY);
		//
		// System.out.println("step 4: remove all binaries ("
		// + stopWatch.getElapsedTime() + ")");
		//
		// TransformationUtils.addAll(
		// targetResourceModule.getModifiableSelfResourceContainer()
		// .getModifiableResources(ContentType.SOURCE),
		// sourceResourcesToMove);
		//
		// System.out.println("step 4: add all sources ("
		// + stopWatch.getElapsedTime() + ")");
		//
		// TransformationUtils.removeAll(originResourceModule,
		// sourceResourcesToMove, ContentType.SOURCE);
		//
		// System.out.println("step 4: remove all sources ("
		// + stopWatch.getElapsedTime() + ")");
		//
		// //
		// System.out.println("step 4: move resources ("
		// + stopWatch.getElapsedTime() + ")");
	}

	private void normalizeResourceSet(ResourceSet resourceSet) {

		// step 1a: normalize resource set includes...
		List<String> normalizedIncludes = new ArrayList<String>(resourceSet
				.getIncludes().size());
		for (String include : resourceSet.getIncludes()) {
			normalizedIncludes.add(FileUtils.normalize(include));
		}
		resourceSet.getIncludes().clear();
		resourceSet.getIncludes().addAll(normalizedIncludes);

		// step 1b: normalize resource set excludes...
		List<String> normalizedExcludes = new ArrayList<String>(resourceSet
				.getExcludes().size());
		for (String exclude : resourceSet.getExcludes()) {
			normalizedExcludes.add(FileUtils.normalize(exclude));
		}
		resourceSet.getExcludes().clear();
		resourceSet.getExcludes().addAll(normalizedExcludes);
	}

	/**
	 * <p>
	 * Returns <code>true</code> if the given resource is included in the
	 * specified resource set, <code>false</code> otherwise.
	 * </p>
	 * 
	 * @param resourceSet
	 * @param resource
	 * @return
	 */
	private static boolean isIncluded(ResourceSet resourceSet,
			IResource resource) {

		//
		String path = FileUtils.normalize(resource.getPath());

		boolean included = false;

		for (String include : resourceSet.getIncludes()) {
			if (SelectorUtils.matchPath(include, path)) {
				included = true;
				break;
			}
		}

		if (!included) {
			return false;
		}

		for (String exclude : resourceSet.getExcludes()) {
			if (SelectorUtils.matchPath(exclude, path)) {
				return false;
			}
		}

		//
		return true;
	}
}
