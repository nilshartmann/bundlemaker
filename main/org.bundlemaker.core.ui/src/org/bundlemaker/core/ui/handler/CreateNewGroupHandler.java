package org.bundlemaker.core.ui.handler;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.analysis.ArtifactType;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.validators.NonEmptyStringValidator;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class CreateNewGroupHandler extends AbstractBundleMakerHandler {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void execute(ExecutionEvent event, ISelection selection) throws Exception {
    IStructuredSelection structuredSelection = (IStructuredSelection) selection;
    IBundleMakerArtifact artifact = (IBundleMakerArtifact) structuredSelection.getFirstElement();

    Shell shell = HandlerUtil.getActiveShell(event);
    if (!(artifact instanceof IGroupAndModuleContainer)) {
      MessageDialog.openError(shell, "Unsupported artifact selected",
          "Please select another Group or the Root artifact to create a new Group.");
      return;
    }

    IGroupAndModuleContainer groupAndModuleContainer = (IGroupAndModuleContainer) artifact;
    Collection<IBundleMakerArtifact> children = groupAndModuleContainer.getChildren();

    Set<String> existingArtifactNames = new HashSet<String>();
    for (IBundleMakerArtifact iBundleMakerArtifact : children) {
      existingArtifactNames.add(iBundleMakerArtifact.getName());
    }

    // Create Validator
    GroupNameValidator groupNameValidator = new GroupNameValidator(existingArtifactNames);

    // JFace Input Dialog
    InputDialog dlg = new InputDialog(shell, "Create new Group", "Please enter the name of new Group", "",
        groupNameValidator);

    if (dlg.open() != Window.OK) {
      // canceled
      return;
    }

    // we have to use "getOrCreateGroup" to prevent duplicate groups
    IGroupAndModuleContainer advancedArtifact = ((IGroupAndModuleContainer) artifact);
    advancedArtifact.getOrCreateGroup(new Path(dlg.getValue()));

    // update navigator
    // TODO
    CommonNavigatorUtils.refresh("org.eclipse.ui.navigator.ProjectExplorer",
        artifact.getType().equals(ArtifactType.Root) ? artifact : artifact.getParent(ArtifactType.Root));
  }

  class GroupNameValidator extends NonEmptyStringValidator {
    private final Set<String> _existingGroupNames;

    /**
     * @param existingGroupNames
     */
    public GroupNameValidator(Set<String> existingGroupNames) {
      _existingGroupNames = existingGroupNames;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bundlemaker.core.ui.validators.NonEmptyStringValidator#isValid(java.lang.String)
     */
    @Override
    public String isValid(String newText) {
      String result = super.isValid(newText);
      if (result != null) {
        return result;
      }

      for (String existingArtifactName : _existingGroupNames) {
        if (existingArtifactName.equalsIgnoreCase(newText.trim())) {
          return "There's already an Artifact with name '" + newText + "'";
        }
      }

      return null;

    }

  }

}
