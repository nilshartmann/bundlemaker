package org.bundlemaker.core.itest.jedit_artifact_model;

import org.bundlemaker.core._type.ITypeArtifact;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itestframework.jedit_model.AbstractJeditAnalysisModelTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MissingTypesTest extends AbstractJeditAnalysisModelTest {

  @Test
  public void missingTypes_BinFlatModel() {
    missingTypes(getBinFlatModel().getRootArtifact());
  }

  @Test
  public void missingTypes_SrcFlatModel() {
    missingTypes(getSrcFlatModel().getRootArtifact());
  }

  @Test
  public void missingTypes_BinHierarchicalModel() {
    missingTypes(getBinHierarchicalModel().getRootArtifact());
  }

  @Test
  public void missingTypes_SrcHierarchicalModel() {
    missingTypes(getSrcHierarchicalModel().getRootArtifact());
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootArtifact
   */
  private void missingTypes(IRootArtifact rootArtifact) {

    // get the 'missing types' module artifact
    IModuleArtifact moduleArtifact = AnalysisModelQueries.getMissingTypesModuleArtifact(rootArtifact);
    Assert.assertNotNull(moduleArtifact);

    // the missing type count
    final int[] missingTypesCount = new int[1];

    // assert 'virtual' & type count
    moduleArtifact.accept(new IAnalysisModelVisitor.Adapter() {
      
      @Override
      public boolean visit(ITypeArtifact typeArtifact) {
        missingTypesCount[0]++;
        return false;
      }
      
      @Override
      public boolean onVisit(IBundleMakerArtifact artifact) {
        Assert.assertTrue(artifact.isVirtual());
        return true;
      }
    });
    
    //
    Assert.assertEquals(52, missingTypesCount[0]);
  }
}
