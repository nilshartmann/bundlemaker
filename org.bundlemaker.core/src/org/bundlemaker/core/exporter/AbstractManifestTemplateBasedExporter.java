package org.bundlemaker.core.exporter;

import java.io.File;

import org.bundlemaker.core.exporter.manifest.internal.ManifestUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

import com.springsource.bundlor.util.SimpleManifestContents;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public abstract class AbstractManifestTemplateBasedExporter extends
		AbstractExporter {

	/** - */
	private ManifestContents _manifestContents;

	/** - */
	private ManifestContents _manifestTemplateContents;

	/** - */
	private File _templateDirectory;

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public final File getTemplateDirectory() {
		return _templateDirectory;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param templateDirectory
	 */
	public final void setTemplateDirectory(File templateDirectory) {
		Assert.isNotNull(templateDirectory);
		Assert.isTrue(templateDirectory.isDirectory());

		_templateDirectory = templateDirectory;
	}

	@Override
	protected void preExportModule() throws CoreException {

		// get the template manifest
		_manifestTemplateContents = getManifestTemplate();

		//
		_manifestContents = createManifest();
		Assert.isNotNull(_manifestContents, String.format(
				"The method createManifest(IModularizedSystem, "
						+ "IResourceModule, IModuleExporterContext) of class "
						+ "'%s' returned 'null'.", this.getClass().getName()));
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public ManifestContents getCurrentManifest() {
		Assert.isNotNull(_manifestContents, String.format(
				"No manifest set. The method createManifest(IModularizedSystem, "
						+ "IResourceModule, IModuleExporterContext) of class "
						+ "'%s' has not been called yet.", this.getClass()
						.getName()));

		return _manifestContents;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public ManifestContents getCurrentManifestTemplate() {
		return _manifestTemplateContents;
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
	protected abstract ManifestContents createManifest() throws CoreException;

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected ManifestContents getManifestTemplate() {

		//
		if (_templateDirectory == null) {
			return createDefaultManifestTemplate();
		}

		File templateFile = new File(_templateDirectory, getCurrentModule()
				.getModuleIdentifier().toString() + ".template");

		if (!templateFile.exists()) {
			templateFile = new File(_templateDirectory, getCurrentModule()
					.getModuleIdentifier().getName() + ".template");
		}

		ManifestContents templateManifestContents = ManifestUtils
				.readManifestContents(templateFile);

		if (templateManifestContents != null) {
			return templateManifestContents;
		}

		// return the default manifest contents
		else {
			return createDefaultManifestTemplate();
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected ManifestContents createDefaultManifestTemplate() {
		return new SimpleManifestContents();
	}
}
