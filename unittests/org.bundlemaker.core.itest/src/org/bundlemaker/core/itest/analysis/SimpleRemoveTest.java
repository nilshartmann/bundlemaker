package org.bundlemaker.core.itest.analysis;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.modifiable.MovableUnit;
import org.bundlemaker.core.projectdescription.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SimpleRemoveTest extends AbstractModularizedSystemTest {

  /** - */
  private IRootArtifact    _rootArtifact;

  /** - */
  private IPackageArtifact _packageArtifact;

  /** - */
  private ITypeArtifact    _typeArtifact;

  /** - */
  private IModuleArtifact  _moduleArtifact;

  /**
   * <p>
   * </p>
   */
  @Before
  public void setup() {

    _rootArtifact = getModularizedSystem().getArtifactModel(
        ArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION);

    _packageArtifact = ArtifactVisitorUtils.findPackageArtifact(_rootArtifact, "de.test.basic");
    Assert.assertNotNull(_packageArtifact);

    _typeArtifact = ArtifactVisitorUtils.findTypeArtifact(_packageArtifact, "de.test.basic.TestClass");
    Assert.assertNotNull(_typeArtifact);

    _moduleArtifact = ArtifactVisitorUtils.findModuleArtifact(_rootArtifact, "BasicArtifactTest");
    Assert.assertNotNull(_moduleArtifact);
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void testHistory() {

    //
    // _packageArtifact.removeArtifact(_typeArtifact);

    // get the resource module
    IModifiableResourceModule resourceModule = (IModifiableResourceModule) getModularizedSystem().getResourceModule(
        "BasicArtifactTest", "1.0.0");

    // assert that resources are contained in the modularized system
    Assert.assertEquals(1, getModularizedSystem().getResourceModule("BasicArtifactTest", "1.0.0")
        .getSelfResourceContainer().getResources(ContentType.BINARY).size());
    Assert.assertEquals(1, getModularizedSystem().getResourceModule("BasicArtifactTest", "1.0.0")
        .getSelfResourceContainer().getResources(ContentType.SOURCE).size());
    
    resourceModule.getModifiableSelfResourceContainer().removeMovableUnit(
        MovableUnit.createFromType(resourceModule.getType("de.test.basic.TestClass"), getModularizedSystem()));

    Assert.assertEquals(0, getModularizedSystem().getResourceModule("BasicArtifactTest", "1.0.0")
        .getSelfResourceContainer().getResources(ContentType.BINARY).size());
    Assert.assertEquals(0, getModularizedSystem().getResourceModule("BasicArtifactTest", "1.0.0")
        .getSelfResourceContainer().getResources(ContentType.SOURCE).size());

    ArtifactUtils.dumpArtifact(_moduleArtifact);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return BasicArtifactTest.class.getSimpleName();
  }
}
