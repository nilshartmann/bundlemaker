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
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.ResourceKey;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JarFileBasedDirectoryFragment extends AbstractDirectoryFragment {

  /** the jar file */
  private JarFile           _jarFile;

  /** the jar file entries for this package */
  private List<JarEntry>    _jarEntries;

  //
  private Set<IResourceKey> _resourceKeys;

  /**
   * <p>
   * Creates a new instance of type {@link JarFileBasedDirectoryFragment}.
   * </p>
   * 
   * @param jarFile
   */
  public JarFileBasedDirectoryFragment(File file, JarFile jarFile) {
    super(file);

    Assert.isNotNull(jarFile);

    _jarFile = jarFile;
    _jarEntries = new LinkedList<JarEntry>();
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

  @Override
  public List<String> getContent() {

    List<String> result = new LinkedList<String>();

    for (JarEntry jarEntry : _jarEntries) {
      result.add(jarEntry.getName());
    }

    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Override
  public int getResourceCount() {
    return _jarEntries.size();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public JarFile getJarFile() {
    return _jarFile;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<JarEntry> getJarEntries() {
    return _jarEntries;
  }

  /**
   * <p>
   * </p>
   * 
   * @param jarEntry
   */
  void addJarEntry(JarEntry jarEntry) {
    _jarEntries.add(jarEntry);
  }

  @Override
  public String toString() {
    return "JarFilePackageContent [_jarEntries=" + _jarEntries + ", _jarFile=" + _jarFile + ", getDirectoryRoot()="
        + getDirectoryFragmentRoot() + ", getWorkspaceRelativeDirectoryRoot()=" + getWorkspaceRelativeRoot() + "]";
  }
}
