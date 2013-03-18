package org.bundlemaker.core.itestframework;

import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.itestframework.internal.TestProjectCreator;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractBundleMakerModelTest {

  /** TEST_PROJECT_VERSION */
  public static final String                      DEFAULT_TEST_PROJECT_VERSION    = "1.0.0";

  /** - */
  private IBundleMakerProject                     _bundleMakerProject;

  /** - */
  private IModifiableModularizedSystem            _modularizedSystem;

  /** has been set up? */
  private static Map<String, IBundleMakerProject> _initializedBundleMakerProjects = new HashMap<String, IBundleMakerProject>();

  /** - */
  private String                                  _initialState_binaryHierarchical;

  private String                                  _initialState_sourceHierarchical;

  /**
   * {@inheritDoc}
   */
  @Before
  public void before() throws CoreException {

    // setup if necessary
    if (!_initializedBundleMakerProjects.containsKey(getTestProjectName())) {
      IBundleMakerProject bundleMakerProject = TestProjectCreator.getBundleMakerProject(getTestProjectName());
      _initializedBundleMakerProjects.put(getTestProjectName(), bundleMakerProject);
      TestProjectCreator.addProjectDescription(bundleMakerProject, getTestProjectName());
      TestProjectCreator.initializeParseAndOPen(bundleMakerProject);
    }

    //
    _bundleMakerProject = TestProjectCreator.getBundleMakerProject(getTestProjectName());

    //
    _modularizedSystem = (IModifiableModularizedSystem) _bundleMakerProject
        .getModularizedSystemWorkingCopy(getTestProjectName());

    // assert the test module
    Assert.assertNotNull(_modularizedSystem.getModule(getTestProjectName(), getTestProjectVersion()));

    // should not be necessary, but just in case...
    _modularizedSystem.undoTransformations(null);

    //
    _initialState_binaryHierarchical = AnalysisModelQueries.artifactToString(_modularizedSystem
        .getAnalysisModel(AnalysisModelConfiguration.HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION));

    _initialState_sourceHierarchical = AnalysisModelQueries.artifactToString(_modularizedSystem
        .getAnalysisModel(AnalysisModelConfiguration.HIERARCHICAL_SOURCE_RESOURCES_CONFIGURATION));
  }

  /**
   * {@inheritDoc}
   */
  @After
  public void after() throws CoreException {

    // undo all changed
    _modularizedSystem.undoTransformations(null);

    //
    Assert.assertEquals(_initialState_binaryHierarchical, AnalysisModelQueries.artifactToString(_modularizedSystem
        .getAnalysisModel(AnalysisModelConfiguration.HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION)));

    Assert.assertEquals(_initialState_sourceHierarchical, AnalysisModelQueries.artifactToString(_modularizedSystem
        .getAnalysisModel(AnalysisModelConfiguration.HIERARCHICAL_SOURCE_RESOURCES_CONFIGURATION)));

    //
    _bundleMakerProject = null;
    _modularizedSystem = null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IBundleMakerProject getBundleMakerProject() {
    return _bundleMakerProject;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IModifiableModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected String getTestProjectName() {
    return this.getClass().getSimpleName();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected String getTestProjectVersion() {
    return DEFAULT_TEST_PROJECT_VERSION;
  }
}
