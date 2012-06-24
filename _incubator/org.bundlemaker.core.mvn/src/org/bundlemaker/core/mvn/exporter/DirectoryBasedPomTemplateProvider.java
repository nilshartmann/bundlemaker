package org.bundlemaker.core.mvn.exporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.bundlemaker.core.exporter.AbstractDirectoryBasedTemplateProvider;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DirectoryBasedPomTemplateProvider extends AbstractDirectoryBasedTemplateProvider<Model> {

  /**
   * <p>
   * Creates a new instance of type {@link DirectoryBasedPomTemplateProvider}.
   * </p>
   * 
   * @param templateRootDirectory
   */
  public DirectoryBasedPomTemplateProvider(File templateRootDirectory) {
    super(templateRootDirectory);
  }

  /**
   * {@inheritDoc}
   */
  protected Model readTemplateFile(File templateFile) throws FileNotFoundException, IOException, XmlPullParserException {
    return new MavenXpp3Reader().read(new FileInputStream(templateFile));
  }

  /**
   * {@inheritDoc}
   */
  protected String getGenericTemplateFileName() {
    return "pom-template.xml";
  }

  /**
   * {@inheritDoc}
   */
  protected String getTemplatePostfix() {
    return "-pom-template.xml";
  }
}
