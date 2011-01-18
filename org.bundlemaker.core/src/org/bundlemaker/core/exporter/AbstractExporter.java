package org.bundlemaker.core.exporter;

import java.io.ByteArrayInputStream;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.util.EclipseProjectUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * <p>
 * Abstract base class for exporters. This implementation's
 * {@link #export(IModularizedSystem, IModuleExporterContext)} method simply
 * calls the
 * {@link AbstractExporter#export(IModularizedSystem, IResourceModule, IModuleExporterContext)}
 * method for each contained {@link IResourceModule}: <code><pre>
 * for (IResourceModule resourceModule : modularizedSystem.getResourceModules()) {
 *   if (canExport(modularizedSystem, resourceModule, context)) {
 *     export(modularizedSystem, resourceModule, context);
 *   }
 * }
 * </pre></code>
 * </p>
 * <p>
 * Clients may implement subclass this class.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractExporter implements IModuleExporter,
		IModularizedSystemExporter {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void export(IModularizedSystem modularizedSystem,
			IModuleExporterContext context) throws Exception {

		// simply call export() for each contained
		for (IResourceModule resourceModule : modularizedSystem
				.getResourceModules()) {

			// export if possible
			if (canExport(modularizedSystem, resourceModule, context)) {
				export(modularizedSystem, resourceModule, context);
			}
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param projectName
	 * @return
	 */
	protected String getUniqueProjectName(String projectName) {
		//
		String newProjectName = projectName;

		// if the project name exists, add a post-fix
		if (doesNonGeneratedProjectExist(newProjectName)) {

			//
			int postfix = 0;

			//
			while (doesNonGeneratedProjectExist(newProjectName)) {

				//
				postfix++;

				//
				newProjectName = String.format("%s (%s)", projectName, postfix);
			}

			//
			projectName = newProjectName;
		}

		return projectName;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param projectName
	 * @return
	 * @throws CoreException
	 */
	public IProject deleteAndCreateProject(String projectName, IPath location)
			throws CoreException {

		// create project
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);

		// delete the project if exists
		if (project.exists()) {
			project.delete(true, true, null);
		}

		// create the project if not exists
		if (!project.exists()) {

			// create the description
			IProjectDescription desc = project.getWorkspace()
					.newProjectDescription(project.getName());

			//
			if (location != null) {
				desc.setLocation(location);
			}

			//
			project.create(desc, null);

			//
			if (!project.isOpen()) {
				project.open(null);
			}
		}

		// add the 'bundlemakergenerated' flag
		IFile file = project.getFile(".bundlemakergenerated");
		file.create(new ByteArrayInputStream(new byte[0]), true, null);

		// return the result
		return project;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param projectName
	 * @return
	 */
	private boolean doesNonGeneratedProjectExist(String projectName) {

		try {

			boolean result = EclipseProjectUtils.exists(projectName)
					&& !EclipseProjectUtils.exists(new Path(projectName
							+ "/.bundlemakergenerated"));

			//
			EclipseProjectUtils.getOrCreateSimpleProject(projectName);

			//
			return result;

		} catch (CoreException e) {

			//
			return true;
		}
	}
}
