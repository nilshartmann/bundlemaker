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

import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependency;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import de.kupzog.ktable.KTableModel;
import de.kupzog.ktable.renderers.DefaultCellRenderer;

/**
 * @author Kai Lehmann
 * 
 */
public class DSMCellRenderer extends DefaultCellRenderer {

  public static final Font DSM_FONT         = new Font(Display.getCurrent(), "ARIAL", 15, SWT.NONE);

  private Color            COLOR_VIOLATION  = Display.getDefault().getSystemColor(SWT.COLOR_RED);

  private Color            COLOR_IGNORED    = Display.getDefault().getSystemColor(SWT.COLOR_MAGENTA);

  private Color            COLOR_TEXT       = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);

  private Color            COLOR_HEADER     = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);

  private Color            COLOR_BACKGROUND = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);

  private Color            COLOR_DEPENDENCY = Display.getDefault().getSystemColor(SWT.COLOR_GREEN);

  protected Display        m_Display;

  public DSMCellRenderer(int style) {
    super(style);
    m_Display = Display.getCurrent();

  }

  @Override
  public void drawCell(GC gc, Rectangle rect, int col, int row, Object content, boolean focus, boolean fixed,
      boolean clicked, KTableModel model) {

    applyFont(gc);
    rect = drawDefaultSolidCellLine(gc, rect, COLOR_BGROWFOCUS, COLOR_BGROWFOCUS);

    if (row == 0 && col != 0) { // Die Columnheader
      if (content instanceof IArtifact) {
        IArtifact artifact = (IArtifact) content;
        this.drawVerticalCellContent(gc, rect, artifact.getName(), null, COLOR_TEXT, COLOR_HEADER);
      }
    } else if (row != 0 && col == 0) { // Die Rowheader
      if (content instanceof IArtifact) {
        IArtifact artifact = (IArtifact) content;
        this.drawCellContent(gc, rect, artifact.getName(), Analysis.instance().getIconForArtifact(artifact),
            COLOR_TEXT, COLOR_HEADER);
      }
    } else if (row > 0 && col > 0 && col == row) {
      this.drawCellContent(gc, rect, "", null, COLOR_TEXT, COLOR_HEADER);
    } else {
      if (content instanceof IDependency) {
        IDependency dependency = (IDependency) content;
        if (col > row) {
          Color violationColor = COLOR_VIOLATION;
          if (dependency.isTaggedIgnore()) {
            violationColor = COLOR_IGNORED;
          }
          this.drawCellContent(gc, rect, "" + dependency.getWeight(), null, COLOR_TEXT, violationColor);
        } else {
          this.drawCellContent(gc, rect, "" + dependency.getWeight(), null, COLOR_TEXT, COLOR_DEPENDENCY);
        }
      } else {
        this.drawCellContent(gc, rect, "", null, COLOR_TEXT, COLOR_BACKGROUND);
      }

    }

    resetFont(gc);

  }

}
