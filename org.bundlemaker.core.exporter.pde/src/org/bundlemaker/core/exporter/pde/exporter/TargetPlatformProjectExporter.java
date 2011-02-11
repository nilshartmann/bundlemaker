package org.bundlemaker.core.exporter.pde.exporter;

import org.bundlemaker.core.exporter.AbstractExporter;
import org.bundlemaker.core.exporter.Helper;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.ModuleExporterContext;
import org.bundlemaker.core.exporter.bundlor.StandardBundlorBasedBinaryBundleExporter;
import org.bundlemaker.core.exporter.pde.Activator;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.pde.internal.core.target.provisional.IBundleContainer;
import org.eclipse.pde.internal.core.target.provisional.ITargetDefinition;
import org.eclipse.pde.internal.core.target.provisional.ITargetHandle;
import org.eclipse.pde.internal.core.target.provisional.ITargetPlatformService;

public class TargetPlatformProjectExporter extends AbstractExporter {

	/** - */
	private StandardBundlorBasedBinaryBundleExporter _binaryBundleExporter;

	/**
	 * <p>
	 * Creates a new instance of type {@link TargetPlatformProjectExporter}.
	 * </p>
	 * 
	 */
	public TargetPlatformProjectExporter() {

		//
		_binaryBundleExporter = new StandardBundlorBasedBinaryBundleExporter();
	}

	@Override
	protected void postExportModules() {
	}

	@Override
	protected void preExportModules() throws Exception {

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

		ModuleExporterContext exporterContext = new ModuleExporterContext(
				getCurrentContext().getBundleMakerProject(), folder
						.getRawLocation().toFile(), getCurrentContext()
						.getModularizedSystem());
	}

	@Override
	public boolean canExport(IModularizedSystem modularizedSystem,
			IResourceModule module, IModuleExporterContext context) {

		return _binaryBundleExporter.canExport(modularizedSystem, module,
				context);
	}

	@Override
	public void export(IModularizedSystem modularizedSystem,
			IResourceModule module, IModuleExporterContext context)
			throws Exception {

		_binaryBundleExporter.export(modularizedSystem, module, context);
	}

}
