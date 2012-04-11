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
package org.bundlemaker.core.ui.editor.dummy;

import org.bundlemaker.core.ui.event.selection.IArtifactSelection;
import org.bundlemaker.core.ui.event.selection.workbench.editor.AbstractArtifactSelectionAwareEditorPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public class DummyEditor extends AbstractArtifactSelectionAwareEditorPart {

  /** the ID of the view as specified by the extension */
  public static final String DummyEditor_ID = DummyEditor.class.getName();

  /** - */
  private Label              _label;

  /**
   * {@inheritDoc}
   */
  @Override
  public void createPartControl(Composite parent) {
    _label = new Label(parent, SWT.NONE);
  }

  @Override
  public void setFocus() {
    //
  }

  @Override
  public void artifactModelModified() {

  }

  @Override
  protected void setCurrentArtifactSelection(IArtifactSelection artifactSelection) {

    super.setCurrentArtifactSelection(artifactSelection);

    if (artifactSelection.hasSelectedArtifacts()) {
      _label.setText(artifactSelection.getRootArtifact().getName());
    }
  }

  @Override
  protected String getProviderId() {
    return DummyEditor_ID;
  }
}