package org.bundlemaker.core.ui.dsmview.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.eclipse.core.runtime.Assert;

/**
 */
public class Tarjan<T extends IArtifact> {

  /** - */
  private int                _index                       = 0;

  /** - */
  private ArrayList<Integer> _stack                       = new ArrayList<Integer>();

  /** - */
  private List<List<T>>      _stronglyConnectedComponents = new ArrayList<List<T>>();

  /** - */
  int[]                      _vlowlink;

  /** - */
  int[]                      _vindex;

  /** - */
  private IArtifact[]        _artifacts;

  /**
   * @param artifacts
   * @return
   */
  public List<List<T>> executeTarjan(Collection<? extends T> artifacts) {
    Assert.isNotNull(artifacts);

    _artifacts = artifacts.toArray(new IArtifact[0]);
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
  private List<List<T>> tarjan(int v, int[][] graph) {
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
    return _stronglyConnectedComponents;
  }

  /**
   * @param children
   */
  private int[][] computeAdjacencyList(IArtifact[] children) {

    //
    // List<IArtifact> bundleMakerArtifacts = new LinkedList<IArtifact>(
    // children);

    // prepare
    int i = 0;
    Map<IArtifact, Integer> map = new HashMap<IArtifact, Integer>();
    for (IArtifact iArtifact : children) {
      map.put(iArtifact, i);
      i++;
    }

    //
    int[][] matrix = new int[children.length][];

    //
    for (IArtifact artifact : children) {

      // get the referenced artifacts
      Collection<? extends IDependency> dependencies = artifact.getDependencies(Arrays.asList(children));

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