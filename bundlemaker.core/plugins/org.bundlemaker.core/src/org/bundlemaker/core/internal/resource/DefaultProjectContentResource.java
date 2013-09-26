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
package org.bundlemaker.core.internal.resource;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.bundlemaker.core.common.FlyWeightString;
import org.bundlemaker.core.common.FlyWeightStringCache;
import org.bundlemaker.core.project.IProjectContentResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;

/**
 * <p>
 * Default implementation of the interface {@link IProjectContentResource}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be extended by clients.
 */
public class DefaultProjectContentResource implements IProjectContentResource {

  /** the content id */
  private FlyWeightString _contentId;

  /** the root of the resource */
  private FlyWeightString _root;

  /** the path of the resource */
  private String          _path;

  /** - */
  private boolean         _analyzeReferences = true;

  /**
   * <p>
   * Creates a new instance of type {@link DefaultProjectContentResource}.
   * </p>
   * 
   * @param contentId
   * @param root
   * @param path
   */
  public DefaultProjectContentResource(String contentId, String root, String path) {
    Assert.isNotNull(contentId);
    Assert.isNotNull(root);
    Assert.isNotNull(path);

    _contentId = new FlyWeightString(normalize(contentId));
    _root = new FlyWeightString(normalize(root));
    _path = path;
  }

  /**
   * <p>
   * Creates a new instance of type {@link DefaultProjectContentResource}.
   * </p>
   * 
   * @param contentId
   * @param root
   * @param path
   * @param cache
   */
  protected DefaultProjectContentResource(String contentId, String root, String path, FlyWeightStringCache cache) {

    Assert.isNotNull(contentId);
    Assert.isNotNull(root);
    Assert.isNotNull(path);
    Assert.isNotNull(cache);

    _contentId = cache.getFlyWeightString(contentId);
    _root = cache.getFlyWeightString(root);
    _path = path;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T adaptAs(Class<T> clazz) {

    //
    T result = (T) Platform.getAdapterManager().getAdapter(this, clazz);
    if (result != null) {
      return result;
    }

    //
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getAdapter(Class adapter) {
    return adaptAs(adapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getProjectContentEntryId() {
    return _contentId.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getRoot() {
    return _root.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPath() {
    return _path;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDirectory() {
    int lastIndex = _path.lastIndexOf('/');
    return lastIndex != -1 ? _path.substring(0, lastIndex) : "";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    int lastIndex = _path.lastIndexOf('/');
    return lastIndex != -1 ? _path.substring(lastIndex + 1) : _path;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] getContent() {

    // jar file?
    if (getRoot().endsWith(".jar") || getRoot().endsWith(".zip")) {

      try {
        ZipFile zipFile = new ZipFile(new File(getRoot()));
        ZipEntry zipEntry = zipFile.getEntry(getPath());

        InputStream is = zipFile.getInputStream(zipEntry);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
          buffer.write(data, 0, nRead);
        }
        buffer.flush();

        byte[] result = buffer.toByteArray();

        is.close();
        zipFile.close();

        // return the result
        return result;

      } catch (Exception ex) {
        // TODO Auto-generated catch block
        ex.printStackTrace();
        throw new RuntimeException("Error while parsing '" + getRoot() + "' with path '" + getPath() + "': " + ex, ex);
      }
    }

    // get the root file
    File rootFile = new File(getRoot());

    //
    if (rootFile.isDirectory()) {

      try {

        File file = new File(rootFile, getPath());
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
          buffer.write(data, 0, nRead);
        }
        buffer.flush();

        byte[] result = buffer.toByteArray();

        is.close();
        buffer.close();

        //
        return result;

      } catch (Exception e) {
        throw new RuntimeException("FEHLER");
      }

    } else {
      throw new RuntimeException("FEHLER");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getCurrentTimestamp() {

    // jar file?
    if (getRoot().endsWith(".jar") || getRoot().endsWith(".zip")) {
      try {
        ZipFile zipFile = ZipFileCache.instance().getZipFile(getRoot());
        ZipEntry zipEntry = zipFile.getEntry(getPath());
        return zipEntry.getTime();
      } catch (Exception e) {
      }
    } else {
      //
      return new File(getRoot(), getPath()).lastModified();
    }

    //
    return -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + _contentId.hashCode();
    result = prime * result + _path.hashCode();
    result = prime * result + _root.hashCode();
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(DefaultProjectContentResource.class.isAssignableFrom(obj.getClass())))
      return false;
    DefaultProjectContentResource other = (DefaultProjectContentResource) obj;
    if (!_contentId.equals(other.getProjectContentEntryId()))
      return false;
    if (!_path.equals(other.getPath()))
      return false;
    if (!_root.equals(other.getRoot()))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return this.getClass().getCanonicalName() + " [_contentId=" + _contentId + ", _root=" + _root + ", _path=" + _path
        + "]";
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private String normalize(String string) {
    return string.replace('\\', '/');
  }

  /**
   * <p>
   * </p>
   * 
   * @return the analyzeReferences
   */
  @Override
  public boolean isAnalyzeReferences() {
    return _analyzeReferences;
  }

  /**
   * <p>
   * </p>
   * 
   * @param analyzeReferences
   *          the analyzeReferences to set
   */
  public void setAnalyzeReferences(boolean analyzeReferences) {
    _analyzeReferences = analyzeReferences;
  }
}
