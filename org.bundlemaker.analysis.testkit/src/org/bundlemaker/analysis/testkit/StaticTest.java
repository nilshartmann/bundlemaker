package org.bundlemaker.analysis.testkit;

import java.io.FileNotFoundException;
import java.util.Collection;

import junit.framework.Assert;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.testkit.framework.AbstractTestKitTest;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class StaticTest extends AbstractTestKitTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testRootArtifact() {

    // assert 'getType'
    Assert.assertEquals(ArtifactType.Root, getRoot().getType());

    // assert 'getName'
    // assert 'getQualifiedName'
    Assert.assertEquals("Original", getRoot().getName());
    Assert.assertEquals("Original", getRoot().getQualifiedName());
    Assert.assertEquals(getRoot().getName(), getRoot().getQualifiedName());

    // assert 'properties'
    getRoot().setProperty("null", null);
    Assert.assertNull(getRoot().getProperty("null"));

    FileNotFoundException exception = new FileNotFoundException();
    getRoot().setProperty("exception", exception);
    Assert.assertEquals(exception, getRoot().getProperty("exception", FileNotFoundException.class));

    // assert 'getParent' returns null
    Assert.assertEquals(null, getRoot().getParent());
    Assert.assertEquals(null, getRoot().getParent(ArtifactType.Root));
    Assert.assertEquals(null, getRoot().getParent(ArtifactType.Group));
    Assert.assertEquals(null, getRoot().getParent(ArtifactType.Module));
    Assert.assertEquals(null, getRoot().getParent(ArtifactType.Package));
    Assert.assertEquals(null, getRoot().getParent(ArtifactType.Resource));
    Assert.assertEquals(null, getRoot().getParent(ArtifactType.Type));

    // assert 'getChildren'
    Assert.assertNotNull(getRoot().getChildren());
    // Assert.assertEquals(1, getRoot().getChildren().size());

    // assert 'getChild'
    Assert.assertNotNull(getRoot().getChild("group1"));
    // Assert.assertNotNull(getRoot().getChild("group1|group1/group2"));
    Assert.assertNotNull(getRoot().getChild("group1|group2"));
    Assert.assertNotNull(getRoot().getChild("group1|group2|jedit_1.0.0"));
    Assert.assertNotNull(getRoot().getChild("group1|group2|jedit_1.0.0|org.gjt.sp.jedit.bsh.org.objectweb.asm"));
    Assert
        .assertNotNull(getRoot()
            .getChild(
                "group1|group2|jedit_1.0.0|org.gjt.sp.jedit.bsh.org.objectweb.asm|org.gjt.sp.jedit.bsh.org.objectweb.asm.ByteVector"));
    Assert.assertNotNull(getRoot().getChild(
        "group1|group2|jedit_1.0.0|org.gjt.sp.jedit.bsh.org.objectweb.asm|ByteVector"));
    Assert.assertNotNull(getRoot().getChild("group1|group2|jedit_1.0.0|asm|ByteVector"));
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void testGroup() {

    // System.out.println(Util.toString(getRoot()));
    IArtifact blaGroup_artifact = getRoot().getChild("group1");
    Assert.assertNotNull(blaGroup_artifact);

    IArtifact blubGroup_artifact = getRoot().getChild("group1|group2");
    Assert.assertNotNull(blaGroup_artifact);

    // get the 'jedit_1.0.0' artifact
    IArtifact jeditModule_artifact = getRoot().getChild("group1|group2|jedit_1.0.0");
    Assert.assertNotNull(jeditModule_artifact);

    // test parent relationship
    Assert.assertEquals(blaGroup_artifact, blubGroup_artifact.getParent());
    Assert.assertEquals(getRoot(), jeditModule_artifact.getParent(ArtifactType.Root));
    Assert.assertEquals(blubGroup_artifact, jeditModule_artifact.getParent());

    //
    Assert.assertEquals(blubGroup_artifact, jeditModule_artifact.getParent(ArtifactType.Group));
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void testModule() {

    // System.out.println(Util.toString(getRoot()));

    // get the 'jedit_1.0.0' artifact
    IArtifact jeditModule_artifact = getRoot().getChild("group1|group2|jedit_1.0.0");
    Assert.assertNotNull(jeditModule_artifact);

    // test 'getChildren'
    Assert.assertEquals(29, jeditModule_artifact.getChildren().size());

    // test 'getParent'
    Assert.assertEquals(getRoot().getChild("group1|group2"), jeditModule_artifact.getParent());
    Assert.assertEquals(getRoot().getChild("group1|group2"), jeditModule_artifact.getParent(ArtifactType.Group));
    Assert.assertEquals(getRoot(), jeditModule_artifact.getParent(ArtifactType.Root));

    // test 'getLeafs'
    Collection<IArtifact> typesAndResources = jeditModule_artifact.getLeafs();

    // assert that only top level types are contained
    Assert.assertEquals(504, typesAndResources.size());
    for (IArtifact iArtifact : typesAndResources) {
      Assert.assertEquals(-1, iArtifact.getName().indexOf('$'));
    }

    // test
    // jeditModule_artifact.
  }
}
