package org.bundlemaker.core.exporter;

import java.io.File;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.Assert;

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

	/** the template directory **/
	public static final Object TEMPLATE_DIRECTORY = "TEMPLATE_DIRECTORY";

	/** - */
	private ManifestContents _manifestContents;

	/** - */
	private ManifestContents _manifestTemplateContents;

	@Override
	public final void export(IModularizedSystem modularizedSystem,
			IResourceModule module, IModuleExporterContext context)
			throws Exception {

		// get the template manifest
		_manifestTemplateContents = getManifestTemplate();

		//
		_manifestContents = createManifest();
		Assert.isNotNull(_manifestContents, String.format(
				"The method createManifest(IModularizedSystem, "
						+ "IResourceModule, IModuleExporterContext) of class "
						+ "'%s' returned 'null'.", this.getClass().getName()));

		//
		onExport();
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
	 */
	protected abstract void onExport() throws Exception;

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
	protected abstract ManifestContents createManifest() throws Exception;

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected ManifestContents getManifestTemplate() {

		//
		if (!getCurrentContext().containsAttribute(TEMPLATE_DIRECTORY)) {
			return createDefaultManifestTemplate();
		}

		//
		Object attribute = getCurrentContext().getAttribute(TEMPLATE_DIRECTORY);

		// type check
		if (!(attribute instanceof File)) {

			// TODO
			throw new RuntimeException("Wrong type: " + attribute.getClass());
		}

		// try to read the template file
		File templateDirectory = (File) attribute;
		File templateFile = new File(templateDirectory, getCurrentModule()
				.getModuleIdentifier().getName() + ".template");

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
