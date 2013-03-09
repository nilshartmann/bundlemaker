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
package org.bundlemaker.core.modules;

import java.util.Map;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResourceModule extends IResourceContainer, IModule {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IResourceContainer getSelfResourceContainer();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Map<String, ? extends IResourceContainer> getContainedResourceContainers();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean containsSources();
}
