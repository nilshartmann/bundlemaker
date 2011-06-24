package org.bundlemaker.core.itest.modules;

import org.bundlemaker.core.itest.analysis.ModuleConverterTest;
import org.bundlemaker.core.itest.framework.AbstractModuleConverterTest;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IType;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class CachesTest extends AbstractModuleConverterTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void testRemoveAndAdd() throws Exception {

    //
    IResourceModule resourceModule = getModularizedSystem().getResourceModule("ModuleConverterTest", "1.0.0");
    Assert.assertNotNull(resourceModule);

    Assert.assertTrue(resourceModule instanceof IModifiableResourceModule);
    IModifiableResourceModule modifiableResourceModule = (IModifiableResourceModule) resourceModule;

    // TEST 1: resource module must contain 2 types
    Assert.assertEquals(2, modifiableResourceModule.getContainedTypes().size());

    // REMOVE THE TYPE
    IType type = resourceModule.getType("de.test.Klasse");
    modifiableResourceModule.getModifiableSelfResourceContainer().remove(type.getBinaryResource(), ContentType.BINARY);
    modifiableResourceModule.getModifiableSelfResourceContainer().remove(type.getSourceResource(), ContentType.SOURCE);

    // TEST 2: remove resource 'de.test.Klasse' and check contained types
    Assert.assertEquals(1, modifiableResourceModule.getContainedTypes().size());
    Assert.assertNull(getModularizedSystem().getType("de.test.Klasse"));
    Assert.assertNull(getModularizedSystem().getTypeContainingModule("de.test.Klasse"));
    Assert.assertEquals(0, getModularizedSystem().getTypeContainingModules("de.test.Klasse").size());

    // ADD THE TYPE
    modifiableResourceModule.getModifiableSelfResourceContainer().add(type.getBinaryResource(), ContentType.BINARY);
    modifiableResourceModule.getModifiableSelfResourceContainer().add(type.getSourceResource(), ContentType.SOURCE);

    // TEST 3: check contained types
    Assert.assertEquals(2, modifiableResourceModule.getContainedTypes().size());
    Assert.assertNotNull(getModularizedSystem().getType("de.test.Klasse"));
    Assert.assertNotNull(getModularizedSystem().getTypeContainingModule("de.test.Klasse"));
    Assert.assertEquals(1, getModularizedSystem().getTypeContainingModules("de.test.Klasse").size());
  }

  @Test
  public void testAddAndRemove() throws Exception {

    //
    IResourceModule resourceModule = getModularizedSystem().getResourceModule("ModuleConverterTest", "1.0.0");
    Assert.assertNotNull(resourceModule);

    Assert.assertTrue(resourceModule instanceof IModifiableResourceModule);
    IModifiableResourceModule modifiableResourceModule = (IModifiableResourceModule) resourceModule;

    // TEST 1: resource module must contain 2 types
    Assert.assertEquals(2, modifiableResourceModule.getContainedTypes().size());

    // ADD THE TYPE
    IType type = resourceModule.getType("de.test.Klasse");
    modifiableResourceModule.getModifiableSelfResourceContainer().add(type.getBinaryResource(), ContentType.BINARY);
    modifiableResourceModule.getModifiableSelfResourceContainer().add(type.getSourceResource(), ContentType.SOURCE);

    // TEST 2: check contained types
    Assert.assertEquals(2, modifiableResourceModule.getContainedTypes().size());
    Assert.assertNotNull(getModularizedSystem().getType("de.test.Klasse"));
    Assert.assertNotNull(getModularizedSystem().getTypeContainingModule("de.test.Klasse"));
    Assert.assertEquals(1, getModularizedSystem().getTypeContainingModules("de.test.Klasse").size());

    // REMOVE THE TYPE
    modifiableResourceModule.getModifiableSelfResourceContainer().remove(type.getBinaryResource(), ContentType.BINARY);
    modifiableResourceModule.getModifiableSelfResourceContainer().remove(type.getSourceResource(), ContentType.SOURCE);

    // TEST 3: check contained types
    Assert.assertEquals(1, modifiableResourceModule.getContainedTypes().size());
    Assert.assertNull(getModularizedSystem().getType("de.test.Klasse"));
    Assert.assertNull(getModularizedSystem().getTypeContainingModule("de.test.Klasse"));
    Assert.assertEquals(0, getModularizedSystem().getTypeContainingModules("de.test.Klasse").size());
  }

  @Test
  public void testRemoveModule() throws Exception {

    //
    IResourceModule resourceModule = getModularizedSystem().getResourceModule("ModuleConverterTest", "1.0.0");
    Assert.assertNotNull(resourceModule);

    Assert.assertTrue(resourceModule instanceof IModifiableResourceModule);
    IModifiableResourceModule modifiableResourceModule = (IModifiableResourceModule) resourceModule;

    // TEST 1: resource module must contain 2 types
    Assert.assertEquals(2, modifiableResourceModule.getContainedTypes().size());
    Assert.assertNotNull(getModularizedSystem().getType("de.test.Klasse"));
    Assert.assertNotNull(getModularizedSystem().getTypeContainingModule("de.test.Klasse"));
    Assert.assertEquals(1, getModularizedSystem().getTypeContainingModules("de.test.Klasse").size());

    // REMOVE THE MODULE
    getModularizedSystem().removeModule(resourceModule.getModuleIdentifier());

    // TEST 2: check contained types
    Assert.assertNull(getModularizedSystem().getType("de.test.Klasse"));
    Assert.assertNull(getModularizedSystem().getTypeContainingModule("de.test.Klasse"));
    Assert.assertEquals(0, getModularizedSystem().getTypeContainingModules("de.test.Klasse").size());

    // ADD THE MODULE
    getModularizedSystem().addModifiableResourceModule(((IModifiableResourceModule) resourceModule));

    // TEST 3: check contained types
    Assert.assertEquals(2, modifiableResourceModule.getContainedTypes().size());
    Assert.assertNotNull(getModularizedSystem().getType("de.test.Klasse"));
    Assert.assertNotNull(getModularizedSystem().getTypeContainingModule("de.test.Klasse"));
    Assert.assertEquals(1, getModularizedSystem().getTypeContainingModules("de.test.Klasse").size());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return ModuleConverterTest.class.getSimpleName();
  }
}
