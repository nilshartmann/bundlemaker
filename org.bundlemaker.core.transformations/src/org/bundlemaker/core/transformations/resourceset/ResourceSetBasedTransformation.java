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
package org.bundlemaker.core.transformations.resourceset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.transformation.ITransformation;
import org.bundlemaker.core.util.TransformationUtils;
import org.eclipse.core.runtime.Assert;

public class ResourceSetBasedTransformation implements ITransformation {

  /** - */
  public static final String                     USER_ATTRIBUTE_KEY = "ResourceSetBasedTransformation-USER_ATTRIBUTE_KEY";

  /** - */
  private List<ResourceSetBasedModuleDefinition> _moduleDefinitions;

  /** - */
  private IResourceSetProcessor                  _resourceSetProcessor;

  /**
   * <p>
   * Creates a new instance of type {@link ResourceSetBasedTransformation}.
   * </p>
   */
  public ResourceSetBasedTransformation() {
    _moduleDefinitions = new ArrayList<ResourceSetBasedModuleDefinition>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void apply(IModifiableModularizedSystem modularizedSystem) {

    //
    for (ResourceSetBasedModuleDefinition moduleDefinition : _moduleDefinitions) {

      // get the target module
      IModuleIdentifier targetModuleIdentifier = moduleDefinition.getModuleIdentifier();

      // log
      System.out.println("Creating module '" + targetModuleIdentifier.toString() + "'...");

      IModifiableResourceModule targetResourceModule = modularizedSystem
          .getModifiableResourceModule(targetModuleIdentifier);

      // create a new one if necessary
      if (targetResourceModule == null) {

        targetResourceModule = modularizedSystem.createResourceModule(new ModuleIdentifier(targetModuleIdentifier
            .getName(), targetModuleIdentifier.getVersion()));
      }

      // set classifications
      if (moduleDefinition.getClassification() != null) {
        targetResourceModule.setClassification(moduleDefinition.getClassification());
      }

      // set user attributes
      targetResourceModule.getUserAttributes().putAll(moduleDefinition.getUserAttributes());

      if (!targetResourceModule.getUserAttributes().containsKey(USER_ATTRIBUTE_KEY)) {
        targetResourceModule.getUserAttributes().put(USER_ATTRIBUTE_KEY, moduleDefinition.getResourceSets());
      } else {
        @SuppressWarnings("unchecked")
        List<ResourceSet> resourceSets = (List<ResourceSet>) targetResourceModule.getUserAttributes().get(
            USER_ATTRIBUTE_KEY);
        resourceSets.addAll(moduleDefinition.getResourceSets());
      }

      //
      for (ResourceSet resourceSet : moduleDefinition.getResourceSets()) {

        IModifiableResourceModule originResourceModule = modularizedSystem.getModifiableResourceModule(resourceSet
            .getModuleIdentifier());

        // origin resource module does not exist
        if (originResourceModule == null) {

          for (IModule typeModule : modularizedSystem.getAllModules()) {

            System.out.println(" - " + typeModule.getModuleIdentifier().toString());
          }

          throw new RuntimeException(String.format("Module '%s' does not exist.", resourceSet.getModuleIdentifier()
              .toString()));
        }

        // handle custom ResourceSetProcessor
        if (_resourceSetProcessor != null) {
          _resourceSetProcessor.processResources(originResourceModule, targetResourceModule, resourceSet);
        }

        //
        else {
          processResources(originResourceModule, targetResourceModule, resourceSet);
        }
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param name
   * @param version
   * @return
   */
  public ResourceSetBasedModuleDefinition addModuleDefinition(String name, String version) {

    return addModuleDefinition(name, version, new HashMap<String, Object>());
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceSetBasedTransformationImpl
   * @param name
   * @param version
   * @return
   */
  public ResourceSetBasedModuleDefinition addModuleDefinition(

  String name, String version, Map<String, Object> userAttibutes) {

    Assert.isNotNull(name);
    Assert.isNotNull(version);

    // TODO: check if duplicate

    // create
    ResourceSetBasedModuleDefinition moduleDefinition = new ResourceSetBasedModuleDefinition();
    ModuleIdentifier targetIdentifier = new ModuleIdentifier(name, version);
    moduleDefinition.setModuleIdentifier(targetIdentifier);
    moduleDefinition.getUserAttributes().putAll(userAttibutes);

    // add
    _moduleDefinitions.add(moduleDefinition);

    // return result
    return moduleDefinition;
  }

  /**
   * <p>
   * </p>
   * 
   * @param originResourceModule
   * @param targetResourceModule
   * @param resourceSet
   */
  private void processResources(IModifiableResourceModule originResourceModule,
      IModifiableResourceModule targetResourceModule, ResourceSet resourceSet) {

    List<IResource> resourceStandinsToMove = resourceSet.getMatchingResources(originResourceModule, ContentType.BINARY);

    TransformationUtils.addAll(
        targetResourceModule.getModifiableSelfResourceContainer().getModifiableResourcesSet(ContentType.BINARY),
        resourceStandinsToMove);

    TransformationUtils.removeAll(originResourceModule, resourceStandinsToMove, ContentType.BINARY);

    resourceStandinsToMove = resourceSet.getMatchingResources(originResourceModule, ContentType.SOURCE);

    TransformationUtils.addAll(
        targetResourceModule.getModifiableSelfResourceContainer().getModifiableResourcesSet(ContentType.SOURCE),
        resourceStandinsToMove);

    TransformationUtils.removeAll(originResourceModule, resourceStandinsToMove, ContentType.SOURCE);
  }

  public void setResourceSetProcessor(IResourceSetProcessor resourceSetProcessor) {
    _resourceSetProcessor = resourceSetProcessor;
  }
}
