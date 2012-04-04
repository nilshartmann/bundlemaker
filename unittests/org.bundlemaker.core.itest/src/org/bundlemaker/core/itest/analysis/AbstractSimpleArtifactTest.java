package org.bundlemaker.core.itest.analysis;

import java.util.Collection;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ModelTransformerCache;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

/**
 * <pre>
 * Group : group1
 *   Group : group2
 *     Module : group1/group2/SimpleArtifactModelTest_1.0.0
 *       Package : de.test
 *         Resource : Test.class
 *           Type : de.test.Test
 *         Resource : Klasse.class
 *           Type : de.test.Klasse
 * Module : jdk16_jdk16
 * </pre>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractSimpleArtifactTest extends AbstractModularizedSystemTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return "SimpleArtifactModelTest";
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public abstract IArtifactModelConfiguration getConfiguration();

  /**
   * <p>
   * This test removes and adds a module artifact from/to the group artifact.
   * </p>
   * 
   * @throws CoreException
   * 
   * @throws Exception
   */
  @Test
  public void testGroup_RemoveModuleAndAddToSameGroup() throws CoreException {

    // Step 1: transform the model
    IBundleMakerArtifact rootArtifact = (IBundleMakerArtifact) ModelTransformerCache.getArtifactModel(
        getModularizedSystem(), getConfiguration()).getRoot();
    Assert.assertNotNull(rootArtifact);

    ArtifactUtils.dumpArtifact(rootArtifact);

    // get the module artifact
    IBundleMakerArtifact moduleArtifact = rootArtifact.getChild("group1|group2|SimpleArtifactModelTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    IBundleMakerArtifact groupArtifact = rootArtifact.getChild("group1|group2");
    Assert.assertNotNull(groupArtifact);

    // TEST 1: REMOVE
    groupArtifact.removeArtifact(moduleArtifact);
    Assert.assertNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));

    // TEST 2: RE-ADD
    groupArtifact.addArtifact(moduleArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));
    Assert.assertEquals(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0").getClassification(),
        new Path("group1/group2"));
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testGroup_RemoveModuleAndAddToExistingOtherGroup() throws CoreException {

    // Step 1: transform the model
    IBundleMakerArtifact rootArtifact = (IBundleMakerArtifact) ModelTransformerCache.getArtifactModel(
        getModularizedSystem(), getConfiguration()).getRoot();
    Assert.assertNotNull(rootArtifact);

    // get the module artifact
    IBundleMakerArtifact moduleArtifact = rootArtifact.getChild("group1|group2|SimpleArtifactModelTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    IBundleMakerArtifact group1_Artifact = rootArtifact.getChild("group1");
    Assert.assertNotNull(group1_Artifact);

    IBundleMakerArtifact group2_Artifact = rootArtifact.getChild("group1|group2");
    Assert.assertNotNull(group2_Artifact);

    // TEST 3: REMOVE FROM GROUP_2 AND ADD TO GROUP_1
    group2_Artifact.removeArtifact(moduleArtifact);
    Assert.assertNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));

    group1_Artifact.addArtifact(moduleArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));
    Assert.assertEquals(new Path("group1"), getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0")
        .getClassification());
  }

  @Test
  public void testGroup_RemoveModuleAndAddToNewGroup() throws CoreException {

    // Step 1: transform the model
    IRootArtifact rootArtifact = getModularizedSystem().getArtifactModel(getConfiguration());
    Assert.assertNotNull(rootArtifact);

    // get the module artifact
    IBundleMakerArtifact moduleArtifact = rootArtifact.getChild("group1|group2|SimpleArtifactModelTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    IBundleMakerArtifact group2_Artifact = rootArtifact.getChild("group1|group2");
    Assert.assertNotNull(group2_Artifact);

    IBundleMakerArtifact groupNew_Artifact = rootArtifact.getOrCreateGroup(new Path("groupNew"));
    Assert.assertNotNull(groupNew_Artifact);

    // TEST 3: REMOVE FROM GROUP_1 AND ADD TO GROUP_2
    group2_Artifact.removeArtifact(moduleArtifact);
    Assert.assertNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));

    groupNew_Artifact.addArtifact(moduleArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));
    Assert.assertEquals(new Path("groupNew"), getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0")
        .getClassification());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testGroup_RemoveGroupAndAddToSameGroup() throws CoreException {

    // Step 1: transform the model
    IBundleMakerArtifact rootArtifact = (IBundleMakerArtifact) ModelTransformerCache.getArtifactModel(
        getModularizedSystem(), getConfiguration()).getRoot();
    Assert.assertNotNull(rootArtifact);

    // get group2 group
    IBundleMakerArtifact group2Group = rootArtifact.getChild("group1|group2");
    Assert.assertNotNull(group2Group);

    IBundleMakerArtifact group1Group = rootArtifact.getChild("group1");
    Assert.assertNotNull(group2Group);

    // TEST: REMOVE AND ADD
    group1Group.removeArtifact(group2Group);
    Assert.assertNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));

    group1Group.addArtifact(group2Group);
    Assert.assertNotNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));
    Assert.assertEquals(new Path("group1/group2"), getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0")
        .getClassification());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testGroup_RemoveGroupAndAddToExistingOtherGroup() throws CoreException {

    // Step 1: transform the model
    IBundleMakerArtifact rootArtifact = (IBundleMakerArtifact) ModelTransformerCache.getArtifactModel(
        getModularizedSystem(), getConfiguration()).getRoot();
    Assert.assertNotNull(rootArtifact);

    // get group2 group
    IBundleMakerArtifact group2Group = rootArtifact.getChild("group1|group2");
    Assert.assertNotNull(group2Group);

    // get group1 group
    IBundleMakerArtifact group1Group = rootArtifact.getChild("group1");
    Assert.assertNotNull(group2Group);

    // TEST: remove from group1
    group1Group.removeArtifact(group2Group);
    Assert.assertNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));

    // TEST: add to root
    rootArtifact.addArtifact(group2Group);
    ArtifactUtils.dumpArtifact(rootArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));
    Assert.assertEquals(new Path("group2"), getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0")
        .getClassification());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testGroup_RemoveGroupAndAddToNewGroup() throws CoreException {

    // Step 1: transform the model
    IRootArtifact rootArtifact = getModularizedSystem().getArtifactModel(getConfiguration());
    Assert.assertNotNull(rootArtifact);

    // get group2 group
    IBundleMakerArtifact group2Group = rootArtifact.getChild("group1|group2");
    Assert.assertNotNull(group2Group);

    // get group1 group
    IBundleMakerArtifact group1Group = rootArtifact.getChild("group1");
    Assert.assertNotNull(group2Group);

    // get newGroup group
    IBundleMakerArtifact newGroupGroup = rootArtifact.getOrCreateGroup(new Path("newGroup"));
    Assert.assertNotNull(newGroupGroup);

    // TEST 3: REMOVE AND ADD TO PARENT
    group1Group.removeArtifact(group2Group);
    Assert.assertNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));

    newGroupGroup.addArtifact(group2Group);
    Assert.assertNotNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));
    Assert.assertEquals(new Path("newGroup/group2"),
        getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0").getClassification());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testGroup_CreateDuplicateGroup_getOrCreateGroup() throws CoreException {

    // Step 1: transform the model
    IRootArtifact rootArtifact = getModularizedSystem().getArtifactModel(getConfiguration());
    Assert.assertNotNull(rootArtifact);

    // get group2 group
    IBundleMakerArtifact group2Group = rootArtifact.getChild("group1|group2");
    Assert.assertNotNull(group2Group);

    //
    IGroupArtifact group1_Artifact = (IGroupArtifact) rootArtifact.getChild("group1");
    Assert.assertNotNull(group1_Artifact);

    //
    IGroupArtifact group2_2_Artifact = group1_Artifact.getOrCreateGroup(new Path("group2"));
    Assert.assertSame(group2Group, group2_2_Artifact);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test(expected = RuntimeException.class)
  public void testGroup_CreateDuplicateGroup_createArtifactContainer() throws CoreException {

    // Step 1: transform the model
    IRootArtifact rootArtifact = getModularizedSystem().getArtifactModel(getConfiguration());
    Assert.assertNotNull(rootArtifact);

    //
    IGroupArtifact group1_Artifact = (IGroupArtifact) rootArtifact.getChild("group1");
    Assert.assertNotNull(group1_Artifact);

    //
    IGroupArtifact group1_no2_Artifact = group1_Artifact.getOrCreateGroup(new Path("group1"));
    
    //
    rootArtifact.addArtifact(group1_no2_Artifact);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testGroup_ModifyModuleClassification() throws CoreException {

    // Step 1: transform the model
    IRootArtifact rootArtifact = getModularizedSystem().getArtifactModel(getConfiguration());
    Assert.assertNotNull(rootArtifact);

    // get the module artifact
    IBundleMakerArtifact moduleArtifact = rootArtifact.getChild("group1|group2|SimpleArtifactModelTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);
    moduleArtifact = rootArtifact.getChild("group1|group2");
    Assert.assertNotNull(moduleArtifact);

    //
    IModifiableResourceModule resourceModule = getModularizedSystem().getModifiableResourceModule(
        new ModuleIdentifier("SimpleArtifactModelTest", "1.0.0"));
    resourceModule.setClassification(new Path("hurz/purz"));

    // get the module artifact
    moduleArtifact = rootArtifact.getChild("group1|group2|SimpleArtifactModelTest_1.0.0");
    Assert.assertNull(moduleArtifact);
    moduleArtifact = rootArtifact.getChild("group1|group2");
    Assert.assertNotNull(moduleArtifact);

    // get the module artifact
    moduleArtifact = rootArtifact.getChild("hurz|purz|SimpleArtifactModelTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testDependencies() throws Exception {

    //
    IModule module = getModularizedSystem().getModule("jdk16", "jdk16");
    // Assert.assertEquals(18247, module.getContainedTypes().size());
    Assert.assertEquals(16217, module.getContainedTypes().size());
    Assert.assertNotNull(module.getType("javax.activation.DataHandler"));

    //
    Assert.assertNotNull(getModularizedSystem().getType("javax.activation.DataHandler"));
    IModule typeContainingModule = getModularizedSystem().getTypeContainingModule("javax.activation.DataHandler");
    Assert.assertNotNull(typeContainingModule);
    Assert.assertSame(typeContainingModule, module);

    // transform the model
    IBundleMakerArtifact rootArtifact = (IBundleMakerArtifact) ModelTransformerCache.getArtifactModel(
        getModularizedSystem(), getConfiguration()).getRoot();
    Assert.assertNotNull(rootArtifact);

    IBundleMakerArtifact module1 = rootArtifact.getChild("group1|group2|SimpleArtifactModelTest_1.0.0");
    Assert.assertNotNull(module1);

    // get group1 group
    IBundleMakerArtifact jreModule = rootArtifact.getChild("jdk16_jdk16");
    Assert.assertNotNull(jreModule);

    //
    IDependency dependency = module1.getDependency(jreModule);
    Assert.assertNotNull(dependency);
    Assert.assertEquals(module1, dependency.getFrom());
    Assert.assertEquals(jreModule, dependency.getTo());
    Assert.assertEquals(dependency.getWeight(), 1);

    //
    Collection<IDependency> dependencies = dependency.getDependencies();

    //
    Assert.assertNotNull(dependencies);
    Assert.assertEquals(dependencies.size(), 1);

    IDependency[] underlyingDeps = dependencies.toArray(new IDependency[0]);
    Assert.assertEquals("de.test.Klasse", underlyingDeps[0].getFrom().getQualifiedName());
    Assert.assertEquals("javax.activation.DataHandler", underlyingDeps[0].getTo().getQualifiedName());
    Assert.assertEquals(dependency.getWeight(), 1);
  }

}
