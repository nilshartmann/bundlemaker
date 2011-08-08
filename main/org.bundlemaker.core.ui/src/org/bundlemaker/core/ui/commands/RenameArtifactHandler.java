package org.bundlemaker.core.ui.commands;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.ui.handlers.AbstractBundleMakerHandler;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.ui.view.navigator.CommonNavigatorUtils;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.IInputValidator;
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
    IArtifact artifact = (IArtifact) structuredSelection.getFirstElement();
    if (artifact instanceof IModuleArtifact || artifact instanceof IGroupArtifact) {

      // JFace Input Dialog
      InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), "", "Enter Name", "GROUP",
          new LengthValidator());

      if (dlg.open() == Window.OK) {
        System.out.println(dlg.getValue());
      }

      if (artifact instanceof IModuleArtifact) {
        ((IModuleArtifact) artifact).setNameAndVersion(dlg.getValue(), "1.0.0");
      } else if (artifact instanceof IGroupArtifact) {
        ((IGroupArtifact) artifact).setName(dlg.getValue());
      }

      // update navigator
      // TODO
      CommonNavigatorUtils.refresh("org.eclipse.ui.navigator.ProjectExplorer",
          artifact.getType().equals(ArtifactType.Root) ? artifact : artifact.getParent(ArtifactType.Root));
    }
  }

  /**
   * This class validates a String. It makes sure that the String is between 5 and 8 characters
   */
  class LengthValidator implements IInputValidator {
    /**
     * Validates the String. Returns null for no error, or an error message
     * 
     * @param newText
     *          the String to validate
     * @return String
     */
    @Override
    public String isValid(String newText) {

      // Input must be OK
      return null;
    }
  }
}
