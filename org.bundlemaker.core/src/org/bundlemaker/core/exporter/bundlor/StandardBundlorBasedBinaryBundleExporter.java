package org.bundlemaker.core.exporter.bundlor;

import org.bundlemaker.core.exporter.AbstractJarFileBundleExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;

import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public class StandardBundlorBasedBinaryBundleExporter extends
		AbstractJarFileBundleExporter {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modularizedSystem
	 * @param module
	 * @param context
	 * @return
	 * @throws Exception
	 */
	protected ManifestContents createManifest(
			IModularizedSystem modularizedSystem, IResourceModule module,
			IModuleExporterContext context) throws Exception {

		// create the manifest
		return new BundlorBasedManifestCreator(module, context)
				.createManifestContents(
						false,
						getManifestTemplateFromTemplateFile(modularizedSystem,
								module, context));
	}
}
