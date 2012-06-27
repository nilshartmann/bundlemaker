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

import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.mvn.content.MvnContentProviderFactory;
import org.bundlemaker.core.mvn.content.xml.MvnArtifactType;
import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.ui.projecteditor.provider.INewProjectContentProviderWizardContribution;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Image;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnContentProviderWizardContribution implements INewProjectContentProviderWizardContribution {

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel(IBundleMakerProject bundleMakerProject) {
    return "Maven repository artifacts";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Image getImage(IBundleMakerProject bundleMakerProject) {
    return MvnBundleMakerImages.MVN_PROJECT_CONTENT_PROVIDER.getImage();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription(IBundleMakerProject bundleMakerProject) {
    return "Add artifacts from a maven repository to your project";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IWizard createWizard(final IBundleMakerProject bundleMakerProject) {

    //
    return new Wizard() {

      //
      private final MvnContentProviderWizardPage _page = new MvnContentProviderWizardPage();

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

        //
        List<MvnArtifactType> artifactTypes = _page.getResult();

        // get description
        IModifiableProjectDescription modifiableProjectDescription = bundleMakerProject
            .getModifiableProjectDescription();

        //
        MvnContentProviderFactory.addNewMvnContentProvider(modifiableProjectDescription,
            artifactTypes.toArray(new MvnArtifactType[0]));

        //
        return true;
      }
    };
  }
}
