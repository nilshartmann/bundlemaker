package org.bundlemaker.core.itestframework.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;

public class ModuleUtils {

  public static final String dump(IModule module) {

    IModularizedSystem modularizedSystem = module.getModularizedSystem();

    StringBuilder builder = new StringBuilder();
    builder.append(module.getModuleIdentifier().toString() + "\n");

    builder.append("\n");
    builder.append("Source-Content: \n");

    for (IResource resource : asSortedList(module.getResources(ProjectContentType.SOURCE))) {
      builder.append(resource.getPath() + "\n");

      for (IReference reference : asSortedList(resource.getReferences())) {
        builder.append(" * " + reference.toString() + "\n");
      }

      for (IType type : asSortedList(resource.getContainedTypes())) {
        builder.append(" - " + type.getFullyQualifiedName() + "\n");

        for (IReference reference : asSortedList(type.getReferences())) {
          builder.append("   * " + reference.toString() + "\n");
        }
      }
    }

    builder.append("\n");
    builder.append("Binary-Content: \n");
    for (IResource resource : asSortedList(module.getResources(ProjectContentType.BINARY))) {
      builder.append(resource.getPath() + "\n");

      for (IReference reference : asSortedList(resource.getReferences())) {
        builder.append(" * " + reference.toString() + "\n");
      }

      for (IResource stickyResources : asSortedList(resource.getStickyResources())) {
        builder.append(" ~sticky~ " + stickyResources.getPath() + "\n");
      }

      for (IType type : asSortedList(resource.getContainedTypes())) {
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
