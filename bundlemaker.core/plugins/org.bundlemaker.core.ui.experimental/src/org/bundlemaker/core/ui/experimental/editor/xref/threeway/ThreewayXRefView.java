/*******************************************************************************
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.experimental.editor.xref.threeway;

import org.bundlemaker.core.selection.IArtifactSelection;
import org.bundlemaker.core.selection.Selection;
import org.bundlemaker.core.ui.event.selection.workbench.editor.AbstractArtifactSelectionAwareEditorPart;
import org.bundlemaker.core.ui.experimental.dependencytable.threeway.XRefComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ThreewayXRefView extends AbstractArtifactSelectionAwareEditorPart {

  /** the ID of the view as specified by the extension */
  public static final String XREF_ID = ThreewayXRefView.class.getName();

  // /** - */
  // private CropableDependencyTreeComposite _composite;

  private XRefComposite      _composite;

  /**
   * {@inheritDoc}
   */
  @Override
  public void createPartControl(Composite parent) {

    _composite = new XRefComposite(parent, XREF_ID, getSite());
  }

  @Override
  public void setFocus() {

    if (_composite != null && !_composite.isDisposed()) {
      _composite.setFocus();
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void analysisModelModified() {
    setCurrentArtifactSelection(getCurrentArtifactSelection());

    refreshFromCurrentArtifactSelection();
  }

  public void refreshFromCurrentArtifactSelection() {
    IArtifactSelection selection = Selection.instance().getArtifactSelectionService()
        .getSelection(getArtifactSelectionId());

    if (selection != null && _composite != null) {

      //
      if (selection.hasSelectedArtifacts()) {
        _composite.setSelectedArtifacts(selection.getSelectedArtifacts());
      }
    }
  }

  @Override
  protected String getArtifactSelectionId() {
    return Selection.PROJECT_EXPLORER_SELECTION_ID;
  }

  @Override
  protected String getProviderId() {
    return XREF_ID;
  }
}