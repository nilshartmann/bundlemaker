package org.bundlemaker.core.parser.bytecode.asm;

import org.bundlemaker.core.resource.ReferenceType;
import org.bundlemaker.core.resource.modifiable.ReferenceAttributes;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.EmptyVisitor;

public class ArtefactAnalyserFieldVisitor extends EmptyVisitor implements
		FieldVisitor {

	/** - */
	private final AsmReferenceRecorder _recorder;

	/**
	 * @param recorder
	 */
	ArtefactAnalyserFieldVisitor(AsmReferenceRecorder recorder) {
		_recorder = recorder;
	}

	/**
	 * {@inheritDoc}
	 */
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {

		//
		Type t = Type.getType(desc);

		// uses
		_recorder.recordReference(VisitorUtils.getFullyQualifiedTypeName(t),
				new ReferenceAttributes(ReferenceType.TYPE_REFERENCE, false,
						false, false, false, true, true, false));

		return null;
	}

}
