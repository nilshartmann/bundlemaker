package org.bundlemaker.core.itest.analysis.test;

import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.itest.analysis.test.framework.AbstractSimpleArtifactModelTest;
import org.junit.Test;

public class GroupRemoveTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void removeGroupArtifact() throws Exception {

    // check the group count
    assertGroupCountInModularizedSystem(2);
    assertGroupCount(_binModel, 2);
    assertGroupCount(_srcModel, 2);
    
    //
    _binModel.getRootArtifact().removeArtifact(_binModel.getGroup1Artifact());
    
   //ArtifactUtils.dumpArtifact(_binModel.getRootArtifact());
    
    // check the group count
    assertGroupCount(_binModel, 0);
    assertGroupCount(_srcModel, 0);
    assertGroupCountInModularizedSystem(0);
  }

}
