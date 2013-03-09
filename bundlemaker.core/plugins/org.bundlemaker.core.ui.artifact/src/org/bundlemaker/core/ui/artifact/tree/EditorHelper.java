package org.bundlemaker.core.ui.artifact.tree;

import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.artifact.cnf.ResourceArtifactEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class EditorHelper {

  public static void openArtifactInEditor(IResourceArtifact resourceArtifact) {

    try {
      final IEditorPart editorPart = getActiveWorkbenchPage().openEditor(
          new ResourceArtifactEditorInput(resourceArtifact),
          "org.bundlemaker.core.ui.editors.sourceViewerEditor");

      CommonNavigatorUtils.activateCommonNavigator(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID);

    } catch (PartInitException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
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
