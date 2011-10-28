package org.bundlemaker.core.ui.commands;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.analysis.ui.handlers.AbstractBundleMakerHandler;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.ui.commands.validators.NonEmptyStringValidator;
import org.bundlemaker.core.ui.view.navigator.CommonNavigatorUtils;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

public class CreateNewGroupHandler extends AbstractBundleMakerHandler {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void execute(ExecutionEvent event, ISelection selection) throws Exception {

    IStructuredSelection structuredSelection = (IStructuredSelection) selection;
    IArtifact artifact = (IArtifact) structuredSelection.getFirstElement();
    System.out.println(artifact.getClass());
    if (artifact instanceof IRootArtifact || artifact instanceof IGroupArtifact) {

      // JFace Input Dialog
      InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), "", "Enter group name", "GROUP",
          NonEmptyStringValidator.instance());

      if (dlg.open() == Window.OK) {
        System.out.println(dlg.getValue());
      }

      System.out.println(artifact);

      IBundleMakerArtifact advancedArtifact = ((IBundleMakerArtifact) artifact);
      IDependencyModel dependencyModel = advancedArtifact.getDependencyModel();
      IArtifact newGroup = dependencyModel.createArtifactContainer(dlg.getValue(), dlg.getValue(), ArtifactType.Group);
      advancedArtifact.addArtifact(newGroup);

      // update navigator
      // TODO
      CommonNavigatorUtils.refresh("org.eclipse.ui.navigator.ProjectExplorer",
          artifact.getType().equals(ArtifactType.Root) ? artifact : artifact.getParent(ArtifactType.Root));
    }
  }

}
