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

import java.io.IOException;
import java.io.StringWriter;

import com.springsource.bundlor.ManifestWriter;
import com.springsource.bundlor.util.BundleManifestUtils;
import com.springsource.util.parser.manifest.ManifestContents;

class SystemOutManifestWriter implements ManifestWriter {

    public void write(ManifestContents manifest) {
        try {
            StringWriter stringWriter = new StringWriter();
            BundleManifestUtils.createBundleManifest(manifest).write(stringWriter);
            System.out.println(stringWriter.toString());
        } catch (IOException e) {
            // Do nothing
        }
    }

    public void close() {
    }

}
