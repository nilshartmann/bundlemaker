package org.bundlemaker.core.exporter.bundle;

import org.bundlemaker.core.exporter.AbstractJarFileBundleExporter;

import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public class BinaryBundleExporter extends AbstractJarFileBundleExporter {

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
	protected ManifestContents createManifest() throws Exception {

		// create the manifest
		return new BundleManifestCreator(getCurrentModule(),
				getCurrentManifestTemplate(), getCurrentModularizedSystem(),
				getCurrentContext()).createManifest();
	}
}
