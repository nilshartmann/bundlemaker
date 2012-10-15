package org.bundlemaker.core.ui.transformation.handlers;

import java.net.URL;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.transformations.script.ITransformationScript;
import org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler;
import org.bundlemaker.core.ui.transformation.Activator;
import org.bundlemaker.core.ui.transformation.console.TransformationScriptConsoleFactory;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class RunTransformationScriptHandler extends AbstractArtifactBasedHandler {
  /**
   * The constructor.
   */
  public RunTransformationScriptHandler() {
  }

  protected void doExecute(ExecutionEvent event) throws Exception {

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler#execute(org.eclipse.core.commands.ExecutionEvent,
   * java.util.List)
   */
  @Override
  protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts) throws Exception {
    if (selectedArtifacts.size() == 0) {
      return;
    }

    IBundleMakerArtifact selectedArtifact = selectedArtifacts.get(0);
    final IRootArtifact rootArtifact = selectedArtifact.getRoot();

    IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("transformation-script");

    IFolder binFolder = (IFolder) project.findMember("bin");
    if (binFolder == null) {
      IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
      MessageDialog.openInformation(window.getShell(), "Transformation", "Bin-Folder not found");

    }

    String urlString = binFolder.getLocationURI().toURL().toString() + "/";
    URL url = new URL(urlString);

    // URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { url });

    TransformationScriptConsoleFactory.showConsole();

    TransformationScriptClassLoader classLoader = TransformationScriptClassLoader.createBundleClassLoaderFor(Activator
        .getDefault().getBundle(), new URL[] { url });
    Class<?> loadClass = classLoader.loadClass("org.bm.transform.TestScript");
    Object object = loadClass.newInstance();
    System.out.println("object: " + object);
    ITransformationScript transformationScript = (ITransformationScript) object;
    TransformationScriptLogger logger = new TransformationScriptLogger();
    try {
      transformationScript.transform(logger, rootArtifact);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    refreshProjectExplorer(rootArtifact);
  }
}
