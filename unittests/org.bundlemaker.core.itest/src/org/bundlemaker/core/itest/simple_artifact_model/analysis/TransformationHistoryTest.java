package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.transformation.AddArtifactsTransformation;
import org.bundlemaker.core.transformation.CreateGroupTransformation;
import org.bundlemaker.core.transformation.CreateModuleTransformation;
import org.bundlemaker.core.transformation.DefaultArtifactSelector;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TransformationHistoryTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void createGroupHistory() {

    // we have a group transformation that is done by the test....
    Assert.assertEquals(1, getModularizedSystem().getTransformations().size());

    // create a new group
    _binModel.getGroup2Artifact().getOrCreateGroup("NewGroup");

    //
    Assert.assertEquals(2, getModularizedSystem().getTransformations().size());
    Assert.assertEquals(CreateGroupTransformation.class, getModularizedSystem().getTransformations().get(1).getClass());

    // create a new group
    _binModel.getRootArtifact().getOrCreateGroup("NewGroup2");

    //
    Assert.assertEquals(3, getModularizedSystem().getTransformations().size());
    Assert.assertEquals(CreateGroupTransformation.class, getModularizedSystem().getTransformations().get(1).getClass());
    Assert.assertEquals(CreateGroupTransformation.class, getModularizedSystem().getTransformations().get(2).getClass());
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void createModuleHistory() {

    // we have a group transformation that is done by the test....
    Assert.assertEquals(1, getModularizedSystem().getTransformations().size());

    // create a new group
    _binModel.getGroup2Artifact().getOrCreateModule("hallo", "1.2.3");

    //
    Assert.assertEquals(2, getModularizedSystem().getTransformations().size());
    Assert
        .assertEquals(CreateModuleTransformation.class, getModularizedSystem().getTransformations().get(1).getClass());
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void addResourcesHistory() {

    // we have one transformation (a group transformation) that is done by the test....
    Assert.assertEquals(1, getModularizedSystem().getTransformations().size());

    // STEP 1: create a new module
    IModuleArtifact newModuleArtifact = _binModel.getGroup2Artifact().getOrCreateModule("hallo", "1.2.3");
    assertResourcesCount(_binModel.getMainModuleArtifact(), 2);
    assertResourcesCount(newModuleArtifact, 0);

    // assert one 'CreateModuleTransformation' transformation
    Assert.assertEquals(2, getModularizedSystem().getTransformations().size());
    Assert
        .assertEquals(CreateModuleTransformation.class, getModularizedSystem().getTransformations().get(1).getClass());

    // STEP 2: add the 'Klasse' resource
    newModuleArtifact.addArtifact(_binModel.getKlasseResource());
    assertResourcesCount(_binModel.getMainModuleArtifact(), 1);
    assertResourcesCount(newModuleArtifact, 1);

    // assert 'AddTransformation' transformation
    Assert.assertEquals(3, getModularizedSystem().getTransformations().size());
    Assert
        .assertEquals(AddArtifactsTransformation.class, getModularizedSystem().getTransformations().get(2).getClass());

    // STEP 3: add the 'Test' resource
    newModuleArtifact.addArtifact(_binModel.getTestResource());
    assertResourcesCount(_binModel.getMainModuleArtifact(), 0);
    assertResourcesCount(newModuleArtifact, 2);

    // assert 'AddTransformation' transformation
    Assert.assertEquals(4, getModularizedSystem().getTransformations().size());
    Assert
        .assertEquals(AddArtifactsTransformation.class, getModularizedSystem().getTransformations().get(3).getClass());
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void addArtifactsTransformationHistory() {

    // we have one transformation (a group transformation) that is done by the test....
    Assert.assertEquals(1, getModularizedSystem().getTransformations().size());

    // STEP 1: create a new module
    IModuleArtifact newModuleArtifact = _binModel.getGroup2Artifact().getOrCreateModule("hallo", "1.2.3");
    assertResourcesCount(_binModel.getMainModuleArtifact(), 2);
    assertResourcesCount(newModuleArtifact, 0);

    // assert one 'CreateModuleTransformation' transformation
    Assert.assertEquals(2, getModularizedSystem().getTransformations().size());
    Assert
        .assertEquals(CreateModuleTransformation.class, getModularizedSystem().getTransformations().get(1).getClass());

    // STEP 2: add the 'Klasse' resource
    AddArtifactsTransformation.Configuration configuration = new AddArtifactsTransformation.Configuration(
        newModuleArtifact, new DefaultArtifactSelector(_binModel.getKlasseResource()));
    AddArtifactsTransformation transformation_1 = new AddArtifactsTransformation(configuration.toJsonTree());
    getModularizedSystem().applyTransformations(null, transformation_1);
    
    assertResourcesCount(_binModel.getMainModuleArtifact(), 1);
    assertResourcesCount(newModuleArtifact, 1);

    // assert 'AddTransformation' transformation
    Assert.assertEquals(3, getModularizedSystem().getTransformations().size());
    Assert
        .assertEquals(AddArtifactsTransformation.class, getModularizedSystem().getTransformations().get(2).getClass());

    // STEP 3: add the 'Test' resource
    AddArtifactsTransformation.Configuration configuration_2 = new AddArtifactsTransformation.Configuration(
        newModuleArtifact, new DefaultArtifactSelector(_binModel.getTestResource()));
    AddArtifactsTransformation transformation_2 = new AddArtifactsTransformation(configuration_2.toJsonTree());
    getModularizedSystem().applyTransformations(null, transformation_2);
    assertResourcesCount(_binModel.getMainModuleArtifact(), 0);
    assertResourcesCount(newModuleArtifact, 2);

    // assert 'AddTransformation' transformation
    Assert.assertEquals(4, getModularizedSystem().getTransformations().size());
    Assert
        .assertEquals(AddArtifactsTransformation.class, getModularizedSystem().getTransformations().get(3).getClass());
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void addPackageHistory() {

    // create a new group
    IModuleArtifact newModuleArtifact = _binModel.getGroup2Artifact().getOrCreateModule("hallo", "1.2.3");
    assertResourcesCount(_binModel.getMainModuleArtifact(), 2);
    assertResourcesCount(newModuleArtifact, 0);

    // assert one 'CreateModuleTransformation' transformation
    Assert.assertEquals(2, getModularizedSystem().getTransformations().size());
    Assert
        .assertEquals(CreateModuleTransformation.class, getModularizedSystem().getTransformations().get(1).getClass());

    // add the 'de.test' package
    newModuleArtifact.addArtifact(_binModel.getTestPackage());
    assertResourcesCount(_binModel.getMainModuleArtifact(), 0);
    assertResourcesCount(newModuleArtifact, 2);

    // assert 'AddTransformation' transformation
    Assert.assertEquals(3, getModularizedSystem().getTransformations().size());
    Assert
        .assertEquals(AddArtifactsTransformation.class, getModularizedSystem().getTransformations().get(2).getClass());
  }
}
