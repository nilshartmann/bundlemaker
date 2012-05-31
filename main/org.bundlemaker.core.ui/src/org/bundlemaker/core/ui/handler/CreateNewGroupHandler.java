package org.bundlemaker.core.ui.handler;

import java.util.Set;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.ui.validators.NonEmptyStringValidator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

public class CreateNewGroupHandler extends AbstractCreateGroupOrModuleHandler {

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.handler.AbstractCreateGroupOrModuleHandler#createArtifact(org.eclipse.swt.widgets.Shell,
   * org.bundlemaker.core.analysis.IGroupAndModuleContainer)
   */
  @Override
  protected IBundleMakerArtifact createArtifact(Shell shell, IGroupAndModuleContainer groupAndModuleContainer) {
    // Create Validator
    Set<String> existingArtifactNames = getExistingArtifactNames(groupAndModuleContainer);
    GroupNameValidator groupNameValidator = new GroupNameValidator(existingArtifactNames);

    String preset = getUniqueArtifactName(groupAndModuleContainer, "GROUP", null);

    // JFace Input Dialog
    InputDialog dlg = new InputDialog(shell, "Create new Group", "Please enter the name of new Group", preset,
        groupNameValidator);

    if (dlg.open() != Window.OK) {
      // canceled
      return null;
    }

    // we have to use "getOrCreateGroup" to prevent duplicate groups
    IGroupArtifact newArtifact = groupAndModuleContainer.getOrCreateGroup(new Path(dlg.getValue()));

    //
    return newArtifact;
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
