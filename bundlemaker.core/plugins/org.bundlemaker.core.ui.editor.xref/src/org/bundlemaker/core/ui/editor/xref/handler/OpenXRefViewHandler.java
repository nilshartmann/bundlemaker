/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.editor.xref.handler;

import org.bundlemaker.core.ui.editor.xref.XRefView;
import org.bundlemaker.core.ui.handler.AbstractOpenEditorHandler;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class OpenXRefViewHandler extends AbstractOpenEditorHandler {

  /* (non-Javadoc)
   * @see org.bundlemaker.core.ui.handler.AbstractOpenEditorHandler#getEditorId()
   */
  @Override
  protected String getEditorId() {
    return XRefView.XREF_ID;
  }
    
}
