package org.bundlemaker.core.ui.transformation.handlers;

import java.net.URL;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.transformations.script.ITransformationScript;
import org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler;
import org.bundlemaker.core.ui.transformation.Activator;
import org.bundlemaker.core.ui.transformation.console.TransformationScriptConsoleFactory;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;

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

    // selectTransformationScript(rootArtifact.getModularizedSystem().getBundleMakerProject());

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

  /**
   * Asks the user to select a transformation script from the workspace.
   * 
   * <p>
   * The dialog that will be opened only displays <tt>.bmt</tt>-transformation scripts
   * 
   * @param project
   * @return the selected BMT-file or null if no file has been selected
   */
  private IFile selectTransformationScript(IBundleMakerProject project) {
    ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(new Shell(), new WorkbenchLabelProvider(),
        new WorkbenchContentProvider());
    dialog.setHelpAvailable(false);
    dialog.setTitle("Choose transformation script");
    dialog.setMessage("Choose a transformation script that will be applied on " + project.getProject().getName());

    // set the filter that filters bmt-files and their containing directories
    dialog.addFilter(new ViewerFilter() {

      @Override
      public boolean select(Viewer viewer, Object parentElement, Object element) {
        System.out.println("element: " + element + " javaElement: " + (element instanceof IJavaElement));
        return true;

      }
    });

    // set the validator that checks that only IFile instances can be selected
    dialog.setValidator(new ISelectionStatusValidator() {

      private IStatus fgErrorStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "");

      private IStatus fgOKStatus    = new Status(IStatus.OK, Activator.PLUGIN_ID, "");

      @Override
      public IStatus validate(Object[] selection) {

        for (Object object : selection) {
          if (!(object instanceof IFile)) {
            return fgErrorStatus;
          }
        }
        return fgOKStatus;
      }
    });
    dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
    dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));
    dialog.setAllowMultiple(false);

    // open the dialog and return the selected IFile
    if (dialog.open() == Window.OK) {
      Object firstResult = dialog.getFirstResult();
      if (firstResult == null) {
        return null;
      }
      IFile selectedScript = (IFile) firstResult;
      return selectedScript;
    }
    return null;
  }
}
