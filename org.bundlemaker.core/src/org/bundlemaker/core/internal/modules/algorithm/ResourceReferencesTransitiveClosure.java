package org.bundlemaker.core.internal.modules.algorithm;

import java.util.Set;

import org.bundlemaker.core.internal.modules.ModularizedSystem;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;

/**
 * <p>
 * </p>
 */
public class ResourceReferencesTransitiveClosure extends
		AbstractResourceClosureQuery {

	/**
	 * <p>
	 * Creates a new instance of type
	 * {@link ResourceReferencesTransitiveClosure}.
	 * </p>
	 * 
	 * @param modularizedSystem
	 */
	public ResourceReferencesTransitiveClosure(
			ModularizedSystem modularizedSystem) {
		super(modularizedSystem);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param typeName
	 */
	public void resolveResource(IResource resource, ContentType contentType,
			IQueryFilter<IType> queryFilter) {

		//
		if (getResources().contains(resource)) {
			return;
		}

		getResources().add(resource);

		//
		for (IReference reference : resource.getResourceAndTypeReferences()) {

			//
			String typeName = reference.getFullyQualifiedName();

			Set<IType> types = getModularizedSystem().getTypeNameToTypeCache()
					.get(typeName);

			if (types == null || types.isEmpty()) {
				System.out.println("NO TYPE FOR '" + typeName + "'.");
				return;
			}

			if (types.size() > 1) {
				System.out.println("MULTIPLE TYPE FOR '" + typeName + "'.");
			}

			//
			IType type = ((IType[]) types.toArray(new IType[0]))[0];

			//
			IResource res = contentType.equals(ContentType.SOURCE) ? type
					.getSourceResource() : type.getBinaryResource();

			if (res != null) {
				resolveResource(res, contentType, queryFilter);
			}
		}
	}
}
