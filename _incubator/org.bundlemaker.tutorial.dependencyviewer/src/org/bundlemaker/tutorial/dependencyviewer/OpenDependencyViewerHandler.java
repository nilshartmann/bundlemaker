package org.bundlemaker.tutorial.dependencyviewer;

import org.bundlemaker.core.ui.handler.AbstractOpenEditorHandler;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class OpenDependencyViewerHandler extends AbstractOpenEditorHandler {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.handler.AbstractOpenEditorHandler#getEditorId()
   */
  @Override
  protected String getEditorId() {
    return DependencyViewEditor.DEPENDENCY_VIEW_EDITOR_ID;
  }

}
