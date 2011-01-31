package org.bundlemaker.core.parser.bytecode.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.EmptyVisitor;

import com.springsource.bundlor.support.partialmanifest.PartialManifest;

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
