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

import com.springsource.bundlor.ManifestWriter;
import com.springsource.util.common.StringUtils;

public final class StandardManifestWriterFactory implements ManifestWriterFactory {

    /**
     * {@inheritDoc}
     */
    public ManifestWriter create(String inputPath, String outputPath) {
        if (!StringUtils.hasText(outputPath)) {
            return new SystemOutManifestWriter();
        }

        File outputFile = new File(outputPath);
        if (outputFile.isDirectory()) {
            return new FileSystemManifestWriter(outputFile);
        }
        return new JarFileManifestWriter(new File(inputPath), new File(outputPath));
    }
}
