/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.projecteditor.provider.internal;

import java.util.LinkedHashSet;
import java.util.Set;

import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;

/**
 * TODO replace with extension point
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ProjectContentProviderEditorRegistry {

  private final Set<IProjectContentProviderEditor> _editors = new LinkedHashSet<IProjectContentProviderEditor>();

  public ProjectContentProviderEditorRegistry() {
    // _editors.add();
  }

  public Set<IProjectContentProviderEditor> getProjectContentProviderEditors() {
    return this._editors;
  }

}
