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
package org.bundlemaker.core.ui.editor;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.projectdescription.IRootPath;
import org.bundlemaker.core.projectdescription.modifiable.IModifiableFileBasedContent;
import org.bundlemaker.core.ui.internal.UIImages;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * <p>
 * Provides {@link IWorkbenchAdapter IWorkbenchAdapters} for BundleMaker's model elements
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BundleMakerAdapterFactory implements IAdapterFactory {
  private final FileBasedContentAdapter   _fileBasedContentAdapter   = new FileBasedContentAdapter();

  private final RootPathAdapter           _rootPathAdapter           = new RootPathAdapter();

  private final BundleMakerProjectAdapter _bundleMakerProjectAdapter = new BundleMakerProjectAdapter();

  class BundleMakerProjectAdapter implements IWorkbenchAdapter {

    @Override
    public Object[] getChildren(Object o) {
      BundleMakerProject project = (BundleMakerProject) o;
      List<? extends IModifiableFileBasedContent> fileBasedContent = project.getModifiableProjectDescription()
          .getModifiableFileBasedContent();
      return fileBasedContent.toArray(new IModifiableFileBasedContent[0]);
    }

    @Override
    public ImageDescriptor getImageDescriptor(Object object) {
      return null;
    }

    @Override
    public String getLabel(Object o) {
      return null;
    }

    @Override
    public Object getParent(Object o) {
      return null;
    }

  }

  class FileBasedContentAdapter implements IWorkbenchAdapter {

    @Override
    public Object[] getChildren(Object o) {

      List<Object> children = new LinkedList<Object>();
      IFileBasedContent content = (IFileBasedContent) o;
      children.addAll(content.getBinaryRootPaths());
      children.addAll(content.getSourceRootPaths());
      return children.toArray();
    }

    @Override
    public ImageDescriptor getImageDescriptor(Object object) {
      return UIImages.RESOURCE_CONTENT.getImageDescriptor();
    }

    @Override
    public String getLabel(Object o) {
      IFileBasedContent content = (IFileBasedContent) o;
      return String.format("%s [%s]", content.getName(), content.getVersion());
    }

    @Override
    public Object getParent(Object o) {
      return null;
    }
  }

  /**
   * An adapter for a {@link BundleMakerPath}-Element
   * <p>
   * </p>
   * 
   * 
   */
  class RootPathAdapter implements IWorkbenchAdapter {

    @Override
    public Object[] getChildren(Object o) {
      return new Object[0];
    }

    @Override
    public ImageDescriptor getImageDescriptor(Object object) {
      IRootPath path = (IRootPath) object;

      return RootPathHelper.getImageDescriptorForPath(path);
    }

    @Override
    public String getLabel(Object o) {
      IRootPath path = (IRootPath) o;
      return RootPathHelper.getLabel(path);
    }

    @Override
    public Object getParent(Object o) {
      // TODO Auto-generated method stub
      return null;
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
    if (adapterType != IWorkbenchAdapter.class) {
      return null;
    }

    if (adaptableObject instanceof IBundleMakerProject) {
      return _bundleMakerProjectAdapter;
    }

    if (adaptableObject instanceof IFileBasedContent) {
      return _fileBasedContentAdapter;
    }

    if (adaptableObject instanceof IRootPath) {
      return _rootPathAdapter;
    }

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("rawtypes")
  @Override
  public Class[] getAdapterList() {
    return new Class[] { IWorkbenchAdapter.class };
  }

  public static void register() {
    BundleMakerAdapterFactory bundleMakerAdapterFactory = new BundleMakerAdapterFactory();
    Platform.getAdapterManager().registerAdapters(bundleMakerAdapterFactory, IBundleMakerProject.class);
    Platform.getAdapterManager().registerAdapters(bundleMakerAdapterFactory, IBundleMakerProjectDescription.class);
    Platform.getAdapterManager().registerAdapters(bundleMakerAdapterFactory, IFileBasedContent.class);
    Platform.getAdapterManager().registerAdapters(bundleMakerAdapterFactory, IRootPath.class);

  }

}
