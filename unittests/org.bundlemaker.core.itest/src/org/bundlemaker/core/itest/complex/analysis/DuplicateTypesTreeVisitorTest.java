package org.bundlemaker.core.itest.complex.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import junit.framework.Assert;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration.ResourcePresentation;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.reports.exporter.DuplicateTypesReportExporter;
import org.bundlemaker.core.testutils.BundleMakerTestUtils;
import org.junit.Test;

/**
 * <p>
 * Example: group1/group2/jedit_1.0.0 velocity_1.5 jdk16_jdk16 << Missing Types >>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DuplicateTypesTreeVisitorTest extends AbstractJeditArtifactTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void testArtifactTreeVisitor() throws Exception {

    // prepare
    ITypeArtifact typeArtifact = (ITypeArtifact) getRootArtifact()
        .getChild(
            "group1|group2|jedit_1.0.0|org.gjt.sp.jedit.textarea|TextAreaPainter.class|org.gjt.sp.jedit.textarea.TextAreaPainter");
    Assert.assertNotNull(typeArtifact);

    IModuleArtifact moduleArtifact = getRootArtifact().getOrCreateModule("test", "1.2.3");
    moduleArtifact.addArtifact(typeArtifact);

    //
    File file = File.createTempFile(this.getClass().getName(), "txt");
    file.getParentFile().mkdirs();
    file.deleteOnExit();

    DuplicateTypesReportExporter exporter = new DuplicateTypesReportExporter();
    exporter.setResultFile(file);
    exporter.export(getModularizedSystem(), new DefaultModuleExporterContext(getBundleMakerProject(), file.getParentFile(),
        getModularizedSystem()), null);

    //
    String actualContent = readFileAsString(file);
    actualContent = actualContent.replace("\r\n", "\n");
    String expectedContent = BundleMakerTestUtils.convertStreamToString(this.getClass().getResourceAsStream(
        "results/Report_DuplicateTypesTreeVisitorTest.txt"));
    expectedContent = expectedContent.replace("\r\n", "\n");
    Assert.assertEquals(expectedContent, actualContent);
  }

  /**
   * {@inheritDoc}
   */
  public IArtifactModelConfiguration getArtifactModelConfiguration() {

    //
    IArtifactModelConfiguration configuration = new ArtifactModelConfiguration(false,
        ResourcePresentation.ALL_RESOURCES, ContentType.BINARY, false, false);

    //
    return configuration;
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
