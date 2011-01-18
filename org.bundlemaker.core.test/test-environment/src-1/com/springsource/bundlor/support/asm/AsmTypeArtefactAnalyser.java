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

package com.springsource.bundlor.support.asm;

import java.io.InputStream;

import org.objectweb.asm.ClassReader;

import com.springsource.bundlor.support.ArtifactAnalyzer;
import com.springsource.bundlor.support.partialmanifest.PartialManifest;

/**
 * {@link ArtifactAnalyzer} implementation that uses ASM to scan <code>.class</code> files for dependencies and exports.
 * <p/>
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Threadsafe.
 * 
 * @author Rob Harrop
 */
public final class AsmTypeArtefactAnalyser implements ArtifactAnalyzer {

    /**
     * @inheritDoc
     */
    public void analyse(InputStream artefact, String artefactName, PartialManifest model) throws Exception {
        ClassReader reader = new ClassReader(artefact);
        reader.accept(new ArtefactAnalyserClassVisitor(model), 0);
    }

    /**
     * @inheritDoc
     */
    public boolean canAnalyse(String artefactName) {
        return artefactName.endsWith(".class");
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "ASM Type Scanner";
    }

}
