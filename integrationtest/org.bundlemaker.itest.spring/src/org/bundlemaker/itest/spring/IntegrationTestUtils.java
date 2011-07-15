package org.bundlemaker.itest.spring;

import java.io.File;
import java.io.FileFilter;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Mkdir;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.modifiable.IModifiableBundleMakerProjectDescription;
import org.bundlemaker.core.transformations.EmbedModuleTransformation;
import org.bundlemaker.core.transformations.resourceset.ResourceSetBasedTransformation;
import org.bundlemaker.itest.AbstractIntegrationTest;
import org.eclipse.core.runtime.CoreException;

public class IntegrationTestUtils {

  public static void addModularizeSpringTransformation(IModularizedSystem modularizedSystem) {
    // ****************************************************************************************
    // TODO: API
    ResourceSetBasedTransformation transformation = new ResourceSetBasedTransformation();
    modularizedSystem.getTransformations().add(transformation);

    // create from identifier
    IModuleIdentifier fromIdentifier = new ModuleIdentifier("Spring", "2.5.6");

    transformation.addModuleDefinition("spring-core", "2.5.6").addResourceSet(
        fromIdentifier,
        new String[] { "org/springframework/core/**", "org/springframework/metadata/**", "org/springframework/util/**",
            "org/springframework/asm/**" }, null);

    transformation.addModuleDefinition("spring-aop", "2.5.6").addResourceSet(fromIdentifier,
        new String[] { "org/springframework/aop/**" }, null);

    transformation.addModuleDefinition("spring-beans", "2.5.6").addResourceSet(fromIdentifier,
        new String[] { "org/springframework/beans/**" }, null);

    transformation.addModuleDefinition("spring-context", "2.5.6").addResourceSet(
        fromIdentifier,
        new String[] { "org/springframework/context/**", "org/springframework/instrument/**",
            "org/springframework/jmx/**", "org/springframework/ejb/**", "org/springframework/jndi/**",
            "org/springframework/remoting/*", "org/springframework/remoting/rmi/**",
            "org/springframework/remoting/soap/**", "org/springframework/remoting/support/**",
            "org/springframework/scheduling/**", "org/springframework/scripting/**",
            "org/springframework/stereotype/**", "org/springframework/ui/**", "org/springframework/validation/**" },
        new String[] { "org/springframework/scheduling/quartz/**" });

    transformation.addModuleDefinition("spring-context-support", "2.5.6").addResourceSet(
        fromIdentifier,
        new String[] { "org/springframework/cache/ehcache/**", "org/springframework/mail/**",
            "org/springframework/scheduling/commonj/**", "org/springframework/scheduling/quartz/**",
            "org/springframework/ui/freemarker/**", "org/springframework/ui/jasperreports/**",
            "org/springframework/ui/velocity/**" }, null);

    transformation.addModuleDefinition("spring-jdbc", "2.5.6").addResourceSet(fromIdentifier,
        new String[] { "org/springframework/jdbc/**" }, null);

    transformation.addModuleDefinition("spring-jms", "2.5.6").addResourceSet(fromIdentifier,
        new String[] { "org/springframework/jms/**" }, null);

    transformation.addModuleDefinition("spring-tx", "2.5.6")
        .addResourceSet(
            fromIdentifier,
            new String[] { "org/springframework/dao/**", "org/springframework/jca/**",
                "org/springframework/transaction/**" }, null);

    transformation.addModuleDefinition("spring-orm", "2.5.6").addResourceSet(fromIdentifier,
        new String[] { "org/springframework/orm/**" }, null);

    transformation.addModuleDefinition("spring-web", "2.5.6").addResourceSet(
        fromIdentifier,
        new String[] { "org/springframework/remoting/caucho/**", "org/springframework/remoting/httpinvoker/**",
            "org/springframework/remoting/jaxrpc/**", "org/springframework/remoting/jaxws/**",
            "org/springframework/web/**" }, null);

    // ****************************************************************************************
  }

  public static void addEmbedAntTransformation(IModularizedSystem modularizedSystem) {
    // ****************************************************************************************
    // TODO: API
    EmbedModuleTransformation embedModuleTransformation = new EmbedModuleTransformation();
    embedModuleTransformation.setHostModuleIdentifier(new ModuleIdentifier("ant", "0.0.0"));
    embedModuleTransformation.getEmbeddedModulesIdentifiers().add(new ModuleIdentifier("ant-trax", "0.0.0"));
    embedModuleTransformation.getEmbeddedModulesIdentifiers().add(new ModuleIdentifier("ant-launcher", "0.0.0"));
    embedModuleTransformation.getEmbeddedModulesIdentifiers().add(new ModuleIdentifier("ant-junit", "0.0.0"));

    modularizedSystem.getTransformations().add(embedModuleTransformation);
    // ****************************************************************************************
  }

  /**
   * <p>
   * </p>
   */
  public static IBundleMakerProjectDescription createProjectDescription(
      IModifiableBundleMakerProjectDescription projectDescription) throws CoreException {

    // step 1:
    projectDescription.clear();

    // step 2: add the JRE
    projectDescription.setJre(AbstractIntegrationTest.getDefaultVmName());

    // step 3: add the source and classes
    File classesZip = new File(System.getProperty("user.dir"), "spring/classes");
    File sourceDirectory = new File(System.getProperty("user.dir"), "spring/source");
    projectDescription.addResourceContent("Spring", "2.5.6", classesZip.getAbsolutePath(),
        sourceDirectory.getAbsolutePath());

    // step 4: process the class path entries
    File libsDir = new File(System.getProperty("user.dir"), "spring/libs");
    File[] jarFiles = libsDir.listFiles(new FileFilter() {
      public boolean accept(File pathname) {
        return !(pathname.getName().contains(".svn") || pathname.getName().contains(".SVN"));
      }
    });
    for (File externalJar : jarFiles) {
      projectDescription.addResourceContent(externalJar.getAbsolutePath());
    }

    // return the result
    return projectDescription;
  }

  /**
   * <p>
   * </p>
   * 
   * @param directory
   */
  public static void delete(File directory) {

    Delete delete = new Delete();
    delete.setProject(new Project());
    delete.setDir(directory);
    delete.setQuiet(true);
    delete.execute();

    Mkdir mkdir = new Mkdir();
    mkdir.setProject(new Project());
    mkdir.setDir(directory);
    mkdir.execute();
  }
}
