package org.bundlemaker.core.itest.misc_models;

import java.util.Collection;

import org.bundlemaker.core._type.IType;
import org.bundlemaker.core._type.ITypeModule;
import org.bundlemaker.core._type.ITypeResource;
import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itestframework.AbstractBundleMakerModelTest;
import org.bundlemaker.core.itestframework.utils.ArtifactTestUtil;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class InnerClassTest extends AbstractBundleMakerModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testInnerClass() throws CoreException {

    //
    IModule resourceModule = getModularizedSystem().getModule("InnerClassTest", "1.0.0");
    IType aType = resourceModule.adaptAs(ITypeModule.class).getType("de.test.innertypes.A");
    IType bType = resourceModule.adaptAs(ITypeModule.class).getType("de.test.innertypes.B");

    // test resources
    Assert.assertNotNull(aType);
    Assert.assertTrue(aType.hasBinaryResource());
    Assert.assertTrue(aType.hasSourceResource());
    IModuleResource aSourceResource = aType.getSourceResource();
    Assert.assertTrue(aSourceResource.adaptAs(ITypeResource.class).isPrimaryType(aType));
    Assert.assertFalse(aSourceResource.adaptAs(ITypeResource.class).isPrimaryType(bType));
    Assert.assertNotNull(aSourceResource.adaptAs(ITypeResource.class).getContainedTypes());
    Assert.assertEquals(aSourceResource.adaptAs(ITypeResource.class).getContainedTypes().size(), 4);

    Assert.assertNotNull(bType);
    Assert.assertTrue(bType.hasBinaryResource());
    Assert.assertTrue(bType.hasSourceResource());

    IModuleResource binaryResource = bType.getBinaryResource();
    Assert.assertNotNull(binaryResource.getStickyResources());
    Assert.assertEquals(1, binaryResource.getStickyResources().size());

    IModuleResource bSourceResource = bType.getSourceResource();
    Assert.assertSame(aSourceResource, bSourceResource);

    // transform the model
    IRootArtifact rootArtifact = getModularizedSystem().getAnalysisModel(
        AnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION);

    System.out.println(ArtifactTestUtil.toString(rootArtifact));
    
    
    IBundleMakerArtifact aArtifact = rootArtifact
        .getChild("InnerClassTest_1.0.0|de.test.innertypes|A.class|A");
    Assert.assertNotNull(aArtifact);
    IBundleMakerArtifact bArtifact = rootArtifact
        .getChild("InnerClassTest_1.0.0|de.test.innertypes|B.class|B");
    Assert.assertNotNull(bArtifact);

    Collection<IDependency> dependencies = aArtifact.getDependenciesTo();
    Assert.assertEquals(2, dependencies.size());
    for (IDependency iDependency : dependencies) {
      System.out.println(iDependency);
    }
  }
}
