package org.bundlemaker.core.spi.analysis;

import java.util.Collection;
import java.util.Map;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;

/**
 * <p>
 * Specifies an {@link IBundleMakerArtifact} that can be referenced by other {@link IBundleMakerArtifact
 * IBundleMakerArtifacts}. An {@link IBundleMakerArtifact} that implements this interface can delegate all methods to an
 * inner instance of {@link ReferencedArtifactTrait}:
 * 
 * <pre>
 * <code>
 * public class MyArtifact extends AbstractArtifact implements IReferencedArtifact {
 * 
 *   private ReferencedArtifactTrait  _referencedArtifact = new ReferencedArtifactTrait() {
 *     
 *     protected void initialize() {
 *       // initialize dependencies here...
 *     }
 *   };
 * 
 *   public Collection<IDependency> getDependenciesFrom() {
 *     return _referencedArtifact.getDependenciesFrom();
 *   }
 *   
 *   ...
 * }
 * </code>
 * </pre>
 * 
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IReferencedArtifact {

  /**
   * <p>
   * Returns a collection with all dependencies <b>to</b> this {@link IBundleMakerArtifact}.
   * </p>
   * 
   * @return a collection with all dependencies <b>to</b> this {@link IBundleMakerArtifact}.
   */
  Collection<IDependency> getDependenciesFrom();

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  boolean hasDependencyFrom(IBundleMakerArtifact artifact);

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  IDependency getDependencyFrom(IBundleMakerArtifact artifact);

  /**
   * <p>
   * </p>
   * 
   * @param artifacts
   * @return
   */
  Collection<? extends IDependency> getDependenciesFrom(Collection<? extends IBundleMakerArtifact> artifacts);

  /**
   * <p>
   * </p>
   * 
   * @param artifacts
   * @return
   */
  Collection<? extends IDependency> getDependenciesFrom(IBundleMakerArtifact... artifacts);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Map<IBundleMakerArtifact, IDependency> coreDependenciesFromMap();
}
