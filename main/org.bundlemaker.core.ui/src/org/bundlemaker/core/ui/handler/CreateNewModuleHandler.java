package org.bundlemaker.core.ui.handler;

import org.bundlemaker.core.analysis.ArtifactType;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.validators.NonEmptyStringValidator;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

public class CreateNewModuleHandler extends AbstractBundleMakerHandler {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void execute(ExecutionEvent event, ISelection selection) throws Exception {

    IStructuredSelection structuredSelection = (IStructuredSelection) selection;
    IBundleMakerArtifact artifact = (IBundleMakerArtifact) structuredSelection.getFirstElement();
    if (artifact instanceof IGroupAndModuleContainer) {

      // JFace Input Dialog
      InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), "", "Enter Module Name", "module",
          NonEmptyStringValidator.instance());

      if (dlg.open() == Window.OK) {
        System.out.println(dlg.getValue());
      }

      System.out.println(artifact);

      IModuleArtifact moduleArtifact = ((IGroupAndModuleContainer) artifact).getOrCreateModule(dlg.getValue(), "1.0.0");

      System.out.println("Classifiy " + moduleArtifact.getAssociatedModule().getClassification());

      // update navigator
      // TODO
      CommonNavigatorUtils.refresh("org.eclipse.ui.navigator.ProjectExplorer",
          artifact.getType().equals(ArtifactType.Root) ? artifact : artifact.getParent(ArtifactType.Root));
    }
  }

}
