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
import org.bundlemaker.core.projectdescription.IProjectDescription;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModularizedSystem extends AbstractArtifactModelAwareModularizedSystem {

  /**
   * <p>
   * Creates a new instance of type {@link ModularizedSystem}.
   * </p>
   * 
   * @param name
   */
  public ModularizedSystem(String name, IProjectDescription projectDescription) {
    super(name, projectDescription);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void postApplyTransformations() {

    // validate the resource modules
    for (IModifiableResourceModule module : getModifiableResourceModules()) {
      ((ResourceModule) module).validate();
    }
  }
}
