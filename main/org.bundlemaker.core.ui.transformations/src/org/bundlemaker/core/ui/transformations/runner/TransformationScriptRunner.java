/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.transformations.runner;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.transformations.script.ITransformationScript;
import org.bundlemaker.core.transformations.script.TransformationModelConfiguration;
import org.bundlemaker.core.ui.transformations.Activator;
import org.bundlemaker.core.ui.transformations.console.TransformationScriptConsoleFactory;
import org.bundlemaker.core.ui.transformations.handlers.TransformationScriptClassLoader;
import org.bundlemaker.core.ui.transformations.handlers.TransformationScriptLogger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class TransformationScriptRunner implements IRunnableWithProgress {

  private final Shell                _shell;

  private final IType                _transformationScriptType;

  private final IBundleMakerArtifact _artifact;

  /**
   * @param shell
   * @param transformationScriptType
   */
  public TransformationScriptRunner(Shell shell, IBundleMakerArtifact artifact, IType transformationScriptType) {
    _shell = shell;
    _transformationScriptType = transformationScriptType;
    _artifact = artifact;
  }

  // public void runScript() {
  //
  // try {
  // doRunScript();
  // } catch (Exception ex) {
  //
  // // Show exception to user
  // StatusManager.getManager().handle(
  // new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to run script: " + ex, ex), StatusManager.SHOW);
  // }
  // }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

    try {
      doRunScript();
    } catch (Exception ex) {
      throw new InvocationTargetException(ex);
    }

  }

  // /*
  // * (non-Javadoc)
  // *
  // * @see org.eclipse.core.resources.WorkspaceJob#runInWorkspace(org.eclipse.core.runtime.IProgressMonitor)
  // */
  // @Override
  // public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
  // try {
  // doRunScript();
  // } catch (Exception ex) {
  // return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to run script: " + ex, ex);
  // }
  //
  // return Status.OK_STATUS;
  // }

  protected void doRunScript() throws Exception {

    // Instantiate the script
    ITransformationScript transformationScript = createTransformationScript();

    // Get the required artifact model configuration
    IAnalysisModelConfiguration artifactModelConfiguration = getAnalysisModelConfiguration(transformationScript);

    // Get an artifact model according to the configuration specified in the script
    IRootArtifact rootArtifact = _artifact.getModularizedSystem().getArtifactModel(artifactModelConfiguration);

    // Create a Logger that logs to the BundleMaker console
    TransformationScriptLogger logger = new TransformationScriptLogger();

    // Make sure the console with output is visible
    TransformationScriptConsoleFactory.showConsole();

    // Run the script
    try {
      transformationScript.transform(logger, rootArtifact);
    } catch (Exception ex) {
      Activator.getDefault().getLog()
          .log(new Status(Status.ERROR, Activator.PLUGIN_ID, "Execution of transformation script failed: " + ex, ex));

      MessageDialog.openError(_shell, "Transformation Script failed",
          "Execution of transformation script failed with Exception:\n\n" + ex + "\n\nSee Error Log for more details");
    }
  }

  /**
   * @return
   * @throws Exception
   */
  private ITransformationScript createTransformationScript() throws Exception {
    // Create the classloader
    ClassLoader classLoader = createScriptClassLoader();
    // Load the script's class
    Class<?> loadClass = classLoader.loadClass(_transformationScriptType.getFullyQualifiedName());

    // Instantiate
    Object object = loadClass.newInstance();
    ITransformationScript transformationScript = (ITransformationScript) object;

    return transformationScript;
  }

  private IAnalysisModelConfiguration getAnalysisModelConfiguration(ITransformationScript script) throws Exception {

    Method declaredTransformMethod = ITransformationScript.class.getDeclaredMethods()[0];

    Method scriptMethod = script.getClass().getMethod(declaredTransformMethod.getName(),
        declaredTransformMethod.getParameterTypes());
    TransformationModelConfiguration annotation = scriptMethod.getAnnotation(TransformationModelConfiguration.class);

    if (annotation == null) {
      return null;
    }

    AnalysisModelConfiguration artifactModelConfiguration = new AnalysisModelConfiguration(
        annotation.hierarchicalPackages(), annotation.contentType(), annotation.useVirtualModuleForMissingTypes());

    return artifactModelConfiguration;

  }

  private ClassLoader createScriptClassLoader() throws Exception {
    // Determine classpath. Note that classes from BundleMaker libraries
    // are always loaded first, regardless where the BM container is placed
    // in the project's classpath
    IProject project = _transformationScriptType.getResource().getProject();

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

    TransformationScriptClassLoader classLoader = TransformationScriptClassLoader.createBundleClassLoaderFor(Activator
        .getDefault().getBundle(), urls.toArray(new URL[0]));

    return classLoader;

  }
}
