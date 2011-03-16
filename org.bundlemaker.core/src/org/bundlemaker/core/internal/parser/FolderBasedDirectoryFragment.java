/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.internal.parser;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.ResourceKey;
import org.eclipse.core.resources.IFile;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FolderBasedDirectoryFragment extends AbstractDirectoryFragment {

  /** - */
  private List<String>      _entries = new LinkedList<String>();

  //
  private Set<IResourceKey> _resourceKeys;

  /**
   * <p>
   * Creates a new instance of type {@link FolderBasedDirectoryFragment}.
   * </p>
   * 
   * @param file
   */
  public FolderBasedDirectoryFragment(File file) {
    super(file);
  }

  /**
   * <p>
   * Creates a new instance of type {@link FolderBasedDirectoryFragment}.
   * </p>
   * 
   * @param ifile
   */
  public FolderBasedDirectoryFragment(IFile ifile) {
    super(ifile);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IResourceKey> getResourceKeys() {

    //
    if (_resourceKeys == null) {

      //
      _resourceKeys = new HashSet<IResourceKey>();

      // parse each class file
      for (String content : getContent()) {

        _resourceKeys.add(new ResourceKey(getDirectory().getFileBasedContent().getId(), getDirectoryFragmentRoot()
            .getAbsolutePath(), content));
      }
    }

    //
    return _resourceKeys;
  }

  /**
   * <p>
   * </p>
   * 
   */
  public void addEntry(String entry) {
    _entries.add(entry);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getContent() {
    return _entries;
  }

  public List<File> getFileEntries() {

    List<File> result = new LinkedList<File>();

    for (String entry : _entries) {
      result.add(new File(getDirectoryFragmentRoot(), entry));
    }

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getResourceCount() {
    return _entries.size();
  }

  @Override
  public String toString() {
    return "DirectoryPackageContent [getPackageRoot()=" + getDirectoryFragmentRoot() + ", _entries=" + _entries + "]";
  }
}
