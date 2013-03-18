package org.bundlemaker.core.itest.jedit_artifact_model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import junit.framework.Assert;

import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itestframework.jedit_model.AbstractJeditAnalysisModelTest;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class GetOrCreateNewModule extends AbstractJeditAnalysisModelTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void createNewModule_BinFlatModel() {
    getOrCreateModule(getBinFlatModel().getRootArtifact());
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void createNewModule_SrcFlatModel() {
    getOrCreateModule(getSrcFlatModel().getRootArtifact());
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void createNewModule_BinHierarchicalModel() {
    getOrCreateModule(getBinHierarchicalModel().getRootArtifact());
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void createNewModule_SrcHierarchicalModel() {
    getOrCreateModule(getSrcHierarchicalModel().getRootArtifact());
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootArtifact
   */
  private void getOrCreateModule(IRootArtifact rootArtifact) {

    //
    Assert.assertNull("Group 'groupTest1' does exist!",
        AnalysisModelQueries.findGroupArtifactByQualifiedName(rootArtifact, "groupTest1"));

    //
    IModuleArtifact moduleArtifact = rootArtifact.getOrCreateModule("groupTest1/groupTest2/MyModule", "1.0.0");
    assertNotNull(moduleArtifact);

    // assert the new module in all models
    assertModule(getBinFlatModel().getRootArtifact());
    assertModule(getSrcFlatModel().getRootArtifact());
    assertModule(getBinHierarchicalModel().getRootArtifact());
    assertModule(getSrcHierarchicalModel().getRootArtifact());
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootArtifact
   */
  private void assertModule(IRootArtifact rootArtifact) {

    // assert that 'groupTest1' exists
    Assert.assertNotNull("Group 'groupTest1' does not exist!",
        AnalysisModelQueries.findGroupArtifactByQualifiedName(rootArtifact, "groupTest1"));

    // assert that 'groupTest2' exists
    Assert.assertNotNull("Group 'groupTest2' does not exist!",
        AnalysisModelQueries.findGroupArtifactByQualifiedName(rootArtifact, "groupTest1/groupTest2"));

    // assert that 'MyModule_1.0.0' exists
    IModuleArtifact moduleArtifact = AnalysisModelQueries.getModuleArtifact(rootArtifact, "MyModule", "1.0.0");

    Assert.assertNotNull("Module 'MyModule_1.0.0' does not exist!", moduleArtifact);

    assertNotNull(moduleArtifact.getParent());
    assertEquals("groupTest2", moduleArtifact.getParent().getName());
    assertEquals("groupTest1/groupTest2/MyModule_1.0.0", moduleArtifact.getQualifiedName());

    // get module artifact
    IModuleArtifact module2Artifact = rootArtifact.getOrCreateModule("groupTest1/groupTest2/MyModule", "1.0.0");
    assertSame(module2Artifact, moduleArtifact);

    // get module artifact again
    IGroupArtifact groupTest2Artifact = (IGroupArtifact) moduleArtifact.getParent();
    module2Artifact = groupTest2Artifact.getOrCreateModule("/groupTest1/groupTest2/MyModule", "1.0.0");
    assertSame(module2Artifact, moduleArtifact);
  }
}
