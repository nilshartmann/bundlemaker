package org.bundlemaker.core.exporter.util;

import org.bundlemaker.core.exporter.AbstractJarFileBundleExporter;
import org.bundlemaker.core.exporter.manifest.BundleManifestCreator;
import org.eclipse.core.runtime.CoreException;

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

	/** - */
	private boolean _useRequireBundle = true;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param useRequireBundle
	 */
	public void setUseRequireBundle(boolean useRequireBundle) {
		_useRequireBundle = useRequireBundle;
	}

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
	protected ManifestContents createManifest() throws CoreException {

		// create the manifest
		BundleManifestCreator creator = new BundleManifestCreator(
				getCurrentModularizedSystem(), getCurrentModule(),
				getCurrentContext(), getCurrentManifestTemplate());

		// set useRequireBundle
		creator.setUseRequireBundle(_useRequireBundle);
		
		// create manifest
		return creator.createManifest();
	}
}
