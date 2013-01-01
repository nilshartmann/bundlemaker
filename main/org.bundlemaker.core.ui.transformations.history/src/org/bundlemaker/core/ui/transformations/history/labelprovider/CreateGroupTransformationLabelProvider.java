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

import org.bundlemaker.core.transformation.CreateGroupTransformation;
import org.eclipse.swt.graphics.Image;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class CreateGroupTransformationLabelProvider extends
    AbstractTransformationLabelProvider<CreateGroupTransformation> {

  public CreateGroupTransformationLabelProvider() {
    super(CreateGroupTransformation.class);
  }

  @Override
  protected String getTitle(CreateGroupTransformation transformation) {
    return "Create Group";
  }

  @Override
  protected Image getTitleImage(CreateGroupTransformation transformation) {
    return null;
  }

  @Override
  protected String getDetails(CreateGroupTransformation transformation) {

    String where = "";

    if (transformation.getParentGroupPath().isEmpty()) {
      where = " at Modularized System's Root";
    } else {
      where = " in " + transformation.getParentGroupPath();
    }
    return transformation.getGroupPath() + where;
  }

}
