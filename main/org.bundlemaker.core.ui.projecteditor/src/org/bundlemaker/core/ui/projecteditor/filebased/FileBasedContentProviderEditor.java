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
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.file.FileBasedContentProvider;
import org.bundlemaker.core.projectdescription.file.VariablePath;
import org.bundlemaker.core.ui.BundleMakerImages;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Image;

/**
 * @author Nils Hartmann (nils@nilshartmann.net) TODO: Rendering (getRootElement, getChildren, getImage, getLabel,
 *         AnalyzeMode) should be re-usable, e.g. for JDTProjectContentProvider
 */
public class FileBasedContentProviderEditor implements IProjectContentProviderEditor {

  @Override
  public boolean canHandle(IProjectContentProvider provider) {
    return (provider instanceof FileBasedContentProvider);
  }

  @Override
  public Object getRootElement(IBundleMakerProject project, IProjectContentProvider provider) {
    return provider;
  }

  @Override
  public List<Object> getChildren(IBundleMakerProject project, IProjectContentProvider provider, Object rootElement) {

    if (!(rootElement instanceof FileBasedContentProvider)) {
      return null;
    }

    FileBasedContentProvider fileBasedContentProvider = (FileBasedContentProvider) rootElement;

    List<Object> result = new LinkedList<Object>();

    addAsProjectPaths(result, fileBasedContentProvider.getFileBasedContent().getBinaryRootPaths(), false);
    addAsProjectPaths(result, fileBasedContentProvider.getFileBasedContent().getSourceRootPaths(), true);

    return result;
  }

  @Override
  public AnalyzeMode getAnalyzeMode(Object element) {
    if (!(element instanceof FileBasedContentProvider)) {
      return null;
    }

    FileBasedContentProvider provider = (FileBasedContentProvider) element;
    return provider.getFileBasedContent().getAnalyzeMode();
  }

  private void addAsProjectPaths(List<Object> target, Set<VariablePath> paths, boolean source) {
    for (VariablePath variablePath : paths) {
      target.add(new ProjectPath(variablePath, source));
    }
  }

  @Override
  public Image getImage(Object element) {
    if (element instanceof FileBasedContentProvider) {
      return BundleMakerImages.RESOURCE_CONTENT.getImage();
    }

    if (element instanceof ProjectPath) {
      ProjectPath projectPath = (ProjectPath) element;
      return getImageForPath(projectPath);
    }
    return null;
  }

  @Override
  public String getLabel(Object element) {
    if (element instanceof FileBasedContentProvider) {
      FileBasedContentProvider fileBasedContentProvider = (FileBasedContentProvider) element;

      return String.format("%s [%s]", fileBasedContentProvider.getFileBasedContent().getName(),
          fileBasedContentProvider.getFileBasedContent().getVersion());
    }

    if (element instanceof ProjectPath) {
      ProjectPath projectPath = (ProjectPath) element;
      return projectPath.getTitle();
    }

    return String.valueOf(element);
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
