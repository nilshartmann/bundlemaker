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
package org.bundlemaker.core.ui.projecteditor.filebased;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.VariablePath;
import org.bundlemaker.core.projectdescription.file.FileBasedProjectContentProvider;
import org.bundlemaker.core.ui.BundleMakerImages;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Image;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class FileBasedContentRenderer {

  private final static FileBasedContentRenderer _instance = new FileBasedContentRenderer();

  public static FileBasedContentRenderer getInstance() {
    return _instance;
  }

  private FileBasedContentRenderer() {

  }

  public boolean canRender(Object object) {
    return (object instanceof IProjectContentEntry || object instanceof ProjectPath);
  }

  /**
   * @param project
   * @param rootElement
   * @return
   */
  public List<? extends Object> getChildren(IBundleMakerProject project, Object object) {

    if (object instanceof FileBasedProjectContentProvider) {
      FileBasedProjectContentProvider fileBasedContentProvider = (FileBasedProjectContentProvider) object;
      return getChildren(fileBasedContentProvider.getFileBasedContent());

    }

    if (object instanceof IProjectContentEntry) {
      IProjectContentEntry fileBasedContent = (IProjectContentEntry) object;
      return getChildren(fileBasedContent);
    }

    return null;
  }

  protected List<? extends Object> getChildren(IProjectContentEntry fileBasedContent) {
    List<Object> result = new LinkedList<Object>();

    addAsProjectPaths(result, fileBasedContent.getBinaryRootPaths(), false);
    addAsProjectPaths(result, fileBasedContent.getSourceRootPaths(), true);

    return result;
  }

  private void addAsProjectPaths(List<Object> target, Set<VariablePath> paths, boolean source) {
    for (VariablePath variablePath : paths) {
      target.add(new ProjectPath(variablePath, source));
    }
  }

  /**
   * @param element
   * @return
   */
  public AnalyzeMode getAnalyzeMode(Object element) {
    if (element instanceof FileBasedProjectContentProvider) {
      return ((FileBasedProjectContentProvider) element).getAnalyzeMode();
    }
    if (element instanceof IProjectContentEntry) {
      return ((IProjectContentEntry) element).getAnalyzeMode();
    }

    return null;
  }

  /**
   * @param element
   * @return
   */
  public Image getImage(Object element) {
    if (element instanceof FileBasedProjectContentProvider || element instanceof IProjectContentEntry) {
      return BundleMakerImages.RESOURCE_CONTENT.getImage();
    }

    if (element instanceof ProjectPath) {
      ProjectPath projectPath = (ProjectPath) element;
      return getImageForPath(projectPath);
    }
    return null;
  }

  /**
   * @param element
   * @return
   */
  public String getLabel(Object element) {

    if (element instanceof FileBasedProjectContentProvider) {
      FileBasedProjectContentProvider fileBasedContentProvider = (FileBasedProjectContentProvider) element;
      return getFileBasedContentLabel(fileBasedContentProvider.getFileBasedContent());
    }

    if (element instanceof IProjectContentEntry) {
      return getFileBasedContentLabel((IProjectContentEntry) element);
    }

    if (element instanceof ProjectPath) {
      ProjectPath projectPath = (ProjectPath) element;
      return projectPath.asString();
    }

    return String.valueOf(element);
  }

  protected String getFileBasedContentLabel(IProjectContentEntry fileBasedContent) {
    return String.format("%s [%s]", fileBasedContent.getName(), fileBasedContent.getVersion());

  }

  /**
   * @param element
   * @return
   */
  private Image getImageForPath(ProjectPath projectPath) {
    boolean isFolder;
    VariablePath path = projectPath.getPath();
    try {
      isFolder = path.getAsFile().isDirectory();
    } catch (CoreException ex) {
      return BundleMakerImages.UNKNOWN_OBJECT.getImage();
    }

    if (isFolder) {
      if (projectPath.isBinary()) {
        return BundleMakerImages.BINARY_FOLDER.getImage();
      }
      return BundleMakerImages.SOURCE_FOLDER.getImage();
    }
    if (projectPath.isBinary()) {
      return BundleMakerImages.BINARY_ARCHIVE.getImage();
    }
    return BundleMakerImages.SOURCE_ARCHIVE.getImage();

  }

}
