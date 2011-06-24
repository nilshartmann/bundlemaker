package org.bundlemaker.core.itest.analysis;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.itest.framework.AbstractModuleConverterTest;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependency;
import org.bundlemaker.dependencyanalysis.base.model.IDependencyModel;
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

    // Step 1: transform the model
    IAdvancedArtifact rootArtifact = (IAdvancedArtifact) ModelTransformer.transform(getModularizedSystem());
    Assert.assertNotNull(rootArtifact);
    ArtifactUtils.dumpArtifact(rootArtifact);

    // Step 2: test 'root' with children
    List<IArtifact> children = new LinkedList<IArtifact>(rootArtifact.getChildren());
    Assert.assertEquals(2, children.size());
    for (IArtifact child : children) {
      Assert.assertEquals(rootArtifact, child.getParent());
    }
    assertNode(children.get(0), ArtifactType.Module, "jdk16_jdk16", getModularizedSystem().getName());
    assertNode(children.get(1), ArtifactType.Group, "bla", getModularizedSystem().getName());

    // Step 3: test 'bla' with children
    children = new LinkedList<IArtifact>(children.get(1).getChildren());
    Assert.assertEquals(1, children.size());
    assertNode(children.get(0), ArtifactType.Group, "blub", "bla");

    // Step 4: test 'blub' with children
    children = new LinkedList<IArtifact>(children.get(0).getChildren());
    Assert.assertEquals(1, children.size());
    assertNode(children.get(0), ArtifactType.Module, "ModuleConverterTest_1.0.0", "blub");

    // Step 5: test 'ModuleConverterTest_1.0.0' with children
    children = new LinkedList<IArtifact>(children.get(0).getChildren());
    Assert.assertEquals(1, children.size());
    assertNode(children.get(0), ArtifactType.Package, "test", "ModuleConverterTest_1.0.0");

    // Step 6: test 'de.test' package with children
    children = new LinkedList<IArtifact>(children.get(0).getChildren());
    Collections.sort(children, new Comparator<IArtifact>() {
      @Override
      public int compare(IArtifact o1, IArtifact o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });
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
    IAdvancedArtifact rootArtifact = (IAdvancedArtifact) ModelTransformer.transform(getModularizedSystem());

    // get the module artifact
    IArtifact moduleArtifact = rootArtifact.getChild("bla/blub/ModuleConverterTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    IArtifact groupArtifact = rootArtifact.getChild("bla/blub");
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
    IAdvancedArtifact rootArtifact = (IAdvancedArtifact) ModelTransformer.transform(getModularizedSystem());

    // get the module artifact
    IArtifact moduleArtifact = rootArtifact.getChild("bla/blub/ModuleConverterTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    IArtifact groupArtifact = rootArtifact.getChild("bla/blub");
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
    IAdvancedArtifact rootArtifact = (IAdvancedArtifact) ModelTransformer.transform(getModularizedSystem());

    // get blub group
    IArtifact blubGroup = rootArtifact.getChild("bla/blub");
    Assert.assertNotNull(blubGroup);

    // get bla group
    IArtifact blaGroup = rootArtifact.getChild("bla");
    Assert.assertNotNull(blubGroup);

    // TEST 3: REMOVE AND ADD TO PARENT
    blaGroup.removeArtifact(blubGroup);
    Assert.assertNull(getModularizedSystem().getModule("ModuleConverterTest", "1.0.0"));

    rootArtifact.addArtifact(blubGroup);
    ArtifactUtils.dumpArtifact(rootArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("ModuleConverterTest", "1.0.0"));
    Assert.assertEquals(new Path("blub"), getModularizedSystem().getModule("ModuleConverterTest", "1.0.0")
        .getClassification());
  }

  @Test
  public void testPackage_SimpleRemoveAndAdd() throws CoreException {

    // transform the model
    IAdvancedArtifact rootArtifact = (IAdvancedArtifact) ModelTransformer.transform(getModularizedSystem());

    // get the module
    IArtifact moduleArtifact = rootArtifact.getChild("bla/blub/ModuleConverterTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    // get package group
    IArtifact packageDeTest = rootArtifact.getChild("bla/blub/ModuleConverterTest_1.0.0/de.test");
    Assert.assertNotNull(packageDeTest);

    // Test 1: assert resources
    IResourceModule resourceModule = getModularizedSystem().getResourceModule("ModuleConverterTest", "1.0.0");
    Assert.assertNotNull(resourceModule.getResource("de/test/Test.class", ContentType.BINARY));
    Assert.assertNotNull(resourceModule.getResource("de/test/Klasse.class", ContentType.BINARY));

    // Test 2: remove resources
    moduleArtifact.removeArtifact(packageDeTest);
    Assert.assertNull(resourceModule.getResource("de/test/Test.class", ContentType.BINARY));
    Assert.assertNull(resourceModule.getResource("de/test/Klasse.class", ContentType.BINARY));

    // Test 3: add resources
    moduleArtifact.addArtifact(packageDeTest);
    Assert.assertNotNull(resourceModule.getResource("de/test/Test.class", ContentType.BINARY));
    Assert.assertNotNull(resourceModule.getResource("de/test/Klasse.class", ContentType.BINARY));
  }

  @Test
  public void testResource_SimpleRemoveAndAdd() throws CoreException {

    // transform the model
    IAdvancedArtifact rootArtifact = (IAdvancedArtifact) ModelTransformer.transform(getModularizedSystem());

    // get package
    IArtifact packageDeTest = rootArtifact.getChild("bla/blub/ModuleConverterTest_1.0.0/de.test");
    Assert.assertNotNull(packageDeTest);

    // get resources
    IArtifact resourceKlasseClass = rootArtifact.getChild("bla/blub/ModuleConverterTest_1.0.0/de.test/Klasse.class");
    Assert.assertNotNull(resourceKlasseClass);
    IArtifact resourceTestClass = rootArtifact.getChild("bla/blub/ModuleConverterTest_1.0.0/de.test/Test.class");
    Assert.assertNotNull(resourceTestClass);

    // Test 1: assert resources
    IResourceModule resourceModule = getModularizedSystem().getResourceModule("ModuleConverterTest", "1.0.0");
    Assert.assertNotNull(resourceModule.getResource("de/test/Test.class", ContentType.BINARY));
    Assert.assertNotNull(resourceModule.getResource("de/test/Klasse.class", ContentType.BINARY));

    // Test 2: remove resources
    packageDeTest.removeArtifact(resourceKlasseClass);
    packageDeTest.removeArtifact(resourceTestClass);
    Assert.assertNull(resourceModule.getResource("de/test/Test.class", ContentType.BINARY));
    Assert.assertNull(resourceModule.getResource("de/test/Klasse.class", ContentType.BINARY));

    // Test 2: add resources
    packageDeTest.addArtifact(resourceKlasseClass);
    packageDeTest.addArtifact(resourceTestClass);
    Assert.assertNotNull(resourceModule.getResource("de/test/Test.class", ContentType.BINARY));
    Assert.assertNotNull(resourceModule.getResource("de/test/Klasse.class", ContentType.BINARY));
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
    Assert.assertEquals(18247, module.getContainedTypes().size());
    Assert.assertNotNull(module.getType("javax.activation.DataHandler"));

    //
    Assert.assertNotNull(getModularizedSystem().getType("javax.activation.DataHandler"));
    IModule typeContainingModule = getModularizedSystem().getTypeContainingModule("javax.activation.DataHandler");
    Assert.assertNotNull(typeContainingModule);
    Assert.assertSame(typeContainingModule, module);

    // transform the model
    IAdvancedArtifact rootArtifact = (IAdvancedArtifact) ModelTransformer.transform(getModularizedSystem());

    IArtifact module1 = rootArtifact.getChild("bla/blub/ModuleConverterTest_1.0.0");
    Assert.assertNotNull(module1);

    // get bla group
    IArtifact jreModule = rootArtifact.getChild("jdk16_jdk16");
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

  @Test
  public void testAggregated() throws CoreException {

    // transform the model
    IAdvancedArtifact rootArtifact = (IAdvancedArtifact) ModelTransformer
        .transformWithAggregatedTypes(getModularizedSystem());
    ArtifactUtils.dumpArtifact(rootArtifact);
  }

  @Test
  public void testDependencyModel() throws CoreException {

    // transform the model
    IDependencyModel dependencyModel = ModelTransformer.getDependencyModel(getBundleMakerProject(),
        getModularizedSystem());
    ArtifactUtils.dumpArtifact(dependencyModel.getRoot());
  }
}