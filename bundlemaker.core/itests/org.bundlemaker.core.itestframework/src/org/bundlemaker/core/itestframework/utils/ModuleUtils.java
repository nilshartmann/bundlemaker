package org.bundlemaker.core.itestframework.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.jtype.IReference;
import org.bundlemaker.core.jtype.IType;
import org.bundlemaker.core.jtype.ITypeResource;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;

public class ModuleUtils {

  public static final String dump(IModule module) {

    IModularizedSystem modularizedSystem = module.getModularizedSystem();

    StringBuilder builder = new StringBuilder();
    builder.append(module.getModuleIdentifier().toString() + "\n");

    builder.append("\n");
    builder.append("Source-Content: \n");

    for (IProjectContentResource resource : asSortedList((Set<IProjectContentResource>)module.getResources(ResourceType.SOURCE))) {
      builder.append(resource.getPath() + "\n");

      for (IReference reference : asSortedList(resource.adaptAs(ITypeResource.class).getReferences())) {
        builder.append(" * " + reference.toString() + "\n");
      }

      for (IType type : asSortedList(resource.adaptAs(ITypeResource.class).getContainedTypes())) {
        builder.append(" - " + type.getFullyQualifiedName() + "\n");

        for (IReference reference : asSortedList(type.getReferences())) {
          builder.append("   * " + reference.toString() + "\n");
        }
      }
    }

    builder.append("\n");
    builder.append("Binary-Content: \n");
    for (IProjectContentResource resource : asSortedList((Set<IProjectContentResource>)module.getResources(ResourceType.BINARY))) {
      builder.append(resource.getPath() + "\n");

      for (IReference reference : asSortedList(resource.adaptAs(ITypeResource.class).getReferences())) {
        builder.append(" * " + reference.toString() + "\n");
      }

//      for (IProjectContentResource stickyResources : asSortedList((Set<IProjectContentResource>)resource.getStickyResources())) {
//        builder.append(" ~sticky~ " + stickyResources.getPath() + "\n");
//      }

      for (IType type : asSortedList(resource.adaptAs(ITypeResource.class).getContainedTypes())) {
        builder.append(" - " + type.getFullyQualifiedName() + "\n");

        for (IReference reference : asSortedList(type.getReferences())) {
          builder.append("   * " + reference.toString() + "\n");
        }
      }
    }

    // builder.append("\n");
    // builder.append("Referenced Types: \n");
    // Set<String> referencedTypes = module
    // .getReferencedTypeNames(ReferenceQueryFilters.ALL_DIRECT_EXTERNAL_REFERENCES_QUERY_FILTER);
    // for (String referencedType : asSortedList(referencedTypes)) {
    // builder.append(referencedType + "\n");
    // }
    //
    // builder.append("\n");
    // builder.append("Indirectly referenced Types: \n");
    // Set<String> indirectlyReferencedTypes = module
    // .getReferencedTypeNames(ReferenceQueryFilters.ALL_DIRECT_EXTERNAL_REFERENCES_QUERY_FILTER);
    // for (String referencedType : asSortedList(indirectlyReferencedTypes)) {
    // if (!referencedTypes.contains(referencedType)) {
    // builder.append(referencedType + "\n");
    // }
    // }

    // builder.append("\n");
    // builder.append("Referenced Modules: \n");
    // IReferencedModulesQueryResult queryResult = modularizedSystem.getReferencedModules(module);
    //
    // for (IModule referencedModule : queryResult.getReferencedModules()) {
    // builder.append(referencedModule.getModuleIdentifier().toString() + "\n");
    // }
    //
    // // TODO
    // builder.append("\n");
    // builder.append("Missing Types: \n");
    // for (IReference missingType : asSortedList(queryResult.getUnsatisfiedReferences())) {
    // builder.append(missingType + "\n");
    // }

    return builder.toString();
  }

  /**
   * <p>
   * </p>
   * 
   * @param <T>
   * @param set
   * @return
   */
  private static <T extends Comparable<T>> List<T> asSortedList(Set<T> set) {

    //
    List<T> arrayList = new ArrayList<T>(set);

    //
    Collections.sort(arrayList);

    //
    return arrayList;
  }
}
