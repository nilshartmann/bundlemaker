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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.springsource.bundlor.ClassPath;
import com.springsource.bundlor.ClassPathEntry;
import com.springsource.bundlor.EntryScannerListener;
import com.springsource.bundlor.ManifestGenerator;
import com.springsource.bundlor.support.partialmanifest.PartialManifest;
import com.springsource.bundlor.support.partialmanifest.ReadablePartialManifest;
import com.springsource.bundlor.util.BundleManifestUtils;
import com.springsource.bundlor.util.SimpleManifestContents;
import com.springsource.util.parser.manifest.ManifestContents;

public class StandardManifestGenerator implements ManifestGenerator {

    private final ManifestGeneratorContributors contributors;

    private final ManifestMerger manifestMerger;

    public StandardManifestGenerator(ManifestGeneratorContributors contributors) {
        this(contributors, new StandardManifestMerger(contributors.getPartialManifestResolver()));
    }

    StandardManifestGenerator(ManifestGeneratorContributors contributors, ManifestMerger manifestMerger) {
        this.contributors = contributors;
        this.manifestMerger = manifestMerger;
    }

    public ManifestContents generate(ManifestContents manifestTemplate, ClassPath... classPaths) {
        ReadablePartialManifest partialManifest = this.contributors.getReadablePartialManifest();
        ManifestContents existingManifest = getExistingManifest(classPaths);

        for (ManifestReader manifestReader : this.contributors.getManifestReaders()) {
            manifestReader.readJarManifest(existingManifest);
            manifestReader.readManifestTemplate(manifestTemplate);
        }

        for (ManifestModifier manifestModifier : this.contributors.getManifestModifiers()) {
            manifestModifier.modify(existingManifest);
        }

        for (ManifestTemplateModifier manifestTemplateModifier : this.contributors.getManifestTemplateModifiers()) {
            manifestTemplateModifier.modify(manifestTemplate);
        }

        analyzeEntries(classPaths, partialManifest);

        for (PartialManifestModifier partialManifestModifier : this.contributors.getPartialManifestModifiers()) {
            partialManifestModifier.modify(partialManifest);
        }

        ManifestContents contributedManifest = new SimpleManifestContents();
        for (ManifestContributor manifestContributor : this.contributors.getManifestContributors()) {
            manifestContributor.contribute(contributedManifest);
        }

        List<String> templateOnlyHeaderNames = new ArrayList<String>();
        for (TemplateHeaderReader templateHeaderReader : this.contributors.getTemplateHeaderReaders()) {
            templateOnlyHeaderNames.addAll(templateHeaderReader.getTemplateOnlyHeaderNames());
        }

        return this.manifestMerger.merge(existingManifest, manifestTemplate, contributedManifest, partialManifest, templateOnlyHeaderNames);
    }

    private ManifestContents getExistingManifest(ClassPath... classPaths) {
        for (ClassPath classPath : classPaths) {
            ClassPathEntry classPathEntry = classPath.getEntry("META-INF/MANIFEST.MF");
            if (classPathEntry != null) {
                return BundleManifestUtils.getManifest(classPathEntry.getReader());
            }
        }
        return new SimpleManifestContents();
    }

    private void analyzeEntries(ClassPath[] classPaths, PartialManifest partialManifest) {
        for (ClassPath classPath : classPaths) {
            try {
                for (ClassPathEntry classPathEntry : classPath) {
                    if (!classPathEntry.isDirectory()) {
                        beginEntry(classPathEntry);
                        analyzeEntry(classPathEntry, partialManifest);
                        endEntry();
                    }
                }
            } finally {
                classPath.close();
            }
        }
    }

    private void analyzeEntry(ClassPathEntry classPathEntry, PartialManifest partialManifest) {
        for (ArtifactAnalyzer artifactAnalyzer : this.contributors.getArtifactAnalyzers()) {
            if (artifactAnalyzer.canAnalyse(classPathEntry.getName())) {
                InputStream inputStream = classPathEntry.getInputStream();
                try {
                    artifactAnalyzer.analyse(inputStream, classPathEntry.getName(), partialManifest);
                } catch (Exception e) {
                    // Swallow exception to allow other analyzers to proceed
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            // Nothing to do
                        }
                    }
                }
            }
        }
    }

    private void beginEntry(ClassPathEntry classPathEntry) {
        for (EntryScannerListener entryScannerListener : this.contributors.getEntryScannerListeners()) {
            entryScannerListener.onBeginEntry(classPathEntry.getName());
        }
    }

    private void endEntry() {
        for (EntryScannerListener entryScannerListener : this.contributors.getEntryScannerListeners()) {
            entryScannerListener.onEndEntry();
        }
    }

}
