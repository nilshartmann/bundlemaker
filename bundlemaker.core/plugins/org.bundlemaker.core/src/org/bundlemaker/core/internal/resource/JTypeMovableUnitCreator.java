package org.bundlemaker.core.internal.resource;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core._type.ITypeResource;
import org.bundlemaker.core.common.collections.GenericCache;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.bundlemaker.core.resource.IMovableUnitCreator;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class JTypeMovableUnitCreator implements IMovableUnitCreator {

  /**
   * <p>
   * </p>
   * @param binaries
   * @param sources
   * 
   * @return
   */
  public Set<IMovableUnit> assignMovableUnits(Map<String, IModuleResource> binaries,
      Map<String, IModuleResource> sources) {

    //
    GenericCache<IModuleResource, Set<IModuleResource>> cache = new GenericCache<IModuleResource, Set<IModuleResource>>() {
      @Override
      protected Set<IModuleResource> create(IModuleResource key) {
        return new HashSet<IModuleResource>();
      }
    };

    //
    for (IModuleResource moduleResource : binaries.values()) {

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
    for (IModuleResource sourceResource : cache.keySet()) {
      result.add(new MovableUnit(sourceResource, new LinkedList<IModuleResource>(cache
          .get(sourceResource))));
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
  private boolean assignByType(IModuleResource moduleResource, ITypeResource typeResource,
      GenericCache<IModuleResource, Set<IModuleResource>> cache) {

    //
    Assert.isNotNull(moduleResource);
    Assert.isNotNull(typeResource);
    Assert.isNotNull(cache);

    // assign by type
    try {

      if (typeResource.containsTypes() && typeResource.getContainedType().hasSourceResource()) {
        cache.getOrCreate(typeResource.getContainedType().getSourceResource()).add(moduleResource);
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
  private boolean assignBySourceName(IModuleResource moduleResource, ITypeResource typeResource,
      Map<String, IModuleResource> sources, GenericCache<IModuleResource, Set<IModuleResource>> cache) {

    //
    String sourceName = typeResource.getSourceName();

    if (sourceName != null) {
      IPath path = new Path(moduleResource.getPath()).removeLastSegments(1).append(sourceName);
      cache.getOrCreate(sources.get(path.toString())).add(moduleResource);
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
  private boolean assignByName(IModuleResource moduleResource, ITypeResource typeResource,
      Map<String, IModuleResource> sources, GenericCache<IModuleResource, Set<IModuleResource>> cache) {

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
