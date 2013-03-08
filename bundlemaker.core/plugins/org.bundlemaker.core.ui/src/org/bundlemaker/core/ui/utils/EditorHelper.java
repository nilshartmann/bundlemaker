package org.bundlemaker.core.ui.utils;

import org.bundlemaker.core.ui.internal.Activator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.part.NullEditorInput;

public class EditorHelper {

  public static void openEditor(String editorId, IEditorInput editorInput) {
    IWorkbenchPage page = getActiveWorkbenchPage();
    if (page != null) {
      try {
        page.openEditor(editorInput, editorId);
        // if (!(editorPart instanceof DependencyViewEditor)) {
        // System.err.println("EditorPart " + editorPart + " is not a DependencyViewEditor?");
        // return;
        // }
      } catch (PartInitException e) {
        IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID,
            "Could not Open Editor '" + editorId + "': " + e, e);
        Activator.getDefault().getLog().log(status);
      }
    }

  }

  @SuppressWarnings("restriction")
  public static IEditorInput newNullEditorInput() {
    return new NullEditorInput();
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
