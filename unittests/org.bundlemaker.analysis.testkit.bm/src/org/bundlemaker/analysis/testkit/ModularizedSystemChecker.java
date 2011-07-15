package org.bundlemaker.analysis.testkit;

import java.io.File;

import org.bundlemaker.core.itestframework.util.ModularizedSystemTestUtils;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.testutils.BundleMakerTestUtils;
import org.bundlemaker.core.testutils.FileDiffUtil;
import org.junit.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModularizedSystemChecker {

  /** MODULARIZED_SYSTEM_PREFIX */
  private static final String MODULARIZED_SYSTEM_PREFIX = "ModularizedSystem";

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   */
  public static void check(IModularizedSystem modularizedSystem, String timestamp) {
    //
    Assert.assertEquals(3, modularizedSystem.getAllModules().size());

    //
    ModuleIdentifier jeditModuleIdentifier = new ModuleIdentifier("jedit", "1.0.0");
    ModuleIdentifier velocityModuleIdentifier = new ModuleIdentifier("velocity", "1.5");

    IResourceModule jeditModule = modularizedSystem.getResourceModule(jeditModuleIdentifier);
    Assert.assertNotNull(jeditModule);

    IResourceModule velocityModule = modularizedSystem.getResourceModule(velocityModuleIdentifier);
    Assert.assertNotNull(velocityModule);

    //
    // Compare against file
    //

    // get the content of the jedit module
    StringBuilder builder = new StringBuilder();
    builder.append(ModularizedSystemTestUtils.dump(jeditModule, modularizedSystem));
    builder.append("\n");
    builder.append(ModularizedSystemTestUtils.dump(velocityModule, modularizedSystem));

    // Compare and dump to result file
    File actual = BundleMakerTestUtils.writeToFile(builder.toString(), "result" + File.separatorChar
        + "ModularizedSystem_" + timestamp + ".txt");
    File expected = new File(System.getProperty("user.dir"), "test-data" + File.separatorChar
        + MODULARIZED_SYSTEM_PREFIX + "_Expected.txt");
    File htmlReport = new File(actual.getAbsolutePath().substring(0,
        actual.getAbsolutePath().length() - ".txt".length())
        + ".html");

    FileDiffUtil.assertArtifactModel(expected, actual, htmlReport);

    // everything was successful - delete the actual file
    actual.delete();
  }
}
