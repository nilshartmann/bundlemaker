package org.bundlemaker.core.itest.complex.analysis;

import junit.framework.Assert;

import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.junit.Test;

/**
 * <p>
 * Example: group1/group2/jedit_1.0.0 velocity_1.5 jdk16_jdk16 << Missing Types >>
 * </p>
 * <p>
 * This test assures that the 'mixed' mode works correctly. Mixed mode means that the artifact tree is specified as a
 * source artifact tree, but modules without source artifacts shows their binary content.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DnDSourceBinaryArtifactTreeTest extends AbstractJeditArtifactTest {

  /**
   * <p>
   * Creates a new group 'testGroup' (below root) and adds the 'Jedit' module to it without removing it from its parent.
   * It must be automatically removed from its former parent.
   * </p>
   */
  @Test
  public void testAddModuleWithoutRemove() {

    // create test group and add the 'jedit' artifact
    IBundleMakerArtifact moduleGroup = getRootArtifact().getOrCreateModule("testModule", "1.2.3");

    ResourceCounter counter = new ResourceCounter();
    moduleGroup.accept(counter);
    Assert.assertEquals(0, counter.getCount());

    IPackageArtifact packageArtifact = getVelocityModuleArtifact().findChild(IPackageArtifact.class,
        "org.apache.velocity.app");
    counter = new ResourceCounter();
    packageArtifact.accept(counter);
    int packageResourceCount = counter.getCount();

    moduleGroup.addArtifact(packageArtifact);
    counter = new ResourceCounter();
    moduleGroup.accept(counter);
    Assert.assertEquals(packageResourceCount, counter.getCount());
  }

  public IArtifactModelConfiguration getArtifactModelConfiguration() {
    return IArtifactModelConfiguration.SOURCE_RESOURCES_CONFIGURATION;
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private final class ResourceCounter extends IArtifactTreeVisitor.Adapter {

    /** - */
    private int _count;

    /**
     * <p>
     * Creates a new instance of type {@link ResourceCounter}.
     * </p>
     */
    private ResourceCounter() {
      _count = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean visit(IResourceArtifact resourceArtifact) {
      _count++;
      return false;
    }

    /**
     * <p>
     * </p>
     * 
     * @return the count
     */
    public int getCount() {
      return _count;
    }
  }
}
