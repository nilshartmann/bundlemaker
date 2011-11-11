package org.bundlemaker.core.itest.analysis;

import java.util.Collection;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class InnerClassTest extends AbstractModularizedSystemTest {

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * @throws AmbiguousElementException
   */
  @Test
  public void testInnerClass() throws CoreException, AmbiguousElementException {

    //
    IResourceModule resourceModule = getModularizedSystem().getResourceModule("InnerClassTest", "1.0.0");
    IType aType = resourceModule.getType("de.test.innertypes.A");
    IType bType = resourceModule.getType("de.test.innertypes.B");

    // test resources
    Assert.assertNotNull(aType);
    Assert.assertTrue(aType.hasBinaryResource());
    Assert.assertTrue(aType.hasSourceResource());
    IResource aSourceResource = aType.getSourceResource();
    Assert.assertTrue(aSourceResource.isPrimaryType(aType));
    Assert.assertFalse(aSourceResource.isPrimaryType(bType));
    Assert.assertNotNull(aSourceResource.getContainedTypes());
    Assert.assertEquals(aSourceResource.getContainedTypes().size(), 4);

    Assert.assertNotNull(bType);
    Assert.assertTrue(bType.hasBinaryResource());
    Assert.assertTrue(bType.hasSourceResource());

    IResource binaryResource = bType.getBinaryResource();
    Assert.assertNotNull(binaryResource.getStickyResources());
    Assert.assertEquals(1, binaryResource.getStickyResources().size());

    IResource bSourceResource = bType.getSourceResource();
    Assert.assertSame(aSourceResource, bSourceResource);

    // transform the model
    IRootArtifact rootArtifact = getModularizedSystem().getArtifactModel(
        ArtifactModelConfiguration.AGGREGATE_INNER_TYPES_CONFIGURATION);

    IArtifact aArtifact = rootArtifact.getChild("group1|group2|InnerClassTest_1.0.0|de.test.innertypes|A");
    Assert.assertNotNull(aArtifact);
    IArtifact bArtifact = rootArtifact.getChild("group1|group2|InnerClassTest_1.0.0|de.test.innertypes|B");
    Assert.assertNull(bArtifact);

    Collection<IDependency> dependencies = aArtifact.getDependencies();
    Assert.assertEquals(6, dependencies.size());
    for (IDependency iDependency : dependencies) {
      System.out.println(iDependency);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * @throws AmbiguousElementException
   */
  @Test
  public void testMoveOuterClass() throws CoreException, AmbiguousElementException {

    // transform the model
    IRootArtifact rootArtifact = getModularizedSystem().getArtifactModel(
        ArtifactModelConfiguration.AGGREGATE_INNER_TYPES_CONFIGURATION);
    ArtifactUtils.dumpArtifact(rootArtifact);
    
    //
    IBundleMakerArtifact aArtifact = rootArtifact.getChild("group1|group2|InnerClassTest_1.0.0|de.test.innertypes|A");
    Assert.assertNotNull(aArtifact);
    IBundleMakerArtifact packageArtifact = rootArtifact
        .getChild("group1|group2|InnerClassTest_1.0.0|de.test.innertypes");
    Assert.assertNotNull(packageArtifact);
    // TODO: is this the intended behavior?
    Assert.assertEquals(packageArtifact.getChildren().size(), 1);
    
    //
    packageArtifact.removeArtifact(aArtifact);
    Assert.assertEquals(packageArtifact.getChildren().size(), 0);
    packageArtifact.addArtifact(aArtifact);
    Assert.assertEquals(packageArtifact.getChildren().size(), 1);
  }
}
