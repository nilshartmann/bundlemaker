/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.osgi.exporter.helper;

import java.io.File;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.osgi.utils.ManifestUtils;

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
public abstract class AbstractManifestTemplateBasedExporterHelper extends
		AbstractTemplateDirectoryBasedExporterHelper {

	/** the manifest template contents */
	private ManifestContents _manifestTemplateContents;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modularizedSystem
	 * @param module
	 * @param context
	 * @param moduleTemplateDirectory
	 */
	public AbstractManifestTemplateBasedExporterHelper(
			IModularizedSystem modularizedSystem, IResourceModule module,
			IModuleExporterContext context, File templateRootDirectory) {

		//
		super(modularizedSystem, module, context, templateRootDirectory);

		// get the template manifest
		_manifestTemplateContents = createManifestTemplate();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modularizedSystem
	 * @param module
	 * @param context
	 */
	public AbstractManifestTemplateBasedExporterHelper(
			IModularizedSystem modularizedSystem, IResourceModule module,
			IModuleExporterContext context) {

		//
		this(modularizedSystem, module, context, null);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected final ManifestContents getManifestTemplate() {
		return _manifestTemplateContents;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected ManifestContents createManifestTemplate() {

		//
		if (getTemplateRootDirectory() == null) {
			return createDefaultManifestTemplate();
		}

		File templateFile = getManifestTemplateFile();

		if (templateFile == null) {
			return createDefaultManifestTemplate();
		}

		ManifestContents templateManifestContents = ManifestUtils
				.readManifestContents(templateFile);

		//
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
	protected File getManifestTemplateFile() {

		//
		File rootDirectory = null;

		// step 1: does a module template directory exists (e.g.
		// '<root>/x.y.z_1.2.3/' or '<root>/x.y.z/')?
		if (hasModuleTemplateDirectory()) {

			// step 1a: get the current root directory
			rootDirectory = getModuleTemplateDirectory();

			// step 1b: try '<root>/<module-root>/x.y.z_1.2.3.template'
			File templateFile = new File(rootDirectory, getModule()
					.getModuleIdentifier().toString() + ".template");

			// step 1c: try '<root>/<module-root>/x.y.z.template'
			if (!templateFile.exists()) {
				templateFile = new File(rootDirectory, getModule()
						.getModuleIdentifier().getName() + ".template");
			}

			// step 1d: try '<root>/<module-root>/manifest.template'
			if (!templateFile.exists()) {
				templateFile = new File(rootDirectory, "manifest.template");
			}

			// step 1e: try '<root>/<module-root>/manifest.properties'
			// DON'T USE - JUST FOR BACKWARD COMPATIBILITY
			if (!templateFile.exists()) {
				templateFile = new File(rootDirectory, "manifest.properties");
			}

			//
			return templateFile.exists() ? templateFile : null;
		}

		// step 2: try the root template directory
		else {

			// step 1b: try '<root>/x.y.z_1.2.3.template'
			File templateFile = new File(getTemplateRootDirectory(),
					getModule().getModuleIdentifier().toString() + ".template");

			// step 1c: try '<root>/x.y.z.template'
			if (!templateFile.exists()) {
				templateFile = new File(rootDirectory, getModule()
						.getModuleIdentifier().getName() + ".template");
			}

			//
			return templateFile.exists() ? templateFile : null;
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
