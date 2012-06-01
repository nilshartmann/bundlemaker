package org.bundlemaker.core.ui.handler;

import java.util.Set;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

public class CreateNewModuleHandler extends AbstractCreateGroupOrModuleHandler {

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.handler.AbstractCreateGroupOrModuleHandler#createArtifact(org.eclipse.swt.widgets.Shell,
   * org.bundlemaker.core.analysis.IGroupAndModuleContainer)
   */
  @Override
  protected IBundleMakerArtifact createArtifact(Shell shell, IGroupAndModuleContainer groupAndModuleContainer) {
    Set<String> existingArtifactNames = getExistingArtifactNames(groupAndModuleContainer);

    String preset = getUniqueArtifactName(groupAndModuleContainer, "NewModule", "_1.0.0");

    EditModuleDialog dialog = new EditModuleDialog(shell, existingArtifactNames, false, preset, "1.0.0");
    if (dialog.open() != Window.OK) {
      // cancel
      return null;
    }

    IModuleArtifact newModuleArtifact = groupAndModuleContainer.getOrCreateModule(dialog.getModuleName(),
        dialog.getModuleVersion());

    return newModuleArtifact;
  }

}
