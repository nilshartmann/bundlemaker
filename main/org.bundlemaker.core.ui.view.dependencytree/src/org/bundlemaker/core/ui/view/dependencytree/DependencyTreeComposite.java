package org.bundlemaker.core.ui.view.dependencytree;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.ui.artifact.ArtifactUtilities;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeViewerFactory;
import org.bundlemaker.core.ui.artifact.tree.IVirtualRootContentProvider;
import org.bundlemaker.core.ui.event.selection.Selection;
import org.bundlemaker.core.util.collections.GenericCache;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
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

  // TODO: MOVE
  private List                                                  _artifactTypeOrder      = Arrays.asList(new Class[] {
      IRootArtifact.class, IGroupArtifact.class, IModuleArtifact.class, IPackageArtifact.class,
      IResourceArtifact.class, ITypeArtifact.class                                     });

  /** - */
  private TreeViewer                                            _fromTreeViewer;

  /** - */
  private TreeViewer                                            _toTreeViewer;

  /** - */
  @SuppressWarnings("serial")
  private GenericCache<IBundleMakerArtifact, List<IDependency>> _targetArtifactMap      = new GenericCache<IBundleMakerArtifact, List<IDependency>>() {
                                                                                          @Override
                                                                                          protected List<IDependency> create(
                                                                                              IBundleMakerArtifact key) {
                                                                                            return new LinkedList<IDependency>();
                                                                                          }
                                                                                        };

  /** - */
  @SuppressWarnings("serial")
  private GenericCache<IBundleMakerArtifact, List<IDependency>> _sourceArtifactMap      = new GenericCache<IBundleMakerArtifact, List<IDependency>>() {
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
  private Class<? extends IBundleMakerArtifact>                 _fromViewerExpandToType = IModuleArtifact.class;

  /** - */
  private Class<? extends IBundleMakerArtifact>                 _toViewerExpandToType   = IModuleArtifact.class;

  /**
   * <p>
   * Creates a new instance of type {@link DependencyTreeComposite}.
   * </p>
   * 
   * @param parent
   */
  public DependencyTreeComposite(Composite parent, String providerId) {
    super(parent, SWT.NONE);

    Assert.isNotNull(providerId);

    _providerId = providerId;

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

    // update 'from' and 'to' tree, no filtering
    setVisibleArtifacts(_fromTreeViewer, _sourceArtifactMap.keySet());
    setVisibleArtifacts(_toTreeViewer, _targetArtifactMap.keySet());

    //
    _fromTreeViewer.setSelection(null);
    _toTreeViewer.setSelection(null);

    //
    expandArtifacts(_fromTreeViewer);
    expandArtifacts(_toTreeViewer);
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
   * 
   * @param autoExpandType
   *          the expandToType to set
   */
  public void setFromTreeViewerAutoExpandType(Class<? extends IBundleMakerArtifact> autoExpandType) {
    _fromViewerExpandToType = autoExpandType;
    expandArtifacts(_fromTreeViewer);
  }

  /**
   * <p>
   * </p>
   * 
   * @param autoExpandType
   */
  public void setToTreeViewerAutoExpandType(Class<? extends IBundleMakerArtifact> autoExpandType) {
    _toViewerExpandToType = autoExpandType;
    expandArtifacts(_toTreeViewer);
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

    //
    _toTreeViewer = ArtifactTreeViewerFactory.createDefaultArtifactTreeViewer(this);

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
  private void setVisibleArtifacts(TreeViewer treeViewer, Collection<IBundleMakerArtifact> visibleArtifacts) {
    Assert.isNotNull(treeViewer);
    Assert.isNotNull(visibleArtifacts);

    // set redraw to false
    treeViewer.getTree().setRedraw(false);

    if (visibleArtifacts.size() > 0) {

      // set the filter
      treeViewer.setFilters(new ViewerFilter[] { new VisibleArtifactsFilter(visibleArtifacts) });

      // set the root if necessary
      IRootArtifact rootArtifact = visibleArtifacts.toArray(new IBundleMakerArtifact[0])[0].getRoot();
      if (!rootArtifact.equals(treeViewer.getInput())) {

        //
        treeViewer.setInput(rootArtifact);
      }
    }

    // set empty list
    else {
      treeViewer.setInput(Collections.emptyList());
    }

    // redraw again
    treeViewer.getTree().setRedraw(true);
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

        _toTreeViewer.setSelection(new StructuredSelection());
        setVisibleArtifacts(_toTreeViewer, visibleArtifacts);
        setSelectedDetailDependencies(selectedDetailDependencies);

        //
        expandArtifacts(_toTreeViewer);

        // set the top item again
        if (!treeItem.isDisposed()) {
          _toTreeViewer.getTree().setTopItem(treeItem);
        } else {
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

    @Override
    public void selectionChanged(SelectionChangedEvent event) {

      //
      IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();

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

        _fromTreeViewer.setSelection(new StructuredSelection());
        setVisibleArtifacts(_fromTreeViewer, visibleArtifacts);
        setSelectedDetailDependencies(selectedDetailDependencies);

        //
        expandArtifacts(_fromTreeViewer);

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
   */
  private void expandArtifacts(final TreeViewer treeViewer) {
    Assert.isNotNull(treeViewer);

    //
    if (!(treeViewer.getInput() instanceof IRootArtifact)) {
      return;
    }

    //
    treeViewer.getTree().setRedraw(false);

    //
    IArtifactTreeVisitor visitor = new IArtifactTreeVisitor.Adapter() {

      @Override
      public boolean visit(IRootArtifact rootArtifact) {
        // always expand root && always visit children
        treeViewer.setExpandedState(rootArtifact, true);
        return true;
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public boolean visit(IGroupArtifact groupArtifact) {
        boolean shouldExpand = shouldExpand(groupArtifact, IGroupArtifact.class, treeViewer);
        treeViewer.setExpandedState(groupArtifact, shouldExpand);
        return shouldExpand;
      }

      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {
        boolean shouldExpand = shouldExpand(moduleArtifact, IModuleArtifact.class, treeViewer);
        treeViewer.setExpandedState(moduleArtifact, shouldExpand);
        return shouldExpand;
      }

      @Override
      public boolean visit(IPackageArtifact packageArtifact) {
        boolean shouldExpand = shouldExpand(packageArtifact, IPackageArtifact.class, treeViewer);
        treeViewer.setExpandedState(packageArtifact, shouldExpand);
        return shouldExpand;
      }

      @Override
      public boolean visit(IResourceArtifact resourceArtifact) {
        boolean shouldExpand = shouldExpand(resourceArtifact, IResourceArtifact.class, treeViewer);
        treeViewer.setExpandedState(resourceArtifact, shouldExpand);
        return shouldExpand;
      }

      /**
       * <p>
       * </p>
       * 
       * @param artifact
       * @param treeViewer
       * @return
       */
      private boolean shouldExpand(IBundleMakerArtifact artifact, Class<? extends IBundleMakerArtifact> clazz,
          TreeViewer treeViewer) {

        //
        Class<? extends IBundleMakerArtifact> expandToType = treeViewer == _fromTreeViewer ? _fromViewerExpandToType
            : _toViewerExpandToType;

        //
        int index1 = _artifactTypeOrder.indexOf(expandToType);
        int index2 = _artifactTypeOrder.indexOf(clazz);

        //
        if (index1 > index2) {
          return true;
        }

        //
        if (index1 != index2) {
          return false;
        }

        ViewerFilter[] filters = treeViewer.getFilters();
        Object[] children = artifact.getChildren(clazz).toArray();
        for (ViewerFilter viewerFilter : filters) {
          children = viewerFilter.filter(treeViewer, artifact, children);
        }

        return children.length > 0;
      }
    };

    //
    ((IRootArtifact) treeViewer.getInput()).accept(visitor);

    // HACK
    IContentProvider contentProvider = treeViewer.getContentProvider();
    if (contentProvider instanceof IVirtualRootContentProvider) {
      treeViewer.setExpandedState(((IVirtualRootContentProvider) contentProvider).getVirtualRoot(), true);
    }

    //
    treeViewer.getTree().setRedraw(true);
  }
}
