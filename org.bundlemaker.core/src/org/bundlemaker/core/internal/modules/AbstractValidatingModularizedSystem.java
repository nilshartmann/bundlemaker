package org.bundlemaker.core.internal.modules;

import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;

/**
 */
public abstract class AbstractValidatingModularizedSystem extends AbstractCachingModularizedSystem {

  /**
   * <p>
   * </p>
   * 
   * @param name
   * @param projectDescription
   */
  public AbstractValidatingModularizedSystem(String name, IBundleMakerProjectDescription projectDescription) {

    //
    super(name, projectDescription);
  }

  @Override
  protected void postApplyTransformations() {
    assertTypesHaveModules();
  }

  /**
   * <p>
   * </p>
   * 
   */
  private void assertTypesHaveModules() {

    // CHECK: check for duplicate entries
    for (IModifiableResourceModule module : getModifiableResourceModulesMap().values()) {

      ((ResourceModule) module).validate();
    }
  }
}
