package org.bundlemaker.core.exporter.manifest;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.manifest.internal.CurrentModule;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.CoreException;

import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleManifestCreator {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modularizedSystem
	 * @param context
	 * @param resourceModule
	 * @param manifestTemplate
	 * 
	 * @return
	 * @throws CoreException
	 */
	public static ManifestContents createManifest(
			IModularizedSystem modularizedSystem,
			IModuleExporterContext context, IResourceModule resourceModule,
			ManifestContents manifestTemplate) throws CoreException {

		// current module
		CurrentModule currentModule = new CurrentModule(modularizedSystem,
				resourceModule, context, manifestTemplate);

		// create the manifest
		return new org.bundlemaker.core.exporter.manifest.internal.BundleManifestCreator(
				currentModule).createManifest();

	}
}
