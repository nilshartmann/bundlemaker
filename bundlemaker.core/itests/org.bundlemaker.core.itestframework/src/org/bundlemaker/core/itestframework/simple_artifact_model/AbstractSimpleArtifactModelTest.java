package org.bundlemaker.core.itestframework.simple_artifact_model;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itestframework.AbstractBundleMakerModelTest;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Before;

/**
 * <pre>
 * Group : group1
 *   Group : group2
 *     Module : group1/group2/SimpleArtifactModelTest_1.0.0
 *       Package : de.test
 *         Resource : Test.class
 *           Type : de.test.Test
 *         Resource : Klasse.class
 *           Type : de.test.Klasse
 * Module : jdk16_jdk16
 * </pre>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractSimpleArtifactModelTest extends AbstractBundleMakerModelTest {

  /** TEST_PROJECT_NAME */
  public static final String  TEST_PROJECT_NAME = "SimpleArtifactModelTest";

  /** the binary model */
  private SimpleArtifactModel _binModel;

  /** the source model */
  private SimpleArtifactModel _srcModel;

  /**
   * {@inheritDoc}
   */
  @Before
  public void before() throws CoreException {

    super.before();

    // apply the basic group transformation
    IRootArtifact rootArtifact = getModularizedSystem().getAnalysisModel(
        AnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION);
    IGroupArtifact groupArtifact = rootArtifact.getOrCreateGroup("group1/group2");
    groupArtifact.addArtifact(AnalysisModelQueries.getModuleArtifact(rootArtifact, getTestProjectName(),
        getTestProjectVersion()));

    //
    _binModel = new SimpleArtifactModel(getBundleMakerProject().getModularizedSystemWorkingCopy(),
        IAnalysisModelConfiguration.HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION);
    _srcModel = new SimpleArtifactModel(getBundleMakerProject().getModularizedSystemWorkingCopy(),
        IAnalysisModelConfiguration.HIERARCHICAL_SOURCE_RESOURCES_CONFIGURATION);

    //
    SimpleArtifactModelAssert.initialAsserts(_binModel, _srcModel);
  }

  /**
   * {@inheritDoc}
   */
  @After
  public void after() throws CoreException {
    _binModel = null;
    _srcModel = null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public SimpleArtifactModel getBinModel() {
    return _binModel;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public SimpleArtifactModel getSrcModel() {
    return _srcModel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getTestProjectName() {
    return TEST_PROJECT_NAME;
  }
}
