package org.bundlemaker.core;

import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.parser.test.AbstractBundleMakerProjectTest;
import org.bundlemaker.core.util.ProgressMonitor;
import org.eclipse.core.runtime.CoreException;
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
    modularizedSystem.applyTransformations();

    //
    ModelTransformer modelTransformer = new ModelTransformer(true);
    IArtifact rootArtifact = modelTransformer.transform(modularizedSystem);

    // WORK in PROGRESS
    Assert.assertNotNull(rootArtifact);
    
    System.out.println(rootArtifact.getChildren());

    // Assert.assertEquals("ModuleConverterTest_1.0.0", rootArtifact.getName());
    // Assert.assertEquals("ModuleConverterTest_1.0.0", rootArtifact.getQualifiedName());
    // Assert.assertEquals(ArtifactType.Module, rootArtifact.getType());
    
  }
}
