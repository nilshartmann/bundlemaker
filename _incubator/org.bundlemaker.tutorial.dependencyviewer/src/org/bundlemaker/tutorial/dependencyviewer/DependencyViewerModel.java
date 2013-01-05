/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.tutorial.dependencyviewer;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DependencyViewerModel {
  private final static int                                MAX_WIDTH        = 10;

  int                                                     minWeight        = 1;

  int                                                     maxWeight        = 1;

  private List<DependencyViewerSnapshot>                  _snapshots       = new LinkedList<DependencyViewerSnapshot>();

  private final Set<IDependencyViewerModelChangeListener> _listener        = new CopyOnWriteArraySet<IDependencyViewerModelChangeListener>();

  private int                                             _currentSnapshot = 0;

  /**
   * @param artifacts
   */
  public DependencyViewerModel() {
    _snapshots.add(new DependencyViewerSnapshot(new LinkedList<IBundleMakerArtifact>(), null));

  }

  /**
   * @return the artifacts
   */
  public List<IBundleMakerArtifact> getArtifacts() {
    return currentSnapshot().getArtifacts();
  }

  private DependencyViewerSnapshot currentSnapshot() {
    return _snapshots.get(_currentSnapshot);
  }

  /**
   * @param artifacts
   *          the artifacts to set
   */
  public void setArtifacts(List<IBundleMakerArtifact> artifacts) {
    _snapshots.add(new DependencyViewerSnapshot(artifacts, null));

    calulateWidths(artifacts);

    selectNewestSnapshot();
  }

  public int getWeight(IDependency dependency) {
    int ratio = maxWeight / minWeight;
    System.out.println("maxWeiht: " + maxWeight);
    double factor = (double) MAX_WIDTH / (double) maxWeight;

    double result = dependency.getWeight() * factor;
    System.out.println("ratio: " + ratio + ", factor: " + factor + ", weight:  " + dependency.getWeight()
        + ", result: " + result);
    return (int) result;
  }

  /**
   * @param bundleMakerArtifact
   */
  public void hideArtifacts(IBundleMakerArtifact... bundleMakerArtifacts) {
    if (bundleMakerArtifacts != null) {

      List<IBundleMakerArtifact> hiddenArtifacts = currentSnapshot().getHiddenArtifacts();

      HashSet<IBundleMakerArtifact> newHiddenArtifacts = new HashSet<IBundleMakerArtifact>(hiddenArtifacts);

      for (IBundleMakerArtifact iBundleMakerArtifact : bundleMakerArtifacts) {
        newHiddenArtifacts.add(iBundleMakerArtifact);
      }

      _snapshots.add(new DependencyViewerSnapshot(currentSnapshot().getArtifacts(), newHiddenArtifacts));

      selectNewestSnapshot();
    }

  }

  /**
   * 
   */
  private void selectNewestSnapshot() {
    // TODO: set current snapshot to last in history

    int newestSnapshot = _snapshots.size() - 1;

    selectSnapshot(newestSnapshot);

  }

  private void selectSnapshot(int newSnapshot) {
    if (newSnapshot != _currentSnapshot) {
      _currentSnapshot = newSnapshot;
      fireModelChangeEvent();
    }
  }

  /**
   * 
   */
  private void fireModelChangeEvent() {
    for (IDependencyViewerModelChangeListener listener : this._listener) {
      listener.modelChanged();
    }
  }

  /**
   * 
   */
  private void calulateWidths(Iterable<IBundleMakerArtifact> artifacts) {

    this.minWeight = 1;
    this.maxWeight = 1;

    for (IBundleMakerArtifact from : artifacts) {
      for (IBundleMakerArtifact to : artifacts) {

        if (from == to) {
          continue;
        }

        IDependency dependency = from.getDependencyTo(to);
        int weight = (dependency == null ? 0 : dependency.getWeight());

        minWeight = Math.min(weight, minWeight);
        maxWeight = Math.max(weight, maxWeight);

      }
    }

    minWeight = Math.max(minWeight, 1);
    maxWeight = Math.max(maxWeight, 1);

    System.out.println("min :" + minWeight + ",max: " + maxWeight);

  }

  /**
   * 
   */
  public void clearHiddenNodes() {
    _snapshots.add(new DependencyViewerSnapshot(getArtifacts(), null));

    selectNewestSnapshot();
  }

  public void addDependencyViewerModelChangeListener(IDependencyViewerModelChangeListener listener) {
    this._listener.add(listener);
  }

  /**
   * @param bundleMakerArtifact
   * @return
   */
  public boolean isHiddenArtifact(IBundleMakerArtifact bundleMakerArtifact) {
    return currentSnapshot().getHiddenArtifacts().contains(bundleMakerArtifact);
  }

  /**
   * Makes sure, that all dependencies of the specified {@link IBundleMakerArtifact} are displayed
   * 
   * @param bundleMakerArtifact
   */
  public void showDependencies(IBundleMakerArtifact bundleMakerArtifact) {

    if (bundleMakerArtifact == null) {
      return;
    }

    List<IBundleMakerArtifact> currentArtifacts = currentSnapshot().getArtifacts();
    List<IBundleMakerArtifact> newArtifacts = new LinkedList<IBundleMakerArtifact>(currentArtifacts);

    Collection<IDependency> dependenciesTo = bundleMakerArtifact.getDependenciesTo();
    for (IDependency iDependency : dependenciesTo) {
      IBundleMakerArtifact to = iDependency.getTo();

      IBundleMakerArtifact parent = to.getParent(bundleMakerArtifact.getClass());

      if (parent != null && !newArtifacts.contains(parent)) {
        newArtifacts.add(parent);
      }
    }

    if (!currentArtifacts.equals(newArtifacts)) {
      DependencyViewerSnapshot newSnapshot = new DependencyViewerSnapshot(newArtifacts, currentSnapshot()
          .getHiddenArtifacts());
      _snapshots.add(newSnapshot);
      calulateWidths(newArtifacts);
      selectNewestSnapshot();
    }

  }

  /**
   * Add all childs of the specified artifact to the model
   * 
   * @param bundleMakerArtifact
   */
  public void showArtifactContent(IBundleMakerArtifact bundleMakerArtifact) {

    List<IBundleMakerArtifact> currentArtifacts = currentSnapshot().getArtifacts();

    Set<IBundleMakerArtifact> newArtifacts = new HashSet<IBundleMakerArtifact>(currentArtifacts);
    // Remove selected node...
    newArtifacts.remove(bundleMakerArtifact);

    // ...add it's children instead
    newArtifacts.addAll(bundleMakerArtifact.getChildren());

    DependencyViewerSnapshot newSnapshot = new DependencyViewerSnapshot(newArtifacts, currentSnapshot()
        .getHiddenArtifacts());
    _snapshots.add(newSnapshot);
    calulateWidths(newArtifacts);
    selectNewestSnapshot();

  }

  public boolean hasPreviousSnapshot() {
    return _currentSnapshot > 0;
  }

  public boolean hasNextSnapshot() {
    return _currentSnapshot < _snapshots.size() - 1;
  }

  public void showNextSnapshot() {
    int newSnapshot = _currentSnapshot + 1;
    if (newSnapshot > _snapshots.size() - 1) {
      newSnapshot = _snapshots.size() - 1;
    }

    selectSnapshot(newSnapshot);
  }

  public void showPreviousSnapshot() {
    int newSnapshot = _currentSnapshot - 1;
    if (newSnapshot < 0) {
      newSnapshot = 0;
    }

    selectSnapshot(newSnapshot);
  }
}
