package org.bundlemaker.core.analysis;

import org.bundlemaker.analysis.model.IArtifact;
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

  // /**
  // * <p>
  // * </p>
  // *
  // * @param identifier
  // * @return
  // */
  // IArtifact getChildByIdentifier(String identifier);

  // /**
  // * <p>
  // * </p>
  // *
  // * @return
  // */
  // String getIdentifier();

  /**
   * Returns the {@link IModularizedSystem} this artifact belongs to
   * 
   * @return the modularized system, never null
   */
  public IModularizedSystem getModularizedSystem();

  // public IArtifact findChild();
}
