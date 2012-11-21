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
package org.bundlemaker.core.internal.projectdescription.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bundlemaker.core.projectdescription.file.FileBasedProjectContentInfo;
import org.bundlemaker.core.projectdescription.file.FileBasedProjectContentInfoService;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FileBasedProjectContentInfoServiceImpl implements FileBasedProjectContentInfoService {

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @return
   */
  public <T> FileBasedProjectContentInfo<T> extractJarInfo(File file) {
    try {
      if (file.isFile()) {
        return extractInfoFromFile(file);
      }

      return extractInfoFromPath(file);

      //
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @Override
  public <T> FileBasedProjectContentInfo<T> getAssociatedFileBasedProjectContent(FileBasedProjectContentInfo<T> info,
      List<FileBasedProjectContentInfo<T>> allInfos) {

    //
    for (FileBasedProjectContentInfo<T> i : allInfos) {

      //
      if (i.getName().equals(info.getName()) && i.getVersion().equals(info.getVersion())
          && i.isSource() != info.isSource()) {
        return i;
      }
    }

    //
    return null;
  }

  private static <T> FileBasedProjectContentInfo<T> extractInfoFromPath(File path) {
    String dirName = path.getName();
    int x = dirName.lastIndexOf('_');
    String version = "0.0.0";
    String name = dirName;

    if (x > 0) {
      name = dirName.substring(0, x);
      version = dirName.substring(x + 1);
    }

    return new FileBasedProjectContentInfo<T>(name, version, false);
  }

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @return
   * @throws IOException
   */
  private static <T> FileBasedProjectContentInfo<T> extractInfoFromFile(File file) throws IOException {

    //
    DefaultFileBasedContentInfoResolver resolver = new DefaultFileBasedContentInfoResolver();

    //
    if (resolver.resolve(file)) {

      // return the result
      return new FileBasedProjectContentInfo<T>(resolver.getName(), resolver.getVersion(), resolver.isSource());
    }

    // return the default result
    return new FileBasedProjectContentInfo<T>(file.getName(), "0.0.0", false);
  }
}
