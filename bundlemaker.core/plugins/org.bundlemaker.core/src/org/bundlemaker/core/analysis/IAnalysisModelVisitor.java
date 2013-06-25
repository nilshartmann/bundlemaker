package org.bundlemaker.core.analysis;

import org.bundlemaker.core._type.ITypeArtifact;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IAnalysisModelVisitor {

  /**
   * <p>
   * </p>
   * 
   * @param rootArtifact
   * @return
   */
  boolean visit(IRootArtifact rootArtifact);

  /**
   * <p>
   * </p>
   * 
   * @param rootArtifact
   * @return
   */
  boolean visit(IGroupArtifact rootArtifact);

  /**
   * <p>
   * </p>
   * 
   * @param moduleArtifact
   * @return
   */
  boolean visit(IModuleArtifact moduleArtifact);

  /**
   * <p>
   * </p>
   * 
   * @param resourceArtifact
   * @return
   */
  boolean visit(IResourceArtifact resourceArtifact);

  /**
   * <p>
   * </p>
   * 
   * @param typeArtifact
   * @return
   */
  @Deprecated
  boolean visit(ITypeArtifact typeArtifact);

  /**
   * <p>
   * </p>
   * 
   * @param packageArtifact
   * @return
   */
  boolean visit(IPackageArtifact packageArtifact);

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public class Adapter implements IAnalysisModelVisitor {

    @Override
    public boolean visit(IRootArtifact rootArtifact) {
      return onVisit(rootArtifact);
    }

    @Override
    public boolean visit(IGroupArtifact groupArtifact) {
      return onVisit(groupArtifact);
    }

    @Override
    public boolean visit(IModuleArtifact moduleArtifact) {
      return onVisit(moduleArtifact);
    }

    @Override
    public boolean visit(IResourceArtifact resourceArtifact) {
      return onVisit(resourceArtifact);
    }

    @Override
    public boolean visit(ITypeArtifact typeArtifact) {
      return onVisit(typeArtifact);
    }

    @Override
    public boolean visit(IPackageArtifact packageArtifact) {
      return onVisit(packageArtifact);
    }

    /**
     * <p>
     * </p>
     * 
     * @param rootArtifact
     * @return
     */
    public boolean onVisit(IBundleMakerArtifact artifact) {
      return true;
    }
  }
}
