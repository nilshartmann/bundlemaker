package org.bundlemaker.core.ui.view.dependencytree;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.ui.artifact.ArtifactUtilities;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeLabelProvider;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeViewerFactory;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.bundlemaker.core.util.collections.GenericCache;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DependencyTreeComposite extends Composite {

  /** the from tree viewer */
  private TreeViewer                                            _fromTreeViewer;
  
  /** the to tree viewer */
  private TreeViewer                                            _toTreeViewer;

  /** the to tree viewer */
  private TreeViewer                                            _currentlySelectedTreeViewer;

  /** - */
  @SuppressWarnings("serial")
  private GenericCache<IBundleMakerArtifact, List<IDependency>> _targetArtifactMap = new GenericCache<IBundleMakerArtifact, List<IDependency>>() {
                                                                                     @Override
                                                                                     protected List<IDependency> create(
                                                                                         IBundleMakerArtifact key) {
                                                                                       return new LinkedList<IDependency>();
                                                                                     }
                                                                                   };

  /** - */
  @SuppressWarnings("serial")
  private GenericCache<IBundleMakerArtifact, List<IDependency>> _sourceArtifactMap = new GenericCache<IBundleMakerArtifact, List<IDependency>>() {
                                                                                     @Override
                                                                                     protected List<IDependency> create(
                                                                                         IBundleMakerArtifact key) {
                                                                                       return new LinkedList<IDependency>();
                                                                                     }
                                                                                   };

  /** - */
  private List<IDependency>                                     _leafDependencies;

  /** - */
  private List<IDependency>                                     _selectedDetailDependencies;

  /** - */
  private String                                                _providerId;

  /** - */
  private IExpandStrategy                                       _expandStrategy;

  /**
   * <p>
   * Creates a new instance of type {@link DependencyTreeComposite}.
   * </p>
   * 
   * @param parent
   */
  public DependencyTreeComposite(Composite parent, String providerId, IExpandStrategy expandStrategy) {
    super(parent, SWT.NONE);

    Assert.isNotNull(providerId);

    _providerId = providerId;
    _expandStrategy = expandStrategy;

    init();
  }

  /**
   * <p>
   * </p>
   * 
   * @param dependencies
   */
  public void setDependencies(List<IDependency> dependencies) {

    //
    _leafDependencies = ArtifactUtilities.getAllLeafDependencies(dependencies);

    //
    _sourceArtifactMap.clear();
    _targetArtifactMap.clear();

    //
    for (IDependency dependency : _leafDependencies) {
      _sourceArtifactMap.getOrCreate(dependency.getFrom()).add(dependency);
      _targetArtifactMap.getOrCreate(dependency.getTo()).add(dependency);
    }

    // set the root if necessary
    if (dependencies.size() > 0) {
      IRootArtifact rootArtifact = dependencies.get(0).getFrom().getRoot();
      if (!rootArtifact.equals(_fromTreeViewer.getInput()) && !rootArtifact.equals(_toTreeViewer.getInput())) {

        //
        _fromTreeViewer.setInput(rootArtifact);
        _toTreeViewer.setInput(rootArtifact);

        //
        if (_expandStrategy != null) {
          _expandStrategy.init(_fromTreeViewer, _toTreeViewer);
        }
      }
    }
    // update 'from' and 'to' tree, no filtering
    setVisibleArtifacts(_fromTreeViewer, _sourceArtifactMap.keySet());
    setVisibleArtifacts(_toTreeViewer, _targetArtifactMap.keySet());

    //
    _fromTreeViewer.setSelection(null);
    _toTreeViewer.setSelection(null);

    //
    expandArtifacts(_fromTreeViewer, null);
    expandArtifacts(_toTreeViewer, null);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final List<IDependency> getSelectedDetailDependencies() {
    return _selectedDetailDependencies;
  }

  /**
   * <p>
   * </p>
   */
  private void init() {

    //
    this.setLayout(new GridLayout(2, true));

    //
    _fromTreeViewer = ArtifactTreeViewerFactory.createDefaultArtifactTreeViewer(this);
    _toTreeViewer = ArtifactTreeViewerFactory.createDefaultArtifactTreeViewer(this);

    //
    _fromTreeViewer.setLabelProvider(new DependencyTreeArtifactLabelProvider(_sourceArtifactMap, _fromTreeViewer, _toTreeViewer, false));
    _toTreeViewer.setLabelProvider(new DependencyTreeArtifactLabelProvider(_targetArtifactMap, _toTreeViewer, _fromTreeViewer, true));
    
    // add SelectionListeners
    _fromTreeViewer.addSelectionChangedListener(new FromArtifactsSelectionChangedListener());
    _toTreeViewer.addSelectionChangedListener(new ToArtifactSelectionChangedListener());

    //
    _selectedDetailDependencies = Collections.emptyList();
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

    if (visibleArtifacts.size() > 0) {

      // set the filter
      result = new VisibleArtifactsFilter(visibleArtifacts);
      treeViewer.setFilters(new ViewerFilter[] { result });
    }

    // set empty list
    else {
      treeViewer.setInput(Collections.emptyList());
    }

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

  /**
   * <p>
   * </p>
   * 
   * @param selectedDetailDependencies
   */
  private void setSelectedDetailDependencies(List<IDependency> selectedDetailDependencies) {

    //
    _selectedDetailDependencies = selectedDetailDependencies;

    //
    Selection.instance().getDependencySelectionService()
        .setSelection(getDependencySelectionId(), _providerId, selectedDetailDependencies);
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private final class FromArtifactsSelectionChangedListener implements ISelectionChangedListener {

    @Override
    public void selectionChanged(SelectionChangedEvent event) {

      //
      IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();

      //
      if (!structuredSelection.isEmpty() && _currentlySelectedTreeViewer != _fromTreeViewer) {
        fromViewerSelected(_fromTreeViewer, _toTreeViewer);
        _currentlySelectedTreeViewer = _fromTreeViewer;
      }

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
        TreeItem treeItem = _toTreeViewer.getTree().getTopItem();

        // create empty lists of visible artifacts / selected detail dependencies
        Set<IBundleMakerArtifact> visibleArtifacts = new HashSet<IBundleMakerArtifact>();
        List<IDependency> selectedDetailDependencies = new LinkedList<IDependency>();

        // iterate over all the selected artifacts
        for (Object selectedObject : structuredSelection.toList()) {
          if (selectedObject instanceof IBundleMakerArtifact) {
            IBundleMakerArtifact bundleMakerArtifact = (IBundleMakerArtifact) selectedObject;

            // we have to find all children
            for (IBundleMakerArtifact artifact : ArtifactUtilities.getSelfAndAllChildren(bundleMakerArtifact)) {
              if (_sourceArtifactMap.containsKey(artifact)) {
                List<IDependency> dependencies = _sourceArtifactMap.get(artifact);
                selectedDetailDependencies.addAll(dependencies);
                for (IDependency dep : dependencies) {
                  visibleArtifacts.add(dep.getTo());
                }
              }
            }
          }
        }

        // TODO
        // _toTreeViewer.setSelection(new StructuredSelection());
        VisibleArtifactsFilter visibleArtifactsFilter = setVisibleArtifacts(_toTreeViewer, visibleArtifacts);
        setSelectedDetailDependencies(selectedDetailDependencies);

        //
        expandArtifacts(_toTreeViewer, visibleArtifactsFilter);

        // set the top item again
        if (treeItem != null && !treeItem.isDisposed()) {
          _toTreeViewer.getTree().setTopItem(treeItem);
        } else if (_toTreeViewer.getTree().getItemCount() > 0) {
          _toTreeViewer.getTree().setTopItem(_toTreeViewer.getTree().getItem(0));
        }

      } else {
        setVisibleArtifacts(_toTreeViewer, _targetArtifactMap.keySet());
        setSelectedDetailDependencies(_leafDependencies);
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

      //
      IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();

      //
      if (!structuredSelection.isEmpty() && _currentlySelectedTreeViewer != _toTreeViewer) {
        toViewerSelected(_fromTreeViewer, _toTreeViewer);
        _currentlySelectedTreeViewer = _toTreeViewer;
      }

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
        TreeItem treeItem = _fromTreeViewer.getTree().getTopItem();

        //
        Set<IBundleMakerArtifact> visibleArtifacts = new HashSet<IBundleMakerArtifact>();
        List<IDependency> selectedDetailDependencies = new LinkedList<IDependency>();
        for (Object selectedObject : structuredSelection.toList()) {
          if (selectedObject instanceof IBundleMakerArtifact) {
            IBundleMakerArtifact bundleMakerArtifact = (IBundleMakerArtifact) selectedObject;
            for (IBundleMakerArtifact artifact : ArtifactUtilities.getSelfAndAllChildren(bundleMakerArtifact)) {
              if (_targetArtifactMap.containsKey(artifact)) {
                List<IDependency> dependencies = _targetArtifactMap.get(artifact);
                selectedDetailDependencies.addAll(dependencies);
                for (IDependency dep : dependencies) {
                  visibleArtifacts.add(dep.getFrom());
                }
              }
            }
          }
        }

        //
        // _fromTreeViewer.setSelection(new StructuredSelection());
        VisibleArtifactsFilter visibleArtifactsFilter = setVisibleArtifacts(_fromTreeViewer, visibleArtifacts);
        setSelectedDetailDependencies(selectedDetailDependencies);

        //
        expandArtifacts(_fromTreeViewer, visibleArtifactsFilter);

        // set the top item again
        try {
          _fromTreeViewer.getTree().setTopItem(treeItem);
        } catch (Exception e) {
          //
        }
      } else {
        setVisibleArtifacts(_fromTreeViewer, _sourceArtifactMap.keySet());
        setSelectedDetailDependencies(_leafDependencies);
      }
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

  /**
   * <p>
   * </p>
   * 
   * @param visibleArtifactsFilter
   * 
   */
  private void expandArtifacts(final TreeViewer treeViewer, final VisibleArtifactsFilter visibleArtifactsFilter) {
    Assert.isNotNull(treeViewer);

    // return if no expand strategy has been set
    if (_expandStrategy == null) {
      return;
    }

    // check if no root artifact has been set (yet)
    if (!(treeViewer.getInput() instanceof IRootArtifact)) {
      return;
    }

    // disable redraw (performance)
    treeViewer.getTree().setRedraw(false);

    //
    if (treeViewer == _fromTreeViewer) {

      //
      _expandStrategy.expandFromTreeViewer(_fromTreeViewer,
          visibleArtifactsFilter != null ? visibleArtifactsFilter.getArtifacts() : new HashSet<IBundleMakerArtifact>());

    } else if (treeViewer == _toTreeViewer) {

      //
      _expandStrategy.expandToTreeViewer(_toTreeViewer,
          visibleArtifactsFilter != null ? visibleArtifactsFilter.getArtifacts() : new HashSet<IBundleMakerArtifact>());
    }

    // enable redraw (performance)
    treeViewer.getTree().setRedraw(true);
  }
}
