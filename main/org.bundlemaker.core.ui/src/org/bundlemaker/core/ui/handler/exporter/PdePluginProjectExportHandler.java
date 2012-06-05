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
package org.bundlemaker.core.ui.handler.exporter;

import org.bundlemaker.core.exporter.IModuleExporter;
import org.bundlemaker.core.osgi.exporter.pde.PdePluginProjectModuleExporter;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class PdePluginProjectExportHandler extends AbstractExportHandler {

  @Override
  protected IModuleExporter createExporter() throws Exception {
    // Create the exporter instance
    PdePluginProjectModuleExporter pdeExporter = new PdePluginProjectModuleExporter();
    pdeExporter.setUseClassifcationForExportDestination(true);

    return pdeExporter;

  }

  // /*
  // * (non-Javadoc)
  // *
  // * @see org.bundlemaker.core.ui.handler.exporter.AbstractExportHandler#getDestinationDirectory()
  // */
  // @Override
  // protected File getDestinationDirectory() {
  // // TODO Auto-generated method stub
  // return new File("/Users/nils/develop/bundlemaker/export-target");
  // }

}
