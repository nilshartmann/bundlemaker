package org.bundlemaker.core.mvn.exporter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.bundlemaker.core.exporter.AbstractExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * h
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnProjectModuleExporter extends AbstractExporter {

  /** SRC_JAVA_DIRECTORY_NAME */
  private static final String SRC_JAVA_DIRECTORY_NAME       = "src/main/java";

  /** SRC_RESOUCRCES_DIRECTORY_NAME */
  private static final String SRC_RESOUCRCES_DIRECTORY_NAME = "src/main/resources";

  /**
   * <p>
   * Creates a new instance of type {@link MvnProjectModuleExporter}.
   * </p>
   */
  public MvnProjectModuleExporter() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canExport(IModularizedSystem modularizedSystem, IResourceModule module, IModuleExporterContext context) {
    return !module.getResources(ContentType.SOURCE).isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doExport(IProgressMonitor progressMonitor) throws CoreException {

    // String projectName = Helper.getUniqueProjectName(getCurrentModule().getModuleIdentifier().getName());
    //
    // // step 2: delete and create project
    // IPath location = null;
    //
    // if (isUseClassifcationForExportDestination()) {
    //
    // Path destinationDirectoryPath = new Path(getCurrentContext().getDestinationDirectory().getAbsolutePath());
    //
    // location = destinationDirectoryPath.append(getCurrentModule().getClassification()).append(projectName);
    // }
    //
    // // (re-)create the project
    // IProject project = Helper.deleteAndCreateProject(projectName, location);
    //
    // // step 3: add java and plug-nature
    // IProjectDescription description = project.getDescription();
    // description.setNatureIds(new String[] { JavaCore.NATURE_ID, IBundleProjectDescription.PLUGIN_NATURE });
    // project.setDescription(description, null);
    //
    // // 'clean' the java project
    // IJavaProject javaProject = JavaCore.create(project);
    // javaProject.setRawClasspath(new IClasspathEntry[] { JavaRuntime.getDefaultJREContainerEntry() }, null);
    // javaProject.save(null, true);
    //
    // // step 4: create and set the bundle project description
    // IBundleProjectService bundleProjectService = Activator.getBundleProjectService();
    //
    // IBundleProjectDescription bundleProjectDescription = bundleProjectService.getDescription(project);
    //
    // //
    // for (String header : getManifestContents().getMainAttributes().keySet()) {
    // bundleProjectDescription.setHeader(header, getManifestContents().getMainAttributes().get(header));
    // }
    //
    // // set source dir
    // IBundleClasspathEntry bundleClasspathEntry = bundleProjectService.newBundleClasspathEntry(new Path(
    // SRC_JAVA_DIRECTORY_NAME), new Path(BIN_DIRECTORY_NAME), null);
    //
    // //
    // bundleProjectDescription.setBundleClassath(new IBundleClasspathEntry[] { bundleClasspathEntry });
    // //
    // bundleProjectDescription.apply(null);

    // // step 5: copy the source files
    // IFolder srcFolder = project.getFolder(SRC_JAVA_DIRECTORY_NAME);

    //
    File projectDirectory = new File(getCurrentContext().getDestinationDirectory(), getCurrentModule()
        .getModuleIdentifier().getName());
    File sourceJavaDirectory = new File(projectDirectory, SRC_JAVA_DIRECTORY_NAME);
    File resourceJavaDirectory = new File(projectDirectory, SRC_RESOUCRCES_DIRECTORY_NAME);

    // copy the source
    for (IResource resource : getCurrentModule().getResources(ContentType.SOURCE)) {

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
}
