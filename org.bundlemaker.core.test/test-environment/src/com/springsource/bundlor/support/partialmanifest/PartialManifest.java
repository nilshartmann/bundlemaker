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

import com.springsource.bundlor.support.ArtifactAnalyzer;

/**
 * Describes a partially-constructed, dynamically-generated manifest. {@link ArtifactAnalyzer ArtefactAnalysers}
 * contribute manifest elements (imports and exports) to a <code>PartialManifest</code> during analysis.
 * <p/>
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Implementations needn't be threadsafe.
 * 
 * @author Rob Harrop
 * @author Ben Hale
 */
public interface PartialManifest {

    /**
     * Records a <code>uses</code> directive member for a given package export.
     * 
     * @param usingPackage the package that is using the package.
     * @param usedPackage the package being used.
     */
    void recordUsesPackage(String usingPackage, String usedPackage);

    /**
     * Records that the supplied type is referenced by the code being analysed.
     * 
     * @param fullyQualifiedTypeName the fully-qualified name of the referenced type
     */
    void recordReferencedType(String fullyQualifiedTypeName);

    /**
     * Records the existence of a type
     * 
     * @param fullyQualifiedTypeName The fully qualified name of the type
     */
    void recordType(String fullyQualifiedTypeName);

    /**
     * Records that the supplied package is referenced by the artefact being analysed.
     * 
     * @param fullyQualifiedPackageName The fully-qualified name of the package
     */
    void recordReferencedPackage(String fullyQualifiedPackageName);

    /**
     * Records that the supplied package should be exported.
     * 
     * @param fullyQualifiedPackageName The fully-qualified name of the package
     */
    void recordExportPackage(String fullyQualifiedPackageName);

}
