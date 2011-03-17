package org.bundlemaker.itest.eclipse;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.itest.AbstractIntegrationTest;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class IntegrationTest extends AbstractIntegrationTest {

  /**
   * <p>
   * Creates a new instance of type {@link IntegrationTest}.
   * </p>
   */
  public IntegrationTest() {
    super("eclipse", true, true, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doAddProjectDescription(IBundleMakerProject bundleMakerProject) throws Exception {

    // step 1:
    bundleMakerProject.getProjectDescription().clear();

    // step 2: add the JRE
    bundleMakerProject.getProjectDescription().setJre("jdk16");

    // step 3: add the source and classes
    File sourceDirectory = new File(System.getProperty("user.dir"), "eclipse/source");

    File classesDir = new File(System.getProperty("user.dir"), "eclipse/classes");
    File[] jarFiles = classesDir.listFiles(new FileFilter() {
      public boolean accept(File pathname) {
        return !(pathname.getName().contains(".svn") || pathname.getName().contains(".SVN"));
      }
    });
    List<String> classes = new LinkedList<String>();
    for (File file : jarFiles) {
      classes.add(file.getAbsolutePath());
    }
    bundleMakerProject.getProjectDescription().addResourceContent("eclipse", "3.6.1", classes,
        Arrays.asList(new String[] { sourceDirectory.getAbsolutePath() }));

    // step 4: process the class path entries
    File libsDir = new File(System.getProperty("user.dir"), "eclipse/libs");
    jarFiles = libsDir.listFiles(new FileFilter() {
      public boolean accept(File pathname) {
        return !(pathname.getName().contains(".svn") || pathname.getName().contains(".SVN"));
      }
    });
    for (File externalJar : jarFiles) {
      bundleMakerProject.getProjectDescription().addResourceContent(externalJar.getAbsolutePath());
    }

    //
    bundleMakerProject.getProjectDescription().save();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doCheckModularizedSystem(IModularizedSystem modularizedSystem) {

    // assert
    for (IResourceModule resourceModule : modularizedSystem.getResourceModules()) {

      // step 1: assert binary content
      for (IResource resource : resourceModule.getResources(ContentType.BINARY)) {

        //
        Assert.assertSame(resourceModule, resource.getAssociatedResourceModule(modularizedSystem));

        //
        for (IReference reference : resource.getReferences()) {
          //
          Assert.assertSame(resource, reference.getResource());
        }
      }

      //
      for (IResource resource : resourceModule.getResources(ContentType.SOURCE)) {

        //
        Assert.assertSame(resourceModule, resource.getAssociatedResourceModule(modularizedSystem));

        //
        for (IReference reference : resource.getReferences()) {
          //
          Assert.assertSame(resource, reference.getResource());
        }
      }
    }
  }
}
