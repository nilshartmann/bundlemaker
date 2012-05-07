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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

public class MoveTransformation implements ITransformation {

  /** - */
  public static final String                 USER_ATTRIBUTE_KEY = "ResourceSetBasedTransformation-USER_ATTRIBUTE_KEY";

  /** - */
  private List<TargetModuleModuleDefinition> _targetModuleModuleDefinitions;

  // /** - */
  // private IResourceSetProcessor _resourceSetProcessor;

  /**
   * <p>
   * Creates a new instance of type {@link MoveTransformation}.
   * </p>
   */
  public MoveTransformation() {
    _targetModuleModuleDefinitions = new ArrayList<TargetModuleModuleDefinition>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor progressMonitor) {

    //
    SubMonitor subMonitor = SubMonitor.convert(progressMonitor, _targetModuleModuleDefinitions.size());

    //
    for (TargetModuleModuleDefinition targetModuleDefinition : _targetModuleModuleDefinitions) {

      // get the target module
      IModuleIdentifier targetModuleIdentifier = targetModuleDefinition.getModuleIdentifier();

      subMonitor.subTask("Creating module '" + targetModuleIdentifier.toString() + "'...");

      //
      IModifiableResourceModule targetResourceModule = getOrCreateTargetModule(modularizedSystem,
          targetModuleDefinition, targetModuleIdentifier);

      // TODO
      // if (!targetResourceModule.getUserAttributes().containsKey(USER_ATTRIBUTE_KEY)) {
      // targetResourceModule.getUserAttributes().put(USER_ATTRIBUTE_KEY, moduleDefinition.getResourceSets());
      // } else {
      // @SuppressWarnings("unchecked")
      // List<ResourceSet> resourceSets = (List<ResourceSet>) targetResourceModule.getUserAttributes().get(
      // USER_ATTRIBUTE_KEY);
      // resourceSets.addAll(moduleDefinition.getResourceSets());
      // }

      for (IModifiableResourceModule resourceModule : modularizedSystem.getModifiableResourceModules()) {

        //
        if (!targetModuleDefinition.matches(resourceModule)) {
          continue;
        }

        //
        for (IMovableUnit movableUnit : resourceModule.getMovableUnits()) {

          //
          if (!targetModuleDefinition.matches(movableUnit)) {
            continue;
          }

          // move
          resourceModule.getModifiableSelfResourceContainer().removeMovableUnit(movableUnit);
          targetResourceModule.getModifiableSelfResourceContainer().addMovableUnit(movableUnit);
        }
      }

      // //
      // for (ResourceSet resourceSet : targetModuleDefinition.getResourceSets()) {
      //
      // IModifiableResourceModule originResourceModule = modularizedSystem.getModifiableResourceModule(resourceSet
      // .getModuleIdentifier());
      //
      // // origin resource module does not exist
      // if (originResourceModule == null) {
      //
      // for (IModule typeModule : modularizedSystem.getAllModules()) {
      //
      // System.out.println(" - " + typeModule.getModuleIdentifier().toString());
      // }
      //
      // throw new RuntimeException(String.format("Module '%s' does not exist.", resourceSet.getModuleIdentifier()
      // .toString()));
      // }
      //
      // // TODO
      // // // handle custom ResourceSetProcessor
      // // if (_resourceSetProcessor != null) {
      // // _resourceSetProcessor.processResources(originResourceModule, targetResourceModule, resourceSet);
      // // }
      //
      // //
      // // else {
      //
      // // }
      // }

      //
      subMonitor.worked(1);
    }
  }

  private IModifiableResourceModule getOrCreateTargetModule(IModifiableModularizedSystem modularizedSystem,
      TargetModuleModuleDefinition moduleDefinition, IModuleIdentifier targetModuleIdentifier) {
    IModifiableResourceModule targetResourceModule = modularizedSystem
        .getModifiableResourceModule(targetModuleIdentifier);

    // create a new one if necessary
    if (targetResourceModule == null) {

      targetResourceModule = modularizedSystem.createResourceModule(new ModuleIdentifier(targetModuleIdentifier
          .getName(), targetModuleIdentifier.getVersion()));
    }

    // set classifications
    setClassification(moduleDefinition, targetResourceModule);

    // set user attributes
    targetResourceModule.getUserAttributes().putAll(moduleDefinition.getUserAttributes());
    return targetResourceModule;
  }

  /**
   * Set the classification of the target module
   * 
   * <p>
   * This implementation sets the classification of the target module to the same as the of the module definition.
   * Subclasses may change this behavior.
   * 
   * @param moduleDefinition
   *          The module definition that is used to build the new module
   * @param targetResourceModule
   *          the new module that should be classified
   */
  protected void setClassification(TargetModuleModuleDefinition moduleDefinition,
      IModifiableResourceModule targetResourceModule) {

    // set classification if no classification has been set yet
    if (moduleDefinition.getClassification() != null) {
      targetResourceModule.setClassification(moduleDefinition.getClassification());
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
  public TargetModuleModuleDefinition addModuleDefinition(String name, String version) {
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
  public TargetModuleModuleDefinition addModuleDefinition(

  String name, String version, Map<String, Object> userAttibutes) {

    Assert.isNotNull(name);
    Assert.isNotNull(version);

    // TODO: check if duplicate

    // create
    TargetModuleModuleDefinition moduleDefinition = new TargetModuleModuleDefinition();
    ModuleIdentifier targetIdentifier = new ModuleIdentifier(name, version);
    moduleDefinition.setModuleIdentifier(targetIdentifier);
    moduleDefinition.getUserAttributes().putAll(userAttibutes);

    // add
    _targetModuleModuleDefinitions.add(moduleDefinition);

    // return result
    return moduleDefinition;
  }

  // /**
  // * <p>
  // * </p>
  // *
  // * @param originResourceModule
  // * @param targetResourceModule
  // * @param resourceSet
  // */
  // private void processResources(IModifiableResourceModule originResourceModule,
  // IModifiableResourceModule targetResourceModule, ResourceSet resourceSet, IModularizedSystem modularizedSystem) {
  //
  // List<IResource> resourceStandinsToMove = resourceSet.getMatchingResources(originResourceModule,
  // ContentType.BINARY);
  //
  // for (IResource iResource : resourceStandinsToMove) {
  //
  // IMovableUnit movableUnit = MovableUnit.createFromResource(iResource, modularizedSystem);
  // originResourceModule.getModifiableSelfResourceContainer().removeMovableUnit(movableUnit);
  // targetResourceModule.getModifiableSelfResourceContainer().addMovableUnit(movableUnit);
  // }
  // }

  // public void setResourceSetProcessor(IResourceSetProcessor resourceSetProcessor) {
  // _resourceSetProcessor = resourceSetProcessor;
  // }
}
