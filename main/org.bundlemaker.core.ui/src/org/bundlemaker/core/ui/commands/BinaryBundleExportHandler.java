/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.commands;

import org.bundlemaker.core.exporter.IModuleExporter;
import org.bundlemaker.core.osgi.exporter.bundle.JarFileBundleExporter;
import org.bundlemaker.core.osgi.manifest.CustomManifestCreator;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BinaryBundleExportHandler extends AbstractExportHandler {

  @Override
  protected IModuleExporter createExporter() throws Exception {
    return new JarFileBundleExporter(null, new CustomManifestCreator(), null);
  }
}
