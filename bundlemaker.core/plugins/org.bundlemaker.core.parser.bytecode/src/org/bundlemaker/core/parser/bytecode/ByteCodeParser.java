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

import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.jtype.ITypeResource;
import org.bundlemaker.core.jtype.JavaTypeUtils;
import org.bundlemaker.core.jtype.JavaUtils;
import org.bundlemaker.core.parser.IProblem;
import org.bundlemaker.core.parser.bytecode.asm.ArtefactAnalyserClassVisitor;
import org.bundlemaker.core.parser.bytecode.asm.AsmReferenceRecorder;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.spi.parser.AbstractParser;
import org.bundlemaker.core.spi.parser.IParsableResource;
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
	public boolean canParse(IParsableResource resource) {

		//
		if (!resource.getPath().endsWith(".class")) {
			return false;
		}

		//
		return JavaUtils.isValidJavaPackage(resource.getPath());
	}

	@Override
  protected void doParseResource(IProjectContentEntry content,
			IParsableResource resource, boolean parseReferences, boolean isBatchParse) {

		// if the resource already contains a type, it already has been parsed.
		// In this case we can return immediately
		if (resource.adaptAs(ITypeResource.class) != null && !resource.adaptAs(ITypeResource.class).getContainedTypes()
				.isEmpty()) {
			return;
		}

		// if the resource does not contain a anonymous or local type
		// the enclosing resource is the resource (the default)
		IParsableResource enclosingResource = resource;

		// get fully qualified type name
		String fullyQualifiedName = JavaTypeUtils
				.convertToFullyQualifiedName(resource.getPath());

		// if the type is an anonymous or local type,
		// we have to get the enclosing type name
		if (JavaTypeUtils.isLocalOrAnonymousTypeName(fullyQualifiedName)
				&& parseReferences) {

			// get the name of the enclosing type
			String enclosingName = JavaTypeUtils
					.getEnclosingNonLocalAndNonAnonymousTypeName(fullyQualifiedName);

			// get the enclosing resource
			enclosingResource = content.getResource(
					JavaTypeUtils.convertFromFullyQualifiedName(enclosingName),
					ResourceType.BINARY).adaptAs(IParsableResource.class);

			// if we have to parse the enclosing type
			if (enclosingResource.adaptAs(ITypeResource.class)
					.getContainedTypes().isEmpty()) {
				parseResource(content, enclosingResource, true, false);

				if (enclosingResource.adaptAs(ITypeResource.class)
						.getContainedTypes().isEmpty()) {
					// TODO
					// TODO remove null handling in AsmReferenceRecorder
					// Assert.isTrue(!enclosingResource.getContainedTypes().isEmpty());
				}
			}
		}

		try {

			// create a new references recorder
			AsmReferenceRecorder referenceRecorder = new AsmReferenceRecorder(
					resource, enclosingResource);

			// parse the class file
			byte[] bytes = resource.getContent();
			ClassReader reader = new ClassReader(bytes);
			reader.accept(new ArtefactAnalyserClassVisitor(referenceRecorder,
					parseReferences), 0);

		} catch (Exception e) {
			e.printStackTrace();
			IProblem byteCodeParserProblem = new IProblem.DefaultProblem(
					resource, e.toString());
			getProblems().add(byteCodeParserProblem);
		}
	}
}
