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
package org.bundlemaker.core.parser;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.resource.IProjectContentResource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractParser implements IParser {

  /** - */
  private List<IProblem> _problems;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected List<IProblem> getProblems() {
    return _problems;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parseBundleMakerProjectStart(IBundleMakerProject bundleMakerProject) {
    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parseBundleMakerProjectStop(IBundleMakerProject bundleMakerProject) {
    // ignore
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceKey
   * @param cache
   */
  public List<IProblem> parseResource(IProjectContentEntry content, IProjectContentResource resource,
      IResourceCache cache) {

    // Reset problem list
    _problems = new LinkedList<IProblem>();

    // do the parsing
    doParseResource(content, resource, cache);

    //
    return _problems;
  }

  /**
   * Override in subclasses to implement parse logic
   * 
   * @param content
   * @param resource
   * @param cache
   */
  protected abstract void doParseResource(IProjectContentEntry content, IProjectContentResource resource,
      IResourceCache cache);

  /**
   * <p>
   * </p>
   * 
   * @param resourceKey
   * @return
   */
  public abstract boolean canParse(IProjectContentResource resourceKey);

}
