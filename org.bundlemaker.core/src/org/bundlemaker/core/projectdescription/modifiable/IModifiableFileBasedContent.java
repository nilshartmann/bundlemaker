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
package org.bundlemaker.core.projectdescription.modifiable;

import java.util.Set;

import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.projectdescription.IRootPath;

/**
 * <p>
 * Describes a file based content entry in an {@link IModifiableBundleMakerProjectDescription}. A file base content
 * entry can contain one or many directories or archive files (*.zip or *.jar).
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IModifiableFileBasedContent extends IFileBasedContent {

  public void setAnalyzeSourceResources(boolean flag);
  
  /**
   * Set the name of this IFileBasedContent to the given value
   * @param name
   */
  public void setName(String name);
  
  /**
   * Set the Version of this IFileBasedContent to the given value
   * @param version
   */
  public void setVersion(String version);
  
  public void setBinaryPaths(String[] binaryRootPaths);
  public void setSourcePaths(String[] sourceRootPaths);
  
}
