package org.bundlemaker.core.ui.handler;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.validators.NonEmptyStringValidator;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

public class RenameArtifactHandler extends AbstractBundleMakerHandler {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void execute(ExecutionEvent event, ISelection selection) throws Exception {

    IStructuredSelection structuredSelection = (IStructuredSelection) selection;
    IBundleMakerArtifact artifact = (IBundleMakerArtifact) structuredSelection.getFirstElement();
    if (artifact instanceof IModuleArtifact || artifact instanceof IGroupArtifact) {

      // JFace Input Dialog
      InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), "", "Enter Name", "GROUP",
          NonEmptyStringValidator.instance());

      if (dlg.open() != Window.OK) {
        return;
      }

      if (artifact instanceof IModuleArtifact) {
        ((IModuleArtifact) artifact).setNameAndVersion(dlg.getValue(), "1.0.0");
      } else if (artifact instanceof IGroupArtifact) {
        ((IGroupArtifact) artifact).setName(dlg.getValue());
      }

      // update navigator
      CommonNavigatorUtils.refresh(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID,
          (artifact instanceof IRootArtifact) ? artifact : artifact.getParent(IRootArtifact.class));
    }
  }
}
