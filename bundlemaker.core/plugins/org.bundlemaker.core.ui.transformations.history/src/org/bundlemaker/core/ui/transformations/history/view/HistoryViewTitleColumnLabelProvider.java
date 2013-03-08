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
package org.bundlemaker.core.ui.transformations.history.view;

import java.util.List;

import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.transformation.ITransformation;
import org.bundlemaker.core.ui.artifact.ArtifactImages;
import org.bundlemaker.core.ui.transformations.history.ITransformationLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for the left column in the tree
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class HistoryViewTitleColumnLabelProvider extends AbstractHistoryViewColumnLabelProvider {

  /**
   * @param transformationLabelProviders
   */
  public HistoryViewTitleColumnLabelProvider(List<ITransformationLabelProvider> transformationLabelProviders) {
    super(transformationLabelProviders);
  }

  @Override
  protected String getTextForArtifact(ITransformation element) {

    ITransformationLabelProvider labelProvider = getTransformationLabelProvider(element);

    return labelProvider.getTitleText(element);
  }

  @Override
  protected String getTextForArtifact(IRootArtifact element) {
    return element.getName();
  }

  @Override
  protected Image getImageForTransformation(ITransformation element) {
    ITransformationLabelProvider labelProvider = getTransformationLabelProvider(element);

    return labelProvider.getImage(element);
  }

  @Override
  protected Image getImageForArtifact(IRootArtifact element) {
    return ArtifactImages.ROOT_ARTIFACT_ICON.getImage();
  }

}
