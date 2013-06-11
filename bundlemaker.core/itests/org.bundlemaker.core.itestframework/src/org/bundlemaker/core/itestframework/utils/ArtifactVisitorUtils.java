package org.bundlemaker.core.itestframework.utils;

import static org.hamcrest.CoreMatchers.is;

import org.bundlemaker.core._type.analysis.ITypeArtifact;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.spi.analysis.IReferencedArtifact;
import org.bundlemaker.core.spi.analysis.IReferencingArtifact;
import org.junit.Assert;

public class ArtifactVisitorUtils {
  
  /**
   * <p>
   * </p>
   *
   */
  public static void checkDependencies(IBundleMakerArtifact artifact, int coreDependenciesTo, int coreDependenciesFrom) {
    
    //
    final int[] cumulatedDependencies = new int[2];

    //
    artifact.accept(new IAnalysisModelVisitor.Adapter() {

      @Override
      public boolean onVisit(IBundleMakerArtifact artifact) {

        //
        if (artifact instanceof IReferencingArtifact) {
          int dependenciesCount = ((IReferencingArtifact) artifact).getDependenciesTo().size();
          cumulatedDependencies[0] += dependenciesCount;
        }

        //
        if (artifact instanceof IReferencedArtifact) {
          int dependenciesCount = ((IReferencedArtifact) artifact).getDependenciesFrom().size();
          cumulatedDependencies[1] += dependenciesCount;
        }

        //
        return super.onVisit(artifact);
      }
    });

    Assert.assertThat(cumulatedDependencies[0], is(coreDependenciesTo));
    Assert.assertThat(cumulatedDependencies[1], is(coreDependenciesFrom));
  }
  

  public static void countArtifacts(IBundleMakerArtifact artifact, int rootCount, int groupCount, int moduleCount,
      int packageCount, int resourceCount, int typeCount) {

    //
    final int[] resultCount = new int[7];

    artifact.accept(new IAnalysisModelVisitor.Adapter() {

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

    Assert.assertEquals(rootCount, resultCount[0]);
    Assert.assertEquals(groupCount, resultCount[1]);
    Assert.assertEquals(moduleCount, resultCount[2]);
    Assert.assertEquals(packageCount, resultCount[3]);
    Assert.assertEquals(resourceCount, resultCount[4]);
    Assert.assertEquals(typeCount, resultCount[5]);
  }
}
