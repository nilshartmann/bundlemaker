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
package org.bundlemaker.core.parser.bytecode;

import org.bundlemaker.core.DefaultProblemImpl;
import org.bundlemaker.core._type.utils.JavaUtils;
import org.bundlemaker.core.parser.AbstractParser;
import org.bundlemaker.core.parser.IResourceCache;
import org.bundlemaker.core.parser.bytecode.asm.ArtefactAnalyserClassVisitor;
import org.bundlemaker.core.parser.bytecode.asm.AsmReferenceRecorder;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.resource.IParsableResource;
import org.bundlemaker.core.resource.IProjectContentResource;
import org.bundlemaker.core.util.JavaTypeUtils;
import org.objectweb.asm.ClassReader;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ByteCodeParser extends AbstractParser {

  /**
   * {@inheritDoc}
   */
  @Override
  public ParserType getParserType() {
    return ParserType.BINARY;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canParse(IProjectContentResource resourceKey) {

    //
    if (!resourceKey.getPath().endsWith(".class")) {
      return false;
    }

    //
    return JavaUtils.isValidJavaPackage(resourceKey.getPath());
  }

  @Override
  protected void doParseResource(IProjectContentEntry content, IProjectContentResource resourceKey, IResourceCache cache) {

    // get the IModifiableResource
    IParsableResource resource = cache.getOrCreateResource(resourceKey.getProjectContentEntryId(),
        resourceKey.getRoot(), resourceKey.getPath());

    // if the resource already contains a type, it already has been parsed.
    // In this case we can return immediately
    if (!resource.getContainedTypes().isEmpty()) {
      return;
    }

    // if the resource does not contain a anonymous or local type
    // the enclosing resource is the resource (the default)
    IParsableResource enclosingResource = resource;

    // get fully qualified type name
    String fullyQualifiedName = JavaTypeUtils.convertToFullyQualifiedName(resource.getPath());

    // if the type is an anonymous or local type,
    // we have to get the enclosing type name
    if (JavaTypeUtils.isLocalOrAnonymousTypeName(fullyQualifiedName)) {

      // get the name of the enclosing type
      String enclosingName = JavaTypeUtils.getEnclosingNonLocalAndNonAnonymousTypeName(fullyQualifiedName);

      // get the enclosing resource
      enclosingResource = cache.getOrCreateResource(resourceKey.getProjectContentEntryId(), resourceKey.getRoot(),
          JavaTypeUtils.convertFromFullyQualifiedName(enclosingName));

      // if we have to parse the enclosing type
      if (enclosingResource.getContainedTypes().isEmpty()) {
        parseResource(content, enclosingResource, cache);

        if (enclosingResource.getContainedTypes().isEmpty()) {
          // TODO
          // TODO remove null handling in AsmReferenceRecorder
          // Assert.isTrue(!enclosingResource.getContainedTypes().isEmpty());
        }
      }
    }

    try {

      // create a new references recorder
      AsmReferenceRecorder referenceRecorder = new AsmReferenceRecorder(resource, enclosingResource);

      // parse the class file
      byte[] bytes = resource.getContent();
      ClassReader reader = new ClassReader(bytes);
      reader.accept(new ArtefactAnalyserClassVisitor(referenceRecorder), 0);

    } catch (Exception e) {
      e.printStackTrace();
      DefaultProblemImpl byteCodeParserProblem = new DefaultProblemImpl(resourceKey, e.toString());
      getProblems().add(byteCodeParserProblem);
    }

  }
}
