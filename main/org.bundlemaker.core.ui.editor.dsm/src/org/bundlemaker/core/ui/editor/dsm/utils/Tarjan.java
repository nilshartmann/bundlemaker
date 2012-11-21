package org.bundlemaker.core.ui.editor.dsm.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.eclipse.core.runtime.Assert;

/**
 */
public class Tarjan<T extends IBundleMakerArtifact> {

  /** - */
  private int                    _index                       = 0;

  /** - */
  private ArrayList<Integer>     _stack                       = new ArrayList<Integer>();

  /** - */
  private List<List<T>>          _stronglyConnectedComponents = new ArrayList<List<T>>();

  /** - */
  int[]                          _vlowlink;

  /** - */
  int[]                          _vindex;

  /** - */
  private IBundleMakerArtifact[] _artifacts;

  /**
   * @param artifacts
   * @return
   */
  public List<List<T>> executeTarjan(Collection<? extends T> artifacts) {
    Assert.isNotNull(artifacts);

    _artifacts = artifacts.toArray(new IBundleMakerArtifact[0]);
    int[][] adjacencyList = computeAdjacencyList(_artifacts);
    return executeTarjan(adjacencyList);
  }

  //
  private List<List<T>> executeTarjan(int[][] graph) {
    Assert.isNotNull(graph);

    // clear
    _stronglyConnectedComponents.clear();
    _index = 0;
    _stack.clear();
    _vlowlink = new int[graph.length];
    _vindex = new int[graph.length];
    for (int i = 0; i < _vlowlink.length; i++) {
      _vlowlink[i] = -1;
      _vindex[i] = -1;
    }

    //
    for (int i = 0; i < graph.length; i++) {
      if (_vindex[i] == -1) {
        tarjan(i, graph);
      }
    }

    //
    return _stronglyConnectedComponents;
  }

  @SuppressWarnings("unchecked")
  private void tarjan(int v, int[][] graph) {
    Assert.isNotNull(v);
    Assert.isNotNull(graph);

    _vindex[v] = _index;
    _vlowlink[v] = _index;

    _index++;
    _stack.add(0, v);
    for (int n : graph[v]) {
      if (_vindex[n] == -1) {
        tarjan(n, graph);
        _vlowlink[v] = Math.min(_vlowlink[v], _vlowlink[n]);
      } else if (_stack.contains(n)) {
        _vlowlink[v] = Math.min(_vlowlink[v], _vindex[n]);
      }
    }
    if (_vlowlink[v] == _vindex[v]) {
      int n;
      ArrayList<T> component = new ArrayList<T>();
      do {
        n = _stack.remove(0);
        component.add((T) _artifacts[n]);
      } while (n != v);
      _stronglyConnectedComponents.add(component);
    }
  }

  /**
   * @param artifacts
   */
  private int[][] computeAdjacencyList(IBundleMakerArtifact[] artifacts) {

    // prepare
    int i = 0;
    Map<IBundleMakerArtifact, Integer> map = new HashMap<IBundleMakerArtifact, Integer>();
    for (IBundleMakerArtifact iArtifact : artifacts) {
      map.put(iArtifact, i);
      i++;
    }

    //
    int[][] matrix = new int[artifacts.length][];

    //
    for (IBundleMakerArtifact artifact : artifacts) {

      // get the referenced artifacts
      Collection<? extends IDependency> dependencies = artifact.getDependenciesTo(Arrays.asList(artifacts));

      if (dependencies == null) {
        dependencies = Collections.emptyList();
      }
      
      //
      int index = map.get(artifact);
      matrix[index] = new int[dependencies.size()];

      // GENERICS HACK
      int count = 0;
      for (IDependency dependency : dependencies) {
        matrix[index][count] = map.get(dependency.getTo());
        count++;
      }
    }

    return matrix;
  }
}