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
package org.bundlemaker.core.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class NewTransformationScriptWizard extends BasicNewResourceWizard implements IWizard {

  private NewTransformationScriptWizardPage _page;

  public NewTransformationScriptWizard() {
    setWindowTitle("New transformation script");
  }

  @Override
  public void addPages() {
    _page = new NewTransformationScriptWizardPage(getSelection());
    addPage(_page);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    IFile file = _page.createNewFile();
    if (file == null) {
      return false;
    }

    try {
      IDE.openEditor(getWorkbench().getActiveWorkbenchWindow().getActivePage(), file);
    } catch (PartInitException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return true;
  }
}
