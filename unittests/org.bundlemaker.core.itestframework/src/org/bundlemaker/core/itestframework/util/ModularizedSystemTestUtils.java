package org.bundlemaker.core.itestframework.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;

public class ModularizedSystemTestUtils {

  /** - */
  private static final ResourceComparator  resourceComparator  = new ResourceComparator();

  /** - */
  private static final ReferenceComparator referenceComparator = new ReferenceComparator();

  private static final TypeComparator      TYPE_COMPARATOR     = new TypeComparator();

  /**
   * <p>
   * </p>
   * 
   * @param module
   * @return
   */
  public static String dump(IResourceModule module, IModularizedSystem modularizedSystem) {

    StringBuilder builder = new StringBuilder();
    builder.append(module.getModuleIdentifier().toString() + "\n");

    builder.append("\n");
    builder.append("Source-Content: \n");

    for (IResource resource : sortResources(module.getResources(ContentType.SOURCE))) {
      builder.append(resource.getPath() + "\n");

      for (IReference reference : sortReferences(resource.getReferences())) {
        builder.append(" * " + reference.toString() + "\n");
      }

      for (IType type : sortTypes(resource.getContainedTypes())) {
        builder.append(" - " + type.getFullyQualifiedName() + "\n");

        for (IReference reference : sortReferences(type.getReferences())) {
          builder.append("   * " + reference.toString() + "\n");
        }
      }
    }

    builder.append("\n");
    builder.append("Binary-Content: \n");
    for (IResource resource : sortResources((module.getResources(ContentType.BINARY)))) {
      builder.append(resource.getPath() + "\n");

      for (IReference reference : sortReferences(resource.getReferences())) {
        builder.append(" * " + reference.toString() + "\n");
      }

      for (IResource stickyResources : sortResources(resource.getStickyResources())) {
        builder.append(" ~sticky~ " + stickyResources.getPath() + "\n");
      }

      for (IType type : sortTypes(resource.getContainedTypes())) {
        builder.append(" - " + type.getFullyQualifiedName() + "\n");

        for (IReference reference : sortReferences(type.getReferences())) {
          builder.append("   * " + reference.toString() + "\n");
        }
      }
    }

    // builder.append("\n");
    // builder.append("Referenced Types: \n");
    // Set<String> referencedTypes = module
    // .getReferencedTypeNames(ReferenceQueryFilters.ALL_DIRECT_EXTERNAL_REFERENCES_QUERY_FILTER);
    // for (String referencedType : BundleMakerTestUtils.asSortedList(referencedTypes)) {
    // builder.append(referencedType + "\n");
    // }
    //
    // builder.append("\n");
    // builder.append("Indirectly referenced Types: \n");
    // Set<String> indirectlyReferencedTypes = module
    // .getReferencedTypeNames(ReferenceQueryFilters.ALL_DIRECT_EXTERNAL_REFERENCES_QUERY_FILTER);
    // for (String referencedType : BundleMakerTestUtils.asSortedList(indirectlyReferencedTypes)) {
    // if (!referencedTypes.contains(referencedType)) {
    // builder.append(referencedType + "\n");
    // }
    // }
    //
    // builder.append("\n");
    // builder.append("Referenced Modules: \n");
    // IReferencedModulesQueryResult queryResult = modularizedSystem.getReferencedModules(module, null);
    //
    // for (IModule referencedModule : queryResult.getReferencedModules()) {
    // builder.append(referencedModule.getModuleIdentifier().toString() + "\n");
    // }
    //
    // // TODO
    // builder.append("\n");
    // builder.append("Missing Types: \n");
    // for (IReference missingType : queryResult.getUnsatisfiedReferences()) {
    // builder.append(missingType + "\n");
    // }

    //
    return builder.toString();
  }

  private static class ResourceComparator implements Comparator<IResource> {
    @Override
    public int compare(IResource o1, IResource o2) {
      return (o1.getRoot() + o1.getPath()).compareTo(o2.getRoot() + o2.getPath());
    }
  }

  private static class ReferenceComparator implements Comparator<IReference> {
    @Override
    public int compare(IReference o1, IReference o2) {
      return (o1.getFullyQualifiedName() + o1.getType()).compareTo(o2.getFullyQualifiedName() + o2.getType());
    }
  }

  private static class TypeComparator implements Comparator<IType> {
    @Override
    public int compare(IType o1, IType o2) {
      return (o1.getFullyQualifiedName()).compareTo(o2.getFullyQualifiedName());
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param references
   * @return
   */
  private static List<IReference> sortReferences(Set<? extends IReference> references) {
    List<IReference> result = new LinkedList<IReference>(references);
    Collections.sort(result, referenceComparator);
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param resources
   * @return
   */
  private static List<IResource> sortResources(Set<? extends IResource> resources) {
    List<IResource> result = new LinkedList<IResource>(resources);
    Collections.sort(result, resourceComparator);
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param resources
   * @return
   */
  private static List<IType> sortTypes(Set<? extends IType> types) {
    List<IType> result = new LinkedList<IType>(types);
    Collections.sort(result, TYPE_COMPARATOR);
    return result;
  }
}
