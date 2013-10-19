package org.bundlemaker.core.internal.parser;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.bundlemaker.core.parser.IProblem;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.project.internal.IResourceStandinNEW;
import org.bundlemaker.core.spi.parser.IParsableResource;
import org.bundlemaker.core.spi.parser.IParser;
import org.bundlemaker.core.spi.parser.IParser.ParserType;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

public class FunctionalHelper {

  static List<IProblem> parseNewOrModifiedResources(IProjectContentEntry content,
      Collection<IResourceStandinNEW> resources, ResourceCache resourceCache, ParserType parserType, IParser[] parsers,
      IProgressMonitor monitor) throws CoreException {

    //
    List<IProblem> result = new LinkedList<IProblem>();

    //
    for (int i = 0; i < parsers.length; i++) {

      IParser parser = parsers[i];

      if (parser.getParserType().equals(parserType)) {

        for (IResourceStandinNEW resourceStandin : resources) {

          // check if the operation has been canceled
          FunctionalHelper.checkIfCanceled(monitor);

          // get the IModifiableResource
          IParsableResource resource = resourceCache.getOrCreateResource(resourceStandin);

          //
          if (parser.canParse(resource)) {
            ((Resource) resource).storeCurrentTimestamp();
            List<IProblem> problems = parser.parseResource(content, resource, resourceStandin.isAnalyzeReferences(),
                true);
            result.addAll(problems);
            resource.setErroneous(!problems.isEmpty());
          }

          monitor.worked(1);
        }
      }
    }

    //
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceStandins
   * @param storedResourcesMap
   * @param resourceCacheResources
   * @param monitor
   * @return
   */
  static Set<IResourceStandinNEW> computeNewAndModifiedResources(Collection<IResourceStandinNEW> resourceStandins,
      Map<IProjectContentResource, Resource> storedResourcesMap, ResourceCache resourceCache, IProgressMonitor monitor) {

    //
    monitor.beginTask("", resourceStandins.size());

    //
    Set<IResourceStandinNEW> result;

    try {

      result = new HashSet<IResourceStandinNEW>();

      //
      for (IResourceStandinNEW resourceStandin : resourceStandins) {

        // check if the operation has been canceled
        checkIfCanceled(monitor);

        // get the associated resource
        Resource resource = storedResourcesMap.remove(resourceStandin);

        // add if resource has to be re-parsed
        if (hasToBeReparsed(resourceStandin, resource)) {
          resource = (Resource) resourceCache.getOrCreateResource(resourceStandin);
          result.add(resourceStandin);
        }

        // associate resource and resource stand-in...
        ((ResourceStandin) resourceStandin).setResource(resource);
        resource.setResourceStandin((ResourceStandin) resourceStandin);

        monitor.worked(1);
      }

    } finally {
      monitor.done();
    }

    // return the result
    return result;
  }

  static boolean failOnMissingBinaries() {
    return false;
    // Boolean.getBoolean("org.bundlemaker.ignoreMissingBinaries") == false;
  }

  static boolean hasToBeReparsed(IProjectContentResource resourceStandin, Resource resource) {

    // resource has to be re-parsed if no resource was stored in the database
    if (resource == null) {
      return true;
    }

    //
    if (resource.isErroneous()) {
      return true;
    }

    //
    if (resourceStandin.isAnalyzeReferences() != resource.isAnalyzeReferences()) {
      return true;
    }

    // check the time stamp
    return resource.getLastParsedTimestamp() != resourceStandin.getCurrentTimestamp();
  }

  /**
   * <p>
   * Throws an {@link OperationCanceledException} if the underlying {@link IProgressMonitor} has been canceled.
   * </p>
   * 
   * @param monitor
   *          the monitor
   * @throws OperationCanceledException
   */
  static void checkIfCanceled(IProgressMonitor monitor) throws OperationCanceledException {
    if (monitor != null && monitor.isCanceled()) {
      throw new OperationCanceledException();
    }
  }

  /**
   * <p>
   * Deletes all given resources from the underlying dependency store.
   * </p>
   * 
   * @param resources
   */
  static void deleteResourcesFromDependencyStore(Collection<Resource> resources) {
    System.out.println("RESOURCES TO DELETE FROM DATABASE: " + resources);
  }
}
