/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.tutorial.dependencyviewer;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.ui.artifact.tree.DefaultArtifactLabelProvider;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.IConnectionStyleProvider;
import org.eclipse.zest.core.widgets.ZestStyles;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DependencyViewerLabelProvider extends LabelProvider implements IConnectionStyleProvider {

  private final DefaultArtifactLabelProvider _artifactLabelProvider = new DefaultArtifactLabelProvider();

  private final DependencyViewerModel        _model;

  public DependencyViewerLabelProvider(DependencyViewerModel model) {
    this._model = model;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
   */
  @Override
  public Image getImage(Object element) {

    System.out.println("getImage, element: " + element);
    if (element instanceof IBundleMakerArtifact) {
      return _artifactLabelProvider.getImage(element);
    }

    return super.getImage(element);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
   */
  @Override
  public String getText(Object element) {

    System.out.println("getText, element: " + element);

    if (element instanceof IBundleMakerArtifact) {
      return _artifactLabelProvider.getText(element);
    }

    return "";
  }

  protected boolean isCircularReference(Object rel) {
    IDependency dependency = (IDependency) rel;

    IBundleMakerArtifact from = dependency.getFrom();
    IBundleMakerArtifact to = dependency.getTo();

    IDependency backDependency = to.getDependencyTo(from);
    System.out.println("Dependency from '" + from.getQualifiedName() + "' to '" + to.getQualifiedName()
        + "' circular: " + (backDependency != null));

    return backDependency != null && backDependency.getWeight() != 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.zest.core.viewers.IConnectionStyleProvider#getConnectionStyle(java.lang.Object)
   */
  @Override
  public int getConnectionStyle(Object rel) {
    return ZestStyles.CONNECTIONS_DIRECTED;
    // return isCircularReference(rel) ? ZestStyles.CONNECTIONS_SOLID : ZestStyles.CONNECTIONS_DIRECTED;
  }

  public Color DARK_GRAY = new Color(Display.getDefault(), 49, 79, 79);

  public Color DARK_BLUE = new Color(Display.getDefault(), 0, 0, 255);

  public Color RED       = new Color(Display.getDefault(), 255, 0, 0);

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.zest.core.viewers.IConnectionStyleProvider#getColor(java.lang.Object)
   */
  @Override
  public Color getColor(Object rel) {
    return isCircularReference(rel) ? RED : DARK_GRAY;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.zest.core.viewers.IConnectionStyleProvider#getHighlightColor(java.lang.Object)
   */
  @Override
  public Color getHighlightColor(Object rel) {
    return DARK_BLUE;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.zest.core.viewers.IConnectionStyleProvider#getLineWidth(java.lang.Object)
   */
  @Override
  public int getLineWidth(Object rel) {
    IDependency dependency = (IDependency) rel;
    return _model.getWeight(dependency);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.zest.core.viewers.IConnectionStyleProvider#getTooltip(java.lang.Object)
   */
  @Override
  public IFigure getTooltip(Object element) {
    if (element instanceof IDependency) {
      IDependency dependency = (IDependency) element;

      String tooltipText = dependencyAsString(dependency);
      IDependency reverseDependency = dependency.getTo().getDependencyTo(dependency.getFrom());
      int backCount = reverseDependency.getWeight();
      if (backCount > 0) {
        tooltipText += "\n" + dependencyAsString(reverseDependency);
      }
      Label label = new Label(tooltipText);
      return label;
    }
    return null;
  }

  private String dependencyAsString(IDependency dependency) {
    String string = "From " + dependency.getFrom().getName() + " to " + dependency.getTo().getName() + ": "
        + dependency.getWeight() + " references";

    return string;
  }

}
