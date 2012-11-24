package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class InvalidateAggregatedDependencies implements IAnalysisModelVisitor {

  @Override
  public boolean visit(IRootArtifact artifact) {
    // invalidate(artifact);
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
  private void invalidate(IBundleMakerArtifact artifact) {
    artifact.invalidateCaches();
  }
}
