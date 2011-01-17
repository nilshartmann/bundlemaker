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

package com.springsource.bundlor.support.propertysubstitution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Version;
import org.springframework.util.StringUtils;

import com.springsource.bundlor.support.TemplateHeaderReader;
import com.springsource.bundlor.util.SimpleParserLogger;
import com.springsource.util.common.PropertyPlaceholderResolver.PlaceholderValueTransformer;
import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;
import com.springsource.util.osgi.manifest.parse.HeaderParser;
import com.springsource.util.osgi.manifest.parse.HeaderParserFactory;
import com.springsource.util.parser.manifest.ManifestContents;

final class VersionExpansionTransformer implements PlaceholderValueTransformer, TemplateHeaderReader {

    private static final String VERSION_PATTERNS = "Version-Patterns";

    private static final String ATT_PATTERN = "pattern";

    private final Map<String, VersionExpander> expanders = new HashMap<String, VersionExpander>();

    private final Object expandersLock = new Object();

    public VersionExpansionTransformer() {
    }

    public VersionExpansionTransformer(ManifestContents manifestTemplate) {
        String value = manifestTemplate.getMainAttributes().get(VERSION_PATTERNS);
        List<HeaderDeclaration> headers = parseTemplate(value);
        for (HeaderDeclaration header : headers) {
            String name = header.getNames().get(0);
            String pattern = header.getAttributes().get(ATT_PATTERN);
            expanders.put(name, VersionExpansionParser.parseVersionExpander(pattern));
        }
    }

    /**
     * {@inheritDoc}
     */
    public String transform(String propertyName, String value, String pattern) {
        Version version = new Version(value);
        VersionExpander expander = getVersionExpander(pattern);
        return expander.expand(version.getMajor(), version.getMinor(), version.getMicro(), version.getQualifier());
    }

    VersionExpander getVersionExpander(String pattern) {
        synchronized (expandersLock) {
            if (!expanders.containsKey(pattern)) {
                expanders.put(pattern, VersionExpansionParser.parseVersionExpander(pattern));
            }
            return expanders.get(pattern);
        }
    }

    public List<String> getTemplateOnlyHeaderNames() {
        return Arrays.asList(VERSION_PATTERNS);
    }

    private List<HeaderDeclaration> parseTemplate(String template) {
        if (StringUtils.hasText(template)) {
            HeaderParser parser = HeaderParserFactory.newHeaderParser(new SimpleParserLogger());
            return parser.parseHeader(template);
        }
        return new ArrayList<HeaderDeclaration>(0);
    }

}
