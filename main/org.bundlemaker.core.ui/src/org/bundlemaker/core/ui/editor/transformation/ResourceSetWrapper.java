package org.bundlemaker.core.ui.editor.transformation;

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.transformations.dsl.transformationDsl.ResourceSet;

public class ResourceSetWrapper {
  private final ResourceSet _resourceSet;

  public static ResourceSetWrapper create(ResourceSet resourceSet) {
    return new ResourceSetWrapper(resourceSet);
  }

  private ResourceSetWrapper(ResourceSet resourceSet) {
    _resourceSet = resourceSet;
  }

  public IModuleIdentifier getModuleIdentifier() {
    return DslUtils.asIModuleIdentifier(_resourceSet.getModuleIdentifier());
  }

  public String[] getIncludes() {
    return DslUtils.nullSafeAsArray(_resourceSet.getIncludeResources());
  }

  public String[] getExcludes() {
    return DslUtils.nullSafeAsArray(_resourceSet.getExcludeResources());
  }

}
