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
import org.bundlemaker.core.resource.IResourceKey;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractParser implements IParser {

  private List<IProblem> _problems;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractParser}.
   * </p>
   */
  public AbstractParser() {

    //
    _problems = new LinkedList<IProblem>();
  }

  @Override
  public List<IProblem> getProblems() {
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
  public void parseResource(IProjectContentEntry content, IResourceKey resource, IResourceCache cache) {
    // Reset problem list
    _problems.clear();

    // do the parsing

  }

  /**
   * Override in subclasses to implement parse logic
   * 
   * @param content
   * @param resource
   * @param cache
   */
  protected abstract void doParseResource(IProjectContentEntry content, IResourceKey resource, IResourceCache cache);

  /**
   * <p>
   * </p>
   * 
   * @param resourceKey
   * @return
   */
  public abstract boolean canParse(IResourceKey resourceKey);

}
