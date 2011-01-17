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

import com.springsource.bundlor.support.partialmanifest.ReadablePartialManifest;

/**
 * Implementations of this interface have the opportunity to modify the partial manifest before it is used to create the
 * bundle manifest
 * <p />
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Implementations of this interface must be threadsafe
 * 
 * @author Ben Hale
 */
public interface PartialManifestModifier {

    /**
     * Modify the partial manifest before it is used to create the bundle manifest
     * 
     * @param partialManifest the partial manifest to modify
     */
    void modify(ReadablePartialManifest partialManifest);
}
