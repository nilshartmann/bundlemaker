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

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ChooseContentProviderDialog extends TitleAreaDialog {

  public ChooseContentProviderDialog(Shell parentShell) {
    super(parentShell);

    configureDialog();
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);

    newShell.setText("Choose Content Provider");
  }

  private void configureDialog() {
    setShellStyle(SWT.CLOSE | SWT.MAX | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL | SWT.RESIZE
        | getDefaultOrientation());
    setHelpAvailable(false);

  }

  @Override
  protected Control createDialogArea(Composite parent) {
    setTitle("Choose a content provider");

    final Composite areaComposite = (Composite) super.createDialogArea(parent);

    Composite dialogComposite = new Composite(areaComposite, SWT.NONE);
    dialogComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
    // GridLayout gridLayout = FormLayoutUtils.createFormGridLayout(false, 2);
    // dialogComposite.setLayout(gridLayout);
    //
    // _binariesContentList = addContentList(dialogComposite, "Binaries");
    // _sourcesContentList = addContentList(dialogComposite, "Sources");
    //
    // prepopulateForm();
    // refresh();

    Dialog.applyDialogFont(areaComposite);

    return areaComposite;

  }

}
