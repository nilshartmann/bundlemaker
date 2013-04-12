/*******************************************************************************
 * Copyright (c) 2013 BundleMaker Development Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.jdt.exporter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.bundlemaker.core.exporter.AbstractExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.util.Helper;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;

/**
 * h
 * <p>
 * </p>
 * 
 * @author Nils Hartmann
 */
public class JdtProjectExporter extends AbstractExporter {

	/** - */
	private static final String SRC_DIRECTORY_NAME = "src";

	/** - */
	private static final String BIN_DIRECTORY_NAME = "bin";

	/** - */
	private boolean _useClassifcationForExportDestination;

	public JdtProjectExporter() {

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
	public void setUseClassifcationForExportDestination(
			boolean useClassifcationForExportDestination) {
		_useClassifcationForExportDestination = useClassifcationForExportDestination;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canExport(IModularizedSystem modularizedSystem,
			IResourceModule module, IModuleExporterContext context) {

		//
		return !module.getResources(ProjectContentType.SOURCE).isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doExport(IProgressMonitor progressMonitor) throws CoreException {

		// step 1: get a non-existing project name
		String projectName = Helper.getUniqueProjectName(getCurrentModule()
				.getModuleIdentifier().getName());

		// step 2: delete and create project
		IPath location = null;

		if (isUseClassifcationForExportDestination()) {

			Path destinationDirectoryPath = new Path(getCurrentContext()
					.getDestinationDirectory().getAbsolutePath());

			location = destinationDirectoryPath.append(
					getCurrentModule().getClassification()).append(projectName);
		}

		// (re-)create the project
		IProject project = Helper.deleteAndCreateProject(projectName, location);

		// step 3: add java and plug-nature
		IProjectDescription description = project.getDescription();
		description.setNatureIds(new String[] { JavaCore.NATURE_ID });
		project.setDescription(description, null);

		// 'clean' the java project
		IJavaProject javaProject = JavaCore.create(project);
		
		
		javaProject.setRawClasspath(new IClasspathEntry[] { //
				JavaRuntime.getDefaultJREContainerEntry(), //
				JavaCore.newSourceEntry(new Path(project.getName()).makeAbsolute().append(SRC_DIRECTORY_NAME)) //
				}//
		, null);
		javaProject.save(null, true);


		// step 5: copy the source files
		IFolder srcFolder = project.getFolder(SRC_DIRECTORY_NAME);

		// copy the source
		for (IResource resourceStandin : getCurrentModule().getResources(
				ProjectContentType.SOURCE)) {

				//
				File targetFile = new File(srcFolder.getRawLocation().toFile(),
						resourceStandin.getPath());
				targetFile.getParentFile().mkdirs();

				try {
					//
					FileUtils.copy(
							new ByteArrayInputStream(resourceStandin
									.getContent()), new FileOutputStream(
									targetFile), new byte[1024]);
				} catch (Exception e) {
					throw new CoreException(new Status(IStatus.ERROR,
							"org.bundlemaker.core.jdt", "Unable to copy file "
									+ resourceStandin.getRoot() + "to "
									+ targetFile + ": " + e, e));
				}
		}

		// Refresh source-folder to make Eclipse aware of new copied files
		srcFolder.refreshLocal(1, progressMonitor);
	}
}
