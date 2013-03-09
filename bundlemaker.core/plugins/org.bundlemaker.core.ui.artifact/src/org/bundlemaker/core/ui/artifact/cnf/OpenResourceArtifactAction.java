package org.bundlemaker.core.ui.artifact.cnf;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IResourceArtifactContent;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.artifact.tree.EditorHelper;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;

/**
 * @since 3.2
 * 
 */
public class OpenResourceArtifactAction extends Action {

  private ISelectionProvider provider;

  private IResourceArtifact  _artifact;

  /**
   * Construct the OpenPropertyAction with the given page.
   * 
   * @param p
   *          The page to use as context to open the editor.
   * @param selectionProvider
   *          The selection provider
   */
  public OpenResourceArtifactAction(IWorkbenchPage p, ISelectionProvider selectionProvider) {
    setText("Open Resource"); //$NON-NLS-1$
    provider = selectionProvider;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.action.Action#isEnabled()
   */
  @Override
  public boolean isEnabled() {
    ISelection selection = provider.getSelection();
    if (!selection.isEmpty()) {
      IStructuredSelection sSelection = (IStructuredSelection) selection;
      if (sSelection.size() == 1
          &&
          (sSelection.getFirstElement() instanceof IResourceArtifact || sSelection.getFirstElement() instanceof IResourceArtifactContent))
      {
        //
        IBundleMakerArtifact bundleMakerArtifact = (IBundleMakerArtifact) sSelection.getFirstElement();

        //
        _artifact = bundleMakerArtifact instanceof IResourceArtifact ? (IResourceArtifact) bundleMakerArtifact
            : bundleMakerArtifact
                .getParent(IResourceArtifact.class);

        //
        return true;
      }
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {

    EditorHelper.openArtifactInEditor(_artifact);
    CommonNavigatorUtils.activateCommonNavigator(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID);

    /*
     * In production code, you should always externalize strings, but this is an example.
     */
    // try {

    // IFile propertiesFile = data.getFile();
    // IEditorPart editor = IDE.openEditor(page, propertiesFile);
    //
    // if (editor instanceof ITextEditor) {
    // ITextEditor textEditor = (ITextEditor) editor;
    //
    // IDocumentProvider documentProvider =
    // textEditor.getDocumentProvider();
    // IDocument document =
    // documentProvider.getDocument(editor.getEditorInput());
    //
    // FindReplaceDocumentAdapter searchAdapter =
    // new FindReplaceDocumentAdapter(document);
    //
    // try {
    //            String searchText = data.getName() + "="; //$NON-NLS-1$ 
    // IRegion region = searchAdapter.find(0,
    // searchText,
    // true /* forwardSearch */,
    // true /* caseSensitive */,
    // false /* wholeWord */,
    // false /* regExSearch */);
    //
    // ((ITextEditor) editor).selectAndReveal(region.getOffset(), region.getLength());
    //
    // } catch (BadLocationException e) {
    //            Activator.logError(0, "Could not open property!", e); //$NON-NLS-1$
    //            MessageDialog.openError(Display.getDefault().getActiveShell(), "Error Opening Property", //$NON-NLS-1$
    //                "Could not open property!"); //$NON-NLS-1$
    // }
    // return;
    // }
    // }
    // } catch (PartInitException e) {
    //      Activator.logError(0, "Could not open property!", e); //$NON-NLS-1$
    //      MessageDialog.openError(Display.getDefault().getActiveShell(), "Error Opening Property", //$NON-NLS-1$
    //          "Could not open property!"); //$NON-NLS-1$
  }
}
