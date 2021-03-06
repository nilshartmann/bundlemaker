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

import org.bundlemaker.core.resource.IModuleAwareBundleMakerProject;

/**
 * <p>
 * Represents a bundle maker project.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IParserAwareBundleMakerProject extends IModuleAwareBundleMakerProject {

  /**
   * <p>
   * Returns a list with all {@link IProblem IProblems} that occurred whilst parsing the project content.
   * </p>
   * 
   * @return a list with all {@link IProblem IProblems} that occurred whilst parsing the project content.
   */
  List<IProblem> getProblems();
}
