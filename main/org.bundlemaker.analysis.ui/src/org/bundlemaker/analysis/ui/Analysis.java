package org.bundlemaker.analysis.ui;

import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.ui.editor.GenericEditor;
import org.bundlemaker.analysis.ui.internal.Activator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.part.NullEditorInput;

/**
 * Common functionality used by BundleMaker UI analysis features
 * 
 * @author Nils Hartmann
 */
@SuppressWarnings("restriction")
public class Analysis {

  /**
   * The id for the Project-Explorer Artifact selection provider
   */
  public static final String PROJECT_EXPLORER_ARTIFACT_SELECTION_PROVIDER_ID = "org.bundlemaker.ui.navigator.selectionprovider";

  /**
   * The id of the Eclipse project explorer
   */
  public static final String PROJECT_EXPLORER_VIEW_ID                        = "org.eclipse.ui.navigator.ProjectExplorer";

  private static Analysis    _instance;

  private IEditorInput       nullInputEditor                                 = new NullEditorInput();

  /**
   * Returns the singleton instance of analysis.
   * 
   * <p>
   * There is exactly one Analysis instance per Eclipse instance
   * 
   * @return
   */
  public static Analysis instance() {
    if (_instance == null) {
      _instance = new Analysis();
    }

    return _instance;
  }

  /**
   * Use {@link #instance()} to retrieve the singleton instance of this class
   */
  private Analysis() {
    //
  }

  /**
   * Returns the Icon-Image that should be used for the given artifact.
   * 
   * <p>
   * If no artifact is given, <tt>null</tt> is returned.
   * 
   * @param artifact
   * @return
   */
  public Image getIconForArtifact(IArtifact artifact) {
    if (artifact == null) {
      return null;
    }

    return Activator.getDefault().getIcon(artifact.getType().getKuerzel());
  }

  /**
   * <p>
   * show the selected specified artifacts in the generic editor
   * </p>
   * <p>
   * If a dependencyPartId is specified, this dependency part will be brought to top
   * 
   * @param dependencyPartId
   *          the dependency part that should be openend or null
   * @param artifacts
   *          the artifacts to show. must not be null.
   */
  public void showInGenericEditor(String dependencyPartId, List<IArtifact> artifacts) {
    IWorkbenchPage page = getActiveWorkbenchPage();
    if (page != null) {
      try {
        IEditorPart editorPart = page.openEditor(nullInputEditor, GenericEditor.ID);
        if (!(editorPart instanceof GenericEditor)) {
          System.err.println("EditorPart " + editorPart + " is not a GenericEditor?");
          return;
        }

        GenericEditor genericEditor = (GenericEditor) editorPart;
        genericEditor.useArtifacts(artifacts);

        if (dependencyPartId != null) {
          genericEditor.openDependencyPart(dependencyPartId);
        }

      } catch (PartInitException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Gets the active {@link IWorkbenchPage}.
   * 
   * @return the active IWorkbenchPage or null
   */
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
