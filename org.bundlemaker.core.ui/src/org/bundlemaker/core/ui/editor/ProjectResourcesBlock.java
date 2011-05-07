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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.projectdescription.modifiable.IModifiableBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.modifiable.IModifiableFileBasedContent;
import org.bundlemaker.core.ui.internal.UIImages;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.ui.wizards.BuildPathDialogAccess;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
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

/**
 * A block (represented by a Form Section) that can be used to edit either the resources or the types of the bundlemaker
 * project description
 * 
 * @see IBundleMakerProjectDescription
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

  private Button                           _removeButton;

  private Button                           _editButton;

  /**
   * @param editResources
   *          true if this block edits resources, false if it edits the project's types
   * @param title
   *          the title of this block
   * @param description
   *          the description to be display in the section's message part
   * @param provider
   */
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

    // Create the collapsible section
    Section resourcesSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR | Section.EXPANDED
        | Section.TWISTIE);
    FormText descriptionText = toolkit.createFormText(resourcesSection, false);
    descriptionText.setText("<form><p>" + _description + "</p></form>", true, false);
    resourcesSection.setDescriptionControl(descriptionText);
    resourcesSection.setText(_title);
    resourcesSection.setLayoutData(new GridData(GridData.FILL_BOTH));

    Composite client = toolkit.createComposite(resourcesSection);
    client.setLayoutData(new GridData(GridData.FILL_BOTH));
    client.setLayout(new GridLayout(2, false));
    resourcesSection.setClient(client);

    _resourcesSectionPart = new SectionPart(resourcesSection);
    mform.addPart(_resourcesSectionPart);

    // Create the tree view and viewer that displays the IFileBasedContent entries

    final Tree projectContentTree = toolkit.createTree(client, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL
        | SWT.FULL_SELECTION);
    GridData gd = new GridData(GridData.FILL_BOTH);
    gd.heightHint = 200;
    gd.widthHint = 100;
    projectContentTree.setLayoutData(gd);
    projectContentTree.setLinesVisible(true);

    _treeViewer = new TreeViewer(projectContentTree);

    // _treeViewer.setLabelProvider(new WorkbenchLabelProvider());
    _treeViewer.setContentProvider(new BaseWorkbenchContentProvider());
    createColumns();

    IModifiableBundleMakerProjectDescription bundleMakerProjectDescription = getBundleMakerProjectDescription();
    System.out.println("Init treeviewer mit projectdescription " + bundleMakerProjectDescription);
    BundleMakerProjectDescriptionWrapper wrapper = (_editResources ? BundleMakerProjectDescriptionWrapper
        .forResources(bundleMakerProjectDescription) : BundleMakerProjectDescriptionWrapper
        .forTypes(bundleMakerProjectDescription));
    _treeViewer.setInput(wrapper);

    // Create the buttonbar
    final VerticalFormButtonBar buttonBar = new VerticalFormButtonBar(client, toolkit);
    final Shell shell = client.getShell();
    _editButton = buttonBar.newButton("Edit...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        editContent(shell);
      }

    });

    // Create buttons
    buttonBar.newButton("Add...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        addContent(shell);
      }

    });

    buttonBar.newButton("Add archives...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        addArchives(shell);
      }
    });

    buttonBar.newButton("Add external archives...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        addExternalArchives(shell);
      }
    });

    _removeButton = buttonBar.newButton("Remove", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        removeContent();
      }
    });

    _treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        refreshEnablement();
      }
    });

    // set initial enablement
    refreshEnablement();

    // paint the borders
    toolkit.paintBordersFor(client);
  }

  /**
   * 
   */
  private void createColumns() {
    Tree tree = _treeViewer.getTree();
    TableLayout layout = new TableLayout();
    TreeViewerColumn column = new TreeViewerColumn(_treeViewer, SWT.NONE);
    column.setLabelProvider(new BundleMakerPathColumnLabelProvider(true));
    column.getColumn().setResizable(true);
    column.getColumn().setMoveable(true);
    column.getColumn().setText("Resource");
    layout.addColumnData(new ColumnWeightData(80));

    column = new TreeViewerColumn(_treeViewer, SWT.NONE);
    column.setLabelProvider(new BundleMakerPathColumnLabelProvider(false));
    column.setEditingSupport(new BundleMakerPathEditingSupport(_treeViewer));
    column.getColumn().setResizable(true);
    column.getColumn().setMoveable(true);
    column.getColumn().setText("Analyze");
    column.getColumn().setAlignment(SWT.CENTER);
    layout.addColumnData(new ColumnWeightData(20));

    tree.setLayout(layout);
    tree.setHeaderVisible(true);
    tree.layout(true);
  }

  private class BundleMakerPathEditingSupport extends EditingSupport {

    public BundleMakerPathEditingSupport(ColumnViewer viewer) {
      super(viewer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.EditingSupport#getCellEditor(java.lang.Object)
     */
    @Override
    protected CellEditor getCellEditor(Object element) {

      if (!canEdit(element)) {
        return null;
      }

      return new CheckboxCellEditor(null, SWT.CHECK);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.EditingSupport#canEdit(java.lang.Object)
     */
    @Override
    protected boolean canEdit(Object element) {
      if (!(element instanceof IModifiableFileBasedContent)) {
        return false;
      }

      IModifiableFileBasedContent content = (IModifiableFileBasedContent) element;
      return content.isResourceContent();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.EditingSupport#getValue(java.lang.Object)
     */
    @Override
    protected Object getValue(Object element) {
      IModifiableFileBasedContent content = (IModifiableFileBasedContent) element;
      return content.isAnalyzeSourceResources();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.EditingSupport#setValue(java.lang.Object, java.lang.Object)
     */
    @Override
    protected void setValue(Object element, Object value) {
      IModifiableFileBasedContent content = (IModifiableFileBasedContent) element;
      Boolean analyze = (Boolean) value;
      System.out.println("Set analyze on " + content + " to -> " + analyze);
      content.setAnalyzeSourceResources(analyze);
      projectDescriptionChanged();
      // _treeViewer.refresh();
    }

  }

  class BundleMakerPathColumnLabelProvider extends ColumnLabelProvider {

    private final boolean _resourceColumn;

    /**
     * @param column
     */
    public BundleMakerPathColumnLabelProvider(boolean resourceColumn) {
      _resourceColumn = resourceColumn;
    }

    @Override
    public Image getImage(Object element) {
      if (!_resourceColumn) {
        if (element instanceof IFileBasedContent) {
          IFileBasedContent content = (IFileBasedContent) element;
          if (!content.isResourceContent()) {
            return null;
          }
          // TODO: ResourceContent ohne Sources sollte isAnalyzeSourceResources() false haben ???
          if (content.isAnalyzeSourceResources()) {
            return UIImages.BINARY_ARCHIVE.getImage();
          }
          return null;
        }

      }
      if (!(element instanceof BundleMakerPath)) {
        return null;
      }
      BundleMakerPath path = (BundleMakerPath) element;

      if (_resourceColumn) {
        if (path.isFolder()) {
          if (path.isBinary()) {
            return UIImages.BINARY_FOLDER.getImage();
          }
          return UIImages.SOURCE_FOLDER.getImage();
        }
        if (path.isBinary()) {
          return UIImages.BINARY_ARCHIVE.getImage();
        }
        return UIImages.SOURCE_ARCHIVE.getImage();
      }

      return super.getImage(element);
    }

    @Override
    public String getText(Object element) {
      if (!_resourceColumn) {
        return null;
      }

      if (element instanceof IFileBasedContent) {
        IFileBasedContent content = (IFileBasedContent) element;
        return String.format("%s [%s]", content.getName(), content.getVersion());
      }

      BundleMakerPath path = (BundleMakerPath) element;
      return path.getLabel();
    }
  }

  /**
   * Opens the {@link ModifyProjectContentDialog} to edit the currently selected IFileBasedContent
   * 
   * @param shell
   */
  private void editContent(Shell shell) {
    Collection<IFileBasedContent> selectedContents = getSelectedElementsOfType(IFileBasedContent.class);
    if (selectedContents.size() != 1) {
      return;
    }

    IFileBasedContent content = selectedContents.iterator().next();
    ModifyProjectContentDialog dialog = new ModifyProjectContentDialog(shell, content);
    if (dialog.open() != Window.OK) {
      return;
    }

    // remove 'old' FileBasedContent
    getBundleMakerProjectDescription().removeContent(content.getId());

    // re-add content
    if (_editResources) {
      getBundleMakerProjectDescription().addResourceContent(dialog.getName(), dialog.getVersion(),
          dialog.getBinaryPaths(), dialog.getSourcePaths());
    } else {
      getBundleMakerProjectDescription().addTypeContent(dialog.getName(), dialog.getVersion(), dialog.getBinaryPaths());
    }

    projectDescriptionChanged();
  }

  /**
   * Add content to the project description using the {@link ModifyProjectContentDialog}
   * 
   * @param shell
   */
  private void addContent(Shell shell) {
    ModifyProjectContentDialog dialog = new ModifyProjectContentDialog(shell, _editResources);
    if (dialog.open() != Window.OK) {
      return;
    }
    if (_editResources) {
      getBundleMakerProjectDescription().addResourceContent(dialog.getName(), dialog.getVersion(),
          dialog.getBinaryPaths(), dialog.getSourcePaths());
    } else {
      getBundleMakerProjectDescription().addTypeContent(dialog.getName(), dialog.getVersion(), dialog.getBinaryPaths());
    }

    projectDescriptionChanged();

  }

  /**
   * Remove the selected content from the project
   */
  private void removeContent() {
    Collection<IFileBasedContent> selectedContents = getSelectedElementsOfType(IFileBasedContent.class);
    if (!selectedContents.isEmpty()) {
      for (IFileBasedContent content : selectedContents) {
        String id = content.getId();
        getBundleMakerProjectDescription().removeContent(id);
      }
      projectDescriptionChanged();
    }
  }

  /**
   * Returns all selected elements from the TreeViewer that are instances of the specified class
   * 
   * @param <T>
   * @param type
   * @return
   */
  private <T> Collection<T> getSelectedElementsOfType(Class<T> type) {
    java.util.List<T> result = new LinkedList<T>();

    ITreeSelection selection = (ITreeSelection) _treeViewer.getSelection();

    Iterator<?> iterator = selection.iterator();
    while (iterator.hasNext()) {
      Object element = iterator.next();
      if (type.isInstance(element)) {
        @SuppressWarnings("unchecked")
        T content = (T) element;
        result.add(content);
      }
    }
    return result;

  }

  /**
   * Returns the {@link IBundleMakerProjectDescription} this block works on
   * 
   * @return
   */
  private IModifiableBundleMakerProjectDescription getBundleMakerProjectDescription() {
    return _bundleMakerProjectProvider.getBundleMakerProject().getModifiableProjectDescription();
  }

  /**
   * Adds the specified {@link IPropertyChangeListener} to the list of listeners
   * 
   * @param listener
   */
  public void addPropertyChangeListener(IPropertyChangeListener listener) {
    _listeners.add(listener);
  }

  /**
   * removes the specified {@link IPropertyChangeListener}
   * 
   * @param listener
   */
  public void removePropertyChangeListener(IPropertyChangeListener listener) {
    _listeners.remove(listener);
  }

  /**
   * Notifies the registered listener that the project description has been changed
   */
  private void firePropertyChange() {
    PropertyChangeEvent event = new PropertyChangeEvent(this, PROJECT_CONTENT_PROPERTY, null, null);
    Object[] listeners = _listeners.getListeners();
    for (int i = 0; i < listeners.length; i++) {
      IPropertyChangeListener listener = (IPropertyChangeListener) listeners[i];
      listener.propertyChange(event);
    }
  }

  /**
   * Adds external archives to the project description
   * 
   * @param shell
   */
  private void addExternalArchives(Shell shell) {
    FileDialog fileDialog = new FileDialog(shell, SWT.MULTI);
    fileDialog.setText("Add " + (_editResources ? " Resources" : "Types"));
    fileDialog.setFilterExtensions(new String[] { "*.jar;*.zip", "*.*" });
    if (fileDialog.open() == null) {
      return;
    }
    String[] fileNames = fileDialog.getFileNames();
    if (fileNames.length < 1) {
      return;
    }

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

    projectDescriptionChanged();

  }

  /**
   * Adds archives from the workspace to the project description
   * 
   * @param shell
   */
  private void addArchives(Shell shell) {
    IPath[] selected = BuildPathDialogAccess.chooseJAREntries(shell, _bundleMakerProjectProvider
        .getBundleMakerProject().getProject().getFullPath(), new IPath[0]);

    if (selected == null || selected.length < 1) {
      return;
    }

    for (IPath iPath : selected) {
      String workspacePath = format("${workspace_loc:%s}", iPath.toString());
      if (_editResources) {
        getBundleMakerProjectDescription().addResourceContent(workspacePath);
      } else {
        getBundleMakerProjectDescription().addTypeContent(workspacePath);
      }
    }

    projectDescriptionChanged();

  }

  /**
   * Indicate the the project description has been changed
   */
  private void projectDescriptionChanged() {
    // Refresh view
    _treeViewer.refresh();

    // notify listener
    firePropertyChange();

    // mark editor dirty
    _resourcesSectionPart.markDirty();
  }

  /**
   * sets the enabled state of the buttons according to the selection in the tree viewer
   */
  private void refreshEnablement() {
    Collection<IFileBasedContent> selectedFileBasedContents = getSelectedElementsOfType(IFileBasedContent.class);
    ITreeSelection selection = (ITreeSelection) _treeViewer.getSelection();
    int selectedElements = selection.size();
    if (selectedElements < 1 // nothing seleceted
        || selectedElements != selectedFileBasedContents.size() // selection contains other elements than
                                                                // IFileBasedContent
    ) {
      _removeButton.setEnabled(false);
      _editButton.setEnabled(false);
      return;
    }
    _editButton.setEnabled(selectedFileBasedContents.size() == 1);
    _removeButton.setEnabled(true);

  }
}
