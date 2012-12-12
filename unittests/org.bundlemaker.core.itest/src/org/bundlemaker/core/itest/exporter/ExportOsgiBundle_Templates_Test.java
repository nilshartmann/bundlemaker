package org.bundlemaker.core.itest.exporter;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.osgi.exporter.bundle.JarFileBundleExporter;
import org.bundlemaker.core.osgi.manifest.DefaultManifestPreferences;
import org.bundlemaker.core.osgi.manifest.IManifestConstants;
import org.bundlemaker.core.osgi.manifest.IManifestPreferences.DependencyStyle;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.virgo.bundlor.util.SimpleManifestContents;
import org.eclipse.virgo.util.osgi.manifest.BundleManifest;
import org.eclipse.virgo.util.osgi.manifest.ExportedPackage;
import org.eclipse.virgo.util.osgi.manifest.ImportedPackage;
import org.eclipse.virgo.util.osgi.manifest.RequiredBundle;
import org.eclipse.virgo.util.parser.manifest.ManifestContents;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ExportOsgiBundle_Templates_Test extends AbstractExportOsgiBundleTest {

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * @throws IOException
   */
  @Test
  public void testExportWithTemplate() throws CoreException, IOException {

    ManifestContents template = new SimpleManifestContents();
    template.getMainAttributes().put(IManifestConstants.HEADER_IMPORT_TEMPLATE, "javax.swing.*;version=\"1.2.3\"");
    template.getMainAttributes().put(IManifestConstants.HEADER_EXCLUDED_IMPORTS, "org.xml.sax.helpers.*");
    template.getMainAttributes().put(IManifestConstants.HEADER_IMPORT_PACKAGE, "com.wuetherich.additional");
    template.getMainAttributes().put(IManifestConstants.HEADER_REQUIRE_BUNDLE, "com.wuetherich.bundle");

    //
    new JarFileBundleExporter(new TestTemplateProvider(template), null, new DefaultManifestPreferences(false,
        DependencyStyle.PREFER_IMPORT_PACKAGE)).export(getModularizedSystem(), getJeditmodule(), getExporterContext(),
        null);

    //
    BundleManifest bundleManifest = readBundleManifest(getResultDir(), "jedit_1.0.0.jar");

    //
    List<ImportedPackage> importedPackages = bundleManifest.getImportPackage().getImportedPackages();
    sortImportedPackages(importedPackages);
    Assert.assertEquals(14, importedPackages.size());
    Assert
        .assertEquals(
            "[com.wuetherich.additional, javax.print.attribute, javax.print.attribute.standard, javax.swing;version=\"1.2.3\", javax.swing.border;version=\"1.2.3\", javax.swing.event;version=\"1.2.3\", javax.swing.filechooser;version=\"1.2.3\", javax.swing.plaf;version=\"1.2.3\", javax.swing.plaf.metal;version=\"1.2.3\", javax.swing.table;version=\"1.2.3\", javax.swing.text;version=\"1.2.3\", javax.swing.text.html;version=\"1.2.3\", javax.swing.tree;version=\"1.2.3\", org.xml.sax]",
            importedPackages.toString());

    //
    List<ExportedPackage> exportedPackages = bundleManifest.getExportPackage().getExportedPackages();
    sortExportedPackages(exportedPackages);
    Assert.assertEquals(14, importedPackages.size());

    //
    List<RequiredBundle> requiredBundles = bundleManifest.getRequireBundle().getRequiredBundles();
    Assert.assertEquals(1, requiredBundles.size());
    Assert.assertEquals("[com.wuetherich.bundle]", requiredBundles.toString());
  }
}
