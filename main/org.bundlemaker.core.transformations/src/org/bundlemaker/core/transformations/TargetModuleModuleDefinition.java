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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.transformations.selectors.ByNameAndVersionResourceModuleSelector;
import org.bundlemaker.core.transformations.selectors.ByNameResourceModuleSelector;
import org.bundlemaker.core.transformations.selectors.IMovableUnitSelector;
import org.bundlemaker.core.transformations.selectors.IResourceModuleSelector;
import org.bundlemaker.core.transformations.selectors.PatternBasedMovableUnitSelector;
import org.bundlemaker.core.transformations.selectors.ResourceModuleAndMovableUnitSelector;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TargetModuleModuleDefinition implements IMovableUnitSelector, IResourceModuleSelector {

  /** - */
  private IModuleIdentifier                          _moduleIdentifier;

  /** - */
  private IPath                                      _classification;

  /** - */
  private Map<String, Object>                        _userAttributes;

  /** - */
  private List<ResourceModuleAndMovableUnitSelector> _selectors;

  /**
   * <p>
   * Creates a new instance of type {@link TargetModuleModuleDefinition}.
   * </p>
   */
  public TargetModuleModuleDefinition() {

    //
    _selectors = new LinkedList<ResourceModuleAndMovableUnitSelector>();
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
  public void addResourceSet(String moduleName, String[] includes, String[] excludes) {

    Assert.isNotNull(moduleName);

    // create new selector
    ResourceModuleAndMovableUnitSelector selector = new ResourceModuleAndMovableUnitSelector();

    // add module selector
    selector.getModuleSelector().add(new ByNameResourceModuleSelector(moduleName));

    // add movable unit selector
    selector.getMovableUnitSelectors().add(createMovableUnitSelector(includes, excludes));

    // add the selector
    _selectors.add(selector);
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

    Assert.isNotNull(fromName);
    Assert.isNotNull(fromVersion);

    // create new selector
    ResourceModuleAndMovableUnitSelector selector = new ResourceModuleAndMovableUnitSelector();

    // add module selector
    selector.getModuleSelector().add(new ByNameAndVersionResourceModuleSelector(fromName, fromVersion));

    // add movable unit selector
    selector.getMovableUnitSelectors().add(createMovableUnitSelector(includes, excludes));

    // add the selector
    _selectors.add(selector);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean matches(IResourceModule resourceModule) {

    //
    if (resourceModule == null) {
      return false;
    }

    //
    for (ResourceModuleAndMovableUnitSelector selector : _selectors) {

      //
      if (selector.matches(resourceModule)) {
        return true;
      }
    }

    //
    return false;
  }

  /**
   * <p>
   * The movable unit.
   * </p>
   * 
   * @param movableUnit
   */
  public boolean matches(IMovableUnit movableUnit) {

    //
    if (movableUnit == null) {
      return false;
    }

    //
    IResourceModule resourceModule = movableUnit.getContainingResourceModule();

    //
    for (ResourceModuleAndMovableUnitSelector selector : _selectors) {

      //
      if (selector.matches(resourceModule) && selector.matches(movableUnit)) {
        return true;
      }
    }

    //
    return false;
  }

  /**
   * <p>
   * </p>
   * 
   * @param includes
   * @param excludes
   * @return
   */
  private PatternBasedMovableUnitSelector createMovableUnitSelector(String[] includes, String[] excludes) {

    // add movable unit selector
    PatternBasedMovableUnitSelector movableUnitSelector = new PatternBasedMovableUnitSelector();

    //
    if (includes != null) {
      for (String include : includes) {
        movableUnitSelector.getIncludes().add(include);
      }
    }

    //
    if (excludes != null) {
      for (String exclude : excludes) {
        movableUnitSelector.getExcludes().add(exclude);
      }
    }

    //
    return movableUnitSelector;
  }
}
