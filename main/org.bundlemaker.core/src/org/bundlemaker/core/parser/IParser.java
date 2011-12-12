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

import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectContent;
import org.bundlemaker.core.resource.IResourceKey;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * Defines the common interface to parse a {@link BundleMakerProject}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IParser {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ParserType getParserType();

  /**
   * <p>
   * </p>
   * 
   * @param resourceKey
   * @return
   */
  boolean canParse(IResourceKey resourceKey);

  /**
   * <p>
   * </p>
   * 
   * @param content
   * @param resource
   * @param cache
   * @throws CoreException
   */
  public void parseResource(IBundleMakerProjectContent content, IResourceKey resource, IResourceCache cache)
      throws CoreException;

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   */
  public void parseBundleMakerProjectStart(IBundleMakerProject bundleMakerProject);

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   */
  public void parseBundleMakerProjectStop(IBundleMakerProject bundleMakerProject);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<IProblem> getProblems();

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public enum ParserType {
    BINARY, BINARY_AND_SOURCE, SOURCE;
  }
}
