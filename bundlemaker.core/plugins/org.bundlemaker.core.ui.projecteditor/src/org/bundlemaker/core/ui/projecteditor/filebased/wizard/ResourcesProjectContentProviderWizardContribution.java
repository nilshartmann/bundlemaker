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
package org.bundlemaker.core.ui.projecteditor.filebased.wizard;

import java.util.List;

import org.bundlemaker.core.project.IModifiableProjectDescription;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.bundlemaker.core.ui.BundleMakerImages;
import org.bundlemaker.core.ui.projecteditor.filebased.FileBasedContentCreator;
import org.bundlemaker.core.ui.projecteditor.provider.INewProjectContentProviderWizardContribution;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Image;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ResourcesProjectContentProviderWizardContribution implements INewProjectContentProviderWizardContribution {

  private final FileBasedContentCreator _fileBasedContentCreator = new FileBasedContentCreator();

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.projecteditor.provider.NewProjectContentProviderWizardContribution#getLabel(org.bundlemaker
   * .core.IBundleMakerProject)
   */
  @Override
  public String getLabel(IProjectDescriptionAwareBundleMakerProject bundleMakerProject) {
    return "Files and directories";
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.projecteditor.provider.NewProjectContentProviderWizardContribution#getImage(org.bundlemaker
   * .core.IBundleMakerProject)
   */
  @Override
  public Image getImage(IProjectDescriptionAwareBundleMakerProject bundleMakerProject) {
    return BundleMakerImages.FILEBASED_PROJECT_CONTENT_PROVIDER.getImage();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.projecteditor.provider.NewProjectContentProviderWizardContribution#getDescription(org.
   * bundlemaker.core.IBundleMakerProject)
   */
  @Override
  public String getDescription(IProjectDescriptionAwareBundleMakerProject bundleMakerProject) {
    return "Add files and directories from workspace or external location to your project";
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.projecteditor.provider.NewProjectContentProviderWizardContribution#createWizard(org.bundlemaker
   * .core.IBundleMakerProject)
   */
  @Override
  public IWizard createWizard(final IProjectDescriptionAwareBundleMakerProject bundleMakerProject) {
    return new Wizard() {
      private final ResourcesProjectContentProviderWizardPage _page = new ResourcesProjectContentProviderWizardPage();

      /*
       * (non-Javadoc)
       * 
       * @see org.eclipse.jface.wizard.Wizard#addPages()
       */
      @Override
      public void addPages() {
        addPage(_page);
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.eclipse.jface.wizard.Wizard#performFinish()
       */
      @Override
      public boolean performFinish() {
        List<String> resources = _page.getChosenResources();

        // get description
        IModifiableProjectDescription modifiableProjectDescription = bundleMakerProject
            .getModifiableProjectDescription();

        _fileBasedContentCreator.addFiles(modifiableProjectDescription, resources.toArray(new String[0]));

        return true;
      }

    };
  }
}
