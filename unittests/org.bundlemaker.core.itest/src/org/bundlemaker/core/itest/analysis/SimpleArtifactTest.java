package org.bundlemaker.core.itest.analysis;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.modules.IModule;
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
  public void removeArtifactAndAddArtifact() {

    // get the module artifact
    IArtifact moduleArtifact = _rootArtifact.getChild("group1|group2|InnerClassTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    // create the new module artifact
    IModuleArtifact newModuleArtifact = _rootArtifact.getOrCreateModule("NewModule", "2.0.0");
    Assert.assertNotNull(newModuleArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("NewModule", "2.0.0"));

    // get the 'de.test.innertypes.A$AA.class' artifact
    IArtifact resource = moduleArtifact.getChild("de|test|innertypes|A$AA.class");
    Assert.assertNotNull(resource);
    Assert.assertNotNull(moduleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA"));

    // remove the artifact
    moduleArtifact.removeArtifact(resource);
    Assert.assertNull(moduleArtifact.getChild("de|test|innertypes|A$AA.class"));
    Assert.assertNull(moduleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA"));

    newModuleArtifact.addArtifact(resource);
    Assert.assertNotNull(newModuleArtifact.getChild("de|test|innertypes|A$AA.class"));
    Assert.assertSame(resource, newModuleArtifact.getChild("de|test|innertypes|A$AA.class"));
    Assert.assertNotNull(newModuleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA"));
  }

  @Test
  public void justAddArtifact() {

    // get the module artifact
    IArtifact moduleArtifact = _rootArtifact.getChild("group1|group2|InnerClassTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    // create the new module artifact
    IModuleArtifact newModuleArtifact = _rootArtifact.getOrCreateModule("NewModule", "2.0.0");
    Assert.assertNotNull(newModuleArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("NewModule", "2.0.0"));

    // get the 'de.test.innertypes.A$AA.class' artifact
    IArtifact resource = moduleArtifact.getChild("de|test|innertypes|A$AA.class");
    Assert.assertNotNull(resource);
    Assert.assertNotNull(moduleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA"));

    // just add the artifact
    newModuleArtifact.addArtifact(resource);

    Assert.assertNull(moduleArtifact.getChild("de|test|innertypes|A$AA.class"));
    Assert.assertNull(moduleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA"));
    Assert.assertNotNull(newModuleArtifact.getChild("de|test|innertypes|A$AA.class"));
    Assert.assertSame(resource, newModuleArtifact.getChild("de|test|innertypes|A$AA.class"));
    Assert.assertNotNull(newModuleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA"));
  }

  @Test
  public void multipleAddArtifact() {

    // get the module artifact
    IArtifact moduleArtifact = _rootArtifact.getChild("group1|group2|InnerClassTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    // create the new module artifact
    IModuleArtifact newModuleArtifact = _rootArtifact.getOrCreateModule("NewModule", "2.0.0");
    Assert.assertNotNull(newModuleArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("NewModule", "2.0.0"));

    // get the 'de.test.innertypes.A$AA.class' artifact
    IArtifact resource = moduleArtifact.getChild("de|test|innertypes|A$AA.class");

    // just add the artifact
    newModuleArtifact.addArtifact(resource);
    Assert.assertNull(moduleArtifact.getChild("de|test|innertypes|A$AA.class"));
    Assert.assertNull(moduleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA"));
    Assert.assertNotNull(newModuleArtifact.getChild("de|test|innertypes|A$AA.class"));
    Assert.assertNotNull(newModuleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA"));

    IResourceModule module = getModularizedSystem().getResourceModule("NewModule", "2.0.0");
    Assert.assertNotNull(module.getResource("de/test/innertypes/A$AA.class", ContentType.BINARY));

    //
    moduleArtifact.addArtifact(resource);
    Assert.assertNotNull(moduleArtifact.getChild("de|test|innertypes|A$AA.class"));
    Assert.assertNotNull(moduleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA"));
    Assert.assertNull(newModuleArtifact.getChild("de|test|innertypes|A$AA.class"));
    Assert.assertNull(newModuleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA"));
  }
  
  @Test
  public void multipleAddType() {

    // get the module artifact
    IArtifact moduleArtifact = _rootArtifact.getChild("group1|group2|InnerClassTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    // create the new module artifact
    IModuleArtifact newModuleArtifact = _rootArtifact.getOrCreateModule("NewModule", "2.0.0");
    Assert.assertNotNull(newModuleArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("NewModule", "2.0.0"));

    // get the 'de.test.innertypes.A$AA.class' artifact
    IArtifact type_1 = moduleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA");
    IArtifact type_2 = moduleArtifact.getChild("de|test|referenced|X.class|X");
    Assert.assertNotNull(type_1);
    Assert.assertNotNull(type_2);
    
    // just add the artifact
    newModuleArtifact.addArtifact(type_1);
    Assert.assertNull(moduleArtifact.getChild("de|test|innertypes|A$AA.class"));
    Assert.assertNull(moduleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA"));
    Assert.assertNotNull(newModuleArtifact.getChild("de|test|innertypes|A$AA.class"));
    Assert.assertNotNull(newModuleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA"));
    Assert.assertSame(type_1, newModuleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA"));
    
    // just add the artifact
    newModuleArtifact.addArtifact(type_2);
    Assert.assertNull(moduleArtifact.getChild("de|test|referenced|X.class|X"));
    Assert.assertNull(moduleArtifact.getChild("de|test|referenced|X.class"));
    Assert.assertNotNull(newModuleArtifact.getChild("de|test|referenced|X.class"));
    Assert.assertNotNull(newModuleArtifact.getChild("de|test|referenced|X.class|X"));
    Assert.assertSame(type_2, newModuleArtifact.getChild("de|test|referenced|X.class|X"));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return InnerClassTest.class.getSimpleName();
  }
}
