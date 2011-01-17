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

package com.springsource.bundlor.support.partialmanifest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.springsource.bundlor.support.PartialManifestResolver;
import com.springsource.bundlor.support.TemplateHeaderReader;
import com.springsource.bundlor.util.MatchUtils;
import com.springsource.bundlor.util.SimpleParserLogger;
import com.springsource.util.osgi.manifest.BundleManifest;
import com.springsource.util.osgi.manifest.BundleManifestFactory;
import com.springsource.util.osgi.manifest.ExportedPackage;
import com.springsource.util.osgi.manifest.ImportedPackage;
import com.springsource.util.osgi.manifest.Resolution;
import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;
import com.springsource.util.osgi.manifest.parse.HeaderParserFactory;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 * Resolves a {@link StandardReadablePartialManifest} against the {@link ManifestContents} template, creating a
 * {@link BundleManifest} with valid OSGi <code>Import-Package</code> and <code>Export-Package</code> headers.
 * 
 * @author Rob Harrop
 * @autor Ben Hale
 */
public final class StandardPartialManifestResolver implements TemplateHeaderReader, PartialManifestResolver {

    private static final String ATTR_BUNDLE_VERSION = "Bundle-Version";

    private static final String ATTR_EXPORT_TEMPLATE = "Export-Template";

    private static final String ATTR_IMPORT_TEMPLATE = "Import-Template";

    public List<String> getTemplateOnlyHeaderNames() {
        return Arrays.asList(ATTR_EXPORT_TEMPLATE, ATTR_IMPORT_TEMPLATE);
    }

    /**
     * {@inheritDoc}
     */
    public BundleManifest resolve(ManifestContents templateManifest, ReadablePartialManifest partial) {
        BundleManifest result = BundleManifestFactory.createBundleManifest(new SimpleParserLogger());

        processImports(result, templateManifest, partial);
        processExports(result, templateManifest, partial);

        return result;
    }

    /**
     * Resolves the exports.
     */
    private void processExports(BundleManifest result, ManifestContents templateManifest, ReadablePartialManifest partial) {
        String bundleVersion = templateManifest.getMainAttributes().get(ATTR_BUNDLE_VERSION);
        String exportTemplate = templateManifest.getMainAttributes().get(ATTR_EXPORT_TEMPLATE);

        List<HeaderDeclaration> exportTemplateDeclarations = parseTemplate(exportTemplate);

        List<ExportedPackage> packageExports = new ArrayList<ExportedPackage>();
        for (String exportedPackage : partial.getExportedPackages()) {
            HeaderDeclaration declaration = findMostSpecificDeclaration(exportTemplateDeclarations, exportedPackage);

            ExportedPackage export = result.getExportPackage().addExportedPackage(exportedPackage);

            if (declaration != null) {
                export.getAttributes().putAll(declaration.getAttributes());
                Map<String, String> directives = declaration.getDirectives();
                addCommaSeparated(export.getExclude(), directives.get("excluded"));
                addCommaSeparated(export.getInclude(), directives.get("include"));
                addCommaSeparated(export.getMandatory(), directives.get("mandatory"));
                addCommaSeparated(export.getUses(), directives.get("uses"));

            }

            Set<String> uses = partial.getUses(exportedPackage);
            export.getUses().addAll(uses);

            String version = resolveVersion(declaration, bundleVersion);
            if (StringUtils.hasText(version)) {
                export.getAttributes().put("version", version);
            }
            packageExports.add(export);
        }
    }

    /**
     * Resolves the imports.
     */
    private void processImports(BundleManifest result, ManifestContents template, ReadablePartialManifest partial) {

        String importTemplate = template.getMainAttributes().get(ATTR_IMPORT_TEMPLATE);

        List<HeaderDeclaration> importTemplateDeclarations = parseTemplate(importTemplate);

        List<ImportedPackage> packageImports = new ArrayList<ImportedPackage>();
        for (String importedPackage : partial.getImportedPackages()) {
            HeaderDeclaration match = findMostSpecificDeclaration(importTemplateDeclarations, importedPackage);

            ImportedPackage packageImport = result.getImportPackage().addImportedPackage(importedPackage);
            if (match != null) {
                packageImport.getAttributes().putAll(match.getAttributes());
                packageImport.setResolution(parseResolution(match));
            }
            packageImports.add(packageImport);
        }
    }

    /**
     * Parses <code>value</code> as a comma-separated value and adds all elements to the supplied {@link Collection}.
     */
    private void addCommaSeparated(Collection<String> target, String value) {
        if (value != null) {
            if (value.startsWith("\"")) {
                value = value.substring(1);
            }
            if (value.endsWith("\"")) {
                value = value.substring(0, value.length() - 1);
            }
        }
        String[] array = StringUtils.commaDelimitedListToStringArray(value);
        Collections.addAll(target, array);
    }

    private HeaderDeclaration findMostSpecificDeclaration(List<HeaderDeclaration> declarations, String packageName) {
        HeaderDeclaration match = null;
        int matchSpecificity = -1;

        for (HeaderDeclaration headerDeclaration : declarations) {
            for (String stem : headerDeclaration.getNames()) {
                int m = MatchUtils.rankedMatch(packageName, stem);
                if (m > matchSpecificity) {
                    match = headerDeclaration;
                    matchSpecificity = m;
                }
            }
        }
        return match;
    }

    /**
     * Resolves the <code>version</code> for supplied {@link HeaderDeclaration}, returning <code>defaultVersion</code>
     * if none is specified.
     */
    private String resolveVersion(HeaderDeclaration declaration, String defaultVersion) {
        String version = declaration != null ? declaration.getAttributes().get("version") : null;
        if (!StringUtils.hasText(version)) {
            version = defaultVersion;
        }
        return version;
    }

    /**
     * Parses the <code>resolution</code> directive for the supplied header.
     */
    private Resolution parseResolution(HeaderDeclaration declaration) {
        if ("optional".equals(declaration.getDirectives().get("resolution"))) {
            return Resolution.OPTIONAL;
        } else {
            return Resolution.MANDATORY;
        }
    }

    private List<HeaderDeclaration> parseTemplate(String template) {
        if (StringUtils.hasText(template)) {
            return HeaderParserFactory.newHeaderParser(new SimpleParserLogger()).parseHeader(template);
        } else {
            return new ArrayList<HeaderDeclaration>(0);
        }
    }
}
