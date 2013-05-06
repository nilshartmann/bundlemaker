package org.bundlemaker.core.internal.parser;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.IProblem;
import org.bundlemaker.core._type.IType;
import org.bundlemaker.core.internal.projectdescription.IResourceStandin;
import org.bundlemaker.core.internal.resource.Reference;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.bundlemaker.core.internal.resource.Type;
import org.bundlemaker.core.parser.IParser;
import org.bundlemaker.core.parser.IParser.ParserType;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.resource.IParsableResource;
import org.bundlemaker.core.resource.IProjectContentResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

public class FunctionalHelper {

  static List<IProblem> parseNewOrModifiedResources(IProjectContentEntry content,
      Collection<IResourceStandin> resources, ResourceCache resourceCache, ParserType parserType, IParser[] parsers,
      IProgressMonitor monitor) throws CoreException {

    //
    List<IProblem> result = new LinkedList<IProblem>();

    //
    for (int i = 0; i < parsers.length; i++) {

      IParser parser = parsers[i];

      if (parser.getParserType().equals(parserType)) {

        for (IResourceStandin resourceStandin : resources) {

          // check if the operation has been canceled
          FunctionalHelper.checkIfCanceled(monitor);

          // get the IModifiableResource
          IParsableResource resource = resourceCache.getOrCreateResource(resourceStandin);

          //
          if (parser.canParse(resource)) {
            List<IProblem> problems = parser.parseResource(content, resource, resourceCache);
            result.addAll(problems);
            resourceCache.getOrCreateResource(resourceStandin).setErroneous(!problems.isEmpty());
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
  static Set<IResourceStandin> computeNewAndModifiedResources(Collection<IResourceStandin> resourceStandins,
      Map<IProjectContentResource, Resource> storedResourcesMap, ResourceCache resourceCache, IProgressMonitor monitor) {

    //
    monitor.beginTask("", resourceStandins.size());

    //
    Set<IResourceStandin> result;

    try {

      result = new HashSet<IResourceStandin>();

      //
      for (IResourceStandin resourceStandin : resourceStandins) {

        // check if the operation has been canceled
        checkIfCanceled(monitor);

        // get the associated resource
        Resource resource = storedResourcesMap.remove(resourceStandin);

        // add if resource has to be re-parsed
        if (hasToBeReparsed(resourceStandin, resource)) {
          result.add(resourceStandin);
        } else {
          resourceCache.addToStoredResourcesMap(resource, resource);
        }

        monitor.worked(1);
      }

    } finally {
      monitor.done();
    }

    // return the result
    return result;
  }

  static boolean failOnMissingBinaries() {
    return Boolean.getBoolean("org.bundlemaker.ignoreMissingBinaries") == false;
  }

  static void associateResourceStandinsWithResources(Collection<IResourceStandin> resourceStandins,
      Map<IProjectContentResource, Resource> map, boolean isSource, IProgressMonitor monitor) {

    Assert.isNotNull(resourceStandins);
    Assert.isNotNull(map);
    Assert.isNotNull(monitor);

    //
    for (IResourceStandin resourceStandin : resourceStandins) {

      // check if the operation has been canceled
      checkIfCanceled(monitor);

      // get the associated resource
      Resource resource = map.get(resourceStandin);

      if (resource == null) {
        throw new RuntimeException(resourceStandin.toString());
      }

      // set up the resource stand-in
      setupResourceStandin(resourceStandin, resource, isSource);

      final boolean failOnMissingBinaries = failOnMissingBinaries();

      // perform some checks
      // TODO: MAYBE REMOVE?
      Assert.isNotNull(((ResourceStandin) resourceStandin).getResource());
      for (IType type : resource.getContainedTypes()) {
        if (!type.hasBinaryResource()) {
          String message = "For source file "
              + resourceStandin.getDirectory()
              + "/"
              + resourceStandin.getName()
              + " there is no binary (class) file";
          if (failOnMissingBinaries) {
            throw new IllegalStateException(
                message
                    + "\nPlease make sure, that your binary paths contains classes for all sources in your project's source folders.");
          } else {
            System.err.println("WARNING! " + message);
          }
        }
        // Assert.isNotNull(type.getBinaryResource(), resourceStandin.toString());
        if (isSource) {
          Assert.isTrue(resourceStandin.equals(type.getSourceResource()),
              resourceStandin + " : " + type.getSourceResource());
        } else {
          Assert.isTrue(resourceStandin.equals(type.getBinaryResource()),
              resourceStandin + " : " + type.getBinaryResource());
        }
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceStandin
   * @param map
   */
  static void setupResourceStandin(IResourceStandin resourceStandin, Resource resource, boolean isSource) {

    Assert.isNotNull(resourceStandin);
    Assert.isNotNull(resource, "No resource for " + resourceStandin.toString());

    // associate resource and resource stand-in...
    ((ResourceStandin) resourceStandin).setResource(resource);
    // ... and set the opposite
    resource.setResourceStandin((ResourceStandin) resourceStandin);

    // set the references
    Set<Reference> resourceReferences = new HashSet<Reference>();
    for (Reference reference : resource.getModifiableReferences()) {
      Reference newReference = new Reference(reference);
      newReference.setResource(resource);
      resourceReferences.add(newReference);
    }
    resource.getModifiableReferences().clear();
    resource.getModifiableReferences().addAll(resourceReferences);

    // set the type-back-references
    for (Type type : resource.getModifiableContainedTypes()) {

      //
      if (isSource) {
        type.setSourceResource(resource);
      } else {
        type.setBinaryResource(resource);
      }

      // set the references
      Map<String, Reference> typeReferences = new HashMap<String, Reference>();
      for (Reference reference : type.getModifiableReferences()) {
        // TODO
        if (reference == null) {
          continue;
        }
        Reference newReference = new Reference(reference);
        newReference.setType(type);
        if (typeReferences.containsKey(newReference)) {
          throw new RuntimeException();
        } else {
          typeReferences.put(newReference.getFullyQualifiedName(), newReference);
        }
      }
      type.getModifiableReferences().clear();
      type.getModifiableReferences().addAll(typeReferences.values());
    }
  }

  static boolean hasToBeReparsed(IResourceStandin resourceStandin, Resource resource) {

    // resource has to be re-parsed if no resource was stored in the database
    if (resource == null) {
      return true;
    }

    //
    if (resource.isErroneous()) {
      return true;
    }

    // String root = resourceStandin.getRoot();
    // String resourceRoot = resource.getRoot();
    //
    // // System.out.println("root: '" + root + "', resourceroot: '" + resourceRoot + "'");
    //
    // if (root != null &&
    // root.endsWith(".jar")) {
    //
    // File rootFile = new File(root);
    // File resourceRootFile = new File(resourceRoot);
    //
    // if (rootFile.getName().equals(resourceRootFile.getName())) {
    // return false;
    // }
    // }
    //
    // check the time stamp
    return resource.getTimestamp() != resourceStandin.getTimestamp();
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
