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

package com.springsource.bundlor.support.contributors;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Constants;
import org.springframework.util.StringUtils;

import com.springsource.bundlor.support.ManifestReader;
import com.springsource.bundlor.support.ManifestTemplateModifier;
import com.springsource.bundlor.support.PartialManifestModifier;
import com.springsource.bundlor.support.partialmanifest.ReadablePartialManifest;
import com.springsource.bundlor.util.SimpleParserLogger;
import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;
import com.springsource.util.osgi.manifest.parse.HeaderParser;
import com.springsource.util.osgi.manifest.parse.HeaderParserFactory;
import com.springsource.util.parser.manifest.ManifestContents;

public class ManifestTemplateDirectiveMigrator implements ManifestReader, ManifestTemplateModifier, PartialManifestModifier {

    private volatile String importPackageString;

    private volatile String exportPackageString;

    private final List<HeaderDeclaration> importPackage = new ArrayList<HeaderDeclaration>();

    private final List<HeaderDeclaration> exportPackage = new ArrayList<HeaderDeclaration>();

    private final Object monitor = new Object();

    public void readJarManifest(ManifestContents manifest) {
    }

    public void readManifestTemplate(ManifestContents manifestTemplate) {
        synchronized (this.monitor) {
            this.importPackageString = manifestTemplate.getMainAttributes().get(Constants.IMPORT_PACKAGE);
            this.importPackage.addAll(parseTemplate(this.importPackageString));

            this.exportPackageString = manifestTemplate.getMainAttributes().get(Constants.EXPORT_PACKAGE);
            this.exportPackage.addAll(parseTemplate(this.exportPackageString));
        }
    }

    public void modify(ManifestContents manifestTemplate) {
        synchronized (this.monitor) {
            if (this.importPackageString != null) {
                String importTemplateString = manifestTemplate.getMainAttributes().get("Import-Template");
                if (importTemplateString == null) {
                    manifestTemplate.getMainAttributes().put("Import-Template", this.importPackageString);
                } else {
                    manifestTemplate.getMainAttributes().put("Import-Template",
                        String.format("%s,%s", importTemplateString, this.importPackageString));
                }
            }

            if (this.exportPackageString != null) {
                String exportTemplateString = manifestTemplate.getMainAttributes().get("Export-Template");
                if (exportTemplateString == null) {
                    manifestTemplate.getMainAttributes().put("Export-Template", this.exportPackageString);
                } else {
                    manifestTemplate.getMainAttributes().put("Export-Template",
                        String.format("%s,%s", exportTemplateString, this.exportPackageString));
                }
            }
        }
    }

    public void modify(ReadablePartialManifest partialManifest) {
        synchronized (this.monitor) {
            for (HeaderDeclaration header : importPackage) {
                partialManifest.recordReferencedPackage(header.getNames().get(0));
            }
            for (HeaderDeclaration header : exportPackage) {
                partialManifest.recordExportPackage(header.getNames().get(0));
            }
        }
    }

    private List<HeaderDeclaration> parseTemplate(String template) {
        if (StringUtils.hasText(template)) {
            HeaderParser parser = HeaderParserFactory.newHeaderParser(new SimpleParserLogger());
            return parser.parseHeader(template);
        }
        return new ArrayList<HeaderDeclaration>(0);
    }
}
