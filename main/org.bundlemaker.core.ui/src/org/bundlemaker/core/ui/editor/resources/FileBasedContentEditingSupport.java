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
package org.bundlemaker.core.ui.editor.resources;

import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.file.FileBasedContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.swt.SWT;

abstract class FileBasedContentEditingSupport extends EditingSupport {

  public static EditingSupport newEditingSupportForAnalyzeSources(ProjectResourcesBlock projectResourcesBlock,
      ColumnViewer columnViewer) {
    return new AnalyzeResourceSourceEditingSupport(projectResourcesBlock, columnViewer);
  }

  public static EditingSupport newEditingSupportForAnalyzeResource(ProjectResourcesBlock projectResourcesBlock,
      ColumnViewer columnViewer) {
    return new AnalyzeResourceEditingSupport(projectResourcesBlock, columnViewer);
  }

  /**
   * 
   */
  private final ProjectResourcesBlock _projectResourcesBlock;

  private FileBasedContentEditingSupport(ProjectResourcesBlock projectResourcesBlock, ColumnViewer viewer) {
    super(viewer);
    _projectResourcesBlock = projectResourcesBlock;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.EditingSupport#getCellEditor(java.lang.Object)
   */
  @Override
  protected CellEditor getCellEditor(Object element) {

    if (!canEdit(element)) {
      return null;
    }

    return new CheckboxCellEditor(null, SWT.CHECK);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.EditingSupport#canEdit(java.lang.Object)
   */
  @Override
  protected boolean canEdit(Object element) {
    if (!(element instanceof FileBasedContentProvider)) {
      return false;
    }

    FileBasedContentProvider content = (FileBasedContentProvider) element;
    return canEditInternal(content);
  }

  protected abstract boolean canEditInternal(FileBasedContentProvider content);

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.EditingSupport#getValue(java.lang.Object)
   */
  @Override
  protected Object getValue(Object element) {
    FileBasedContentProvider content = (FileBasedContentProvider) element;
    return getValueInternal(content);
  }

  protected abstract boolean getValueInternal(FileBasedContentProvider content);

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.EditingSupport#setValue(java.lang.Object, java.lang.Object)
   */
  @Override
  protected void setValue(Object element, Object value) {
    FileBasedContentProvider content = (FileBasedContentProvider) element;
    Boolean analyze = (Boolean) value;

    setValueInternal(content, analyze);

    // TODO: Listener support
    _projectResourcesBlock.projectDescriptionChanged();
  }

  protected abstract void setValueInternal(FileBasedContentProvider content, boolean value);

  static class AnalyzeResourceEditingSupport extends FileBasedContentEditingSupport {

    /**
     * @param projectResourcesBlock
     * @param viewer
     */
    public AnalyzeResourceEditingSupport(ProjectResourcesBlock projectResourcesBlock, ColumnViewer viewer) {
      super(projectResourcesBlock, viewer);
    }

    @Override
    protected void setValueInternal(FileBasedContentProvider content, boolean value) {
      if (value) {
        content.setAnalyzeMode(AnalyzeMode.BINARIES_ONLY);
      } else {
        content.setAnalyzeMode(AnalyzeMode.DO_NOT_ANALYZE);
      }
    }

    @Override
    protected boolean getValueInternal(FileBasedContentProvider content) {
      return content.getFileBasedContent().getAnalyzeMode().isAnalyze();
    }

    @Override
    protected boolean canEditInternal(FileBasedContentProvider content) {
      return true;
    }

  }

  static class AnalyzeResourceSourceEditingSupport extends FileBasedContentEditingSupport {

    /**
     * @param projectResourcesBlock
     * @param viewer
     */
    public AnalyzeResourceSourceEditingSupport(ProjectResourcesBlock projectResourcesBlock, ColumnViewer viewer) {
      super(projectResourcesBlock, viewer);
    }

    @Override
    protected void setValueInternal(FileBasedContentProvider content, boolean value) {
      if (value) {
        content.setAnalyzeMode(AnalyzeMode.BINARIES_AND_SOURCES);
      } else {
        if (content.getFileBasedContent().getAnalyzeMode() != AnalyzeMode.DO_NOT_ANALYZE) {
          content.setAnalyzeMode(AnalyzeMode.BINARIES_ONLY);
        }
      }

    }

    @Override
    protected boolean getValueInternal(FileBasedContentProvider content) {
      return content.getFileBasedContent().getAnalyzeMode() == AnalyzeMode.BINARIES_AND_SOURCES;
    }

    @Override
    protected boolean canEditInternal(FileBasedContentProvider content) {
      return content.getFileBasedContent().getAnalyzeMode().isAnalyze();
    }

  }

}