package org.bundlemaker.core.ui.experimental.dependencytable.threeway;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeLabelProvider;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeViewerFactory;
import org.bundlemaker.core.ui.artifact.tree.VisibleArtifactsFilter;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.bundlemaker.core.ui.view.dependencytree.DefaultExpandStrategy;
import org.bundlemaker.core.ui.view.dependencytree.Helper;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TreeItem;

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

  /**
   * <p>
   * Creates a new instance of type {@link XRefComposite}.
   * </p>
   * 
   * @param parent
   */
  public XRefComposite(Composite parent, String providerId) {
    super(parent, SWT.NONE);

    Assert.isNotNull(providerId);

    _providerId = providerId;

    init();
  }

  public void setRoot(IRootArtifact rootArtifact) {
    _fromTreeViewer.setInput(rootArtifact);
    _centerViewer.setInput(rootArtifact);
    _toTreeViewer.setInput(rootArtifact);
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

    // ToolBar toolBar1 = new ToolBar(this, SWT.HORIZONTAL);
    // ToolBar toolBar2 = new ToolBar(this, SWT.HORIZONTAL);
    // ToolBar toolBar3 = new ToolBar(this, SWT.HORIZONTAL);

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
    _artifactLabelProvider = new XRefTreeArtifactLabelProvider();
    _centerViewer.setLabelProvider(_artifactLabelProvider);
    _toTreeViewer.setLabelProvider(new ArtifactTreeLabelProvider());

    // add SelectionListeners
    _fromTreeViewer.addSelectionChangedListener(new FromArtifactsSelectionChangedListener());
    _centerViewer.addSelectionChangedListener(new CenterArtifactsSelectionChangedListener());
    _toTreeViewer.addSelectionChangedListener(new ToArtifactSelectionChangedListener());

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

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected String getDependencySelectionId() {
    return Selection.DETAIL_DEPENDENCY_SELECTION_ID;
  }

  // /**
  // * <p>
  // * </p>
  // *
  // * @param selectedDetailDependencies
  // */
  // private void setSelectedDetailDependencies(Collection<IDependency> dependencies) {
  //
  // //
  // Selection.instance().getDependencySelectionService()
  // .setSelection(getDependencySelectionId(), _providerId, dependencies);
  // }

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

      // _artifactLabelProvider.setBundleMakerArtifacts(null);
      // _centerViewer.refresh();

      // //
      // if (!structuredSelection.isEmpty() && _currentlySelectedTreeViewer != _fromTreeViewer) {
      // fromViewerSelected(_fromTreeViewer, _toTreeViewer);
      // _currentlySelectedTreeViewer = _fromTreeViewer;
      // }

      // check for selected root element
      boolean containsRoot = false;
      for (Object object : structuredSelection.toList()) {
        if (object instanceof IRootArtifact) {
          containsRoot = true;
          break;
        }
      }

      if (structuredSelection.size() > 0 && !containsRoot) {

        // store the top item
        TreeItem toTreeTopItem = _toTreeViewer.getTree().getTopItem();
        TreeItem fromTreeTopItem = _fromTreeViewer.getTree().getTopItem();

        //
        List<IBundleMakerArtifact> selectedArtifacts = Helper.toArtifactList(structuredSelection.toList());

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

        // Update Details Label
        String detailsString = (selectedArtifacts.size() > 1 ? selectedArtifacts.size() + " Artifacts"
            : selectedArtifacts.get(0).getName());
        int fromSize = fromArtifacts.size();
        detailsString += ", Referenced By: " + fromSize + " " + (fromSize > 1 ? "Artifacts" : "Artifact");
        int toSize = toArtifacts.size();
        detailsString += ", Referencing: " + toSize + " " + (toSize > 1 ? "Artifacts" : "Artifact");

        _detailsLabel.setText(detailsString);

        // //
        // Set<IBundleMakerArtifact> visibleArtifacts =
        // _helper.setFromArtifacts(Helper.toArtifactList(structuredSelection
        // .toList()));
        // VisibleArtifactsFilter visibleArtifactsFilter = setVisibleArtifacts(_toTreeViewer, visibleArtifacts);
        // setSelectedDetailDependencies(_helper.getFilteredDependencies());
        //
        // //
        // expandArtifacts(_toTreeViewer, visibleArtifactsFilter);
        //

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

      } else {
        // setVisibleArtifacts(_toTreeViewer, _helper.getUnfilteredTargetArtifacts());
        // setSelectedDetailDependencies(_helper.getUnfilteredDependencies());
      }
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

      if (structuredSelection.size() > 0) {

        //
        List<IBundleMakerArtifact> selectedArtifacts = Helper.toArtifactList(structuredSelection.toList());

        //
        Set<IBundleMakerArtifact> fromArtifacts = new HashSet<IBundleMakerArtifact>();
        Set<IBundleMakerArtifact> visibleArtifacts = _fromTreeVisibleArtifactsFilter.getArtifacts();
        for (IBundleMakerArtifact artifact : selectedArtifacts) {
          for (IDependency dep : artifact.getDependenciesTo()) {
            if (visibleArtifacts.contains(dep.getFrom())) {
              fromArtifacts.add(dep.getTo());
            }
          }
        }

        //
        _artifactLabelProvider.setBundleMakerArtifacts(fromArtifacts);
        _centerViewer.refresh();

        String detailsText = (selectedArtifacts.size() == 1 ? selectedArtifacts.get(0).getName() + " references "
            : selectedArtifacts.size() + " Artifacts referencing ");
        int fromSize = fromArtifacts.size();
        detailsText += fromSize + (fromSize == 1 ? " Artifact" : " Artifacts");
        detailsText += " in " + selectedArtifacts.get(0).getRoot().getName();
        _detailsLabel.setText(detailsText);

      } else {
        // setVisibleArtifacts(_toTreeViewer, _helper.getUnfilteredTargetArtifacts());
        // setSelectedDetailDependencies(_helper.getUnfilteredDependencies());
      }
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

        Set<IBundleMakerArtifact> toArtifacts = new HashSet<IBundleMakerArtifact>();
        Set<IBundleMakerArtifact> visibleArtifacts = _toTreeVisibleArtifactsFilter.getArtifacts();
        for (IBundleMakerArtifact artifact : selectedArtifacts) {
          for (IDependency dep : artifact.getDependenciesFrom()) {
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

      }
      // toViewerSelected(_fromTreeViewer, _toTreeViewer);
      // _currentlySelectedTreeViewer = _toTreeViewer;
      // }
      //
      // // check for selected root element
      // boolean containsRoot = false;
      // for (Object object : structuredSelection.toList()) {
      // if (object instanceof IRootArtifact) {
      // containsRoot = true;
      // break;
      // }
      // }
      //
      // if (structuredSelection.size() > 0 && !containsRoot) {
      //
      // // store the top item
      // TreeItem treeItem = _fromTreeViewer.getTree().getTopItem();
      //
      // //
      // Set<IBundleMakerArtifact> visibleArtifacts = _helper.setToArtifacts(Helper.toArtifactList(structuredSelection
      // .toList()));
      // VisibleArtifactsFilter visibleArtifactsFilter = setVisibleArtifacts(_fromTreeViewer, visibleArtifacts);
      // setSelectedDetailDependencies(_helper.getFilteredDependencies());
      //
      // //
      // expandArtifacts(_fromTreeViewer, visibleArtifactsFilter);
      //
      // // set the top item again
      // try {
      // _fromTreeViewer.getTree().setTopItem(treeItem);
      // } catch (Exception e) {
      // //
      // }
      // } else {
      // setVisibleArtifacts(_fromTreeViewer, _helper.getUnfilteredSourceArtifacts());
      // setSelectedDetailDependencies(_helper.getUnfilteredDependencies());
      // }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param toTreeViewer
   * @param fromTreeViewer
   */
  protected void toViewerSelected(TreeViewer fromTreeViewer, TreeViewer toTreeViewer) {

  }

  /**
   * <p>
   * </p>
   * 
   * @param fromTreeViewer
   * @param toTreeViewer
   */
  protected void fromViewerSelected(TreeViewer fromTreeViewer, TreeViewer toTreeViewer) {

  }

  // /**
  // * <p>
  // * </p>
  // *
  // * @param visibleArtifactsFilter
  // *
  // */
  // private void expandArtifacts(final TreeViewer treeViewer, final VisibleArtifactsFilter visibleArtifactsFilter) {
  // Assert.isNotNull(treeViewer);
  //
  // // return if no expand strategy has been set
  // if (_expandStrategy == null) {
  // return;
  // }
  //
  // // check if no root artifact has been set (yet)
  // if (!(treeViewer.getInput() instanceof IRootArtifact)) {
  // return;
  // }
  //
  // // disable redraw (performance)
  // treeViewer.getTree().setRedraw(false);
  //
  // //
  // if (treeViewer == _fromTreeViewer) {
  //
  // //
  // _expandStrategy.expandFromTreeViewer(_fromTreeViewer,
  // visibleArtifactsFilter != null ? visibleArtifactsFilter.getArtifacts() : new HashSet<IBundleMakerArtifact>());
  //
  // } else if (treeViewer == _toTreeViewer) {
  //
  // //
  // _expandStrategy.expandToTreeViewer(_toTreeViewer,
  // visibleArtifactsFilter != null ? visibleArtifactsFilter.getArtifacts() : new HashSet<IBundleMakerArtifact>());
  // }
  //
  // // enable redraw (performance)
  // treeViewer.getTree().setRedraw(true);
  // }
}
