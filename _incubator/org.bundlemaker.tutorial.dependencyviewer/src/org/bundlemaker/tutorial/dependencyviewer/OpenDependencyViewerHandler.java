package org.bundlemaker.tutorial.dependencyviewer;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.part.NullEditorInput;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class OpenDependencyViewerHandler extends AbstractArtifactBasedHandler {

  private IEditorInput nullInputEditor = new NullEditorInput();

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.analysis.ui.handlers.AbstractArtifactBasedHandler#execute(org.eclipse.core.commands.ExecutionEvent,
   * java.util.List)
   */
  @Override
  protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts) throws Exception {
    IWorkbenchPage page = getActiveWorkbenchPage();
    if (page != null) {
      try {
        IEditorPart editorPart = page.openEditor(nullInputEditor, DependencyViewEditor.DEPENDENCY_VIEW_EDITOR_ID);
        if (!(editorPart instanceof DependencyViewEditor)) {
          System.err.println("EditorPart " + editorPart + " is not a DependencyViewEditor?");
          return;
        }
      } catch (PartInitException e) {
        e.printStackTrace();
      }
    }
  }

  private IWorkbenchPage getActiveWorkbenchPage() {
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
