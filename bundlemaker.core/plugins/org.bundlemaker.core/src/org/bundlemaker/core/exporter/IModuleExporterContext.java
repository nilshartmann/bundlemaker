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
package org.bundlemaker.core.exporter;

import java.io.File;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.resource.IModularizedSystem;

/**
 * <p>
 * </p>
 * <p>
 * Clients may implement this interface.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IModuleExporterContext {

  /**
   * <p>
   * Returns the {@link IBundleMakerProject}.
   * </p>
   * 
   * @return
   */
  IBundleMakerProject getBundleMakerProject();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  File getDestinationDirectory();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IModularizedSystem getModularizedSystem();

  /**
   * <p>
   * </p>
   * 
   * @param key
   * @return
   */
  boolean containsAttribute(Object key);

  /**
   * <p>
   * </p>
   * 
   * @param key
   * @return
   */
  Object getAttribute(Object key);
}
