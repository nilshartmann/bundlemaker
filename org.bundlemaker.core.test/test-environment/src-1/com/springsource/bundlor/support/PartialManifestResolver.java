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

package com.springsource.bundlor.support;

import com.springsource.bundlor.support.partialmanifest.ReadablePartialManifest;
import com.springsource.bundlor.support.partialmanifest.StandardReadablePartialManifest;
import com.springsource.util.osgi.manifest.BundleManifest;
import com.springsource.util.parser.manifest.ManifestContents;

public interface PartialManifestResolver {

    /**
     * Resolves the supplied {@link StandardReadablePartialManifest} against the supplied manifest template.
     * 
     * @param templateManifest the template.
     * @param partial the partial manifest.
     * @return the resolved {@link BundleManifest}.
     */
    BundleManifest resolve(ManifestContents templateManifest, ReadablePartialManifest partial);

}