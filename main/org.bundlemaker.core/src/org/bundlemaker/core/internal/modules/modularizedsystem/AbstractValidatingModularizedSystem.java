/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.internal.modules.modularizedsystem;

import org.bundlemaker.core.internal.modules.ResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;

/**
 */
public abstract class AbstractValidatingModularizedSystem extends AbstractArtifactModelAwareModularizedSystem {

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
