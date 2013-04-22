/*******************************************************************************
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.editor.xref3;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.selection.Selection;
import org.bundlemaker.core.ui.event.selection.workbench.editor.AbstractArtifactSelectionAwareEditorPart;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

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
    // setCurrentArtifactSelection(getCurrentArtifactSelection());
    //
    // IArtifactSelection selection = Selection.instance().getArtifactSelectionService()
    // .getSelection(getArtifactSelectionId());
    //
    // if (selection != null) {
    // refreshFromCurrentArtifactSelection(selection.getSelectedArtifacts());
    // }

    Display display = Display.getCurrent();
    if (display != null) {
      display.asyncExec(new Runnable() {

        @Override
        public void run() {
          if (_composite != null && !_composite.isDisposed()) {
            _composite.refresh();
          }
        }
      });

    }

  }

  public void refreshFromCurrentArtifactSelection(List<IBundleMakerArtifact> selectedArtifacts) {

    if (selectedArtifacts != null && _composite != null && selectedArtifacts.size() > 0) {

      //
      _composite.setSelectedArtifacts(selectedArtifacts);
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