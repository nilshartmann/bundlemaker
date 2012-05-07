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
package org.bundlemaker.core.transformations.selectors;

import java.util.ArrayList;
import java.util.List;

import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceModuleAndMovableUnitSelector implements IResourceModuleSelector, IMovableUnitSelector {

  /** the movable unit selectors */
  private List<IResourceModuleSelector> _moduleSelector;

  /** the movable unit selectors */
  private List<IMovableUnitSelector>    _movableUnitSelectors;

  /**
   * <p>
   * Creates a new instance of type {@link ResourceModuleAndMovableUnitSelector}.
   * </p>
   */
  public ResourceModuleAndMovableUnitSelector() {

    //
    _moduleSelector = new ArrayList<IResourceModuleSelector>();

    //
    _movableUnitSelectors = new ArrayList<IMovableUnitSelector>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean matches(IMovableUnit movableUnit) {

    //
    if (movableUnit == null) {
      return false;
    }

    //
    for (IMovableUnitSelector movableUnitSelector : _movableUnitSelectors) {
      if (movableUnitSelector.matches(movableUnit)) {
        return true;
      }
    }

    //
    return false;
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
    for (IResourceModuleSelector moduleSelector : _moduleSelector) {
      if (moduleSelector.matches(resourceModule)) {
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
   * @return
   */
  public final List<IResourceModuleSelector> getModuleSelector() {
    return _moduleSelector;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final List<IMovableUnitSelector> getMovableUnitSelectors() {
    return _movableUnitSelectors;
  }

  /**
   * <p>
   * </p>
   * 
   * @param movableUnit
   * @return
   */
  protected boolean matchesMovableUnit(IMovableUnit movableUnit) {

    //
    for (IMovableUnitSelector movableUnitSelector : _movableUnitSelectors) {

      //
      if (movableUnitSelector.matches(movableUnit)) {
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
   * @param modifiableResourceModule
   * @return
   */
  protected boolean matchesResourceModule(IModifiableResourceModule modifiableResourceModule) {

    //
    for (IResourceModuleSelector resourceModuleSelector : _moduleSelector) {

      //
      if (resourceModuleSelector.matches(modifiableResourceModule)) {
        return true;
      }
    }

    //
    return false;
  }
}
