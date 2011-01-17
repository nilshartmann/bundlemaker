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

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import com.springsource.bundlor.support.partialmanifest.ReadablePartialManifest;
import com.springsource.bundlor.util.SimpleManifestContents;
import com.springsource.util.osgi.manifest.BundleManifest;
import com.springsource.util.parser.manifest.ManifestContents;

final class StandardManifestMerger implements ManifestMerger {

    private final PartialManifestResolver partialManifestResolver;

    public StandardManifestMerger(PartialManifestResolver partialManifestResolver) {
        this.partialManifestResolver = partialManifestResolver;
    }

    public ManifestContents merge(ManifestContents existingManifest, ManifestContents manifestTemplate, ManifestContents contributedManifest,
        ReadablePartialManifest partialManifest, List<String> templateOnlyHeaderNames) {

        ManifestContents manifest = new SimpleManifestContents();
        mergeManifests(manifest, existingManifest);
        mergeManifests(manifest, manifestTemplate);
        mergeManifests(manifest, contributedManifest);

        BundleManifest resolved = this.partialManifestResolver.resolve(manifestTemplate, partialManifest);
        mergeManifests(manifest, toManifestContents(resolved));

        removeTemplateOnlyHeaders(manifest, templateOnlyHeaderNames);

        return manifest;
    }

    private void mergeManifests(ManifestContents base, ManifestContents add) {
        base.getMainAttributes().putAll(add.getMainAttributes());
        for (String sectionName : add.getSectionNames()) {
            base.getAttributesForSection(sectionName).putAll(add.getAttributesForSection(sectionName));
        }
    }

    private ManifestContents toManifestContents(BundleManifest bundleManifest) {
        Dictionary<String, String> headers = bundleManifest.toDictionary();
        ManifestContents manifest = new SimpleManifestContents(headers.get("Manifest-Version"));
        Map<String, String> attributes = manifest.getMainAttributes();

        Enumeration<String> headerNames = headers.keys();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            attributes.put(headerName, headers.get(headerName));
        }

        return manifest;
    }

    private void removeTemplateOnlyHeaders(ManifestContents manifest, List<String> names) {
        for (String name : names) {
            manifest.getMainAttributes().remove(name);
        }
    }

}
