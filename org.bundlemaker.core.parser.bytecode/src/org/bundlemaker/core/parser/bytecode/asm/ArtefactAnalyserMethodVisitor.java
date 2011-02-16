package org.bundlemaker.core.parser.bytecode.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.EmptyVisitor;

public class ArtefactAnalyserMethodVisitor extends EmptyVisitor implements
		MethodVisitor {

	/** - */
	private final AsmReferenceRecorder _recorder;

	/**
	 * The type that is being scanned.
	 */
	private final Type type;

	/**
	 * <p>
	 * Creates a new instance of type {@link ArtefactAnalyserMethodVisitor}.
	 * </p>
	 * 
	 * @param recorder
	 * @param type
	 */
	public ArtefactAnalyserMethodVisitor(AsmReferenceRecorder recorder,
			Type type) {
		this._recorder = recorder;
		this.type = type;
	}

	/**
	 * @inheritDoc
	 */
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {

		Type t = Type.getType(desc);
		VisitorUtils.recordReferencedTypes(_recorder, t);
		// TODO uses
		// VisitorUtils.recordUses(partialManifest, this.type, t);
		return null;
	}

	/**
	 * @inheritDoc
	 */
	public void visitFieldInsn(int opcode, String owner, String name,
			String desc) {

		VisitorUtils.recordReferencedTypes(_recorder, Type.getType(desc));
		VisitorUtils
				.recordReferencedTypes(_recorder, Type.getObjectType(owner));
	}

	/**
	 * @inheritDoc
	 */
	public void visitLocalVariable(String name, String desc, String signature,
			Label start, Label end, int index) {

		VisitorUtils.recordReferencedTypes(_recorder, Type.getType(desc));
	}

	/**
	 * @inheritDoc
	 */
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {

		Type t = Type.getObjectType(owner);
		VisitorUtils.recordReferencedTypes(_recorder, t);
		VisitorUtils.recordReferencedTypes(_recorder, Type.getReturnType(desc));
		VisitorUtils.recordReferencedTypes(_recorder,
				Type.getArgumentTypes(desc));
	}

	/**
	 * @inheritDoc
	 */
	public void visitMultiANewArrayInsn(String desc, int dims) {

		Type t = Type.getType(desc);
		VisitorUtils.recordReferencedTypes(_recorder, t);
	}

	/**
	 * @inheritDoc
	 */
	public AnnotationVisitor visitParameterAnnotation(int parameter,
			String desc, boolean visible) {

		Type t = Type.getType(desc);
		VisitorUtils.recordReferencedTypes(_recorder, t);
		// TODO uses
		// VisitorUtils.recordUses(partialManifest, this.type, t);
		return null;
	}

	/**
	 * @inheritDoc
	 */
	public void visitTryCatchBlock(Label start, Label end, Label handler,
			String type) {
		if (type != null) {
			Type t = Type.getObjectType(type);
			VisitorUtils.recordReferencedTypes(_recorder, t);
		}
	}

	/**
	 * @inheritDoc
	 */
	public void visitTypeInsn(int opcode, String type) {
		Type t = Type.getObjectType(type);
		VisitorUtils.recordReferencedTypes(_recorder, t);
	}

	/**
	 * {@inheritDoc}
	 */
	public void visitLdcInsn(Object cst) {
		if (cst instanceof Type) {
			VisitorUtils.recordReferencedTypes(_recorder, (Type) cst);
		}
	}

}
