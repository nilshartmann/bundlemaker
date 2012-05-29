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

import static java.lang.String.format;

import java.util.Arrays;
import java.util.LinkedList;

import org.bundlemaker.core.ui.projecteditor.filebased.FileBasedContentEditorUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.internal.ui.wizards.TypedViewerFilter;
import org.eclipse.jdt.internal.ui.wizards.buildpaths.MultipleFolderSelectionDialog;
import org.eclipse.jdt.ui.wizards.BuildPathDialogAccess;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ResourcesProjectContentProviderWizardPage extends WizardPage {

  /**
   * The list containing the currently selected entries
   */
  private List   _contentList;

  private Button _removeButton;

  public ResourcesProjectContentProviderWizardPage() {
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

    Composite contentListComposite = new Composite(parent, SWT.NONE);
    final Shell shell = contentListComposite.getShell();
    GridData layoutData = new GridData(GridData.FILL_BOTH);
    contentListComposite.setLayoutData(layoutData);
    contentListComposite.setLayout(new GridLayout(2, false));

    // Create the SWT List displaying the content
    _contentList = new List(contentListComposite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
    layoutData = new GridData(GridData.FILL_BOTH);
    layoutData.verticalIndent = 0;
    _contentList.setLayoutData(layoutData);

    // Create the button bar on the right side of the content list
    Composite buttonBar = new Composite(contentListComposite, SWT.NONE);

    buttonBar.setLayout(new GridLayout(1, false));
    GridData gd = new GridData();
    gd.verticalAlignment = GridData.BEGINNING;
    gd.horizontalAlignment = GridData.FILL;
    gd.verticalIndent = 0;
    buttonBar.setLayoutData(gd);

    newTextButton(buttonBar, "Add Archives...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        addArchives(shell);
      }

    });

    newTextButton(buttonBar, "Add external archives...", new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        addExternalArchives(shell, "Select archives to add...");
      }

    });

    newTextButton(buttonBar, "Add Folders...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        addFolders(shell);
      }

    });

    newTextButton(buttonBar, "Add external folders...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        addExternalFolders(shell);
      }

    });

    _removeButton = newTextButton(buttonBar, "Remove Entry", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        _contentList.remove(_contentList.getSelectionIndices());
        refreshEnablement();
      }

    });

    // _otherButtons.add(newTextButton(buttonBar, "Add variable...", new SelectionAdapter() {
    // @Override
    // public void widgetSelected(SelectionEvent e) {
    // addVariable(shell);
    // }
    //
    // }));
    refreshEnablement();

    setControl(contentListComposite);
  }

  /**
   * Refresh the button enablement state according to the selection in the list
   */
  private void refreshEnablement() {

    int itemsSelected = _contentList.getSelectionCount();

    _removeButton.setEnabled(itemsSelected > 0);

    setPageComplete(_contentList.getItemCount() > 0);
  }

  /**
   * Add one ore more archives from the workspace to the content
   * 
   * @param parentShell
   */
  private void addArchives(Shell parentShell) {
    IPath[] selected = BuildPathDialogAccess.chooseJAREntries(parentShell, null, new IPath[0]);

    if (selected == null || selected.length < 1) {
      // nothing selected
      return;
    }

    IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

    for (int i = 0; i < selected.length; i++) {
      IResource member = workspaceRoot.findMember(selected[i]);
      if (member == null) {
        String workspacePath = format("${workspace_loc:%s}", selected[i].toString());
        _contentList.add(workspacePath);
      } else {
        String projectRelativePath = FileBasedContentEditorUtils.getProjectRelativePath(member);
        _contentList.add(projectRelativePath);
      }
    }

    refreshEnablement();
  }

  /**
   * Add one ore more workspace-relative folders to the content
   * 
   * @param shell
   */
  @SuppressWarnings("restriction")
  private void addFolders(Shell shell) {
    MultipleFolderSelectionDialog dialog = new MultipleFolderSelectionDialog(shell, new WorkbenchLabelProvider(),
        new WorkbenchContentProvider());
    dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
    dialog.setTitle("Select Folders");
    dialog.setMessage("Select Folder you want to add to your resource definition");
    dialog.addFilter(new TypedViewerFilter(new Class[] { IProject.class, IFolder.class }));
    if (dialog.open() == Window.OK) {
      Object[] elements = dialog.getResult();
      for (int i = 0; i < elements.length; i++) {
        IResource elem = (IResource) elements[i];
        String workspaceFolder = FileBasedContentEditorUtils.getProjectRelativePath(elem);
        _contentList.add(workspaceFolder);
      }
    }

    refreshEnablement();
  }

  /**
   * Opens a {@link DirectoryDialog} and returns the qualified selected folder or null if the dialog has been canceled.
   * 
   * @param parentShell
   * @return
   */
  private void addExternalFolders(Shell parentShell) {
    DirectoryDialog dialog = new DirectoryDialog(parentShell, SWT.MULTI);
    String folder = dialog.open();
    if (folder != null) {
      _contentList.add(folder);
    }

    refreshEnablement();
  }

  /**
   * Add some or more external archives from the filesystem to the content
   * 
   * @param parentShell
   * @param title
   */
  private void addExternalArchives(Shell parentShell, String title) {
    FileDialog fileDialog = new FileDialog(parentShell, SWT.MULTI);
    fileDialog.setText(title);
    fileDialog.setFilterExtensions(new String[] { "*.jar;*.zip", "*.*" });
    if (fileDialog.open() == null) {
      return;
    }
    String[] fileNames = fileDialog.getFileNames();
    if (fileNames.length > 0) {
      for (int i = 0; i < fileNames.length; i++) {
        IPath path = new Path(fileDialog.getFilterPath()).append(fileNames[i]);
        _contentList.add(path.toOSString());
      }
    }

    refreshEnablement();
  }

  /**
   * Creates a default text button with the specified text and SelectionListener
   * 
   * @param composite
   * @param text
   * @param listener
   * @return
   */
  private Button newTextButton(Composite composite, String text, SelectionListener listener) {

    final Button button = new Button(composite, SWT.PUSH);
    button.setText(text);
    button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    if (listener != null) {
      button.addSelectionListener(listener);
    }
    return button;

  }

  public java.util.List<String> getChosenResources() {
    java.util.List<String> result = new LinkedList<String>();

    result.addAll(Arrays.asList(_contentList.getItems()));

    return result;
  }

}
