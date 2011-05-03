package org.bundlemaker.core.internal.parser;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.bundlemaker.core.internal.parserold.ParserCallable;
import org.bundlemaker.core.internal.projectdescription.FileBasedContent;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.bundlemaker.core.parser.IParser;
import org.bundlemaker.core.parser.IParser.ParserType;
import org.bundlemaker.core.resource.IResourceKey;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;

public class CallableReparse implements Callable<Void> {

  /** - */
  private ResourceCache               _resourceCache;

  /** - */
  private IProgressMonitor            _progressMonitor;

  /** - */
  private FileBasedContent            _content;

  /** - */
  private Collection<ResourceStandin> _sourceResources;

  /** - */
  private Collection<ResourceStandin> _binaryResources;

  /** - */
  private Map<IResourceKey, Resource> _storedResourcesMap;

  // /** the list of all errors */
  // private List<IProblem> _errors;

  /** - */
  private IParser[]                   _parser;

  /**
   * <p>
   * Creates a new instance of type {@link ParserCallable}.
   * </p>
   * 
   * @param content
   * @param resources
   * @param parser
   * @param resourceCache
   */
  public CallableReparse(FileBasedContent content, Collection<ResourceStandin> sourceResources,
      Collection<ResourceStandin> binaryResources, IParser[] parser, Map<IResourceKey, Resource> storedResourcesMap,
      ResourceCache resourceCache, IProgressMonitor progressMonitor) {

    //
    Assert.isNotNull(content);
    Assert.isNotNull(sourceResources);
    Assert.isNotNull(binaryResources);
    Assert.isNotNull(parser);
    Assert.isNotNull(resourceCache);
    Assert.isNotNull(progressMonitor);

    //
    _content = content;

    // set the directories to parse
    _sourceResources = sourceResources;
    _binaryResources = binaryResources;

    //
    _parser = parser;

    //
    _resourceCache = resourceCache;

    //
    _storedResourcesMap = storedResourcesMap;

    //
    _progressMonitor = progressMonitor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Void call() throws Exception {

    // step 4.4: set up binary resources
    FunctionalHelper.parseNewOrModifiedResources(_content, _binaryResources, _resourceCache, ParserType.BINARY,
        _parser, _progressMonitor);

    // step 4.5: set up source resources
    FunctionalHelper.parseNewOrModifiedResources(_content, _sourceResources, _resourceCache, ParserType.SOURCE,
        _parser, _progressMonitor);

    return null;
  }
}
