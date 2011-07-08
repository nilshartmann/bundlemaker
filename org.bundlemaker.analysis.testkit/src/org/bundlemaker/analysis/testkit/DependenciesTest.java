package org.bundlemaker.analysis.testkit;

import java.util.Collection;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.testkit.framework.AbstractTestKitTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DependenciesTest extends AbstractTestKitTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testDependencies() {

    // get the 'jedit' module
    IArtifact moduleArtifact = getRoot().getChild("group1|group2|jedit_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    // get all the dependencies
    Collection<IDependency> dependencies = moduleArtifact.getDependencies();
    Assert.assertNotNull(dependencies);
    Assert.assertEquals(4731, dependencies.size());

    // assert dependency types
    for (IDependency iDependency : dependencies) {
      Assert.assertNotNull(iDependency.getFrom());
      Assert.assertEquals(ArtifactType.Type, iDependency.getFrom().getType());
      Assert.assertNotNull(iDependency.getTo());
      Assert.assertEquals(ArtifactType.Type, iDependency.getTo().getType());

    }

    IArtifact guiPackageArtifact = getRoot().getChild("group1|group2|jedit_1.0.0|org.gjt.sp.jedit.gui");
    Assert.assertNotNull(guiPackageArtifact);

    IArtifact jeditPackageArtifact = getRoot().getChild("group1|group2|jedit_1.0.0|org.gjt.sp.jedit");
    Assert.assertNotNull(jeditPackageArtifact);

    Assert.assertEquals(944, guiPackageArtifact.getDependencies().size());
    Assert.assertEquals(637, jeditPackageArtifact.getDependencies().size());

    //
    IDependency dependency = guiPackageArtifact.getDependency(jeditPackageArtifact);
    dumpDependency(dependency);

    //
    Assert.assertEquals(944, guiPackageArtifact.getDependencies().size());
    Assert.assertEquals(637, jeditPackageArtifact.getDependencies().size());
  }

  /**
   * <p>
   * </p>
   */
  private void dumpDependency(IDependency iDependency) {
    System.out.println(iDependency.getFrom().getQualifiedName() + " -- " + iDependency.getDependencyKind() + " ["
        + iDependency.getWeight() + "]" + " --> " + iDependency.getTo().getQualifiedName());
  }
}
