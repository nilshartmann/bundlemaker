package org.bundlemaker.core.ui.stage.view;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.IVirtualRoot;
import org.bundlemaker.core.selection.IArtifactSelection;
import org.bundlemaker.core.selection.IArtifactSelectionListener;
import org.bundlemaker.core.selection.Selection;
import org.bundlemaker.core.selection.stage.ArtifactStageAddMode;
import org.bundlemaker.core.selection.stage.ArtifactStageChangedEvent;
import org.bundlemaker.core.selection.stage.IArtifactStageChangeListener;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeContentProvider;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeLabelProvider;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeViewerSorter;
import org.bundlemaker.core.ui.stage.actions.AddModeActionGroup;
import org.bundlemaker.core.ui.stage.actions.StageIcons;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class StageView extends ViewPart {

  /**
   * The ID of the view as specified by the extension.
   */
  public static final String                 ID                           = "org.bundlemaker.core.ui.stage.StageView";

  protected final List<IBundleMakerArtifact> EMPTY_ARTIFACTS              = Collections.emptyList();

  private final IArtifactSelectionListener   _artifactSelectionListener   = new IArtifactSelectionListener() {
                                                                            @Override
                                                                            public void artifactSelectionChanged(
                                                                                IArtifactSelection event) {
                                                                              refreshTreeContent(event);

                                                                            }
                                                                          };

  private final IArtifactStageChangeListener _artifactStageChangeListener = new IArtifactStageChangeListener() {

                                                                            @Override
                                                                            public void artifactStateChanged(
                                                                                ArtifactStageChangedEvent event) {
                                                                              artifactStageConfigurationChanged();
                                                                            }
                                                                          };

  private List<IBundleMakerArtifact>         _effectiveSelectedArtifacts  = Collections.emptyList();

  private TreeViewer                         _treeViewer;

  private DrillDownAdapter                   drillDownAdapter;

  private Action                             _autoExpandAction;

  private ClearStageAction                   _clearStageAction;

  private RemoveArtifactsAction              _removeArtifactsAction;

  private boolean                            _autoExpand                  = true;

  private AddModeActionGroup                 _addModeActionGroup;

  /**
   * The constructor.
   */
  public StageView() {
  }

  /**
   * This is a callback that will allow us to create the viewer and initialize it.
   */
  @Override
  public void createPartControl(Composite parent) {

    _treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

    _treeViewer.setUseHashlookup(true);
    _treeViewer.setContentProvider(new ArtifactTreeContentProvider(true));
    _treeViewer.getTree().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
    _treeViewer.setSorter(new ArtifactTreeViewerSorter());

    _treeViewer.setContentProvider(new ArtifactStageContentProvider());
    _treeViewer.setLabelProvider(new StageViewLabelProvider());

    _treeViewer.addDoubleClickListener(new IDoubleClickListener() {
      @Override
      public void doubleClick(DoubleClickEvent event) {

        //
        IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();

        if (structuredSelection.size() == 1) {
          Object object = structuredSelection.getFirstElement();
          _treeViewer.setExpandedState(object, !_treeViewer.getExpandedState(object));
        }
      }
    });
    int operations = DND.DROP_MOVE;
    Transfer[] transferTypes = new Transfer[] { LocalSelectionTransfer.getTransfer() };
    // _tre|eViewer.addDragSupport(operations, transferTypes, new ArtifactTreeDragAdapter(treeViewer));
    _treeViewer.addDropSupport(operations, transferTypes, new ArtifactStageTreeDropAdapter(_treeViewer));

    // viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL |
    // SWT.V_SCROLL);
    // drillDownAdapter = new DrillDownAdapter(viewer);
    // viewer.setContentProvider(new ViewContentProvider());
    // viewer.setLabelProvider(new ViewLabelProvider());
    // viewer.setSorter(new NameSorter());
    // viewer.setInput(getViewSite());

    makeActions();
    hookContextMenu();
    hookDoubleClickAction();
    contributeToActionBars();

    Selection.instance().getArtifactStage().addArtifactStageChangeListener(_artifactStageChangeListener);

    Selection.instance().getArtifactSelectionService()
        .addArtifactSelectionListener(Selection.ARTIFACT_STAGE_SELECTION_ID, _artifactSelectionListener);

    IArtifactSelection selection = Selection.instance().getArtifactSelectionService()
        .getSelection(Selection.ARTIFACT_STAGE_SELECTION_ID);

    refreshTreeContent(selection);

  }

  private void refreshTreeContent(IArtifactSelection event) {
    if (event == null) {
      _effectiveSelectedArtifacts = Collections.emptyList();
    } else {
      _effectiveSelectedArtifacts = event.getEffectiveSelectedArtifacts();
    }
    //
    // set redraw to false
    _treeViewer.getTree().setRedraw(false);

    if (_effectiveSelectedArtifacts.size() > 0) {

      Map<IBundleMakerArtifact, ArtifactHolder> artifactHolderCache = new Hashtable<IBundleMakerArtifact, ArtifactHolder>();

      ArtifactHolder root = null;

      for (IBundleMakerArtifact artifact : _effectiveSelectedArtifacts) {
        if (artifactHolderCache.containsKey(artifact)) {
          // no need to walk up further, as artifact including it's parent already
          // have been added
          continue;
        }

        ArtifactHolder currentArtifactHolder = new ArtifactHolder(artifact);

        if (root == null && artifact.isInstanceOf(IRootArtifact.class)) {
          root = currentArtifactHolder;
        }

        artifactHolderCache.put(artifact, currentArtifactHolder);
        IBundleMakerArtifact parent = artifact.getParent();
        while (parent != null) {

          ArtifactHolder parentHolder = artifactHolderCache.get(parent);
          if (parentHolder != null) {
            parentHolder.addChild(currentArtifactHolder);
            currentArtifactHolder.setParent(parentHolder);

            // no need to walk up the tree any longer
            break;
          } else {
            parentHolder = new ArtifactHolder(parent);
            parentHolder.addChild(currentArtifactHolder);
            currentArtifactHolder.setParent(parentHolder);
            artifactHolderCache.put(parent, parentHolder);
          }

          if (root == null && parent.isInstanceOf(IRootArtifact.class)) {
            root = parentHolder;
          }

          currentArtifactHolder = parentHolder;

          parent = parent.getParent();
        }
      }
      System.out.println("root: " + root);
      // set the artifacts
      ArtifactHolder virtualRootHolder = new ArtifactHolder(null);
      virtualRootHolder.addChild(root);
      _treeViewer.setInput(virtualRootHolder);
    }

    // set empty list
    else {
      _treeViewer.setInput(_effectiveSelectedArtifacts);
    }

    if (isAutoExpand()) {
      _treeViewer.expandAll();
    }

    // redraw again
    _treeViewer.getTree().setRedraw(true);

    refreshEnablement();
  }

  private void hookContextMenu() {
    MenuManager menuMgr = new MenuManager("#PopupMenu");
    menuMgr.setRemoveAllWhenShown(true);
    menuMgr.addMenuListener(new IMenuListener() {
      @Override
      public void menuAboutToShow(IMenuManager manager) {
        StageView.this.fillContextMenu(manager);
      }
    });
    Menu menu = menuMgr.createContextMenu(_treeViewer.getControl());
    _treeViewer.getControl().setMenu(menu);
    getSite().registerContextMenu(menuMgr, _treeViewer);
  }

  private void contributeToActionBars() {
    IActionBars bars = getViewSite().getActionBars();
    fillLocalPullDown(bars.getMenuManager());
    fillLocalToolBar(bars.getToolBarManager());
  }

  private void fillLocalPullDown(IMenuManager manager) {

    _addModeActionGroup.fill(manager);

    manager.add(new Separator());
    manager.add(_removeArtifactsAction);
    manager.add(_clearStageAction);

    manager.add(new Separator());
    manager.add(_autoExpandAction);
    // manager.add(action1);
    // manager.add(action2);
  }

  private void fillContextMenu(IMenuManager manager) {
    IStructuredSelection selection = (IStructuredSelection) _treeViewer.getSelection();
    _removeArtifactsAction.setEnabled(selection != null && !selection.isEmpty());
    manager.add(_removeArtifactsAction);

    // manager.add(action1);
    // manager.add(action2);
    // manager.add(new Separator());
    // drillDownAdapter.addNavigationActions(manager);
    // // Other plug-ins can contribute there actions here
    // manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
  }

  private void fillLocalToolBar(IToolBarManager manager) {

    manager.add(_removeArtifactsAction);
    manager.add(_clearStageAction);
    manager.add(new Separator());

    _addModeActionGroup.fill(manager);
    // manager.add(_pinStageAction);
    // drillDownAdapter.addNavigationActions(manager);
  }

  private void makeActions() {
    _autoExpandAction = new AutoExpandAction();

    _clearStageAction = new ClearStageAction();
    _removeArtifactsAction = new RemoveArtifactsAction();

    _addModeActionGroup = new AddModeActionGroup();

    _treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        refreshEnablement();

      }
    });

    refreshEnablement();

  }

  @Override
  public void dispose() {
    Selection.instance().getArtifactStage().removeArtifactStageChangeListener(_artifactStageChangeListener);
    Selection.instance().getArtifactSelectionService().removeArtifactSelectionListener(_artifactSelectionListener);
    super.dispose();
  }

  protected void refreshEnablement() {
    IStructuredSelection selection = (IStructuredSelection) _treeViewer.getSelection();
    _removeArtifactsAction.setEnabled(!selection.isEmpty());
    _clearStageAction.setEnabled(!_effectiveSelectedArtifacts.isEmpty());
  }

  /**
   * @return the autoExpand
   */
  public boolean isAutoExpand() {
    return _autoExpand;
  }

  /**
   * @param autoExpand
   *          the autoExpand to set
   */
  public void setAutoExpand(boolean autoExpand) {
    _autoExpand = autoExpand;

    if (_autoExpand) {
      _treeViewer.expandAll();
    }
  }

  private void hookDoubleClickAction() {
    _treeViewer.addDoubleClickListener(new IDoubleClickListener() {
      @Override
      public void doubleClick(DoubleClickEvent event) {
        // doubleClickAction.run();
      }
    });
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    _treeViewer.getControl().setFocus();
  }

  protected void artifactStageConfigurationChanged() {
    _addModeActionGroup.update();
  }

  protected ArtifactStageAddMode getAddMode() {
    return Selection.instance().getArtifactStage().getAddMode();
  }

  class AutoExpandAction extends Action {
    public AutoExpandAction() {
      super("Auto Expand", IAction.AS_CHECK_BOX);
      setToolTipText("Auto expand selection tree");
      // setImageDescriptor(TransformationHistoryImages.PIN_SELECTION.getImageDescriptor());
      update();
    }

    @Override
    public void run() {
      setAutoExpand(isChecked());

    }

    public void update() {
      setChecked(isAutoExpand());
    }
  }

  class ClearStageAction extends Action {
    ClearStageAction() {
      super("Clear Stage", IAction.AS_PUSH_BUTTON);

      setImageDescriptor(StageIcons.CLEAR_STAGE.getImageDescriptor());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
      Selection.instance().getArtifactStage().setStagedArtifacts(null);
    }
  }

  class RemoveArtifactsAction extends Action {
    RemoveArtifactsAction() {
      super("Remove from Stage", IAction.AS_PUSH_BUTTON);

      setImageDescriptor(StageIcons.REMOVE_FROM_STAGE.getImageDescriptor());
    }

    @Override
    public void run() {
      IStructuredSelection selection = (IStructuredSelection) _treeViewer.getSelection();
      if (selection == null || selection.isEmpty()) {
        return;
      }

      LinkedHashSet<IBundleMakerArtifact> artifacts = new LinkedHashSet<IBundleMakerArtifact>();
      @SuppressWarnings("unchecked")
      Iterator<IBundleMakerArtifact> iterator = selection.iterator();
      while (iterator.hasNext()) {
        IBundleMakerArtifact artifact = iterator.next();
        artifacts.add(artifact);
        artifacts.addAll(artifact.getChildren());
      }

      Selection.instance().getArtifactStage().removeStagedArtifacts(artifacts);

    }
  }

  class StageViewLabelProvider extends ArtifactTreeLabelProvider implements IColorProvider {

    @Override
    public Color getForeground(Object element) {

      boolean stagedArtifact = false;

      if (element instanceof ArtifactHolder) {
        IBundleMakerArtifact bundleMakerArtifact = ((ArtifactHolder) element).getArtifact();
        if (bundleMakerArtifact instanceof IVirtualRoot) {
          bundleMakerArtifact = bundleMakerArtifact.getRoot();

        }
        // System.out.println("Artifact: " + bundleMakerArtifact);
        // System.out.println("  STAGED ARTIFACTS: " + _effectiveSelectedArtifacts);
        stagedArtifact = _effectiveSelectedArtifacts.contains(bundleMakerArtifact);
      }

      //
      if (stagedArtifact) {
        return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
      }

      //
      return Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
    }

    @Override
    public String getText(Object obj) {
      if (obj instanceof ArtifactHolder) {
        obj = ((ArtifactHolder) obj).getArtifact();

      }
      return super.getText(obj);
    }

    @Override
    public Image getImage(Object obj) {
      if (obj instanceof ArtifactHolder) {
        obj = ((ArtifactHolder) obj).getArtifact();
      }
      return super.getImage(obj);
    }

    @Override
    public Color getBackground(Object element) {
      return null;
    }

  }
}