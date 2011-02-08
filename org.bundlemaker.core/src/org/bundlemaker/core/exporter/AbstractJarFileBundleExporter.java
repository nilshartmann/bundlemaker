package org.bundlemaker.core.exporter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.util.JarFileUtils;

import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractJarFileBundleExporter extends
		AbstractManifestTemplateBasedExporter {

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IOException
	 */
	@Override
	public void export(IModularizedSystem modularizedSystem,
			final IResourceModule module, IModuleExporterContext context)
			throws Exception {

		// create the manifest
		ManifestContents manifestContents = createManifest(modularizedSystem,
				module, context);

		// create the output stream
		OutputStream outputStream = createOutputStream(modularizedSystem,
				module, context);

		// export the jar archive
		JarFileUtils.createJarArchive(module.getResources(ContentType.BINARY),
				ManifestUtils.toManifest(manifestContents), outputStream);

		// close the output stream
		outputStream.close();
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
	protected abstract ManifestContents createManifest(
			IModularizedSystem modularizedSystem, IResourceModule module,
			IModuleExporterContext context) throws Exception;

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
	protected OutputStream createOutputStream(
			IModularizedSystem modularizedSystem, IResourceModule module,
			IModuleExporterContext context) throws Exception {

		// create the target file
		File targetFile = new File(context.getDestinationDirectory(),
				computeJarFileName(module));

		// create the parent directories
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}

		// return a new file output stream
		return new FileOutputStream(targetFile);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param module
	 * @return
	 */
	protected String computeJarFileName(IResourceModule module) {

		//
		return module.getModuleIdentifier().getName() + "_"
				+ module.getModuleIdentifier().getVersion() + ".jar";
	}
}
