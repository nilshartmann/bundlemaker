package org.bundlemaker.core.itest.reports;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import junit.framework.Assert;

import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceContainer;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.reports.exporter.DuplicateTypesReportExporter;
import org.bundlemaker.core.testutils.BundleMakerTestUtils;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DuplicateTypesTreeVisitorTest extends AbstractModularizedSystemTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void testArtifactTreeVisitor_1() throws Exception {

    //
    File file = File.createTempFile(this.getClass().getName(), "txt");
    file.getParentFile().mkdirs();
    file.deleteOnExit();

    DuplicateTypesReportExporter exporter = new DuplicateTypesReportExporter();
    exporter.setResultFile(file);
    exporter.export(getModularizedSystem(),
        new DefaultModuleExporterContext(getBundleMakerProject(), file.getParentFile(), getModularizedSystem()), null);

    //
    String actualContent = readFileAsString(file);
    actualContent = actualContent.replace("\r\n", "\n");
    String expectedContent = BundleMakerTestUtils.convertStreamToString(this.getClass().getResourceAsStream(
        "results/DuplicateTypesTreeVisitorTest_testArtifactTreeVisitor_1.txt"));
    expectedContent = expectedContent.replace("\r\n", "\n");
    Assert.assertEquals(expectedContent, actualContent);
  }

  @Test
  public void testArtifactTreeVisitor_2() throws Exception {

    //
    IModifiableResourceModule resourceModule = getModularizedSystem().getModifiableResourceModule(
        new ModuleIdentifier(DuplicateTypesTreeVisitorTest.class.getSimpleName(), "1.0.0"));

    //
    IModifiableResourceContainer container = resourceModule.getModifiableSelfResourceContainer();
    int i = 0;
    for (IMovableUnit movableUnit : container.getMovableUnits()) {

      if (i == 1) {
        container.removeMovableUnit(movableUnit);
        i = 0;
      } else {
        i++;
      }
    }

    //
    File file = File.createTempFile(this.getClass().getName(), "txt");
    file.getParentFile().mkdirs();
    file.deleteOnExit();

    DuplicateTypesReportExporter exporter = new DuplicateTypesReportExporter();
    exporter.setResultFile(file);
    exporter.export(getModularizedSystem(),
        new DefaultModuleExporterContext(getBundleMakerProject(), file.getParentFile(), getModularizedSystem()), null);

    //
    String actualContent = readFileAsString(file);
    actualContent = actualContent.replace("\r\n", "\n");
    String expectedContent = BundleMakerTestUtils.convertStreamToString(this.getClass().getResourceAsStream(
        "results/DuplicateTypesTreeVisitorTest_testArtifactTreeVisitor_2.txt"));
    expectedContent = expectedContent.replace("\r\n", "\n");
    Assert.assertEquals(expectedContent, actualContent);
  }

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @return
   * @throws java.io.IOException
   */
  private static String readFileAsString(File file) throws java.io.IOException {
    StringBuffer fileData = new StringBuffer(1000);
    BufferedReader reader = new BufferedReader(new FileReader(file));
    char[] buf = new char[1024];
    int numRead = 0;
    while ((numRead = reader.read(buf)) != -1) {
      String readData = String.valueOf(buf, 0, numRead);
      fileData.append(readData);
      buf = new char[1024];
    }
    reader.close();
    return fileData.toString();
  }
}
