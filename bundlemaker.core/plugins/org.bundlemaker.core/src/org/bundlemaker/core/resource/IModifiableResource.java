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
package org.bundlemaker.core.resource;

import org.bundlemaker.core._type.IType;
import org.bundlemaker.core._type.TypeEnum;
import org.bundlemaker.core._type.modifiable.IReferenceRecorder;
import org.bundlemaker.core.internal.resource.Type;

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

  // *** TODO: REMOVE ***/

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @return
   */
  public Type getOrCreateType(String fullyQualifiedName, TypeEnum typeEnum, boolean abstractType);

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
}
