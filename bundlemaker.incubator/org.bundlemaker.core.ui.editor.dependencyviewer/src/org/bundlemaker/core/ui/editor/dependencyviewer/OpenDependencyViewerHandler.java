/*******************************************************************************
 * Copyright (c) 2013 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/

package org.bundlemaker.core.ui.editor.dependencyviewer;

import org.bundlemaker.core.ui.handler.AbstractOpenEditorHandler;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public class OpenDependencyViewerHandler extends AbstractOpenEditorHandler {

  @Override
  protected String getEditorId() {
    return DependencyViewerEditor.DEPENDENCY_VIEWER_EDITOR_ID;
  }

}
