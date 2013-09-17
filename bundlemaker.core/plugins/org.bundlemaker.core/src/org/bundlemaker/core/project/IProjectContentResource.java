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
package org.bundlemaker.core.project;

import org.bundlemaker.core.common.IGenericAdaptable;
import org.bundlemaker.core.common.IResource;

/**
 * <p>
 * An {@link IProjectContentResource} defines a resource that is defined through a project content entry. It provides
 * access to the <code>contentId</code> and the <code>root</code> directory or archive file.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IProjectContentResource extends IResource, IGenericAdaptable {

  /**
   * <p>
   * Returns the identifier of the project content entry that defines the resource.
   * </p>
   * 
   * @return the identifier of the project content entry that defines the resource.
   */
  String getProjectContentEntryId();

  /**
   * <p>
   * Returns the root directory or archive file that contains the resource (e.g. <code>'c:/dev/classes.zip'</code> or
   * <code>'c:/dev/source'</code>). Note that resource paths are always slash-delimited ('/').
   * </p>
   * 
   * @return the root directory or archive file that contains the resource.
   */
  String getRoot();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isAnalyzeReferences();
}
