package org.bundlemaker.core.ui.editor.dsm.handlers;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.editor.dsm.DSMArtifactModelEditor;
import org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.part.NullEditorInput;

public abstract class AbstractDsmViewHandler extends AbstractArtifactBasedHandler {

  private static IEditorInput nullInputEditor = new NullEditorInput();

  @Override
  protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts) throws Exception {

    // get the artifacts that should be displayed in DSM View
    List<IBundleMakerArtifact> artifactsForDsmView = getArtifactsForDsmView(selectedArtifacts);

    // make sure the editor and views are visible
    openEditorAndViews(artifactsForDsmView);
  }

  protected List<IBundleMakerArtifact> getArtifactsForDsmView(List<IBundleMakerArtifact> selectedArtifacts) {
    return selectedArtifacts;
  }

  private void openEditorAndViews(List<IBundleMakerArtifact> selectedArtifacts) {
    IWorkbenchPage page = getActiveWorkbenchPage();
    if (page != null) {
      try {
        IEditorPart editorPart = page.openEditor(nullInputEditor, DSMArtifactModelEditor.DSM_EDITOR_ID);
        if (!(editorPart instanceof DSMArtifactModelEditor)) {
          System.err.println("EditorPart " + editorPart + " is not a DSMViewNeu?");
          return;
        }

        // DSMArtifactModelEditor genericEditor = (DSMArtifactModelEditor) editorPart;
        // genericEditor.setCurrentArtifactSelection(selectedArtifacts);

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
