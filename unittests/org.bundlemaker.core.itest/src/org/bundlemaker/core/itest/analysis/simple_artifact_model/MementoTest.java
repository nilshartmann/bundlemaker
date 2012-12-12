package org.bundlemaker.core.itest.analysis.simple_artifact_model;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.transformation.AddArtifactsTransformation;
import org.bundlemaker.core.transformation.CreateGroupTransformation;
import org.bundlemaker.core.transformation.CreateModuleTransformation;
import org.bundlemaker.core.transformation.DefaultArtifactSelector;
import org.bundlemaker.core.util.gson.GsonHelper;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MementoTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void artifactModelConfigurationMemento() throws Exception {

    //
    IAnalysisModelConfiguration configuration = _binModel.getGroup1Artifact().getConfiguration();

    String memento = GsonHelper.gson(getModularizedSystem()).toJson(configuration);
    Assert.assertNotNull(memento);

    AnalysisModelConfiguration result = GsonHelper.gson(getModularizedSystem()).fromJson(memento,
        AnalysisModelConfiguration.class);
    Assert.assertNotNull(result);

    //
    Assert.assertEquals(configuration, result);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void bundleMakerArtifactMemento() throws Exception {

    //
    _binModel.getRootArtifact().accept(new IAnalysisModelVisitor.Adapter() {

      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {
        // skip non-resource artifacts
        if (moduleArtifact.isResourceModule()) {
          return onVisit(moduleArtifact);
        } else {
          return false;
        }
      }

      @Override
      public boolean onVisit(IBundleMakerArtifact artifact) {

        //
        String memento = GsonHelper.gson(getModularizedSystem()).toJson(artifact);
        Assert.assertNotNull(memento);
        //
        IBundleMakerArtifact restoredArtifact = GsonHelper.gson(getModularizedSystem()).fromJson(memento,
            IBundleMakerArtifact.class);
        Assert.assertNotNull(restoredArtifact);
        //
        Assert.assertSame(artifact, restoredArtifact);
        //
        return true;
      }
    });
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void addArtifactsTransformationMemento() throws Exception {

    //
    AddArtifactsTransformation.Configuration configuration = new AddArtifactsTransformation.Configuration(
        _binModel.getGroup1Artifact(), new DefaultArtifactSelector(
            _binModel.getMainModuleArtifact()));

    String memento = GsonHelper.gson(getModularizedSystem()).toJson(configuration);

    //
    AddArtifactsTransformation.Configuration restoredConfiguration = GsonHelper.gson(getModularizedSystem()).fromJson(
        memento,
        AddArtifactsTransformation.Configuration.class);

    //
    Assert.assertEquals(restoredConfiguration, configuration);

    //
    Assert.assertEquals(new AddArtifactsTransformation(configuration.toJsonTree()), new AddArtifactsTransformation(
        configuration.toJsonTree()));
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void createModuleTransformationMemento() throws Exception {

    //
    CreateModuleTransformation.Configuration configuration = new CreateModuleTransformation.Configuration(
        _binModel.getGroup1Artifact(), "testModule", "1.2.3");

    //
    String memento = GsonHelper.gson(getModularizedSystem()).toJson(configuration);

    //
    CreateModuleTransformation.Configuration restoredConfiguration = GsonHelper.gson(getModularizedSystem()).fromJson(
        memento,
        CreateModuleTransformation.Configuration.class);

    //
    Assert.assertEquals(configuration, restoredConfiguration);

    //
    Assert.assertEquals(new CreateModuleTransformation(configuration.toJsonTree()), new CreateModuleTransformation(
        configuration.toJsonTree()));
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void createGroupTransformationMemento() throws Exception {

    //
    CreateGroupTransformation.Configuration configuration = new CreateGroupTransformation.Configuration(
        _binModel.getGroup1Artifact(), new Path("testGroup"));

    //
    String memento = GsonHelper.gson(getModularizedSystem()).toJson(configuration);

    //
    CreateGroupTransformation.Configuration restoredConfiguration = GsonHelper.gson(getModularizedSystem()).fromJson(
        memento,
        CreateGroupTransformation.Configuration.class);

    //
    Assert.assertEquals(configuration, restoredConfiguration);

    //
    Assert.assertEquals(new CreateGroupTransformation(configuration.toJsonTree()), new CreateGroupTransformation(
        configuration.toJsonTree()));
  }

  // /**
  // * <p>
  // * </p>
  // */
  // @Test
  // public void transformationHistory() {
  //
  // // transform
  // IModuleArtifact newModuleArtifact = _binModel.getGroup2Artifact().getOrCreateModule("hallo", "1.2.3");
  // newModuleArtifact.addArtifact(_binModel.getKlasseResource());
  //
  // //
  // List<ITransformation> transformations = getModularizedSystem().getTransformations();
  // transformations = transformations.subList(1, transformations.size());
  //
  // String json = GsonHelper.gson(getModularizedSystem()).toJson(transformations);
  //
  // System.out.println(json);
  // }
}
