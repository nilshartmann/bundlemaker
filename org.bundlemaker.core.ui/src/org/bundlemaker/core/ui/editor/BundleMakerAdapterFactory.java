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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.ui.internal.UIImages;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IPath;
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
  private final FileBasedContentAdapter                     _fileBasedContentAdapter                     = new FileBasedContentAdapter();

  private final BundleMakerPathAdapter                      _bundleMakerPathAdapter                      = new BundleMakerPathAdapter();

  private final BundleMakerProjectDescriptionWrapperAdapter _bundleMakerProjectDescriptionWrapperAdapter = new BundleMakerProjectDescriptionWrapperAdapter();

  class BundleMakerProjectDescriptionWrapperAdapter implements IWorkbenchAdapter {

    @Override
    public Object[] getChildren(Object o) {
      BundleMakerProjectDescriptionWrapper wrapper = (BundleMakerProjectDescriptionWrapper) o;
      return wrapper.getContent();
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
      children.addAll(asBundleMakerPaths(content.getBinaryPaths(), true));
      if (content.isResourceContent()) {
        children.addAll(asBundleMakerPaths(content.getSourcePaths(), false));
        // children.addAll(getChildren(content));
      }
      return children.toArray();
    }

    private Collection<Object> getChildren(IFileBasedContent content) {
      List<Object> children = new LinkedList<Object>();
      Set<IPath> sourcePaths = content.getSourcePaths();
      for (IPath iPath : sourcePaths) {

        children.add(new BundleMakerPath(iPath, false, iPath.toFile().isDirectory()));
      }
      return children;
    }

    @Override
    public ImageDescriptor getImageDescriptor(Object object) {
      return UIImages.RESOURCE_CONTENT.getImageDescriptor();
    }

    @Override
    public String getLabel(Object o) {
      IFileBasedContent content = (IFileBasedContent) o;
      return String.format("%s [%s]", content.getName(), content.getVersion(), content.isResourceContent());
    }

    @Override
    public Object getParent(Object o) {
      IFileBasedContent content = (IFileBasedContent) o;
      return null;
    }
  }

  static Collection<BundleMakerPath> asBundleMakerPaths(Collection<IPath> paths, boolean binary) {
    List<BundleMakerPath> bundleMakerPaths = new LinkedList<BundleMakerAdapterFactory.BundleMakerPath>();
    for (IPath path : paths) {
      bundleMakerPaths.add(new BundleMakerPath(path, binary, path.toFile().isDirectory()));
    }
    return bundleMakerPaths;
  }

  /**
   * An adapter for a {@link BundleMakerPath}-Element
   * <p>
   * </p>
   * 
   * 
   */
  class BundleMakerPathAdapter implements IWorkbenchAdapter {

    @Override
    public Object[] getChildren(Object o) {
      return new Object[0];
    }

    @Override
    public ImageDescriptor getImageDescriptor(Object object) {
      BundleMakerPath path = (BundleMakerPath) object;
      if (path.isFolder()) {
        if (path.isBinary()) {
          return UIImages.BINARY_FOLDER.getImageDescriptor();
        }
        return UIImages.SOURCE_FOLDER.getImageDescriptor();
      }
      if (path.isBinary()) {
        return UIImages.BINARY_ARCHIVE.getImageDescriptor();
      }
      return UIImages.SOURCE_ARCHIVE.getImageDescriptor();
    }

    @Override
    public String getLabel(Object o) {
      BundleMakerPath path = (BundleMakerPath) o;
      return path.getLabel();
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

    if (adaptableObject instanceof BundleMakerProjectDescriptionWrapper) {
      return _bundleMakerProjectDescriptionWrapperAdapter;
    }

    if (adaptableObject instanceof IFileBasedContent) {
      return _fileBasedContentAdapter;
    }

    if (adaptableObject instanceof BundleMakerPath) {
      return _bundleMakerPathAdapter;
    }

    return null;
  }

  static class BundleMakerPath {
    private final IPath   _path;

    private final boolean _binary;

    private final boolean _folder;

    public BundleMakerPath(IPath path, boolean binary, boolean folder) {
      super();
      _path = path;
      _binary = binary;
      _folder = folder;
    }

    public boolean isFolder() {
      return _folder;
    }

    public String getLabel() {
      return _path.toString();
    }

    public boolean isBinary() {
      return _binary;
    }
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
    Platform.getAdapterManager()
        .registerAdapters(bundleMakerAdapterFactory, BundleMakerProjectDescriptionWrapper.class);
    Platform.getAdapterManager().registerAdapters(bundleMakerAdapterFactory, IBundleMakerProjectDescription.class);
    Platform.getAdapterManager().registerAdapters(bundleMakerAdapterFactory, IFileBasedContent.class);
    Platform.getAdapterManager().registerAdapters(bundleMakerAdapterFactory, BundleMakerPath.class);

  }

}
