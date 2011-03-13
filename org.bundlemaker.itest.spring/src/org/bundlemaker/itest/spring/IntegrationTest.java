package org.bundlemaker.itest.spring;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.exporter.SimpleReportExporter;
import org.bundlemaker.core.exporter.structure101.Structure101Exporter;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.ITypeSelector;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.osgi.exporter.BinaryBundleExporter;
import org.bundlemaker.core.osgi.pde.exporter.PdePluginProjectModuleExporter;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.BundleMakerProjectUtils;
import org.bundlemaker.core.util.EclipseProjectUtils;
import org.bundlemaker.core.util.ProgressMonitor;
import org.bundlemaker.core.util.StopWatch;
import org.bundlemaker.itest.spring.tests.ModularizedSystemTests;
import org.bundlemaker.itest.spring.tests.ModuleTest;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class IntegrationTest {

  /** - */
  public static final String   PROJECT_NAME = "spring";

  /** - */
  private static final boolean PARSE        = Boolean.getBoolean("parse");

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void testIntegrationTestSpring() throws Exception {

    // IApiBaseline baseline =
    // ApiModelFactory.newApiBaseline("testBaseLine");
    // IApiComponent component1 = new SpecialComponent(
    // baseline,
    // new File(
    // "R:/environments/bundlemaker2-environment/git-workspace/org.bundlemaker.itest.spring/spring/libs/bsh-2.0b4.jar"));
    //
    // IApiBaseline baseline2 = ApiModelFactory
    // .newApiBaseline("testBaseLine2");
    // IApiComponent component2 = new SpecialComponent(
    // baseline2,
    // new File(
    // "R:/environments/bundlemaker2-environment/git-workspace/org.bundlemaker.itest.spring/spring/libs/testng-5.8-jdk15.jar"));
    //
    // ApiComparator apiComparator = new ApiComparator();
    //
    // //
    // IDelta delta = apiComparator.compare(component2, component1,
    // baseline2,
    // baseline, VisibilityModifiers.ALL_VISIBILITIES, null);
    //
    // System.out.println(delta);

    // if (true) {
    // return;
    // }

    // delete the project
    if (PARSE) {
      EclipseProjectUtils.deleteProjectIfExists(PROJECT_NAME);
    }

    // create simple project
    IProject simpleProject = BundleMakerCore.getOrCreateSimpleProjectWithBundleMakerNature(PROJECT_NAME);

    // get the BundleMaker project
    IBundleMakerProject bundleMakerProject = BundleMakerCore.getBundleMakerProject(simpleProject, null);

    // create the project description
    if (PARSE) {
      IntegrationTestUtils.createProjectDescription(bundleMakerProject.getProjectDescription());
      bundleMakerProject.saveProjectDescription();
    }

    // create the progress monitor
    IProgressMonitor progressMonitor = new ProgressMonitor();

    // initialize the project
    bundleMakerProject.initialize(progressMonitor);

    // parse the project
    if (PARSE) {

      StopWatch stopWatch = new StopWatch();
      stopWatch.start();

      List<? extends IProblem> problems = bundleMakerProject.parse(progressMonitor, true);

      stopWatch.stop();
      System.out.println(stopWatch.getElapsedTime());

      BundleMakerProjectUtils.dumpProblems(problems);
    }

    // open the project
    bundleMakerProject.open(progressMonitor);

    // check the model
    checkResourceModel(bundleMakerProject);

    // get the default modularized system
    final IModularizedSystem modularizedSystem = bundleMakerProject.getModularizedSystemWorkingCopy(bundleMakerProject
        .getProject().getName());

    // add the transformations
    IntegrationTestUtils.addEmbedAntTransformation(modularizedSystem);
    IntegrationTestUtils.addModularizeSpringTransformation(modularizedSystem);

    // apply the transformation
    modularizedSystem.applyTransformations();

    modularizedSystem.getModuleSelectors().add(
        new PatternBasedModuleSelector(new String[] { "bsh.**" }, null, "bsh", "2.0b4"));

    modularizedSystem.getModuleSelectors().add(
        new PatternBasedModuleSelector(new String[] { "com.sun.jmx.**", "com.sun.management.**", "com.sun.rowset.**",
            "com.sun.activation.**", "javax.activation.**", "javax.jws.**", "javax.management.**",
            "org.omg.stub.javax.management.**", "javax.xml.**", "javax.sql.rowset.**", "javax.annotation.**",
            "org.w3c.dom.**" }, null, "jdk16", "jdk16"));

    modularizedSystem.getModuleSelectors().add(
        new PatternBasedModuleSelector(new String[] { "com.thoughtworks.qdox.**" }, null, "qdox", "1.5"));

    modularizedSystem.getModuleSelectors().add(
        new PatternBasedModuleSelector(new String[] { "javax.persistence.**" }, null, "toplink-essentials", "1.0"));

    modularizedSystem.getModuleSelectors().add(
        new PatternBasedModuleSelector(new String[] { "javax.transaction.**" }, null, "jta", "0.0.0"));

    modularizedSystem.getModuleSelectors().add(
        new PatternBasedModuleSelector(new String[] { "junit.**" }, null, "junit", "4.4"));

    modularizedSystem.getModuleSelectors().add(
        new PatternBasedModuleSelector(new String[] { "org.apache.commons.collections.**" }, null,
            "commons-collections", "3.2"));
    
    modularizedSystem.getModuleSelectors().add(
        new PatternBasedModuleSelector(new String[] { "org.aspectj.**" }, null,
            "aspectjrt", "0.0.0"));
    
    modularizedSystem.getModuleSelectors().add(
        new PatternBasedModuleSelector(new String[] { "org.aopalliance.**" }, null,
            "aopalliance", "0.0.0"));

    //
    ModularizedSystemTests.testGetModules(modularizedSystem);
    ModuleTest.testModules(modularizedSystem);

    //
    IResourceModule resourceModule = modularizedSystem.getResourceModule("Spring-JDBC", "2.5.6");

    //
    Map<String, Set<IType>> ambiguousTypesMap = modularizedSystem.getAmbiguousTypes();
    List<String> ambiguousTypes = new ArrayList<String>(ambiguousTypesMap.keySet());
    Collections.sort(ambiguousTypes);
    for (String typeName : ambiguousTypes) {
      try {
        modularizedSystem.getType(typeName, resourceModule);
      } catch (AmbiguousElementException e) {
        System.out.println(" - " + typeName);
        for (IType type : modularizedSystem.getTypes(typeName, resourceModule)) {
          System.out.println("   - " + type.getModule(modularizedSystem));
        }
      }
    }

    // //
    // for (String ambiguousType : ambiguousTypes) {
    //
    // //
    // Set<IModule> referencedModules = modularizedSystem
    // .getTypeContainingModules(ambiguousType);
    //
    // if (referencedModules.size() > 1) {
    // System.out.println("~~~~~~~");
    // System.out.println(ambiguousType);
    // for (IModule iModule : referencedModules) {
    // System.out.println(" - " + iModule.getModuleIdentifier());
    // }
    // }
    // }

    // // check the model
    // checkTypeModel(modularizedSystem);
    //
    // //
    // checkModularizedSystem(modularizedSystem);

    // // // export to simple report
    // // exportToSimpleReport(bundleMakerProject, modularizedSystem);
    //
    // // export to structure 101
    // exportToStructure101(bundleMakerProject, modularizedSystem);
    //
    // // export to binary bundle
    // exportToBinaryBundle(bundleMakerProject, modularizedSystem);
    //
    // // exportToPdeProjects
    // exportToPdeProjects(bundleMakerProject, modularizedSystem);
  }

  private void checkTypeModel(IModularizedSystem modularizedSystem) {

    // //
    // ITypeModule executionEnvironment = modularizedSystem
    // .getExecutionEnvironmentTypeModule();
    //
    // //
    // for (IType type : executionEnvironment.getContainedTypes()) {
    // Assert.assertTrue(
    // type.getFullyQualifiedName(),
    // ((ModularizedSystem) modularizedSystem)._typeToModuleListMap
    // .containsKey(type.getFullyQualifiedName()));
    // }
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   */
  private void checkModularizedSystem(IModularizedSystem modularizedSystem) {

    Collection<IResourceModule> resourceModules = modularizedSystem.getResourceModules();

    Assert.assertEquals(112, resourceModules.size());

    IResourceModule resourceModule = modularizedSystem.getResourceModule(new ModuleIdentifier("Spring-Core", "2.5.6"));

    Assert.assertNotNull(resourceModule);

    Collection<String> typeNames = resourceModule.getContainedTypeNames();
    Assert.assertEquals(212, typeNames.size());

    int externalBinaryReferencesCount = resourceModule.getAllReferences(true, false, false).size();

    int externalBinaryAndSourceReferencesCount = resourceModule.getAllReferences(true, true, false).size();

    int binaryReferencesCount = resourceModule.getAllReferences(false, false, false).size();

    int binaryAndSourceReferencesCount = resourceModule.getAllReferences(false, true, false).size();

    System.out.println(externalBinaryReferencesCount);
    System.out.println(externalBinaryAndSourceReferencesCount);
    System.out.println(binaryReferencesCount);
    System.out.println(binaryAndSourceReferencesCount);

    Set<IReference> externalBinaryReferences = resourceModule.getAllReferences(true, false, false);

    for (IReference reference : resourceModule.getAllReferences(true, true, false)) {

      if (reference.isCompileTimeReference() && !reference.isRuntimeReference()) {

        /** TODO **/

        if (reference.hasAssociatedType()) {
          System.out.println(reference);
          System.out.println(reference.getType().getSourceResource().getPath());
          System.out.println(reference.getType().getBinaryResource().getPath());
        }
      }

      // if (!externalBinaryReferences.contains(reference)) {
      // System.out.println(" - " + reference);
      // } else {
      // System.out.println(" + " + reference);
      // }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @param modularizedSystem
   * @throws Exception
   */
  private void exportToPdeProjects(IBundleMakerProject bundleMakerProject, IModularizedSystem modularizedSystem)
      throws Exception {

    //
    File destination = new File(System.getProperty("user.dir"), "destination");
    destination.mkdirs();

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(bundleMakerProject, destination,
        modularizedSystem);

    File templateDirectory = new File(System.getProperty("user.dir"), "templates");

    // TargetPlatformProjectExporter targetPlatformProjectExporter = new
    // TargetPlatformProjectExporter();
    // targetPlatformProjectExporter.setTemplateDirectory(templateDirectory);
    // targetPlatformProjectExporter
    // .export(modularizedSystem, exporterContext);

    PdePluginProjectModuleExporter pdeExporter = new PdePluginProjectModuleExporter();
    pdeExporter.setUseClassifcationForExportDestination(true);
    pdeExporter.setTemplateRootDirectory(templateDirectory);

    new ModularizedSystemExporterAdapter(pdeExporter).export(modularizedSystem, exporterContext);
  }

  private void exportToStructure101(IBundleMakerProject bundleMakerProject, IModularizedSystem modularizedSystem)
      throws Exception {

    //
    File destination = new File(System.getProperty("user.dir"), "destination");
    destination.mkdirs();

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(bundleMakerProject, destination,
        modularizedSystem);

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    Structure101Exporter exporter = new Structure101Exporter();
    exporter.export(modularizedSystem, exporterContext);
    stopWatch.stop();
    System.out.println("Dauer " + stopWatch.getElapsedTime());
  }

  private void exportToBinaryBundle(IBundleMakerProject bundleMakerProject, IModularizedSystem modularizedSystem)
      throws Exception {

    //
    File destination = new File(System.getProperty("user.dir"), "destination");
    destination.mkdirs();

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(bundleMakerProject, destination,
        modularizedSystem);

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    BinaryBundleExporter exporter = new BinaryBundleExporter();
    new ModularizedSystemExporterAdapter(exporter).export(modularizedSystem, exporterContext);
    stopWatch.stop();
    System.out.println("Dauer " + stopWatch.getElapsedTime());
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @param modularizedSystem
   * @throws Exception
   */
  private void exportToSimpleReport(IBundleMakerProject bundleMakerProject, IModularizedSystem modularizedSystem)
      throws Exception {

    //
    File destination = new File(System.getProperty("user.dir"), "destination");
    destination.mkdirs();

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(bundleMakerProject, destination,
        modularizedSystem);

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    SimpleReportExporter exporter = new SimpleReportExporter();
    new ModularizedSystemExporterAdapter(exporter).export(modularizedSystem, exporterContext);
    stopWatch.stop();
    System.out.println("Dauer " + stopWatch.getElapsedTime());
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   */
  private void checkResourceModel(IBundleMakerProject bundleMakerProject) {

    // check each file content entry
    for (IFileBasedContent fileBasedContent : bundleMakerProject.getProjectDescription().getFileBasedContent()) {

      //
      Assert.assertTrue(fileBasedContent.isResourceContent());

      //
      Map<String, IType> typeMap = new HashMap<String, IType>();

      // step 1: assert binary content
      for (IResource resource : fileBasedContent.getBinaryResources()) {

        // assert that the binary resources don't have any references
        Assert.assertEquals(0, resource.getReferences().size());

        // additional asserts if the resource is a class
        if (resource.getPath().endsWith(".class")) {

          // TODO
          // Assert.assertEquals(resourceStandin.getPath(), 1,
          // resourceStandin.getResource().getContainedTypes()
          // .size());

          for (IType type : resource.getContainedTypes()) {

            Assert.assertNotNull(type.getBinaryResource());
            Assert.assertTrue(type.hasBinaryResource());
            Assert.assertEquals(resource, type.getBinaryResource());

            //
            typeMap.put(type.getFullyQualifiedName(), type);

            Map<String, IReference> referenceMap = new HashMap<String, IReference>();

            for (IReference reference : type.getReferences()) {

              Assert.assertNotNull(reference.getFullyQualifiedName());
              Assert.assertFalse(reference.hasAssociatedResource());
              Assert.assertTrue(reference.hasAssociatedType());
              Assert.assertEquals(type, reference.getType());

              //
              Assert.assertFalse(
                  String.format("Duplicate reference '%s' -> '%s'", reference, referenceMap.get(reference)),
                  referenceMap.containsKey(reference.getFullyQualifiedName()));

              referenceMap.put(reference.getFullyQualifiedName(), reference);

            }
          }
        }
      }

      // step 2: assert source content
      for (IResource resource : fileBasedContent.getSourceResources()) {

        // assert that the binary resources don't have any references
        // Assert.assertEquals(0, resource.getReferences().size());

        // additional asserts if the resource is a class
        if (resource.getPath().endsWith(".java")) {

          // TODO
          // Assert.assertTrue(sourceResource.getPath(),
          // sourceResource.getContainedTypes().size() > 0);
          if (resource.getContainedTypes().size() == 0) {
            System.out.println(resource.getPath());
          }

          // TODO
          // Assert.assertEquals(resourceStandin.getPath(), 1,
          // resourceStandin.getResource().getContainedTypes()
          // .size());

          for (IType type : resource.getContainedTypes()) {

            Assert.assertNotNull(type.getSourceResource());
            Assert.assertTrue(type.hasSourceResource());
            Assert.assertEquals(resource, type.getSourceResource());

            Assert.assertTrue(
                String.format("Type '%s' is not contained in the binary type map.", type.getFullyQualifiedName()),
                typeMap.containsKey(type.getFullyQualifiedName()));

            Assert.assertSame(
                String.format("Type '%s' is not contained in the binary type map.", type.getFullyQualifiedName()),
                type, typeMap.get(type.getFullyQualifiedName()));

            Map<String, IReference> referenceMap = new HashMap<String, IReference>();

            for (IReference reference : type.getReferences()) {

              Assert.assertNotNull(reference.getFullyQualifiedName());
              Assert.assertFalse(reference.hasAssociatedResource());
              Assert.assertTrue(reference.hasAssociatedType());
              Assert.assertEquals(type, reference.getType());

              //
              Assert.assertFalse(
                  String.format("Duplicate reference '%s' -> '%s'", reference, referenceMap.get(reference)),
                  referenceMap.containsKey(reference.getFullyQualifiedName()));

              referenceMap.put(reference.getFullyQualifiedName(), reference);
            }
          }
        }
      }
    }
  }

  public static boolean isLocalOrAnonymousType(String fullQualifiedName) {
    return fullQualifiedName.matches(".*\\$\\d.*");
  }
}
