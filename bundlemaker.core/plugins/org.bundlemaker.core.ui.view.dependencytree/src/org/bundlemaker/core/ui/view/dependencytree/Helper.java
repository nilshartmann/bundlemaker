package org.bundlemaker.core.ui.view.dependencytree;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.common.collections.GenericCache;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Helper {

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
  private List<IDependency>                                     _unfilteredDependencies = new LinkedList<IDependency>();

  /** - */
  private List<IDependency>                                     _filteredDependencies   = new LinkedList<IDependency>();

  /**
   * <p>
   * </p>
   * 
   * @param dependencies
   */
  public void setDependencies(List<IDependency> dependencies) {

    //
    Assert.isNotNull(dependencies);

    //
    _unfilteredDependencies = AnalysisModelQueries.getCoreDependencies(dependencies);

    //
    _sourceArtifactMap.clear();
    _targetArtifactMap.clear();

    //
    for (IDependency dependency : _unfilteredDependencies) {
      _sourceArtifactMap.getOrCreate(dependency.getFrom()).add(dependency);
      _targetArtifactMap.getOrCreate(dependency.getTo()).add(dependency);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param toArtifacts
   */
  public Set<IBundleMakerArtifact> setSelectedToArtifacts(List<IBundleMakerArtifact> toArtifacts) {

    //
    Assert.isNotNull(toArtifacts);

    //
    Set<IBundleMakerArtifact> filteredArtifacts = new HashSet<IBundleMakerArtifact>();
    _filteredDependencies.clear();

    for (IBundleMakerArtifact bundleMakerArtifact : toArtifacts) {

      // we have to find all children
      for (IBundleMakerArtifact artifact : AnalysisModelQueries.getSelfAndAllChildren(bundleMakerArtifact)) {
        if (_targetArtifactMap.containsKey(artifact)) {
          List<IDependency> dependencies = _targetArtifactMap.get(artifact);
          _filteredDependencies.addAll(dependencies);
          for (IDependency dep : dependencies) {
            filteredArtifacts.add(dep.getFrom());
          }
        }
      }
    }

    //
    return filteredArtifacts;
  }

  /**
   * <p>
   * </p>
   * 
   * @param fromArtifacts
   * @return
   */
  public Set<IBundleMakerArtifact> setSelectedFromArtifacts(List<IBundleMakerArtifact> fromArtifacts) {

    //
    Assert.isNotNull(fromArtifacts);

    // create empty lists of visible artifacts / selected detail dependencies
    Set<IBundleMakerArtifact> visibleArtifacts = new HashSet<IBundleMakerArtifact>();
    _filteredDependencies.clear();

    // iterate over all the selected artifacts
    for (IBundleMakerArtifact bundleMakerArtifact : fromArtifacts) {

      // we have to find all children
      for (IBundleMakerArtifact artifact : AnalysisModelQueries.getSelfAndAllChildren(bundleMakerArtifact)) {
        if (_sourceArtifactMap.containsKey(artifact)) {
          List<IDependency> dependencies = _sourceArtifactMap.get(artifact);
          _filteredDependencies.addAll(dependencies);
          for (IDependency dep : dependencies) {
            visibleArtifacts.add(dep.getTo());
          }
        }
      }
    }

    //
    return visibleArtifacts;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Collection<IBundleMakerArtifact> getUnfilteredSourceArtifacts() {
    return _sourceArtifactMap.keySet();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Collection<IBundleMakerArtifact> getUnfilteredTargetArtifacts() {
    return _targetArtifactMap.keySet();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<IDependency> getUnfilteredDependencies() {
    return _unfilteredDependencies;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<IDependency> getFilteredDependencies() {
    return _filteredDependencies;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public GenericCache<IBundleMakerArtifact, List<IDependency>> getTargetArtifactMap() {
    return _targetArtifactMap;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public GenericCache<IBundleMakerArtifact, List<IDependency>> getSourceArtifactMap() {
    return _sourceArtifactMap;
  }

  /**
   * <p>
   * Helper method.
   * </p>
   * 
   * @param objects
   * @return
   */
  public static List<IBundleMakerArtifact> toArtifactList(List<?> objects) {

    //
    Assert.isNotNull(objects);

    //
    List<IBundleMakerArtifact> result = new LinkedList<IBundleMakerArtifact>();

    //
    for (Object object : objects) {
      if (object instanceof IBundleMakerArtifact) {
        result.add((IBundleMakerArtifact) object);
      }
    }

    //
    return result;
  }
  
  public static List<IBundleMakerArtifact> toArtifactList(ISelection selection) {
    
    if (!(selection instanceof IStructuredSelection)) {
      return Collections.emptyList();
    }

    //
    //
    List<IBundleMakerArtifact> result = new LinkedList<IBundleMakerArtifact>();

    //
    Iterator<?> it = ((IStructuredSelection)selection).iterator();
    while (it.hasNext()) {
      Object object = it.next();
      if (object instanceof IBundleMakerArtifact) {
        result.add((IBundleMakerArtifact) object);
      }
    }

    //
    return result;
  }

}
