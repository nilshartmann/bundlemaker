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
package org.bundlemaker.core.ui.editor.provider;

import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.eclipse.jface.wizard.Wizard;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ChooseContentProviderWizard extends Wizard {

  private final IModifiableProjectDescription _projectDescription;

  public ChooseContentProviderWizard(IModifiableProjectDescription projectDescription) {
    setWindowTitle("Choose Content Provider");
    _projectDescription = projectDescription;
    setForcePreviousAndNextButtons(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    addPage(new ProviderSelectionPage(_projectDescription));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.Wizard#addPages() rride public void addPages() { addPage(new
   * AddBundleContainerSelectionPage(fTarget)); }
   * 
   * /* (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.Wizard#perform
   */
  @Override
  public boolean performFinish() {
    // Handled by the individual container wizards
    return true;
  }

}