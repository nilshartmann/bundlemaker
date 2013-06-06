/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.event.internal;

import org.bundlemaker.core.parser.IParserAwareBundleMakerProject;
import org.bundlemaker.core.resource.IModuleAwareBundleMakerProject;
import org.bundlemaker.core.ui.event.IBundleMakerProjectOpenedEvent;
import org.eclipse.core.runtime.Assert;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BundleMakerProjectOpenedEvent implements IBundleMakerProjectOpenedEvent {

  private final IModuleAwareBundleMakerProject _bundleMakerProject;

  /**
   * @param bundleMakerProject
   */
  public BundleMakerProjectOpenedEvent(IModuleAwareBundleMakerProject bundleMakerProject) {
    Assert.isNotNull(bundleMakerProject);

    _bundleMakerProject = bundleMakerProject;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.event.IBundleMakerProjectOpenedEvent#getBundleMakerProject()
   */
  @Override
  public IModuleAwareBundleMakerProject getBundleMakerProject() {
    return this._bundleMakerProject;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "BundleMakerProjectOpenedEvent [_bundleMakerProject=" + _bundleMakerProject + "]";
  }
}
