package org.bundlemaker.core.mvn.exporter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.apache.maven.model.merge.ModelMerger;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.exporter.AbstractExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.ITemplateProvider;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.mvn.content.MvnArtifactType;
import org.bundlemaker.core.mvn.internal.MvnArtifactConverter;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnProjectModuleExporter extends AbstractExporter {

  /** SRC_JAVA_DIRECTORY_NAME */
  private static final String      SRC_JAVA_DIRECTORY_NAME       = "src/main/java";

  /** SRC_RESOUCRCES_DIRECTORY_NAME */
  private static final String      SRC_RESOUCRCES_DIRECTORY_NAME = "src/main/resources";

  /** the template provider */
  private ITemplateProvider<Model> _templateProvider;

  /**
   * <p>
   * Creates a new instance of type {@link MvnProjectModuleExporter}.
   * </p>
   */
  public MvnProjectModuleExporter(ITemplateProvider<Model> templateProvider) {

    //
    Assert.isNotNull(templateProvider);

    // set the template provider
    _templateProvider = templateProvider;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canExport(IModularizedSystem modularizedSystem, IModule module, IModuleExporterContext context) {
    return !module.getResources(ProjectContentType.SOURCE).isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doExport(IProgressMonitor progressMonitor) throws CoreException {

    //
    MvnArtifactType mvnArtifactType = MvnArtifactConverter.fromModule(getCurrentModule());

    //
    File groupDirectory = new File(getCurrentContext().getDestinationDirectory(), mvnArtifactType.getGroupId());
    File artifactDirectory = new File(groupDirectory, mvnArtifactType.getArtifactId());
    File versionDirectory = new File(artifactDirectory, mvnArtifactType.getVersion());

    System.out.println(String.format("Export '%s' to '%s'", getCurrentModule().getModuleIdentifier().toString(),
        versionDirectory.getAbsolutePath()));

    if (true) {
      try {
        createPOM(versionDirectory);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        throw new CoreException(new Status(IStatus.ERROR, "asd", "asd"));
      }
    }

    File sourceJavaDirectory = new File(versionDirectory, SRC_JAVA_DIRECTORY_NAME);
    File resourceJavaDirectory = new File(versionDirectory, SRC_RESOUCRCES_DIRECTORY_NAME);

    // copy the source
    for (IResource resource : getCurrentModule().getResources(ProjectContentType.SOURCE)) {

      if (!resource.getPath().startsWith("META-INF")) {

        //
        File targetFile = resource.getName().endsWith(".java") ? new File(sourceJavaDirectory, resource.getPath())
            : new File(resourceJavaDirectory, resource.getPath());

        //
        targetFile.getParentFile().mkdirs();

        try {

          //
          FileUtils.copy(new ByteArrayInputStream(resource.getContent()), new FileOutputStream(targetFile),
              new byte[1024]);

        } catch (Exception e) {
          // TODO
          e.printStackTrace();
          throw new CoreException(new Status(IStatus.ERROR, "asd", "asd"));
        }
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @throws IOException
   * @throws FileNotFoundException
   */
  protected void createPOM(File projectDirectory) throws FileNotFoundException, IOException {

    // get the artifact model to resolve the dependencies
    IRootArtifact artifactModel = getCurrentModularizedSystem().getAnalysisModel(
        IAnalysisModelConfiguration.SOURCE_RESOURCES_CONFIGURATION);

    // get the current module artifact
    IModuleArtifact currentModuleArtifact = artifactModel.getModuleArtifact(getCurrentModule());

    // create a new maven model
    Model model = new Model();

    // get the maven model template (if exists)
    Model template = _templateProvider != null ? _templateProvider.getTemplate(getCurrentModule(),
        getCurrentModularizedSystem(),
        getCurrentContext()) : null;

    // merge the template
    if (template != null) {
      new ModelMerger().merge(model, template, true, null);
    }

    // set the maven pom coordinates
    MvnArtifactType mvnArtifactType = MvnArtifactConverter.fromModule(getCurrentModule());
    model.setGroupId(mvnArtifactType.getGroupId());
    model.setArtifactId(mvnArtifactType.getArtifactId());
    model.setVersion(mvnArtifactType.getVersion());

    // get all modules
    final List<IModuleArtifact> allModules = new LinkedList<IModuleArtifact>();
    artifactModel.accept(
        new IAnalysisModelVisitor.Adapter() {
          @Override
          public boolean visit(IModuleArtifact moduleArtifact) {
            if (!getCurrentModularizedSystem().getExecutionEnvironment().equals(moduleArtifact.getAssociatedModule())) {
              allModules.add(moduleArtifact);
            }
            return false;
          }
        });

    // resolve the dependencies
    Collection<? extends IDependency> dependencies = currentModuleArtifact.getDependenciesTo(allModules);
    for (IDependency dependency : dependencies) {

      //
      IModuleArtifact toArtifact = (IModuleArtifact) dependency.getTo();

      // get the mvn artifact type
      MvnArtifactType toMvnArtifactType = MvnArtifactConverter.fromModule(toArtifact.getAssociatedModule());

      Dependency mvnDependency = new Dependency();
      mvnDependency.setGroupId(toMvnArtifactType.getGroupId());
      mvnDependency.setArtifactId(toMvnArtifactType.getArtifactId());
      mvnDependency.setVersion(toMvnArtifactType.getVersion());

      model.addDependency(mvnDependency);
    }

    //
    File resultFile = new File(projectDirectory, "pom.xml");
    resultFile.getParentFile().mkdirs();

    new MavenXpp3Writer().write(new FileOutputStream(resultFile), model);
  }
}
