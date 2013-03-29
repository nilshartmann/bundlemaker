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

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.selection.IArtifactSelection;
import org.bundlemaker.core.ui.event.selection.workbench.editor.AbstractArtifactSelectionAwareEditorPart;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public class DependencyViewerEditor extends AbstractArtifactSelectionAwareEditorPart {

  public final static String              DEPENDENCY_VIEWER_EDITOR_ID = "org.bundlemaker.core.ui.editor.dependencyviewer.DependencyViewer";

  
  @Override
  public void analysisModelModified() {
    setCurrentArtifactSelection(getCurrentArtifactSelection());
  }
  @Override
  protected void setCurrentArtifactSelection(IArtifactSelection artifactSelection) {
    super.setCurrentArtifactSelection(artifactSelection);

    //
    List<IBundleMakerArtifact> selectedArtifacts = artifactSelection.getEffectiveSelectedArtifacts();
    System.out.println("Artifacts: " + selectedArtifacts);
  }
  
  @Override
  protected String getProviderId() {
    return DEPENDENCY_VIEWER_EDITOR_ID;
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPartControl(Composite parent) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {

  }

}
