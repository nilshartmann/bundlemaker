/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.handler.exporter;

import java.io.File;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.internal.ui.wizards.TypedViewerFilter;
import org.eclipse.jdt.internal.ui.wizards.buildpaths.FolderSelectionDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
@SuppressWarnings("restriction")
public class BinaryBundleExporterConfigurationDialog extends AbstractExporterConfigurationDialog {

  // output location
  private boolean   _saveToFileSystem = true;

  private Combo     _externalFolderCombo;

  private Button    _externalFolderRadio;

  private String    _externalFolder   = "";  //$NON-NLS-1$

  private Text      _workspaceText;

  private IResource _workspaceFolder;

  private Button    _workspaceFolderRadio;

  // configuration
  private Button    _includeSourcesCheckBox;

  private boolean   _includeSources   = true;

  /**
   * @param parentShell
   */
  public BinaryBundleExporterConfigurationDialog(Shell parentShell) {
    super(parentShell);

  }

  @Override
  protected void createControls(Composite dialogComposite) {
    setTitle("Export modules as binary bundles");
    setMessage("Choose how to export your modules");

    // Location group
    Group locationGroup = createGroup(dialogComposite, "Export destination");

    createExportToFolder(locationGroup);
    createExportToWorkspace(locationGroup);

    // Configuration group
    Group settingsGroup = createGroup(dialogComposite, "Exporter settings");

    createSettings(settingsGroup);

    // set initial enablement
    updateEnablement();
  }

  /**
   * @param settingsGroup
   */
  private void createSettings(Group settingsGroup) {
    _includeSourcesCheckBox = new Button(settingsGroup, SWT.CHECK);
    _includeSourcesCheckBox.setText("Include sources in OSGI-OPT");
    _includeSourcesCheckBox.setSelection(_includeSources);
    _includeSourcesCheckBox.addListener(SWT.Selection, new Listener() {
      @Override
      public void handleEvent(Event event) {
        _includeSources = _includeSourcesCheckBox.getSelection();
      }
    });

  }

  private void createExportToFolder(Composite composite) {
    _externalFolderRadio = new Button(composite, SWT.RADIO);
    _externalFolderRadio.setText("External folder");
    _externalFolderRadio.addListener(SWT.Selection, new Listener() {
      @Override
      public void handleEvent(Event event) {
        _saveToFileSystem = true;
        _externalFolder = _externalFolderCombo.getText();
        _externalFolderCombo.setFocus();
        updateEnablement();
      }

    });

    Composite inner = new Composite(composite, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    layout.marginWidth = 0;
    inner.setLayout(layout);
    GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
    inner.setLayoutData(data);

    _externalFolderCombo = createDropDownCombo(inner);
    _externalFolder = "";
    _externalFolderCombo.setItems(BinaryBundleConfigurationStore.getInstance().getHistory());
    _externalFolderCombo.setText(_externalFolder);
    _externalFolderCombo.addListener(SWT.Modify, new Listener() {
      @Override
      public void handleEvent(Event event) {
        _externalFolder = _externalFolderCombo.getText();
        updateEnablement();
      }
    });

    final Button browseButton = new Button(inner, SWT.PUSH);
    browseButton.setText("Browse...");
    data = new GridData();
    data.horizontalAlignment = GridData.FILL;
    int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
    data.widthHint = Math.max(widthHint, browseButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
    browseButton.setLayoutData(data);
    browseButton.addListener(SWT.Selection, new Listener() {
      @Override
      public void handleEvent(Event event) {

        DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.MULTI);
        String folder = dialog.open();
        if (folder != null) {
          _saveToFileSystem = true;
          _externalFolderCombo.setText(folder);
          _externalFolder = folder;
          _externalFolderCombo.setFocus();
          updateEnablement();
        }
      }
    });
  }

  private void createExportToWorkspace(Composite composite) {
    _workspaceFolderRadio = new Button(composite, SWT.RADIO);
    _workspaceFolderRadio.setText("Workspace location");
    _workspaceFolderRadio.addListener(SWT.Selection, new Listener() {
      @Override
      public void handleEvent(Event event) {
        _saveToFileSystem = false;
        _workspaceText.setFocus();
        updateEnablement();
      }
    });

    final Composite nameGroup = new Composite(composite, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    layout.marginWidth = 0;
    nameGroup.setLayout(layout);
    final GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
    nameGroup.setLayoutData(data);

    _workspaceText = createTextField(nameGroup);
    _workspaceText.setEditable(false);
    // _workspaceText.addListener(SWT.Modify, new Listener() {
    // @Override
    // public void handleEvent(Event event) {
    // _externalFolder = _workspaceFolder.getLocation().toString();
    // updateEnablement();
    // }
    // });
    Button wsBrowseButton = new Button(nameGroup, SWT.PUSH);
    GridData gd = new GridData();
    gd.horizontalAlignment = GridData.FILL;
    int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
    gd.widthHint = Math.max(widthHint, wsBrowseButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
    wsBrowseButton.setLayoutData(gd);
    wsBrowseButton.setText("Browse...");
    wsBrowseButton.addListener(SWT.Selection, new Listener() {
      @Override
      public void handleEvent(Event event) {
        _saveToFileSystem = false;
        FolderSelectionDialog dialog = new FolderSelectionDialog(getShell(), new WorkbenchLabelProvider(),
            new WorkbenchContentProvider());
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        dialog.setInput(workspaceRoot);
        dialog.setTitle("Select folder");
        dialog.setMessage("Select a destination folder from your workspace");
        dialog.addFilter(new TypedViewerFilter(new Class[] { IProject.class, IFolder.class }));

        if (_workspaceFolder != null) {
          dialog.setInitialSelection(_workspaceFolder);
        }

        if (dialog.open() == Window.OK) {
          IResource resource = (IResource) dialog.getFirstResult();
          if (resource != null) {
            _saveToFileSystem = false;
            _workspaceFolder = resource;
            _workspaceText.setText(resource.getFullPath().toString());
            _workspaceText.setFocus();
            updateEnablement();
          }
        }
      }
    });
  }

  private void updateEnablement() {
    _externalFolderRadio.setSelection(_saveToFileSystem);
    _workspaceFolderRadio.setSelection(!_saveToFileSystem);

    if (_saveToFileSystem) {
      if (_externalFolder == null || _externalFolder.isEmpty()) {
        setErrorMessage("Select output folder");
        return;
      }
    }

    if (!_saveToFileSystem) {
      if (_workspaceFolder == null) {
        setErrorMessage("Select a folder from your workspace");
        return;
      }
    }

    setErrorMessage(null);
  }

  public boolean isIncludeSources() {
    return _includeSources;
  }

  public File getDestination() {
    if (isSaveToFileSystem()) {
      // remember last selection
      BinaryBundleConfigurationStore.getInstance().remember(_externalFolder);
      return new File(_externalFolder);
    }

    return _workspaceFolder.getLocation().toFile();

  }

  public IResource getWorkspaceResource() {
    return _workspaceFolder;
  }

  public boolean isSaveToFileSystem() {
    return _saveToFileSystem;
  }

  static class BinaryBundleConfigurationStore extends ConfigurationStore {

    private static BinaryBundleConfigurationStore instance;

    public static BinaryBundleConfigurationStore getInstance() {
      if (instance == null) {
        instance = new BinaryBundleConfigurationStore();
      }
      return instance;
    }

    @Override
    protected String getPreviousTag() {
      return "previousFolders";
    }

    @Override
    protected String getListTag() {
      return "folders";
    }

    @Override
    protected String getStoreSection() {
      return "BinaryBundleExporterConfigurationDialog";
    }

  }

}
