package org.bundlemaker.itest.spring.tests;

import java.util.Collection;

import junit.framework.Assert;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.query.TypeQueryFilters;
import org.bundlemaker.core.resource.IType;

public class ModuleTest {

  public static void testModules(IModularizedSystem modularizedSystem) {

    //
    IModule module = modularizedSystem.getModule("Spring-Context", "2.5.6");

    //
    Assert.assertEquals(49, module.getContainedPackageNames().size());

    //
    Collection<IType> types = module.getContainedTypes(TypeQueryFilters.newPatternBasedTypeFilter(
        new String[] { "org\\.springframework\\.scheduling\\.[^.]*" }, new String[] {}));
    Assert.assertEquals(3, types.size());

    //
    types = module.getContainedTypes(TypeQueryFilters.newPatternBasedTypeFilter(
        new String[] { "org\\.springframework\\.context\\.[^.]*" }, new String[] {}));
    Assert.assertEquals(15, types.size());
    System.out.println(types);

    for (IType iType : types) {
      IType iType2 = module.getType(iType.getFullyQualifiedName());
      Assert.assertSame(iType, iType2);
    }
  }
}
