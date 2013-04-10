package org.bundlemaker.core.ui.experimental.dependencytable.threeway;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.selection.Selection;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeContentProvider;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeLabelProvider;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeViewerFactory;
import org.bundlemaker.core.ui.artifact.tree.VisibleArtifactsFilter;
import org.bundlemaker.core.ui.view.dependencytree.DefaultExpandStrategy;
import org.bundlemaker.core.ui.view.dependencytree.Helper;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPartSite;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class XRefComposite extends Composite {

  /** the from tree viewer */
  private TreeViewer                    _fromTreeViewer;

  /** - */
  private VisibleArtifactsFilter        _fromTreeVisibleArtifactsFilter;

  /** the to tree viewer */
  private TreeViewer                    _centerViewer;

  /** the to tree viewer */
  private TreeViewer                    _toTreeViewer;

  /** Label that displays details about the current selection */
  private Label                         _detailsLabel;

  /** - */
  private VisibleArtifactsFilter        _toTreeVisibleArtifactsFilter;

  /** - */
  private String                        _providerId;

  /** - */
  private DefaultExpandStrategy         _expandStrategy;

  private XRefTreeArtifactLabelProvider _artifactLabelProvider;

  private IWorkbenchPartSite            _site;

  /**
   * <p>
   * Creates a new instance of type {@link XRefComposite}.
   * </p>
   * 
   * @param parent
   * @param iWorkbenchPartSite
   */
  public XRefComposite(Composite parent, String providerId, IWorkbenchPartSite iWorkbenchPartSite) {
    super(parent, SWT.NONE);

    Assert.isNotNull(providerId);

    _providerId = providerId;
    _site = iWorkbenchPartSite;

    init();
  }

  public void setSelectedArtifacts(List<IBundleMakerArtifact> artifacts) {
    if (artifacts.isEmpty()) {
      return;
    }

    IRootArtifact rootArtifact = artifacts.get(0).getRoot();

    // Set Tree Viewer input
    _fromTreeViewer.setInput(rootArtifact);
    _centerViewer.setInput(rootArtifact);
    _toTreeViewer.setInput(rootArtifact);

    IBundleMakerArtifact[] selectedArtifacts = new IBundleMakerArtifact[artifacts.size()];
    for (int i = 0; i < artifacts.size(); i++) {
      IBundleMakerArtifact artifact = artifacts.get(i);

      if (artifact.isInstanceOf(IRootArtifact.class)) {
        artifact = ((ArtifactTreeContentProvider) _centerViewer.getContentProvider()).getVirtualRoot();
      }

      selectedArtifacts[i] = artifact;

    }
    StructuredSelection selection = new StructuredSelection(selectedArtifacts);

    // (Re-)Expand Tree Viewer according to User settings
    _expandStrategy.exandTreeViewer();

    // Make sure selected Artifacts are visible in Center Tree Viewer
    _centerViewer.setSelection(selection, true);

    _fromTreeViewer.setSelection(
        new StructuredSelection(((ArtifactTreeContentProvider) _fromTreeViewer.getContentProvider()).getVirtualRoot()),
        true);

    // expand at least to level two, to make sure that more than the root artifact is visible
    _centerViewer.expandToLevel(2);
    _centerViewer.getTree().setFocus();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.swt.widgets.Composite#setFocus()
   */
  @Override
  public boolean setFocus() {
    return _centerViewer.getTree().setFocus();
  }

  protected IRootArtifact getVirtualRoot(TreeViewer treeViewer) {
    ArtifactTreeContentProvider artifactTreeContentProvider = (ArtifactTreeContentProvider) treeViewer
        .getContentProvider();
    return artifactTreeContentProvider.getVirtualRoot();
  }

  private Composite createToolBarComposite() {
    Composite fromToolbar = new Composite(this, SWT.BORDER_SOLID);
    GridData gridData = new GridData();
    gridData.verticalAlignment = SWT.TOP;
    gridData.horizontalAlignment = SWT.FILL;
    gridData.grabExcessHorizontalSpace = true;
    gridData.grabExcessVerticalSpace = false;
    fromToolbar.setLayoutData(gridData);
    fromToolbar.setLayout(new GridLayout(1, false));

    return fromToolbar;

  }

  /**
   * <p>
   * </p>
   */
  private void init() {

    //
    this.setLayout(new GridLayout(3, true));

    //
    // https://bugs.eclipse.org/bugs/show_bug.cgi?id=162698
    // https://bugs.eclipse.org/bugs/attachment.cgi?id=52918

    Composite fromToolBarComposite = createToolBarComposite();
    Composite centerToolBarComposite = createToolBarComposite();
    Composite toToolBarComposite = createToolBarComposite();

    //
    _fromTreeViewer = ArtifactTreeViewerFactory.createDefaultArtifactTreeViewer(this);
    _centerViewer = ArtifactTreeViewerFactory.createDefaultArtifactTreeViewer(this);
    _toTreeViewer = ArtifactTreeViewerFactory.createDefaultArtifactTreeViewer(this);

    _expandStrategy = new DefaultExpandStrategy();
    _expandStrategy.init(_fromTreeViewer, _toTreeViewer);

    _detailsLabel = new Label(this, SWT.NONE);
    GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
    _detailsLabel.setLayoutData(gridData);

    //
    _fromTreeViewer.setLabelProvider(new ArtifactTreeLabelProvider());
    _artifactLabelProvider = new XRefTreeArtifactLabelProvider(_centerViewer);
    _centerViewer.setLabelProvider(_artifactLabelProvider);
    _toTreeViewer.setLabelProvider(new ArtifactTreeLabelProvider());

    // add SelectionListeners
    _fromTreeViewer.addSelectionChangedListener(new FromArtifactsSelectionChangedListener());
    _centerViewer.addSelectionChangedListener(new CenterArtifactsSelectionChangedListener());
    _toTreeViewer.addSelectionChangedListener(new ToArtifactSelectionChangedListener());

    // === Context-Menu Center Viewer ===
    MenuManager menuMgr = new MenuManager();
    menuMgr.setRemoveAllWhenShown(true);
    menuMgr.addMenuListener(new IMenuListener() {
      @Override
      public void menuAboutToShow(IMenuManager mgr) {
        fillContextMenu(mgr);
      }
    });
    // menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
    Menu menu = menuMgr.createContextMenu(_centerViewer.getControl());
    _centerViewer.getControl().setMenu(menu);
    String id = _site.getId();
    _site.registerContextMenu(menuMgr, _centerViewer);
    _site.setSelectionProvider(_centerViewer);

    // === Toolbar =====
    ToolBarManager mgr1 = new ToolBarManager();

    ExpandStrategyActionGroup fromGroup = new ExpandStrategyActionGroup(_expandStrategy, false);
    fromGroup.fill(mgr1);

    mgr1.createControl(fromToolBarComposite);

    ToolBarManager mgr2 = new ToolBarManager();
    mgr2.createControl(centerToolBarComposite);

    ToolBarManager mgr3 = new ToolBarManager();
    ExpandStrategyActionGroup toGroup = new ExpandStrategyActionGroup(_expandStrategy, true);
    toGroup.fill(mgr3);
    mgr3.createControl(toToolBarComposite);

  }

  class AbcAction extends Action {
    public AbcAction(String l) {
      super(l, IAction.AS_PUSH_BUTTON);
    }
  }

  private void fillContextMenu(IMenuManager manager) {
    manager.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
  }

  /**
   * <p>
   * </p>
   * 
   * @param treeViewer
   * @param visibleArtifacts
   */
  private VisibleArtifactsFilter setVisibleArtifacts(TreeViewer treeViewer,
      Collection<IBundleMakerArtifact> visibleArtifacts) {
    Assert.isNotNull(treeViewer);
    Assert.isNotNull(visibleArtifacts);

    //
    VisibleArtifactsFilter result = null;

    // set redraw to false
    treeViewer.getTree().setRedraw(false);

    // set the filter
    result = new VisibleArtifactsFilter(visibleArtifacts);
    treeViewer.setFilters(new ViewerFilter[] { result });

    // redraw again
    treeViewer.getTree().setRedraw(true);

    //
    return result;
  }

  protected void setSelectedCenterArtifacts(Collection<IBundleMakerArtifact> selectedArtifacts) {

    // store the top item
    TreeItem toTreeTopItem = _toTreeViewer.getTree().getTopItem();
    TreeItem fromTreeTopItem = _fromTreeViewer.getTree().getTopItem();

    //
    Set<IBundleMakerArtifact> toArtifacts = new HashSet<IBundleMakerArtifact>();
    for (IBundleMakerArtifact artifact : selectedArtifacts) {
      for (IDependency dep : artifact.getDependenciesTo()) {
        toArtifacts.add(dep.getTo());
      }
    }

    //
    Set<IBundleMakerArtifact> fromArtifacts = new HashSet<IBundleMakerArtifact>();
    for (IBundleMakerArtifact artifact : selectedArtifacts) {
      for (IDependency dep : artifact.getDependenciesFrom()) {
        fromArtifacts.add(dep.getFrom());
      }
    }

    //
    _toTreeVisibleArtifactsFilter = setVisibleArtifacts(_toTreeViewer, toArtifacts);
    _fromTreeVisibleArtifactsFilter = setVisibleArtifacts(_fromTreeViewer, fromArtifacts);

    _expandStrategy.exandTreeViewer();

    // Update Details Label
    String detailsString = (selectedArtifacts.size() > 1 ? selectedArtifacts.size() + " Artifacts" : selectedArtifacts
        .iterator().next().getName());
    int fromSize = fromArtifacts.size();
    detailsString += ", Referenced By: " + fromSize + " " + (fromSize > 1 ? "Artifacts" : "Artifact");
    int toSize = toArtifacts.size();
    detailsString += ", Referencing: " + toSize + " " + (toSize > 1 ? "Artifacts" : "Artifact");

    _detailsLabel.setText(detailsString);

    // set the top item again
    if (toTreeTopItem != null && !toTreeTopItem.isDisposed()) {
      _toTreeViewer.getTree().setTopItem(toTreeTopItem);
    } else if (_toTreeViewer.getTree().getItemCount() > 0) {
      _toTreeViewer.getTree().setTopItem(_toTreeViewer.getTree().getItem(0));
    }

    if (fromTreeTopItem != null && !fromTreeTopItem.isDisposed()) {
      _fromTreeViewer.getTree().setTopItem(fromTreeTopItem);
    } else if (_fromTreeViewer.getTree().getItemCount() > 0) {
      _fromTreeViewer.getTree().setTopItem(_fromTreeViewer.getTree().getItem(0));
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected String getDependencySelectionId() {
    return Selection.MAIN_DEPENDENCY_SELECTION_ID;
  }

  /**
   * <p>
   * </p>
   * 
   * @param selectedDetailDependencies
   */
  private void setSelectedDependencies(Collection<IDependency> dependencies) {

    //
    Selection.instance().getDependencySelectionService()
        .setSelection(getDependencySelectionId(), _providerId, dependencies);
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   * 
   */
  private final class CenterArtifactsSelectionChangedListener implements ISelectionChangedListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectionChanged(SelectionChangedEvent event) {

      //
      IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();
      if (structuredSelection.isEmpty()) {
        return;
      }
      List<IBundleMakerArtifact> selectedArtifacts = Helper.toArtifactList(structuredSelection.toList());

      // don't highlight anything
      _artifactLabelProvider.setBundleMakerArtifacts(null);

      setSelectedCenterArtifacts(selectedArtifacts);

      _centerViewer.refresh();

    }
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private final class FromArtifactsSelectionChangedListener implements ISelectionChangedListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectionChanged(SelectionChangedEvent event) {

      //
      IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();

      if (structuredSelection.isEmpty()) {
        return;
      }

      // Reset Selection in 'to' Viewer
      _toTreeViewer.setSelection(new StructuredSelection());

      // Detect highlighted artifacts
      List<IBundleMakerArtifact> selectedArtifacts = Helper.toArtifactList(structuredSelection.toList());
      List<IBundleMakerArtifact> selectedCenterArtifacts = Helper.toArtifactList(_centerViewer.getSelection());

      // Holds the dependencies that are published via DependencySelectionService
      List<IDependency> selectedDpendencies = new LinkedList<IDependency>();

      // Holds the Artifacts that should be highlighted in the center tree
      Set<IBundleMakerArtifact> highlightedArtifacts = new HashSet<IBundleMakerArtifact>();

      for (final IBundleMakerArtifact selectedArtifact : selectedArtifacts) {

        Collection<IDependency> dependencies = selectedArtifact.getDependenciesTo(selectedCenterArtifacts);
        for (IDependency dep : dependencies) {
          selectedDpendencies.add(dep);

          Collection<IDependency> coreDependencies = dep.getCoreDependencies();
          for (IDependency coreDependency : coreDependencies) {

            IBundleMakerArtifact referencedArtifact = coreDependency.getTo();
            highlightedArtifacts.add(referencedArtifact);

            // highlight all parents of the referenced artifact up to the selection in the center
            while (referencedArtifact != null) {
              if (selectedCenterArtifacts.contains(referencedArtifact)) {
                break;
              }
              highlightedArtifacts.add(referencedArtifact);
              referencedArtifact = referencedArtifact.getParent();
            }
          }
        }
      }

      //
      setSelectedDependencies(selectedDpendencies);

      //
      _artifactLabelProvider.setBundleMakerArtifacts(highlightedArtifacts);
      _centerViewer.refresh();

      String detailsText = (selectedArtifacts.size() == 1 ? selectedArtifacts.get(0).getName() + " references "
          : selectedArtifacts.size() + " Artifacts referencing ");
      int fromSize = highlightedArtifacts.size();
      detailsText += fromSize + (fromSize == 1 ? " Artifact" : " Artifacts");
      detailsText += " in " + selectedArtifacts.get(0).getRoot().getName();
      _detailsLabel.setText(detailsText);

    }
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private final class ToArtifactSelectionChangedListener implements ISelectionChangedListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectionChanged(SelectionChangedEvent event) {

      // //
      IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();
      List<IBundleMakerArtifact> selectedArtifacts = Helper.toArtifactList(structuredSelection.toList());
      //
      if (!structuredSelection.isEmpty()) {
        _fromTreeViewer.setSelection(new StructuredSelection());

        List<IDependency> dependencies = new LinkedList<IDependency>();

        Set<IBundleMakerArtifact> toArtifacts = new HashSet<IBundleMakerArtifact>();
        Set<IBundleMakerArtifact> visibleArtifacts = _toTreeVisibleArtifactsFilter.getArtifacts();
        for (IBundleMakerArtifact artifact : selectedArtifacts) {
          for (IDependency dep : artifact.getDependenciesFrom()) {
            dependencies.add(dep);
            if (visibleArtifacts.contains(dep.getTo())) {
              toArtifacts.add(dep.getFrom());
            }
          }
        }

        //
        _artifactLabelProvider.setBundleMakerArtifacts(toArtifacts);
        _centerViewer.refresh();

        String detailsText = (selectedArtifacts.size() == 1 ? selectedArtifacts.get(0).getName() + " referenced by "
            : selectedArtifacts.size() + " Artifacts referenced by ");
        int toSize = toArtifacts.size();
        detailsText += toSize + (toSize == 1 ? " Artifact" : " Artifacts");
        detailsText += " in " + selectedArtifacts.get(0).getRoot().getName();
        _detailsLabel.setText(detailsText);

        setSelectedDependencies(dependencies);

      }
    }
  }
}