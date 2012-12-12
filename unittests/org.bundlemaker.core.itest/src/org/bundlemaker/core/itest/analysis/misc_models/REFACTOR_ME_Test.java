package org.bundlemaker.core.itest.analysis.misc_models;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itest._framework.AbstractModularizedSystemTest;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ProjectContentType;
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
public class REFACTOR_ME_Test extends AbstractModularizedSystemTest {

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
    _rootArtifact = getModularizedSystem().getAnalysisModel(
        AnalysisModelConfiguration.HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION);
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
    IBundleMakerArtifact purzGroup = _rootArtifact.getOrCreateGroup(new Path("hurz/purz"));
    Assert.assertNotNull(_rootArtifact.getChild("hurz"));
    purzGroup.addArtifact(_rootArtifact.getChild("group1|group2|InnerClassTest_1.0.0"));
    Assert.assertNotNull(_rootArtifact.getChild("hurz"));

    //
    IBundleMakerArtifact module = _rootArtifact.getOrCreateModule("hurz/purz/test", "1.2.2");
    Assert.assertNotNull(_rootArtifact.getChild("hurz|purz|test_1.2.2"));
  }

  @Test
  public void justAddArtifact() {

    // get the module artifact
    IBundleMakerArtifact moduleArtifact = _rootArtifact.getChild("group1|group2|InnerClassTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    // create the new module artifact
    IModuleArtifact newModuleArtifact = _rootArtifact.getOrCreateModule("NewModule", "2.0.0");
    Assert.assertNotNull(newModuleArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("NewModule", "2.0.0"));

    // get the 'de.test.innertypes.A$AA.class' artifact
    IBundleMakerArtifact resource = moduleArtifact.getChild("de|test|innertypes|A$AA.class");
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
    IBundleMakerArtifact moduleArtifact = _rootArtifact.getChild("group1|group2|InnerClassTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    // create the new module artifact
    IModuleArtifact newModuleArtifact = _rootArtifact.getOrCreateModule("NewModule", "2.0.0");
    Assert.assertNotNull(newModuleArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("NewModule", "2.0.0"));

    // get the 'de.test.innertypes.A$AA.class' artifact
    IBundleMakerArtifact resource = moduleArtifact.getChild("de|test|innertypes|A$AA.class");

    // just add the artifact
    newModuleArtifact.addArtifact(resource);
    Assert.assertNull(moduleArtifact.getChild("de|test|innertypes|A$AA.class"));
    Assert.assertNull(moduleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA"));
    Assert.assertNotNull(newModuleArtifact.getChild("de|test|innertypes|A$AA.class"));
    Assert.assertNotNull(newModuleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA"));

    IResourceModule module = getModularizedSystem().getResourceModule("NewModule", "2.0.0");
    Assert.assertNotNull(module.getResource("de/test/innertypes/A$AA.class", ProjectContentType.BINARY));

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
    IBundleMakerArtifact moduleArtifact = _rootArtifact.getChild("group1|group2|InnerClassTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    // create the new module artifact
    IModuleArtifact newModuleArtifact = _rootArtifact.getOrCreateModule("NewModule", "2.0.0");
    Assert.assertNotNull(newModuleArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("NewModule", "2.0.0"));

    // get the 'de.test.innertypes.A$AA.class' artifact
    IBundleMakerArtifact type_1 = moduleArtifact.getChild("de|test|innertypes|A$AA.class|A$AA");
    IBundleMakerArtifact type_2 = moduleArtifact.getChild("de|test|referenced|X.class|X");
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
