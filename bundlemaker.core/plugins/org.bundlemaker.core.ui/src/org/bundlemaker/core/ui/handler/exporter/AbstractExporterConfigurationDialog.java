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
package org.bundlemaker.core.ui.handler.exporter;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractExporterConfigurationDialog extends TitleAreaDialog {

  /**
   * @param parentShell
   */
  public AbstractExporterConfigurationDialog(Shell parentShell) {
    super(parentShell);

    setShellStyle(SWT.CLOSE | SWT.MAX | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL | SWT.RESIZE
        | getDefaultOrientation());
    setHelpAvailable(false);

  }

  @Override
  protected Control createDialogArea(Composite parent) {

    final Composite areaComposite = (Composite) super.createDialogArea(parent);
    Composite dialogComposite = createComposite(areaComposite, 1);
    initializeDialogUnits(dialogComposite);

    createControls(dialogComposite);

    return areaComposite;

  }

  /**
   * Override to add controls to the dialog.
   * 
   * @param dialogComposite
   */
  protected abstract void createControls(Composite dialogComposite);

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.TitleAreaDialog#setErrorMessage(java.lang.String)
   */
  @Override
  public void setErrorMessage(String newErrorMessage) {
    super.setErrorMessage(newErrorMessage);
    setOkButtonEnabled(newErrorMessage == null);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.Dialog#createButton(org.eclipse.swt.widgets.Composite, int, java.lang.String,
   * boolean)
   */
  @Override
  protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
    Button button = super.createButton(parent, id, label, defaultButton);
    if (id == IDialogConstants.OK_ID) {
      button.setEnabled(getErrorMessage() == null);
    }

    return button;
  }

  private void setOkButtonEnabled(boolean enabled) {
    Control button = getButton(IDialogConstants.OK_ID);
    if (button != null) {
      button.setEnabled(enabled);
    }
  }

  /**
   * Creates composite control and sets the default layout data.
   * 
   * @param parent
   *          the parent of the new composite
   * @param numColumns
   *          the number of columns for the new composite
   * @return the newly-created coposite
   */
  protected Composite createComposite(Composite parent, int numColumns) {
    Composite composite = new Composite(parent, SWT.NULL);

    // GridLayout
    GridLayout layout = new GridLayout();
    layout.numColumns = numColumns;
    composite.setLayout(layout);

    // GridData
    GridData data = new GridData();
    data.verticalAlignment = GridData.FILL;
    data.horizontalAlignment = GridData.FILL;
    composite.setLayoutData(data);
    return composite;
  }

  protected Group createGroup(Composite parent, String title) {
    Group group = new Group(parent, SWT.None);
    GridLayout layout = new GridLayout();
    group.setLayout(layout);
    GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
    group.setLayoutData(data);
    group.setText(title);

    return group;

  }

  /**
   * Create a drop-down combo box specific for this application
   * 
   * @param parent
   *          the parent of the new combo box
   * @return the new combo box
   */
  protected Combo createDropDownCombo(Composite parent) {
    Combo combo = new Combo(parent, SWT.DROP_DOWN);
    GridData comboData = new GridData(GridData.FILL_HORIZONTAL);
    comboData.verticalAlignment = GridData.CENTER;
    comboData.grabExcessVerticalSpace = false;
    comboData.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
    combo.setLayoutData(comboData);
    return combo;
  }

  protected Combo createReadOnlyDropDownCombo(Composite parent) {
    Combo combo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
    GridData comboData = new GridData(GridData.FILL_HORIZONTAL);
    comboData.verticalAlignment = GridData.CENTER;
    comboData.grabExcessVerticalSpace = false;
    comboData.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
    combo.setLayoutData(comboData);
    return combo;
  }

  /**
   * Create a text field specific for this application
   * 
   * @param parent
   *          the parent of the new text field
   * @return the new text field
   */
  protected Text createTextField(Composite parent) {
    Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
    GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.verticalAlignment = GridData.CENTER;
    data.grabExcessVerticalSpace = false;
    data.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
    text.setLayoutData(data);
    return text;
  }

}
