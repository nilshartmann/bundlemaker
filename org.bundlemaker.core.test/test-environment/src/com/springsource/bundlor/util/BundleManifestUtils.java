/*
 * Copyright 2008-2009 SpringSource
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

package com.springsource.bundlor.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.springsource.util.osgi.manifest.BundleManifest;
import com.springsource.util.osgi.manifest.BundleManifestFactory;
import com.springsource.util.parser.manifest.ManifestContents;
import com.springsource.util.parser.manifest.ManifestParser;
import com.springsource.util.parser.manifest.ManifestProblem;
import com.springsource.util.parser.manifest.RecoveringManifestParser;

/**
 * Utilities for working with {@link BundleManifest BundleManifests}.
 * <p/>
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Threadsafe.
 * 
 * @author Rob Harrop
 */
public final class BundleManifestUtils {

    /**
     * Creates a {@link BundleManifest} from the supplied {@link ManifestContents}.
     * 
     * @param mf the <code>Manifest</code>.
     * @return the created <code>BundleManifest</code>.
     */
    public static BundleManifest createBundleManifest(ManifestContents manifest) {
        return BundleManifestFactory.createBundleManifest(manifest, new SimpleParserLogger());
    }

    public static ManifestContents getManifest(File manifestFile) {
        if (manifestFile == null || !manifestFile.exists()) {
            return new SimpleManifestContents();
        }

        try {
            return getManifest(new FileReader(manifestFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ManifestContents getManifest(Reader reader) {
        ManifestParser parser = new RecoveringManifestParser();
        try {
            ManifestContents manifest = parser.parse(reader);
            if (parser.foundProblems()) {
                for (ManifestProblem problem : parser.getProblems()) {
                    System.err.println(problem.toStringWithContext());
                    System.err.println();
                }
                throw new RuntimeException("There was a problem with the manifest");
            }
            return manifest;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Nothing to do
                }
            }
        }
    }

}
