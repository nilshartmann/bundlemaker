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

import org.bundlemaker.core.BundleMakerProjectChangedEvent;
import org.bundlemaker.core.IBundleMakerProjectChangedListener;
import org.bundlemaker.core.ui.editor.BundleMakerProjectProvider;
import org.bundlemaker.core.ui.editor.ModifyProjectContentDialog;
import org.bundlemaker.core.ui.internal.UIImages;
import org.bundlemaker.core.ui.internal.VerticalFormButtonBar;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ContentProviderBlock implements IBundleMakerProjectChangedListener {
  /**
   * Name of the property, that will be fired when the project content has been changed by this block
   */
  public final static String               PROJECT_CONTENT_PROPERTY = "bundleMakerProjectContent";

  private final BundleMakerProjectProvider _bundleMakerProjectProvider;

  private TreeViewer                       _treeViewer;

  private SectionPart                      _resourcesSectionPart;

  private Button                           _addButton;

  private Button                           _editButton;

  private Button                           _removeButton;

  private Button                           _moveDownButton;

  private Button                           _moveUpButton;

  private Button                           _parseProjectButton;

  /**
   * change listeners
   */
  private final ListenerList               _listeners               = new ListenerList();

  /**
   * @param bundleMakerProjectProvider
   */
  public ContentProviderBlock(BundleMakerProjectProvider bundleMakerProjectProvider) {
    super();
    _bundleMakerProjectProvider = bundleMakerProjectProvider;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.IBundleMakerProjectChangedListener#bundleMakerProjectChanged(org.bundlemaker.core.
   * BundleMakerProjectChangedEvent)
   */
  @Override
  public void bundleMakerProjectChanged(BundleMakerProjectChangedEvent event) {
    // TODO Auto-generated method stub

  }

  public void createControl(IManagedForm mform) {

    FormToolkit toolkit = mform.getToolkit();
    final ScrolledForm form = mform.getForm();

    // Create the collapsible section
    Section resourcesSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR | Section.EXPANDED
        | Section.TWISTIE);
    FormText descriptionText = toolkit.createFormText(resourcesSection, false);
    descriptionText.setText("<form><p>Project content</p></form>", true, false);
    resourcesSection.setDescriptionControl(descriptionText);
    resourcesSection.setText("Project content");
    resourcesSection.setLayoutData(new GridData(GridData.FILL_BOTH));

    Composite client = toolkit.createComposite(resourcesSection);
    client.setLayoutData(new GridData(GridData.FILL_BOTH));
    client.setLayout(new GridLayout(2, false));
    resourcesSection.setClient(client);

    _resourcesSectionPart = new SectionPart(resourcesSection);
    mform.addPart(_resourcesSectionPart);

    // Create the tree view and viewer that displays the IFileBasedContent entries
    Composite treeComposite = new Composite(client, SWT.NO);
    final Tree projectContentTree = toolkit.createTree(treeComposite, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL
        | SWT.FULL_SELECTION);
    GridData gd = new GridData(GridData.FILL_BOTH);
    gd.heightHint = 200;
    gd.widthHint = 100;
    treeComposite.setLayoutData(gd);
    projectContentTree.setLinesVisible(true);

    _treeViewer = new TreeViewer(projectContentTree);
    // _treeViewer.setLabelProvider(new WorkbenchLabelProvider());
    _treeViewer.setContentProvider(new BaseWorkbenchContentProvider());
    createColumns();

    final Shell shell = client.getShell();

    _treeViewer.setInput(_bundleMakerProjectProvider.getBundleMakerProject().getModifiableProjectDescription());
    _treeViewer.expandAll();
    // _treeViewer.addDoubleClickListener(new IDoubleClickListener() {
    //
    // @Override
    // public void doubleClick(DoubleClickEvent event) {
    // TreeSelection ts = (TreeSelection) event.getSelection();
    // if (!ts.isEmpty()) {
    // editContent(shell);
    // }
    // }
    // });

    // Create the buttonbar
    final VerticalFormButtonBar buttonBar = new VerticalFormButtonBar(client, toolkit);

    // Create buttons
    buttonBar.newButton("Add...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        addContent(shell);
      }

    });

    _editButton = buttonBar.newButton("Edit...", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
      }

    });

    _removeButton = buttonBar.newButton("Remove", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        // removeContent();
      }
    });
    _moveUpButton = buttonBar.newButton("Up", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        // TODO
        System.out.println(" MOVE UP NEEDS TO BE IMPLEMENTED !!!!");
        // moveUp();
      }
    });
    _moveDownButton = buttonBar.newButton("Down", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        // TODO
        System.out.println(" MOVE DOWN NEEDS TO BE IMPLEMENTED !!!!");

        // moveDown();
      }
    });

    _parseProjectButton = buttonBar.newButton("Parse project", new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        _bundleMakerProjectProvider.parseProject();
      }

    });
    GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
    layoutData.grabExcessVerticalSpace = true;
    layoutData.verticalAlignment = SWT.BOTTOM;
    _parseProjectButton.setImage(UIImages.REFRESH.getImage());
    _parseProjectButton.setLayoutData(layoutData);

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

    _bundleMakerProjectProvider.getBundleMakerProject().addBundleMakerProjectChangedListener(this);

  }

  /**
   * Add content to the project description using the {@link ModifyProjectContentDialog}
   * 
   * @param shell
   */
  private void addContent(Shell shell) {
    //
    ChooseContentProviderWizard wizard = new ChooseContentProviderWizard(_bundleMakerProjectProvider
        .getBundleMakerProject().getModifiableProjectDescription());

    WizardDialog dialog = new WizardDialog(shell, wizard);
    if (dialog.open() != Window.CANCEL) {
      projectDescriptionChanged();
    }

    // ModifyProjectContentDialog dialog = new ModifyProjectContentDialog(shell);
    // if (dialog.open() != Window.OK) {
    // return;
    // }

    // add the new content to the project description

    // FileBasedContentProviderFactory.addNewFileBasedContentProvider(_bundleMakerProjectProvider.getBundleMakerProject()
    // .getModifiableProjectDescription(), dialog.getName(), dialog.getVersion(), dialog.getBinaryPaths(), dialog
    // .getSourcePaths(), getAnalyzeMode(dialog.isAnalyze(), dialog.isAnalyzeSources()));
    //
    // projectDescriptionChanged();

  }

  /**
   * Indicate the the project description has been changed
   */
  protected void projectDescriptionChanged() {
    // Refresh view
    _treeViewer.refresh();

    // notify listener
    firePropertyChange();

    // mark editor dirty
    _resourcesSectionPart.markDirty();

    refreshEnablement();
  }

  /**
   * 
   */
  private void createColumns() {
    Tree tree = _treeViewer.getTree();
    TreeColumnLayout layout = new TreeColumnLayout();
    TreeViewerColumn column = new TreeViewerColumn(_treeViewer, SWT.NONE);
    column.setLabelProvider(new WorkbenchAdapterColumnLabelProvider()); // (new ResourceNameColumnLabelProvider());
    column.getColumn().setResizable(true);
    column.getColumn().setMoveable(true);
    column.getColumn().setText("Resource");
    layout.setColumnData(column.getColumn(), new ColumnWeightData(80));

    column = new TreeViewerColumn(_treeViewer, SWT.NONE);
    column.setLabelProvider(new WorkbenchAdapterColumnLabelProvider()); // BundleMakerProjectDescriptionColumnLabelProvider(1));
    // column.setEditingSupport(FileBasedContentEditingSupport.newEditingSupportForAnalyzeResource(this, _treeViewer));
    column.getColumn().setResizable(true);
    column.getColumn().setMoveable(true);
    column.getColumn().setText("Analyze");
    column.getColumn().setAlignment(SWT.CENTER);
    layout.setColumnData(column.getColumn(), new ColumnWeightData(10));

    column = new TreeViewerColumn(_treeViewer, SWT.NONE);
    column.setLabelProvider(new WorkbenchAdapterColumnLabelProvider()); // new
                                                                        // BundleMakerProjectDescriptionColumnLabelProvider(2));
    // column.setEditingSupport(FileBasedContentEditingSupport.newEditingSupportForAnalyzeSources(this, _treeViewer));
    column.getColumn().setResizable(true);
    column.getColumn().setMoveable(true);
    column.getColumn().setText("Analyze Sources");
    column.getColumn().setAlignment(SWT.CENTER);
    layout.setColumnData(column.getColumn(), new ColumnWeightData(20));

    tree.getParent().setLayout(layout);
    tree.setHeaderVisible(true);
    tree.layout(true);
  }

  class WorkbenchAdapterColumnLabelProvider extends ColumnLabelProvider {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object element) {

      IWorkbenchAdapter adapter = (IWorkbenchAdapter) Platform.getAdapterManager().getAdapter(element,
          IWorkbenchAdapter.class);
      if (adapter != null) {
        return adapter.getLabel(element);
      }
      return super.getText(element);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
     */
    @Override
    public Image getImage(Object element) {
      IWorkbenchAdapter adapter = (IWorkbenchAdapter) Platform.getAdapterManager().getAdapter(element,
          IWorkbenchAdapter.class);
      if (adapter != null) {
        return adapter.getImageDescriptor(element).createImage();
      }
      return super.getImage(element);
    }

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
   * 
   */
  public void dispose() {
    _bundleMakerProjectProvider.getBundleMakerProject().removeBundleMakerProjectChangedListener(this);
  }

  private void refreshEnablement() {
    // TODO Auto-generated method stub

  }
}