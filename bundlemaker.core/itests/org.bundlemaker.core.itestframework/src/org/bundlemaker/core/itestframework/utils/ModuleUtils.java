package org.bundlemaker.core.itestframework.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core._type.IReference;
import org.bundlemaker.core._type.IType;
import org.bundlemaker.core._type.ITypeResource;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.ResourceType;

public class ModuleUtils {

  public static final String dump(IModule module) {

    IModularizedSystem modularizedSystem = module.getModularizedSystem();

    StringBuilder builder = new StringBuilder();
    builder.append(module.getModuleIdentifier().toString() + "\n");

    builder.append("\n");
    builder.append("Source-Content: \n");

    for (IModuleResource resource : asSortedList((Set<IModuleResource>)module.getResources(ResourceType.SOURCE))) {
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
    for (IModuleResource resource : asSortedList((Set<IModuleResource>)module.getResources(ResourceType.BINARY))) {
      builder.append(resource.getPath() + "\n");

      for (IReference reference : asSortedList(resource.adaptAs(ITypeResource.class).getReferences())) {
        builder.append(" * " + reference.toString() + "\n");
      }

      for (IModuleResource stickyResources : asSortedList((Set<IModuleResource>)resource.getStickyResources())) {
        builder.append(" ~sticky~ " + stickyResources.getPath() + "\n");
      }

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
