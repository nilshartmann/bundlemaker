package org.bundlemaker.core.itest.modules;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.itest.analysis.InnerClassTest;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.modules.modifiable.MovableUnit;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SimpleArtifactTest extends AbstractModularizedSystemTest {

  // the resource module
  private IResourceModule _resourceModule;

  //
  private IRootArtifact   _rootArtifact;

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Before
  public void initResourceModule() throws CoreException {

    //
    _resourceModule = getModularizedSystem().getResourceModule("InnerClassTest", "1.0.0");
    _rootArtifact = getModularizedSystem().getArtifactModel(
        ArtifactModelConfiguration.HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION);
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void testResourceModuleTypes() {

    // //
    // Assert.assertNotNull(_rootArtifact.getChild("group1|group2|InnerClassTest_1.0.0"));
    //
    // //
    // ((IModifiableResourceModule) _resourceModule).setClassification(new Path("hurz"));
    //
    // //
    // Assert.assertNotNull(_rootArtifact.getChild("hurz|InnerClassTest_1.0.0"));
    // Assert.assertEquals(new Path("hurz"), _resourceModule.getClassification());

    //
    Assert.assertNull(_rootArtifact.getChild("hurz"));
    IArtifact purzGroup = _rootArtifact.getOrCreateGroup("hurz/purz");
    Assert.assertNotNull(_rootArtifact.getChild("hurz"));
    purzGroup.addArtifact(_rootArtifact.getChild("group1|group2|InnerClassTest_1.0.0"));
    Assert.assertNotNull(_rootArtifact.getChild("hurz"));

    //
    IArtifact module = _rootArtifact.getOrCreateModule("hurz/purz/test", "1.2.2");
    Assert.assertNotNull(_rootArtifact.getChild("hurz|purz|test_1.2.2"));
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void test() {

    //
    IArtifact moduleArtifact = _rootArtifact.getChild("group1|group2|InnerClassTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    //
    IModuleArtifact newModuleArtifact = _rootArtifact.getOrCreateModule("NewModule", "2.0.0");
    Assert.assertNotNull(newModuleArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("NewModule", "2.0.0"));

    //
    IArtifact resource = moduleArtifact.getChild("de|test|innertypes|A$AA.class");
    Assert.assertNotNull(resource);

    //
    moduleArtifact.removeArtifact(resource);
    newModuleArtifact.addArtifact(resource);

    resource = moduleArtifact.getChild("de|test|innertypes|A$AA.class");
    Assert.assertNull(resource);

    resource = newModuleArtifact.getChild("de|test|innertypes|A$AA.class");
    Assert.assertNull(resource);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return InnerClassTest.class.getSimpleName();
  }
}
