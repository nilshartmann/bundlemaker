/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Kai Lehmann - initial API and implementation
 *     Bundlemaker project team - integration with BundleMaker Analysis UI
 ******************************************************************************/
package org.bundlemaker.analysis.ui.dsmview;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.analysis.ui.editor.DependencyPart;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionChangedEvent;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionListener;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DSMView extends DependencyPart {

  /**
   * This is used as the DSMView's providerId for the xxxSelectionServices
   */
  public static String     ID = "org.bundlemaker.analysis.ui.dsmview.DSMView";

  private DsmViewComposite _dsmViewWidget;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInit(Composite composite) {
    //
    _dsmViewWidget = new DsmViewComposite(composite, new DsmViewModel());

    //
    Analysis.instance().getArtifactSelectionService().addArtifactSelectionListener(new IArtifactSelectionListener() {
      @Override
      public void artifactSelectionChanged(IArtifactSelectionChangedEvent event) {
        if (event.getSelection().getSelectedArtifacts().size() == 1) {

          IArtifact selectedArtifact = event.getSelection().getSelectedArtifacts().get(0);
          List<IArtifact> artifacts = new LinkedList<IArtifact>(selectedArtifact.getChildren());
          useArtifacts(artifacts);
        }
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doDispose() {
    //
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void useArtifacts(List<IArtifact> artifacts) {
    super.useArtifacts(artifacts);
    _dsmViewWidget.setModel(new DsmViewModel(artifacts));
  }
}
