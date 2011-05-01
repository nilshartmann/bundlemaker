package org.bundlemaker.core;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.parser.test.AbstractBundleMakerProjectTest;
import org.bundlemaker.core.util.ProgressMonitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleConverterTest extends AbstractBundleMakerProjectTest {

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * 
   * @throws Exception
   */
  @Test
  public void test() throws CoreException {

    //
    addProjectDescription();

    //
    getBundleMakerProject().initialize(new ProgressMonitor());
    getBundleMakerProject().parse(new ProgressMonitor(), true);
    getBundleMakerProject().open(new ProgressMonitor());

    //
    IModularizedSystem modularizedSystem = getBundleMakerProject()
        .getModularizedSystemWorkingCopy(getTestProjectName());

    modularizedSystem.getTransformations().add(
        new GroupTransformation(new ModuleIdentifier("ModuleConverterTest", "1.0.0"), new Path("bla/blub")));

    modularizedSystem.applyTransformations();

    //
    IModule module = modularizedSystem.getModule("ModuleConverterTest", "1.0.0");
    Assert.assertEquals(2, module.getContainedTypes().size());

    // the 
    ModelTransformer modelTransformer = new ModelTransformer(true);
    IArtifact rootArtifact = modelTransformer.transform(modularizedSystem);
    ModelTransformer.dumpArtifact(rootArtifact);

    // assert the root artifact is not null
    Assert.assertNotNull(rootArtifact);

    // get 2 children
    List<IArtifact> children = new LinkedList<IArtifact>(rootArtifact.getChildren());
    Assert.assertEquals(2, children.size());

    // assert the 'jdk16_jdk16' node
    assertNode(children.get(0), ArtifactType.Module, "jdk16_jdk16", modularizedSystem.getName());

    // assert the 'bla' node
    assertNode(children.get(1), ArtifactType.Group, "bla", modularizedSystem.getName());

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
    // assertNode(children.get(0), ArtifactType.Resource, "Klasse.class", "test");
    // assertNode(children.get(1), ArtifactType.Resource, "Test.class", "test");
  }

  /**
   * <p>
   * </p>
   * 
   * @param node
   * @param type
   *          TODO
   * @param modularizedSystem
   */
  private void assertNode(IArtifact node, ArtifactType type, String nodeName, String parentName) {
    Assert.assertEquals(type, node.getType());
    Assert.assertEquals(nodeName, node.getName());
    Assert.assertNotNull(node.getParent());
    Assert.assertEquals(parentName, node.getParent().getName());
  }
}
