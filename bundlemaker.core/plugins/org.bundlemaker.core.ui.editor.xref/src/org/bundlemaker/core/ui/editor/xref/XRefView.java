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
package org.bundlemaker.core.ui.editor.xref;

import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.selection.IArtifactSelection;
import org.bundlemaker.core.selection.Selection;
import org.bundlemaker.core.ui.event.selection.workbench.editor.AbstractArtifactSelectionAwareEditorPart;
import org.bundlemaker.core.ui.view.dependencytree.CropableDependencyTreeComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class XRefView extends AbstractArtifactSelectionAwareEditorPart {

  /** the ID of the view as specified by the extension */
  public static final String              XREF_ID = XRefView.class.getName();

  /** - */
  private CropableDependencyTreeComposite _composite;

  /**
   * {@inheritDoc}
   */
  @Override
  public void createPartControl(Composite parent) {

    //
    _composite = new CropableDependencyTreeComposite(parent, XREF_ID, false, false) {

      @Override
      protected String getDependencySelectionId() {
        return Selection.MAIN_DEPENDENCY_SELECTION_ID;
      }
    };
  }

  @Override
  public void setFocus() {
    //
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void analysisModelModified() {
    setCurrentArtifactSelection(getCurrentArtifactSelection());
  }

  @Override
  protected void setCurrentArtifactSelection(IArtifactSelection event) {

    // do nothing if the root has not changed
    if (getCurrentArtifactSelection() != null && getCurrentArtifactSelection().hasSelectedArtifacts() && event != null
        && event.hasSelectedArtifacts()
        && getCurrentArtifactSelection().getRootArtifact().equals(event.getRootArtifact())) {

      //
      return;
    }

    // call super
    super.setCurrentArtifactSelection(event);

    if (_composite != null) {

      if (getCurrentArtifactSelection() != null && getCurrentArtifactSelection().hasSelectedArtifacts()) {

        //
        _composite.setDependencies(AnalysisModelQueries.getCoreDependencies(getCurrentArtifactSelection()
            .getRootArtifact().getDependenciesTo()));

      }
      // else {
      //
      // //
      // List<IDependency> dependencies = Collections.emptyList();
      // _composite.setDependencies(dependencies);
      //
      // }
    }
  }

  @Override
  protected String getProviderId() {
    return XREF_ID;
  }
}