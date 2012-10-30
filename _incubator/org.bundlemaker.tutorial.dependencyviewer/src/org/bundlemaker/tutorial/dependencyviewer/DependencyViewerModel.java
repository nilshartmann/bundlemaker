/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.tutorial.dependencyviewer;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DependencyViewerModel {

  private List<IBundleMakerArtifact> _artifacts;

  /**
   * @param artifacts
   */
  public DependencyViewerModel(List<IBundleMakerArtifact> artifacts) {
    super();
    _artifacts = artifacts;
    initModel();
  }

  /**
   * @return the artifacts
   */
  public List<IBundleMakerArtifact> getArtifacts() {
    return _artifacts;
  }

  /**
   * @param artifacts
   *          the artifacts to set
   */
  public void setArtifacts(List<IBundleMakerArtifact> artifacts) {
    _artifacts = artifacts;
    initModel();
  }

  int       minWeight = 1;

  int       maxWeight = 1;

  final int MAX_WIDTH = 10;

  public int getWeight(IDependency dependency) {
    int ratio = maxWeight / minWeight;
    System.out.println("maxWeiht: " + maxWeight);
    double factor = (double) MAX_WIDTH / (double) maxWeight;

    double result = dependency.getWeight() * factor;
    System.out.println("ratio: " + ratio + ", factor: " + factor + ", weight:  " + dependency.getWeight()
        + ", result: " + result);
    return (int) result;
  }

  /**
   * 
   */
  private void initModel() {

    this.minWeight = 1;
    this.maxWeight = 1;

    for (IBundleMakerArtifact from : _artifacts) {
      for (IBundleMakerArtifact to : _artifacts) {

        if (from == to) {
          continue;
        }

        IDependency dependency = from.getDependencyTo(to);
        int weight = (dependency == null ? 0 : dependency.getWeight());

        minWeight = Math.min(weight, minWeight);
        maxWeight = Math.max(weight, maxWeight);

      }
    }

    minWeight = Math.max(minWeight, 1);
    maxWeight = Math.max(maxWeight, 1);

    System.out.println("min :" + minWeight + ",max: " + maxWeight);

  }

}
