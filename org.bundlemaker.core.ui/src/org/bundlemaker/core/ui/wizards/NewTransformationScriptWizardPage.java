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

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class NewTransformationScriptWizardPage extends WizardNewFileCreationPage {

  /**
   * @param pageName
   * @param selection
   */
  public NewTransformationScriptWizardPage(IStructuredSelection selection) {
    super("NewTransformationScriptWizardPage", selection);

    setTitle("New transformation script");
    setDescription("Create a new transformation script");
    setFileExtension("bmt");
  }

  @Override
  protected InputStream getInitialContents() {
    try {
      return new ByteArrayInputStream("// Write down your transformation here\n\n".getBytes());
    } catch (Exception e) {
      return null; // ignore and create empty comments
    }
  }
}
