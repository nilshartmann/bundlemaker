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

import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.projectdescription.file.FileBasedContentProviderFactory;
import org.bundlemaker.core.ui.BundleMakerImages;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class FileBasedProjectContentProviderNode extends AbstractProjectContentProviderNode {

  /**
   * @param name
   * @param description
   * @param image
   */
  public FileBasedProjectContentProviderNode(IModifiableProjectDescription pd) {
    super(pd, "File based content", "Add content from archives and directories",
        BundleMakerImages.FILEBASED_PROJECT_CONTENT_PROVIDER.getImage());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.editor.provider.AbstractProjectContentProviderNode#createWizard()
   */
  @Override
  public IWizard createWizard() {
    return new Wizard() {

      private EditFileBasedContentProviderPage _page = new EditFileBasedContentProviderPage();

      /*
       * (non-Javadoc)
       * 
       * @see org.eclipse.jface.wizard.Wizard#addPage(org.eclipse.jface.wizard.IWizardPage)
       */
      @Override
      public void addPages() {
        addPage(_page);
      }

      @Override
      public boolean performFinish() {
        _page.finish();
        FileBasedContentProviderFactory.addNewFileBasedContentProvider(getModifiableProjectDescription(), _page
            .getName(), _page.getVersion(), _page.getBinaryPaths().toArray(new String[0]), _page.getSourcePaths()
            .toArray(new String[0]), getAnalyzeMode(_page.isAnalyze(), _page.isAnalyzeSources()));

        return true;

      }
    };
  }

  public static AnalyzeMode getAnalyzeMode(boolean analyze, boolean analyzeSources) {
    if (analyzeSources) {
      return AnalyzeMode.BINARIES_AND_SOURCES;
    }

    if (analyze) {
      return AnalyzeMode.BINARIES_ONLY;
    }

    return AnalyzeMode.DO_NOT_ANALYZE;
  }

}
