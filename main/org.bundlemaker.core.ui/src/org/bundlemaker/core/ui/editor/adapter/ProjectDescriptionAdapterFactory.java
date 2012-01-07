/*******************************************************************************
 * Copyright (c) 2011 Nils Hartmann
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.editor.adapter;

import org.bundlemaker.core.content.jdt.JdtProjectContentProvider;
import org.bundlemaker.core.projectdescription.IProjectDescription;
import org.bundlemaker.core.projectdescription.file.FileBasedContentProvider;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 *         This one could be replaced with the extension point <code>org.eclipse.core.runtime.adapters</code>
 * 
 */
public class ProjectDescriptionAdapterFactory implements IAdapterFactory {

  private final static Class<?>                  ADAPTER_LIST[] = new Class<?>[] { IWorkbenchAdapter.class };

  private final ProjectDescriptionAdapter        _projectDescriptionAdapter;

  private final FileBasedContentProviderAdapter  _fileBasedContentProviderAdapter;

  private final ProjectPathAdapter               _projectPathAdapter;

  private final JdtProjectContentProviderAdapter _jdtProjectContentProviderAdapter;

  public ProjectDescriptionAdapterFactory() {
    _projectDescriptionAdapter = new ProjectDescriptionAdapter();
    _fileBasedContentProviderAdapter = new FileBasedContentProviderAdapter();
    _projectPathAdapter = new ProjectPathAdapter();
    _jdtProjectContentProviderAdapter = new JdtProjectContentProviderAdapter();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
   */
  @Override
  public Class<?>[] getAdapterList() {
    return ADAPTER_LIST;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
   */
  @Override
  public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
    if (adapterType != IWorkbenchAdapter.class) {
      return null;
    }

    if (adaptableObject instanceof IProjectDescription) {
      return _projectDescriptionAdapter;
    }

    if (adaptableObject instanceof FileBasedContentProvider) {
      return _fileBasedContentProviderAdapter;
    }

    if (adaptableObject instanceof JdtProjectContentProvider) {
      return _jdtProjectContentProviderAdapter;
    }

    if (adaptableObject instanceof ProjectPath) {
      return _projectPathAdapter;
    }

    return null;
  }

  public static void register() {
    ProjectDescriptionAdapterFactory factory = new ProjectDescriptionAdapterFactory();
    Platform.getAdapterManager().registerAdapters(factory, IProjectDescription.class);
    Platform.getAdapterManager().registerAdapters(factory, FileBasedContentProvider.class);
    Platform.getAdapterManager().registerAdapters(factory, JdtProjectContentProvider.class);
    Platform.getAdapterManager().registerAdapters(factory, ProjectPath.class);
  }

  /**
   * 
   */
  public static void unregister() {
    // TODO unregister
  }

}
