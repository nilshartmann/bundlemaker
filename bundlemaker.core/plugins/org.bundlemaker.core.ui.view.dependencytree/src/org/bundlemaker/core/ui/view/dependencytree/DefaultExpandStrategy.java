package org.bundlemaker.core.ui.view.dependencytree;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.ui.artifact.tree.IVirtualRootContentProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DefaultExpandStrategy implements IExpandStrategy {

  /** - */
  private List                                  _artifactTypeOrder             = Arrays.asList(new Class[] {
                                                                               IRootArtifact.class,
                                                                               IGroupArtifact.class,
                                                                               IModuleArtifact.class,
                                                                               IPackageArtifact.class,
                                                                               IResourceArtifact.class,
                                                                               ITypeArtifact.class });

  /** - */
  private Class<? extends IBundleMakerArtifact> _fromViewerExpandToType        = IModuleArtifact.class;

  /** - */
  private Class<? extends IBundleMakerArtifact> _toViewerExpandToType          = IModuleArtifact.class;

  /** - */
  private final Set<Object>                     _manuallyCollapsedElementsTo   = new HashSet<Object>();

  /** - */
  private final Set<Object>                     _manuallyExpandedElementsTo    = new HashSet<Object>();

  /** - */
  private final Set<Object>                     _expandedElementsTo            = new HashSet<Object>();

  /** - */
  private final Set<Object>                     _manuallyCollapsedElementsFrom = new HashSet<Object>();

  /** - */
  private final Set<Object>                     _manuallyExpandedElementsFrom  = new HashSet<Object>();

  /** - */
  private final Set<Object>                     _expandedElementsFrom          = new HashSet<Object>();

  /** - */
  private TreeViewer                            _fromTreeViewer;

  /** - */
  private TreeViewer                            _toTreeViewer;

  /** - */
  private Set<IBundleMakerArtifact>             _toVisibleArtifact;

  /** - */
  private Set<IBundleMakerArtifact>             _fromVisibleArtifacts;

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(TreeViewer fromTreeViewer, TreeViewer toTreeViewer) {

    _fromTreeViewer = fromTreeViewer;
    _toTreeViewer = toTreeViewer;

    //
    fromTreeViewer.addTreeListener(new ITreeViewerListener() {

      @Override
      public void treeExpanded(TreeExpansionEvent event) {
        _manuallyExpandedElementsFrom.add(event.getElement());
        _manuallyCollapsedElementsFrom.remove(event.getElement());
      }

      @Override
      public void treeCollapsed(TreeExpansionEvent event) {
        _manuallyExpandedElementsFrom.remove(event.getElement());
        _manuallyCollapsedElementsFrom.add(event.getElement());
      }
    });

    //
    addRootArtifact(_fromTreeViewer, _manuallyExpandedElementsFrom);

    //
    toTreeViewer.addTreeListener(new ITreeViewerListener() {

      @Override
      public void treeExpanded(TreeExpansionEvent event) {
        _manuallyExpandedElementsTo.add(event.getElement());
        _manuallyCollapsedElementsTo.remove(event.getElement());
      }

      @Override
      public void treeCollapsed(TreeExpansionEvent event) {
        _manuallyExpandedElementsTo.remove(event.getElement());
        _manuallyCollapsedElementsTo.add(event.getElement());
      }
    });

    //
    addRootArtifact(_toTreeViewer, _manuallyExpandedElementsTo);
  }

  /**
   * <p>
   * </p>
   * 
   * @param treeViewer
   * @param manuallyExpandedElements
   */
  private void addRootArtifact(TreeViewer treeViewer, Set<Object> manuallyExpandedElements) {

    //
    IContentProvider contentProvider = treeViewer.getContentProvider();

    //
    if (contentProvider instanceof IVirtualRootContentProvider) {
      IRootArtifact virtualRoot = ((IVirtualRootContentProvider) contentProvider).getVirtualRoot();
      if (virtualRoot != null) {
      manuallyExpandedElements.add(virtualRoot);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void expandFromTreeViewer(TreeViewer fromTreeViewer, Set<IBundleMakerArtifact> visibleArtifact) {

    //
    _fromVisibleArtifacts = visibleArtifact;
    expandFromTreeViewer(false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void expandToTreeViewer(TreeViewer toTreeViewer, Set<IBundleMakerArtifact> visibleArtifact) {

    //
    _toVisibleArtifact = visibleArtifact;
    expandToTreeViewer(false);
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
    expandFromTreeViewer(true);
  }
  
  /**
   * @return the fromViewerExpandToType
   */
  public Class<? extends IBundleMakerArtifact> getFromViewerExpandToType() {
    return _fromViewerExpandToType;
  }

  /**
   * <p>
   * </p>
   * 
   * @param autoExpandType
   */
  public void setToTreeViewerAutoExpandType(Class<? extends IBundleMakerArtifact> autoExpandType) {
    _toViewerExpandToType = autoExpandType;
    expandToTreeViewer(true);
  }
  
/**
 * @return the toViewerExpandToType
 */
public Class<? extends IBundleMakerArtifact> getToViewerExpandToType() {
  return _toViewerExpandToType;
}

public void exandTreeViewer() {
  expandFromTreeViewer(false);
  expandToTreeViewer(false);
}
  /**
   * <p>
   * </p>
   * 
   * @param deleteManuallyExpandedElements
   */
  private void expandFromTreeViewer(boolean deleteManuallyExpandedElements) {

    //
    if (deleteManuallyExpandedElements) {
      _manuallyExpandedElementsFrom.clear();
      _manuallyCollapsedElementsFrom.clear();
      addRootArtifact(_fromTreeViewer, _manuallyExpandedElementsFrom);
    }

    expandArtifacts(_expandedElementsFrom, _fromViewerExpandToType, (IRootArtifact) _fromTreeViewer.getInput(),
        _fromVisibleArtifacts);

    _expandedElementsFrom.addAll(_manuallyExpandedElementsFrom);
    _expandedElementsFrom.removeAll(_manuallyCollapsedElementsFrom);

    _fromTreeViewer.setExpandedElements(_expandedElementsFrom.toArray());
  }

  /**
   * <p>
   * </p>
   * 
   * @param deleteManuallyExpandedElements
   */
  private void expandToTreeViewer(boolean deleteManuallyExpandedElements) {

    //
    if (deleteManuallyExpandedElements) {
      _manuallyExpandedElementsTo.clear();
      _manuallyCollapsedElementsTo.clear();
      addRootArtifact(_toTreeViewer, _manuallyExpandedElementsTo);
    }

    expandArtifacts(_expandedElementsTo, _toViewerExpandToType, (IRootArtifact) _toTreeViewer.getInput(),
        _toVisibleArtifact);

    _expandedElementsTo.addAll(_manuallyExpandedElementsTo);
    _expandedElementsTo.removeAll(_manuallyCollapsedElementsTo);

    _toTreeViewer.setExpandedElements(_expandedElementsTo.toArray());
  }

  /**
   * <p>
   * </p>
   * 
   * @param expandedElements
   * @param expandToType
   * @param rootArtifact
   */
  private void expandArtifacts(final Set<Object> expandedElements,
      final Class<? extends IBundleMakerArtifact> expandToType, final IRootArtifact rootArtifact,
      final Set<IBundleMakerArtifact> visibleArtifacts) {

    //
    for (Iterator<Object> iterator = expandedElements.iterator(); iterator.hasNext();) {
      if (!shouldExpand((IBundleMakerArtifact) iterator.next(), expandToType, visibleArtifacts)) {
        iterator.remove();
      }
    }

    // expandedElements.clear();

    //
    IAnalysisModelVisitor visitor = new IAnalysisModelVisitor.Adapter() {

      @Override
      public boolean visit(IRootArtifact rootArtifact) {
        expandedElements.add(rootArtifact);
        return true;
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public boolean visit(IGroupArtifact groupArtifact) {
        boolean shouldExpand = shouldExpand(groupArtifact, expandToType, visibleArtifacts);
        if (shouldExpand) {
          expandedElements.add(groupArtifact);
        }
        return shouldExpand;
      }

      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {
        boolean shouldExpand = shouldExpand(moduleArtifact, expandToType, visibleArtifacts);
        if (shouldExpand) {
          expandedElements.add(moduleArtifact);
        }
        return shouldExpand;
      }

      @Override
      public boolean visit(IPackageArtifact packageArtifact) {
        boolean shouldExpand = shouldExpand(packageArtifact, expandToType, visibleArtifacts);
        if (shouldExpand) {
          expandedElements.add(packageArtifact);
        }
        return shouldExpand;
      }

      @Override
      public boolean visit(IResourceArtifact resourceArtifact) {
        boolean shouldExpand = shouldExpand(resourceArtifact, expandToType, visibleArtifacts);
        if (shouldExpand) {
          expandedElements.add(resourceArtifact);
        }
        return shouldExpand;
      }
    };

    rootArtifact.accept(visitor);

    // // HACK
    // IContentProvider contentProvider = _fromTreeViewer.getContentProvider();
    // if (contentProvider instanceof IVirtualRootContentProvider) {
    // expandedElements.add(((IVirtualRootContentProvider) contentProvider).getVirtualRoot());
    // }
    //
    // //
    // treeViewer.getTree().setRedraw(true);
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param expandToType
   * @return
   */
  private boolean shouldExpand(IBundleMakerArtifact artifact, Class<? extends IBundleMakerArtifact> expandToType,
      Set<IBundleMakerArtifact> visibleArtifacts) {

    //
    boolean result = false;

    //
    int index1 = _artifactTypeOrder.indexOf(expandToType);
    int index2 = _artifactTypeOrder.indexOf(getType(artifact));

    //
    if (index1 > index2) {
      result = true;
    }

    //
    else if (index1 != index2) {
      result = false;
    }

    //
    else if (expandToType.equals(IPackageArtifact.class)) {
      Collection<? extends IBundleMakerArtifact> children = artifact.getChildren(expandToType);
      result = children.size() > 0;
    }

    //
    if (result && visibleArtifacts != null && !visibleArtifacts.isEmpty() && !(artifact instanceof IRootArtifact)) {
      result = visibleArtifacts.contains(artifact);
    }

    //
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  private Class<? extends IBundleMakerArtifact> getType(IBundleMakerArtifact artifact) {

    //
    if (IRootArtifact.class.isAssignableFrom(artifact.getClass())) {
      return IRootArtifact.class;
    }
    //
    else if (IGroupArtifact.class.isAssignableFrom(artifact.getClass())) {
      return IGroupArtifact.class;
    }
    //
    else if (IModuleArtifact.class.isAssignableFrom(artifact.getClass())) {
      return IModuleArtifact.class;
    }
    //
    else if (IPackageArtifact.class.isAssignableFrom(artifact.getClass())) {
      return IPackageArtifact.class;
    }
    //
    else if (IResourceArtifact.class.isAssignableFrom(artifact.getClass())) {
      return IResourceArtifact.class;
    }
    //
    else if (ITypeArtifact.class.isAssignableFrom(artifact.getClass())) {
      return ITypeArtifact.class;
    }

    return null;
  }
}
