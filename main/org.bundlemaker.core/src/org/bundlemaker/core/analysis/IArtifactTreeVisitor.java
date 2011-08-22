package org.bundlemaker.core.analysis;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IArtifactTreeVisitor {

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
  public class Adapter implements IArtifactTreeVisitor {

    @Override
    public boolean visit(IRootArtifact rootArtifact) {
      return true;
    }

    @Override
    public boolean visit(IGroupArtifact rootArtifact) {
      return true;
    }

    @Override
    public boolean visit(IModuleArtifact moduleArtifact) {
      return true;
    }

    @Override
    public boolean visit(IResourceArtifact resourceArtifact) {
      return true;
    }

    @Override
    public boolean visit(ITypeArtifact typeArtifact) {
      return true;
    }

    @Override
    public boolean visit(IPackageArtifact packageArtifact) {
      return true;
    }
  }
}
