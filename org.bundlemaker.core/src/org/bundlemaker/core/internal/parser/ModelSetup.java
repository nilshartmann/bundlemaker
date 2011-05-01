package org.bundlemaker.core.internal.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.internal.Activator;
import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.internal.parserold.ProjectParser;
import org.bundlemaker.core.internal.projectdescription.FileBasedContent;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.bundlemaker.core.internal.store.IDependencyStore;
import org.bundlemaker.core.internal.store.IPersistentDependencyStore;
import org.bundlemaker.core.parser.IParser;
import org.bundlemaker.core.parser.IParser.ParserType;
import org.bundlemaker.core.parser.IParserFactory;
import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.util.MemoryUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModelSetup {

  /** THREAD_COUNT */
  private static final int   THREAD_COUNT             = Runtime.getRuntime().availableProcessors();

  // /** the list of all errors */
  // public List<IProblem> _errors;

  /** the bundle maker project */
  private BundleMakerProject _bundleMakerProject;

  /** the parser array: the first index is the parser, the second the thread */
  private IParser[][]        _parsers;

  /** - */
  private boolean            _parseIndirectReferences = true;

  /**
   * <p>
   * Creates a new instance of type {@link ProjectParser}.
   * </p>
   * 
   * @param bundleMakerProject
   *          the bundle maker project
   */
  public ModelSetup(BundleMakerProject bundleMakerProject) {
    Assert.isNotNull(bundleMakerProject);

    // set the project
    _bundleMakerProject = bundleMakerProject;
  }

  /**
   * <p>
   * </p>
   * 
   * @param modifiableFileBasedContent
   * @param dependencyStore
   */
  public void setup(List<FileBasedContent> fileBasedContents, IPersistentDependencyStore dependencyStore,
      IProgressMonitor monitor) throws OperationCanceledException, CoreException {

    Assert.isNotNull(fileBasedContents);
    Assert.isNotNull(dependencyStore);

    // step 1: create new null monitor if necessary
    if (monitor == null) {
      monitor = new NullProgressMonitor();
    }

    //
    setupParsers();

    //
    notifyParseStart();

    // step 2: reads all the resources from the underlying dependency store and puts it in a map
    Map<IResourceKey, Resource> storedResourcesMap = readFromDependencyStore(dependencyStore, monitor);

    // step 3: create the resource cache that holds all resources that must be stored
    ResourceCache resourceCache = new ResourceCache(dependencyStore);

    // step 4: perform up-to-date check and parse new or modified resources
    for (FileBasedContent fileBasedContent : fileBasedContents) {

      // TODO: perform in multiple threads
      {

        // we only have check resource content
        if (fileBasedContent.isResourceContent()) {

          // step 4.1: compute new and modified resources
          Set<ResourceStandin> newAndModifiedBinaryResources = FunctionalHelper.computeNewAndModifiedResources(
              fileBasedContent.getModifiableBinaryResources(), storedResourcesMap, resourceCache, monitor);

          Set<ResourceStandin> newAndModifiedSourceResources = FunctionalHelper.computeNewAndModifiedResources(
              fileBasedContent.getModifiableSourceResources(), storedResourcesMap, resourceCache, monitor);

          // step 4.2:
          for (ResourceStandin resourceStandin : newAndModifiedBinaryResources) {
            resourceCache.getOrCreateResource(resourceStandin);
          }

          for (ResourceStandin resourceStandin : newAndModifiedSourceResources) {
            resourceCache.getOrCreateResource(resourceStandin);
          }

          // step 4.3:
          if (!(newAndModifiedBinaryResources.isEmpty() && newAndModifiedSourceResources.isEmpty())) {
            resourceCache.setupTypeCache(fileBasedContent);
          }

          // step 4.4: set up binary resources
          if (!newAndModifiedBinaryResources.isEmpty()) {
            parseNewOrModifiedResources(fileBasedContent, newAndModifiedBinaryResources, resourceCache,
                ParserType.BINARY, monitor);
          }

          // step 4.5: set up source resources
          if (!newAndModifiedSourceResources.isEmpty()) {
            parseNewOrModifiedResources(fileBasedContent, newAndModifiedSourceResources, resourceCache,
                ParserType.SOURCE, monitor);
          }
        }

      }
    }

    // step 4: update dependency store
    resourceCache.commit(monitor);
    // deleteResourcesFromDependencyStore(storedResourcesMap.values());

    //
    Map<IResourceKey, Resource> newMap = resourceCache.getCombinedMap();

    // step 5: setup the resource content
    // TODO: perform in multiple threads
    for (FileBasedContent fileBasedContent : fileBasedContents) {

      // TODO: perform in multiple threads
      // we only have to set up resources for resource content
      if (fileBasedContent.isResourceContent()) {

        // step 5.1: set up binary resources
        FunctionalHelper.associateResourceStandinsWithResources(fileBasedContent.getModifiableBinaryResources(),
            newMap, false, monitor);

        // step 5.2: set up binary resources
        FunctionalHelper.associateResourceStandinsWithResources(fileBasedContent.getModifiableSourceResources(),
            newMap, true, monitor);
      }
    }

    //
    notifyParseStop();

    // // TODO: REMOVE?
    // // ALWAYS assert a binary resource for the contained types
    // for (ResourceStandin resourceStandin : fileBasedContent.getModifiableBinaryResources()) {
    // for (IType type : resourceStandin.getContainedTypes()) {
    // Assert.isNotNull(type.getBinaryResource(), "No binary resource for type " + type.getFullyQualifiedName());
    // }
    // }
  }

  private void parseNewOrModifiedResources(FileBasedContent fileBasedContent, Set<ResourceStandin> resources,
      ResourceCache resourceCache, ParserType parserType, IProgressMonitor monitor) throws CoreException {

    for (int i = 0; i < _parsers.length; i++) {

      IParser parser = _parsers[i][0];

      if (parser.getParserType().equals(parserType)) {

        for (ResourceStandin resourceStandin : resources) {

          
          // check if the operation has been canceled
          FunctionalHelper.checkIfCanceled(monitor);

          //
          if (parser.canParse(resourceStandin)) {
            System.out.println(resourceStandin.toString() + " : " + MemoryUtils.getMemoryUsage());
            parser.parseResource(fileBasedContent, resourceStandin, resourceCache);
          }
        }
      }
    }
  }

  /**
   * <p>
   * Reads all resources from the underlying dependency store.
   * </p>
   * 
   * @param dependencyStore
   * @param monitor
   * @return
   */
  private static Map<IResourceKey, Resource> readFromDependencyStore(IDependencyStore dependencyStore,
      IProgressMonitor monitor) {

    Assert.isNotNull(dependencyStore);
    Assert.isNotNull(monitor);

    Map<IResourceKey, Resource> map = new HashMap<IResourceKey, Resource>();

    if (dependencyStore != null) {

      List<Resource> resources = dependencyStore.getResources();

      monitor.beginTask("Opening database ", resources.size());

      for (Resource resource : resources) {

        // check if canceled
        // checkIfCanceled(monitor);

        // put in the map
        map.put(resource, resource);

        // set monitor
        monitor.worked(1);
      }
    }

    // work is done
    monitor.done();

    // return the map
    return map;
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  private void setupParsers() throws CoreException {

    // get the registered parser factories
    List<IParserFactory> parserFactories = Activator.getDefault().getParserFactoryRegistry().getParserFactories();

    // no parsers defined
    if (parserFactories.isEmpty()) {
      throw new RuntimeException("No parserFactories defined...");
    }

    // create one parser for each thread...
    IParser[][] parsers = new IParser[parserFactories.size()][THREAD_COUNT];
    _parsers = new IParser[parserFactories.size()][THREAD_COUNT];

    // ... setup
    for (int i = 0; i < parserFactories.size(); i++) {
      for (int j = 0; j < THREAD_COUNT; j++) {
        parsers[i][j] = parserFactories.get(i).createParser(_bundleMakerProject, _parseIndirectReferences);
      }
    }

    // first the source parsers
    int position = 0;
    for (int i = 0; i < parserFactories.size(); i++) {
      if (!parsers[i][0].getParserType().equals(ParserType.BINARY)) {
        for (int j = 0; j < THREAD_COUNT; j++) {
          _parsers[position][j] = parsers[i][j];
        }
        position++;
      }
    }

    // then the binary parsers
    for (int i = 0; i < parserFactories.size(); i++) {
      if (parsers[i][0].getParserType().equals(ParserType.BINARY)) {
        for (int j = 0; j < THREAD_COUNT; j++) {
          _parsers[position][j] = parsers[i][j];
        }
        position++;
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  private void notifyParseStart() throws CoreException {

    //
    for (IParser[] parsers : _parsers) {
      for (IParser parser : parsers) {

        // notify 'start'
        parser.parseBundleMakerProjectStart(_bundleMakerProject);
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  private void notifyParseStop() throws CoreException {

    //
    for (IParser[] parsers : _parsers) {
      for (IParser parser : parsers) {

        // notify 'stop'
        parser.parseBundleMakerProjectStop(_bundleMakerProject);
      }
    }
  }
}
