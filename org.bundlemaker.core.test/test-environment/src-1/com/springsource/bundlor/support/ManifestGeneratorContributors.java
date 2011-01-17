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

import java.util.ArrayList;
import java.util.List;

import com.springsource.bundlor.EntryScannerListener;
import com.springsource.bundlor.support.partialmanifest.ReadablePartialManifest;

public final class ManifestGeneratorContributors {

    private final List<ArtifactAnalyzer> artifactAnalyzers = new ArrayList<ArtifactAnalyzer>();

    private final List<ManifestReader> manifestReaders = new ArrayList<ManifestReader>();

    private final List<ManifestModifier> manifestModifiers = new ArrayList<ManifestModifier>();

    private final List<ManifestTemplateModifier> manifestTemplateModifiers = new ArrayList<ManifestTemplateModifier>();

    private final List<PartialManifestModifier> partialManifestModifiers = new ArrayList<PartialManifestModifier>();

    private final List<ManifestContributor> manifestContributors = new ArrayList<ManifestContributor>();

    private final List<TemplateHeaderReader> templateHeaderReaders = new ArrayList<TemplateHeaderReader>();

    private final List<EntryScannerListener> entryScannerListeners = new ArrayList<EntryScannerListener>();

    private volatile ReadablePartialManifest readablePartialManifest = null;

    private volatile PartialManifestResolver partialManifestResolver = null;

    List<ArtifactAnalyzer> getArtifactAnalyzers() {
        return this.artifactAnalyzers;
    }

    List<ManifestReader> getManifestReaders() {
        return this.manifestReaders;
    }

    List<ManifestModifier> getManifestModifiers() {
        return this.manifestModifiers;
    }

    List<ManifestTemplateModifier> getManifestTemplateModifiers() {
        return this.manifestTemplateModifiers;
    }

    List<PartialManifestModifier> getPartialManifestModifiers() {
        return this.partialManifestModifiers;
    }

    public List<ManifestContributor> getManifestContributors() {
        return manifestContributors;
    }

    List<TemplateHeaderReader> getTemplateHeaderReaders() {
        return this.templateHeaderReaders;
    }

    List<EntryScannerListener> getEntryScannerListeners() {
        return this.entryScannerListeners;
    }

    ReadablePartialManifest getReadablePartialManifest() {
        return readablePartialManifest;
    }

    PartialManifestResolver getPartialManifestResolver() {
        return partialManifestResolver;
    }

    public ManifestGeneratorContributors addArtifactAnalyzer(ArtifactAnalyzer artifactAnalyzer) {
        this.artifactAnalyzers.add(artifactAnalyzer);
        return this;
    }

    public ManifestGeneratorContributors addManifestReader(ManifestReader manifestReader) {
        this.manifestReaders.add(manifestReader);
        return this;
    }

    public ManifestGeneratorContributors addManifestModifier(ManifestModifier manifestModifier) {
        this.manifestModifiers.add(manifestModifier);
        return this;
    }

    public ManifestGeneratorContributors addManifestTemplateModifier(ManifestTemplateModifier manifestTemplateModifier) {
        this.manifestTemplateModifiers.add(manifestTemplateModifier);
        return this;
    }

    public ManifestGeneratorContributors addPartialManifestModifier(PartialManifestModifier partialManifestModifier) {
        this.partialManifestModifiers.add(partialManifestModifier);
        return this;
    }

    public ManifestGeneratorContributors addManifestContributor(ManifestContributor manifestContributor) {
        this.manifestContributors.add(manifestContributor);
        return this;
    }

    public ManifestGeneratorContributors addTemplateHeaderReader(TemplateHeaderReader templateHeaderReader) {
        this.templateHeaderReaders.add(templateHeaderReader);
        return this;
    }

    public ManifestGeneratorContributors addEntryScannerListener(EntryScannerListener entryScannerListener) {
        this.entryScannerListeners.add(entryScannerListener);
        return this;
    }

    public ManifestGeneratorContributors setReadablePartialManifest(ReadablePartialManifest readablePartialManifest) {
        this.readablePartialManifest = readablePartialManifest;
        return this;
    }

    public ManifestGeneratorContributors setPartialManifestResolver(PartialManifestResolver partialManifestResolver) {
        this.partialManifestResolver = partialManifestResolver;
        return this;
    }
}
