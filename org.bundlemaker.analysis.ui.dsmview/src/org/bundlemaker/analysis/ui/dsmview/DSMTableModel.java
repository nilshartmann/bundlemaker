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

import java.util.Hashtable;
import java.util.Map;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;

import de.kupzog.ktable.KTableCellEditor;
import de.kupzog.ktable.KTableCellRenderer;
import de.kupzog.ktable.KTableModel;

/**
 * <p>
 * The {@link KTableModel} for the Dependency Structure Matrix
 * 
 * <p>
 * Stellt den Inhalt für KTabel zur Verfügung.
 * 
 * @author Kai Lehmann
 * @author Nils Hartmann
 */
public class DSMTableModel implements KTableModel {

  private final int                   HEADER_SIZE        = 27;

  private final int                   MAX_ROW_WIDTH      = 100;

  private final int                   MAX_ROW_WIDTH_LONG = 180;

  private final Map<Integer, Integer> _columWidths       = new Hashtable<Integer, Integer>();

  private final Map<Integer, Integer> _rowHeights        = new Hashtable<Integer, Integer>();

  private IDependency[][]             dependencies;

  private DSMCellRenderer             dsmCellRenderer;

  private float                       zoomFactor         = 0f;

  private int                         sizeOfLongestName;

  private IArtifact[]                 headers;

  public DSMTableModel(IDependency[][] dependencies, IArtifact[] headers) {
    this.dependencies = dependencies;
    this.dsmCellRenderer = new DSMCellRenderer(SWT.NONE);

    this.headers = headers;

    this.sizeOfLongestName = 0;
  }

  @Override
  public Point belongsToCell(int arg0, int arg1) {
    return null;
  }

  @Override
  public KTableCellEditor getCellEditor(int arg0, int arg1) {
    return null;
  }

  @Override
  public KTableCellRenderer getCellRenderer(int arg0, int arg1) {
    return dsmCellRenderer;
  }

  @Override
  public int getColumnCount() {
    int min = dependencies.length > 0 ? dependencies[0].length : 0;
    return min + getFixedHeaderColumnCount();
  }

  @Override
  public int getColumnWidth(int col) {
    if (_columWidths.containsKey(col)) {
      return _columWidths.get(col);
    }
    if (col == 0) {
      int available = 1600 /* 800 */- getRowCount() * getCellWidth();
      if (available > getSizeOfLongestName()) {
        return applyZoomFactory(getSizeOfLongestName() + 14);
      } else if (available > 30) {
        return applyZoomFactory(available);
      } else {
        return applyZoomFactory(30);
      }
    }
    return applyZoomFactory(getCellWidth());
  }

  @Override
  public Object getContentAt(int col, int row) {
    if (col == 0 && row == 0) {
      return "";
    } else if (col == 0) {
      return headers[row - 1];
    } else if (row == 0) {
      return headers[col - 1];
    }
    if (dependencies[col - 1][row - 1] == null) {
      return "";
    } else {
      return dependencies[col - 1][row - 1];
    }

  }

  @Override
  public int getFixedHeaderColumnCount() {
    return 1;
  }

  @Override
  public int getFixedHeaderRowCount() {
    return 1;
  }

  @Override
  public int getFixedSelectableColumnCount() {
    return 0;
  }

  @Override
  public int getFixedSelectableRowCount() {
    return 0;
  }

  @Override
  public int getRowCount() {
    return dependencies.length + getFixedHeaderRowCount();
  }

  private int getCellWidth() {

    int width = 500 / getRowCount();

    if (width > HEADER_SIZE) {
      return HEADER_SIZE;
    } else if (width < 5) {
      return 5;
    } else {
      return width;
    }
  }

  @Override
  public int getRowHeight(int row) {
    if (_rowHeights.containsKey(row)) {
      return _rowHeights.get(row);
    }
    if (row == 0) {
      return applyZoomFactory(getHeaderHeight());
    }

    return applyZoomFactory(getCellWidth());
  }

  private int applyZoomFactory(int value) {
    int newValue = value + (int) (value * zoomFactor);
    return newValue;
  }

  @Override
  public int getRowHeightMinimum() {
    return 20;
  }

  @Override
  public String getTooltipAt(int col, int row) {
    if (col > 0 && row > 0) {
      IDependency dependency = dependencies[col - 1][row - 1];
      if (dependency != null) {
        IArtifact from = dependency.getFrom();
        IArtifact to = dependency.getTo();

        StringBuilder info = new StringBuilder(40);
        if (dependency.isTaggedIgnore()) {
          info.append("Ignored: ");
        }
        info.append(getName(from));
        info.append(" -> ");
        info.append(getName(to));
        info.append(" weight: ");
        info.append(dependency.getWeight());

        return info.toString();
      }
    } else if (col == 0 && row != 0) {
      return headers[row - 1].getName();
    } else if (col != 0 && row == 0) {
      return headers[col - 1].getName();
    }

    return null;

  }

  private String getName(IArtifact artifact) {
    if (artifact == null) {
      return "(null)";
    }
    if (artifact.getType() == ArtifactType.Package) {
      return artifact.getQualifiedName();
    }

    return artifact.getName();
  }

  @Override
  public boolean isColumnResizable(int arg0) {
    return true;
  }

  @Override
  public boolean isRowResizable(int arg0) {
    return true;
  }

  @Override
  public void setColumnWidth(int arg0, int arg1) {
    _columWidths.put(arg0, arg1);
  }

  @Override
  public void setContentAt(int col, int row, Object value) {
    dependencies[row][col] = (IDependency) value;
  }

  @Override
  public void setRowHeight(int arg0, int arg1) {
    System.out.printf("setRowHeight %d, %d%n", arg0, arg1);
    _rowHeights.put(arg0, arg1);

  }

  private int getSizeOfLongestName() {
    if (sizeOfLongestName == 0) {
      for (IArtifact artifact : headers) {
        sizeOfLongestName = Math.max(sizeOfLongestName,
            FigureUtilities.getTextWidth(artifact.getName(), DSMCellRenderer.DSM_FONT));
      }
    }
    return sizeOfLongestName + 5;
  }

  private int getHeaderHeight() {
    if (getRowCount() < 20) {
      return Math.min(getSizeOfLongestName(), MAX_ROW_WIDTH_LONG);
    } else if (getRowCount() < 30) {
      return Math.min(getSizeOfLongestName(), MAX_ROW_WIDTH);
    } else {
      return 5;
    }
  }

  /**
   * Sets the factor the DSM View should be scaled with
   * 
   * @param d
   */
  public void setZoomFactor(float d) {
    zoomFactor = d;
  }

}
