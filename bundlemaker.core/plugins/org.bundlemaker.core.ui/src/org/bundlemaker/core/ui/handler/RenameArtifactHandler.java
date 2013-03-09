package org.bundlemaker.core.ui.handler;

import java.util.Set;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class RenameArtifactHandler extends AbstractBundleMakerHandler {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void execute(ExecutionEvent event, ISelection selection) throws Exception {

    IStructuredSelection structuredSelection = (IStructuredSelection) selection;
    IBundleMakerArtifact artifact = (IBundleMakerArtifact) structuredSelection.getFirstElement();

    Shell shell = HandlerUtil.getActiveShell(event);

    if (artifact == null) {
      return;
    }

    if (artifact instanceof IGroupArtifact) {
      IGroupArtifact groupArtifact = (IGroupArtifact) artifact;
      IGroupAndModuleContainer parentContainer = (IGroupAndModuleContainer) groupArtifact.getParent();

      // Propmpt user for new group name
      String groupName = CreateNewGroupHandler.getGroupName(shell, parentContainer, groupArtifact.getName(), false);
      if (groupName == null || groupName.equals(groupArtifact.getName())) {
        // unchanged
        return;
      }

      // set new group name
      groupArtifact.setName(groupName);
    } else if (artifact instanceof IModuleArtifact) {
      IModuleArtifact moduleArtifact = (IModuleArtifact) artifact;
      IGroupAndModuleContainer parentContainer = (IGroupAndModuleContainer) moduleArtifact.getParent();

      //
      Set<String> existingArtifactNames = AbstractCreateGroupOrModuleHandler.getExistingArtifactNames(parentContainer);
      existingArtifactNames.remove(moduleArtifact.getName());

      // Prompt user for new module name and version
      EditModuleDialog editModuleDialog = new EditModuleDialog(shell, existingArtifactNames, true,
          moduleArtifact.getModuleName(), moduleArtifact.getModuleVersion());
      if (editModuleDialog.open() != Window.OK) {
        return;
      }

      // update artifact with new name and version
      moduleArtifact.setNameAndVersion(editModuleDialog.getModuleName(), editModuleDialog.getModuleVersion());
    } else {
      // unsupported type
      System.out.println("ArtifactType: " + artifact.getClass().getName());

      return;
    }

    // update navigator
    CommonNavigatorUtils.refresh(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID,
        (artifact instanceof IRootArtifact) ? artifact : artifact.getParent(IRootArtifact.class));
  }
}
