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

import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.transformation.ITransformation;
import org.bundlemaker.core.transformations.selectors.IMovableUnitSelector;
import org.bundlemaker.core.transformations.selectors.IResourceModuleSelector;
import org.bundlemaker.core.transformations.selectors.PatternBasedMovableUnitSelector;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class RemoveMovableUnitTransformation implements ITransformation {

  /** the movable unit selectors */
  private List<IMovableUnitSelector> _movableUnitSelectors;

  /**
   * <p>
   * Creates a new instance of type {@link RemoveMovableUnitTransformation}.
   * </p>
   */
  public RemoveMovableUnitTransformation() {

    //
    _movableUnitSelectors = new ArrayList<IMovableUnitSelector>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor monitor) {

    //
    SubMonitor subMonitor = SubMonitor.convert(monitor, _movableUnitSelectors.size());

    //
    for (IMovableUnitSelector movableUnitSelector : _movableUnitSelectors) {

      //
      for (IModifiableResourceModule modifiableResourceModule : modularizedSystem.getModifiableResourceModules()) {

        for (IMovableUnit movableUnit : modifiableResourceModule.getMovableUnits()) {
          if (movableUnitSelector.matches(movableUnit)) {
            modifiableResourceModule.getModifiableSelfResourceContainer().removeMovableUnit(movableUnit);
          }
        }
      }

      // increment the subMonitor
      subMonitor.worked(1);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param includes
   * @param excludes
   */
  public void addPatternBasedMovableUnitSelector(String[] includes, String[] excludes) {
    addPatternBasedMovableUnitSelector(null, includes, excludes);
  }

  /**
   * <p>
   * </p>
   * 
   * @param fromModuleIdentifier
   * @param includes
   * @param excludes
   */
  public void addPatternBasedMovableUnitSelector(IResourceModuleSelector resourceModuleSelector, String[] includes,
      String[] excludes) {
    // TODO
    // //
    // PatternBasedMovableUnitSelector patternBasedMovableUnitSelector = new PatternBasedMovableUnitSelector();
    //
    // //
    // if (resourceModuleSelector != null) {
    // patternBasedMovableUnitSelector.setResourceModuleSelector(resourceModuleSelector);
    // }
    //
    // //
    // if (includes != null) {
    // for (String include : includes) {
    // patternBasedMovableUnitSelector.getIncludes().add(include);
    // }
    // }
    //
    // //
    // if (excludes != null) {
    // for (String exclude : excludes) {
    // patternBasedMovableUnitSelector.getExcludes().add(exclude);
    // }
    // }
    //
    // //
    // _movableUnitSelectors.add(patternBasedMovableUnitSelector);
  }
}
