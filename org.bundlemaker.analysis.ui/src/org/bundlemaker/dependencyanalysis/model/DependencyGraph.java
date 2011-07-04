/*--- (C) 1999-2010 Techniker Krankenkasse ---*/

package org.bundlemaker.dependencyanalysis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.rules.Severity;
import org.bundlemaker.analysis.rules.Violation;
import org.bundlemaker.dependencyanalysis.model.impl.ArtifactEdgeFactory;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.sonar.graph.CycleDetector;
import org.sonar.graph.DirectedGraph;
import org.sonar.graph.Dsm;
import org.sonar.graph.DsmTopologicalSorter;
import org.sonar.graph.Edge;
import org.sonar.graph.MinimumFeedbackEdgeSetSolver;

/**
 * Diese Interface beschreibt einen Abhaengigikeitsgraph.
 * 
 * <p>
 * Ein Abhaengigkeitsgraph besteht formal aus einer Menge von Artefakten sowie eine Menge von Abhaengigkeiten, die eine
 * Teilmenge des kartesischen Produktes der Menge der Artefakte ist.
 * 
 * 
 * 
 * @author Kai Lehmann
 * 
 */
public class DependencyGraph implements Serializable {
  private static final int                            MAX_DSM_ARTIFACTS         = 300;

  // Map Artifact -> Collection of Dependencies to Artifacts
  private HashMap<IArtifact, Collection<IDependency>> artifactsMap;

  private Set<Cycle>                                  cycles                    = new HashSet<Cycle>();

  private Dsm<IArtifact>                              dsm;

  private List<IDependency>                           ignoredDependencies;

  private boolean                                     isInvalid                 = false;

  private boolean                                     hasTooMuchArtifactsForDSM = false;

  // Rules
  private Violation                                   cycleViolation;

  /**
   * Methode, die aus den uebergebenen Artefakten ein Abhaengigkeitsgraph erzeugt. Es erfolgt eine Partitionierung des
   * Abhaengigkeitsgraphen durch den DSM-Algorithmus von SonarGraph sowie eine Bestimmung der starken
   * Zusammenhangskomponenten ueber den Algorithmus von Tarjan. Abschließend wird das Regelwerk auf den
   * Abhaengigkeitsgraphen angewendet.
   * 
   * @param artifacts
   */
  public static DependencyGraph calculateDependencyGraph(Collection<IArtifact> artifacts) {
    DependencyGraph newGraph = new DependencyGraph(artifacts);
    newGraph.calculateDependencies();

    return newGraph;
  }

  public DependencyGraph(Collection<IArtifact> artifacts) {
    artifactsMap = new HashMap<IArtifact, Collection<IDependency>>();
    cycles = new HashSet<Cycle>();

    for (IArtifact artifact : artifacts) {
      artifactsMap.put(artifact, new ArrayList<IDependency>());
    }

    cycleViolation = new Violation(Severity.ERROR, "Die Abhängigkeit führt zu einem Zyklus");

    if ((getArtifacts().size() > MAX_DSM_ARTIFACTS)) {
      MessageBox msgBox = new MessageBox(Display.getCurrent().getActiveShell());
      msgBox.setMessage("Zu viele Artefakte für den DSM: " + getArtifacts().size());
      msgBox.open();
      dsm = null;
      hasTooMuchArtifactsForDSM = true;
    } else {
      hasTooMuchArtifactsForDSM = false;
    }

  }

  public Set<Cycle> getCycles() {
    return cycles;
  }

  public void setInvalid(boolean isInvalid) {
    this.isInvalid = isInvalid;
  }

  /**
   * Gibt die Menge der Artefakte zurueck
   * 
   * @return <code>Collection</code> der Artefakte
   */
  public Collection<IArtifact> getArtifacts() {
    return new ArrayList(artifactsMap.keySet());
  }

  public int getNumberOfCycles() {
    return cycles.size();
  }

  public void calculateDependencies() {
    for (IArtifact artifact : artifactsMap.keySet()) {
      calculateDependencies(artifact);
    }
    if (!hasTooMuchArtifactsForDSM) {
      calculateDSM();
    } else {
      dsm = null;
    }
  }

  private void calculateDependencies(IArtifact artifact) {
    artifactsMap.put(artifact, artifact.getDependencies(artifactsMap.keySet()));
  }

  public void calculateCycles() {
    for (IArtifact artifact : artifactsMap.keySet()) {
      calculateCycle(artifact, new ArrayList<IArtifact>(), new Cycle());
    }
  }

  private void calculateCycle(IArtifact artifact, List<IArtifact> visitedArtifacts, Cycle cycle) {
    if (visitedArtifacts.contains(artifact)) {
      return;
    } else {
      visitedArtifacts.add(artifact);
      for (IDependency dependency : artifactsMap.get(artifact)) {
        if (dependency.getFrom().equals(artifact)) {
          Cycle newCycle = (Cycle) cycle.clone();
          newCycle.addDependency(dependency);
          if (newCycle.isCycle()) {
            cycles.add(newCycle);
            return;
          } else {
            calculateCycle(dependency.getTo(), new ArrayList<IArtifact>(visitedArtifacts), newCycle);
          }
        }
      }
    }

  }

  /**
   * Gibt die Menge der Abhaengigkeiten zurueck
   */
  public Collection<IDependency> getDependencies() {
    Collection<IDependency> dependenciesSet = new HashSet<IDependency>();
    for (Collection<IDependency> dependencies : artifactsMap.values()) {
      dependenciesSet.addAll(dependencies);
    }

    return dependenciesSet;
  }

  /**
   * <p>
   * Gibt die Artefakte in einer partitionierten Dependency Structure Matrix (DSM) zurueck.
   * 
   * <p>
   * Zur Partitionierung wird der Partitionierungsalgorihmus von Sonar-Graph verwendet. Ebenso die Datenstruktur des DSM
   * von Sonargraph
   * 
   * @return
   */
  public Dsm<IArtifact> getDsm() {
    if (!isInvalid && (dsm == null)) {
      if (!hasTooMuchArtifactsForDSM) {
        calculateDSM();
      } else {
        dsm = null;
      }
    }
    return dsm;
  }

  public List<IDependency> getIgnoredDependencies() {
    return ignoredDependencies;
  }

  private void calculateDSM() {
    DirectedGraph<IArtifact, DependencyEdge> directedGraph = new DirectedGraph<IArtifact, DependencyEdge>(
        new ArtifactEdgeFactory());

    directedGraph.addVertices(this.getArtifacts());

    for (IDependency dependency : this.getDependencies()) {
      boolean isTaggedIgnore = false;
      // TODO ###REFACTORING
      if (/* DependencyRoot.isTransformedModel() && */dependency.isTaggedIgnore()) {
        isTaggedIgnore = true;
        if (ignoredDependencies == null) {
          ignoredDependencies = new ArrayList<IDependency>();
        }
        ignoredDependencies.add(dependency);
      }

      if (!isTaggedIgnore) {
        directedGraph.addEdge(new DependencyEdge(dependency));
      } else {
        System.out.println("Ignored Dependency: " + dependency);
      }
    }

    // Zyklen finden
    CycleDetector<IArtifact> cycleDetector = new CycleDetector<IArtifact>(directedGraph);

    cycleDetector.detectCyclesWithMaxSearchDepth(5);

    // Minimum Feedback Edge Algorithmus zur Analyse der problematischen Artefakte
    MinimumFeedbackEdgeSetSolver solver = new MinimumFeedbackEdgeSetSolver(cycleDetector.getCycles());

    for (Edge<IArtifact> edge : solver.getEdges()) {
      DependencyEdge dependency = (DependencyEdge) edge;
      dependency.addViolation(cycleViolation);
    }

    dsm = new Dsm<IArtifact>(directedGraph, solver.getEdges());

    // Dsm sortieren
    DsmTopologicalSorter.sort(dsm);

  }

}

/*--- Formatiert nach TK Code Konventionen vom 05.03.2002 ---*/
