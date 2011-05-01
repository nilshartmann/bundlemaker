/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.internal.parserold;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.parser.IParser;
import org.bundlemaker.core.parser.IResourceCache;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IResourceKey;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ParserCallable implements Callable<List<IProblem>> {

  /** - */
  private IFileBasedContent  _content;

  /** the package fragments */
  private List<IResourceKey> _resources;

  /** the list of all errors */
  private List<IProblem>     _errors;

  /** - */
  private IParser            _parser;

  /** - */
  private IResourceCache     _resourceCache;

  /** - */
  private IProgressMonitor   _progressMonitor;

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
  public ParserCallable(IFileBasedContent content, List<IResourceKey> resources, IParser parser,
      IResourceCache resourceCache, IProgressMonitor progressMonitor) {

    //
    Assert.isNotNull(content);
    Assert.isNotNull(resources);
    Assert.isNotNull(parser);
    Assert.isNotNull(resourceCache);

    //
    _content = content;

    // set the directories to parse
    _resources = resources;

    //
    _parser = parser;

    //
    _resourceCache = resourceCache;

    //
    _progressMonitor = progressMonitor;
  }

  /**
   * {@inheritDoc}
   */
  public List<IProblem> call() throws Exception {

    //
    _errors = new LinkedList<IProblem>();

    // parse
    _errors.addAll(_parser.parseResources(_content, _resources, _resourceCache, _progressMonitor));

    // return the errors
    return _errors;
  }
}
