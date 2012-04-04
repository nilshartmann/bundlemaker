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
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.file.FileBasedContent;
import org.bundlemaker.core.projectdescription.file.VariablePath;
import org.bundlemaker.core.ui.BundleMakerImages;
import org.eclipse.core.runtime.IAdapterFactory;
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
      List<? extends IProjectContentProvider> fileBasedContent = project.getModifiableProjectDescription()
          .getContentProviders();
      return fileBasedContent.toArray(new IProjectContentProvider[0]);
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
      IProjectContentEntry content = (IProjectContentEntry) o;
      children.addAll(((FileBasedContent) content).getBinaryRootPaths());
      children.addAll(((FileBasedContent) content).getSourceRootPaths());
      return children.toArray();
    }

    @Override
    public ImageDescriptor getImageDescriptor(Object object) {
      return BundleMakerImages.RESOURCE_CONTENT.getImageDescriptor();
    }

    @Override
    public String getLabel(Object o) {
      IProjectContentEntry content = (IProjectContentEntry) o;
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
      VariablePath path = (VariablePath) object;

      // TODO: FIX ME!!
      return RootPathHelper.getImageDescriptorForPath(path, true);
    }

    @Override
    public String getLabel(Object o) {
      VariablePath path = (VariablePath) o;
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

    if (adaptableObject instanceof IProjectContentEntry) {
      return _fileBasedContentAdapter;
    }

    if (adaptableObject instanceof VariablePath) {
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
    // BundleMakerAdapterFactory bundleMakerAdapterFactory = new BundleMakerAdapterFactory();
    // Platform.getAdapterManager().registerAdapters(bundleMakerAdapterFactory, IBundleMakerProject.class);
    // Platform.getAdapterManager().registerAdapters(bundleMakerAdapterFactory, IProjectDescription.class);
    // Platform.getAdapterManager().registerAdapters(bundleMakerAdapterFactory, IProjectContentEntry.class);
    // Platform.getAdapterManager().registerAdapters(bundleMakerAdapterFactory, VariablePath.class);
  }

}
