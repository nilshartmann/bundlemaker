/**
 * 
 */
package org.bundlemaker.core.ui.projecteditor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.BundleMakerProjectChangedEvent;
import org.bundlemaker.core.BundleMakerProjectChangedEvent.Type;
import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IBundleMakerProjectChangedListener;
import org.bundlemaker.core.ui.BundleMakerImages;
import org.bundlemaker.core.ui.VerticalFormButtonBar;
import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropProvider;
import org.bundlemaker.core.ui.projecteditor.dnd.internal.ProjectEditorDndProviderRegistry;
import org.bundlemaker.core.ui.projecteditor.layout.FormLayoutUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * <p>
 * Graphical editor for BundleMakers project contents
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ProjectEditorPage extends FormPage {

  /**
   * The project that is edited
   */
  private final IBundleMakerProject _bundleMakerProject;

  /**
   * The form holding our controls
   */
  private ScrolledForm              _form;

  private boolean                   _needsReparsing = true;

  private TreeViewer                _treeViewer;

  private Button                    _addButton;

  private Button                    _editButton;

  private Button                    _removeButton;

  private Button                    _moveDownButton;

  private Button                    _moveUpButton;

  private Button                    _parseProjectButton;

  public ProjectEditorPage(ProjectEditor editor) {
    super(editor, "Content", "Content");

    _bundleMakerProject = editor.getBundleMakerProject();
  }

  @Override
  protected void createFormContent(IManagedForm mform) {
    super.createFormContent(mform);

    FormToolkit toolkit = mform.getToolkit();
    _form = mform.getForm();
    toolkit.decorateFormHeading(_form.getForm());
    _form.setImage(BundleMakerImages.BUNDLEMAKER_ICON_SMALL.getImage());
    _form.setText("Content");
    _form.getBody().setLayout(FormLayoutUtils.createFormGridLayout(true, 1));

    addBundleMakerProjectChangedListener();

    getEditor().addPropertyListener(new IPropertyListener() {

      @Override
      public void propertyChanged(Object source, int propId) {
        if (propId == IEditorPart.PROP_DIRTY) {
          dirty = !dirty;
        }
      }
    });

    // HIER KOMMT DER EDITOR !!!
    createEditorControls(mform);

    // Add actions to toolbar
    updateToolBar();

    refreshFormTitle();
  }

  private boolean dirty;

  @Override
  public boolean isDirty() {
    return this.dirty;
  }

  public void markDirty() {
    getEditor().editorDirtyStateChanged();
    //
    // IManagedForm managedForm = getManagedForm();
    // managedForm.dirtyStateChanged();
  }

  private void createEditorControls(final IManagedForm mform) {
    FormToolkit toolkit = mform.getToolkit();
    final ScrolledForm form = mform.getForm();

    Composite client = toolkit.createComposite(form.getBody());
    client.setLayoutData(new GridData(GridData.FILL_BOTH));
    client.setLayout(new GridLayout(2, false));

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
    _treeViewer.setLabelProvider(new WorkbenchLabelProvider());
    _treeViewer.setContentProvider(new BaseWorkbenchContentProvider());
    configureTreeDragAndDrop();
    createTreeColumns();

    final Shell shell = client.getShell();

    _treeViewer.setInput(_bundleMakerProject.getModifiableProjectDescription());

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
        editContent(shell);
      }

    });

    _removeButton = buttonBar.newButton("Remove", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        removeContent();
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
        parseProject();
      }

    });
    GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
    layoutData.grabExcessVerticalSpace = true;
    layoutData.verticalAlignment = SWT.BOTTOM;
    _parseProjectButton.setImage(BundleMakerImages.REFRESH.getImage());
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

  }

  private void configureTreeDragAndDrop() {
    ProjectEditorDndProviderRegistry projectEditorDndProviderRegistry = Activator.getDefault()
        .getProjectEditorDndProviderRegistry();

    Set<IProjectEditorDropProvider> registeredDndProviders = projectEditorDndProviderRegistry
        .getRegisteredDndProviders();

    ProjectEditorTreeViewerDropAdapter adapter = new ProjectEditorTreeViewerDropAdapter(_treeViewer,
        _bundleMakerProject, registeredDndProviders) {

      @Override
      protected void afterDrop() {
        getViewer().refresh();
        markDirty();
      }

    };

    // TODO: allow providers to specify supported operations
    int operations = DND.DROP_COPY | DND.DROP_MOVE;

    Set<Transfer> allSupportedTransferTypes = new HashSet<Transfer>();

    for (IProjectEditorDropProvider provider : registeredDndProviders) {
      Transfer[] supportedTypes = provider.getSupportedDropTypes();
      allSupportedTransferTypes.addAll(Arrays.asList(supportedTypes));
    }

    _treeViewer.addDropSupport(operations, allSupportedTransferTypes.toArray(new Transfer[0]), adapter);

  }

  private void refreshEnablement() {
    // TODO Auto-generated method stub

  }

  /**
   * @param shell
   */
  protected void addContent(Shell shell) {
    // TODO Auto-generated method stub

  }

  /**
   * @param shell
   */
  protected void editContent(Shell shell) {
    // TODO Auto-generated method stub

  }

  /**
   * 
   */
  protected void removeContent() {
    // TODO Auto-generated method stub

  }

  /**
   * 
   */
  private void createTreeColumns() {
    Tree tree = _treeViewer.getTree();
    TreeColumnLayout layout = new TreeColumnLayout();
    TreeViewerColumn column = new TreeViewerColumn(_treeViewer, SWT.NONE);
    column.setLabelProvider(new WorkbenchAdapterColumnLabelProvider()); // (new ResourceNameColumnLabelProvider());
    column.getColumn().setResizable(true);
    column.getColumn().setMoveable(true);
    column.getColumn().setText("Resource");
    layout.setColumnData(column.getColumn(), new ColumnWeightData(80));

    column = new TreeViewerColumn(_treeViewer, SWT.NONE);
    // column.setLabelProvider(new BundleMakerProjectDescriptionColumnLabelProvider(1));
    // column.setEditingSupport(FileBasedContentEditingSupport.newEditingSupportForAnalyzeResource(this, _treeViewer));
    column.setLabelProvider(new WorkbenchAdapterColumnLabelProvider()); // (new ResourceNameColumnLabelProvider());
    column.getColumn().setResizable(true);
    column.getColumn().setMoveable(true);
    column.getColumn().setText("Analyze");
    column.getColumn().setAlignment(SWT.CENTER);
    layout.setColumnData(column.getColumn(), new ColumnWeightData(10));

    column = new TreeViewerColumn(_treeViewer, SWT.NONE);
    // column.setLabelProvider(new BundleMakerProjectDescriptionColumnLabelProvider(2));
    // column.setEditingSupport(FileBasedContentEditingSupport.newEditingSupportForAnalyzeSources(this, _treeViewer));
    column.setLabelProvider(new WorkbenchAdapterColumnLabelProvider()); // (new ResourceNameColumnLabelProvider());
    column.getColumn().setResizable(true);
    column.getColumn().setMoveable(true);
    column.getColumn().setText("Analyze Sources");
    column.getColumn().setAlignment(SWT.CENTER);
    layout.setColumnData(column.getColumn(), new ColumnWeightData(20));

    tree.getParent().setLayout(layout);
    tree.setHeaderVisible(true);
    tree.layout(true);
  }

  private void updateToolBar() {
    // grab the form's toolbar
    IToolBarManager toolBarManager = _form.getToolBarManager();

    // add actions
    toolBarManager.add(new ParseAction());

    // update the toolbar
    _form.updateToolBar();
  }

  private void refreshFormTitle() {
    if (!_form.isDisposed()) {
      _form.setText("Content");
      if (_needsReparsing) {
        _form.setMessage("Needs reparsing", IMessageProvider.INFORMATION);
      } else {
        _form.setMessage(null, IMessageProvider.NONE);
      }
    }
  }

  public void parseProject() {

    // Bug-Fix: Refresh the workspace to prevent eclipse from showing hidden projects
    try {
      ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
    } catch (CoreException e) {
      // silently ignore...
    }

    // allow user to save the project if project is dirty
    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    if (!page.saveEditor(getEditor(), true)) {
      // user canceled operation
      return;
    }

    // Parse the project
    // ParseBundleMakerProjectRunnable.parseProject(getBundleMakerProject());
  }

  private void addBundleMakerProjectChangedListener() {
    getBundleMakerProject().addBundleMakerProjectChangedListener(new IBundleMakerProjectChangedListener() {

      @Override
      public void bundleMakerProjectChanged(BundleMakerProjectChangedEvent event) {

        if (event.getType() == Type.PROJECT_STATE_CHANGED) {
          _needsReparsing = getBundleMakerProject().getState() != BundleMakerProjectState.READY;
          Display.getDefault().syncExec(new Runnable() {
            @Override
            public void run() {
              refreshFormTitle();
            }
          });
        } else if (event.getType() == Type.PROJECT_DESCRIPTION_CHANGED) {
          _treeViewer.refresh();
        }

      }
    });

  }

  /**
   * @return
   */
  private IBundleMakerProject getBundleMakerProject() {
    return _bundleMakerProject;
  }

  /**
   * An {@link Action} that (re-)parses the BundleMaker project
   */
  class ParseAction extends Action {
    public ParseAction() {
      super("Parse");
      setImageDescriptor(BundleMakerImages.REFRESH.getImageDescriptor());
    }

    @Override
    public void run() {
      parseProject();
    }
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
}
