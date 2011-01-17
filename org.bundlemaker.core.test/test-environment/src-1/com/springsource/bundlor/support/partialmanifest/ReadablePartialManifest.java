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

package com.springsource.bundlor.support.partialmanifest;

import java.util.Set;

public interface ReadablePartialManifest extends PartialManifest {

    /**
     * Gets the exported packages.
     * 
     * @return the exported packages.
     */
    Set<String> getExportedPackages();

    /**
     * Gets the imported packages.
     * 
     * @return the imported packages.
     */
    Set<String> getImportedPackages();

    /**
     * Gets the uses for the supplied exporting package.
     * 
     * @param exportingPackage the exporting package.
     * @return the uses.
     */
    Set<String> getUses(String exportingPackage);

    /**
     * Indicate whether a package is recordable
     * 
     * @param packageName The name of the package to record
     */
    boolean isRecordablePackage(String packageName);

    /**
     * Gets the set of unsatisfied types for a given package
     * @param packageName The name of the package
     * @return The set of unsatisfied types
     */
    Set<String> getUnsatisfiedTypes(String packageName);

}