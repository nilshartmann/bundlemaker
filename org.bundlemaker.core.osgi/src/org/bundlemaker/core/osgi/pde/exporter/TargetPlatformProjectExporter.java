package org.bundlemaker.core.osgi.pde.exporter;

import java.io.File;

import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.exporter.util.Helper;
import org.bundlemaker.core.osgi.Activator;
import org.bundlemaker.core.osgi.exporter.BinaryBundleExporter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.pde.internal.core.target.provisional.IBundleContainer;
import org.eclipse.pde.internal.core.target.provisional.ITargetDefinition;
import org.eclipse.pde.internal.core.target.provisional.ITargetHandle;
import org.eclipse.pde.internal.core.target.provisional.ITargetPlatformService;

@SuppressWarnings("restriction")
public class TargetPlatformProjectExporter extends
		ModularizedSystemExporterAdapter {

	/**
	 * <p>
	 * Creates a new instance of type {@link TargetPlatformProjectExporter}.
	 * </p>
	 * 
	 */
	public TargetPlatformProjectExporter() {
		super(new BinaryBundleExporter());
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param templateDirectory
	 */
	public void setTemplateDirectory(File templateDirectory) {
		((BinaryBundleExporter) getModuleExporter())
				.setTemplateRootDirectory(templateDirectory);
	}

	@Override
	protected IModuleExporterContext preExportModules() throws Exception {

		// get a non-existing project name
		String projectName = Helper.getUniqueProjectName(String.format(
				"%s.target", getCurrentModularizedSystem().getName()));

		// delete and create project
		IProject project = Helper.deleteAndCreateProject(projectName, null);

		//
		IFolder folder = project.getFolder("bundles");
		folder.create(true, true, null);

		IFile targetFile = project.getFile(projectName);

		ITargetPlatformService targetPlatformService = Activator
				.getTargetPlatformService();

		ITargetHandle targetHandle = targetPlatformService
				.getTarget(targetFile);

		ITargetDefinition targetDefinition = targetHandle.getTargetDefinition();
		targetDefinition.setName(projectName);

		IBundleContainer bundleContainer = targetPlatformService
				.newDirectoryContainer("${project_loc:/" + projectName
						+ "}/bundles");

		targetDefinition
				.setBundleContainers(new IBundleContainer[] { bundleContainer });

		targetPlatformService.saveTargetDefinition(targetDefinition);

		return new DefaultModuleExporterContext(getCurrentContext()
				.getBundleMakerProject(), folder.getRawLocation().toFile(),
				getCurrentContext().getModularizedSystem());
	}
}
