package org.bundlemaker.core.itest.analysis.complex;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.modules.IModule;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import static org.junit.Assert.*;
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
   * </p>
   */
  @Test
  public void testAddWithoutRemove() {

    // create test group and add the 'jedit' artifact
    IArtifact testGroup = createNewGroup(getRootArtifact(), "testGroup");
    testGroup.addArtifact(getJeditModuleArtifact());

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
   * </p>
   */
  @Test
  public void testDuplicateAdd() {

    // create test group and add the 'jedit' artifact
    IArtifact testGroup = createNewGroup(getRootArtifact(), "testGroup");
    testGroup.addArtifact(getJeditModuleArtifact());

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

    // create test group and add the 'jedit' artifact
    IArtifact testGroup2 = createNewGroup(getRootArtifact(), "testGroup");
    testGroup2.addArtifact(getJeditModuleArtifact());

    // assert children
    assertArtifactChildrenCount(getRootArtifact(), 6);
    assertArtifactChildrenCount(getGroup2Artifact(), 0);
    assertArtifactChildrenCount(testGroup, 0);
    assertArtifactChildrenCount(testGroup2, 1);

    // assert parent
    assertArtifactHasParent(testGroup, getRootArtifact());
    assertArtifactHasParent(testGroup2, getRootArtifact());
    assertArtifactHasParent(getJeditModuleArtifact(), testGroup2);

    // assert dependencies
    assertDependencyWeight(testGroup2, getJdkArtifact(), 1904);
    assertDependencyWeight(testGroup, getJdkArtifact(), 0);
    assertDependencyWeight(getGroup1Artifact(), getJdkArtifact(), 0);
    assertDependencyWeight(getVelocityModuleArtifact(), getJdkArtifact(), 4);
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
    
    assertEquals(new Path(""), jeditModule.getClassification());
    assertEquals(new Path(""), velocityModule.getClassification());
    assertEquals(new Path(""), eeModule.getClassification());

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

  // @Test
  // public void testMoveGroup() throws CoreException, IOException {
  //
  // //
  // IArtifact testGroup_1 = getRootArtifact().getDependencyModel().createArtifactContainer("testGroup_1",
  // "testGroup_1", ArtifactType.Group);
  // getRootArtifact().addArtifact(testGroup_1);
  //
  // //
  // IArtifact testGroup_2 = getRootArtifact().getDependencyModel().createArtifactContainer("testGroup_2",
  // "testGroup_2", ArtifactType.Group);
  // getRootArtifact().addArtifact(testGroup_2);
  //
  // //
  // testGroup_1.addArtifact(getJeditModuleArtifact());
  // testGroup_1.addArtifact(getVelocityModuleArtifact());
  // testGroup_2.addArtifact(getVelocityModuleArtifact());
  //
  // assertTypeCount(1438);
  //
  // //
  // assertTypeCount(0);
  //
  // //
  // Assert.assertEquals(0, getModularizedSystem().getResourceModules().size());
  // }

}
