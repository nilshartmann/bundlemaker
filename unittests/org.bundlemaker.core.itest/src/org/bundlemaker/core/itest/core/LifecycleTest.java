package org.bundlemaker.core.itest.core;

import static org.junit.Assert.assertEquals;
import junit.framework.Assert;

import org.bundlemaker.core.BundleMakerProjectChangedEvent;
import org.bundlemaker.core.BundleMakerProjectChangedEvent.Type;
import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.IBundleMakerProjectChangedListener;
import org.bundlemaker.core.itest.analysis.SimpleArtifact_BINARY_RESOURCES_CONFIGURATION_Test;
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

  /** - */
  private BundleMakerProjectChangedEvent _changedEvent;

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

    //
    getBundleMakerProject().addBundleMakerProjectChangedListener(new IBundleMakerProjectChangedListener() {
      @Override
      public void bundleMakerProjectChanged(BundleMakerProjectChangedEvent event) {
        _changedEvent = event;
      }
    });

    // assert equals
    assertEquals(BundleMakerProjectState.CREATED, getBundleMakerProject().getState());

    // reset event
    _changedEvent = null;

    // initialize
    initialize();

    //
    Assert.assertNotNull(_changedEvent);
    Assert.assertEquals(Type.PROJECT_STATE_CHANGED, _changedEvent.getType());

    // reset event
    _changedEvent = null;

    // parse
    parse();

    //
    Assert.assertNotNull(_changedEvent);
    Assert.assertEquals(Type.PROJECT_STATE_CHANGED, _changedEvent.getType());

    // reset event
    _changedEvent = null;

    //
    getBundleMakerProject().getModifiableProjectDescription().save();

    //
    Assert.assertNotNull(_changedEvent);
    Assert.assertEquals(Type.PROJECT_DESCRIPTION_CHANGED, _changedEvent.getType());
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
    return "SimpleArtifactModelTest";
  }
}
