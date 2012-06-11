package org.bundlemaker.core.mvn.exporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.bundlemaker.core.exporter.AbstractDirectoryBasedTemplateProvider;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.mvn.MvnArtifactConverter;
import org.bundlemaker.core.mvn.content.xml.MvnArtifactType;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.core.runtime.Assert;

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

  // WRONG IMPLEMENTATION
  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public File getModuleTemplateDirectory(IResourceModule resourceModule) {
  //
  // //
  // Assert.isNotNull(resourceModule);
  //
  // //
  // MvnArtifactType artifactType = MvnArtifactConverter.fromResourceModule(resourceModule);
  //
  // //
  // File groupDirectory = new File(getTemplateRootDirectory(), artifactType.getGroupId());
  // File artifactDirectory = new File(groupDirectory, artifactType.getArtifactId());
  // File versionDirectory = new File(artifactDirectory, artifactType.getVersion());
  //
  // // e.g. 'de.test/my.module/1.2.3/pom-template.xml'
  // File template = new File(versionDirectory, getGenericTemplateFileName());
  // if (template.exists()) {
  // return versionDirectory;
  // }
  //
  // // e.g. 'de.test/my.module/pom-template.xml'
  // template = new File(artifactDirectory, getGenericTemplateFileName());
  // if (template.exists()) {
  // return artifactDirectory;
  // }
  //
  // // e.g. 'de.test/my.module_1.2.3_pom-template.xml'
  // template = new File(groupDirectory, artifactType.getArtifactId() + "_" + artifactType.getVersion() + "_"
  // + getTemplatePostfix());
  // if (template.exists()) {
  // return template;
  // }
  //
  // // e.g. 'de.test/my.module_pom-template.xml'
  // template = new File(groupDirectory, artifactType.getArtifactId() + "_"
  // + getTemplatePostfix());
  // if (template.exists()) {
  // return template;
  // }
  //
  // // e.g. 'de.test/pom-template.xml'
  // template = new File(groupDirectory, getGenericTemplateFileName());
  // if (template.exists()) {
  // return template;
  // }
  //
  // // e.g. 'pom-template.xml'
  // template = new File(getTemplateRootDirectory(), getGenericTemplateFileName());
  // if (template.exists()) {
  // return template;
  // }
  //
  // //
  // return null;
  // }

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
