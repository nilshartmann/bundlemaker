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
package org.bundlemaker.core.ui.view.transformationhistory.view;

import java.util.List;

import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.transformation.ITransformation;
import org.bundlemaker.core.ui.view.transformationhistory.ITransformationLabelProvider;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class HistoryViewDetailsColumnLabelProvider extends AbstractHistoryViewColumnLabelProvider {

  /**
   * @param transformationLabelProviders
   */
  public HistoryViewDetailsColumnLabelProvider(List<ITransformationLabelProvider> transformationLabelProviders) {
    super(transformationLabelProviders);
  }

  @Override
  protected String getTextForArtifact(IRootArtifact element) {

    int transformationCount = element.getModularizedSystem().getTransformations().size();

    if (transformationCount == 0) {
      return "No Transformations";
    } else if (transformationCount == 1) {
      return "One Transformation";
    } else {
      return transformationCount + " Transformations";
    }

  }

  @Override
  protected String getTextForArtifact(ITransformation element) {
    return getTransformationLabelProvider(element).getDetailsText(element);
  }

}
