/*******************************************************************************
 * Copyright (c) 2011 Nils Hartmann
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.projecteditor.newwizard;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.ui.projecteditor.provider.internal.ProjectEditorContributionRegistry;
import org.eclipse.jface.wizard.Wizard;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ChooseContentProviderWizard extends Wizard {

  private final IBundleMakerProject               _bundleMakerProject;

  private final ProjectEditorContributionRegistry _newProjectContentProviderWizardRegistry;

  public ChooseContentProviderWizard(IBundleMakerProject bundleMakerProject,
      ProjectEditorContributionRegistry registry) {
    setWindowTitle("Choose Content Provider");
    _newProjectContentProviderWizardRegistry = registry;
    _bundleMakerProject = bundleMakerProject;
    setForcePreviousAndNextButtons(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    addPage(new ChooseContentProviderSelectionPage(_bundleMakerProject, _newProjectContentProviderWizardRegistry));
  }

  @Override
  public boolean performFinish() {
    // Handled by the individual container wizards
    return true;
  }

}