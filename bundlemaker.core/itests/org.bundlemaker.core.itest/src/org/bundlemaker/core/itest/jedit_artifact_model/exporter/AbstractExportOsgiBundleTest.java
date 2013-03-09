package org.bundlemaker.core.itest.jedit_artifact_model.exporter;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import junit.framework.Assert;

import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.ITemplateProvider;
import org.bundlemaker.core.itest.jedit_artifact_model.core.AbstractJeditTest;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IReadableResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.virgo.bundlor.util.BundleManifestUtils;
import org.eclipse.virgo.util.osgi.manifest.BundleManifest;
import org.eclipse.virgo.util.osgi.manifest.ExportedPackage;
import org.eclipse.virgo.util.osgi.manifest.ImportedPackage;
import org.eclipse.virgo.util.parser.manifest.ManifestContents;
import org.eclipse.virgo.util.parser.manifest.RecoveringManifestParser;
import org.junit.After;
import org.junit.Before;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractExportOsgiBundleTest extends AbstractJeditTest {

  /** - */
  private File                   _resultDir;

  /** - */
  private IResourceModule        _jeditmodule;

  /** - */
  private IModuleExporterContext _exporterContext;

  /**
   * {@inheritDoc}
   */
  @Before
  public void before() throws CoreException {

    super.before();

    //
    _resultDir = new File(System.getProperty("java.io.tmpdir"), "bm-test");
    _resultDir.mkdirs();

    //
    _jeditmodule = getModularizedSystem().getResourceModule("jedit", "1.0.0");
    Assert.assertEquals(998, _jeditmodule.getContainedTypes().size());

    //
    _exporterContext = new DefaultModuleExporterContext(getBundleMakerProject(), _resultDir, getModularizedSystem());
  }

  /**
   * {@inheritDoc}
   */
  @After
  public void after() throws CoreException {

    super.after();

    _resultDir = null;
    _jeditmodule = null;
    _exporterContext = null;
  }

  public File getResultDir() {
    return _resultDir;
  }

  public IResourceModule getJeditmodule() {
    return _jeditmodule;
  }

  public IModuleExporterContext getExporterContext() {
    return _exporterContext;
  }

  /**
   * <p>
   * </p>
   * 
   * @param importedPackages
   */
  public static final void sortImportedPackages(List<ImportedPackage> importedPackages) {

    //
    Collections.sort(importedPackages, new Comparator<ImportedPackage>() {
      @Override
      public int compare(ImportedPackage o1, ImportedPackage o2) {
        return o1.getPackageName().compareTo(o2.getPackageName());
      }
    });
  }

  /**
   * <p>
   * </p>
   *
   * @param exportedPackages
   */
  public static final void sortExportedPackages(List<ExportedPackage> exportedPackages) {

    //
    Collections.sort(exportedPackages, new Comparator<ExportedPackage>() {
      @Override
      public int compare(ExportedPackage o1, ExportedPackage o2) {
        return o1.getPackageName().compareTo(o2.getPackageName());
      }
    });
  }

  /**
   * <p>
   * </p>
   * 
   * @param resultDir
   * @param fileName
   * @return
   * @throws IOException
   */
  public static final BundleManifest readBundleManifest(File resultDir, String fileName) throws IOException {

    //
    File resultFile = new File(resultDir, fileName);

    Assert.assertTrue(resultFile.isFile());

    JarFile jarFile = new JarFile(resultFile);
    ZipEntry zipEntry = jarFile.getEntry("META-INF/MANIFEST.MF");
    ManifestContents result = new RecoveringManifestParser().parse(new InputStreamReader(jarFile
        .getInputStream(zipEntry)));

    //
    BundleManifest bundleManifest = BundleManifestUtils.createBundleManifest(result);

    //
    return bundleManifest;
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public static class TestTemplateProvider implements ITemplateProvider {

    /** - */
    private ManifestContents _manifestContents;

    /**
     * <p>
     * Creates a new instance of type {@link TestTemplateProvider}.
     * </p>
     * 
     * @param manifestContents
     */
    public TestTemplateProvider(ManifestContents manifestContents) {
      Assert.assertNotNull(manifestContents);

      _manifestContents = manifestContents;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ManifestContents getTemplate(IResourceModule module, IModularizedSystem modularizedSystem,
        IModuleExporterContext context) {

      //
      return _manifestContents;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<IReadableResource> getAdditionalResources(IResourceModule currentModule,
        IModularizedSystem currentModularizedSystem, IModuleExporterContext currentContext) {

      //
      return Collections.emptySet();
    }
  }
}
