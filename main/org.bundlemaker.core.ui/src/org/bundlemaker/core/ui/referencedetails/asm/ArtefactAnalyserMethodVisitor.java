/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.referencedetails.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.EmptyVisitor;

public class ArtefactAnalyserMethodVisitor extends EmptyVisitor implements MethodVisitor {

  /** - */
  private final AsmReferenceRecorder _recorder;

  /**
   * @param recorder
   * @param type
   */
  public ArtefactAnalyserMethodVisitor(AsmReferenceRecorder recorder) {
    this._recorder = recorder;
  }

  @Override
  public void visitLineNumber(int line, Label start) {
    _recorder.recordCurrentLineNumber(line);
  }

  /**
   * @inheritDoc
   */
  @Override
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
  @Override
  public void visitFieldInsn(int opcode, String owner, String name, String desc) {

    VisitorUtils.recordReferencedTypes(_recorder, Type.getType(desc));
    VisitorUtils.recordReferencedTypes(_recorder, Type.getObjectType(owner));
  }

  /**
   * @inheritDoc
   */
  @Override
  public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {

    VisitorUtils.recordReferencedTypes(_recorder, Type.getType(desc));
  }

  /**
   * @inheritDoc
   */
  @Override
  public void visitMethodInsn(int opcode, String owner, String name, String desc) {

    Type t = Type.getObjectType(owner);
    VisitorUtils.recordReferencedTypes(_recorder, t);
    VisitorUtils.recordReferencedTypes(_recorder, Type.getReturnType(desc));
    VisitorUtils.recordReferencedTypes(_recorder, Type.getArgumentTypes(desc));
  }

  /**
   * @inheritDoc
   */
  @Override
  public void visitMultiANewArrayInsn(String desc, int dims) {

    Type t = Type.getType(desc);
    VisitorUtils.recordReferencedTypes(_recorder, t);
  }

  /**
   * @inheritDoc
   */
  @Override
  public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {

    Type t = Type.getType(desc);
    VisitorUtils.recordReferencedTypes(_recorder, t);
    // TODO uses
    // VisitorUtils.recordUses(partialManifest, this.type, t);
    return null;
  }

  /**
   * @inheritDoc
   */
  @Override
  public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
    if (type != null) {
      Type t = Type.getObjectType(type);
      VisitorUtils.recordReferencedTypes(_recorder, t);
    }
  }

  /**
   * @inheritDoc
   */
  @Override
  public void visitTypeInsn(int opcode, String type) {
    Type t = Type.getObjectType(type);
    VisitorUtils.recordReferencedTypes(_recorder, t);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visitLdcInsn(Object cst) {
    if (cst instanceof Type) {
      VisitorUtils.recordReferencedTypes(_recorder, (Type) cst);
    }
  }

}
