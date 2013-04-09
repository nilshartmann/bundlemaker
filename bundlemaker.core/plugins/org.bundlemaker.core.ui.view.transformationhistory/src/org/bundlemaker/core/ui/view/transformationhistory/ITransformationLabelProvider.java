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
package org.bundlemaker.core.ui.view.transformationhistory;

import org.bundlemaker.core.modules.transformation.ITransformation;
import org.eclipse.swt.graphics.Image;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public interface ITransformationLabelProvider {

  public boolean canHandle(ITransformation transformation);

  /**
   * Return the text that is displayed in the title column (left) inside the History View Tree
   * 
   * @param transformation
   * @return
   */
  public String getTitleText(ITransformation transformation);

  public String getDetailsText(ITransformation transformation);

  public Image getImage(ITransformation transformation);

}
