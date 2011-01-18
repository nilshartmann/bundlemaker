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

package com.springsource.bundlor.support.contributors;

import com.springsource.bundlor.support.ManifestReader;
import com.springsource.bundlor.util.SimpleParserLogger;
import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;
import com.springsource.util.osgi.manifest.parse.HeaderParser;
import com.springsource.util.osgi.manifest.parse.HeaderParserFactory;
import com.springsource.util.parser.manifest.ManifestContents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

/**
 * An analyzer for an OSGi Blueprint located in a JAR file. Analyzes the list of package names that are found in the
 * context.
 * <p />
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Not threadsafe.
 * 
 * @author Ben Hale
 */
public final class BlueprintArtifactAnalyzer extends AbstractXmlConfigurationArtifactAnalyzer implements ManifestReader {

    private static final String CLASS_ATTRIBUTES = //
    "//bp:bean/bp:argument/@type | " + //
        "//bp:bean/@class | " + //
        "//bp:service/@interface | " + //
        "//bp:reference/@interface | " + //
        "//bp:reference-list/@interface | " + //
        "//bp:map/@key-type | " + //
        "//bp:map/@value-type | " + //
        "//bp:list/@value-type | " + //
        "//bp:set/@value-type | " + //
        "//bp:array/@value-type";

    private static final String CLASS_VALUES = //
    "//bp:interfaces/bp:value";

    private static final Map<String, String> NAMESPACE_MAPPING;

    static {
        NAMESPACE_MAPPING = new HashMap<String, String>();
        NAMESPACE_MAPPING.put("bp", "http://www.osgi.org/xmlns/blueprint/v1.0.0");
    }

    private static final String DEFAULT_CONTEXT_LOCATION = "OSGI-INF/blueprint/*.xml";

    private static final String CONTEXT_PATH_HEADER = "Bundle-Blueprint";

    private final List<String> contextPaths = new ArrayList<String>();

    public final void readJarManifest(ManifestContents manifest) {
        readContextPaths(manifest);
    }

    public final void readManifestTemplate(ManifestContents manifestTemplate) {
        readContextPaths(manifestTemplate);
    }

    private final void readContextPaths(ManifestContents manifest) {
        String value = manifest.getMainAttributes().get(CONTEXT_PATH_HEADER);
        List<HeaderDeclaration> headers = parseTemplate(value);
        if (headers.size() == 0) {
            contextPaths.add(DEFAULT_CONTEXT_LOCATION);
        } else {
            for (HeaderDeclaration header : headers) {
                contextPaths.add(header.getNames().get(0));
            }
        }
    }

    private final List<HeaderDeclaration> parseTemplate(String template) {
        if (StringUtils.hasText(template)) {
            HeaderParser parser = HeaderParserFactory.newHeaderParser(new SimpleParserLogger());
            return parser.parseHeader(template);
        }
        return new ArrayList<HeaderDeclaration>(0);
    }

    public final boolean canAnalyse(String artefactName) {
        AntPathMatcher matcher = new AntPathMatcher();
        for (String contextPath : this.contextPaths) {
            if (matcher.match(contextPath, artefactName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected String getClassAttributesXPathExpression() {
        return CLASS_ATTRIBUTES;
    }

    @Override
    protected String getClassValueXPathExpression() {
        return CLASS_VALUES;
    }

    @Override
    protected String getPackageXPathExpression() {
        return null;
    }

    @Override
    protected Map<String, String> getNamespaceMapping() {
        return NAMESPACE_MAPPING;
    }

}
