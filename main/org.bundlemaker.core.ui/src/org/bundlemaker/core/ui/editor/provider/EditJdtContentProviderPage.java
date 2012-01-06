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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class EditJdtContentProviderPage extends WizardPage {

  private CheckboxTableViewer _projectNames;

  /**
   * @param pageName
   */
  protected EditJdtContentProviderPage() {
    super("EditJdtContentProviderPage");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createControl(Composite parent) {
    setMessage("Select Java projects from your workspace");
    setTitle("Add Java projects");
    setPageComplete(false);

    Composite comp = new Composite(parent, SWT.NONE);
    GridLayout layout = new GridLayout(1, true);
    GridData gridData = new GridData(GridData.FILL_BOTH);
    comp.setLayout(layout);
    comp.setLayoutData(gridData);

    createProjectSelectionTable(comp);

    setControl(comp);
  }

  private void createProjectSelectionTable(Composite radioGroup) {
    _projectNames = CheckboxTableViewer.newCheckList(radioGroup, SWT.BORDER);
    _projectNames.setContentProvider(new WorkbenchContentProvider());
    _projectNames.setLabelProvider(new WorkbenchLabelProvider());
    // projectNames.setComparator(new ResourceComparator(ResourceComparator.NAME));
    _projectNames.addFilter(new ViewerFilter() {
      private final IProject[] projectHolder = new IProject[1];

      @Override
      public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (!(element instanceof IProject)) {
          return false;
        }
        IProject project = (IProject) element;
        if (!project.isAccessible()) {
          return false;
        }
        projectHolder[0] = project;
        return isJavaProject(project);
      }
    });
    _projectNames.setInput(ResourcesPlugin.getWorkspace().getRoot());
    GridData data = new GridData(GridData.FILL_BOTH);
    data.horizontalSpan = 2;
    data.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
    data.heightHint = IDialogConstants.ENTRY_FIELD_WIDTH;
    _projectNames.getTable().setLayoutData(data);
    // table is disabled to start because all button is selected
    // projectNames.getTable().setEnabled(selectedButton.getSelection());
    _projectNames.addCheckStateListener(new ICheckStateListener() {
      @Override
      public void checkStateChanged(CheckStateChangedEvent event) {
        Object[] checkedElements = _projectNames.getCheckedElements();
        setPageComplete(checkedElements.length > 0);
      }
    });
  }

  protected boolean isJavaProject(IProject project) {
    try {
      return project.hasNature("org.eclipse.jdt.core.javanature");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  public IProject[] getSelectedProjects() {
    Object[] checkedElements = _projectNames.getCheckedElements();

    List<IProject> result = new LinkedList<IProject>();
    for (Object element : checkedElements) {
      result.add((IProject) element);
    }

    return result.toArray(new IProject[0]);
  }

}
