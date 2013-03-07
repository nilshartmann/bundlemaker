package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.NoModificationAssertion;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Undo_AddArtifactsToGroup_Test extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void tryToAddGroupToGroupArtifact_1() throws Exception {
    perform(new AddToGroup() {
      @Override
      public void addToGroup(IGroupArtifact groupArtifact) {
        groupArtifact.addArtifact(_binModel.getGroup1Artifact());
      }
    });
  }
  
  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void tryToAddGroupToGroupArtifact_2() throws Exception {
    perform(new AddToGroup() {
      @Override
      public void addToGroup(IGroupArtifact groupArtifact) {
        groupArtifact.addArtifact(_binModel.getGroup2Artifact());
      }
    });
  }
  
  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void tryToAddModuleToGroup() throws Exception {
    perform(new AddToGroup() {
      @Override
      public void addToGroup(IGroupArtifact groupArtifact) {
        groupArtifact.addArtifact(_binModel.getMainModuleArtifact());
      }
    });
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  private void perform(final AddToGroup addToGroup) throws Exception {

    //
    NoModificationAssertion.assertNoModification(this, new Runnable() {

      @Override
      public void run() {

        //
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(1);
        assertResourceModuleCount(_binModel, 1);
        assertResourceModuleCount(_srcModel, 1);

        //
        Assert.assertEquals(1, getModularizedSystem().getTransformations().size());

        // STEP 1: create a new group
        IGroupArtifact groupArtifact = _binModel.getRootArtifact().getOrCreateGroup("newTestGroup");
        Assert.assertEquals("newTestGroup", groupArtifact.getQualifiedName());

        // assert that we three groups
        Assert.assertEquals(3, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(1);
        assertResourceModuleCount(_srcModel, 1);
        assertResourceModuleCount(_binModel, 1);
        Assert.assertEquals(_binModel.getRootArtifact(), groupArtifact.getParent());

        addToGroup.addToGroup(groupArtifact);
        
        //
        Assert.assertEquals(3, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(1);
        assertResourceModuleCount(_srcModel, 1);
        assertResourceModuleCount(_binModel, 1);
        Assert.assertEquals(_binModel.getRootArtifact(), groupArtifact.getParent());

        // STEP 3: Undo...
        for (int i = getModularizedSystem().getTransformations().size() - 1; i > 0; i--) {
          getModularizedSystem().undoLastTransformation();
        }

        // assert that we one modules
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(1);
        assertResourceModuleCount(_srcModel, 1);
        assertResourceModuleCount(_binModel, 1);
      }
    });
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private static interface AddToGroup {

    /**
     * <p>
     * </p>
     * 
     * @param newModuleArtifact
     */
    public void addToGroup(IGroupArtifact groupArtifact);
  }
}
