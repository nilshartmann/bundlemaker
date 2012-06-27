package org.bundlemaker.core.itest.analysis.cmd;

import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.cmd.SimpleResourceArtifactSelector;
import org.bundlemaker.core.analysis.cmd.IResourceArtifactSelector;
import org.bundlemaker.core.analysis.cmd.MoveResourcesCommand;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.itest.analysis.ArtifactVisitorUtils;
import org.bundlemaker.core.itest.analysis.BasicArtifactTest;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MoveResourcesCommandTest extends AbstractModularizedSystemTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return BasicArtifactTest.class.getSimpleName();
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void moveResourcesCmd() throws Exception {

    // step 1: get the rootArtifact
    IRootArtifact rootArtifact = getModularizedSystem().getArtifactModel(
        ArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION);

    // the package artifact
    IPackageArtifact packageArtifact = ArtifactVisitorUtils.findPackageArtifact(rootArtifact, "de.test.basic");
    Assert.assertNotNull(packageArtifact);
    Assert.assertEquals(1, packageArtifact.getChildren(IResourceArtifact.class).size());

    //
    MoveResourcesCommand cmd = new MoveResourcesCommand();
    IModuleArtifact newModule = rootArtifact.getOrCreateModule("neuesModule", "1.2.3");

    //
    SimpleResourceArtifactSelector selector = new SimpleResourceArtifactSelector();
    for (IResourceArtifact resourceArtifact : packageArtifact.getChildren(IResourceArtifact.class)) {
      selector.getResourceArtifacts().add(resourceArtifact);
    }

    //
    cmd.setTargetArtifact(newModule);
    cmd.add(selector);

    //
    cmd.execute();

    //
    Assert.assertEquals(0, packageArtifact.getChildren(IResourceArtifact.class).size());
    IPackageArtifact newPackageArtifact = ArtifactVisitorUtils.findPackageArtifact(newModule, "de.test.basic");
    Assert.assertNotNull(newPackageArtifact);
    Assert.assertEquals(1, newPackageArtifact.getChildren(IResourceArtifact.class).size());

    //
    cmd.undo();

    //
    Assert.assertEquals(1, packageArtifact.getChildren(IResourceArtifact.class).size());
    Assert.assertEquals(0, newPackageArtifact.getChildren(IResourceArtifact.class).size());
  }
}
