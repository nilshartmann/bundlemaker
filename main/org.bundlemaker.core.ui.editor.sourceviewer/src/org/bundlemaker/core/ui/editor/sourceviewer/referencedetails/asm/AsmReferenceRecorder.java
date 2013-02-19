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
package org.bundlemaker.core.ui.editor.sourceviewer.referencedetails.asm;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.resource.TypeEnum;
import org.bundlemaker.core.resource.modifiable.IReferenceRecorder;
import org.bundlemaker.core.resource.modifiable.ReferenceAttributes;
import org.bundlemaker.core.util.collections.GenericCache;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AsmReferenceRecorder implements IReferenceRecorder {

  //
  private GenericCache<String, List<Integer>> lines = new GenericCache<String, List<Integer>>() {

                                                      @Override
                                                      protected List<Integer> create(String key) {
                                                        return new LinkedList<Integer>();
                                                      }
                                                    };

  /** - */
  private int                                 _currentline;

  public GenericCache<String, List<Integer>> getLines() {
    return lines;
  }

  /**
   * @param lineNumber
   */
  public void recordCurrentLineNumber(int lineNumber) {
    _currentline = lineNumber;
  }

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   */
  public void recordContainedType(String fullyQualifiedName, TypeEnum typeEnum) {

    // try {
    //
    // //
    // Assert.isNotNull(fullyQualifiedName);
    //
    // // set the type name
    // _fullyQualifiedTypeName = fullyQualifiedName;
    //
    // //
    // if (JavaTypeUtils.isLocalOrAnonymousTypeName(fullyQualifiedName)) {
    //
    // IType type = _resource.getOrCreateType(fullyQualifiedName, typeEnum);
    // _resource.setPrimaryType(type);
    //
    // // we have to check for the existence of contained types:
    // // in the rare case of an (erroneous) non-set type we fall back
    // // on the resource type
    // if (!(_enclosingClassFileResource.getContainedTypes().isEmpty())) {
    //
    // //
    // _bundleMakerType = (_enclosingClassFileResource.getContainedTypes().toArray(new IModifiableType[0]))[0];
    //
    // _fullyQualifiedEnclosingTypeName = _bundleMakerType.getFullyQualifiedName();
    //
    // } else {
    //
    // // create the fall-back type
    // _bundleMakerType = _resource.getOrCreateType(fullyQualifiedName, typeEnum);
    // }
    //
    // // add as sticky
    // _enclosingClassFileResource.addStickyResource(_resource);
    //
    // } else {
    //
    // // create the type
    // _bundleMakerType = _resource.getOrCreateType(fullyQualifiedName, typeEnum);
    // }
    //
    // } catch (RuntimeException runtimeException) {
    //
    // //
    // System.out.println(String.format("Exception while parsing '%s' [enclosing: '%s']", _resource,
    // _enclosingClassFileResource));
    // runtimeException.printStackTrace();
    // }
  }

  @Override
  public void recordReference(String fullyQualifiedName, ReferenceAttributes attributes) {

    //
    if (fullyQualifiedName == null) {
      return;
    }

    // try {

    // if (fullyQualifiedName != null && !fullyQualifiedName.equals(_fullyQualifiedTypeName)
    // && !fullyQualifiedName.equals(_fullyQualifiedEnclosingTypeName)) {
    //
    // //
    // if (!_resource.equals(_enclosingClassFileResource)) {
    //
    // attributes = new ReferenceAttributes(ReferenceType.TYPE_REFERENCE, false, false, false,
    // attributes.isCompileTime(), attributes.isRuntimeTime(), attributes.isDirectlyReferenced(),
    // attributes.isIndirectlyReferenced());
    // }

    //
    org.objectweb.asm.Type objectType = org.objectweb.asm.Type.getObjectType(fullyQualifiedName);

    String name = VisitorUtils.getFullyQualifiedTypeName(objectType);

    lines.getOrCreate(name).add(_currentline);

    // _bundleMakerType.recordReference(name, attributes);
    // }
    //
    // } catch (RuntimeException runtimeException) {
    //
    // //
    // System.out.println(String.format("Exception while parsing '%s' [enclosing: '%s']", _resource,
    // _enclosingClassFileResource));
    // runtimeException.printStackTrace();
    // }
  }
}
