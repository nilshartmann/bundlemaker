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
import org.bundlemaker.core.transformation.ITransformation;
import org.bundlemaker.core.ui.transformations.history.ITransformationLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class AbstractHistoryViewColumnLabelProvider extends ColumnLabelProvider {

  private final FallbackTransformationLabelProvider _fallbackTransformationLabelProvider = new FallbackTransformationLabelProvider();

  private final List<ITransformationLabelProvider>  _transformationLabelProviders;

  public AbstractHistoryViewColumnLabelProvider(List<ITransformationLabelProvider> transformationLabelProviders) {
    _transformationLabelProviders = transformationLabelProviders;
  }

  protected ITransformationLabelProvider getTransformationLabelProvider(ITransformation transformation) {

    for (ITransformationLabelProvider labelProvider : _transformationLabelProviders) {
      if (labelProvider.canHandle(transformation)) {
        return labelProvider;
      }
    }

    // non found: return fallback
    return _fallbackTransformationLabelProvider;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
   */
  @Override
  public Image getImage(Object element) {

    if (element instanceof IRootArtifact) {

      return getImageForArtifact((IRootArtifact) element);
      // return ArtifactImages.ROOT_ARTIFACT_ICON.getImage();

    } else if (element instanceof ITransformation) {
      return getImageForTransformation((ITransformation) element);
    }

    return super.getImage(element);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
   */
  @Override
  public String getText(Object element) {
    if (element instanceof IRootArtifact) {

      return getTextForArtifact((IRootArtifact) element);
      // return ArtifactImages.ROOT_ARTIFACT_ICON.getImage();

    } else if (element instanceof ITransformation) {
      return getTextForArtifact((ITransformation) element);
    }
    return super.getText(element);
  }

  /**
   * @param element
   * @return
   */
  protected String getTextForArtifact(ITransformation element) {
    return null;
  }

  /**
   * @param element
   * @return
   */
  protected String getTextForArtifact(IRootArtifact element) {
    return null;
  }

  /**
   * @param element
   * @return
   */
  protected Image getImageForTransformation(ITransformation element) {
    return null;
  }

  /**
   * @param element
   * @return
   */
  protected Image getImageForArtifact(IRootArtifact element) {
    return null;
  }

  /**
   * Used as fallback {@link ITransformationLabelProvider} if no provider for a given {@link ITransformation} has been
   * registered
   * 
   * @author Nils Hartmann (nils@nilshartmann.net)
   * 
   */
  private static class FallbackTransformationLabelProvider implements ITransformationLabelProvider {

    @Override
    public boolean canHandle(ITransformation transformation) {
      return true;
    }

    @Override
    public String getTitleText(ITransformation transformation) {
      return transformation.getClass().getSimpleName();
    }

    @Override
    public Image getImage(ITransformation transformation) {
      return null;
    }

    @Override
    public String getDetailsText(ITransformation transformation) {
      return null;
    }

  }

}
