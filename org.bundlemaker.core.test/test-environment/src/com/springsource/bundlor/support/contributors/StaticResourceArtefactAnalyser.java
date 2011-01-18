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

import java.io.InputStream;

import com.springsource.bundlor.support.ArtifactAnalyzer;
import com.springsource.bundlor.support.partialmanifest.PartialManifest;

/**
 * An analyzer that detects the packages of static resources
 * <p />
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Threadsafe
 * 
 * @author Ben Hale
 */
public final class StaticResourceArtefactAnalyser implements ArtifactAnalyzer {

    /**
     * {@inheritDoc}
     */
    public void analyse(InputStream artefact, String artefactName, PartialManifest partialManifest) throws Exception {
        int index = artefactName.lastIndexOf('/');
        if (index > -1) {
            String packageName = artefactName.substring(0, index).replace('/', '.');
            if (packageName.matches("([a-zA-Z$_][a-zA-Z0-9$_]+)+(\\.[a-zA-Z$_][a-zA-Z0-9$_]+)*")) {
                partialManifest.recordExportPackage(packageName.trim());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean canAnalyse(String artifactName) {
        return !artifactName.startsWith("META-INF") && !artifactName.startsWith("WEB-INF") && !artifactName.endsWith(".class")
            && !artifactName.startsWith(".");
    }
}
