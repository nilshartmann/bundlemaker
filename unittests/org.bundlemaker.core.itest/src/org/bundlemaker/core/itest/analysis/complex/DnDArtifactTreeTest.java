package org.bundlemaker.core.itest.analysis.complex;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.modules.IModule;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.Test;

/**
 * <p>
 * Example: group1/group2/jedit_1.0.0 velocity_1.5 jdk16_jdk16 << Missing Types >>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DnDArtifactTreeTest extends AbstractComplexTest {

  /**
   * <p>
   * Creates a new group 'testGroup' (below root) and adds the 'Jedit' module to it without removing it from its parent.
   * It must be automatically removed from its former parent.
   * </p>
   */
  @Test
  public void testAddModuleWithoutRemove() {

    // create test group and add the 'jedit' artifact
    IArtifact testGroup = createNewGroup(getRootArtifact(), "testGroup");
    testGroup.addArtifact(getJeditModuleArtifact());
    
    // TODO 
    getRootArtifact().invalidateDependencyCache();

    // assert children
    assertArtifactChildrenCount(getRootArtifact(), 5);
    assertArtifactChildrenCount(getGroup2Artifact(), 0);
    assertArtifactChildrenCount(testGroup, 1);

    // assert parent
    assertArtifactHasParent(testGroup, getRootArtifact());
    assertArtifactHasParent(getJeditModuleArtifact(), testGroup);

    // assert dependencies
    assertDependencyWeight(testGroup, getJdkArtifact(), 1904);
    assertDependencyWeight(getGroup1Artifact(), getJdkArtifact(), 0);
    assertDependencyWeight(getVelocityModuleArtifact(), getJdkArtifact(), 4);
  }

  /**
   * <p>
   * Creates a new group 'GROUP' and adds the 'Jedit' module to it. Than a second group 'GROUP2' is created and the
   * 'Jedit' module is added to it.
   * </p>
   * <p>
   * All parent / child relationships have to be set correctly. Cached dependencies have to be invalidated and
   * re-computed.
   * </p>
   */
  @Test
  public void testDuplicateAdd() {

    // create test group and add the 'jedit' artifact
    IArtifact GROUPgroup = createNewGroup(getRootArtifact(), "GROUP");
    GROUPgroup.addArtifact(getJeditModuleArtifact());
    
    // TODO 
    getRootArtifact().invalidateDependencyCache();

    // assert children
    assertArtifactChildrenCount(getRootArtifact(), 5);
    assertArtifactChildrenCount(getGroup2Artifact(), 0);
    assertArtifactChildrenCount(GROUPgroup, 1);

    // assert parent
    assertArtifactHasParent(GROUPgroup, getRootArtifact());
    assertArtifactHasParent(getJeditModuleArtifact(), GROUPgroup);

    // assert dependencies
    assertDependencyWeight(GROUPgroup, getJdkArtifact(), 1904);
    assertDependencyWeight(getGroup1Artifact(), getJdkArtifact(), 0);
    assertDependencyWeight(getVelocityModuleArtifact(), getJdkArtifact(), 4);

    // create test group and add the 'jedit' artifact
    IArtifact GROUP2Group = createNewGroup(getRootArtifact(), "GROUP2");
    GROUP2Group.addArtifact(getJeditModuleArtifact());
    
    // TODO 
    getRootArtifact().invalidateDependencyCache();

    // assert children
    assertArtifactChildrenCount(getRootArtifact(), 6);
    assertArtifactChildrenCount(getGroup2Artifact(), 0);
    assertArtifactChildrenCount(GROUPgroup, 0);
    assertArtifactChildrenCount(GROUP2Group, 1);

    // assert parent
    assertArtifactHasParent(GROUPgroup, getRootArtifact());
    assertArtifactHasParent(GROUP2Group, getRootArtifact());
    assertArtifactHasParent(getJeditModuleArtifact(), GROUP2Group);

    // assert dependencies
    assertDependencyWeight(GROUP2Group, getJdkArtifact(), 1904);
    assertDependencyWeight(GROUPgroup, getJdkArtifact(), 0);
    assertDependencyWeight(getGroup1Artifact(), getJdkArtifact(), 0);
    assertDependencyWeight(getVelocityModuleArtifact(), getJdkArtifact(), 4);
  }

  @Test
  public void testChangedDependencies() {

    // create test group and add the 'jedit' artifact
    IArtifact GROUPgroup = createNewGroup(getRootArtifact(), "GROUP");
    IArtifact GROUP2group = createNewGroup(getRootArtifact(), "GROUP2");

    GROUPgroup.addArtifact(getJeditModuleArtifact());
    GROUP2group.addArtifact(getJdkArtifact());

    // TODO 
    getRootArtifact().invalidateDependencyCache();
    
    assertDependencyWeight(GROUPgroup, GROUP2group, 1904);

    GROUPgroup.addArtifact(getVelocityModuleArtifact());
    
    // TODO 
    getRootArtifact().invalidateDependencyCache();

    assertDependencyWeight(GROUPgroup, GROUP2group, 1908);
    assertArtifactHasParent(getJdkArtifact(), GROUP2group);

    getRootArtifact().addArtifact(getJdkArtifact());
    
    // TODO 
    getRootArtifact().invalidateDependencyCache();

    assertArtifactChildrenCount(GROUP2group, 0);
    assertArtifactHasParent(getJdkArtifact(), getRootArtifact());

    ArtifactUtils.dumpArtifact(GROUPgroup);
    System.out.println("********************************************");
    ArtifactUtils.dumpArtifact(GROUP2group);

    assertDependencyWeight(GROUPgroup, GROUP2group, 0);
    assertDependencyWeight(GROUPgroup, getJdkArtifact(), 1908);
  }

  @Test
  public void testAddToRoot() {

    // create test group and add the 'jedit' artifact
    IArtifact testGroup = createNewGroup(getRootArtifact(), "testGroup");
    testGroup.addArtifact(getJeditModuleArtifact());
    testGroup.addArtifact(getJdkArtifact());
    testGroup.addArtifact(getVelocityModuleArtifact());

    //
    IModule jeditModule = getModularizedSystem().getModule("jedit", "1.0.0");
    IModule velocityModule = getModularizedSystem().getModule("velocity", "1.5");
    IModule eeModule = getModularizedSystem().getExecutionEnvironment();

    assertEquals(new Path("testGroup"), jeditModule.getClassification());
    assertEquals(new Path("testGroup"), velocityModule.getClassification());
    assertEquals(new Path("testGroup"), eeModule.getClassification());

    // assert children
    assertArtifactChildrenCount(getRootArtifact(), 3);
    assertArtifactChildrenCount(getGroup2Artifact(), 0);
    assertArtifactChildrenCount(testGroup, 3);

    // assert parent
    assertArtifactHasParent(testGroup, getRootArtifact());
    assertArtifactHasParent(getJeditModuleArtifact(), testGroup);
    assertArtifactHasParent(getJdkArtifact(), testGroup);
    assertArtifactHasParent(getVelocityModuleArtifact(), testGroup);

    //
    getRootArtifact().addArtifact(getJeditModuleArtifact());
    getRootArtifact().addArtifact(getJdkArtifact());
    getRootArtifact().addArtifact(getVelocityModuleArtifact());

    assertEquals(null, jeditModule.getClassification());
    assertEquals(null, velocityModule.getClassification());
    assertEquals(null, eeModule.getClassification());

    assertTypeCount(1438);

    // // assert dependencies
    // assertDependencyWeight(testGroup, getJdkArtifact(), 0);
    // assertDependencyWeight(testGroup, getVelocityModuleArtifact(), 0);
    // assertDependencyWeight(testGroup, getJeditModuleArtifact(), 0);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * @throws IOException
   */
  @Test
  public void testMoveModuleToNewGroup() {

    // remove 'jedit' module
    getJeditModuleArtifact().getParent().removeArtifact(getJeditModuleArtifact());

    // assert
    assertTypeCount(246);

    //
    IArtifact testGroup = createNewGroup(getRootArtifact(), "testGroup");
    testGroup.addArtifact(getJeditModuleArtifact());

    // get the 'group2Artifact' artifact...
    IArtifact group2Artifact = getArtifact(getRootArtifact(), "group1|group2");
    assertEquals(0, group2Artifact.getChildren().size());

    // assert the result
    InputStream inputstream = getClass().getResourceAsStream("results/DnDArtifactTreeTest_MoveModuleToNewGroup.txt");
    assertResult(ArtifactUtils.artifactToString(getJeditModuleArtifact()), inputstream,
        "DnDArtifactTreeTest_MoveModuleToNewGroup_" + getCurrentTimeStamp());

    // assert
    IModule module = getModularizedSystem().getModule("jedit", "1.0.0");
    assertEquals(new Path("testGroup"), module.getClassification());

    assertTypeCount(1438);
  }
}
