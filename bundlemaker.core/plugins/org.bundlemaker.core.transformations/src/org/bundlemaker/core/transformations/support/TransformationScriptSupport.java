/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.transformations.support;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.bundlemaker.core.BundleMakerCore;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class TransformationScriptSupport {

  /**
   * Name of the sample transformation script that is created the first time the transformation script support is
   * enabled
   */
  public static final IPath SAMPLE_TRANSFORMATION_SCRIPT_FILENAME = new Path(
                                                                      "org/bundlemaker/example/transformation/SampleTransformationScript.java");

  /**
   * Enables transformation script capability to the specified project.
   * 
   * <p>
   * This method adds all missing natures, classpaths and folders to the specified project
   * 
   * @param eclipseProject
   * @param jreClasspathEntries
   * @throws CoreException
   */
  /**
   * @param eclipseProject
   * @param jreClasspathEntries
   * @throws CoreException
   */
  public static void enableTransformationScriptSupport(IProject eclipseProject, IClasspathEntry[] jreClasspathEntries)
      throws CoreException {
    // hier: org.eclipse.jdt.ui.wizards.JavaCapabilityConfigurationPage.init(IJavaProject, IPath, IClasspathEntry[],
    // boolean)

    // Convert to JDT java project
    boolean alreadyJavaProject = BundleMakerCore.isJavaProject(eclipseProject);
    if (!alreadyJavaProject) {
      BundleMakerCore.addJavaNature(eclipseProject);
    }
    IPath projectPath = new Path(eclipseProject.getName()).makeAbsolute();
    IWorkspaceRoot root = eclipseProject.getWorkspace().getRoot();

    // Create source and binary folders
    IPath sourceFolderPath = projectPath.append("src");
    IPath binFolderPath = projectPath.append("bin");

    createFolder(root.getFolder(sourceFolderPath), true, true, null);
    createDerivedFolder(root.getFolder(binFolderPath), true, true, null);

    // Create classpath including the BundleMaker Classpath Container containing BM libs
    // from running Eclipse instance
    ClasspathBuilder.forProject(eclipseProject, alreadyJavaProject) //
        .setOutputLocation(binFolderPath) //
        .addSourceEntry(sourceFolderPath) //
        .addContainerEntry(BundleMakerCore.BUNDLEMAKER_CONTAINER_PATH) //
        .addEntries(jreClasspathEntries) //
        .save();

    // Write sample script
    InputStream is = TransformationScriptSupport.class.getResourceAsStream("TransformationScriptTemplate.txt");

    IFolder examplePackageFolder = createFolder(
        root.getFolder(sourceFolderPath.append(SAMPLE_TRANSFORMATION_SCRIPT_FILENAME.removeLastSegments(1))), true,
        true, null);
    IFile sampleScript = examplePackageFolder.getFile(SAMPLE_TRANSFORMATION_SCRIPT_FILENAME.lastSegment());

    // don't override if already existing
    if (!sampleScript.exists()) {
      sampleScript.create(is, true, null);
    }

  }

  /**
   * Returns an {@link IFile} pointing to the sample transformation script if available
   * 
   * @param project
   * @return
   */
  public static IFile findSampleTransformationScript(IProject project) {

    IFile result = project.getFile(new Path("src").append(SAMPLE_TRANSFORMATION_SCRIPT_FILENAME));

    if (result == null || result.exists() == false) {
      return null;
    }

    return result;
  }

  static class ClasspathBuilder {
    private final IJavaProject          _javaProject;

    private final List<IClasspathEntry> _cpEntries;

    private IPath                       _outputLocation;

    static ClasspathBuilder forProject(IProject eclipseProject, boolean alreadyJavaProject) throws CoreException {

      IJavaProject javaProject = JavaCore.create(eclipseProject);
      IClasspathEntry[] rawClasspath = javaProject.getRawClasspath();
      List<IClasspathEntry> cpEntries = new ArrayList<IClasspathEntry>();

      // // add already existing classpath entries
      if (alreadyJavaProject) {
        for (IClasspathEntry iClasspathEntry : rawClasspath) {
          cpEntries.add(iClasspathEntry);
        }
      }

      return new ClasspathBuilder(javaProject, cpEntries);

    }

    private ClasspathBuilder(IJavaProject project, List<IClasspathEntry> cpEntries) throws CoreException {
      _javaProject = project;
      _cpEntries = cpEntries;
    }

    public ClasspathBuilder addSourceEntry(IPath sourceFolderPath) {
      IClasspathEntry sourceEntry = JavaCore.newSourceEntry(sourceFolderPath);

      if (!_cpEntries.contains(sourceEntry)) {
        _cpEntries.add(sourceEntry);
      }
      return this;
    }

    public ClasspathBuilder addContainerEntry(IPath containerPath) {
      IClasspathEntry containerEntry = JavaCore.newContainerEntry(containerPath);

      if (!_cpEntries.contains(containerEntry)) {
        _cpEntries.add(containerEntry);
      }
      return this;
    }

    public ClasspathBuilder setOutputLocation(IPath outputLocation) {
      this._outputLocation = outputLocation;
      return this;
    }

    /**
     * @param jreClasspathEntries
     */
    public ClasspathBuilder addEntries(IClasspathEntry[] jreClasspathEntries) {

      for (IClasspathEntry iClasspathEntry : jreClasspathEntries) {
        if (!_cpEntries.contains(iClasspathEntry)) {
          _cpEntries.add(iClasspathEntry);
        }
      }

      return this;
    }

    public void save() throws CoreException {
      IClasspathEntry[] classpathEntries = _cpEntries.toArray(new IClasspathEntry[0]);
      _javaProject.setRawClasspath(classpathEntries, _outputLocation, null);

      // save changes to the jdt project
      _javaProject.save(null, true);

    }
  }

  public static void createDerivedFolder(IFolder folder, boolean force, boolean local, IProgressMonitor monitor)
      throws CoreException {
    if (!folder.exists()) {
      IContainer parent = folder.getParent();
      if (parent instanceof IFolder) {
        createDerivedFolder((IFolder) parent, force, local, null);
      }
      folder.create(force ? (IResource.FORCE | IResource.DERIVED) : IResource.DERIVED, local, monitor);
    }
  }

  /**
   * Creates a folder and all parent folders if not existing. Project must exist.
   * <code> org.eclipse.ui.dialogs.ContainerGenerator</code> is too heavy (creates a runnable)
   * 
   * @param folder
   *          the folder to create
   * @param force
   *          a flag controlling how to deal with resources that are not in sync with the local file system
   * @param local
   *          a flag controlling whether or not the folder will be local after the creation
   * @param monitor
   *          the progress monitor
   * @throws CoreException
   *           thrown if the creation failed
   */
  public static IFolder createFolder(IFolder folder, boolean force, boolean local, IProgressMonitor monitor)
      throws CoreException {
    if (!folder.exists()) {
      IContainer parent = folder.getParent();
      if (parent instanceof IFolder) {
        createFolder((IFolder) parent, force, local, null);
      }
      folder.create(force, local, monitor);
    }

    return folder;
  }

}
