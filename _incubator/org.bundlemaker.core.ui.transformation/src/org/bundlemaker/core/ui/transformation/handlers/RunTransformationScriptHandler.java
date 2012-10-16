package org.bundlemaker.core.ui.transformation.handlers;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.transformations.script.ITransformationScript;
import org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler;
import org.bundlemaker.core.ui.transformation.Activator;
import org.bundlemaker.core.ui.transformation.console.TransformationScriptConsoleFactory;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.dialogs.ITypeInfoFilterExtension;
import org.eclipse.jdt.ui.dialogs.ITypeInfoRequestor;
import org.eclipse.jdt.ui.dialogs.TypeSelectionExtension;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.progress.IProgressService;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class RunTransformationScriptHandler extends AbstractArtifactBasedHandler {

  private final static String TRANSFORMATION_SCRIPT_TYPE_NAME = ITransformationScript.class.getName();

  /**
   * The constructor.
   */
  public RunTransformationScriptHandler() {
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

    IType transformationScriptType = selectTransformationScript(HandlerUtil.getActiveShell(event));

    if (transformationScriptType == null) {
      return;
    }

    IBundleMakerArtifact selectedArtifact = selectedArtifacts.get(0);
    final IRootArtifact rootArtifact = selectedArtifact.getRoot();

    // selectTransformationScript(rootArtifact.getModularizedSystem().getBundleMakerProject());

    IProject project = transformationScriptType.getResource().getProject();

    IJavaProject javaProject = JavaCore.create(project);

    String[] classpath = JavaRuntime.computeDefaultRuntimeClassPath(javaProject);
    List<URL> urls = new LinkedList<URL>();
    for (String classpathEntry : classpath) {
      File file = new File(classpathEntry);
      if (file.exists()) {
        URL url = file.toURI().toURL();
        urls.add(url);
      }
    }

    TransformationScriptConsoleFactory.showConsole();

    TransformationScriptClassLoader classLoader = TransformationScriptClassLoader.createBundleClassLoaderFor(Activator
        .getDefault().getBundle(), urls.toArray(new URL[0]));
    Class<?> loadClass = classLoader.loadClass(transformationScriptType.getFullyQualifiedName());
    Object object = loadClass.newInstance();
    ITransformationScript transformationScript = (ITransformationScript) object;
    TransformationScriptLogger logger = new TransformationScriptLogger();
    try {
      transformationScript.transform(logger, rootArtifact);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    refreshProjectExplorer(rootArtifact);
  }

  private IType selectTransformationScript(final Shell shell) {

    IRunnableContext runnableContext = new IRunnableContext() {
      @Override
      public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable)
          throws InvocationTargetException, InterruptedException {
        IProgressService manager = PlatformUI.getWorkbench().getProgressService();
        manager.busyCursorWhile(runnable);
      }
    };
    IJavaSearchScope scope = SearchEngine.createWorkspaceScope();
    try {
      SelectionDialog typeDialog = JavaUI.createTypeDialog(shell, runnableContext, scope,
          IJavaElementSearchConstants.CONSIDER_CLASSES, false, "*TransformationScript",
          new TransformationScriptTypeDialogExtension());
      if (typeDialog.open() != Window.OK) {
        return null;
      }

      Object[] result = typeDialog.getResult();
      IType transformationScriptType = (IType) result[0];
      return transformationScriptType;

    } catch (JavaModelException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  class TransformationScriptTypeDialogExtension extends TypeSelectionExtension {
    @Override
    public ISelectionStatusValidator getSelectionValidator() {
      return new ISelectionStatusValidator() {
        @Override
        public IStatus validate(Object[] selection) {
          if (selection.length == 1) {
            try {
              IType type = (IType) selection[0];
              ITypeHierarchy hierarchy = type.newSupertypeHierarchy(new NullProgressMonitor());

              IType[] types = hierarchy.getAllSuperInterfaces(type);
              for (IType curr : types) {
                String fullyQualifiedName = curr.getFullyQualifiedName('.');
                System.out.println("fullyQualifiedName: " + fullyQualifiedName);
                if (TRANSFORMATION_SCRIPT_TYPE_NAME.equals(fullyQualifiedName)) { //$NON-NLS-1$
                  return Status.OK_STATUS;
                }

              }

            } catch (JavaModelException e) {
              // TODO
              e.printStackTrace();
              return Status.CANCEL_STATUS;
            }
          }
          return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Type is not an instanceof "
              + TRANSFORMATION_SCRIPT_TYPE_NAME);
        }

      };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jdt.ui.dialogs.TypeSelectionExtension#getFilterExtension()
     */
    @Override
    public ITypeInfoFilterExtension getFilterExtension() {
      return new ITypeInfoFilterExtension() {

        @Override
        public boolean select(ITypeInfoRequestor typeInfoRequestor) {
          // exclude classes coming from bundlemaker itself
          return (!typeInfoRequestor.getPackageName().startsWith("org.bundlemaker.core"));
        }
      };
    }

  }

}
