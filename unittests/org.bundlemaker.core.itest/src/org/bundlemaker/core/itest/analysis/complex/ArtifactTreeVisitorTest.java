package org.bundlemaker.core.itest.analysis.complex;

import junit.framework.Assert;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.junit.Test;

/**
 * <p>
 * Example: group1/group2/jedit_1.0.0 velocity_1.5 jdk16_jdk16 << Missing Types >>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactTreeVisitorTest extends AbstractComplexTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testArtifactTreeVisitor() {

    final int[] resultCount = new int[7];

    getRootArtifact().accept(new IArtifactTreeVisitor.Adapter() {

      @Override
      public boolean visit(IRootArtifact rootArtifact) {
        resultCount[0]++;
        return true;
      }

      @Override
      public boolean visit(IGroupArtifact rootArtifact) {
        resultCount[1]++;
        return true;
      }

      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {
        System.out.println(moduleArtifact.getQualifiedName());
        resultCount[2]++;
        return true;
      }

      @Override
      public boolean visit(IPackageArtifact packageArtifact) {
        resultCount[3]++;
        return true;
      }

      @Override
      public boolean visit(IResourceArtifact resourceArtifact) {
        resultCount[4]++;
        return true;
      }

      @Override
      public boolean visit(ITypeArtifact typeArtifact) {
        resultCount[5]++;
        return true;
      }
    });

    Assert.assertEquals(1, resultCount[0]);
    Assert.assertEquals(2, resultCount[1]);
    Assert.assertEquals(4, resultCount[2]);
    Assert.assertEquals(879, resultCount[3]);
    Assert.assertEquals(1547, resultCount[4]);
    Assert.assertEquals(17483, resultCount[5]);
  }
}
