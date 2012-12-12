package org.bundlemaker.core.itest.modules;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bundlemaker.core.itest._framework.AbstractModularizedSystemTest;
import org.bundlemaker.core.itest.analysis.misc_models.InnerClassTest;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.modules.modifiable.MovableUnit;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MovableUnitTest extends AbstractModularizedSystemTest {

  // the resource module
  private IResourceModule _resourceModule;

  @Before
  public void initResourceModule() throws CoreException {
    _resourceModule = getModularizedSystem().getResourceModule("InnerClassTest", "1.0.0");
  }

  /**
   * <p>
   * This test assures that the (test) resource module contains only the following four types:
   * <ul>
   * <li>de.test.innertypes.A</li>
   * <li>de.test.innertypes.A$AA</li>
   * <li>de.test.innertypes.B</li>
   * <li>de.test.innertypes.B$BB</li>
   * <li></li>
   * </ul>
   * </p>
   */
  @Test
  public void testResourceModuleTypes() {

    // test the resource module
    Assert.assertNotNull(_resourceModule.getType("de.test.innertypes.A"));
    Assert.assertNotNull(_resourceModule.getType("de.test.innertypes.A$AA"));
    Assert.assertNotNull(_resourceModule.getType("de.test.innertypes.B"));
    Assert.assertNotNull(_resourceModule.getType("de.test.innertypes.B$BB"));
    Assert.assertNull(_resourceModule.getType("de.test.innertypes.A$1"));
    Assert.assertNull(_resourceModule.getType("de.test.innertypes.B$1"));
  }

  /**
   * <p>
   * This test assures that all MovableUnits contains the same resources and types.
   * </p>
   */
  @Test
  public void testMovableUnit() {

    //
    assertMovableUnit(MovableUnit.createFromType(_resourceModule.getType("de.test.innertypes.A"),
        getModularizedSystem()));
    assertMovableUnit(MovableUnit.createFromType(_resourceModule.getType("de.test.innertypes.A$AA"),
        getModularizedSystem()));
    assertMovableUnit(MovableUnit.createFromType(_resourceModule.getType("de.test.innertypes.B"),
        getModularizedSystem()));
    assertMovableUnit(MovableUnit.createFromType(_resourceModule.getType("de.test.innertypes.B$BB"),
        getModularizedSystem()));

    //
    assertMovableUnit(MovableUnit.createFromResource(
        _resourceModule.getResource("de/test/innertypes/A.java", ProjectContentType.SOURCE), getModularizedSystem()));

    //
    assertMovableUnit(MovableUnit.createFromResource(
        _resourceModule.getResource("de/test/innertypes/A.class", ProjectContentType.BINARY), getModularizedSystem()));
    assertMovableUnit(MovableUnit.createFromResource(
        _resourceModule.getResource("de/test/innertypes/A$AA.class", ProjectContentType.BINARY), getModularizedSystem()));
    assertMovableUnit(MovableUnit.createFromResource(
        _resourceModule.getResource("de/test/innertypes/B.class", ProjectContentType.BINARY), getModularizedSystem()));
    assertMovableUnit(MovableUnit.createFromResource(
        _resourceModule.getResource("de/test/innertypes/B$BB.class", ProjectContentType.BINARY), getModularizedSystem()));
    assertMovableUnit(MovableUnit.createFromResource(
        _resourceModule.getResource("de/test/innertypes/B$1.class", ProjectContentType.BINARY), getModularizedSystem()));
    assertMovableUnit(MovableUnit.createFromResource(
        _resourceModule.getResource("de/test/innertypes/A$1.class", ProjectContentType.BINARY), getModularizedSystem()));
  }

  /**
   * <p>
   * </p>
   * 
   */
  @Test
  public void testAssertEquals() {

    Assert.assertEquals(
        MovableUnit.createFromType(_resourceModule.getType("de.test.innertypes.A"), getModularizedSystem()),
        MovableUnit.createFromType(_resourceModule.getType("de.test.innertypes.A$AA"), getModularizedSystem()));
    
    Assert.assertEquals(
        MovableUnit.createFromType(_resourceModule.getType("de.test.innertypes.A"), getModularizedSystem()),
        MovableUnit.createFromType(_resourceModule.getType("de.test.innertypes.B"), getModularizedSystem()));
  }

  /**
   * <p>
   * Helper method that checks the specified {@link IMovableUnit}.
   * </p>
   * 
   * @param movableUnit
   */
  private void assertMovableUnit(IMovableUnit movableUnit) {

    // assert not null
    Assert.assertNotNull(movableUnit);

    // assert associated types
    List<IType> types = movableUnit.getAssociatedTypes();
    Collections.sort(types, new Comparator<IType>() {
      public int compare(IType o1, IType o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });
    Assert.assertEquals(4, types.size());
    Assert.assertEquals("A", types.get(0).getName());
    Assert.assertEquals("A$AA", types.get(1).getName());
    Assert.assertEquals("B", types.get(2).getName());
    Assert.assertEquals("B$BB", types.get(3).getName());

    // assert binary resources
    List<IResource> binaryResources = movableUnit.getAssociatedBinaryResources();
    Collections.sort(binaryResources, new Comparator<IResource>() {
      public int compare(IResource o1, IResource o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });
    Assert.assertEquals(6, binaryResources.size());
    Assert.assertEquals("A$1.class", binaryResources.get(0).getName());
    Assert.assertEquals("A$AA.class", binaryResources.get(1).getName());
    Assert.assertEquals("A.class", binaryResources.get(2).getName());
    Assert.assertEquals("B$1.class", binaryResources.get(3).getName());
    Assert.assertEquals("B$BB.class", binaryResources.get(4).getName());
    Assert.assertEquals("B.class", binaryResources.get(5).getName());

    // test source resources
    IResource sourceResources = movableUnit.getAssociatedSourceResource();
    Assert.assertNotNull(sourceResources);
    Assert.assertEquals("A.java", sourceResources.getName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return InnerClassTest.class.getSimpleName();
  }
}
