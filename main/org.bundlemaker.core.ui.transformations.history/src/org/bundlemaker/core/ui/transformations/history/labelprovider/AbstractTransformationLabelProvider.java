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
package org.bundlemaker.core.ui.transformations.history.labelprovider;

import org.bundlemaker.core.transformation.ITransformation;
import org.bundlemaker.core.ui.transformations.history.ITransformationLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractTransformationLabelProvider<T extends ITransformation> implements
    ITransformationLabelProvider {

  private final Class<T> _supportedType;

  protected AbstractTransformationLabelProvider(Class<T> supportedType) {
    _supportedType = supportedType;
  }

  @Override
  public boolean canHandle(ITransformation transformation) {

    return _supportedType.isInstance(transformation);
  }

  @Override
  public String getTitleText(ITransformation transformation) {
    return getTitle(_supportedType.cast(transformation));
  }

  @Override
  public String getDetailsText(ITransformation transformation) {
    return getDetails(_supportedType.cast(transformation));
  }

  @Override
  public Image getImage(ITransformation transformation) {
    return getTitleImage(_supportedType.cast(transformation));
  }

  protected abstract String getTitle(T transformation);

  protected abstract Image getTitleImage(T transformation);

  protected abstract String getDetails(T transformation);

}
