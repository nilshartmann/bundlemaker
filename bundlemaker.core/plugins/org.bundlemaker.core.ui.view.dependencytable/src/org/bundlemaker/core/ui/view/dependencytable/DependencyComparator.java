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
package org.bundlemaker.core.ui.view.dependencytable;

import java.util.Comparator;

import org.bundlemaker.core.analysis.IDependency;
import org.eclipse.swt.SWT;

/**
 * Compares {@link IDependency} instances, either by their from- or to-side or their usage
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DependencyComparator implements Comparator<IDependency> {

  private static final int                 DESCENDING = 1;

  private final ArtifactPathLabelGenerator _fromArtifactPathLabelGenerator;

  private final ArtifactPathLabelGenerator _toArtifactPathLabelGenerator;

  private int                              _columnIndex;

  private int                              direction  = DESCENDING;

  /**
   * @param fromArtifactPathLabelGenerator
   * @param toArtifactPathLabelGenerator
   */
  public DependencyComparator(ArtifactPathLabelGenerator fromArtifactPathLabelGenerator,
      ArtifactPathLabelGenerator toArtifactPathLabelGenerator) {

    _fromArtifactPathLabelGenerator = fromArtifactPathLabelGenerator;
    _toArtifactPathLabelGenerator = toArtifactPathLabelGenerator;

    this._columnIndex = 0;
    direction = DESCENDING;
  }

  public int getDirection() {
    return direction == 1 ? SWT.DOWN : SWT.UP;
  }

  /**
   * Set the column that should be used for ordering: 0 for from-label, 2 for usage, 2 for to-label
   * @param column
   */
  public void setColumn(int column) {
    if (column == this._columnIndex) {
      // Same column as last sort; toggle the direction
      direction = 1 - direction;
    } else {
      // New column; do an ascending sort
      this._columnIndex = column;
      direction = DESCENDING;
    }
  }

  @Override
  public int compare(IDependency o1, IDependency o2) {
    // Get the actual values to compare (dependending on the order column)
    String value1 = getCompareValue(o1);
    String value2 = getCompareValue(o2);
    
    // Compare
    return (direction == DESCENDING ?
        value1.compareTo(value2) : value2.compareTo(value1));
  }

  protected String getCompareValue(IDependency element) {
    if (_columnIndex == 0) {
      return _fromArtifactPathLabelGenerator.getLabel(element.getFrom());
    }
    
    if (_columnIndex == 1) {
      return String.valueOf(element.getDependencyKind());
    }

    return _toArtifactPathLabelGenerator.getLabel(element.getTo());
  }

}
