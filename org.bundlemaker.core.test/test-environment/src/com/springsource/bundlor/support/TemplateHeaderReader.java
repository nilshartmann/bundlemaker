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

package com.springsource.bundlor.support;

import java.util.List;

/**
 * An interface that allows Bundlor to determine which headers exist to support operation and should not exist at
 * runtime. These headers will be removed from the final manifest.
 * <p />
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Implementations should be threadsafe.
 * 
 * @author Ben Hale
 */
public interface TemplateHeaderReader {

    /**
     * Gets the list of header names required by this reader.
     * @return The list of header names.
     */
    List<String> getTemplateOnlyHeaderNames();
}
