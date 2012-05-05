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
package org.bundlemaker.core.resource.modifiable;

import org.bundlemaker.core.internal.resource.Type;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.resource.TypeEnum;

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
public interface IModifiableResource extends IResource, IReferenceRecorder {

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @return
   */
  public Type getOrCreateType(String fullyQualifiedName, TypeEnum typeEnum);

  /**
   * <p>
   * </p>
   * 
   * @param type
   */
  public void setPrimaryType(IType type);

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @return
   */
  public Type getType(String fullyQualifiedName);

  /**
   * <p>
   * </p>
   * 
   * @param stickyResource
   */
  public void addStickyResource(IModifiableResource stickyResource);

  /**
   * <p>
   * </p>
   * 
   * @param erroneous
   */
  public void setErroneous(boolean erroneous);
}
