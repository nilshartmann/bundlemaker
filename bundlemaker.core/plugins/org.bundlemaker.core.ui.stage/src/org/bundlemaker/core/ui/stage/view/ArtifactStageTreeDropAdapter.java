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

package org.bundlemaker.core.ui.stage.view;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.selection.Selection;
import org.bundlemaker.core.ui.ArtifactStageActionHelper;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeDropAdapter;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactStageTreeDropAdapter extends ViewerDropAdapter {

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactTreeDropAdapter}.
   * </p>
   * 
   * @param viewer
   */
  public ArtifactStageTreeDropAdapter(TreeViewer viewer) {
    super(viewer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void drop(DropTargetEvent event) {

    //
    ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();

    // nativeToJava(event.currentDataType);

    if (!(selection instanceof IStructuredSelection)) {
      return;
    }

    if (!ArtifactStageActionHelper.switchToManualAddModeIfRequired()) {
      // not in manual mode
      return;
    }

    IStructuredSelection structuredSelection = (IStructuredSelection) selection;

    List<IBundleMakerArtifact> artifacts = new LinkedList<IBundleMakerArtifact>();

    for (Object selectedObject : structuredSelection.toArray()) {

      //
      final IBundleMakerArtifact sourceArtifact = (IBundleMakerArtifact) selectedObject;

      artifacts.add(sourceArtifact);
    }

    Selection.instance().getArtifactStage().addToStage(artifacts);

  }

  @Override
  public boolean validateDrop(Object target, int operation, TransferData transferData) {

    //
    ISelection selection = LocalSelectionTransfer.getTransfer().getSelection(); // nativeToJava(transferData);

    if (!(selection instanceof IStructuredSelection)) {
      return false;
    }

    IStructuredSelection structuredSelection = (IStructuredSelection) selection;
    if (structuredSelection.isEmpty()) {
      return false;
    }

    Iterator<?> iterator = structuredSelection.iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      if (!(o instanceof IBundleMakerArtifact)) {
        return false;
      }
    }

    return true;

    // //
    // if (structuredSelection.getFirstElement() instanceof IBundleMakerArtifact && target instanceof
    // IBundleMakerArtifact) {
    //
    // IBundleMakerArtifact sourceArtifact = (IBundleMakerArtifact) structuredSelection.getFirstElement();
    // IBundleMakerArtifact targetArtifact = (IBundleMakerArtifact) target;
    //
    // if (targetArtifact.canAdd(sourceArtifact) && sourceArtifact.isMovable()) {
    // return true;
    // }
    // }

    //
  }

  @Override
  public boolean performDrop(Object data) {

    return false;
  }
}
