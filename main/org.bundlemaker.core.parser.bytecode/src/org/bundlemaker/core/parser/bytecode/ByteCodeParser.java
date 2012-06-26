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
import org.bundlemaker.core.parser.AbstractParser;
import org.bundlemaker.core.parser.IResourceCache;
import org.bundlemaker.core.parser.bytecode.asm.ArtefactAnalyserClassVisitor;
import org.bundlemaker.core.parser.bytecode.asm.AsmReferenceRecorder;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.ResourceKey;
import org.bundlemaker.core.resource.modifiable.IModifiableResource;
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
  public boolean canParse(IResourceKey resourceKey) {

    //
    if (!resourceKey.getPath().endsWith(".class")) {
      return false;
    }

    //
    return resourceKey.isValidJavaPackage();
  }

  @Override
  public void parseResource(IProjectContentEntry content, IResourceKey resourceKey, IResourceCache cache) {

    // get the IModifiableResource
    IModifiableResource resource = cache.getOrCreateResource(resourceKey);

    // if the resource already contains a type, it already has been parsed.
    // In this case we can return immediately
    if (!resource.getContainedTypes().isEmpty()) {
      return;
    }

    // if the resource does not contain a anonymous or local type
    // the enclosing resource is the resource (the default)
    IModifiableResource enclosingResource = resource;

    // get fully qualified type name
    String fullyQualifiedName = JavaTypeUtils.convertToFullyQualifiedName(resource.getPath());

    // if the type is an anonymous or local type,
    // we have to get the enclosing type name
    if (JavaTypeUtils.isLocalOrAnonymousTypeName(fullyQualifiedName)) {

      // get the name of the enclosing type
      String enclosingName = JavaTypeUtils.getEnclosingNonLocalAndNonAnonymousTypeName(fullyQualifiedName);

      // the resource key for the enclosing type
      ResourceKey enclosingKey = new ResourceKey(resourceKey.getProjectContentEntryId(), resourceKey.getRoot(),
          JavaTypeUtils.convertFromFullyQualifiedName(enclosingName));

      // get the enclosing resource
      enclosingResource = cache.getOrCreateResource(enclosingKey);

      // if we have to parse the enclosing type
      if (enclosingResource.getContainedTypes().isEmpty()) {
        parseResource(content, enclosingKey, cache);

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
