package org.bundlemaker.core.itest.jedit_artifact_model.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itestframework.AbstractBundleMakerProjectTest;
import org.bundlemaker.core.jdt.content.JdtProjectContentProvider;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdditionalContentTest extends AbstractBundleMakerProjectTest {

  /**
   * {@inheritDoc}
   */
  @Before
  public void before() throws CoreException {
    super.before();

    addProjectDescription();

    getBundleMakerProject().initialize(null);
    getBundleMakerProject().parseAndOpen(null);
  }

  @Test
  public void test() throws CoreException, IOException {

    //
    IModularizedSystem modularizedSystem = getBundleMakerProject().getModularizedSystemWorkingCopy();
    Assert.assertNotNull(modularizedSystem);

    IRootArtifact rootArtifact = modularizedSystem
        .getAnalysisModel(IAnalysisModelConfiguration.HIERARCHICAL_SOURCE_RESOURCES_CONFIGURATION);
    IModuleArtifact moduleArtifact = AnalysisModelQueries.findChild(rootArtifact, "name_1.2.3", IModuleArtifact.class);
  }

  /**
   * <p>
   * </p>
   * 
   * @param name
   * @return
   * @throws CoreException
   */
  private IProject createNewJavaProject(String name) throws CoreException {

    Assert.assertNotNull(name);

    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    IProject associatedProject = root.getProject(name);

    if (associatedProject.exists()) {
      associatedProject.delete(true, null);
    }

    IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(name);

    description.setNatureIds(new String[] { JavaCore.NATURE_ID });

    associatedProject.create(description, null);
    associatedProject.open(null);

    return associatedProject;
  }

  /**
   * <p>
   * </p>
   * 
   * @param sourceLocation
   * @param targetLocation
   * @throws IOException
   */
  public void copyDirectory(File sourceLocation, File targetLocation) throws IOException {

    if (sourceLocation.isDirectory()) {
      if (!targetLocation.exists()) {
        targetLocation.mkdir();
      }

      String[] children = sourceLocation.list();
      for (int i = 0; i < children.length; i++) {
        copyDirectory(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
      }
    } else {

      InputStream in = new FileInputStream(sourceLocation);
      OutputStream out = new FileOutputStream(targetLocation);

      // Copy the bits from instream to outstream
      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }
      in.close();
      out.close();
    }
  }

  protected void addProjectDescription(IBundleMakerProject bundleMakerProject, File directory) throws CoreException {

    Assert.assertTrue(directory.isDirectory());

    //
    IModifiableProjectDescription projectDescription = bundleMakerProject.getModifiableProjectDescription();

    // step 1:
    projectDescription.clear();

    //
    IProject project = createNewJavaProject("test");
    IJavaProject javaProject = JavaCore.create(project);

    Assert.assertNotNull(javaProject);

    //
    File testDataDirectory = new File(new File(System.getProperty("user.dir"), "test-data"), "NoPrimaryTypeTest");
    Assert.assertTrue(testDataDirectory.isDirectory());
    try {
      copyDirectory(testDataDirectory, project.getLocation().toFile());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    project.refreshLocal(IResource.DEPTH_INFINITE, null);

    // step 2: 'unset' the class path
    javaProject.setRawClasspath(null, null);

    // step 3: create the entries list
    List<IClasspathEntry> entries = new LinkedList<IClasspathEntry>();

    // step 3.1: add the vm path
    IVMInstall vmInstall = JavaRuntime.getDefaultVMInstall();
    IPath path = JavaRuntime.newJREContainerPath(vmInstall);
    IClasspathEntry classpathEntry = JavaCore.newContainerEntry(path);
    entries.add(classpathEntry);

    // TODO!!
    //
    classpathEntry = JavaCore.newSourceEntry(project.getFullPath().append("src"));
    entries.add(classpathEntry);

    // set the classpath
    javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[0]), null);
    javaProject.setOutputLocation(project.getFullPath().append("classes"), null);

    //
    JdtProjectContentProvider provider = new JdtProjectContentProvider();
    provider.setJavaProject(javaProject);

    // step 2: add the JRE
    projectDescription.setJre(getDefaultVmName());
    projectDescription.addContentProvider(provider);

    //
    projectDescription.save();
  }

  @Override
  protected String computeTestProjectName() {
    return "NoPrimaryTypeTest";
  }
}
