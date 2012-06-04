package org.bundlemaker.core.ui.handler;

import java.util.Set;

import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
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

    String preset = getUniqueArtifactName(groupAndModuleContainer, "GROUP", null);

    // prompt user for name of new group
    String newGroupName = getGroupName(shell, groupAndModuleContainer, preset, true);

    if (newGroupName == null) {
      return null;
    }

    System.out.println("Create new Group: " + newGroupName);

    // we have to use "getOrCreateGroup" to prevent duplicate groups
    Path newGroupPath = new Path(newGroupName);
    IGroupArtifact newArtifact = groupAndModuleContainer.getOrCreateGroup(newGroupPath);

    System.out.println("New Group Artifact: " + newArtifact);

    System.out.println(" ======== NACH CREATEGROUP: ======================== ");
    ArtifactUtils.dumpArtifact(newArtifact.getParent(IRootArtifact.class));
    System.out.println(" ======== ENDE CREATEGROUP ======================== ");

    //
    return newArtifact;
  }

  /**
   * Prompts the user for the name of a (new) group
   * 
   * @param shell
   * @param parentContainer
   *          the container the (new) group belongs to
   * @param preset
   *          the preset that is displayed to the user
   * @return the entered name or null if the user has canceled the dialog
   */
  public static String getGroupName(Shell shell, IGroupAndModuleContainer parentContainer, String preset,
      boolean newGroup) {

    // Create Validator
    Set<String> existingArtifactNames = getExistingArtifactNames(parentContainer);
    existingArtifactNames.remove(preset);
    GroupNameValidator groupNameValidator = new GroupNameValidator(existingArtifactNames);

    // JFace Input Dialog
    InputDialog dlg = new InputDialog(shell, //
        (newGroup ? "Create new Group" : "Rename Group"), //
        (newGroup ? "Please enter the name of new Group" : "Please enter new name for Group " + preset), //
        preset, //
        groupNameValidator);

    if (dlg.open() != Window.OK) {
      // canceled
      return null;
    }

    return dlg.getValue();

  }

  static class GroupNameValidator extends NonEmptyStringValidator {
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
