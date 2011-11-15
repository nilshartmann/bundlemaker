package org.bundlemaker.core.itest.complex.analysis;

import java.io.File;

import junit.framework.Assert;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration.ResourcePresentation;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.reports.exporter.DuplicateTypesReportExporter;
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
    DuplicateTypesReportExporter exporter = new DuplicateTypesReportExporter();
    exporter.export(getModularizedSystem(), new DefaultModuleExporterContext(getBundleMakerProject(), new File(
        "d:/temp"), getModularizedSystem()), null);

    // StopWatch stopWatch = new StopWatch();
    // stopWatch.start();
    // DuplicateTypesVisitor visitor = new DuplicateTypesVisitor();
    // getRootArtifact().accept(visitor);
    // stopWatch.stop();
    // System.out.println(stopWatch.getElapsedTime());
    //
    // System.out.println(visitor.getDuplicateTypes());
    // // System.out.println(visitor.getSingleTypes());
    //
    // System.out.println(visitor.getDuplicatePackages());
    // // System.out.println(visitor.getSinglePackages());
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
}
