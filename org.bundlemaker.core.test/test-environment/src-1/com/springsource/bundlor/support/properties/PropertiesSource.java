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
 * Describes a source for manifest property values. Those values will be replaced in the template manifest prior to
 * generating the final manifest.
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Implementations need to be threadsafe.
 * 
 * @author Christian Dupuis
 */
public interface PropertiesSource {

    /**
     * Returns the priority of this {@link PropertiesSource} instance. The priority describes the order in which all
     * {@link PropertiesSource}s will be merged into the final {@link Properties} instance.
     * <p>
     * A lower priority means that the {@link PropertiesSource}'s {@link Properties} instance will be added to the
     * merged {@link Properties} instance prior to {@link PropertiesSource}s with higher priority. For example the
     * properties returned by {@link System#getProperties()} are likely to have the lowest priority to be able to
     * override those by user specified values.
     * 
     * @return the priority
     */
    int getPriority();

    /**
     * Returns the full-constructed {@link Properties} instance.
     * 
     * @return the {@link Properties} instance created by this {@link PropertiesSource}
     */
    Properties getProperties();

}
