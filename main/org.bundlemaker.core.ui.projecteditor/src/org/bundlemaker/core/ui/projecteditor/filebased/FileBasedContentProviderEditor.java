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

import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.file.FileBasedContentProvider;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;
import org.eclipse.swt.graphics.Image;

/**
 * @author Nils Hartmann (nils@nilshartmann.net) TODO: Rendering (getRootElement, getChildren, getImage, getLabel,
 *         AnalyzeMode) should be re-usable, e.g. for JDTProjectContentProvider
 */
public class FileBasedContentProviderEditor implements IProjectContentProviderEditor {

  private final FileBasedContentRenderer _fileBasedContentRenderer = FileBasedContentRenderer.getInstance();

  @Override
  public boolean canHandle(IProjectContentProvider provider) {
    return (provider instanceof FileBasedContentProvider);
  }

  @Override
  public Object getRootElement(IBundleMakerProject project, IProjectContentProvider provider) {
    return provider;
    // FileBasedContentProvider fileBasedContentProvider = (FileBasedContentProvider) provider;
    //
    // return fileBasedContentProvider.getFileBasedContent();
  }

  @Override
  public List<? extends Object> getChildren(IBundleMakerProject project, IProjectContentProvider provider,
      Object rootElement) {

    return _fileBasedContentRenderer.getChildren(project, rootElement);

  }

  @Override
  public AnalyzeMode getAnalyzeMode(Object element) {
    return _fileBasedContentRenderer.getAnalyzeMode(element);
  }

  @Override
  public Image getImage(Object element) {
    return _fileBasedContentRenderer.getImage(element);

  }

  @Override
  public String getLabel(Object element) {
    return _fileBasedContentRenderer.getLabel(element);

  }

}
