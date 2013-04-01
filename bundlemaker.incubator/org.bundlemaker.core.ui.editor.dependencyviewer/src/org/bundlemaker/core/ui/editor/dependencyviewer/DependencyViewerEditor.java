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

import java.awt.Frame;

import org.bundlemaker.core.selection.IArtifactSelection;
import org.bundlemaker.core.ui.editor.dependencyviewer.graph.DependencyViewerGraph;
import org.bundlemaker.core.ui.event.selection.workbench.editor.AbstractArtifactSelectionAwareEditorPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DependencyViewerEditor extends AbstractArtifactSelectionAwareEditorPart {

  public final static String    DEPENDENCY_VIEWER_EDITOR_ID = "org.bundlemaker.core.ui.editor.dependencyviewer.DependencyViewer";

  private DependencyViewerGraph _dependencyViewerGraph;

  @Override
  public void analysisModelModified() {
    setCurrentArtifactSelection(getCurrentArtifactSelection());
  }

  @Override
  protected void setCurrentArtifactSelection(IArtifactSelection artifactSelection) {
    super.setCurrentArtifactSelection(artifactSelection);

    renderSelectedArtifacts(artifactSelection);

  }

  /**
   * @param artifactSelection
   */
  private void renderSelectedArtifacts(IArtifactSelection artifactSelection) {
    if (artifactSelection != null && _dependencyViewerGraph != null) {
      _dependencyViewerGraph.showArtifacts(artifactSelection.getEffectiveSelectedArtifacts());
    }

  }

  @Override
  protected String getProviderId() {
    return DEPENDENCY_VIEWER_EDITOR_ID;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPartControl(Composite parent) {
    Composite composite = new Composite(parent, SWT.EMBEDDED | SWT.NO_BACKGROUND);
    Frame frame = SWT_AWT.new_Frame(composite);

    _dependencyViewerGraph = new DependencyViewerGraph();
    _dependencyViewerGraph.create(frame, parent.getDisplay());

    renderSelectedArtifacts(getCurrentArtifactSelection());

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.event.selection.workbench.editor.AbstractArtifactSelectionAwareEditorPart#dispose()
   */
  @Override
  public void dispose() {
    super.dispose();

    _dependencyViewerGraph.dispose();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {

  }
}
