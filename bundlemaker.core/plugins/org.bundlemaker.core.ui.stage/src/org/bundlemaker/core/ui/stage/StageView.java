package org.bundlemaker.core.ui.stage;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeViewerFactory;
import org.bundlemaker.core.ui.artifact.tree.VisibleArtifactsFilter;
import org.bundlemaker.core.ui.event.selection.IArtifactSelection;
import org.bundlemaker.core.ui.event.selection.IArtifactSelectionListener;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;
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

  private Action                             _pinStageAction;

  private Action                             _autoExpandAction;

  private boolean                            _stagePinned    = false;

  private boolean                            _autoExpand     = true;

  private boolean                            _autoAnalyze    = true;

  private Action                             _analyzeAction;

  private Action                             _autoAnalyzeAction;

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

    Selection.instance().getArtifactSelectionService()
        .addArtifactSelectionListener(Selection.PROJECT_EXLPORER_SELECTION_ID, new IArtifactSelectionListener() {

          @Override
          public void artifactSelectionChanged(IArtifactSelection event) {

            if (StageSelection.isAnalyzeCommandSelectionProvider(event)) {
              // User has explicitly chosen to analyze the selected artifacts,
              // regardless what settings have been made in the Stage View

              setStagedArtifacts(event.getSelectedArtifacts(), true);
            } else {
              projectExplorerSelectionChanged(event);
            }
          }
        });

    IArtifactSelection selection = Selection.instance().getArtifactSelectionService()
        .getSelection(Selection.PROJECT_EXLPORER_SELECTION_ID);
    projectExplorerSelectionChanged(selection);
  }

  protected void projectExplorerSelectionChanged(IArtifactSelection newSelection) {
    // Selection in Project Explorer changed

    if (isStagePinned() && hasStagedArtifacts()) {
      // ignore
      return;
    }

    if (newSelection == null) {
      setStagedArtifacts(EMPTY_ARTIFACTS, isAutoAnalyze());
    } else {
      // publish changes if in auto-analyze mode or when there have been no
      // staged artifacts before (convenience)
      boolean publishChanges = isAutoAnalyze() || !hasStagedArtifacts();

      setStagedArtifacts(newSelection.getSelectedArtifacts(), publishChanges);
    }
  }

  protected boolean hasStagedArtifacts() {

    return getStagedArtifacts().size() > 0;
  }

  protected List<IBundleMakerArtifact> getStagedArtifacts() {

    if (_stagedArtifacts == null) {
      return EMPTY_ARTIFACTS;
    }

    return _stagedArtifacts;

  }

  private List<IBundleMakerArtifact> _stagedArtifacts;

  private void setStagedArtifacts(List<IBundleMakerArtifact> visibleArtifacts, boolean publishChanges) {
    Assert.isNotNull(visibleArtifacts);

    _stagedArtifacts = visibleArtifacts;

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

    //
    if (publishChanges) {
      publishStagedArtifacts();
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
    // manager.add(action1);
    // manager.add(new Separator());
    // manager.add(action2);
  }

  private void fillContextMenu(IMenuManager manager) {
    // manager.add(action1);
    // manager.add(action2);
    // manager.add(new Separator());
    // drillDownAdapter.addNavigationActions(manager);
    // // Other plug-ins can contribute there actions here
    // manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
  }

  private void fillLocalToolBar(IToolBarManager manager) {
    manager.add(_analyzeAction);
    manager.add(_autoAnalyzeAction);
    manager.add(new Separator());
    manager.add(_pinStageAction);
    manager.add(_autoExpandAction);
    // drillDownAdapter.addNavigationActions(manager);
  }

  private void makeActions() {
    _pinStageAction = new PinSelectionAction();
    _autoExpandAction = new AutoExpandAction();
    _analyzeAction = new AnalyzeAction();
    _autoAnalyzeAction = new AutoAnalyzeAction();
  }

  /**
   * @return the selectionPinnned
   */
  public boolean isStagePinned() {
    return _stagePinned;
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

  /**
   * @param selectionPinnned
   *          the selectionPinnned to set
   */
  public void setStagePinned(boolean selectionPinnned) {
    _stagePinned = selectionPinnned;
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

  class PinSelectionAction extends Action {
    public PinSelectionAction() {
      super("Pin Stage", IAction.AS_CHECK_BOX);
      setToolTipText("Pin Stage");
      // setImageDescriptor(TransformationHistoryImages.PIN_SELECTION.getImageDescriptor());
      update();
    }

    @Override
    public void run() {
      setStagePinned(isChecked());
    }

    public void update() {
      setChecked(isStagePinned());
    }

  }

  /**
   * @return the autoAnalyze
   */
  public boolean isAutoAnalyze() {
    return _autoAnalyze;
  }

  /**
   * @param autoAnalyze
   *          the autoAnalyze to set
   */
  public void setAutoAnalyze(boolean autoAnalyze) {
    _autoAnalyze = autoAnalyze;
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

  class AutoAnalyzeAction extends Action {
    public AutoAnalyzeAction() {
      super("Auto Analyze", IAction.AS_CHECK_BOX);
      setToolTipText("Auto Analyze staged artifacts");
      // setImageDescriptor(TransformationHistoryImages.PIN_SELECTION.getImageDescriptor());
      update();
    }

    @Override
    public void run() {
      setAutoAnalyze(isChecked());

    }

    public void update() {
      setChecked(isAutoAnalyze());
    }
  }

  class AnalyzeAction extends Action {
    public AnalyzeAction() {
      super("Analyze");
    }

    @Override
    public void run() {
      publishStagedArtifacts();
    }
  }

  protected void publishStagedArtifacts() {
    IStructuredSelection selection = (IStructuredSelection) _treeViewer.getSelection();
    List<IBundleMakerArtifact> artifacts;

    if (selection.isEmpty()) {
      artifacts = getStagedArtifacts();
    } else {
      artifacts = new LinkedList<IBundleMakerArtifact>();
      Iterator<?> iterator = selection.iterator();
      while (iterator.hasNext()) {
        Object o = iterator.next();
        if (o instanceof IBundleMakerArtifact) {
          artifacts.add((IBundleMakerArtifact) o);
        }
      }
    }

    System.out.println("Set MAIN_SELECTION: " + artifacts);
    Selection.instance().getArtifactSelectionService().setSelection(Selection.MAIN_ARTIFACT_SELECTION_ID, //
        StageSelection.STAGE_VIEW_SELECTION_PROVIDER_ID, //
        artifacts);

  }

}