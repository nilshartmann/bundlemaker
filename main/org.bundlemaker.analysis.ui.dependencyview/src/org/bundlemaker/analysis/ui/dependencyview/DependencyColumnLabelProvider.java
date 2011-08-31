/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.analysis.ui.dependencyview;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.ui.DefaultArtifactLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class DependencyColumnLabelProvider extends ColumnLabelProvider {
  private static final DefaultArtifactLabelProvider _defaultArtifactLabelProvider = new DefaultArtifactLabelProvider();

  private final ArtifactPathLabelGenerator          _labelGenerator;

  /**
   * @param labelGenerator
   */
  public DependencyColumnLabelProvider(ArtifactPathLabelGenerator labelGenerator) {
    super();
    _labelGenerator = labelGenerator;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
   */
  @Override
  public Image getImage(Object element) {
    if (element instanceof IDependency) {
      element = getArtifactElement((IDependency) element);
    }
    return _defaultArtifactLabelProvider.getImage(element);
  }

  /**
   * Sets the 'base artifact' that is the IArtifact from the IDependency (either from- or to-side)
   * 
   * @param baseArtifact
   *          the baseArtifact to set
   */
  public void setBaseArtifact(IArtifact baseArtifact) {
    getLabelGenerator().setBaseArtifact(baseArtifact);
  }

  /**
   * @return
   */
  protected ArtifactPathLabelGenerator getLabelGenerator() {
    return _labelGenerator;
  }

  /**
   * @param element
   * @return
   */
  protected abstract IArtifact getArtifactElement(IDependency element);

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
   */
  @Override
  public String getText(Object element) {
    if (element instanceof IDependency) {
      IArtifact artifact = getArtifactElement((IDependency) element);
      return getArtifactLabel(artifact);
    }
    return _defaultArtifactLabelProvider.getText(element);
  }

  protected String getArtifactLabel(IArtifact artifact) {
    return _labelGenerator.getLabel(artifact);
  }

}
