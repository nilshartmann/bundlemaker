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
package org.bundlemaker.core.spi.parser;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.parser.IParserAwareBundleMakerProject;
import org.bundlemaker.core.parser.IProblem;
import org.bundlemaker.core.project.IProjectContentEntry;

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
  public void batchParseStart(IParserAwareBundleMakerProject bundleMakerProject) throws Exception {
    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void batchParseStop(IParserAwareBundleMakerProject bundleMakerProject) throws Exception {
    // ignore
  }

  /**
   * <p>
   * </p>
   * 
   * @param context
   * @param resourceKey
   */
  public List<IProblem> parseResource(IProjectContentEntry content, IParsableResource resource,
      boolean parseReferences, boolean isBatchParse) {

    // Reset problem list
    _problems = new LinkedList<IProblem>();

    // do the parsing
    doParseResource(content, resource, parseReferences, isBatchParse);

    //
    return _problems;
  }

  /**
   * Override in subclasses to implement parse logic
   * 
   * @param content
   * @param resource
   * @param context
   */
  protected abstract void doParseResource(IProjectContentEntry content, IParsableResource resource,
      boolean parseReferences, boolean isBatchParse);

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @return
   */
  public abstract boolean canParse(IParsableResource resource);

}
