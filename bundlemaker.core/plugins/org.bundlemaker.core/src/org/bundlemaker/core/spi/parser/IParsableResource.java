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

import org.bundlemaker.core.resource.IModuleResource;

/**
 * <p>
 * 
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IParsableResource extends IModuleResource {

  /**
   * <p>
   * </p>
   * 
   * @param stickyResource
   */
  public void addStickyResource(IModuleResource stickyResource);

  /**
   * <p>
   * </p>
   * 
   * @param erroneous
   */
  public void setErroneous(boolean erroneous);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  long getLastParsedTimestamp();

  /**
   * <p>
   * </p>
   * 
   * @param modelExtension
   */
  void setModelExtension(Object modelExtension);
}
