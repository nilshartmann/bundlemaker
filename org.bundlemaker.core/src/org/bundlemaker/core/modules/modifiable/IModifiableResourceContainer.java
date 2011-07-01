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
package org.bundlemaker.core.modules.modifiable;

import java.util.Collection;

import org.bundlemaker.core.modules.IResourceContainer;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModifiableResourceContainer extends IResourceContainer, IModifiableTypeContainer {

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @param contentType
   */
  void add(IResource resource, ContentType contentType);

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @param contentType
   */
  void addAll(Collection<? extends IResource> resource, ContentType contentType);

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @param contentType
   */
  void remove(IResource resource, ContentType contentType);

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @param contentType
   */
  void removeAll(Collection<? extends IResource> resource, ContentType contentType);

  /**
   * <p>
   * </p>
   * 
   * @param movableUnit
   */
  void addMovableUnit(IMovableUnit movableUnit);

  /**
   * <p>
   * </p>
   * 
   * @param movableUnit
   */
  void removeMovableUnit(IMovableUnit movableUnit);
}
