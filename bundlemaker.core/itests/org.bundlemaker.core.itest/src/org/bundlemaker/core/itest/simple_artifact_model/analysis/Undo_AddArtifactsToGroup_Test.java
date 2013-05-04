package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertResourceModuleCount;
import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertResourceModuleCountInModularizedSystem;

import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.itestframework.simple_artifact_model.NoModificationAssertion;
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
        groupArtifact.addArtifact(getBinModel().getGroup1Artifact());
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
        groupArtifact.addArtifact(getBinModel().getGroup2Artifact());
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
        groupArtifact.addArtifact(getBinModel().getMainModuleArtifact());
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
    NoModificationAssertion.assertNoModification(this, new NoModificationAssertion.Action() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void prePostCondition() {
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(getModularizedSystem(), 2);
        assertResourceModuleCount(getBinModel(), 2);
        assertResourceModuleCount(getSrcModel(), 2);
        Assert.assertEquals(2, getModularizedSystem().getTransformations().size());
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void execute() {
        // STEP 1: create a new group
        IGroupArtifact groupArtifact = getBinModel().getRootArtifact().getOrCreateGroup("newTestGroup");
        Assert.assertEquals("newTestGroup", groupArtifact.getQualifiedName());

        // assert that we three groups
        Assert.assertEquals(3, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(getModularizedSystem(), 2);
        assertResourceModuleCount(getSrcModel(), 2);
        assertResourceModuleCount(getBinModel(), 2);
        Assert.assertEquals(getBinModel().getRootArtifact(), groupArtifact.getParent());

        addToGroup.addToGroup(groupArtifact);

        //
        Assert.assertEquals(3, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(getModularizedSystem(),2);
        assertResourceModuleCount(getSrcModel(), 2);
        assertResourceModuleCount(getBinModel(), 2);
        Assert.assertEquals(getBinModel().getRootArtifact(), groupArtifact.getParent());
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
