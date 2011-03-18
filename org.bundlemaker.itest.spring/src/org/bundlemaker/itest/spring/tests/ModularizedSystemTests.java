package org.bundlemaker.itest.spring.tests;

import java.util.Collection;

import junit.framework.Assert;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.modules.query.ResourceModuleQueryFilters;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModularizedSystemTests {

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   */
  public static void testGetModules(final IModularizedSystem modularizedSystem) {

    //
    Assert.assertNotNull(modularizedSystem);

    //
    Assert.assertNotNull(modularizedSystem.getAllModules());
    Assert.assertEquals(115, modularizedSystem.getAllModules().size());

    //
    Collection<IModule> modules = modularizedSystem.getAllModules(new IQueryFilter<IModule>() {
      public boolean matches(IModule content) {
        return content.getModuleIdentifier().getName().startsWith("spring");
      }
    });
    Assert.assertNotNull(modules);
    Assert.assertEquals(10, modules.size());

    //
    Assert.assertNotNull(modularizedSystem.getNonResourceModules());
    Assert.assertEquals(1, modularizedSystem.getNonResourceModules().size());
    modules = modularizedSystem.getAllModules(new IQueryFilter<IModule>() {
      public boolean matches(IModule content) {
        return content.getModuleIdentifier().equals(modularizedSystem.getExecutionEnvironment().getModuleIdentifier());
      }
    });
    Assert.assertNotNull(modules);
    Assert.assertEquals(1, modules.size());

    //
    Assert.assertNotNull(modularizedSystem.getResourceModules());
    Assert.assertEquals(114, modularizedSystem.getResourceModules().size());
    Collection<IResourceModule> resourceModules = modularizedSystem
        .getResourceModules(ResourceModuleQueryFilters.TRUE_QUERY_FILTER);
    Assert.assertNotNull(resourceModules);

    //
    Assert.assertNotNull(modularizedSystem.getModule(new ModuleIdentifier("jdk16", "jdk16")));
    Assert.assertNotNull(modularizedSystem.getModule(new ModuleIdentifier("spring-jdbc", "2.5.6")));

    //
    Assert.assertNull(modularizedSystem.getResourceModule(new ModuleIdentifier("jdk16", "jdk16")));
    Assert.assertNotNull(modularizedSystem.getResourceModule(new ModuleIdentifier("spring-jdbc", "2.5.6")));
  }

  // private void checkModularizedSystem(IModularizedSystem modularizedSystem) {
  //
  // Collection<IResourceModule> resourceModules = modularizedSystem.getResourceModules();
  //
  // Assert.assertEquals(112, resourceModules.size());
  //
  // IResourceModule resourceModule = modularizedSystem.getResourceModule(new ModuleIdentifier("Spring-Core", "2.5.6"));
  //
  // Assert.assertNotNull(resourceModule);
  //
  // Collection<String> typeNames = resourceModule.getContainedTypeNames();
  // Assert.assertEquals(212, typeNames.size());
  //
  // int externalBinaryReferencesCount = resourceModule.getAllReferences(true, false, false).size();
  //
  // int externalBinaryAndSourceReferencesCount = resourceModule.getAllReferences(true, true, false).size();
  //
  // int binaryReferencesCount = resourceModule.getAllReferences(false, false, false).size();
  //
  // int binaryAndSourceReferencesCount = resourceModule.getAllReferences(false, true, false).size();
  //
  // System.out.println(externalBinaryReferencesCount);
  // System.out.println(externalBinaryAndSourceReferencesCount);
  // System.out.println(binaryReferencesCount);
  // System.out.println(binaryAndSourceReferencesCount);
  //
  // Set<IReference> externalBinaryReferences = resourceModule.getAllReferences(true, false, false);
  //
  // for (IReference reference : resourceModule.getAllReferences(true, true, false)) {
  //
  // if (reference.isCompileTimeReference() && !reference.isRuntimeReference()) {
  //
  // /** TODO **/
  //
  // if (reference.hasAssociatedType()) {
  // System.out.println(reference);
  // System.out.println(reference.getType().getSourceResource().getPath());
  // System.out.println(reference.getType().getBinaryResource().getPath());
  // }
  // }
  //
  // // if (!externalBinaryReferences.contains(reference)) {
  // // System.out.println(" - " + reference);
  // // } else {
  // // System.out.println(" + " + reference);
  // // }
  // }
  // }

}
