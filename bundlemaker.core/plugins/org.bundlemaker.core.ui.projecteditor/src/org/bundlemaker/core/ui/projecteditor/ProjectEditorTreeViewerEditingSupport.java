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
package org.bundlemaker.core.ui.projecteditor;

import org.bundlemaker.core.project.util.AnalyzeMode;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.swt.SWT;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ProjectEditorTreeViewerEditingSupport extends EditingSupport {

  private final boolean _binaryColumn;

  public ProjectEditorTreeViewerEditingSupport(ColumnViewer viewer, boolean binaryColumn) {
    super(viewer);

    _binaryColumn = binaryColumn;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.EditingSupport#getCellEditor(java.lang.Object)
   */
  @Override
  protected CellEditor getCellEditor(Object element) {

    if (canEdit(element)) {
      return new CheckboxCellEditor(null, SWT.CHECK);
    }

    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.EditingSupport#canEdit(java.lang.Object)
   */
  @Override
  protected boolean canEdit(Object element) {
    ProjectEditorTreeViewerElement treeviewerElement = getAsProjectEditorTreeViewerElement(element);

    boolean canEdit = treeviewerElement.getProvidingEditor().canChangeAnalyzeMode(
        treeviewerElement.getProjectContentProvider(), treeviewerElement.getElement());
    return canEdit;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.EditingSupport#getValue(java.lang.Object)
   */
  @Override
  protected Object getValue(Object element) {

    ProjectEditorTreeViewerElement treeviewerElement = getAsProjectEditorTreeViewerElement(element);
    AnalyzeMode analyzeMode = treeviewerElement.getProvidingEditor().getAnalyzeMode(treeviewerElement.getElement());

    if (analyzeMode == null) {
      return false;
    }

    if (isBinaryColumn()) {
      return analyzeMode.isAnalyze();
    }

    return analyzeMode == AnalyzeMode.BINARIES_AND_SOURCES;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.EditingSupport#setValue(java.lang.Object, java.lang.Object)
   */
  @Override
  protected void setValue(Object element, Object value) {

    boolean selected = (Boolean) value;

    ProjectEditorTreeViewerElement treeviewerElement = getAsProjectEditorTreeViewerElement(element);
    AnalyzeMode currentAnalyzeMode = treeviewerElement.getProvidingEditor().getAnalyzeMode(
        treeviewerElement.getElement());

    AnalyzeMode newAnalyzeMode;

    if (currentAnalyzeMode == null) {
      return;
    }

    if (isBinaryColumn()) {
      if (!selected) {
        newAnalyzeMode = AnalyzeMode.DO_NOT_ANALYZE;
      } else {
        newAnalyzeMode = AnalyzeMode.BINARIES_ONLY;
      }
    } else {
      if (selected) {
        newAnalyzeMode = AnalyzeMode.BINARIES_AND_SOURCES;
      } else {
        newAnalyzeMode = AnalyzeMode.BINARIES_ONLY;
      }
    }

    treeviewerElement.getProvidingEditor().setAnalyzeMode(treeviewerElement.getProjectContentProvider(),
        treeviewerElement.getElement(), newAnalyzeMode);

    getViewer().refresh(null);

  }

  private ProjectEditorTreeViewerElement getAsProjectEditorTreeViewerElement(Object element) {
    return (ProjectEditorTreeViewerElement) element;
  }

  protected boolean isBinaryColumn() {
    return _binaryColumn;
  }

}
