package org.bundlemaker.core.ui.projecteditor.filebased.edit;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.LinkedList;

import org.bundlemaker.core.ui.projecteditor.filebased.FileBasedContentEditorUtils;
import org.bundlemaker.core.ui.projecteditor.filebased.ProjectPath;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.ui.StringVariableSelectionDialog;
import org.eclipse.jdt.internal.ui.wizards.TypedViewerFilter;
import org.eclipse.jdt.internal.ui.wizards.buildpaths.MultipleFolderSelectionDialog;
import org.eclipse.jdt.ui.wizards.BuildPathDialogAccess;
import org.eclipse.jface.window.Window;
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
 * A block that displays and selects archives, folders and variables
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
@SuppressWarnings("restriction")
public class ContentListBlock {

  /**
   * The list containing the currently selected entries
   */
  private List                         _contentList;

  private Button                       _removeButton;

  private Button                       _editButton;

  private boolean                      _enabled      = true;

  private final java.util.List<Button> _otherButtons = new LinkedList<Button>();

  public void createContent(Composite parent) {
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

    _contentList.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        refreshEnablement();
      }

    });

    // Create the button bar on the right side of the content list
    Composite buttonBar = new Composite(contentListComposite, SWT.NONE);

    buttonBar.setLayout(new GridLayout(1, false));
    GridData gd = new GridData();
    gd.verticalAlignment = GridData.BEGINNING;
    gd.horizontalAlignment = GridData.FILL;
    gd.verticalIndent = 0;
    buttonBar.setLayoutData(gd);

    _otherButtons.add(newTextButton(buttonBar, "Add Archives...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        addArchives(shell);
      }

    }));

    _otherButtons.add(newTextButton(buttonBar, "Add external archives...", new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        addExternalArchives(shell, "Select archives to add...");
      }

    }));

    _otherButtons.add(newTextButton(buttonBar, "Add Folders...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        addFolders(shell);
      }

    }));

    _otherButtons.add(newTextButton(buttonBar, "Add external folders...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        addExternalFolders(shell);
      }

    }));

    _otherButtons.add(newTextButton(buttonBar, "Add variable...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        addVariable(shell);
      }

    }));

    // Add the buttons
    _editButton = newTextButton(buttonBar, "Edit entry...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        String selection = _contentList.getSelection()[0];
        ProjectPath projectPath = new ProjectPath(selection, false);
        EditProjectPathDialog dialog = new EditProjectPathDialog(shell, projectPath);
        if (dialog.open() == Window.OK) {
          String modifiedEntry = dialog.getEntry().asString();
          _contentList.setItem(_contentList.getSelectionIndex(), modifiedEntry);
        }
      }

    });

    _removeButton = newTextButton(buttonBar, "Remove Entry", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        _contentList.remove(_contentList.getSelectionIndices());
        contentListChanged();
        refreshEnablement();
      }

    });

    // refresh initial enablement
    refreshEnablement();

  }

  protected void contentListChanged() {

  }

  /**
   * Refresh the button enablement state according to the selection in the list
   */
  private void refreshEnablement() {

    for (Button button : _otherButtons) {
      button.setEnabled(_enabled);
    }

    // _contentList.setEnabled(_enabled);

    int itemsSelected = _contentList.getSelectionCount();

    _editButton.setEnabled(_enabled && itemsSelected == 1);
    _removeButton.setEnabled(_enabled && itemsSelected > 0);
  }

  /**
   * @return true if this ContentListBlock contains no entries
   */
  public boolean isEmpty() {
    return _contentList.getItemCount() < 1;
  }

  /**
   * @return if this ContentListBlock currenty contains at least one entry
   */
  public boolean hasItems() {
    return _contentList.getItemCount() > 0;
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
      contentListChanged();

    }
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
      contentListChanged();
    }
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

    for (int i = 0; i < selected.length; i++) {
      String workspacePath = format("${workspace_loc:%s}", selected[i].toString());
      _contentList.add(workspacePath);
    }
    contentListChanged();

  }

  /**
   * Add one ore more workspace-relative folders to the content
   * 
   * @param shell
   */
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
        contentListChanged();
      }

    }

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

  /**
   * Add a Variable to the content
   * 
   * @param parentShell
   */
  private void addVariable(Shell parentShell) {
    StringVariableSelectionDialog dialog = new StringVariableSelectionDialog(parentShell);
    if (dialog.open() != Window.OK) {
      return;
    }

    String variableExpression = dialog.getVariableExpression();
    if (variableExpression != null) {
      _contentList.add(variableExpression);
      contentListChanged();
    }

  }

  /**
   * Get the current items contained in the list
   * 
   * @return
   */
  public java.util.List<String> getItems() {
    return Arrays.asList(_contentList.getItems());
  }

  /**
   * Set the (initial) content of the list
   * 
   * @param array
   */
  public void setItems(String[] items) {
    _contentList.setItems(items);
  }

  public void setEnabled(boolean enabled) {
    _enabled = enabled;

    refreshEnablement();

  }

}
