package org.bundlemaker.core.itest.analysis.test;

import junit.framework.Assert;

import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itest.analysis.test.framework.AbstractSimpleArtifactModelTest;
import org.eclipse.core.runtime.IPath;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class GetChildByPathTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void findByFullPath() throws Exception {

    //
    _binModel.getRootArtifact().accept(new IArtifactTreeVisitor.Adapter() {

      @Override
      public boolean visit(IRootArtifact rootArtifact) {
        // skip the root artifact
        return true;
      }

      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {
        // skip non-resource artifacts
        if (moduleArtifact.isResourceModule()) {
          return onVisit(moduleArtifact);
        } else {
          return false;
        }
      }

      @Override
      public boolean onVisit(IBundleMakerArtifact artifact) {
        //
        IPath path = artifact.getFullPath();
        IBundleMakerArtifact bundleMakerArtifact = _binModel.getRootArtifact().getChildByPath(
            IBundleMakerArtifact.class, path);
        Assert.assertEquals(artifact, bundleMakerArtifact);
        //
        return true;
      }
    });
  }
}
