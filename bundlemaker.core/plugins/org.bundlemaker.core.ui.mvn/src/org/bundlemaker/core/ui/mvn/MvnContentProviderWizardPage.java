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
package org.bundlemaker.core.ui.mvn;

import org.bundlemaker.core.mvn.content.MvnArtifactType;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class MvnContentProviderWizardPage extends WizardPage {

  /** - */
  private CompositeEditMvnArtifacts _artifactListComposite;

  /**
   * <p>
   * Creates a new instance of type {@link MvnContentProviderWizardPage}.
   * </p>
   */
  public MvnContentProviderWizardPage() {
    super("ResourcesProjectContentProviderWizardPage");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createControl(Composite parent) {

    setMessage("Select resource from workspace or external location ");
    setTitle("Select resources");
    setPageComplete(false);

    _artifactListComposite = new CompositeEditMvnArtifacts(parent, SWT.NONE) {

      @Override
      protected void onRefreshEnablement() {
        setPageComplete(getArtifactTypes().size() > 0);
      }
    };

    setControl(_artifactListComposite);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public java.util.List<MvnArtifactType> getResult() {
    return _artifactListComposite.getArtifactTypes();
  }
}
