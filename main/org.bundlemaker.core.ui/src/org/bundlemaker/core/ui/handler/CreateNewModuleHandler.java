package org.bundlemaker.core.ui.handler;

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

    EditModuleDialog dialog = new EditModuleDialog(shell);
    if (dialog.open() != Window.OK) {
      // cancel
      return null;
    }

    IModuleArtifact newModuleArtifact = groupAndModuleContainer.getOrCreateModule(dialog.getModuleName(),
        dialog.getModuleVersion());

    return newModuleArtifact;
  }

}
