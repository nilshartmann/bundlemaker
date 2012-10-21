package org.bundlemaker.core.ui.view.dependencytree;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeLabelProvider;
import org.bundlemaker.core.util.collections.GenericCache;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DependencyTreeArtifactLabelProvider extends ArtifactTreeLabelProvider {

//  /** - */
//  private GenericCache<IBundleMakerArtifact, List<IDependency>> _dependencyCache;

//  /** - */
//  private TreeViewer                                            _fromTreeViewer;
//
//  /** - */
//  private TreeViewer                                            _toTreeViewer;
//
//  /** - */
//  private boolean                                               _reverse;

//  /**
//   * <p>
//   * Creates a new instance of type {@link DependencyTreeArtifactLabelProvider}.
//   * </p>
//   * 
//   * @param dependencyCache
//   * @param toTreeViewer
//   * @param fromTreeViewer
//   */
//  public DependencyTreeArtifactLabelProvider(GenericCache<IBundleMakerArtifact, List<IDependency>> dependencyCache,
//      TreeViewer fromTreeViewer, TreeViewer toTreeViewer, boolean reverse) {
//
//    //
//    Assert.isNotNull(dependencyCache);
//
//    //
//    _dependencyCache = dependencyCache;
//    _fromTreeViewer = fromTreeViewer;
//    _toTreeViewer = toTreeViewer;
//    _reverse = reverse;
//  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getText(Object obj) {

    //
    IBundleMakerArtifact artifact = (IBundleMakerArtifact) obj;

    //
    StringBuilder stringBuilder = new StringBuilder();

    //
    stringBuilder.append(super.getText(obj));

    // if (_fromTreeViewer.getFilters().length > 0 && _toTreeViewer.getFilters().length > 0) {
    //
    // stringBuilder.append(" (");
    //
    // //
    // final VisibleArtifactsFilter fromFilter = (VisibleArtifactsFilter) _fromTreeViewer.getFilters()[0];
    // final VisibleArtifactsFilter toFilter = (VisibleArtifactsFilter) _toTreeViewer.getFilters()[0];
    //
    // //
    // final List<IDependency> dependencies = new LinkedList<IDependency>();
    //
    // // get the dependencies
    // artifact.accept(new IArtifactTreeVisitor.Adapter() {
    // @Override
    // public boolean onVisit(IBundleMakerArtifact artifact) {
    // if (_dependencyCache.containsKey(artifact)) {
    // for (IDependency iDependency : _dependencyCache.get(artifact)) {
    //
    // IBundleMakerArtifact origin = _reverse ? iDependency.getTo() : iDependency.getFrom();
    // IBundleMakerArtifact target = _reverse ? iDependency.getFrom() : iDependency.getTo();
    //
    // if (origin.equals(artifact) && fromFilter.getArtifacts().contains(origin)
    // && toFilter.getArtifacts().contains(target)) {
    // dependencies.add(iDependency);
    // }
    // }
    // }
    // return true;
    // }
    // });
    //
    // //
    // stringBuilder.append(dependencies.size());
    // stringBuilder.append(")");
    //
    // }

    //
    return stringBuilder.toString();
  }
}
