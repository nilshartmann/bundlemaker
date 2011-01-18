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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.springsource.bundlor.support.ManifestModifier;
import com.springsource.bundlor.support.ManifestReader;
import com.springsource.bundlor.support.TemplateHeaderReader;
import com.springsource.bundlor.util.SimpleParserLogger;
import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;
import com.springsource.util.osgi.manifest.parse.HeaderParser;
import com.springsource.util.osgi.manifest.parse.HeaderParserFactory;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 * An analyzer that removes existing headers from the input manifest before it is used to build the bundle manifest.
 * <p />
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Threadsafe
 * 
 * @author Ben Hale
 */
public final class IgnoredExistingHeadersManifestModifier implements ManifestModifier, ManifestReader, TemplateHeaderReader {

    private static final String ATTR_IGNORED_EXISTING_HEADERS = "Ignored-Existing-Headers";

    private final List<String> ignoredExistingHeaders = new ArrayList<String>();

    private final Object ignoredExistingHeadersMonitor = new Object();

    /**
     * {@inheritDoc}
     */
    public void readJarManifest(ManifestContents manifest) {
        // Nothing to do
    }

    /**
     * {@inheritDoc}
     */
    public void readManifestTemplate(ManifestContents manifestTemplate) {
        synchronized (ignoredExistingHeadersMonitor) {
            String value = manifestTemplate.getMainAttributes().get(ATTR_IGNORED_EXISTING_HEADERS);
            List<HeaderDeclaration> headers = parseTemplate(value);
            for (HeaderDeclaration header : headers) {
                ignoredExistingHeaders.add(header.getNames().get(0));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void modify(ManifestContents manifest) {
        for (String ignoredExistingHeader : ignoredExistingHeaders) {
            Pattern pattern = Pattern.compile(ignoredExistingHeader.replace("*", ".*"));

            Map<String, String> attributes = manifest.getMainAttributes();
            Iterator<String> i = attributes.keySet().iterator();
            while(i.hasNext()) {
                String header = i.next();
                if (pattern.matcher(header).matches()) {
                    i.remove();
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getTemplateOnlyHeaderNames() {
        return Arrays.asList(ATTR_IGNORED_EXISTING_HEADERS);
    }

    private List<HeaderDeclaration> parseTemplate(String template) {
        if (StringUtils.hasText(template)) {
            HeaderParser parser = HeaderParserFactory.newHeaderParser(new SimpleParserLogger());
            return parser.parseHeader(template);
        }
        return new ArrayList<HeaderDeclaration>(0);
    }

}
