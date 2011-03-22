/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.osgi.pde.exporter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.util.Helper;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.osgi.Activator;
import org.bundlemaker.core.osgi.exporter.AbstractManifestAwareExporter;
import org.bundlemaker.core.osgi.internal.manifest.DroolsBasedBundleManifestCreator;
import org.bundlemaker.core.osgi.internal.manifest.ExportPackagePreferences;
import org.bundlemaker.core.osgi.internal.manifest.PackageWiringPreferences;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.pde.core.project.IBundleClasspathEntry;
import org.eclipse.pde.core.project.IBundleProjectDescription;
import org.eclipse.pde.core.project.IBundleProjectService;

import com.springsource.bundlor.util.BundleManifestUtils;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 * h
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class PdePluginProjectModuleExporter extends AbstractManifestAwareExporter {

  /** - */
  private static final String              SRC_DIRECTORY_NAME = "src";

  /** - */
  private static final String              BIN_DIRECTORY_NAME = "bin";

  /** - */
  private boolean                          _useClassifcationForExportDestination;

  /** - */
  private DroolsBasedBundleManifestCreator _manifestCreator;

  /**
   * <p>
   * Creates a new instance of type {@link PdePluginProjectModuleExporter}.
   * </p>
   * 
   */
  public PdePluginProjectModuleExporter() {
    _manifestCreator = new DroolsBasedBundleManifestCreator();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean isUseClassifcationForExportDestination() {
    return _useClassifcationForExportDestination;
  }

  /**
   * <p>
   * </p>
   * 
   * @param useClassifcationForExportDestination
   */
  public void setUseClassifcationForExportDestination(boolean useClassifcationForExportDestination) {
    _useClassifcationForExportDestination = useClassifcationForExportDestination;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canExport(IModularizedSystem modularizedSystem, IResourceModule module, IModuleExporterContext context) {

    //
    return !module.getResources(ContentType.SOURCE).isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doExport() throws CoreException {

    // step 1: get a non-existing project name
    String projectName = Helper.getUniqueProjectName(getCurrentModule().getModuleIdentifier().getName());

    // step 2: delete and create project
    IPath location = null;

    if (isUseClassifcationForExportDestination()) {

      Path destinationDirectoryPath = new Path(getCurrentContext().getDestinationDirectory().getAbsolutePath());

      location = destinationDirectoryPath.append(getCurrentModule().getClassification()).append(projectName);
    }

    // (re-)create the project
    IProject project = Helper.deleteAndCreateProject(projectName, location);

    // step 3: add java and plug-nature
    IProjectDescription description = project.getDescription();
    description.setNatureIds(new String[] { JavaCore.NATURE_ID, IBundleProjectDescription.PLUGIN_NATURE });
    project.setDescription(description, null);

    // 'clean' the java project
    IJavaProject javaProject = JavaCore.create(project);
    javaProject.setRawClasspath(new IClasspathEntry[] { JavaRuntime.getDefaultJREContainerEntry() }, null);
    javaProject.save(null, true);

    // step 4: create and set the bundle project description
    IBundleProjectService bundleProjectService = Activator.getBundleProjectService();

    IBundleProjectDescription bundleProjectDescription = bundleProjectService.getDescription(project);

    //
    for (String header : getCurrentManifest().getMainAttributes().keySet()) {
      bundleProjectDescription.setHeader(header, getCurrentManifest().getMainAttributes().get(header));
    }

    // set source dir
    IBundleClasspathEntry bundleClasspathEntry = bundleProjectService.newBundleClasspathEntry(new Path(
        SRC_DIRECTORY_NAME), new Path(BIN_DIRECTORY_NAME), null);

    //
    bundleProjectDescription.setBundleClassath(new IBundleClasspathEntry[] { bundleClasspathEntry });

    //
    bundleProjectDescription.apply(null);

    // step 5: copy the source files
    IFolder srcFolder = project.getFolder(SRC_DIRECTORY_NAME);

    // copy the source
    for (IResource resourceStandin : getCurrentModule().getResources(ContentType.SOURCE)) {

      if (!resourceStandin.getPath().startsWith("META-INF")) {

        //
        File targetFile = new File(srcFolder.getRawLocation().toFile(), resourceStandin.getPath());
        targetFile.getParentFile().mkdirs();

        try {
          //
          FileUtils.copy(new ByteArrayInputStream(resourceStandin.getContent()), new FileOutputStream(targetFile),
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
   * {@inheritDoc}
   */
  protected ManifestContents createManifest() throws CoreException {
    return _manifestCreator.createManifest(getCurrentModularizedSystem(), getCurrentModule(),
        BundleManifestUtils.createBundleManifest(getCurrentManifestTemplate()),
        BundleManifestUtils.createBundleManifest(getOriginalManifest()), new ExportPackagePreferences(),
        new PackageWiringPreferences());
  }
}
