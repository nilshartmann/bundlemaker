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
  public static void testGetModules(IModularizedSystem modularizedSystem) {

    //
    Assert.assertNotNull(modularizedSystem);

    //
    Assert.assertNotNull(modularizedSystem.getAllModules());
    Assert.assertEquals(113, modularizedSystem.getAllModules().size());

    //
    Collection<IModule> modules = modularizedSystem.getAllModules(new IQueryFilter<IModule>() {
      public boolean matches(IModule content) {
        return content.getModuleIdentifier().getName().startsWith("Spring");
      }
    });
    Assert.assertNotNull(modules);
    Assert.assertEquals(8, modules.size());

    //
    Assert.assertNotNull(modularizedSystem.getNonResourceModules());
    Assert.assertEquals(1, modularizedSystem.getNonResourceModules().size());
    modules = modularizedSystem.getAllModules(new IQueryFilter<IModule>() {
      public boolean matches(IModule content) {
        return content.getModuleIdentifier().getName().startsWith("jdk16");
      }
    });
    Assert.assertNotNull(modules);
    Assert.assertEquals(1, modules.size());

    //
    Assert.assertNotNull(modularizedSystem.getResourceModules());
    Assert.assertEquals(112, modularizedSystem.getResourceModules().size());
    Collection<IResourceModule> resourceModules = modularizedSystem
        .getResourceModules(ResourceModuleQueryFilters.TRUE_QUERY_FILTER);
    Assert.assertNotNull(resourceModules);
    Assert.assertEquals(112, resourceModules.size());

    //
    Assert.assertNotNull(modularizedSystem.getModule(new ModuleIdentifier("jdk16", "jdk16")));
    Assert.assertNotNull(modularizedSystem.getModule(new ModuleIdentifier("Spring-JDBC", "2.5.6")));

    //
    Assert.assertNull(modularizedSystem.getResourceModule(new ModuleIdentifier("jdk16", "jdk16")));
    Assert.assertNotNull(modularizedSystem.getResourceModule(new ModuleIdentifier("Spring-JDBC", "2.5.6")));
  }
}
