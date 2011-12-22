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
package org.bundlemaker.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.bundlemaker.core.resource.IReadableResource;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public final class JarFileUtils {

  /**
   * <p>
   * Creates a jar archive for the given list of {@link IResource IResources}.
   * </p>
   * 
   * @param resources
   *          the list of resources
   * @param manifest
   *          the manifest file
   * @param archiveFile
   *          the archive file to create
   */
  public static void createJarArchive(Set<IResource> resources, Manifest manifest,
      Set<IReadableResource> additionalContent, OutputStream outputStream) {

    Assert.isNotNull(resources);
    Assert.isNotNull(manifest);

    manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");

    // create the input and output streams

    JarOutputStream jarOutputStream = null;
    InputStream inputStream = null;

    try {

      // open the archive file
      jarOutputStream = new JarOutputStream(outputStream, manifest);

      // add all the resources
      for (IResource resourceStandin : resources) {

        // add everything but the manifest
        if (!skipResource(resourceStandin.getPath())) {

          // add archive entry
          JarEntry newEntry = new JarEntry(resourceStandin.getPath());
          jarOutputStream.putNextEntry(newEntry);

          // copy
          inputStream = new ByteArrayInputStream(resourceStandin.getContent());
          copy(inputStream, jarOutputStream);

          inputStream.close();

          jarOutputStream.closeEntry();
        }
      }

      // add all additional files
      for (IReadableResource contentProvider : additionalContent) {

        // add archive entry
        JarEntry newEntry = new JarEntry(contentProvider.getPath());
        jarOutputStream.putNextEntry(newEntry);

        // copy
        inputStream = new ByteArrayInputStream(contentProvider.getContent());
        copy(inputStream, jarOutputStream);

        inputStream.close();

        jarOutputStream.closeEntry();
      }

      //
      jarOutputStream.flush();

    } catch (Exception ex) {
      // TODO
      ex.printStackTrace();

    } finally {
      close(jarOutputStream);
      close(inputStream);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @return
   */
  public static boolean skipResource(String path) {

    // skip the original manifest
    if ("META-INF/MANIFEST.MF".equalsIgnoreCase(path) || "META-INF/".equalsIgnoreCase(path)) {
      return true;
    }

    // skip META-INF/*.RSA, META-INF/*.DSA and META-INF/*.SF files
    if (path.matches("[Mm][Ee][Tt][Aa]-[Ii][Nn][Ff]/[^/]*\\.[Rr][Ss][Aa]")
        || path.matches("[Mm][Ee][Tt][Aa]-[Ii][Nn][Ff]/[^/]*\\.[Dd][Ss][Aa]")
        || path.matches("[Mm][Ee][Tt][Aa]-[Ii][Nn][Ff]/[^/]*\\.[Ss][Ff]")) {
      return true;
    }

    // default: don't skip
    return false;
  }

  /**
   * <p>
   * </p>
   * 
   * @param in
   * @param out
   * @throws IOException
   */
  private static void copy(InputStream in, OutputStream out) throws IOException {
    byte[] buffer = new byte[8192];
    int bytesRead = -1;
    while ((bytesRead = in.read(buffer)) != -1) {
      out.write(buffer, 0, bytesRead);
    }
    out.flush();
  }

  /**
   * <p>
   * </p>
   * 
   * @param closeable
   */
  private static void close(Closeable closeable) {

    if (closeable != null) {

      try {
        closeable.close();
      } catch (IOException e) {
        //
      }
    }
  }

  private static byte[] serialiseManifest(Manifest manifest) throws IOException {
    ByteArrayOutputStream byteout = new ByteArrayOutputStream();
    manifest.write(byteout);
    byteout.close();
    byte[] result = byteout.toByteArray();
    return result;
  }
}
