package org.bundlemaker.core.internal.modules.modularizedsystem;

import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.analysis.AdapterRoot2IArtifact;
import org.bundlemaker.core.internal.analysis.ModelTransformerCache;
import org.bundlemaker.core.projectdescription.IProjectDescription;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractArtifactModelAwareModularizedSystem extends AbstractQueryableModularizedSystem {

  /** - */
  private ModelTransformerCache _transformerCache = null;

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

    _transformerCache = new ModelTransformerCache();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifact getArtifactModel(IArtifactModelConfiguration configuration) {

    IRootArtifact result = (IRootArtifact) _transformerCache.getArtifactModel(this, configuration);

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void afterApplyTransformations() {
    super.afterApplyTransformations();

    //
    for (IRootArtifact rootArtifact : _transformerCache.getAllArtifactModels()) {
      ((AdapterRoot2IArtifact) rootArtifact).fireArtifactModelChanged();
    }
  }
}
