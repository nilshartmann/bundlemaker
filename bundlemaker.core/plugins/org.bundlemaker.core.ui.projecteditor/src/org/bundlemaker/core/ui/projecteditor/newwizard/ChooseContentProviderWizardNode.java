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
package org.bundlemaker.core.ui.projecteditor.newwizard;

import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.bundlemaker.core.ui.projecteditor.provider.INewProjectContentProviderWizardContribution;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ChooseContentProviderWizardNode implements IWizardNode {

  private final IProjectDescriptionAwareBundleMakerProject                         _bundleMakerProject;

  private final INewProjectContentProviderWizardContribution _contribution;

  private IWizard                                           _wizard;

  /**
   * @param bundleMakerProject
   * @param contribution
   */
  public ChooseContentProviderWizardNode(IProjectDescriptionAwareBundleMakerProject bundleMakerProject,
      INewProjectContentProviderWizardContribution contribution) {
    super();
    _bundleMakerProject = bundleMakerProject;
    _contribution = contribution;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.IWizardNode#dispose()
   */
  @Override
  public void dispose() {
    if (_wizard != null) {
      _wizard.dispose();
      _wizard = null;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.IWizardNode#getExtent()
   */
  @Override
  public Point getExtent() {
    return new Point(-1, -1);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.IWizardNode#getWizard()
   */
  @Override
  public IWizard getWizard() {
    if (_wizard == null) {
      _wizard = createWizard();
    }
    return _wizard;
  }

  /**
   * @return
   */
  protected IWizard createWizard() {
    IWizard wizard = _contribution.createWizard(_bundleMakerProject);

    return wizard;
  }

  public String getLabel() {
    return _contribution.getLabel(_bundleMakerProject);
  }

  public Image getImage() {
    return _contribution.getImage(_bundleMakerProject);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.IWizardNode#isContentCreated()
   */
  @Override
  public boolean isContentCreated() {
    return _wizard != null;
  }

  /**
   * @return
   */
  public String getDescription() {
    return _contribution.getDescription(_bundleMakerProject);
  }

}
