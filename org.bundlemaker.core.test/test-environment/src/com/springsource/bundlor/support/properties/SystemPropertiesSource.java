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

package com.springsource.bundlor.support.properties;

import java.util.Properties;

/**
 * Simple {@link PropertiesSource} implementation exposing the {@link Properties}s instance of
 * {@link System#getProperties()}.
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Not threadsafe.
 * 
 * @author Christian Dupuis
 */
public final class SystemPropertiesSource implements PropertiesSource {

    /**
     * Internal copy of {@link System#getProperties()} as of creation time of this instance
     */
    private final Properties properties;

    /**
     * Create a new {@link SystemPropertiesSource}
     */
    public SystemPropertiesSource() {
        this.properties = new Properties();
        this.properties.putAll(System.getProperties());
    }

    /**
     * {@inheritDoc}
     */
    public int getPriority() {
        return Integer.MIN_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    public Properties getProperties() {
        return properties;
    }

}
