/**
 * 
 */
package org.bundlemaker.core.ui.projecteditor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.project.BundleMakerProjectChangedEvent;
import org.bundlemaker.core.project.BundleMakerProjectChangedEvent.Type;
import org.bundlemaker.core.project.util.BundleMakerProjectState;
import org.bundlemaker.core.project.IBundleMakerProjectChangedListener;
import org.bundlemaker.core.project.IModifiableProjectDescription;
import org.bundlemaker.core.project.IProjectContentProvider;
import org.bundlemaker.core.resource.IModuleAwareBundleMakerProject;
import org.bundlemaker.core.ui.BundleMakerImages;
import org.bundlemaker.core.ui.VerticalFormButtonBar;
import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropProvider;
import org.bundlemaker.core.ui.projecteditor.layout.FormLayoutUtils;
import org.bundlemaker.core.ui.projecteditor.newwizard.ChooseContentProviderWizard;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditorElement;
import org.bundlemaker.core.ui.projecteditor.provider.internal.ProjectEditorContributionRegistry;
import org.bundlemaker.core.ui.utils.BundleMakerProjectOpener;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.wizard.WizardDialog;
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
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.model.IWorkbenchAdapter;

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
  private final IModuleAwareBundleMakerProject _bundleMakerProject;

  /**
   * The form holding our controls
   */
  private ScrolledForm                           _form;

  private boolean                                _needsReopening = true;

  private TreeViewer                             _treeViewer;

  private Button                                 _editButton;

  private Button                                 _removeButton;

  private Button                                 _moveDownButton;

  private Button                                 _moveUpButton;

  private Button                                 _parseProjectButton;

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

    // getEditor().addPropertyListener(new IPropertyListener() {
    //
    // @Override
    // public void propertyChanged(Object source, int propId) {
    // if (propId == IEditorPart.PROP_DIRTY) {
    // dirty = !dirty;
    // }
    // }
    // });

    // HIER KOMMT DER EDITOR !!!
    createEditorControls(mform);

    // Add actions to toolbar
    updateToolBar();

    refreshFormTitle();
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

    _treeViewer.setContentProvider(new ProjectEditorTreeViewerContentProvider(getProjectEditorContributionRegistry()));
    configureTreeDragAndDrop();
    createTreeColumns();
    createMenuManager();

    final Shell shell = client.getShell();

    // Set tree content
    _treeViewer.setInput(_bundleMakerProject);

    // add double click listener that edits the selected item
    _treeViewer.addDoubleClickListener(new IDoubleClickListener() {

      @Override
      public void doubleClick(DoubleClickEvent event) {
        editContent(shell);
      }
    });

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
        removeContent(shell);
      }
    });
    _moveUpButton = buttonBar.newButton("Up", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        moveProjectContentProviderUp();
      }

    });
    _moveDownButton = buttonBar.newButton("Down", new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        moveProjectContentProviderDown();
      }
    });

    _parseProjectButton = buttonBar.newButton("Open project", new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        parseProject();
      }

    });
    GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
    layoutData.grabExcessVerticalSpace = true;
    layoutData.verticalAlignment = SWT.BOTTOM;
    _parseProjectButton.setImage(BundleMakerImages.BUNDLEMAKER_PARSE_PROJECT.getImage());
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

  /**
   * @return
   */
  private ProjectEditorContributionRegistry getProjectEditorContributionRegistry() {
    return Activator.getDefault()
        .getProjectEditorContributionRegistry();
  }

  /**
   * 
   */
  private void createMenuManager() {

    // Create menu manager
    MenuManager menuMgr = new MenuManager();

    // Create menu for tree viewer
    Menu menu = menuMgr.createContextMenu(_treeViewer.getControl());
    menuMgr.addMenuListener(new IMenuListener() {
      @Override
      public void menuAboutToShow(IMenuManager manager) {
        // (re-)populate context menu each time the menu is shown
        fillContextMenu(manager);
      }
    });

    menuMgr.setRemoveAllWhenShown(true);
    _treeViewer.getControl().setMenu(menu);
  }

  protected void fillContextMenu(IMenuManager menuManager) {

    List<IProjectContentProviderEditorElement> selectedTreeViewerElements =
        getSelectedObjects(_treeViewer.getSelection(), IProjectContentProviderEditorElement.class);

    Set<IProjectContentProviderEditor> contentProviderEditors = getProjectEditorContributionRegistry()
        .getContentProviderEditors();

    for (IProjectContentProviderEditor iProjectContentProviderEditor : contentProviderEditors) {
      List<IAction> contextMenuActions = iProjectContentProviderEditor.getContextMenuActions(_bundleMakerProject,
          selectedTreeViewerElements);

      if (contextMenuActions != null) {
        for (IAction iAction : contextMenuActions) {
          menuManager.add(iAction);
        }
      }
    }
  }

  protected Map<IProjectContentProviderEditor, List<Object>> getSelectedElementsByProvider() {
    Map<IProjectContentProviderEditor, List<Object>> result = new Hashtable<IProjectContentProviderEditor, List<Object>>();
    List<ProjectEditorTreeViewerElement> selectedTreeViewerElements = getSelectedTreeViewerElements();

    for (ProjectEditorTreeViewerElement selectedElement : selectedTreeViewerElements) {
      IProjectContentProviderEditor providingEditor = selectedElement.getProvidingEditor();
      List<Object> objects = result.get(providingEditor);
      if (objects == null) {
        objects = new LinkedList<Object>();
        result.put(providingEditor, objects);
      }
      objects.add(selectedElement.getElement());
    }

    return result;

  }

  private void configureTreeDragAndDrop() {
    ProjectEditorContributionRegistry projectEditorDndProviderRegistry = getProjectEditorContributionRegistry();

    Set<IProjectEditorDropProvider> registeredDndProviders = projectEditorDndProviderRegistry
        .getDropProviders();

    ProjectEditorTreeViewerDropAdapter adapter = new ProjectEditorTreeViewerDropAdapter(_treeViewer,
        _bundleMakerProject, registeredDndProviders) {

      @Override
      protected void afterDrop(Object target) {

        // refresh the tree
        _treeViewer.refresh(null);

        // make the new object visible
        _treeViewer.expandToLevel(target, 1);

        // make sure tree viewer has focus
        _treeViewer.getControl().getParent().setFocus();
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
    List<ProjectEditorTreeViewerElement> selectedTreeViewerElements = getSelectedTreeViewerElements();

    // Add... Button is always enabled as long as their is a "IProjectContentProviderFactory-Editor" available
    // Edit... is enabled only when there is exactly ONE selected object AND it's providing ProviderEditor returns true
    // on canEdit(Object)

    // Remove is enabled when at least one element is selected and either...
    // ...all of the elements are IProjectContentProviders (top level types) OR
    // ...all of the elements are children of the SAME TYPE of IProjectContentProviders
    // Up/Down are enabled when at least on IProjectContentProvider is selected (and no other element)

    boolean moveButtonsEnabled;
    boolean editButtonEnabled;
    boolean removeButtonEnabled;

    if (selectedTreeViewerElements.isEmpty()) {
      moveButtonsEnabled = false;
      editButtonEnabled = false;
      removeButtonEnabled = false;
    } else {
      moveButtonsEnabled = true;
      removeButtonEnabled = true;
      editButtonEnabled = selectedTreeViewerElements.size() == 1;
      for (ProjectEditorTreeViewerElement projectEditorTreeViewerElement : selectedTreeViewerElements) {

        final boolean isProjectContentProviderSelected = (projectEditorTreeViewerElement.getElement() instanceof IProjectContentProvider);

        if (!isProjectContentProviderSelected) {
          // move and remove buttons are only enable if only IProjectContentProvider elements are selected
          moveButtonsEnabled = false;

          if (removeButtonEnabled) {
            removeButtonEnabled = projectEditorTreeViewerElement.getProvidingEditor().canRemove(
                projectEditorTreeViewerElement.getElement());
          }
        }

        if (editButtonEnabled) {
          editButtonEnabled = projectEditorTreeViewerElement.getProvidingEditor().canEdit(
              projectEditorTreeViewerElement.getElement());
        }

      }
    }

    _moveDownButton.setEnabled(moveButtonsEnabled);
    _moveUpButton.setEnabled(moveButtonsEnabled);
    _removeButton.setEnabled(removeButtonEnabled);
    _editButton.setEnabled(editButtonEnabled);

  }

  /**
   * @param shell
   */
  protected void addContent(Shell shell) {

    // Create the wizard presenting the available content provider wizards
    ProjectEditorContributionRegistry registry = getProjectEditorContributionRegistry();
    ChooseContentProviderWizard wizard = new ChooseContentProviderWizard(_bundleMakerProject, registry);

    // create the WizardDialog
    WizardDialog dialog = new WizardDialog(shell, wizard);
    dialog.open();

    // refresh Tree
    _treeViewer.refresh(null);
  }

  /**
   * @param shell
   */
  protected void editContent(Shell shell) {

    IStructuredSelection treeViewerSelection = getTreeViewerSelection();
    if (treeViewerSelection.isEmpty()) {
      return;
    }

    ProjectEditorTreeViewerElement element = (ProjectEditorTreeViewerElement) treeViewerSelection.getFirstElement();
    boolean result = element.getProvidingEditor().edit(shell, _bundleMakerProject, element.getProjectContentProvider(),
        element.getElement());

    if (!result) {
      return;
    }

    _treeViewer.refresh(null);

  }

  protected void moveProjectContentProviderUp() {
    moveProjectContentProvider(true);

  }

  protected void moveProjectContentProviderDown() {
    moveProjectContentProvider(false);
  }

  protected void moveProjectContentProvider(boolean up) {
    List<ProjectEditorTreeViewerElement> selectedTreeViewerElements = getSelectedTreeViewerElements();

    List<IProjectContentProvider> contentProviders = new LinkedList<IProjectContentProvider>();

    for (ProjectEditorTreeViewerElement projectEditorTreeViewerElement : selectedTreeViewerElements) {
      if (projectEditorTreeViewerElement.getElement() instanceof IProjectContentProvider) {
        IProjectContentProvider projectContentProvider = (IProjectContentProvider) projectEditorTreeViewerElement
            .getElement();
        contentProviders.add(projectContentProvider);
      }
    }

    if (!contentProviders.isEmpty()) {
      IModifiableProjectDescription modifiableProjectDescription = getBundleMakerProject()
          .getModifiableProjectDescription();

      if (up) {
        modifiableProjectDescription.moveUpContentProviders(contentProviders);
      } else {
        modifiableProjectDescription.moveDownContentProviders(contentProviders);
      }
    }

    _treeViewer.refresh(null);

  }

  /**
   * 
   */
  protected void removeContent(final Shell shell) {

    List<ProjectEditorTreeViewerElement> selectedObjects = getSelectedTreeViewerElements();

    for (ProjectEditorTreeViewerElement projectEditorTreeViewerElement : selectedObjects) {
      if (projectEditorTreeViewerElement.getElement() instanceof IProjectContentProvider) {
        IProjectContentProvider provider = (IProjectContentProvider) projectEditorTreeViewerElement.getElement();
        _bundleMakerProject.getModifiableProjectDescription().removeContentProvider(provider);
      } else {
        projectEditorTreeViewerElement.getProvidingEditor().remove(shell, _bundleMakerProject,
            projectEditorTreeViewerElement.getProjectContentProvider(),
            projectEditorTreeViewerElement.getElement());

      }
    }

    _treeViewer.refresh(null);

  }

  protected List<ProjectEditorTreeViewerElement> getSelectedTreeViewerElements() {
    return getSelectedObjects(_treeViewer.getSelection(), ProjectEditorTreeViewerElement.class);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static <T> List<T> getSelectedObjects(ISelection selection, Class<T> type) {
    final List<T> result = new LinkedList<T>();
    if (selection instanceof IStructuredSelection) {
      IStructuredSelection structuredSelection = (IStructuredSelection) selection;
      Iterator iterator = structuredSelection.iterator();
      while (iterator.hasNext()) {
        Object element = iterator.next();
        if (type.isInstance(element)) {
          result.add((T) element);
        }
      }
    }
    return result;
  }

  protected IStructuredSelection getTreeViewerSelection() {
    return (IStructuredSelection) _treeViewer.getSelection();
  }

  /**
   * 
   */
  private void createTreeColumns() {
    Tree tree = _treeViewer.getTree();
    TreeColumnLayout layout = new TreeColumnLayout();
    TreeViewerColumn column = new TreeViewerColumn(_treeViewer, SWT.NONE);
    column.setLabelProvider(new ProjectEditorTreeViewerResourceLabelProvider());

    column.getColumn().setResizable(true);
    column.getColumn().setMoveable(true);
    column.getColumn().setText("Resource");
    layout.setColumnData(column.getColumn(), new ColumnWeightData(80));

    column = new TreeViewerColumn(_treeViewer, SWT.NONE);
    // column.setLabelProvider(new BundleMakerProjectDescriptionColumnLabelProvider(1));
    // column.setEditingSupport(FileBasedContentEditingSupport.newEditingSupportForAnalyzeResource(this, _treeViewer));
    column.setLabelProvider(new ProjectEditorTreeViewerAnalyzeLabelProvider(0));
    column.setEditingSupport(new ProjectEditorTreeViewerEditingSupport(_treeViewer, true));
    column.getColumn().setResizable(true);
    column.getColumn().setMoveable(true);
    column.getColumn().setText("Analyze");
    column.getColumn().setAlignment(SWT.CENTER);
    layout.setColumnData(column.getColumn(), new ColumnWeightData(10));

    column = new TreeViewerColumn(_treeViewer, SWT.NONE);
    // column.setLabelProvider(new BundleMakerProjectDescriptionColumnLabelProvider(2));
    // column.setEditingSupport(FileBasedContentEditingSupport.newEditingSupportForAnalyzeSources(this, _treeViewer));
    column.setLabelProvider(new ProjectEditorTreeViewerAnalyzeLabelProvider(1));
    column.setEditingSupport(new ProjectEditorTreeViewerEditingSupport(_treeViewer, false));
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
      if (_needsReopening) {
        _form.setMessage("Needs re-opening", IMessageProvider.INFORMATION);
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
    BundleMakerProjectOpener.openProject(getBundleMakerProject());
  }

  private void addBundleMakerProjectChangedListener() {
    getBundleMakerProject().addBundleMakerProjectChangedListener(new IBundleMakerProjectChangedListener() {

      @Override
      public void bundleMakerProjectChanged(BundleMakerProjectChangedEvent event) {
        if (event.getType() == Type.PROJECT_STATE_CHANGED || event.getType() == Type.PROJECT_DESCRIPTION_RECOMPUTED) {
          _needsReopening = getBundleMakerProject().getState() != BundleMakerProjectState.READY;
          Display.getDefault().syncExec(new Runnable() {
            @Override
            public void run() {
              refreshFormTitle();
            }
          });
        }
      }
    });

  }

  /**
   * @return
   */
  private IModuleAwareBundleMakerProject getBundleMakerProject() {
    return _bundleMakerProject;
  }

  /**
   * An {@link Action} that (re-)parses the BundleMaker project
   */
  class ParseAction extends Action {
    public ParseAction() {
      super("Open");
      setImageDescriptor(BundleMakerImages.BUNDLEMAKER_PARSE_PROJECT.getImageDescriptor());
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
