package org.bundlemaker.core.ui.utils;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.ILinkHelper;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerFileLinkHelper implements ILinkHelper {

  @Override
  public void activateEditor(IWorkbenchPage page, IStructuredSelection selection) {

    // return immediately if the selection is null or empty
    if (selection == null || selection.isEmpty())
      return;

    // get the first element
    Object element = selection.getFirstElement();

    //
    if (element instanceof IBundleMakerArtifact) {

      //
      IBundleMakerArtifact bundleMakerArtifact = (IBundleMakerArtifact) element;

      //
      IResourceArtifact resourceArtifact = bundleMakerArtifact instanceof IResourceArtifact ? (IResourceArtifact) bundleMakerArtifact
          : (IResourceArtifact) bundleMakerArtifact.getParent(IResourceArtifact.class);

      //
      if (resourceArtifact == null) {
        return;
      }

      //
      IEditorReference editorReference = getEditorReference(page, resourceArtifact);

      //
      if (editorReference != null) {

        //
        EditorHelper.open(resourceArtifact, null);
      }
    }
  }

  @Override
  public IStructuredSelection findSelection(IEditorInput input) {

    //
    if (input instanceof BundleMakerClassFileEditorInput) {

      BundleMakerClassFileEditorInput bmClassFileEditorInput = (BundleMakerClassFileEditorInput) input;

      IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
      if (page != null) {

        //
        IViewPart view = page.findView(IPageLayout.ID_PROJECT_EXPLORER);

        //
        if (view != null && view instanceof CommonNavigator) {
          CommonNavigator cn = (CommonNavigator) view;
          return new StructuredSelection(bmClassFileEditorInput.getResourceArtifact());
        }
      }
    }

    // System.out.println("findSelection " + input);
    //
    // IJavaElement element = JavaUI.getEditorInputJavaElement(input);
    // if (element == null) {
    // IFile file = ResourceUtil.getFile(input);
    // if (file != null) {
    // element = JavaCore.create(file);
    // }
    // }
    //
    // System.out.println(element.getElementName());
    // System.out.println(element.getJavaProject().getProject().getName());

    return StructuredSelection.EMPTY;
  }

  /**
   * <p>
   * </p>
   */
  private IEditorReference getEditorReference(IWorkbenchPage page, IResourceArtifact resourceArtifact) {

    try {

      // IEditorPart actEditor = page.getActiveEditor();
      for (IEditorReference iEditorReference : page.getEditorReferences()) {

        //
        if (iEditorReference.getEditorInput() instanceof BundleMakerClassFileEditorInput) {

          //
          BundleMakerClassFileEditorInput input = (BundleMakerClassFileEditorInput) iEditorReference.getEditorInput();

          //
          if (input.getResourceArtifact().equals(resourceArtifact)) {
            return iEditorReference;
          }
        }
      }
    } catch (PartInitException e) {

      // return false
      return null;
    }

    // return false
    return null;
  }
}
