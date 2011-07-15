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

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceSetBasedModuleDefinition {

  /** - */
  private IModuleIdentifier   _moduleIdentifier;

  /** - */
  private IPath               _classification;

  /** - */
  private List<ResourceSet>   _resourceSets;

  /** - */
  private Map<String, Object> _userAttributes;

  /**
   * <p>
   * Creates a new instance of type {@link ResourceSetBasedModuleDefinition}.
   * </p>
   */
  public ResourceSetBasedModuleDefinition() {

    //
    _resourceSets = new ArrayList<ResourceSet>();
    _userAttributes = new HashMap<String, Object>();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IModuleIdentifier getModuleIdentifier() {
    return _moduleIdentifier;
  }

  /**
   * <p>
   * </p>
   * 
   * @param moduleIdentifier
   */
  public void setModuleIdentifier(IModuleIdentifier moduleIdentifier) {
    _moduleIdentifier = moduleIdentifier;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IPath getClassification() {
    return _classification;
  }

  /**
   * <p>
   * </p>
   * 
   * @param classification
   */
  public void setClassification(IPath classification) {
    _classification = classification;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<ResourceSet> getResourceSets() {
    return _resourceSets;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Map<String, Object> getUserAttributes() {
    return _userAttributes;
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceSetBasedModuleDefinition
   * @param fromModuleIdentifier
   * @param includes
   * @param excludes
   */
  public void addResourceSet(IModuleIdentifier fromModuleIdentifier, String[] includes, String[] excludes) {

    ResourceSet resourceSet = new ResourceSet();

    resourceSet.setModuleIdentifier(fromModuleIdentifier);

    if (includes != null) {
      for (String include : includes) {
        resourceSet.getIncludes().add(include);
      }
    }

    if (excludes != null) {
      for (String exclude : excludes) {
        resourceSet.getExcludes().add(exclude);
      }
    }

    _resourceSets.add(resourceSet);
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceSetBasedModuleDefinition
   * @param fromName
   * @param fromVersion
   * @param includes
   * @param excludes
   */
  public void addResourceSet(String fromName, String fromVersion, String[] includes, String[] excludes) {

    IModuleIdentifier originIdentifier = new ModuleIdentifier(fromName, fromVersion);

    addResourceSet(originIdentifier, includes, excludes);
  }
}
