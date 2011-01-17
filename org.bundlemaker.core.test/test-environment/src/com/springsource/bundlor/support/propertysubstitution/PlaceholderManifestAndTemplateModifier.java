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

import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import com.springsource.bundlor.support.ManifestModifier;
import com.springsource.bundlor.support.ManifestTemplateModifier;
import com.springsource.bundlor.support.TemplateHeaderReader;
import com.springsource.util.common.PropertyPlaceholderResolver;
import com.springsource.util.common.PropertyPlaceholderResolver.PlaceholderValueTransformer;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 * An implementation of {@link ManifestTemplateModifier} that substitutes property values in for place holder values
 * <p />
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Threadsafe
 * 
 * @author Ben Hale
 */
public final class PlaceholderManifestAndTemplateModifier implements ManifestModifier, ManifestTemplateModifier, TemplateHeaderReader {

    private final Properties properties;

    public PlaceholderManifestAndTemplateModifier(Properties properties) {
        this.properties = properties;
    }

    public void modify(ManifestContents manifest) {
        PlaceholderValueTransformer transformer = new VersionExpansionTransformer(manifest);
        PropertyPlaceholderResolver placeholderResolver = new PropertyPlaceholderResolver();

        for (Entry<String, String> entry : manifest.getMainAttributes().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            manifest.getMainAttributes().put(key, placeholderResolver.resolve(value, this.properties, transformer));
        }
        
        for(String name : manifest.getSectionNames()) {
            for(Entry<String, String> entry : manifest.getAttributesForSection(name).entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                manifest.getAttributesForSection(name).put(key, placeholderResolver.resolve(value, this.properties, transformer));
            }
        }
    }

    public List<String> getTemplateOnlyHeaderNames() {
        return new VersionExpansionTransformer().getTemplateOnlyHeaderNames();
    }

}
