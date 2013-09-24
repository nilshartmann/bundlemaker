package org.bundlemaker.core.internal.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.jtype.IType;
import org.bundlemaker.core.jtype.ITypeResource;
import org.bundlemaker.core.jtype.TypeEnum;
import org.bundlemaker.core.jtype.internal.JTypeMovableUnitCreator;
import org.bundlemaker.core.jtype.internal.ModelExtension;
import org.bundlemaker.core.jtype.internal.Type;
import org.bundlemaker.core.jtype.internal.TypeResource;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Test;

public class MovableUnitTest {

  /** - */
  private IProjectContentEntry         _projectContentEntry;

  private IModularizedSystem           _modularizedSystem;

  private Map<String, IModuleResource> _binaries;

  private Map<String, IModuleResource> _sources;

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Before
  public void setup() throws CoreException {

    new ModelExtension().initializeModelExtension();

    //
    Set<IProjectContentResource> binaryResources = new HashSet<IProjectContentResource>();
    Set<IProjectContentResource> sourceResources = new HashSet<IProjectContentResource>();

    //
    Type typeBlubb = new Type("de.test.Blubb", TypeEnum.INTERFACE, "1", false);
    Set<IType> typeSetBlubb = new HashSet<IType>();
    typeSetBlubb.add(typeBlubb);

    //
    Type typeBlubb1 = new Type("de.test.Blubb$1", TypeEnum.INTERFACE, "1", false);
    Set<IType> typeSetBlubb1 = new HashSet<IType>();
    typeSetBlubb1.add(typeBlubb1);

    //
    binaryResources.add(new Resource("1", "root", "de/test/NurBinary.txt"));

    //
    binaryResources.add(new Resource("1", "root", "de/test/Bla.test"));

    //
    Resource binaryResource = new Resource("1", "root", "de/test/Blubb.class");
    TypeResource typeResource = (TypeResource) binaryResource.adaptAs(ITypeResource.class);
    typeResource.getModifiableContainedTypes().add(typeBlubb);
    typeBlubb.setBinaryResource(binaryResource);
    binaryResources.add(binaryResource);

    //
    binaryResource = new Resource("1", "root", "de/test/InnerBlubbWithoutType.class");
    typeResource = (TypeResource) binaryResource.adaptAs(ITypeResource.class);
    typeResource.setSourceName("Blubb.java");
    binaryResources.add(binaryResource);

    //
    binaryResource = new Resource("1", "root", "de/test/Blubb$1.class");
    typeResource = (TypeResource) binaryResource.adaptAs(ITypeResource.class);
    typeResource.getModifiableContainedTypes().add(typeBlubb1);
    typeBlubb1.setBinaryResource(binaryResource);
    binaryResources.add(binaryResource);

    //
    sourceResources.add(new Resource("1", "root", "de/test/NurSource.txt"));

    //
    sourceResources.add(new Resource("1", "root", "de/test/Bla.test"));

    //
    Resource sourceResource = new Resource("1", "root", "de/test/Blubb.java");
    sourceResources.add(sourceResource);
    typeResource = (TypeResource) binaryResource.adaptAs(ITypeResource.class);
    typeResource.getModifiableContainedTypes().add(typeBlubb);
    typeBlubb.setSourceResource(sourceResource);
    sourceResources.add(sourceResource);

    //
    _projectContentEntry = mock(IProjectContentEntry.class);
    stub(_projectContentEntry.getBinaryResources()).toReturn((Set) binaryResources);
    stub(_projectContentEntry.getSourceResources()).toReturn((Set) sourceResources);

    //
    _modularizedSystem = mock(IModularizedSystem.class);

    //
    assertThat(_projectContentEntry.getBinaryResources().size(), is(5));
    assertThat(_projectContentEntry.getSourceResources().size(), is(3));

    //
    _binaries = new HashMap<String, IModuleResource>();
    for (IProjectContentResource resource : _projectContentEntry.getBinaryResources()) {
      if (resource instanceof IModuleResource) {
        _binaries.put(resource.getPath(), (IModuleResource) resource);
      }
    }

    //
    _sources = new HashMap<String, IModuleResource>();
    for (IProjectContentResource resource : _projectContentEntry.getSourceResources()) {
      if (resource instanceof IModuleResource) {
        _sources.put(resource.getPath(), (IModuleResource) resource);
      }
    }

  }

  /**
   * <p>
   * </p>
   * 
   * @throws IOException
   */
  @Test
  public void testDefaultMovableUnitCreator() throws IOException {

    //
    DefaultMovableUnitCreator creator = new DefaultMovableUnitCreator();
    Set<IMovableUnit> movableUnits = creator.assignMovableUnits(_binaries, _sources);
    assertThat(movableUnits.size(), is(1));
    assertEquals("de/test/Bla.test", ((IMovableUnit) movableUnits.toArray()[0]).getAssociatedSourceResource().getPath());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws IOException
   */
  @Test
  public void testTypeMovableUnitCreator() throws IOException {

    //
    JTypeMovableUnitCreator creator = new JTypeMovableUnitCreator();
    Set<IMovableUnit> movableUnits = creator.assignMovableUnits(_binaries, _sources);
    assertThat(movableUnits.size(), is(1));
    assertEquals("de/test/Blubb.java", ((IMovableUnit) movableUnits.toArray()[0]).getAssociatedSourceResource()
        .getPath());

    List<? extends IModuleResource> binaries = ((IMovableUnit) movableUnits.toArray()[0])
        .getAssociatedBinaryResources();
    Collections.sort(binaries, new Comparator<IModuleResource>() {
      @Override
      public int compare(IModuleResource o1, IModuleResource o2) {
        return o1.getPath().compareTo(o2.getPath());
      }
    });
    assertEquals(3, binaries.size());
    assertEquals("de/test/Blubb$1.class", binaries.get(0).getPath());
    assertEquals("de/test/Blubb.class", binaries.get(1).getPath());
    assertEquals("de/test/InnerBlubbWithoutType.class", binaries.get(2).getPath());
  }

  @Test
  public void testDispatchingMovableUnitCreator() throws IOException {

    //
    DispatchingMovableUnitCreator creator = new DispatchingMovableUnitCreator();
    Set<IMovableUnit> movableUnits = creator.assignMovableUnits(_binaries, _sources);
    assertThat(movableUnits.size(), is(4));

    //
    List<? extends IMovableUnit> sortedUnits = sortMovableUnit(movableUnits);

    //
    assertEquals("de/test/Bla.test", sortedUnits.get(0).getAssociatedSourceResource().getPath());
    assertEquals("de/test/Blubb.java", sortedUnits.get(1).getAssociatedSourceResource().getPath());
    assertNull(sortedUnits.get(2).getAssociatedSourceResource());
    assertEquals("de/test/NurSource.txt", sortedUnits.get(3).getAssociatedSourceResource().getPath());
    
    
    List<? extends IModuleResource> binaries = sortBinaries(sortedUnits.get(0));
    assertEquals(1, binaries.size());
    assertEquals("de/test/Bla.test", binaries.get(0).getPath());
    
    binaries = sortBinaries(sortedUnits.get(1));
    assertEquals(3, binaries.size());
    assertEquals("de/test/Blubb$1.class", binaries.get(0).getPath());
    assertEquals("de/test/Blubb.class", binaries.get(1).getPath());
    assertEquals("de/test/InnerBlubbWithoutType.class", binaries.get(2).getPath());
    
    binaries = sortBinaries(sortedUnits.get(2));
    assertEquals(1, binaries.size());
    assertEquals("de/test/NurBinary.txt", binaries.get(0).getPath());
    
    binaries = sortBinaries(sortedUnits.get(3));
    assertEquals(0, binaries.size());
    

  }

  private List<? extends IMovableUnit> sortMovableUnit(Set<? extends IMovableUnit> units) {

    //
    List<? extends IMovableUnit> result = new LinkedList<IMovableUnit>(units);

    //
    Collections.sort(result, new Comparator<IMovableUnit>() {

      @Override
      public int compare(IMovableUnit o1, IMovableUnit o2) {

        //
        if (o1.hasAssociatedBinaryResources() && o2.hasAssociatedBinaryResources()) {
          return o1.getAssociatedBinaryResources().get(0).getPath()
              .compareTo(o2.getAssociatedBinaryResources().get(0).getPath());
        }

        //
        if (o1.hasAssociatedSourceResource() && o2.hasAssociatedSourceResource()) {
          return o1.getAssociatedSourceResource().getPath().compareTo(o2.getAssociatedSourceResource().getPath());
        }

        //
        if (o1.hasAssociatedSourceResource() && o2.hasAssociatedBinaryResources()) {
          return o1.getAssociatedSourceResource().getPath()
              .compareTo(o2.getAssociatedBinaryResources().get(0).getPath());
        }

        //
        return o1.getAssociatedBinaryResources().get(0).getPath().compareTo(o2.getAssociatedSourceResource().getPath());
      }
    });

    //
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param movableUnits
   * @return
   */
  private List<? extends IModuleResource> sortBinaries(IMovableUnit movableUnit) {
    
    //
    List<? extends IModuleResource> result = movableUnit.getAssociatedBinaryResources();
    
    //
    Collections.sort(result, new Comparator<IModuleResource>() {
      @Override
      public int compare(IModuleResource o1, IModuleResource o2) {
        return o1.getPath().compareTo(o2.getPath());
      }
    });
    
    //
    return result;
  }
}
