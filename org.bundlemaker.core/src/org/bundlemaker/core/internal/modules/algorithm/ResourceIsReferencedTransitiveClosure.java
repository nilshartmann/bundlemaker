package org.bundlemaker.core.internal.modules.algorithm;

import java.util.Set;

import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;

/**
 * <p>
 * </p>
 */
public class ResourceIsReferencedTransitiveClosure extends AbstractResourceClosureQuery {

  /**
   * <p>
   * Creates a new instance of type {@link ResourceIsReferencedTransitiveClosure}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public ResourceIsReferencedTransitiveClosure(ModularizedSystem modularizedSystem) {
    super(modularizedSystem);
  }

  /**
   * <p>
   * </p>
   * 
   * @param typeName
   */
  public void resolveResource(IResource resource, ContentType contentType, IQueryFilter<IResource> queryFilter) {

    //
    if (getResources().contains(resource)) {
      return;
    }

    //
    getResources().add(resource);

    //
    for (IType containedType : resource.getContainedTypes()) {

      Set<IType> types = getModularizedSystem().getTypeNameToReferringCache()
          .get(containedType.getFullyQualifiedName());

      if (types == null || types.isEmpty()) {
        System.out.println("NO TYPE REFERS TO '" + containedType.getFullyQualifiedName() + "'.");
        return;
      }

      for (IType type : types) {

        //
        IResource iResource = contentType.equals(ContentType.SOURCE) ? type.getSourceResource() : type
            .getBinaryResource();

        //
        if (iResource != null && queryFilter.matches(iResource)) {
          resolveResource(iResource, contentType, queryFilter);
        }
      }
    }
  }
}
