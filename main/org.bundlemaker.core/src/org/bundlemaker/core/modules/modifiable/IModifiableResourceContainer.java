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

import org.bundlemaker.core.modules.IResourceContainer;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModifiableResourceContainer extends IResourceContainer, IModifiableTypeContainer {

  // /**
  // * <p>
  // * </p>
  // *
  // * @param resource
  // * @param contentType
  // *
  // * @deprecated Use addMovableUnit instead.
  // */
  // @Deprecated
  // void add(IResource resource, ContentType contentType);
  //
  // /**
  // * <p>
  // * </p>
  // *
  // * @param resource
  // * @param contentType
  // *
  // * @deprecated Use addMovableUnit instead.
  // */
  // @Deprecated
  // void addAll(Collection<? extends IResource> resource, ContentType contentType);
  //
  // /**
  // * <p>
  // * </p>
  // *
  // * @param resource
  // * @param contentType
  // *
  // * @deprecated Use removeMovableUnit instead.
  // */
  // @Deprecated
  // void remove(IResource resource, ContentType contentType);
  //
  // /**
  // * <p>
  // * </p>
  // *
  // * @param resource
  // * @param contentType
  // *
  // * @deprecated Use removeMovableUnit instead.
  // */
  // @Deprecated
  // void removeAll(Collection<? extends IResource> resource, ContentType contentType);
}
