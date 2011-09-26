package org.bundlemaker.core.internal.modules.modularizedsystem;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;

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
  public AbstractArtifactModelAwareModularizedSystem(String name, IBundleMakerProjectDescription projectDescription) {
    super(name, projectDescription);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifact getArtifactModel(ArtifactModelConfiguration configuration) {

    IRootArtifact result = (IRootArtifact) ModelTransformer.getDependencyModel(this, configuration).getRoot();

    return result;
  }
}
