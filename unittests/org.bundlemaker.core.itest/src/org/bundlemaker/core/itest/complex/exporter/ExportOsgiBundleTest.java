package org.bundlemaker.core.itest.complex.exporter;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.osgi.exporter.bundle.JarFileBundleExporter;
import org.bundlemaker.core.osgi.manifest.DefaultManifestCreator;
import org.bundlemaker.core.osgi.manifest.IBundleManifestCreator;
import org.bundlemaker.core.osgi.manifest.IManifestPreferences.DependencyStyle;
import org.bundlemaker.core.osgi.manifest.DefaultManifestPreferences;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

import com.springsource.util.osgi.manifest.BundleManifest;
import com.springsource.util.osgi.manifest.ImportedPackage;
import com.springsource.util.osgi.manifest.RequiredBundle;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ExportOsgiBundleTest extends AbstractExportOsgiBundleTest {

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * @throws IOException
   */
  @Test
  public void testExportWithImportPackage() throws CoreException, IOException {

    //
    JarFileBundleExporter binaryBundleExporter = new JarFileBundleExporter(null, null, new DefaultManifestPreferences(false,
        DependencyStyle.PREFER_IMPORT_PACKAGE));

    //
    binaryBundleExporter.export(getModularizedSystem(), getJeditmodule(), getExporterContext(), null);

    //
    BundleManifest bundleManifest = readBundleManifest(getResultDir(), "jedit_1.0.0.jar");

    //
    List<ImportedPackage> importedPackages = bundleManifest.getImportPackage().getImportedPackages();
    sortImportedPackages(importedPackages);
    Assert.assertEquals(14, importedPackages.size());

    //
    Assert
        .assertEquals(
            "[javax.print.attribute, javax.print.attribute.standard, javax.swing, javax.swing.border, javax.swing.event, javax.swing.filechooser, javax.swing.plaf, javax.swing.plaf.metal, javax.swing.table, javax.swing.text, javax.swing.text.html, javax.swing.tree, org.xml.sax, org.xml.sax.helpers]",
            importedPackages.toString());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * @throws IOException
   */
  @Test
  public void testExportWithRequiredBundle() throws CoreException, IOException {

    //
    JarFileBundleExporter binaryBundleExporter = new JarFileBundleExporter(null, null, new DefaultManifestPreferences(false,
        DependencyStyle.STRICT_REQUIRE_BUNDLE));

    //
    binaryBundleExporter.export(getModularizedSystem(), getJeditmodule(), getExporterContext(), null);

    //
    BundleManifest bundleManifest = readBundleManifest(getResultDir(), "jedit_1.0.0.jar");

    //
    Assert.assertEquals(0, bundleManifest.getImportPackage().getImportedPackages().size());

    //
    List<RequiredBundle> requiredBundles = bundleManifest.getRequireBundle().getRequiredBundles();
    Assert.assertEquals(1, requiredBundles.size());

    //
    Assert.assertEquals("[system.bundle]", requiredBundles.toString());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * @throws IOException
   */
  @Test
  public void testCustomizedExport() throws CoreException, IOException {

    // the manifest creator
    IBundleManifestCreator manifestCreator = new DefaultManifestCreator() {

      @Override
      protected boolean useImportPackage(String packageName, List<IModuleArtifact> exportingModules) {

        //
        if (exportingModules.size() == 1) {
          return getModularizedSystem().getExecutionEnvironment().equals(exportingModules.get(0).getAssociatedModule())
              && packageName.startsWith("javax.");
        }

        //
        return false;
      }
    };

    //
    JarFileBundleExporter binaryBundleExporter = new JarFileBundleExporter(null, manifestCreator,
        new DefaultManifestPreferences(false, DependencyStyle.STRICT_REQUIRE_BUNDLE));

    //
    binaryBundleExporter.export(getModularizedSystem(), getJeditmodule(), getExporterContext(), null);

    //
    BundleManifest bundleManifest = readBundleManifest(getResultDir(), "jedit_1.0.0.jar");

    //
    //
    List<ImportedPackage> importedPackages = bundleManifest.getImportPackage().getImportedPackages();
    sortImportedPackages(importedPackages);
    Assert.assertEquals(12, importedPackages.size());
    Assert
        .assertEquals(
            "[javax.print.attribute, javax.print.attribute.standard, javax.swing, javax.swing.border, javax.swing.event, javax.swing.filechooser, javax.swing.plaf, javax.swing.plaf.metal, javax.swing.table, javax.swing.text, javax.swing.text.html, javax.swing.tree]",
            importedPackages.toString());

    //
    List<RequiredBundle> requiredBundles = bundleManifest.getRequireBundle().getRequiredBundles();
    Assert.assertEquals(1, requiredBundles.size());
    Assert.assertEquals("[system.bundle]", requiredBundles.toString());
  }
}
