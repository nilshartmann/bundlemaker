/*******************************************************************************
 * Copyright (c) 2013 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.common;

import java.io.File;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.zip.ZipFile;

import org.bundlemaker.core.common.collections.GenericCache;

/**
 * A Cache for {@link ZipFile} instances.
 * 
 * The cache can be globally enabled or disabled. When disabled each invocation of {@link ZipFile}
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ZipFileCache {

  private static ZipFileCache _instance = new ZipFileCache();

  /**
   * @return the singleton instance
   */
  public static ZipFileCache instance() {
    return _instance;
  }

  private GenericCache<String, ZipFile> _cache       = new GenericCache<String, ZipFile>() {

                                                       @Override
                                                       protected ZipFile create(String key) {
                                                         return newZipFile(key);
                                                       }
                                                     };

  private boolean                       _cacheActive = false;

  /**
   * Activate the cache
   */
  public void activateCache() {
    _cacheActive = true;
  }

  /**
   * Deactive the cache.
   * 
   * <p>
   * The cache is cleaned and all cached ZipFiles are closed
   */
  public void deactivateCache() {
    _cacheActive = false;

    Iterator<Entry<String, ZipFile>> iterator = _cache.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, ZipFile> entry = iterator.next();
      try {
        entry.getValue().close();
      } catch (Exception ex) {
        // ignore
      }

      iterator.remove();
    }
  }

  /**
   * Get a {@link ZipFile} instance for the specified fileName
   * 
   * @param fileName
   * @return New or Cached ZipFile
   */
  public ZipFile getZipFile(String fileName) {
    if (_cacheActive) {
      return _cache.getOrCreate(fileName);
    }

    return newZipFile(fileName);
  }

  protected ZipFile newZipFile(String fileName) {
    File file = new File(fileName);
    try {
      return new ZipFile(file);
    } catch (Exception ex) {
      throw new IllegalStateException(
          "Could not open ZipFile for '"
              + file + "': " + ex, ex);
    }
  }

}
