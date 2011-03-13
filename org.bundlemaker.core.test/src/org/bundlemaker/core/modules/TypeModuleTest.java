package org.bundlemaker.core.modules;

import javax.swing.JFrame;

import junit.framework.Assert;

import org.bundlemaker.core.internal.modules.TypeModule;
import org.bundlemaker.core.internal.resource.Type;
import org.bundlemaker.core.resource.TypeEnum;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeModuleTest {

  /**
   * <p>
   * </p>
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testUnmodifiableGetContainedPackages() {

    //
    TypeModule module = new TypeModule(new ModuleIdentifier("name", "version"));

    module.getContainedPackageNames().add("");
  }

  /**
   * <p>
   * </p>
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testUnmodifiableGetContainedTypes() {

    //
    TypeModule module = new TypeModule(new ModuleIdentifier("name", "version"));

    module.getContainedTypeNames().add("");
  }

  /**
   * <p>
   * </p>
   */
  public void testUnmodifiableUserAttributes() {

    //
    IModule module = new TypeModule(new ModuleIdentifier("name", "version"));

    module.getUserAttributes().put("test", new JFrame());
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void testGetContainedTypes() {

    //
    TypeModule module = new TypeModule(new ModuleIdentifier("name", "version"));

    //
    for (int i = 0; i < 100000; i++) {
      module.getModifiableSelfResourceContainer().getModifiableContainedTypesMap()
          .put("a.b.c" + i, new Type("a.b.c" + i, TypeEnum.CLASS));
    }

    //
    Assert.assertEquals(100000, module.getModifiableSelfResourceContainer().getModifiableContainedTypesMap().size());

    Assert.assertEquals(100000, module.getContainedTypeNames().size());
  }
}
