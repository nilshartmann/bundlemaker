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
package org.bundlemaker.core.parser.bytecode.asm;

import org.bundlemaker.core.resource.TypeEnum;
import org.eclipse.core.runtime.Assert;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.EmptyVisitor;

/**
 * <p>
 * </p>
 */
public class ArtefactAnalyserClassVisitor extends EmptyVisitor implements ClassVisitor {

  /** - */
  private static final String  CLASS_NAME_PREFIX = "class$";

  /** the ASM type */
  private Type                 _type;

  /** - */
  private AsmReferenceRecorder _recorder;

  /**
   * <p>
   * Creates a new instance of type {@link ArtefactAnalyserClassVisitor}.
   * </p>
   * 
   * @param recorder
   */
  public ArtefactAnalyserClassVisitor(AsmReferenceRecorder recorder) {

    Assert.isNotNull(recorder);

    _recorder = recorder;
  }

  /**
   * @inheritDoc
   */
  @Override
  public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

    // get the type
    _type = Type.getObjectType(name);
    
    // get the abstract flag
    boolean abstractType = ((access & Opcodes.ACC_ABSTRACT)!=0);
    
    // get the type type
    TypeEnum typeEnum = null;
    if ((access & Opcodes.ACC_ENUM) != 0) {
      typeEnum = TypeEnum.ENUM;
    } else if ((access & Opcodes.ACC_ANNOTATION) != 0) {
      typeEnum = TypeEnum.ANNOTATION;
    } else if ((access & Opcodes.ACC_INTERFACE) != 0) {
      typeEnum = TypeEnum.INTERFACE;
      abstractType = true;
    } else {
      typeEnum = TypeEnum.CLASS;
    }
    
    

    // get the fully qualified name
    String fullyQualifiedName = VisitorUtils.getFullyQualifiedTypeName(_type);

    // record the contained type
    _recorder.recordContainedType(fullyQualifiedName, typeEnum,abstractType);

    // super type
    // TODO: USES
    Type superType = Type.getObjectType(superName);
    VisitorUtils.recordReferencedTypes(_recorder, true, false, false, superType);

    //
    for (String interfaceName : interfaces) {

      // implemented interfaces
      // TODO: USES
      Type implementsType = Type.getObjectType(interfaceName);
      VisitorUtils.recordReferencedTypes(_recorder, false, true, false, implementsType);
    }
  }

  /**
   * @inheritDoc
   */
  @Override
  public AnnotationVisitor visitAnnotation(String desc, boolean visible) {

    // class annotation
    // TODO: USES
    Type type = Type.getType(desc);
    VisitorUtils.recordReferencedTypes(_recorder, false, false, true, type);

    return null;
  }

  /**
   * @inheritDoc
   */
  @Override
  public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
    Type t = Type.getType(desc);
    if ((access & Opcodes.ACC_SYNTHETIC) == Opcodes.ACC_SYNTHETIC) {
      if (Class.class.getName().equals(t.getClassName())) {
        if (name != null && name.startsWith(CLASS_NAME_PREFIX)) {
          name = name.substring(CLASS_NAME_PREFIX.length());
          name = name.replace('$', '.');
          int lastDotIndex = name.lastIndexOf('.');

          for (int i = 0; i < lastDotIndex; i++) {
            if (Character.isUpperCase(name.charAt(i))) {
              if (i == 0) {
                return null;
              }
              if (name.charAt(i - 1) == '.') {
                name = name.substring(0, i) + name.substring(i).replace('.', '$');
                break;
              }
            }
          }
          if (Character.isJavaIdentifierStart(name.charAt(0))) {
            // no uses
            VisitorUtils.recordReferencedTypes(_recorder, false, false, false, name);
          }
        }
      }
    }

    // no uses
    VisitorUtils.recordReferencedTypes(_recorder, false, false, false, t);

    //
    return new ArtefactAnalyserFieldVisitor(_recorder);
  }

  /**
   * @inheritDoc
   */
  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

    VisitorUtils.recordReferencedTypes(_recorder, false, false, false, Type.getArgumentTypes(desc));
    VisitorUtils.recordReferencedTypes(_recorder, false, false, false, Type.getReturnType(desc));

    if (exceptions != null) {
      for (String exception : exceptions) {
        VisitorUtils.recordReferencedTypes(_recorder, false, false, false, Type.getObjectType(exception));
      }
    }

    // TODO uses
    // if (access != Opcodes.ACC_PRIVATE) {
    // VisitorUtils.recordUses(this.partialManifest, this._type,
    // Type.getArgumentTypes(desc));
    // VisitorUtils.recordUses(this.partialManifest, this._type,
    // Type.getReturnType(desc));
    // if (exceptions != null) {
    // for (String exception : exceptions) {
    // VisitorUtils.recordUses(this.partialManifest, this._type,
    // Type.getObjectType(exception));
    // }
    // }
    // }

    return new ArtefactAnalyserMethodVisitor(_recorder);
  }
}
