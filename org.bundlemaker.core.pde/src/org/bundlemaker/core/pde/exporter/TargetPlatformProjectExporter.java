package org.bundlemaker.core.pde.exporter;

import org.bundlemaker.core.exporter.AbstractExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.StandardBundlorBasedBinaryBundleExporter;
import org.bundlemaker.core.exporter.StandardModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.pde.Activator;
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
	public void export(IModularizedSystem modularizedSystem,
			IModuleExporterContext context) throws Exception {

		// get a non-existing project name
		String projectName = getUniqueProjectName(String.format("%s.target",
				modularizedSystem.getName()));

		// delete and create project
		IProject project = deleteAndCreateProject(projectName);

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

		StandardModuleExporterContext exporterContext = new StandardModuleExporterContext(
				context.getBundleMakerProject(), folder.getRawLocation()
						.toFile(), context.getModularizedSystem());

		//
		super.export(modularizedSystem, exporterContext);
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
