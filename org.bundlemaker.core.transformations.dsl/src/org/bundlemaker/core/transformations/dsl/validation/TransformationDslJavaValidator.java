package org.bundlemaker.core.transformations.dsl.validation;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationDslPackage;
import org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationModel;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.validation.Check;
 

/**
 * TODO this should goto dsl.ui module
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public class TransformationDslJavaValidator extends AbstractTransformationDslJavaValidator {
  
  @Check
  public void warnIfBundleMakerProjectIsNotOpen(TransformationModel model) {
    
    IBundleMakerProject bundleMakerProject = getBundleMakerProject(model);
    if (bundleMakerProject == null) {
      return;
    }
    if (bundleMakerProject.getState() != BundleMakerProjectState.READY) {
      warning("Associated BundleMaker project '" + bundleMakerProject.getProject().getName() +  "' is not open", model, TransformationDslPackage.TRANSFORMATION_MODEL, "BMProjectNotOpen");
    }
    
    
  }
  
  public static IProject getOwningProject(EObject object) {
    URI uri = object.eResource().getURI();
    String platformString = uri.toPlatformString(false);
    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    IFile file = root.getFile(new Path(platformString));
    IProject project = file.getProject();
    return project;
  }

  public static IBundleMakerProject getBundleMakerProject(EObject object) {
    try {
      IProject project = getOwningProject(object);
      if (BundleMakerCore.isBundleMakerProject(project)) {
        return BundleMakerCore.getBundleMakerProject(project, null);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

//	@Check
//	public void checkGreetingStartsWithCapital(Greeting greeting) {
//		if (!Character.isUpperCase(greeting.getName().charAt(0))) {
//			warning("Name should start with a capital", MyDslPackage.GREETING__NAME);
//		}
//	}

}
