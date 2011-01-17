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

package com.springsource.bundlor.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springsource.util.parser.manifest.ManifestContents;

/**
 * A simple implementation of the {@link ManifestContents} interface backed by maps.
 * <p />
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Not threadsafe
 * 
 * @author Ben Hale
 */
public class SimpleManifestContents implements ManifestContents {

    private final String version;

    private final Map<String, String> mainAttributes = new HashMap<String, String>();

    private final Map<String, Map<String, String>> otherSectionAttributes = new HashMap<String, Map<String, String>>();

    public SimpleManifestContents() {
        this("1.0");
    }

    public SimpleManifestContents(String version) {
        this.version = version;
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, String> getAttributesForSection(String sectionName) {
        if (!this.otherSectionAttributes.containsKey(sectionName)) {
            this.otherSectionAttributes.put(sectionName, new HashMap<String, String>());
        }
        return this.otherSectionAttributes.get(sectionName);
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, String> getMainAttributes() {
        return this.mainAttributes;
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getSectionNames() {
        return new ArrayList<String>(this.otherSectionAttributes.keySet());
    }

    /**
     * {@inheritDoc}
     */
    public String getVersion() {
        return this.version;
    }

}
