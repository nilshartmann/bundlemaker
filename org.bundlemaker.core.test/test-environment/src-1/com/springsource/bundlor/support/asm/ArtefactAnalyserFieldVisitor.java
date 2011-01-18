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

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.EmptyVisitor;

import com.springsource.bundlor.support.partialmanifest.PartialManifest;

/**
 * ASM {@link FieldVisitor} for scanning class files.
 * <p/>
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Not thread safe.
 * 
 * @author Glyn Normington
 */
final class ArtefactAnalyserFieldVisitor extends EmptyVisitor implements FieldVisitor {

    /**
     * That <code>PartialManifest</code> being updated.
     */
    private final PartialManifest partialManifest;

    /**
     * The type that is being scanned.
     */
    private final Type type;

    /**
     * Creates a new <code>ArtefactAnalyserClassVisitor</code> to scan the supplied {@link PartialManifest}.
     * 
     * @param partialManifest the <code>PartialManifest</code> to scan.
     */
    ArtefactAnalyserFieldVisitor(PartialManifest partialManifest, Type type) {
        this.partialManifest = partialManifest;
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        Type t = Type.getType(desc);
        VisitorUtils.recordReferencedTypes(partialManifest, t);
        VisitorUtils.recordUses(partialManifest, this.type, t);
        return null;
    }

}
