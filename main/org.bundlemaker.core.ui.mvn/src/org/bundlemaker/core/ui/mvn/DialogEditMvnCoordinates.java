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
package org.bundlemaker.core.ui.mvn;

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

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DialogEditMvnCoordinates extends TitleAreaDialog {

  private static final String  ENTER_MAVEN_COORDINATES   = "Enter Maven Coordinates";

  private final ModifyListener _validationModifyListener = new ValidationModifyListener();

  private String               _groupId;

  private String               _artifactId;

  private String               _version;

  private Text                 _groupIdTextField;

  private Text                 _artifactIdTextField;

  private Text                 _versionTextField;

  /**
   * <p>
   * Creates a new instance of type {@link DialogEditMvnCoordinates}.
   * </p>
   * 
   * @param shell
   */
  public DialogEditMvnCoordinates(Shell shell) {
    this(shell, "", "", "");
  }

  /**
   * <p>
   * Creates a new instance of type {@link DialogEditMvnCoordinates}.
   * </p>
   * 
   * @param shell
   * @param groupId
   * @param artifactId
   * @param version
   */
  public DialogEditMvnCoordinates(Shell shell, String groupId, String artifactId, String version) {
    super(shell);

    this._groupId = groupId;
    this._artifactId = artifactId;
    this._version = version;

    // set style
    setShellStyle(SWT.CLOSE | SWT.MAX | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL | SWT.RESIZE
        | getDefaultOrientation());

    // set help
    setHelpAvailable(false);
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);

    // maven coordinates
    newShell.setText(ENTER_MAVEN_COORDINATES);
  }

  @Override
  protected Control createDialogArea(Composite parent) {

    setTitle(ENTER_MAVEN_COORDINATES);
    setMessage("Please enter the coordinates of the maven artifact");

    final Composite areaComposite = (Composite) super.createDialogArea(parent);

    Composite dialogComposite = new Composite(areaComposite, SWT.NONE);
    dialogComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

    dialogComposite.setLayout(new GridLayout(2, false));

    // group id
    Label label = new Label(dialogComposite, SWT.NONE);
    label.setText("GroupId:");
    _groupIdTextField = new Text(dialogComposite, SWT.BORDER);
    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.grabExcessHorizontalSpace = true;
    _groupIdTextField.setLayoutData(gd);
    _groupIdTextField.setText(_groupId);

    // artifact id
    label = new Label(dialogComposite, SWT.NONE);
    label.setText("ArtifactId:");
    _artifactIdTextField = new Text(dialogComposite, SWT.BORDER);
    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.grabExcessHorizontalSpace = true;
    _artifactIdTextField.setLayoutData(gd);
    _artifactIdTextField.setText(_artifactId);

    // version
    label = new Label(dialogComposite, SWT.NONE);
    label.setText("Version:");
    _versionTextField = new Text(dialogComposite, SWT.BORDER);
    gd = new GridData(GridData.FILL_HORIZONTAL);
    gd.grabExcessHorizontalSpace = true;
    _versionTextField.setLayoutData(gd);
    _versionTextField.setText(_version);

    //
    _groupIdTextField.addModifyListener(_validationModifyListener);
    _artifactIdTextField.addModifyListener(_validationModifyListener);
    _versionTextField.addModifyListener(_validationModifyListener);

    //
    Dialog.applyDialogFont(areaComposite);

    //
    return areaComposite;
  }

  /**
   * <p>
   * </p>
   */
  protected void validateInput() {
    // String errorMessage = null;
    //
    // errorMessage = validateModuleName();
    // if (errorMessage == null) {
    // errorMessage = validateModuleVersion();
    // }
    //
    // if (errorMessage == null) {
    // // make sure, artifact name (including version) is not in use yet
    // String artifactName = _nameTextField.getText() + "_" + _versionTextField.getText();
    //
    // if (_existingArtifactNames.contains(artifactName)) {
    // errorMessage = "Enter a unique module name and version";
    // }
    //
    // }
    //
    // setErrorMessage(errorMessage);
  }

  // protected String validateModuleName() {
  // String moduleName = _nameTextField.getText();
  // if (moduleName == null || moduleName.trim().isEmpty()) {
  // return "Enter a valid module name";
  // }
  //
  // return null;
  // }
  //
  // protected String validateModuleVersion() {
  // String moduleVersion = _versionTextField.getText();
  // if (moduleVersion == null || moduleVersion.trim().isEmpty()) {
  // return "Enter a version, e.g. 1.0.0";
  // }
  //
  // try {
  // Version.parseVersion(moduleVersion);
  // } catch (Exception ex) {
  // return "Enter a valid OSGi version";
  // }
  //
  // return null;
  // }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.Dialog#okPressed()
   */
  @Override
  protected void okPressed() {

    _groupId = _groupIdTextField.getText();
    _artifactId = _artifactIdTextField.getText();
    _version = _versionTextField.getText();

    super.okPressed();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getGroupId() {
    return _groupId;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getArtifactId() {
    return _artifactId;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getVersion() {
    return _version;
  }

  /**
   * {@inheritDoc}
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
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  class ValidationModifyListener implements ModifyListener {
    @Override
    public void modifyText(ModifyEvent e) {
      validateInput();
    }
  }

}
