package org.bundlemaker.analysis.ui;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.ui.editor.DependencyPart;
import org.bundlemaker.analysis.ui.editor.GenericEditor;
import org.bundlemaker.analysis.ui.internal.Activator;
import org.bundlemaker.analysis.ui.internal.AnalysisContext;
import org.bundlemaker.analysis.ui.view.table.DependencyTreeTableView;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
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
public class Analysis {

  private static Analysis        _instance;

  private final IAnalysisContext _analysisContext;

  @SuppressWarnings("restriction")
  private IEditorInput           nullInputEditor = new NullEditorInput();

  /**
   * Returns the singleton instnace of analysis.
   * 
   * <p>
   * There is exactly one Analysis instance per Eclispe instance
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
   * Returns the {@link DependencyTreeTableView} or null if it does not exists (yet)
   * 
   * @return
   */
  public DependencyTreeTableView getDependencyTreeTableView() {
    IWorkbenchPage workbenchPage = getActiveWorkbenchPage();

      if (workbenchPage != null) {
        return (DependencyTreeTableView) workbenchPage.findView(DependencyTreeTableView.ID);
      }

    return null;
  }

  /**
   * Opens the {@link DependencyTreeTableView}
   */
  public void openDependencyTreeTableView() {
    IWorkbenchPage workbenchPage = getActiveWorkbenchPage();
        if (workbenchPage != null) {
      try {
          workbenchPage.showView(DependencyTreeTableView.ID);
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