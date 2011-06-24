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
import java.util.List;

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.transformation.ITransformation;
import org.bundlemaker.core.transformations.resourceset.ResourceSet;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class RemoveResourcesTransformation implements ITransformation {

  /** - */
  private List<ResourceSet> _resourcesToRemove;

  public RemoveResourcesTransformation() {
    _resourcesToRemove = new ArrayList<ResourceSet>();
  }

  public List<ResourceSet> getResourcesToRemove() {
    return _resourcesToRemove;
  }

  @Override
  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor monitor) {

    SubMonitor subMonitor = SubMonitor.convert(monitor, _resourcesToRemove.size());

    //
    for (ResourceSet resourceSet : _resourcesToRemove) {
      // Notify user via subMonitor
      subMonitor.subTask("Removing resources from " + resourceSet.getModuleIdentifier());

      //
      IModifiableResourceModule resourceModule = modularizedSystem.getModifiableResourceModule(resourceSet
          .getModuleIdentifier());

      List<IResource> resourceStandinsToMove = resourceSet.getMatchingResources(resourceModule, ContentType.BINARY);

      resourceModule.getModifiableSelfResourceContainer().removeAll(resourceStandinsToMove, ContentType.BINARY);
      // TransformationUtils.removeAll(resourceModule, resourceStandinsToMove, );

      resourceStandinsToMove = resourceSet.getMatchingResources(resourceModule, ContentType.SOURCE);

      resourceModule.getModifiableSelfResourceContainer().removeAll(resourceStandinsToMove, ContentType.SOURCE);
      // TransformationUtils.removeAll(resourceModule, resourceStandinsToMove, ContentType.SOURCE);

      // increment the subMonitor
      subMonitor.worked(1);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param name
   * @param version
   * @param includes
   * @param excludes
   */
  public void addResourceSet(RemoveResourcesTransformation transformation, String name, String version,
      String[] includes, String[] excludes) {

    //
    addResourceSet(new ModuleIdentifier(name, version), includes, excludes);
  }

  /**
   * <p>
   * </p>
   * 
   * @param fromModuleIdentifier
   * @param includes
   * @param excludes
   */
  public void addResourceSet(IModuleIdentifier fromModuleIdentifier, String[] includes, String[] excludes) {

    //
    ResourceSet resourceSet = new ResourceSet();

    //
    resourceSet.setModuleIdentifier(fromModuleIdentifier);

    //
    if (includes != null) {
      for (String include : includes) {
        resourceSet.getIncludes().add(include);
      }
    }

    //
    if (excludes != null) {
      for (String exclude : excludes) {
        resourceSet.getExcludes().add(exclude);
      }
    }

    //
    _resourcesToRemove.add(resourceSet);
  }
}
