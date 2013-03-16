package org.bundlemaker.core.ui.stage.view;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IVirtualRoot;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeLabelProvider;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeViewerFactory;
import org.bundlemaker.core.ui.artifact.tree.VisibleArtifactsFilter;
import org.bundlemaker.core.ui.stage.ArtifactStage;
import org.bundlemaker.core.ui.stage.ArtifactStageAddMode;
import org.bundlemaker.core.ui.stage.ArtifactStageChangedEvent;
import org.bundlemaker.core.ui.stage.IArtifactStageChangeListener;
import org.bundlemaker.core.ui.stage.actions.AddModeActionGroup;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
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
  public static final String                 ID              = "org.bundlemaker.core.ui.stage.StageView";

  protected final List<IBundleMakerArtifact> EMPTY_ARTIFACTS = Collections.emptyList();

  private TreeViewer                         _treeViewer;

  private DrillDownAdapter                   drillDownAdapter;

  private Action                             _autoExpandAction;

  private ClearStageAction                   _clearStageAction;

  private RemoveArtifactsAction              _removeArtifactsAction;

  private boolean                            _autoExpand     = true;

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

    _treeViewer = ArtifactTreeViewerFactory.createDefaultArtifactTreeViewer(parent);
    _treeViewer.setLabelProvider(new StageViewLabelProvider());

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

    ArtifactStage.instance().addArtifactStageChangeListener(new IArtifactStageChangeListener() {

      @Override
      public void artifactStateChanged(ArtifactStageChangedEvent event) {
        if (event.getReason().hasContentChanged()) {
          refreshTreeContent();
        } else {
          artifactStageConfigurationChanged();
        }
      }
    });

    refreshTreeContent();

  }

  private void refreshTreeContent() {

    List<IBundleMakerArtifact> visibleArtifacts = ArtifactStage.instance().getStagedArtifacts();

    //
    VisibleArtifactsFilter result = null;

    // set redraw to false
    _treeViewer.getTree().setRedraw(false);

    if (visibleArtifacts.size() > 0) {
      // set the artifacts
      IBundleMakerArtifact artifact = visibleArtifacts.get(0);
      _treeViewer.setInput(artifact.getRoot());

      // set the filter
      result = new VisibleArtifactsFilter(visibleArtifacts);
      _treeViewer.setFilters(new ViewerFilter[] { result });
    }

    // set empty list
    else {
      _treeViewer.setInput(Collections.emptyList());
    }

    // redraw again
    _treeViewer.getTree().setRedraw(true);

    if (isAutoExpand()) {
      _treeViewer.expandAll();
    }
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

  protected ArtifactStage getArtifactStage() {
    return ArtifactStage.instance();
  }

  protected void artifactStageConfigurationChanged() {
    _addModeActionGroup.update();
  }

  protected ArtifactStageAddMode getAddMode() {
    return getArtifactStage().getAddMode();
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
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
      getArtifactStage().setStagedArtifacts(null);
    }
  }

  class RemoveArtifactsAction extends Action {
    RemoveArtifactsAction() {
      super("Remove from Stage", IAction.AS_PUSH_BUTTON);
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

      getArtifactStage().removeStagedArtifacts(artifacts);

    }
  }

  class StageViewLabelProvider extends ArtifactTreeLabelProvider implements IColorProvider {

    @Override
    public Color getForeground(Object element) {

      boolean stagedArtifact = false;

      if (element instanceof IBundleMakerArtifact) {
        IBundleMakerArtifact bundleMakerArtifact = (IBundleMakerArtifact) element;
        if (bundleMakerArtifact instanceof IVirtualRoot) {
          bundleMakerArtifact = bundleMakerArtifact.getRoot();

        }
        System.out.println("Artifact: " + bundleMakerArtifact);
        System.out.println("  STAGED ARTIFACTS: " + getArtifactStage().getStagedArtifacts());
        stagedArtifact = getArtifactStage().isStaged(bundleMakerArtifact);
      }

      //
      if (stagedArtifact) {
        return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
      }

      //
      return Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
    }

    @Override
    public Color getBackground(Object element) {
      return null;
    }

  }
}