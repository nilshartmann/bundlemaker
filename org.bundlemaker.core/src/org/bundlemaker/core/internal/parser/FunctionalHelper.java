package org.bundlemaker.core.internal.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.internal.Activator;
import org.bundlemaker.core.internal.projectdescription.FileBasedContent;
import org.bundlemaker.core.internal.resource.Reference;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.bundlemaker.core.internal.resource.Type;
import org.bundlemaker.core.parser.IParser;
import org.bundlemaker.core.parser.IParser.ParserType;
import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

public class FunctionalHelper {

  static void parseNewOrModifiedResources(FileBasedContent fileBasedContent, Collection<ResourceStandin> resources,
      ResourceCache resourceCache, ParserType parserType, IParser[] parsers, IProgressMonitor monitor)
      throws CoreException {

    for (int i = 0; i < parsers.length; i++) {

      IParser parser = parsers[i];

      if (parser.getParserType().equals(parserType)) {

        for (ResourceStandin resourceStandin : resources) {

          // check if the operation has been canceled
          FunctionalHelper.checkIfCanceled(monitor);

          //
          if (parser.canParse(resourceStandin)) {
            parser.parseResource(fileBasedContent, resourceStandin, resourceCache);
          }

          monitor.worked(1);
        }
      }
    }
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
  static Set<ResourceStandin> computeNewAndModifiedResources(Collection<ResourceStandin> resourceStandins,
      Map<IResourceKey, Resource> storedResourcesMap, ResourceCache resourceCache, IProgressMonitor monitor) {

    //
    monitor.beginTask("", resourceStandins.size());

    //
    Set<ResourceStandin> result;

    try {

      result = new HashSet<ResourceStandin>();

      //
      for (ResourceStandin resourceStandin : resourceStandins) {

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

  static void associateResourceStandinsWithResources(Collection<ResourceStandin> resourceStandins,
      Map<IResourceKey, Resource> map, boolean isSource, IProgressMonitor monitor) {

    Assert.isNotNull(resourceStandins);
    Assert.isNotNull(map);
    Assert.isNotNull(monitor);

    //
    for (ResourceStandin resourceStandin : resourceStandins) {

      // check if the operation has been canceled
      checkIfCanceled(monitor);

      // get the associated resource
      Resource resource = map.get(resourceStandin);

      if (resource == null) {
        throw new RuntimeException(resourceStandin.toString());
      }

      // set up the resource stand-in
      setupResourceStandin(resourceStandin, resource, isSource);

      // perform some checks
      // TODO: MAYBE REMOVE?
      Assert.isNotNull(resourceStandin.getResource());
      for (IType type : resource.getContainedTypes()) {
        Assert.isNotNull(type.getBinaryResource(), resourceStandin.toString());
        Assert.isTrue(type.hasBinaryResource());
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
  static void setupResourceStandin(ResourceStandin resourceStandin, Resource resource, boolean isSource) {

    Assert.isNotNull(resourceStandin);
    Assert.isNotNull(resource, "No resource for " + resourceStandin.toString());

    // associate resource and resource stand-in...
    resourceStandin.setResource(resource);
    // ... and set the opposite
    resource.setResourceStandin(resourceStandin);

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

  static boolean hasToBeReparsed(ResourceStandin resourceStandin, Resource resource) {

    // resource has to be re-parsed if no resource was stored in the database
    if (resource == null) {
      return true;
    }

    // check the time stamp
    if (resource.getTimestamp() != resourceStandin.getTimestamp()) {

      // we can additionally check the hash values...
      if (Activator.ENABLE_HASHVALUES_FOR_COMPARISON) {
        if (!resourceStandin.hasHashvalue()) {
          resourceStandin.computeHashvalue();
        }

        byte[] storedResourceHashValue = resource.getHashvalue();
        byte[] resourceStandinHashValue = resourceStandin.getHashvalue();
        if (!Arrays.equals(storedResourceHashValue, resourceStandinHashValue)) {
          return true;
        }
      }
      
      //
      else {
        return true;
      }
    }

    //
    return false;
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
