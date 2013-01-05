package org.bundlemaker.core.ui.utils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.modules.modifiable.MovableUnit;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.ui.internal.Activator;
import org.bundlemaker.core.ui.referencedetails.IReferenceDetailParser;
import org.bundlemaker.core.ui.referencedetails.ReferenceDetailParser;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.Position;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.part.NullEditorInput;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

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

  /**
   * Opens an Editor for the specified artifact. If toArtifact is set, references to this artifact are marked in the
   * editor's ruler.
   * 
   * @param artifact
   * @param toArtifact
   */
  public static void openArtifactInEditor(IBundleMakerArtifact artifact, IBundleMakerArtifact toArtifact) {

    try {
      // TODO: MOVE
      // CommonNavigator commonNavigator = CommonNavigatorUtils.findCommonNavigator(IPageLayout.ID_PROJECT_EXPLORER);
      // commonNavigator.setLinkingEnabled(false);

      IReferenceDetailParser detailParser = new ReferenceDetailParser();

      IResourceArtifact resourceArtifact = artifact instanceof IResourceArtifact ? (IResourceArtifact) artifact
          : (IResourceArtifact) artifact.getParent(IResourceArtifact.class);

      IResource resource = resourceArtifact.getAssociatedResource();
      IMovableUnit movableUnit = MovableUnit.createFromResource(resource, resourceArtifact.getModularizedSystem());

      //
      IResource sourceResource = movableUnit.getAssociatedBinaryResources().get(0);

      IBundleMakerProject bundleMakerProject = resourceArtifact.getRoot().getModularizedSystem()
          .getBundleMakerProject();
      IJavaProject javaProject = getAssociatedJavaProject(bundleMakerProject.getProject());
      IJavaElement javaElement = javaProject.findElement(new Path(sourceResource.getPath()));

      if (javaElement instanceof IClassFile) {

        //
        BundleMakerClassFileEditorInput editorInput = new BundleMakerClassFileEditorInput((IClassFile) javaElement,
            resourceArtifact);

        //
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        EditorPart editorPart = (EditorPart) page.openEditor(editorInput,
            "org.bundlemaker.core.ui.utils.BundleMakerClassFileEditor");

        // EditorPart editorPart = (EditorPart) page.openEditor(editorInput, "org.eclipse.jdt.ui.ClassFileEditor");

        try {
          Method method = EditorPart.class.getDeclaredMethod("setPartName", String.class);
          method.setAccessible(true);
          method.invoke(editorPart, artifact.getName());
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        //
        if (toArtifact != null) {

          //
          Map<String, List<Position>> positions = detailParser.parseReferencePositions(sourceResource,
              artifact.getModularizedSystem());

          //
          if (positions.containsKey(toArtifact.getQualifiedName())) {
            ReferenceAnnotationModel.attach((ITextEditor) editorPart, toArtifact.getQualifiedName(),
                positions.get(toArtifact.getQualifiedName()));
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static IJavaProject getAssociatedJavaProject(IProject bundleMakerProject) {

    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    IProject associatedProject = root.getProject(getAssociatedJavaProjectName(bundleMakerProject));

    IJavaProject javaProject = JavaCore.create(associatedProject);

    try {
      javaProject.open(null);
    } catch (JavaModelException e) {
      throw new RuntimeException(e.getMessage());
    }

    return javaProject;
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @return
   */
  private static String getAssociatedJavaProjectName(IProject project) {
    return project.getName() + "$bundlemakerJdt";
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
