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

import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class PatternBasedMovableUnitSelector extends AbstractPatternBasedSelector implements IMovableUnitSelector {

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public enum Origin {

    /** - */
    SOURCES,

    /** - */
    BINARIES,

    /** - */
    SOURCES_AND_BINARIES,

    /** - */
    TYPES
  }

  /** - */
  private Origin _origin = Origin.SOURCES_AND_BINARIES;

  /**
   * <p>
   * </p>
   * 
   * @param origin
   */
  public void setOrigin(Origin origin) {

    //
    Assert.isNotNull(origin);

    //
    _origin = origin;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Origin getOrigin() {
    return _origin;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean matches(IMovableUnit movableUnit) {

    //
    if (movableUnit.hasAssociatedBinaryResources() && Origin.BINARIES.equals(_origin)
        || Origin.SOURCES_AND_BINARIES.equals(_origin)) {
      for (IResource resource : movableUnit.getAssociatedBinaryResources()) {
        if (isIncluded(resource.getPath())) {
          return true;
        }
      }
    }

    //
    if (movableUnit.hasAssociatedSourceResource()
        && (Origin.SOURCES.equals(_origin) || Origin.SOURCES_AND_BINARIES.equals(_origin))) {
      if (isIncluded(movableUnit.getAssociatedSourceResource().getPath())) {
        return true;
      }
    }

    //
    return false;
  }
}
