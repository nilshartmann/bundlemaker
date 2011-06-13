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

import java.io.File;
import java.util.List;

import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.ui.commands.AbstractBundleMakerHandler;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractExportHandler extends AbstractBundleMakerHandler {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.ui.commands.AbstractBundleMakerHandler#execute(java.util.List)
   */
  @Override
  protected void execute(List<IArtifact> selectedArtifacts) throws Exception {

    for (IArtifact iArtifact : selectedArtifacts) {
      if (iArtifact instanceof IAdvancedArtifact) {
        export((IAdvancedArtifact) iArtifact);
      }
    }

  }

  /**
   * Export the given artifact
   * 
   * @param advancedArtifact
   *          The artifact that should be exported. Never null
   * @throws Exception
   */
  protected abstract void export(IAdvancedArtifact advancedArtifact) throws Exception;

  protected File getDestinationDirectory() {
    DirectoryDialog dialog = new DirectoryDialog(new Shell());
    dialog.setMessage("Select an external folder");
    dialog.setText("External folder");
    // Path currPath = new Path(_entryText.getText());
    // dialog.setFilterPath(currPath.toOSString());
    String res = dialog.open();

    if (res != null) {
      File destination = Path.fromOSString(res).makeAbsolute().toFile();
      destination.mkdirs();

      return destination;
    }

    return null;

  }

}
