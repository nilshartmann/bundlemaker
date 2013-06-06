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
package org.bundlemaker.core.ui.view.transformationhistory.labelprovider;

import org.bundlemaker.core.resource.ICreateGroupTransformation;
import org.eclipse.swt.graphics.Image;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class CreateGroupTransformationLabelProvider extends
    AbstractTransformationLabelProvider<ICreateGroupTransformation> {

  public CreateGroupTransformationLabelProvider() {
    super(ICreateGroupTransformation.class);
  }

  @Override
  protected String getTitle(ICreateGroupTransformation transformation) {
    return "Create Group";
  }

  @Override
  protected Image getTitleImage(ICreateGroupTransformation transformation) {
    return null;
  }

  @Override
  protected String getDetails(ICreateGroupTransformation transformation) {

    String where = "";

    if (transformation.getParentGroupPath().isEmpty()) {
      where = " at Modularized System's Root";
    } else {
      where = " in " + transformation.getParentGroupPath();
    }
    return transformation.getGroupPath() + where;
  }

}
