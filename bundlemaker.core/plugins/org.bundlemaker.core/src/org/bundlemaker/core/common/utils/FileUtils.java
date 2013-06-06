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
package org.bundlemaker.core.common.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.bundlemaker.core.internal.Activator;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * <p>
 * Helper class that provides utility methods for retrieving all children of a given file.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FileUtils {

  // public static void copyFile(File in, File out) throws IOException {
  // FileChannel inChannel = new FileInputStream(in).getChannel();
  // FileChannel outChannel = new FileOutputStream(out).getChannel();
  // try {
  // inChannel.transferTo(0, inChannel.size(), outChannel);
  // } catch (IOException e) {
  // throw e;
  // } finally {
  // if (inChannel != null)
  // inChannel.close();
  // if (outChannel != null)
  // outChannel.close();
  // }
  // }

  /**
   * <p>
   * </p>
   * 
   * @param string
   * @return
   */
  public static String normalize(String string) {
    return string.replace('/', File.separatorChar).replace('\\', File.separatorChar);
  }

  /**
   * Closes the supplied stream if it's available.
   * 
   * @param closeable
   *          the closeable. Maybe <code>null</code>.
   */
  public static final void close(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (IOException ex) {
        // generally not interesting so a warning is appropriate here
        //
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param instream
   * @param outstream
   * @throws IOException
   */
  public static final void copy(InputStream instream, OutputStream outstream) throws IOException {
    copy(instream, outstream, new byte[1024]);
  }

  /**
   * Copies the complete content from an InputStream into an OutputStream using a specified buffer. Both streams will be
   * closed after completion or in case an exception comes up.
   * 
   * @param instream
   *          The InputStream providing the content. Not <code>null</code>.
   * @param outstream
   *          The OutputStream used to write the content to. Not <code>null</code>.
   * @param buffer
   *          The buffer used for the copying process. Not <code>null</code> .
   * 
   * @throws IOException
   *           Copying failed for some reason.
   */
  public static final void copy(InputStream instream, OutputStream outstream, byte[] buffer) throws IOException {

    Assert.isNotNull(instream);
    Assert.isNotNull(outstream);

    try {
      int read = instream.read(buffer);
      while (read != -1) {
        if (read > 0) {
          outstream.write(buffer, 0, read);
        }
        read = instream.read(buffer);
      }
    } finally {
      close(outstream);
      close(instream);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @return
   * @throws CoreException
   */
  public static List<String> getAllChildren(File file) throws CoreException {

    //
    if (file.isDirectory()) {

      List<String> result = new LinkedList<String>();
      getAllChildren(file, file, result);
      return result;
    }

    //
    else if (file.isFile() && (file.getName().endsWith(".zip") || file.getName().endsWith(".jar"))) {

      try {
        ZipFile zipFile = new ZipFile(file);

        Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

        List<String> result = new LinkedList<String>();

        while (enumeration.hasMoreElements()) {
          ZipEntry zipEntry = (ZipEntry) enumeration.nextElement();
          if (!zipEntry.isDirectory()) {
            String name = zipEntry.getName();
            name = name.replace('\\', '/');
            result.add(name);
          }
        }

        return result;

      } catch (Exception e) {
        // throw the core exception
        throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ""));
      }
    }

    // throw the core exception
    throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, String.format(
        "File '%s' does not exist.", file.getAbsolutePath())));
  }

  /**
   * <p>
   * </p>
   * 
   * @param root
   * @param directory
   * @return
   */
  private static void getAllChildren(File root, File directory, List<String> content) {

    int length = root.getAbsolutePath().length();

    //
    for (File child : directory.listFiles()) {

      if (child.isFile()) {

        String entry = child.getAbsolutePath().substring(length + 1);

        entry = entry.replace("\\", "/");

        content.add(entry);

      } else if (child.isDirectory()) {
        getAllChildren(root, child, content);
      }
    }
  }
}
