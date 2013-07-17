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
package org.bundlemaker.core.internal.transformation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.internal.api.resource.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.api.resource.IModifiableModule;
import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractModularizedSystem;
import org.bundlemaker.core.internal.resource.DispatchingMovableUnitCreator;
import org.bundlemaker.core.internal.resource.ModuleIdentifier;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.eclipse.core.runtime.IProgressMonitor;

public class BasicProjectContentTransformation implements IInternalTransformation {

  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor progressMonitor) {

    // iterate over the file based content
    for (IProjectContentEntry projectContentEntry : modularizedSystem.getBundleMakerProject().getProjectDescription()
        .getContent()) {

      // create new module
      IModifiableModule module = modularizedSystem.createResourceModule(new ModuleIdentifier(projectContentEntry
          .getName(), projectContentEntry.getVersion()));

      // put the user attributes
      module.getUserAttributes().putAll(projectContentEntry.getUserAttributes());

      //
      for (IMovableUnit movableUnit : setupMovableUnits(projectContentEntry)) {
        module.addMovableUnit(movableUnit);
      }

      //
      if (projectContentEntry.getUserAttributes().containsKey("EXECUTION_ENVIRONMENT")) {
        ((AbstractModularizedSystem) modularizedSystem).setExecutionEnvironment(module);
      }
    }
  }

  /**
   * <p>
   * </p>
   */
  public Set<IMovableUnit> setupMovableUnits(IProjectContentEntry projectContentEntry) {

    //
    DispatchingMovableUnitCreator unitCreator = new DispatchingMovableUnitCreator();

    //
    Map<String, IModuleResource> binaries = new HashMap<String, IModuleResource>();
    for (IProjectContentResource resource : projectContentEntry.getBinaryResources()) {
      if (resource instanceof IModuleResource) {
        binaries.put(resource.getPath(), (IModuleResource) resource);
      }
    }

    //
    Map<String, IModuleResource> sources = new HashMap<String, IModuleResource>();
    for (IProjectContentResource resource : projectContentEntry.getSourceResources()) {
      if (resource instanceof IModuleResource) {
        sources.put(resource.getPath(), (IModuleResource) resource);
      }
    }

    //
    return unitCreator.assignMovableUnits(binaries, sources);
  }
}
