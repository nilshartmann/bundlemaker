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

import org.bundlemaker.core.content.jdt.JdtProjectContentProvider;
import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.ui.internal.UIImages;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class JdtProjectContentProviderNode extends AbstractProjectContentProviderNode {

  /**
   * @param name
   * @param description
   * @param image
   */
  public JdtProjectContentProviderNode(IModifiableProjectDescription pd) {
    super(pd, "JDT Project", "Add a JDT project from your workspace", UIImages.JDT_PROJECT_CONTENT_PROVIDER.getImage());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.editor.provider.AbstractProjectContentProviderNode#createWizard()
   */
  @Override
  public IWizard createWizard() {
    return new Wizard() {
      private final EditJdtContentProviderPage _page = new EditJdtContentProviderPage(getModifiableProjectDescription());

      /*
       * (non-Javadoc)
       * 
       * @see org.eclipse.jface.wizard.Wizard#addPages()
       */
      @Override
      public void addPages() {
        addPage(_page);
      }

      @Override
      public boolean performFinish() {
        IProject[] selectedProjects = _page.getSelectedProjects();
        for (IProject iProject : selectedProjects) {
          JdtProjectContentProvider provider = new JdtProjectContentProvider();
          IJavaProject javaProject = JavaCore.create(iProject);
          provider.setJavaProject(javaProject);
          getModifiableProjectDescription().addContentProvider(provider);
        }
        return true;
      }
    };
  }
}
