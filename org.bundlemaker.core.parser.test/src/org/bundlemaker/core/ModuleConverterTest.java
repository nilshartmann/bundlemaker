package org.bundlemaker.core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependency;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

/**
 * <pre>
 * Group : bla
 *   Group : blub
 *     Module : bla/blub/ModuleConverterTest_1.0.0
 *       Package : de.test
 *         Resource : Test.class
 *           Type : de.test.Test
 *         Resource : Klasse.class
 *           Type : de.test.Klasse
 * </pre>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleConverterTest extends AbstractModuleConverterTest {

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * 
   * @throws Exception
   */
  @Test
  public void testTransformedModel() throws CoreException {

    // the
    IAdvancedArtifact rootArtifact = new ModelTransformer(true).transform(getModularizedSystem());
    ModelTransformer.dumpArtifact(rootArtifact);

    // assert the root artifact is not null
    Assert.assertNotNull(rootArtifact);

    // get 2 children
    List<IArtifact> children = new LinkedList<IArtifact>(rootArtifact.getChildren());
    Assert.assertEquals(2, children.size());

    // assert the 'jdk16_jdk16' node
    assertNode(children.get(0), ArtifactType.Module, "jdk16_jdk16", getModularizedSystem().getName());

    // assert the 'bla' node
    assertNode(children.get(1), ArtifactType.Group, "bla", getModularizedSystem().getName());

    // get child
    children = new LinkedList<IArtifact>(children.get(1).getChildren());
    Assert.assertEquals(1, children.size());

    // assert the 'bla' node
    assertNode(children.get(0), ArtifactType.Group, "blub", "bla");

    // get child
    children = new LinkedList<IArtifact>(children.get(0).getChildren());
    Assert.assertEquals(1, children.size());

    // assert the 'ModuleConverterTest_1.0.0' node
    assertNode(children.get(0), ArtifactType.Module, "ModuleConverterTest_1.0.0", "blub");

    // assert the 'de.test' node
    children = new LinkedList<IArtifact>(children.get(0).getChildren());
    Assert.assertEquals(1, children.size());
    assertNode(children.get(0), ArtifactType.Package, "test", "ModuleConverterTest_1.0.0");

    // assert the resource nodes
    children = new LinkedList<IArtifact>(children.get(0).getChildren());
    Assert.assertEquals(2, children.size());
    assertNode(children.get(0), ArtifactType.Resource, "Klasse.class", "test");
    assertNode(children.get(1), ArtifactType.Resource, "Test.class", "test");
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * 
   * @throws Exception
   */
  @Test
  public void testGroup_SimpleRemoveAddModule() throws CoreException {

    // transform the model
    IAdvancedArtifact rootArtifact = new ModelTransformer(true).transform(getModularizedSystem());

    // get the module artifact
    IArtifact moduleArtifact = rootArtifact.getChild(new Path("bla/blub/ModuleConverterTest_1.0.0"));
    Assert.assertNotNull(moduleArtifact);

    IArtifact groupArtifact = rootArtifact.getChild(new Path("bla/blub"));
    Assert.assertNotNull(groupArtifact);

    // TEST 1: REMOVE
    groupArtifact.removeArtifact(moduleArtifact);
    Assert.assertNull(getModularizedSystem().getModule("ModuleConverterTest", "1.0.0"));

    // TEST 2: RE-ADD
    groupArtifact.addArtifact(moduleArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("ModuleConverterTest", "1.0.0"));
    Assert.assertEquals(getModularizedSystem().getModule("ModuleConverterTest", "1.0.0").getClassification(), new Path(
        "bla/blub"));
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testGroup_RemoveModuleAndAddToOtherGroup() throws CoreException {

    // transform the model
    IAdvancedArtifact rootArtifact = new ModelTransformer(true).transform(getModularizedSystem());

    // get the module artifact
    IArtifact moduleArtifact = rootArtifact.getChild(new Path("bla/blub/ModuleConverterTest_1.0.0"));
    Assert.assertNotNull(moduleArtifact);

    IArtifact groupArtifact = rootArtifact.getChild(new Path("bla/blub"));
    Assert.assertNotNull(groupArtifact);

    // TEST 3: REMOVE AND ADD TO PARENT
    groupArtifact.removeArtifact(moduleArtifact);
    Assert.assertNull(getModularizedSystem().getModule("ModuleConverterTest", "1.0.0"));

    groupArtifact.getParent().addArtifact(moduleArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("ModuleConverterTest", "1.0.0"));
    Assert.assertEquals(new Path("bla"), getModularizedSystem().getModule("ModuleConverterTest", "1.0.0")
        .getClassification());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testGroup_RemoveGroupAndAddToOtherGroup() throws CoreException {

    // transform the model
    IAdvancedArtifact rootArtifact = new ModelTransformer(true).transform(getModularizedSystem());

    // get blub group
    IArtifact blubGroup = rootArtifact.getChild(new Path("bla/blub"));
    Assert.assertNotNull(blubGroup);

    // get bla group
    IArtifact blaGroup = rootArtifact.getChild(new Path("bla"));
    Assert.assertNotNull(blubGroup);

    // TEST 3: REMOVE AND ADD TO PARENT
    blaGroup.removeArtifact(blubGroup);
    Assert.assertNull(getModularizedSystem().getModule("ModuleConverterTest", "1.0.0"));

    rootArtifact.addArtifact(blubGroup);
    ModelTransformer.dumpArtifact(rootArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("ModuleConverterTest", "1.0.0"));
    Assert.assertEquals(new Path("blub"), getModularizedSystem().getModule("ModuleConverterTest", "1.0.0")
        .getClassification());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testDependencies() throws CoreException {

    // transform the model
    IAdvancedArtifact rootArtifact = new ModelTransformer(true).transform(getModularizedSystem());

    IArtifact module1 = rootArtifact.getChild(new Path("bla/blub/ModuleConverterTest_1.0.0"));
    Assert.assertNotNull(module1);

    // get bla group
    IArtifact jreModule = rootArtifact.getChild(new Path("jdk16_jdk16"));
    Assert.assertNotNull(jreModule);

    System.out.println(module1.getDependencies());

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
