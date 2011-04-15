package org.bundlemaker.core;

import org.bundlemaker.core.parser.test.AbstractBundleMakerProjectTest;
import org.bundlemaker.core.util.ProgressMonitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.junit.Test;
import static org.junit.Assert.*;

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
  public void test() throws CoreException {

    //
    addProjectDescription();

    //
    assertEquals(BundleMakerProjectState.CREATED, getBundleMakerProject().getState());

    //
    getBundleMakerProject().initialize(new ProgressMonitor());
    assertEquals(BundleMakerProjectState.INITIALIZED, getBundleMakerProject().getState());

    //
    getBundleMakerProject().parse(new ProgressMonitor(), true);
    assertEquals(BundleMakerProjectState.PARSED, getBundleMakerProject().getState());
  }
}
