package org.bundlemaker.core;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
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
    ModelTransformer modelTransformer = new ModelTransformer(true);
    IArtifact rootArtifact = modelTransformer.transform(modularizedSystem);

    System.out.println(rootArtifact.getChildren());

    // assert the root artifact is not null
    Assert.assertNotNull(rootArtifact);

    // get 2 children
    List<IArtifact> children = new LinkedList<IArtifact>(rootArtifact.getChildren());
    Assert.assertEquals(2, children.size());

    // assert the 'jdk16_jdk16' node
    assertNode(children.get(0), "jdk16_jdk16", modularizedSystem.getName());

    // assert the 'bla' node
    assertNode(children.get(1), "bla", modularizedSystem.getName());

    // get child
    children = new LinkedList<IArtifact>(children.get(1).getChildren());
    Assert.assertEquals(1, children.size());

    // assert the 'bla' node
    assertNode(children.get(0), "blub", "bla");
    
    // get child
    children = new LinkedList<IArtifact>(children.get(0).getChildren());
    Assert.assertEquals(1, children.size());

    // assert the 'bla' node
    assertNode(children.get(0), "ModuleConverterTest_1.0.0", "blub");
  }

  /**
   * <p>
   * </p>
   * 
   * @param node
   * @param modularizedSystem
   */
  private void assertNode(IArtifact node, String nodeName, String parentName) {
    Assert.assertEquals(nodeName, node.getName());
    Assert.assertNotNull(node.getParent());
    Assert.assertEquals(parentName, node.getParent().getName());
  }
}
