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
package org.bundlemaker.core.transformations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.core.runtime.IProgressMonitor;

public class EmbedModuleTransformation implements ITransformation {

  private IModuleIdentifier       _hostModuleIdentifier;

  private List<IModuleIdentifier> _embeddedModulesIdentifiers;

  /**
   * <p>
   * Creates a new instance of type {@link EmbedModuleTransformation}.
   * </p>
   */
  public EmbedModuleTransformation() {

    //
    _embeddedModulesIdentifiers = new ArrayList<IModuleIdentifier>();
  }

  public IModuleIdentifier getHostModuleIdentifier() {
    return _hostModuleIdentifier;
  }

  public void setHostModuleIdentifier(IModuleIdentifier hostModuleIdentifier) {
    _hostModuleIdentifier = hostModuleIdentifier;
  }

  public List<IModuleIdentifier> getEmbeddedModulesIdentifiers() {
    return _embeddedModulesIdentifiers;
  }

  @Override
  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor monitor) {

    // step 1: define modules variables
    IResourceModule hostModule = null;

    List<IResourceModule> embeddedModules = new LinkedList<IResourceModule>();

    // step 2: fetch host and embedded modules
    for (IModifiableResourceModule embeddedModule : modularizedSystem.getModifiableResourceModules()) {

      // try to fetch host module
      if (embeddedModule.getModuleIdentifier().equals(_hostModuleIdentifier)) {

        // set the host module
        hostModule = embeddedModule;
      }

      // try to fetch embedded modules
      else {

        for (IModuleIdentifier embeddedModulesIdentifier : _embeddedModulesIdentifiers) {

          if (embeddedModule.getModuleIdentifier().equals(embeddedModulesIdentifier)) {
            embeddedModules.add(embeddedModule);
          }
        }
      }
    }

    // step 3: add the embedded modules as container
    for (IResourceModule embeddedModule : embeddedModules) {

      // // get the module name
      // String embeddedModuleName = ModelUtils.toString(embeddedModule
      // .getModuleIdentifier());
      //
      // // get the resource container
      // ResourceContainer embeddedContainer = embeddedModule
      // .getSelfContainer().getModifiableResourcesSet(contentType);
      //
      // // embed the container
      // hostModule.getModifiableContainedResourceContainers().put(
      // embeddedModuleName, embeddedContainer);
      //
      // // embed all embedded containers
      // if (embeddedModule.getModifiableContainedResourceContainers()
      // .size() > 0) {
      //
      // // add the map
      // hostModule.getModifiableContainedResourceContainers().putAll(
      // embeddedModule
      // .getModifiableContainedResourceContainers());
      // }

      // remove as top level module
      modularizedSystem.removeModule(embeddedModule.getModuleIdentifier());
    }
  }
}
