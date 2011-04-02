package org.bundlemaker.core.ui.editor.transformation;

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.transformations.dsl.transformationDsl.ModuleIdentifier;
import org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceList;

public class DslUtils {

  public static String[] nullSafeAsArray(ResourceList resourceList) {
    if (resourceList == null) {
      return new String[0];
    }
    return (String[]) resourceList.getResources().toArray(new String[0]);
  }

  public static IModuleIdentifier asIModuleIdentifier(ModuleIdentifier moduleIdentifier) {
    return new org.bundlemaker.core.modules.ModuleIdentifier(moduleIdentifier.getModulename(),
        moduleIdentifier.getVersion());
  }

}
