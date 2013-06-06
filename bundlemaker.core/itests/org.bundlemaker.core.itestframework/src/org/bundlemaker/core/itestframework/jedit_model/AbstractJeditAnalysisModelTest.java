package org.bundlemaker.core.itestframework.jedit_model;

import static org.bundlemaker.core.itestframework.jedit_model.TOOLS.assertTypeCount;

import org.bundlemaker.core.analysis.AnalysisCore;
import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itestframework.AbstractBundleMakerModelTest;
import org.bundlemaker.core.itestframework.utils.ArtifactTestUtil;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public abstract class AbstractJeditAnalysisModelTest extends AbstractBundleMakerModelTest {

  /** - */
  private JeditAnalysisModel _srcHierarchicalModel;

  /** - */
  private JeditAnalysisModel _binHierarchicalModel;

  /** - */
  private JeditAnalysisModel _srcFlatModel;

  /** - */
  private JeditAnalysisModel _binFlatModel;

  /**
   * {@inheritDoc}
   */
  @Before
  public void before() throws CoreException {

    //
    super.before();

    // prepare the model
    assertTypeCount(getModularizedSystem(), 1466);

    // apply the basic group transformation
    IRootArtifact rootArtifact = AnalysisCore.getAnalysisModel(getModularizedSystem(),
        AnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION);

    IGroupArtifact groupArtifact = rootArtifact.getOrCreateGroup("group1/group2");

    groupArtifact.addArtifact(AnalysisModelQueries.getModuleArtifact(rootArtifact, getTestProjectName(),
        getTestProjectVersion()));

    // TODO
    // // assert the input
    // InputStream inputstream = AbstractJeditAnalysisModelTest.class.getResourceAsStream("results/"
    // + getTestProjectName() + ".txt");
    // assertResult(ModuleUtils.dump(getModularizedSystem().getResourceModule(getTestProjectName(), "1.0.0")),
    // inputstream, getTestProjectName() + getCurrentTimeStamp());

    _srcHierarchicalModel = new JeditAnalysisModel(getModularizedSystem(),
        AnalysisModelConfiguration.HIERARCHICAL_SOURCE_RESOURCES_CONFIGURATION);

    _binHierarchicalModel = new JeditAnalysisModel(getModularizedSystem(),
        AnalysisModelConfiguration.HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION);

    _srcFlatModel = new JeditAnalysisModel(getModularizedSystem(),
        AnalysisModelConfiguration.SOURCE_RESOURCES_CONFIGURATION);

    _binFlatModel = new JeditAnalysisModel(getModularizedSystem(),
        AnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION);

    // // get the root artifact
    // _rootArtifact = getModularizedSystem().getAnalysisModel(getArtifactModelConfiguration());
    // Assert.assertNotNull(_rootArtifact);
    //
    // //
    // if (_rootArtifact.getConfiguration().isIncludeVirtualModuleForMissingTypes()) {
    // Assert.assertEquals(8275, _rootArtifact.getDependenciesTo().size());
    // Assert.assertEquals(8158, _rootArtifact.getDependenciesFrom().size());
    // } else {
    // Assert.assertEquals(8158, _rootArtifact.getDependenciesTo().size());
    // Assert.assertEquals(8158, _rootArtifact.getDependenciesFrom().size());
    // }
    //
    // assertDependencyWeight(getGroup1Artifact(), getJreArtifact(), 1904);
    // assertDependencyWeight(getVelocityModuleArtifact(), getJreArtifact(), 4);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public JeditAnalysisModel getSrcHierarchicalModel() {
    return _srcHierarchicalModel;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public JeditAnalysisModel getBinHierarchicalModel() {
    return _binHierarchicalModel;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public JeditAnalysisModel getSrcFlatModel() {
    return _srcFlatModel;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public JeditAnalysisModel getBinFlatModel() {
    return _binFlatModel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getTestProjectName() {
    return "jedit";
  }

}
