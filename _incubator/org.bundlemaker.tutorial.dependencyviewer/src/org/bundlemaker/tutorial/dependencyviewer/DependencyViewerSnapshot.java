/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.tutorial.dependencyviewer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DependencyViewerSnapshot {

  private final List<IBundleMakerArtifact> _artifacts;

  private final List<IBundleMakerArtifact> _hiddenArtifacts;

  // public static DependencyViewerSnapshot newRootSnapshot(List<IBundleMakerArtifact> artifacts) {
  // return new DependencyViewerSnapshot(artifacts, null);
  // }

  /**
   * @param artifacts
   */
  DependencyViewerSnapshot(Collection<IBundleMakerArtifact> artifacts, Collection<IBundleMakerArtifact> hiddenArtifacts) {
    _artifacts = new LinkedList<IBundleMakerArtifact>(artifacts);
    _hiddenArtifacts = new LinkedList<IBundleMakerArtifact>();
    if (hiddenArtifacts != null) {
      _hiddenArtifacts.addAll(hiddenArtifacts);
    }
  }

  // public DependencyViewerSnapshot deriveSnapshotWithNewArtifacts(List<IBundleMakerArtifact> newArtifacts) {
  // DependencyViewerSnapshot newSnapshot = new DependencyViewerSnapshot(newArtifacts, null);
  // return newSnapshot;
  // }
  //
  // public DependencyViewerSnapshot deriveSnapshotWithHiddenArtifacts(List<IBundleMakerArtifact> hiddenArtifacts) {
  // DependencyViewerSnapshot newSnapshot = new DependencyViewerSnapshot(newArtifacts, _hiddenArtifacts);
  // return newSnapshot;
  // }

  /**
   * @return
   */
  public List<IBundleMakerArtifact> getArtifacts() {
    return this._artifacts;
  }

  /**
   * 
   */
  public List<IBundleMakerArtifact> getHiddenArtifacts() {
    return this._hiddenArtifacts;

  }

}
