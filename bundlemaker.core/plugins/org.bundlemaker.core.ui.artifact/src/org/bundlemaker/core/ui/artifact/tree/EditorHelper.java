package org.bundlemaker.core.ui.artifact.tree;

import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.ui.artifact.Activator;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.artifact.cnf.ResourceArtifactEditorInput;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;

public class EditorHelper {

  /**
   * <p>
   * </p>
   * 
   * @param resourceArtifact
   */
  public static void openArtifactInEditor(IResourceArtifact resourceArtifact) {

    // return immediately if resource artifact is null
    if (resourceArtifact == null) {
      return;
    }

    // show warning and return immediately if resource artifact has not attached source
    if (!resourceArtifact.hasAssociatedSourceResource()) {

      //
      MessageDialog.openWarning(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
          "No source available.",
          String.format("The selected artifact '%s' has no attached source.", resourceArtifact.getName()));

      //
      return;
    }

    // open the editor part
    try {

      getActiveWorkbenchPage().openEditor(
          new ResourceArtifactEditorInput(resourceArtifact),
          "org.bundlemaker.core.ui.editors.sourceViewerEditor");

      CommonNavigatorUtils.activateCommonNavigator(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID);

    } catch (PartInitException e) {
      StatusManager.getManager().handle(
          new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Opening Editor failed: " + e, e),
          StatusManager.LOG | StatusManager.BLOCK);
    }
  }

  private static IWorkbenchPage getActiveWorkbenchPage() {
    IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    if (workbenchWindow != null) {
      IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
      if (workbenchPage != null) {
        return workbenchPage;
      }
    }
    return null;
  }
}
