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
package org.bundlemaker.core.osgi.exporter;

import java.io.File;

import org.bundlemaker.core.exporter.AbstractExporter;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public abstract class AbstractTemplateDirectoryBasedExporter extends
		AbstractExporter {

	/** the root directory for all templates */
	private File _templateRootDirectory;

	/** the module template directory */
	private File _moduleTemplateDirectory;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param templateRootDirectory
	 */
	public final void setTemplateRootDirectory(File templateRootDirectory) {
		Assert.isNotNull(templateRootDirectory);
		Assert.isTrue(templateRootDirectory.isDirectory());

		_templateRootDirectory = templateRootDirectory;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected final boolean hasCurrentTemplateRootDirectory() {
		return _moduleTemplateDirectory != null;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected final File getCurrentTemplateRootDirectory() {
		return _templateRootDirectory;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected final File getCurrentModuleTemplateDirectory() {
		return _moduleTemplateDirectory;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected final boolean hasCurrentModuleTemplateDirectory() {
		return _moduleTemplateDirectory != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void preExportModule() throws CoreException {

		//
		super.preExportModule();

		// 'create' the project template directory
		_moduleTemplateDirectory = createModuleTemplateDirectory();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected File createModuleTemplateDirectory() {

		//
		File result = new File(_templateRootDirectory, getCurrentModule()
				.getModuleIdentifier().toString());

		if (result.exists()) {
			return result;
		}

		//
		result = new File(_templateRootDirectory, getCurrentModule()
				.getModuleIdentifier().getName());

		if (result.exists()) {
			return result;
		}

		//
		return null;
	}
}
