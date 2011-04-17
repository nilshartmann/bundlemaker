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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.internal.debug.ui.jres.BuildJREDescriptor;
import org.eclipse.jdt.internal.debug.ui.jres.JREsComboBlock;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

/**
 * <p>
 * A wizard page to enter the required settings for a BundleMaker project.
 * </p>
 * 
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
@SuppressWarnings("restriction")
public class NewBundleMakerProjectWizardCreationPage extends WizardNewProjectCreationPage {

  /**
   * The block that allows selecting the JRE/Execution Environment
   */
  private JREsComboBlock _jreComboBlock;

  public NewBundleMakerProjectWizardCreationPage() {
    super("NewBundleMakerProjectWizardCreationPage");

    setTitle("Create a Bundlemaker project");
    setDescription("Set project name and JRE.");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createControl(Composite parent) {
    // add controls from default basic wizard page
    super.createControl(parent);
    Composite control = (Composite) getControl();

    // Create the JRE selection box
    createJreGroup(control);

    // Pre-select default JRE
    preselectJre();
  }

  /**
   * <p>
   * Creates the group for selecting the project JRE
   * </p>
   * 
   * @param parent
   */
  private final void createJreGroup(Composite parent) {
    // jre setting group
    Composite jreGroup = new Composite(parent, SWT.NONE);
    jreGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
    jreGroup.setLayout(new GridLayout(2, false));

    _jreComboBlock = new JREsComboBlock(true);
    _jreComboBlock.setDefaultJREDescriptor(new BuildJREDescriptor());
    _jreComboBlock.setTitle("JRE");
    _jreComboBlock.createControl(jreGroup);
    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
    _jreComboBlock.getControl().setLayoutData(gd);
    _jreComboBlock.addPropertyChangeListener(new IPropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent event) {
        setErrorMessageFromStatus(_jreComboBlock.getStatus());
      }
    });

  }

  /**
   * Sets the message from given IStatus as the dialog's error message, if status is not OK. Otherwise sets error
   * message to null.
   * 
   * @param status
   */
  private void setErrorMessageFromStatus(IStatus status) {
    if (status == null || status.isOK()) {
      setErrorMessage(null);
    } else {
      setErrorMessage(status.getMessage());
    }
  }

  /**
   * Initializes the JRE selection
   */
  protected void preselectJre() {
    _jreComboBlock.setPath(JavaRuntime.newDefaultJREContainerPath());
  }

  public String getSelectedJreId() {
    return _jreComboBlock.getPath().toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.dialogs.WizardNewProjectCreationPage#validatePage()
   */
  @Override
  protected boolean validatePage() {
    if (!super.validatePage()) {
      return false;
    }

    // Validate JRE selection
    IStatus status = _jreComboBlock.getStatus();
    setErrorMessageFromStatus(status);

    return status.isOK();
  }

}
