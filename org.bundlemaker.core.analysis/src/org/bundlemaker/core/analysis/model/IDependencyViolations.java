/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.analysis.model;

import java.util.List;

/**
 * <p>
 * </p>
 * 
 * @author Frank Schlueter
 * @author Kai Lehmann
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IDependencyViolations {
  // public void setDependencyModel(DependencyModel dependencyModel);

  public void asyncInitProcessing(boolean runAsync);

  public List<IDependency> getViolations();

  public List<IDependency> getTypeViolations();

  public List<IDependency> getNewViolations();

  public int getViolationCount();
}
