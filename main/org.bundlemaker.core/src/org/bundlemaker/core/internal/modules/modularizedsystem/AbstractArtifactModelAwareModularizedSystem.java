package org.bundlemaker.core.internal.modules.modularizedsystem;

import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ModelTransformerCache;
import org.bundlemaker.core.projectdescription.IProjectDescription;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractArtifactModelAwareModularizedSystem extends AbstractQueryableModularizedSystem {

  /**
   * <p>
   * Creates a new instance of type {@link AbstractArtifactModelAwareModularizedSystem}.
   * </p>
   * 
   * @param name
   * @param projectDescription
   */
  public AbstractArtifactModelAwareModularizedSystem(String name, IProjectDescription projectDescription) {
    super(name, projectDescription);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifact getArtifactModel(IArtifactModelConfiguration configuration) {

    IRootArtifact result = (IRootArtifact) ModelTransformerCache.getArtifactModel(this, configuration);

    return result;
  }
}
