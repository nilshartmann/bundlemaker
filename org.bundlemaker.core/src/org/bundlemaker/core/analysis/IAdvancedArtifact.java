package org.bundlemaker.core.analysis;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;

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
   * @return
   */
  String getIdentifier();

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
   * </p>
   * 
   * @param identifier
   * @return
   */
  IArtifact getChildByIdentifier(String identifier);

  /**
   * <p>
   * </p>
   * 
   * @param path
   * @return
   */
  IArtifact getChild(String path);

  /**
   * Returns the {@link IModularizedSystem} this artifact belongs to
   * 
   * @return the modularized system, never null
   */
  public IModularizedSystem getModularizedSystem();

  // public IArtifact findChild();
}
