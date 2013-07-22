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
package org.bundlemaker.core.jdt.parser;

import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * <p>
 * Defines the common interface for JDT source parser hooks.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IJdtSourceParserHook {

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   */
  public void parseBundleMakerProjectStart(IProjectDescriptionAwareBundleMakerProject bundleMakerProject);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public void analyzeCompilationUnit(IProjectContentResource resourceKey, CompilationUnit compilationUnit);

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   */
  public void parseBundleMakerProjectStop(IProjectDescriptionAwareBundleMakerProject bundleMakerProject);
}
