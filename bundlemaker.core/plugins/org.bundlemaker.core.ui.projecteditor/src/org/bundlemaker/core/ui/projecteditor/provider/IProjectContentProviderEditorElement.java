/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.projecteditor.provider;

import org.bundlemaker.core.projectdescription.IProjectContentProvider;

/**
 * Represents an Element that is shown in the project content editor tree.
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public interface IProjectContentProviderEditorElement {

  /**
   * Returns the element itself. Never null.
   * 
   * @return
   */
  public Object getElement();

  /**
   * Return the associated content provider
   * 
   * @return
   */
  public IProjectContentProvider getProjectContentProvider();

  /**
   * Returns the editor that has contributed this element to the project content tree.
   * 
   * @return
   */
  public IProjectContentProviderEditor getProvidingEditor();

}
