/*
 * Copyright 2009 SpringSource
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bundlemaker.core.osgi.exporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import org.bundlemaker.core.util.JarFileUtils;

import com.springsource.bundlor.ManifestWriter;
import com.springsource.bundlor.util.BundleManifestUtils;
import com.springsource.util.osgi.manifest.BundleManifest;
import com.springsource.util.parser.manifest.ManifestContents;

final class JarFileManifestWriter implements ManifestWriter {

  private final File inputFile;

  private final File outputFile;

  public JarFileManifestWriter(File inputFile, File outputFile) {
    this.inputFile = inputFile;
    this.outputFile = outputFile;
  }

  public void write(ManifestContents manifest) {
    JarOutputStream out = null;
    InputStream in = null;
    try {
      JarFile inputJar = new JarFile(inputFile);

      out = new JarOutputStream(new FileOutputStream(outputFile));

      writeManifest(out, BundleManifestUtils.createBundleManifest(manifest));

      Enumeration<JarEntry> entries = inputJar.entries();
      while (entries.hasMoreElements()) {
        JarEntry entry = entries.nextElement();
        if (!JarFileUtils.skipResource(entry.getName())) {
          if (entry.isDirectory()) {
            out.putNextEntry(entry);
            out.write(new byte[0]);
            out.flush();
            out.closeEntry();
          } else {
            out.putNextEntry(new JarEntry(entry.getName()));
            in = inputJar.getInputStream(entry);
            copy(in, out);
            out.flush();
            out.closeEntry();
          }
        }
      }

      System.out.printf("Transformed bundle written to '%s'%n", outputFile.getAbsolutePath());
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        if (out != null) {
          out.flush();
          out.closeEntry();
          out.flush();
          out.close();
        }
      } catch (IOException e) {
        // Nothing to do
      }
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException e) {
        // Nothing to do
      }
    }
  }

  public void close() {
    // Nothing to close
  }

  private void writeManifest(JarOutputStream out, BundleManifest manifest) throws IOException {
    out.putNextEntry(new JarEntry("META-INF/"));
    out.write(new byte[0]);
    out.flush();
    out.closeEntry();

    out.putNextEntry(new JarEntry(JarFile.MANIFEST_NAME));
    Writer writer = new NonClosingOuptutStreamWriter(out);
    manifest.write(writer);
    writer.flush();
    out.flush();
    out.closeEntry();
  }

  private void copy(InputStream in, OutputStream out) throws IOException {
    byte[] buffer = new byte[8192];
    int bytesRead = -1;
    while ((bytesRead = in.read(buffer)) != -1) {
      out.write(buffer, 0, bytesRead);
    }
    out.flush();
  }

  private static class NonClosingOuptutStreamWriter extends OutputStreamWriter {

    public NonClosingOuptutStreamWriter(OutputStream out) {
      super(out);
    }

    @Override
    public void close() throws IOException {
      // Don't close.
    }

  }
}
