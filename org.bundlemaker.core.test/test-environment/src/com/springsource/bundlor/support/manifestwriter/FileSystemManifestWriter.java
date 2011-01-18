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

package com.springsource.bundlor.support.manifestwriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.springsource.bundlor.ManifestWriter;
import com.springsource.bundlor.util.BundleManifestUtils;
import com.springsource.util.parser.manifest.ManifestContents;

final class FileSystemManifestWriter implements ManifestWriter {

    private static final String MANIFEST_PATH = "META-INF/MANIFEST.MF";

    private final File manifestFile;

    public FileSystemManifestWriter(File root) {
        manifestFile = new File(root, MANIFEST_PATH);
    }

    public void write(ManifestContents manifest) {
        Writer out = null;

        try {
            if (!manifestFile.getParentFile().exists() && !manifestFile.getParentFile().mkdirs()) {
                throw new RuntimeException(String.format("Could not create parent directories of '%s'", manifestFile.getAbsolutePath()));
            }

            out = new FileWriter(manifestFile);
            BundleManifestUtils.createBundleManifest(manifest).write(out);
            System.out.printf("Manifest written to '%s'%n", manifestFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // Nothing to do
                }
            }
        }
    }

    public void close() {
        // Nothing to close
    }

}
