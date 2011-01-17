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

import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;

import com.springsource.bundlor.support.asm.AsmTypeArtefactAnalyser;
import com.springsource.bundlor.support.contributors.BlueprintArtifactAnalyzer;
import com.springsource.bundlor.support.contributors.BundleClassPathArtifactAnalyzer;
import com.springsource.bundlor.support.contributors.ExcludedImportAndExportPartialManifestModifier;
import com.springsource.bundlor.support.contributors.HibernateMappingArtefactAnalyser;
import com.springsource.bundlor.support.contributors.IgnoredExistingHeadersManifestModifier;
import com.springsource.bundlor.support.contributors.JpaPersistenceArtefactAnalyser;
import com.springsource.bundlor.support.contributors.JspArtifactAnalyzer;
import com.springsource.bundlor.support.contributors.Log4JXmlArtifactAnalyzer;
import com.springsource.bundlor.support.contributors.ManifestTemplateDirectiveMigrator;
import com.springsource.bundlor.support.contributors.OsgiProfileManifestTemplateModifier;
import com.springsource.bundlor.support.contributors.SpringApplicationContextArtefactAnalyser;
import com.springsource.bundlor.support.contributors.StaticResourceArtefactAnalyser;
import com.springsource.bundlor.support.contributors.ToolStampManifestModifier;
import com.springsource.bundlor.support.contributors.WebApplicationArtifactAnalyzer;
import com.springsource.bundlor.support.partialmanifest.StandardPartialManifestResolver;
import com.springsource.bundlor.support.partialmanifest.StandardReadablePartialManifest;
import com.springsource.bundlor.support.properties.PropertiesSource;
import com.springsource.bundlor.support.propertysubstitution.PlaceholderManifestAndTemplateModifier;

public final class DefaultManifestGeneratorContributorsFactory {

    public static ManifestGeneratorContributors create(PropertiesSource... propertiesSources) {
        ManifestGeneratorContributors contributors = new ManifestGeneratorContributors();

        Properties properties = combineProperties(propertiesSources);

        BlueprintArtifactAnalyzer blueprintArtifactAnalyzer = new BlueprintArtifactAnalyzer();
        IgnoredExistingHeadersManifestModifier ignoredExistingHeadersManifestModifier = new IgnoredExistingHeadersManifestModifier();
        ExcludedImportAndExportPartialManifestModifier excludedImportAndExportPartialManifestModifier = new ExcludedImportAndExportPartialManifestModifier();
        PlaceholderManifestAndTemplateModifier placeholderManifestAndTemplateModifier = new PlaceholderManifestAndTemplateModifier(properties);
        ManifestTemplateDirectiveMigrator manifestTemplateDirectiveMigrator = new ManifestTemplateDirectiveMigrator();
        StandardPartialManifestResolver partialManifestResolver = new StandardPartialManifestResolver();
        BundleClassPathArtifactAnalyzer bundleClassPathArtifactAnalyzer = new BundleClassPathArtifactAnalyzer(contributors.getArtifactAnalyzers());

        contributors //
        .addArtifactAnalyzer(new AsmTypeArtefactAnalyser()) //
        .addArtifactAnalyzer(new StaticResourceArtefactAnalyser()) //
        .addArtifactAnalyzer(new HibernateMappingArtefactAnalyser()) //
        .addArtifactAnalyzer(new JpaPersistenceArtefactAnalyser()) //
        .addArtifactAnalyzer(new Log4JXmlArtifactAnalyzer()) //
        .addArtifactAnalyzer(new SpringApplicationContextArtefactAnalyser()) //
        .addArtifactAnalyzer(blueprintArtifactAnalyzer) //
        .addArtifactAnalyzer(new WebApplicationArtifactAnalyzer()) //
        .addArtifactAnalyzer(bundleClassPathArtifactAnalyzer) //
        .addArtifactAnalyzer(new JspArtifactAnalyzer());

        contributors //
        .addManifestReader(excludedImportAndExportPartialManifestModifier) //
        .addManifestReader(ignoredExistingHeadersManifestModifier) //
        .addManifestReader(blueprintArtifactAnalyzer) //
        .addManifestReader(manifestTemplateDirectiveMigrator);

        contributors //
        .addManifestModifier(placeholderManifestAndTemplateModifier) //
        .addManifestModifier(ignoredExistingHeadersManifestModifier) //
        .addManifestModifier(new ToolStampManifestModifier());

        contributors //
        .addManifestTemplateModifier(manifestTemplateDirectiveMigrator) //
        .addManifestTemplateModifier(placeholderManifestAndTemplateModifier) //
        .addManifestTemplateModifier(new OsgiProfileManifestTemplateModifier(properties));

        contributors //
        .addManifestContributor(bundleClassPathArtifactAnalyzer);

        contributors //
        .addPartialManifestModifier(manifestTemplateDirectiveMigrator) //
        .addPartialManifestModifier(excludedImportAndExportPartialManifestModifier);

        contributors //
        .addTemplateHeaderReader(excludedImportAndExportPartialManifestModifier) //
        .addTemplateHeaderReader(ignoredExistingHeadersManifestModifier) //
        .addTemplateHeaderReader(placeholderManifestAndTemplateModifier) //
        .addTemplateHeaderReader(partialManifestResolver);

        contributors //
        .setReadablePartialManifest(new StandardReadablePartialManifest());

        contributors //
        .setPartialManifestResolver(partialManifestResolver);

        return contributors;
    }

    private static Properties combineProperties(PropertiesSource... propertiesSources) {
        PropertiesSource[] sortedPropertiesSources = new PropertiesSource[propertiesSources.length];
        System.arraycopy(propertiesSources, 0, sortedPropertiesSources, 0, propertiesSources.length);
        // Sort by priority so that sources with lower priority are added first into the final
        // Properties instance to allow for overriding by later instances
        Arrays.sort(sortedPropertiesSources, new Comparator<PropertiesSource>() {

            public int compare(PropertiesSource o1, PropertiesSource o2) {
                if (o1.getPriority() == o2.getPriority()) {
                    return 0;
                } else if (o1.getPriority() > o2.getPriority()) {
                    return 1;
                }
                return -1;
            }
        });

        Properties properties = new Properties();
        for (PropertiesSource source : propertiesSources) {
            properties.putAll(source.getProperties());
        }
        return properties;
    }
}
