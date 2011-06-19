package org.bundlemaker.core;

import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.parser.test.AbstractBundleMakerProjectTest;
import org.bundlemaker.core.util.ProgressMonitor;
import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractModuleConverterTest extends AbstractBundleMakerProjectTest {

  /** - */
  private IModifiableModularizedSystem _modularizedSystem;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IModifiableModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  @Before
  public void init() throws CoreException {

    //
    addProjectDescription();

    //
    getBundleMakerProject().initialize(new ProgressMonitor());
    getBundleMakerProject().parseAndOpen(new ProgressMonitor());

    _modularizedSystem = (IModifiableModularizedSystem) getBundleMakerProject().getModularizedSystemWorkingCopy(getTestProjectName());

    _modularizedSystem.getTransformations().add(
        new GroupTransformation(new ModuleIdentifier(getTestProjectName(), "1.0.0"), new Path("bla/blub")));

    _modularizedSystem.applyTransformations(null);

    //
    IModule module = getModularizedSystem().getModule(getTestProjectName(), "1.0.0");
    Assert.assertNotNull(module);
  }

  @After
  public void dispose() throws CoreException {

    //
    _modularizedSystem = null;
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
  protected void assertNode(IArtifact node, ArtifactType type, String nodeName, String parentName) {
    Assert.assertEquals(type, node.getType());
    Assert.assertEquals(nodeName, node.getName());
    Assert.assertNotNull(node.getParent());
    Assert.assertEquals(parentName, node.getParent().getName());
  }
}
