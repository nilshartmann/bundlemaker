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
package org.bundlemaker.core.ui.view.transformationhistory.internal;

import org.bundlemaker.core.modules.transformation.ICreateGroupTransformation;
import org.bundlemaker.core.modules.transformation.ITransformation;
import org.bundlemaker.core.ui.view.transformationhistory.ITransformationLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Used as fallback {@link ITransformationLabelProvider} if no provider for a given {@link ITransformation} has been
 * registered
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DefaultTransformationLabelProvider implements ITransformationLabelProvider {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.transformations.history.ITransformationLabelProvider#canHandle(org.bundlemaker.core.
   * transformation.ITransformation)
   */
  @Override
  public boolean canHandle(ITransformation transformation) {
    return (transformation instanceof ICreateGroupTransformation);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.transformations.history.ITransformationLabelProvider#getText(org.bundlemaker.core.
   * transformation.ITransformation)
   */
  @Override
  public String getTitleText(ITransformation transformation) {
    return transformation.getClass().getSimpleName();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.transformations.history.ITransformationLabelProvider#getImage(org.bundlemaker.core.
   * transformation.ITransformation)
   */
  @Override
  public Image getImage(ITransformation transformation) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.transformations.history.ITransformationLabelProvider#getDetailsText(org.bundlemaker.core
   * .transformation.ITransformation)
   */
  @Override
  public String getDetailsText(ITransformation transformation) {
    return null;
  }

}
