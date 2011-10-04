package org.bundlemaker.core.analysis;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;

/**
 * <p>
 * Defines an {@link IArtifact IArtifacts} that holds an {@link IModularizedSystem} instance.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IRootArtifact extends IBundleMakerArtifact, IGroupAndModuleContainer {

  // empty tag interface
}
