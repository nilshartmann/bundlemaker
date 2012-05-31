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
package org.bundlemaker.core.ui.handler;

import java.util.Set;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Version;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class EditModuleDialog extends TitleAreaDialog {

  private final ModifyListener _validationModifyListener = new ValidationModifyListener();

  private boolean              _editExistingModule       = false;

  private String               _moduleName;

  private String               _moduleVersion;

  private Text                 _nameTextField;

  private Text                 _versionTextField;

  private final Set<String>    _existingArtifactNames;

  public EditModuleDialog(Shell shell, Set<String> existingArtifactNames, boolean editExistingModule,
      String moduleName, String moduleVersion) {
    super(shell);

    this._existingArtifactNames = existingArtifactNames;
    this._editExistingModule = editExistingModule;
    this._moduleName = moduleName;
    this._moduleVersion = moduleVersion;

    configureDialog();
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);

    String title = (_editExistingModule ? "Rename Module" : "New Module");

    newShell.setText(title);
  }

  private void configureDialog() {
    setShellStyle(SWT.CLOSE | SWT.MAX | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL | SWT.RESIZE
        | getDefaultOrientation());
    setHelpAvailable(false);
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    String title = (_editExistingModule ? "Rename Module" : "New Module");
    setTitle(title);
    setMessage("Please enter name and version of your module");

    final Composite areaComposite = (Composite) super.createDialogArea(parent);

    Composite dialogComposite = new Composite(areaComposite, SWT.NONE);
    dialogComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

    dialogComposite.setLayout(new GridLayout(2, false));

    Label label = new Label(dialogComposite, SWT.NONE);
    label.setText("Name:");

    _nameTextField = new Text(dialogComposite, SWT.BORDER);
    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.grabExcessHorizontalSpace = true;
    _nameTextField.setLayoutData(gd);
    _nameTextField.setText(_moduleName);

    label = new Label(dialogComposite, SWT.NONE);
    label.setText("Version:");
    _versionTextField = new Text(dialogComposite, SWT.BORDER);

    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.grabExcessHorizontalSpace = true;
    _versionTextField.setLayoutData(gd);
    _versionTextField.setText(_moduleVersion);

    if (!_editExistingModule) {
      _nameTextField.selectAll();
    }

    _nameTextField.addModifyListener(_validationModifyListener);
    _versionTextField.addModifyListener(_validationModifyListener);

    Dialog.applyDialogFont(areaComposite);
    return areaComposite;
  }

  protected void validateInput() {
    String errorMessage = null;

    errorMessage = validateModuleName();
    if (errorMessage == null) {
      errorMessage = validateModuleVersion();
    }

    if (errorMessage == null) {
      // make sure, artifact name (including version) is not in use yet
      String artifactName = _nameTextField.getText() + "_" + _versionTextField.getText();

      if (_existingArtifactNames.contains(artifactName)) {
        errorMessage = "Enter a unique module name and version";
      }

    }

    setErrorMessage(errorMessage);

  }

  protected String validateModuleName() {
    String moduleName = _nameTextField.getText();
    if (moduleName == null || moduleName.trim().isEmpty()) {
      return "Enter a valid module name";
    }

    return null;
  }

  protected String validateModuleVersion() {
    String moduleVersion = _versionTextField.getText();
    if (moduleVersion == null || moduleVersion.trim().isEmpty()) {
      return "Enter a version, e.g. 1.0.0";
    }

    try {
      Version.parseVersion(moduleVersion);
    } catch (Exception ex) {
      return "Enter a valid OSGi version";
    }

    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.Dialog#okPressed()
   */
  @Override
  protected void okPressed() {

    _moduleName = _nameTextField.getText();
    _moduleVersion = _versionTextField.getText();

    super.okPressed();
  }

  /**
   * @return the moduleName
   */
  public String getModuleName() {
    return _moduleName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.TitleAreaDialog#setErrorMessage(java.lang.String)
   */
  @Override
  public void setErrorMessage(String newErrorMessage) {
    super.setErrorMessage(newErrorMessage);

    Control button = getButton(IDialogConstants.OK_ID);
    if (button != null) {
      button.setEnabled(newErrorMessage == null);
    }
  }

  /**
   * @return the moduleVersion
   */
  public String getModuleVersion() {
    return _moduleVersion;
  }

  class ValidationModifyListener implements ModifyListener {

    @Override
    public void modifyText(ModifyEvent e) {
      validateInput();
    }

  }

}
