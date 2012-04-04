package org.bundlemaker.core.ui.utils;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.ArtifactType;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.modules.modifiable.MovableUnit;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.navigator.CommonNavigator;

public class EditorHelper {

  public static void open(IBundleMakerArtifact artifact) {

    // TODO: MOVE
    try {

      CommonNavigator commonNavigator = CommonNavigatorUtils.findCommonNavigator(IPageLayout.ID_PROJECT_EXPLORER);

      commonNavigator.setLinkingEnabled(false);

      IResourceArtifact resourceArtifact = (IResourceArtifact) artifact.getParent(ArtifactType.Resource);
      IResource resource = resourceArtifact.getAssociatedResource();
      IMovableUnit movableUnit = MovableUnit.createFromResource(resource, resourceArtifact.getModularizedSystem());
      IResource sourceResource = movableUnit.hasAssociatedSourceResource() ? movableUnit.getAssociatedSourceResource()
          : resource;

      IBundleMakerProject bundleMakerProject = resourceArtifact.getRoot().getModularizedSystem()
          .getBundleMakerProject();
      IJavaProject javaProject = getAssociatedJavaProject(bundleMakerProject.getProject());
      IJavaElement javaElement = javaProject.findElement(new Path(sourceResource.getPath()));
      JavaUI.openInEditor(javaElement);
    } catch (PartInitException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JavaModelException e) {
      // TODO Auto-generated catch block
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
}
