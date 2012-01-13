package org.bundlemaker.analysis.model.dependencies;

import java.util.Collection;
import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.sonar.graph.Dsm;

public interface IDependencyGraph {

  /**
   * Gibt die Menge der Artefakte zurueck
   * 
   * @return <code>Collection</code> der Artefakte
   */
  Collection<? extends IArtifact> getArtifacts();

  /**
   * Gibt die Menge der Abhaengigkeiten zurueck
   */
  Collection<? extends IDependency> getDependencies();

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
  Dsm<? extends IArtifact> getDsm();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Deprecated
  List<? extends IDependency> getIgnoredDependencies();
}