package org.bundlemaker.core.internal.analysis;

import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class InvalidateAggregatedDependencies implements IArtifactTreeVisitor {

  /** - */
  private List<IArtifact> _artifacts;

  /**
   * <p>
   * Creates a new instance of type {@link InvalidateAggregatedDependencies}.
   * </p>
   * 
   * @param artifacts
   */
  public InvalidateAggregatedDependencies(List<IArtifact> artifacts) {
    Assert.isNotNull(artifacts);

    _artifacts = artifacts;
  }

  @Override
  public boolean visit(IRootArtifact artifact) {
    invalidate(artifact);
    return true;
  }

  @Override
  public boolean visit(IGroupArtifact artifact) {
    invalidate(artifact);
    return true;
  }

  @Override
  public boolean visit(IModuleArtifact artifact) {
    invalidate(artifact);
    return true;
  }

  @Override
  public boolean visit(IResourceArtifact artifact) {
    invalidate(artifact);
    return true;
  }

  @Override
  public boolean visit(ITypeArtifact artifact) {
    invalidate(artifact);
    return true;
  }

  @Override
  public boolean visit(IPackageArtifact artifact) {
    invalidate(artifact);
    return true;
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  private void invalidate(IAdvancedArtifact artifact) {
    artifact.invalidateDependencyCache();
  }
}
