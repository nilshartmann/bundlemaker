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
package org.bundlemaker.core.resource;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.bundlemaker.core.internal.resource.FlyWeightCache;
import org.bundlemaker.core.internal.resource.FlyWeightString;
import org.bundlemaker.core.internal.resource.ZipFileCache;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Default implementation of the interface {@link IResourceKey}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be extended by clients.
 */
public class ResourceKey implements IResourceKey {

  /** the content id */
  private FlyWeightString _contentId;

  /** the root of the resource */
  private FlyWeightString _root;

  /** the path of the resource */
  private String          _path;

  /** - */
  private long            _timestamp = -1;

  /**
   * <p>
   * Creates a new instance of type {@link ResourceKey}.
   * </p>
   * 
   * @param contentId
   * @param root
   * @param path
   */
  public ResourceKey(String contentId, String root, String path) {
    Assert.isNotNull(contentId);
    Assert.isNotNull(root);
    Assert.isNotNull(path);

    _contentId = new FlyWeightString(normalize(contentId));
    _root = new FlyWeightString(normalize(root));
    _path = path;
  }

  /**
   * <p>
   * Creates a new instance of type {@link ResourceKey}.
   * </p>
   * 
   * @param contentId
   * @param root
   * @param path
   * @param cache
   */
  protected ResourceKey(String contentId, String root, String path, FlyWeightCache cache) {

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
  public boolean isValidJavaPackage() {

    //
    String[] elements = getPath().split("/");

    //
    for (int i = 0; i < elements.length - 1; i++) {

      String element = elements[i];

      if (!isValidJavaIdentifier(element)) {
        return false;
      }
    }

    //
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPackageName() {
    return getDirectory().replace('/', '.');
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
        setTimeStamp(zipEntry);

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
        setTimeStamp(file);
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
  public long getTimestamp() {

    //
    if (_timestamp == -1) {

      // jar file?
      if (getRoot().endsWith(".jar") || getRoot().endsWith(".zip")) {

        try {
          ZipFile zipFile = ZipFileCache.instance().getZipFile(getRoot());
          //
          // new ZipFile(new File(getRoot()));
          ZipEntry zipEntry = zipFile.getEntry(getPath());
          setTimeStamp(zipEntry);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

      } else {
        setTimeStamp(new File(getRoot(), getPath()));
      }
    }

    return _timestamp;
  }

  /**
   * <p>
   * </p>
   * 
   * @param s
   * @return
   */
  public final static boolean isValidJavaIdentifier(String s) {

    // an empty or null string cannot be a valid identifier
    if (s == null || s.length() == 0) {
      return false;
    }

    char[] c = s.toCharArray();
    if (!Character.isJavaIdentifierStart(c[0])) {
      return false;
    }

    for (int i = 1; i < c.length; i++) {
      if (!Character.isJavaIdentifierPart(c[i])) {
        return false;
      }
    }

    return true;
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
    if (!(ResourceKey.class.isAssignableFrom(obj.getClass())))
      return false;
    ResourceKey other = (ResourceKey) obj;
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
    return "ResourceKey [_contentId=" + _contentId + ", _root=" + _root + ", _path=" + _path + "]";
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
   * @param file
   */
  private void setTimeStamp(File file) {
    long timestamp = file.lastModified();
    if (timestamp != 0l) {
      _timestamp = timestamp;
    } else {
      System.out.println(this);
      throw new RuntimeException();
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param zipEntry
   */
  private void setTimeStamp(ZipEntry zipEntry) {
    long timestamp = zipEntry.getTime();
    if (timestamp != -1l) {
      _timestamp = timestamp;
    } else {
      System.out.println(this);
      throw new RuntimeException();
    }
  }
}
