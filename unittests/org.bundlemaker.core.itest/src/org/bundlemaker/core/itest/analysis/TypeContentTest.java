package org.bundlemaker.core.itest.analysis;

import java.io.File;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.osgi.exporter.bundle.JarFileBundleExporter;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.junit.Assert;
import org.junit.Test;

public class TypeContentTest extends AbstractModularizedSystemTest {

  @Test
  public void qualifiedNameWithFlatPackages() throws Exception {

    // get the root artifact
    IBundleMakerArtifact rootArtifact = getModularizedSystem().getAnalysisModel(
        AnalysisModelConfiguration.HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION);

    Assert.assertNotNull(rootArtifact);

    IPackageArtifact packageArtifact = (IPackageArtifact) rootArtifact
        .getChild("group1|group2|TypeContentTest_1.0.0|de|test");

    //
    for (IBundleMakerArtifact advancedArtifact : packageArtifact.getChildren()) {
      System.out.println(advancedArtifact.getDependenciesTo());
    }

    // Dump all module dependencies
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(getBundleMakerProject(), new File(
        "d:/temp"), getModularizedSystem());

    JarFileBundleExporter jarFileBundleExporter = new JarFileBundleExporter(null, null, null);
    ModularizedSystemExporterAdapter exporterAdapter = new ModularizedSystemExporterAdapter(jarFileBundleExporter);
    exporterAdapter.export(getModularizedSystem(), exporterContext, null);
  }

  protected AnalyzeMode getLibraryAnalyzeMode() {
    return AnalyzeMode.DO_NOT_ANALYZE;
  }
}
