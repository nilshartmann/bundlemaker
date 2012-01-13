package org.bundlemaker.analysis.ui;

import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.model.dependencies.IDependencyGraph;
import org.bundlemaker.analysis.ui.dependencies.IDependencyGraphService;
import org.bundlemaker.analysis.ui.dependencies.internal.DependencyGraphService;
import org.bundlemaker.analysis.ui.editor.DependencyPart;
import org.bundlemaker.analysis.ui.editor.GenericEditor;
import org.bundlemaker.analysis.ui.internal.Activator;
import org.bundlemaker.analysis.ui.internal.AnalysisContext;
import org.bundlemaker.analysis.ui.internal.selection.ArtifactSelectionService;
import org.bundlemaker.analysis.ui.internal.selection.DependencySelectionService;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionService;
import org.bundlemaker.analysis.ui.selection.IDependencySelectionService;
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
 * 
 */
@SuppressWarnings("restriction")
public class Analysis {

  /**
   * The id for the Project-Explorer Artifact selection provider
   */
  public static final String                PROJECT_EXPLORER_ARTIFACT_SELECTION_PROVIDER_ID = "org.bundlemaker.ui.navigator.selectionprovider";

  /**
   * The id of the Eclipse project explorer
   */
  public static final String                PROJECT_EXPLORER_VIEW_ID                        = "org.eclipse.ui.navigator.ProjectExplorer";

  private static Analysis                   _instance;

  private final IAnalysisContext            _analysisContext;

  private final IArtifactSelectionService   _artifactSelectionService;

  /**
   * The {@link IDependencySelectionService} that manages selections of {@link IDependency} objects in the IDE
   */
  private final IDependencySelectionService _dependencySelectionService;

  /**
   * The {@link IDependencyGraphService} instance for getting {@link IDependencyGraph}-instances
   */
  private final IDependencyGraphService     _dependencyGraphService;

  private IEditorInput                      nullInputEditor                                 = new NullEditorInput();

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
    _analysisContext = new AnalysisContext();

    // Create the ArtifactSelectionService
    _artifactSelectionService = new ArtifactSelectionService();

    // Create the DependencySelectionService
    _dependencySelectionService = new DependencySelectionService();

    // Create the DependencyGraphService
    _dependencyGraphService = new DependencyGraphService();
  }

  /**
   * Returns the {@link IAnalysisContext} instance
   * 
   * @return
   */
  public IAnalysisContext getContext() {
    return _analysisContext;
  }

  /**
   * Return the {@link IArtifactSelectionService} instance.
   * 
   * @return the {@link IArtifactSelectionService}. Never null
   */
  public IArtifactSelectionService getArtifactSelectionService() {
    return _artifactSelectionService;
  }

  /**
   * Returns the single {@link IDependencySelectionService} instance
   * 
   * @return {@link IDependencySelectionService}. Never null.
   */
  public IDependencySelectionService getDependencySelectionService() {
    return _dependencySelectionService;
  }

  /**
   * <p>
   * Returns the single {@link IDependencyGraphService} instance
   * </p>
   * 
   * @return The {@link IDependencyGraphService}. Never null.
   */
  public IDependencyGraphService getDependencyGraphService() {
    return this._dependencyGraphService;
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

  // /**
  // * Returns the {@link DependencyTreeTableView} or null if it does not exists (yet)
  // *
  // * @return
  // */
  // public DependencyTreeTableView getDependencyTreeTableView() {
  // IWorkbenchPage workbenchPage = getActiveWorkbenchPage();
  //
  // if (workbenchPage != null) {
  // return (DependencyTreeTableView) workbenchPage.findView(DependencyTreeTableView.ID);
  // }
  //
  // return null;
  // }
  //
  /**
   * Opens the {@link DependencyTreeTableView}
   */
  public void openDependencyTreeTableView() {
    IWorkbenchPage workbenchPage = getActiveWorkbenchPage();
    if (workbenchPage != null) {
      try {
        workbenchPage.showView("org.bundlemaker.analysis.ui.view.table.DependencyTreeTableView");
      } catch (PartInitException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Opens the {@link GenericEditor} that is displaying the contributed {@link DependencyPart DependencyParts}
   */
  public void openGenericEditor() {
    IWorkbenchPage page = getActiveWorkbenchPage();
    if (page != null) {
      try {
        page.openEditor(nullInputEditor, GenericEditor.ID);
      } catch (PartInitException e) {
        e.printStackTrace();
      }
    }
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

  // public DependencyPart getGenericPart() {
  // IWorkbenchPage page = getActiveWorkbenchPage();
  // if (page != null) {
  // try {
  // IEditorReference[] editorReferences = page.findEditors(null, GenericEditor.ID, IWorkbenchPage.MATCH_ID);
  // if (editorReferences.length>0) {
  // IEditorReference reference = editorReferences[0];
  // return reference.getEditor(false)
  // }
  // return editorReferences
  // )
  // page.openEditor(nullInputEditor, GenericEditor.ID);
  // } catch (PartInitException e) {
  // e.printStackTrace();
  // }
  // }
  //
  // return null;
  // }

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
