package org.bundlemaker.core.itest.core;

import static org.junit.Assert.assertEquals;

import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.itest.analysis.ModuleConverterTest;
import org.bundlemaker.core.itestframework.AbstractBundleMakerProjectTest;
import org.bundlemaker.core.util.ProgressMonitor;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LifecycleTest extends AbstractBundleMakerProjectTest {

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void test() throws Exception {

    //
    addProjectDescription();

    // assert equals
    assertEquals(BundleMakerProjectState.CREATED, getBundleMakerProject().getState());

    // initialize
    initialize();

    // parse
    parse();
  }

  /**
   * <p>
   * parse()
   * </p>
   * 
   * @throws CoreException
   */
  private void parse() throws CoreException {
    // parse the project
    getBundleMakerProject().parseAndOpen(new ProgressMonitor());
    assertEquals(BundleMakerProjectState.READY, getBundleMakerProject().getState());
  }

  /**
   * <p>
   * initialize()
   * </p>
   * 
   * @throws CoreException
   */
  private void initialize() throws CoreException {
    //
    getBundleMakerProject().initialize(new ProgressMonitor());
    assertEquals(BundleMakerProjectState.INITIALIZED, getBundleMakerProject().getState());

    // assert resource list
    assertEquals(2, getBundleMakerProject().getBinaryResources().size());
    assertEquals(2, getBundleMakerProject().getSourceResources().size());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return ModuleConverterTest.class.getSimpleName();
  }
}
