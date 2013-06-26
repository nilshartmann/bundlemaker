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

import java.io.File;

import org.bundlemaker.core._type.SimpleReportExporter;
import org.bundlemaker.core.exporter.IModuleExporter;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ReportExportHandler extends AbstractExportHandler {

  @Override
  protected IModuleExporter createExporter() throws Exception {
    return new SimpleReportExporter();
  }

  @Override
  protected File getDestinationDirectory() {
    DirectoryDialog dialog = new DirectoryDialog(new Shell());
    dialog.setMessage("Select the export destination folder");
    dialog.setText("Export modules");
    String res = dialog.open();

    if (res != null) {
      File destination = Path.fromOSString(res).makeAbsolute().toFile();
      destination.mkdirs();

      return destination;
    }

    return null;
  }
}
