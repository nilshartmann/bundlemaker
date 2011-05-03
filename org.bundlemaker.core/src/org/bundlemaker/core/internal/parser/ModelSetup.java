package org.bundlemaker.core.internal.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.FutureTask;

import org.bundlemaker.core.internal.Activator;
import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.internal.parserold.ProjectParser;
import org.bundlemaker.core.internal.projectdescription.FileBasedContent;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.bundlemaker.core.internal.store.IDependencyStore;
import org.bundlemaker.core.internal.store.IPersistentDependencyStore;
import org.bundlemaker.core.parser.IParser;
import org.bundlemaker.core.parser.IParserFactory;
import org.bundlemaker.core.resource.IResourceKey;
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
  private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();
  // private static final int   THREAD_COUNT             = 1;

  // /** the list of all errors */
  // public List<IProblem> _errors;

  /** the bundle maker project */
  private BundleMakerProject _bundleMakerProject;

  /**  */
  private List<IParser[]>    _parsers4threads;

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

        System.out.println(" ****** COMPARE COMPLETED ");

        resourceCache.setupTypeCache(fileBasedContent);

        System.out.println(" ****** setupTypeCache COMPLETED ");

        multiThreadedReparse(storedResourcesMap, newAndModifiedSourceResources,
            newAndModifiedBinaryResources, resourceCache, fileBasedContent, monitor);

        System.out.println(" ****** REPARSE COMPLETED ");
      }
    }

    // step 4: update dependency store
    resourceCache.commit(monitor);
    // deleteResourcesFromDependencyStore(storedResourcesMap.values());

    //
    Map<IResourceKey, Resource> newMap = resourceCache.getCombinedMap();

    // step 5: setup the resource content

    // step 5.1: set up binary resources
    FunctionalHelper.associateResourceStandinsWithResources(_bundleMakerProject.getBinaryResourceStandins(), newMap,
        false, monitor);

    // step 5.2: set up binary resources
    FunctionalHelper.associateResourceStandinsWithResources(_bundleMakerProject.getSourceResourceStandins(), newMap,
        true, monitor);

    //
    notifyParseStop();
  }

  private void multiThreadedReparse(Map<IResourceKey, Resource> storedResourcesMap,
      Collection<ResourceStandin> sourceResources, Collection<ResourceStandin> binaryResources,
      ResourceCache resourceCache, FileBasedContent fileBasedContent, IProgressMonitor monitor) {

    //
    List<ResourceStandin>[] sourceSublists = splitIntoSublists(new ArrayList<ResourceStandin>(sourceResources));
    List<ResourceStandin>[] binarySublists = splitIntoSublists(new ArrayList<ResourceStandin>(binaryResources));

    // set up the callables
    CallableReparse[] callables = new CallableReparse[THREAD_COUNT];
    for (int i = 0; i < callables.length; i++) {
      callables[i] = new CallableReparse(fileBasedContent, sourceSublists[i], binarySublists[i],
          _parsers4threads.get(i), storedResourcesMap, resourceCache, monitor);
    }

    // create the future tasks
    FutureTask<Void>[] futureTasks = new FutureTask[THREAD_COUNT];
    for (int i = 0; i < futureTasks.length; i++) {
      futureTasks[i] = new FutureTask<Void>(callables[i]);
      new Thread(futureTasks[i]).start();
    }

    // collect the result
    for (int i = 0; i < futureTasks.length; i++) {
      try {
        futureTasks[i].get();
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param list
   * @return
   */
  private List<ResourceStandin>[] splitIntoSublists(List<ResourceStandin> list) {

    // compute the part size
    float partSizeAsFloat = list.size() / (float) THREAD_COUNT;
    int partSize = (int) Math.ceil(partSizeAsFloat);

    // split the package list in n sublist (one for each thread)
    List<ResourceStandin>[] sublists = new List[THREAD_COUNT];
    for (int i = 0; i < THREAD_COUNT; i++) {
      if ((i + 1) * partSize <= list.size()) {
        sublists[i] = list.subList(i * partSize, (i + 1) * partSize);
      } else if ((i) * partSize <= list.size()) {
        sublists[i] = list.subList(i * partSize, list.size());
      } else {
        sublists[i] = Collections.emptyList();
      }
    }

    // sub lists
    return sublists;
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
    List<IParser[]> parsers4threads = new LinkedList<IParser[]>();
    for (int i = 0; i < THREAD_COUNT; i++) {
      parsers4threads.add(new IParser[parserFactories.size()]);
    }

    // ... setup
    for (IParser[] parsers : parsers4threads) {
      for (int i = 0; i < parsers.length; i++) {
        parsers[i] = parserFactories.get(i).createParser(_bundleMakerProject, _parseIndirectReferences);
      }
    }

    // TODO
    // // first the source parsers
    // int position = 0;
    // for (int i = 0; i < parserFactories.size(); i++) {
    // if (!parsers4threads[i][0].getParserType().equals(ParserType.BINARY)) {
    // for (int j = 0; j < THREAD_COUNT; j++) {
    // _parsers[position][j] = parsers4threads[i][j];
    // }
    // position++;
    // }
    // }
    //
    // // then the binary parsers
    // for (int i = 0; i < parserFactories.size(); i++) {
    // if (parsers4threads[i][0].getParserType().equals(ParserType.BINARY)) {
    // for (int j = 0; j < THREAD_COUNT; j++) {
    // _parsers[position][j] = parsers4threads[i][j];
    // }
    // position++;
    // }
    // }

    // assign
    _parsers4threads = parsers4threads;
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  private void notifyParseStart() throws CoreException {

    //
    for (IParser[] parsers : _parsers4threads) {
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
    for (IParser[] parsers : _parsers4threads) {
      for (IParser parser : parsers) {

        // notify 'stop'
        parser.parseBundleMakerProjectStop(_bundleMakerProject);
      }
    }
  }
}
