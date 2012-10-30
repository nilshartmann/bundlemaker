package org.bundlemaker.core.itest.complex.analysis;

import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * Example: group1/group2/jedit_1.0.0 velocity_1.5 jdk16_jdk16 << Missing Types >>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DnDHierachicalPackagesTest extends AbstractJeditArtifactTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testMovePackageToNewModuleAndBack() {

    //
    String jeditOriginalContent = ArtifactUtils.artifactToString(getJeditModuleArtifact());

    //
    IBundleMakerArtifact testModule = getRootArtifact().getOrCreateModule("testModule", "1.0.0");
    Assert.assertNotNull(testModule);

    //
    Assert.assertNotNull(getJeditModuleArtifact().getChild("org|gjt|sp|jedit"));
    Assert.assertNull(testModule.getChild("org|gjt|sp|jedit"));
    testModule.addArtifact(getJeditModuleArtifact().getChild("org|gjt|sp|jedit"));
    // Assert.assertNull(getJeditModuleArtifact().getChild("org|gjt|sp|jedit"));
    // Assert.assertNotNull(testModule.getChild("org|gjt|sp|jedit"));
    getJeditModuleArtifact().addArtifact(testModule.getChild("org|gjt|sp|jedit"));
    ArtifactUtils.dumpArtifact(testModule);
    ArtifactUtils.dumpArtifact(getJeditModuleArtifact());
    // Assert.assertNotNull(getJeditModuleArtifact().getChild("org|gjt|sp|jedit"));
    // Assert.assertNull(testModule.getChild("org|gjt|sp|jedit"));

    // Assert.assertEquals(testModule, packageArtifact.getParent(ArtifactType.Module));
    //
    // testModule.addArtifact(packageArtifact);
    //
    // Assert.assertEquals(jeditOriginalContent, ArtifactUtils.artifactToString(getJeditModuleArtifact()));
  }

  @Test
  public void testMovePackageWithTypesAndResources() {

    //
    String jeditOriginalContent = ArtifactUtils.artifactToString(getJeditModuleArtifact());

    //
    IBundleMakerArtifact testModule = getRootArtifact().getOrCreateModule("testModule", "1.0.0");
    Assert.assertNotNull(testModule);

    String packagePath = "org|gjt|sp|util";

    //
    Assert.assertNotNull(getJeditModuleArtifact().getChild(packagePath));
    Assert.assertNull(testModule.getChild(packagePath));
    testModule.addArtifact(getJeditModuleArtifact().getChild(packagePath));
    // Assert.assertNull(getJeditModuleArtifact().getChild("org|gjt|sp|jedit"));
    // Assert.assertNotNull(testModule.getChild("org|gjt|sp|jedit"));
    // getJeditModuleArtifact().addArtifact(testModule.getChild(packagePath));
    ArtifactUtils.dumpArtifact(testModule);
    // ArtifactUtils.dumpArtifact(getJeditModuleArtifact());
    // Assert.assertNotNull(getJeditModuleArtifact().getChild("org|gjt|sp|jedit"));
    // Assert.assertNull(testModule.getChild("org|gjt|sp|jedit"));

    // Assert.assertEquals(testModule, packageArtifact.getParent(ArtifactType.Module));
    //
    // testModule.addArtifact(packageArtifact);
    //
    // Assert.assertEquals(jeditOriginalContent, ArtifactUtils.artifactToString(getJeditModuleArtifact()));
  }

  @Override
  public IAnalysisModelConfiguration getArtifactModelConfiguration() {
    return IAnalysisModelConfiguration.HIERARCHICAL_SOURCE_RESOURCES_CONFIGURATION;
  }
}
