package org.bundlemaker.core;

import static org.junit.Assert.assertEquals;

import org.bundlemaker.core.parser.test.AbstractBundleMakerProjectTest;
import org.bundlemaker.core.util.ProgressMonitor;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LifecycleTest extends AbstractBundleMakerProjectTest {

  /** - */
  private static final String[] EXPECTED_HASHCODES = new String[] { "1671a00db46fae3167c1efe6234fa742fcfb9798",
      "85b79c43c37429714e5601e76946c4bfa5340143", "8813bcbb4039329723ead794c9af3877e6d1bfa3",
      "4e0a3e2816874c166b9efc1164d68cb3ed8f679a" };

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
    assertEquals(BundleMakerProjectState.CREATED, getBundleMakerProject().getState());

    //
    getBundleMakerProject().initialize(new ProgressMonitor());
    assertEquals(BundleMakerProjectState.INITIALIZED, getBundleMakerProject().getState());

    // assert resource list
    assertEquals(2, getBundleMakerProject().getBinaryResources().size());
    assertEquals(2, getBundleMakerProject().getSourceResources().size());

    //
    Assert.assertEquals(EXPECTED_HASHCODES[0],
        MDHelper.getHexString(getBundleMakerProject().getBinaryResources().get(0).getHashvalue()));
    Assert.assertEquals(EXPECTED_HASHCODES[1],
        MDHelper.getHexString(getBundleMakerProject().getBinaryResources().get(1).getHashvalue()));

    //
    Assert.assertEquals(EXPECTED_HASHCODES[2],
        MDHelper.getHexString(getBundleMakerProject().getSourceResources().get(0).getHashvalue()));
    Assert.assertEquals(EXPECTED_HASHCODES[3],
        MDHelper.getHexString(getBundleMakerProject().getSourceResources().get(1).getHashvalue()));

    //
    getBundleMakerProject().parse(new ProgressMonitor(), true);
    assertEquals(BundleMakerProjectState.PARSED, getBundleMakerProject().getState());
  }
}
