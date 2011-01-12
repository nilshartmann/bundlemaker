package org.bundlemaker.core.exporter;

import java.io.File;
import java.io.IOException;

import org.bundlemaker.core.exporter.manifest.BundlorBasedManifestCreator;
import org.bundlemaker.core.exporter.manifest.ManifestUtils;
import org.bundlemaker.core.model.projectdescription.ContentType;
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
public class StandardBundlorBasedBinaryBundleExporter extends AbstractExporter {

	/** the template directory **/
	public static final Object TEMPLATE_DIRECTORY = "TEMPLATE_DIRECTORY";

	/** the jar file creator */
	private JarFileCreator _jarFileCreator;

	/**
	 * <p>
	 * Creates a new instance of type
	 * {@link StandardBundlorBasedBinaryBundleExporter}.
	 * </p>
	 */
	public StandardBundlorBasedBinaryBundleExporter() {

		// create the jar file creator
		_jarFileCreator = new JarFileCreator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canExport(IModularizedSystem modularizedSystem,
			IResourceModule module, IModuleExporterContext context) {

		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IOException
	 */
	@Override
	public void export(IModularizedSystem modularizedSystem,
			final IResourceModule module, IModuleExporterContext context)
			throws IOException {

		// create the manifest
		ManifestContents manifestContents = new BundlorBasedManifestCreator(
				module, context).createManifestContents(false);

		// create the target file
		File targetFile = new File(context.getDestinationDirectory(), module
				.getModuleIdentifier().getName()
				+ "_"
				+ module.getModuleIdentifier().getVersion() + ".jar");

		// create the jar archive
		_jarFileCreator.createJarArchive(
				module.getResources(ContentType.BINARY),
				ManifestUtils.toManifest(manifestContents), targetFile);
	}

}
