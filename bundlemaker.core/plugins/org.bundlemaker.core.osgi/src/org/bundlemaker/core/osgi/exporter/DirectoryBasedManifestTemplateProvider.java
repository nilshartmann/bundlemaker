package org.bundlemaker.core.osgi.exporter;

import java.io.File;

import org.bundlemaker.core.exporter.AbstractDirectoryBasedTemplateProvider;
import org.bundlemaker.core.osgi.utils.ManifestUtils;
import org.eclipse.virgo.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DirectoryBasedManifestTemplateProvider extends AbstractDirectoryBasedTemplateProvider<ManifestContents> {

  /**
   * <p>
   * Creates a new instance of type {@link DirectoryBasedManifestTemplateProvider}.
   * </p>
   * 
   * @param templateRootDirectory
   */
  public DirectoryBasedManifestTemplateProvider(File templateRootDirectory) {
    super(templateRootDirectory);
  }

  /**
   * <p>
   * </p>
   * 
   * @param templateFile
   * @return
   */
  protected ManifestContents readTemplateFile(File templateFile) {
    return ManifestUtils.readManifestContents(templateFile);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected String getGenericTemplateFileName() {
    return "manifest.template";
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected String getTemplatePostfix() {
    return ".template";
  }
}
