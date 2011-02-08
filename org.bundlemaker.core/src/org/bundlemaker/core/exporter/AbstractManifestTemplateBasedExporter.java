package org.bundlemaker.core.exporter;

import java.io.File;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;

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

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected ManifestContents getManifestTemplate(
			IModularizedSystem modularizedSystem, IResourceModule module,
			IModuleExporterContext context) {

		//
		return getManifestTemplateFromTemplateFile(modularizedSystem, module,
				context);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param context
	 * @return
	 */
	protected ManifestContents getManifestTemplateFromTemplateFile(
			IModularizedSystem modularizedSystem, IResourceModule module,
			IModuleExporterContext context) {

		//
		if (!context.containsAttribute(TEMPLATE_DIRECTORY)) {
			return createDefaultManifestContents();
		}

		//
		Object attribute = context.getAttribute(TEMPLATE_DIRECTORY);

		// type check
		if (!(attribute instanceof File)) {

			// TODO
			throw new RuntimeException("Wrong type: " + attribute.getClass());
		}

		// try to read the template file
		File templateDirectory = (File) attribute;
		File templateFile = new File(templateDirectory, module
				.getModuleIdentifier().getName() + ".template");

		ManifestContents templateManifestContents = ManifestUtils
				.readManifestContents(templateFile);

		if (templateManifestContents != null) {
			return templateManifestContents;
		}

		// return the default manifest contents
		else {
			return createDefaultManifestContents();
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected ManifestContents createDefaultManifestContents() {
		return new SimpleManifestContents();
	}
}
