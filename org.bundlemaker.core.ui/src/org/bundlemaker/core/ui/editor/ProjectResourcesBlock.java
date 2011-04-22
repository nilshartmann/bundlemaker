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

import java.util.Iterator;

import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.ui.wizards.BuildPathDialogAccess;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ProjectResourcesBlock {

  /**
   * Name of the property, that will be fired when the project content has been changed by this block
   */
  public final static String               PROJECT_CONTENT_PROPERTY = "bundleMakerProjectContent";

  /**
   * change listeners
   */
  private final ListenerList               _listeners               = new ListenerList();

  /**
   * If set to true this block edits resources otherwise types
   */
  private final boolean                    _editResources;

  private final String                     _title;

  private final String                     _description;

  private final BundleMakerProjectProvider _bundleMakerProjectProvider;

  private TreeViewer                       _treeViewer;

  private SectionPart                      _resourcesSectionPart;

  public ProjectResourcesBlock(boolean editResources, String title, String description,
      BundleMakerProjectProvider provider) {
    _editResources = editResources;
    _title = title;
    _description = description;
    _bundleMakerProjectProvider = provider;
  }

  public void createControl(IManagedForm mform) {

    FormToolkit toolkit = mform.getToolkit();
    final ScrolledForm form = mform.getForm();

    Section resourcesSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR | Section.EXPANDED
        | Section.TWISTIE);
    FormText descriptionText = toolkit.createFormText(resourcesSection, false);
    descriptionText.setText("<form><p>" + _description + "</p></form>", true, false);
    resourcesSection.setDescriptionControl(descriptionText);
    resourcesSection.setText(_title);
    // resourcesSection.setDescription(description);
    resourcesSection.setLayoutData(new GridData(GridData.FILL_BOTH));

    Composite client = toolkit.createComposite(resourcesSection);
    client.setLayoutData(new GridData(GridData.FILL_BOTH));
    client.setLayout(new GridLayout(2, false));

    resourcesSection.setClient(client);

    _resourcesSectionPart = new SectionPart(resourcesSection);
    mform.addPart(_resourcesSectionPart);

    final Tree projectContentTree = toolkit.createTree(client, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
    GridData gd = new GridData(GridData.FILL_BOTH);
    gd.heightHint = 200;
    gd.widthHint = 100;
    projectContentTree.setLayoutData(gd);

    _treeViewer = new TreeViewer(projectContentTree);
    _treeViewer.setLabelProvider(new WorkbenchLabelProvider());
    BaseWorkbenchContentProvider provider = new BaseWorkbenchContentProvider();
    _treeViewer.setContentProvider(provider);

    IBundleMakerProjectDescription bundleMakerProjectDescription = getBundleMakerProjectDescription();
    System.out.println("Init treeviewer mit projectdescription " + bundleMakerProjectDescription);
    BundleMakerProjectDescriptionWrapper wrapper = (_editResources ? BundleMakerProjectDescriptionWrapper
        .forResources(bundleMakerProjectDescription) : BundleMakerProjectDescriptionWrapper
        .forTypes(bundleMakerProjectDescription));
    _treeViewer.setInput(wrapper);

    Composite buttonBar = toolkit.createComposite(client);
    buttonBar.setLayout(new GridLayout(1, false));
    gd = new GridData();
    gd.verticalAlignment = GridData.BEGINNING;
    gd.horizontalAlignment = GridData.FILL;
    buttonBar.setLayoutData(gd);

    final Button editButton = toolkit.createButton(buttonBar, "Edit...", SWT.PUSH);
    editButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    final Button addButton = toolkit.createButton(buttonBar, "Add...", SWT.PUSH);
    addButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    addButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        ModifyProjectContentDialog dialog = new ModifyProjectContentDialog(editButton.getShell(), _editResources, false);
        if (dialog.open() == Window.OK) {
          if (_editResources) {
            System.out.println("sourcepaths: " + dialog.getSourcePaths());
            getBundleMakerProjectDescription().addResourceContent(dialog.getName(), dialog.getVersion(),
                dialog.getBinaryPaths(), dialog.getSourcePaths());
          } else {
            getBundleMakerProjectDescription().addTypeContent(dialog.getName(), dialog.getVersion(),
                dialog.getBinaryPaths());
          }
          // Refresh UI
          _treeViewer.refresh();

          firePropertyChange();

          // Mark editor dirty
          _resourcesSectionPart.markDirty();

        }
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });

    final Button addArchivesButton = toolkit.createButton(buttonBar, "Add archives...", SWT.PUSH);
    addArchivesButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    final Button addExternalArchivesButton = toolkit.createButton(buttonBar, "Add external archives...", SWT.PUSH);
    addArchivesButton.addSelectionListener(new AddArchivesSelectionListener(addArchivesButton.getShell()));
    addExternalArchivesButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    addExternalArchivesButton.addSelectionListener(new AddExternalArchiveSelectionListener(addExternalArchivesButton
        .getShell()));
    Button removeButton = toolkit.createButton(buttonBar, "Remove", SWT.PUSH);
    removeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    removeButton.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        ISelection selection = _treeViewer.getSelection();
        if (selection.isEmpty() || !(selection instanceof IStructuredSelection)) {
          // TODO enablement
          return;
        }

        IStructuredSelection structuredSelection = (IStructuredSelection) selection;
        Iterator<?> iterator = structuredSelection.iterator();
        while (iterator.hasNext()) {
          Object element = iterator.next();
          System.out.println("element: " + element);
          if (element instanceof IFileBasedContent) {
            IFileBasedContent content = (IFileBasedContent) element;
            String id = content.getId();
            getBundleMakerProjectDescription().removeContent(id);
          }
          // Refresh view
          _treeViewer.refresh();

          firePropertyChange();

          // mark editor dirty
          _resourcesSectionPart.markDirty();

        }
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
        // TODO Auto-generated method stub

      }
    });
    toolkit.paintBordersFor(client);

  }

  private IBundleMakerProjectDescription getBundleMakerProjectDescription() {
    return _bundleMakerProjectProvider.getBundleMakerProject().getProjectDescription();
  }

  public void addPropertyChangeListener(IPropertyChangeListener listener) {
    _listeners.add(listener);
  }

  public void removePropertyChangeListener(IPropertyChangeListener listener) {
    _listeners.remove(listener);
  }

  private void firePropertyChange() {
    PropertyChangeEvent event = new PropertyChangeEvent(this, PROJECT_CONTENT_PROPERTY, null, null);
    Object[] listeners = _listeners.getListeners();
    for (int i = 0; i < listeners.length; i++) {
      IPropertyChangeListener listener = (IPropertyChangeListener) listeners[i];
      listener.propertyChange(event);
    }
  }

  abstract class AbstractModifyContentHandler implements SelectionListener {
    protected final Shell _shell;

    public AbstractModifyContentHandler(Shell shell) {
      _shell = shell;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    @Override
    public final void widgetSelected(SelectionEvent e) {
      if (execute()) {
        _treeViewer.refresh();
        // Refresh view

        firePropertyChange();

        // mark editor dirty
        _resourcesSectionPart.markDirty();

      }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    @Override
    public final void widgetDefaultSelected(SelectionEvent e) {
    }

    /**
     * Do the work. Return true if the project has been modified otherwise false.
     * 
     * @return
     */
    protected abstract boolean execute();

  }

  class AddExternalArchiveSelectionListener extends AbstractModifyContentHandler {

    /**
     * @param shell
     */
    public AddExternalArchiveSelectionListener(Shell shell) {
      super(shell);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bundlemaker.core.ui.editor.ProjectResourcesBlock.AbstractModifyContentHandler#execute()
     */
    @Override
    protected boolean execute() {
      FileDialog fileDialog = new FileDialog(_shell, SWT.MULTI);
      fileDialog.setText("Add " + (_editResources ? " Resources" : "Types"));
      fileDialog.setFilterExtensions(new String[] { "*.jar;*.zip", "*.*" });
      if (fileDialog.open() == null) {
        return false;
      }
      String[] fileNames = fileDialog.getFileNames();
      if (fileNames.length > 0) {
        // Add all selected archives to the project description
        for (String string : fileNames) {
          IPath path = new Path(fileDialog.getFilterPath()).append(string);
          String binaryRoot = path.toOSString();
          if (_editResources) {
            getBundleMakerProjectDescription().addResourceContent(binaryRoot);
          } else {
            getBundleMakerProjectDescription().addTypeContent(binaryRoot);
          }
        }
        return true;
      }
      return false;
    }

  }

  class AddArchivesSelectionListener extends AbstractModifyContentHandler {

    /**
     * @param shell
     */
    public AddArchivesSelectionListener(Shell shell) {
      super(shell);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bundlemaker.core.ui.editor.ProjectResourcesBlock.AbstractModifyContentHandler#execute()
     */
    @Override
    protected boolean execute() {
      IPath[] selected = BuildPathDialogAccess.chooseJAREntries(_shell, _bundleMakerProjectProvider
          .getBundleMakerProject().getProject().getFullPath(), new IPath[0]);

      if (selected == null || selected.length < 1) {
        return false;
      }

      for (IPath iPath : selected) {
        String workspacePath = format("${workspace_loc:%s}", iPath.toString());
        if (_editResources) {
          getBundleMakerProjectDescription().addResourceContent(workspacePath);
        } else {
          getBundleMakerProjectDescription().addTypeContent(workspacePath);
        }
      }

      return true;

    }

  }
  // IPath[] selected= BuildPathDialogAccess.chooseJAREntries(getShell(), fCurrJProject.getPath(),
  // getUsedJARFiles(existing));

}
