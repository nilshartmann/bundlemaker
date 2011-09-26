package org.bundlemaker.core.analysis;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;

/**
 * <p>
 * Defines an {@link IArtifact IArtifacts} that holds an {@link IModularizedSystem} instance.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IRootArtifact extends IBundleMakerArtifact, IGroupAndModuleContainer {

  /**
   * {@inheritDoc}
   */
  public IModifiableModularizedSystem getModularizedSystem();
}
