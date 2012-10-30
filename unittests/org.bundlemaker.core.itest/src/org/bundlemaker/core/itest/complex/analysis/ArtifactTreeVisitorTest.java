package org.bundlemaker.core.itest.complex.analysis;

import junit.framework.Assert;

import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
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
public class ArtifactTreeVisitorTest extends AbstractJeditArtifactTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testArtifactTreeVisitor() {

    final int[] resultCount = new int[7];

    getJeditModuleArtifact().accept(new IAnalysisModelVisitor.Adapter() {

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

    Assert.assertEquals(0, resultCount[0]);
    Assert.assertEquals(0, resultCount[1]);
    Assert.assertEquals(1, resultCount[2]);
    Assert.assertEquals(69, resultCount[3]);
    Assert.assertEquals(1302, resultCount[4]);
    Assert.assertEquals(975, resultCount[5]);
  }
}
