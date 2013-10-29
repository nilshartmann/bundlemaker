package org.bundlemaker.core.jtype.internal;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.common.collections.GenericCache;
import org.bundlemaker.core.jtype.ITypeResource;
import org.bundlemaker.core.project.IMovableUnit;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IModuleAwareMovableUnit;
import org.bundlemaker.core.spi.movableunit.AbstractMovableUnitCreator;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class JTypeMovableUnitCreator extends AbstractMovableUnitCreator {

  /**
   * <p>
   * </p>
   * 
   * @param binaries
   * @param sources
   * 
   * @return
   */
  public Set<IMovableUnit> assignMovableUnits(Map<String, IProjectContentResource> binaries,
      Map<String, IProjectContentResource> sources) {

    //
    GenericCache<IProjectContentResource, Set<IProjectContentResource>> cache = new GenericCache<IProjectContentResource, Set<IProjectContentResource>>() {
      @Override
      protected Set<IProjectContentResource> create(IProjectContentResource key) {
        return new HashSet<IProjectContentResource>();
      }
    };

    //
    for (IProjectContentResource moduleResource : binaries.values()) {

      //
      ITypeResource typeResource = moduleResource.adaptAs(ITypeResource.class);

      //
      if (typeResource != null) {

        //
        if (assignByType(moduleResource, typeResource, cache)) {
          continue;
        }

        if (assignBySourceName(moduleResource, typeResource, sources, cache)) {
          continue;
        }

        if (assignByName(moduleResource, typeResource, sources, cache)) {
          continue;
        }
      }
    }

    // create the result
    Set<IMovableUnit> result = new HashSet<IMovableUnit>();
    for (IProjectContentResource sourceResource : cache.keySet()) {
      result.add(createMovableUnit(sourceResource, new LinkedList<IProjectContentResource>(cache.get(sourceResource))));
    }

    //
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param moduleResource
   * @param typeResource
   * @param cache
   * @return
   */
  private boolean assignByType(IProjectContentResource moduleResource, ITypeResource typeResource,
      GenericCache<IProjectContentResource, Set<IProjectContentResource>> cache) {

    //
    Assert.isNotNull(moduleResource);
    Assert.isNotNull(typeResource);
    Assert.isNotNull(cache);

    // assign by type
    try {

      if (typeResource.containsTypes() && typeResource.getContainedType().hasSourceResource()) {
        cache.getOrCreate(typeResource.getContainedType().getSourceResource()).add(
            moduleResource.adaptAs(IModuleResource.class));
        return true;
      }

    } catch (CoreException e) {
      return false;
    }

    //
    return false;
  }

  /**
   * <p>
   * </p>
   * 
   * @param moduleResource
   * @param typeResource
   * @param sources
   * @param cache
   * @return
   */
  private boolean assignBySourceName(IProjectContentResource moduleResource, ITypeResource typeResource,
      Map<String, IProjectContentResource> sources, GenericCache<IProjectContentResource, Set<IProjectContentResource>> cache) {

    //
    String sourceName = typeResource.getSourceName();

    if (sourceName != null) {
      IPath path = new Path(moduleResource.getPath()).removeLastSegments(1).append(sourceName);
      cache.getOrCreate(sources.get(path.toString())).add(moduleResource.adaptAs(IModuleResource.class));
      return true;
    }

    //
    return false;
  }

  /**
   * <p>
   * </p>
   * 
   * @param moduleResource
   * @param typeResource
   * @param sources
   * @param cache
   * @return
   */
  private boolean assignByName(IProjectContentResource moduleResource, ITypeResource typeResource,
      Map<String, IProjectContentResource> sources, GenericCache<IProjectContentResource, Set<IProjectContentResource>> cache) {

    //
    if (moduleResource.getPath().endsWith(".class") && moduleResource.getPath().contains("$")) {

      String outer = moduleResource.getPath().substring(0, moduleResource.getPath().lastIndexOf('$'));
      String sourcePath = outer + ".java";
      if (sources.containsKey(sourcePath)) {
        cache.getOrCreate(sources.get(sourcePath)).add(moduleResource);
      }
      return true;
    }

    //
    return false;
  }
}
