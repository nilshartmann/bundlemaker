package org.bundlemaker.core.analysis;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.core.modules.IModularizedSystem;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IAdvancedArtifact extends IArtifact {

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  boolean canAdd(IArtifact artifact);

  /**
   * <p>
   * Returns the {@link IModularizedSystem} this artifact belongs to.
   * </p>
   * 
   * @return the modularized system, never null
   */
  public IModularizedSystem getModularizedSystem();

  /**
   * <p>
   * Returns the associated {@link IDependencyModel}.
   * </p>
   * 
   * @return the associated {@link IDependencyModel}.
   */
  public IDependencyModel getDependencyModel();
}
