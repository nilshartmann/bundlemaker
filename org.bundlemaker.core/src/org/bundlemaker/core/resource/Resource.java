package org.bundlemaker.core.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;

public class Resource extends AbstractResourceKey implements IResource {

	/** - */
	private List<Reference> _references;

	/** - */
	private List<String> _containedTypes;

	/** - */
	private List<Resource> _associatedResource;

	/** - */
	private IResourceStandin _resourceStandin;

	/**
	 * <p>
	 * Creates a new instance of type {@link Resource}.
	 * </p>
	 * 
	 * @param contentId
	 * @param root
	 * @param path
	 */
	public Resource(String contentId, String root, String path) {
		super(contentId, root, path);

		_references = new ArrayList<Reference>();
		_containedTypes = new ArrayList<String>();
		_associatedResource = new ArrayList<Resource>();
	}

	// /**
	// * <p>
	// * Creates a new instance of type {@link Resource}.
	// * </p>
	// *
	// * @param contentId
	// * @param root
	// * @param path
	// */
	// public Resource(String contentId, String root, String path) {
	// super(contentId, root, path);
	//
	// _references = new ArrayList<Reference>();
	// _containedTypes = new ArrayList<String>();
	// _associatedResource = new ArrayList<Resource>();
	// }

	// /**
	// * <p>
	// * Creates a new instance of type {@link Resource}.
	// * </p>
	// */
	// public Resource() {
	// _references = new ArrayList<Reference>();
	// _containedTypes = new ArrayList<String>();
	// _associatedResource = new ArrayList<Resource>();
	// }

	@Override
	public List<? extends IReference> getReferences() {
		List<? extends IReference> result = Collections
				.unmodifiableList(_references);
		return result;
	}

	@Override
	public List<? extends IResource> getAssociatedResources() {
		List<? extends IResource> result = Collections
				.unmodifiableList(_associatedResource);
		return result;
	}

	@Override
	public List<String> getContainedTypes() {
		return Collections.unmodifiableList(_containedTypes);
	}

	@Override
	public IResourceStandin getResourceStandin() {
		return _resourceStandin;
	}

	public void setResourceStandin(IResourceStandin resourceStandin) {
		_resourceStandin = resourceStandin;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public List<String> getModifiableContainedTypes() {
		return _containedTypes;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 */
	public Reference createOrGetReference(String fullyQualifiedName,
			ReferenceType referenceType) {

		// assert
		Assert.isNotNull(fullyQualifiedName);

		// return 'null reference' if the package a java.* package
		if (fullyQualifiedName.startsWith("java.")) {
			return new Reference(fullyQualifiedName, referenceType);
		}

		// search existing references
		for (Reference reference : _references) {
			if (reference.getFullyQualifiedName().equals(fullyQualifiedName)
					&& reference.getReferenceType().equals(referenceType)) {
				return reference;
			}
		}

		// create and add the new reference
		Reference reference = new Reference(fullyQualifiedName, referenceType);
		_references.add(reference);

		// return the new reference
		return reference;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param associatedResource
	 */
	public void addAssociatedResource(Resource associatedResource) {

		// add associated resource
		_associatedResource.add(associatedResource);

		// add references
		for (Reference reference : associatedResource._references) {

			Reference sourceFileRef = createOrGetReference(
					reference.getFullyQualifiedName(),
					reference.getReferenceType());

			sourceFileRef.setByteCodeDependency(true);
		}
	}
}
