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
package org.bundlemaker.core.ui.editor;

import static java.lang.String.format;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.ui.StringVariableSelectionDialog;
import org.eclipse.jdt.internal.ui.wizards.buildpaths.ArchiveFileFilter;
import org.eclipse.jdt.internal.ui.wizards.buildpaths.FolderSelectionDialog;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * Dialog for editing an entry of the project content
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
@SuppressWarnings("restriction")
public class EditEntryDialog extends TitleAreaDialog {

  private String _entry;

  private Text   _entryText;

  /**
   * @param parentShell
   */
  public EditEntryDialog(Shell parentShell, String preselection) {
    super(parentShell);
    _entry = preselection;

    configureDialog();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.TitleAreaDialog#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea(Composite parent) {
    setTitle("Edit entry");
    setMessage("Edit the entry");

    final Composite areaComposite = (Composite) super.createDialogArea(parent);

    Composite dialogComposite = new Composite(areaComposite, SWT.NONE);
    dialogComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
    GridLayout gridLayout = new GridLayout(2, false);
    dialogComposite.setLayout(gridLayout);

    Label label = new Label(dialogComposite, SWT.NONE);
    label.setText("Entry:");
    _entryText = new Text(dialogComposite, SWT.BORDER);
    _entryText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    _entryText.setText(_entry);

    Composite buttonBar = new Composite(dialogComposite, SWT.NONE);
    buttonBar.setLayout(new GridLayout(3, true));

    GridData gridData = new GridData();
    gridData.horizontalAlignment = SWT.RIGHT;
    gridData.horizontalSpan = 2;
    buttonBar.setLayoutData(gridData);

    final Shell shell = buttonBar.getShell();

    createButton(buttonBar, "Workspace...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        selectFromWorkspace(shell);
      }
    });

    createButton(buttonBar, "External archive...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        selectExternalArchive(shell);
      }
    });

    createButton(buttonBar, "External folder...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        selectExternalFolder(shell);
      }
    });

    createButton(buttonBar, "Variable...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        selectVariable(shell);
      }
    });

    Dialog.applyDialogFont(areaComposite);

    return areaComposite;
  }

  @SuppressWarnings({ "rawtypes" })
  private void selectFromWorkspace(Shell shell) {
    FolderSelectionDialog dialog = new FolderSelectionDialog(shell, new WorkbenchLabelProvider(),
        new WorkbenchContentProvider());
    dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
    dialog.setTitle("Select path");
    dialog.setMessage("Select a folder or archive of from your workspace");
    ViewerFilter filter = new ArchiveFileFilter((List) null, false, false);
    dialog.addFilter(filter); // new TypedViewerFilter(new Class[] { IProject.class, IFolder.class, IFile.class }));

    if (dialog.open() == Window.OK) {
      IResource resource = (IResource) dialog.getFirstResult();
      resource.getLocation();
      String entry = format("{workspace_loc:%s}", resource.getLocation());
      _entryText.setText(entry);
    }
  }

  private void selectVariable(Shell parentShell) {
    StringVariableSelectionDialog dialog = new StringVariableSelectionDialog(parentShell);
    if (dialog.open() != Window.OK) {
      return;
    }

    String variableExpression = dialog.getVariableExpression();
    if (variableExpression != null) {
      _entryText.setText(variableExpression);
    }
  }

  private void selectExternalArchive(Shell parent) {
    FileDialog dialog = new FileDialog(parent);
    dialog.setText("Select external archive");
    dialog.setFilterExtensions(new String[] { "*.jar;*.zip", "*.*" });
    String res = dialog.open();
    if (res != null) {
      _entryText.setText(Path.fromOSString(res).makeAbsolute().toOSString());
    }
  }

  private void selectExternalFolder(Shell parentShell) {
    DirectoryDialog dialog = new DirectoryDialog(parentShell);
    dialog.setMessage("Select an external folder");
    dialog.setText("External folder");
    // dialog.setFilterPath(currPath.toOSString());
    String res = dialog.open();
    if (res != null) {
      _entryText.setText(Path.fromOSString(res).makeAbsolute().toOSString());
    }

  }

  private Button createButton(Composite parent, String text, SelectionListener selectionListener) {
    Button button = new Button(parent, SWT.PUSH);
    button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    button.setText(text);

    if (selectionListener != null) {
      button.addSelectionListener(selectionListener);
    }

    return button;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
   */
  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText("Edit entry");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.Dialog#okPressed()
   */
  @Override
  protected void okPressed() {
    _entry = _entryText.getText();
    super.okPressed();
  }

  private void configureDialog() {
    setShellStyle(SWT.CLOSE | SWT.MAX | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL | SWT.RESIZE
        | getDefaultOrientation());
    setHelpAvailable(false);

  }

  public String getEntry() {
    return _entry;
  }

}
