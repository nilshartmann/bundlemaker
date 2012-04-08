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
package org.bundlemaker.core.parser.jdt.internal;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.jdt.content.JdtProjectContentProvider;
import org.bundlemaker.core.parser.IResourceCache;
import org.bundlemaker.core.parser.jdt.CoreParserJdt;
import org.bundlemaker.core.parser.jdt.IJdtSourceParserHook;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.resource.modifiable.IModifiableResource;
import org.bundlemaker.core.util.ExtensionRegistryTracker;
import org.bundlemaker.core.util.JavaTypeUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JdtParser extends AbstractHookAwareJdtParser {

  /** the AST parser */
  private ASTParser    _parser;

  /** the associated java project */
  private IJavaProject _javaProject;

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @throws CoreException
   */
  public JdtParser(IBundleMakerProject bundleMakerProject, ExtensionRegistryTracker<IJdtSourceParserHook> hookRegistry,
      boolean parseIndirectReferences) throws CoreException {

    super(hookRegistry);

    Assert.isNotNull(bundleMakerProject);

    // create the AST parser
    _parser = ASTParser.newParser(AST.JLS3);

    // the associated java project
    _javaProject = JdtProjectHelper.getAssociatedJavaProject(bundleMakerProject);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ParserType getParserType() {
    return ParserType.SOURCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized void parseResource(IProjectContentEntry projectContent, IResourceKey resourceKey,
      IResourceCache cache) {

    //
    if (!canParse(resourceKey)) {
      return;
    }

    // get the modifiable resource
    IModifiableResource modifiableResource = cache.getOrCreateResource(resourceKey);

    try {

      // _parser.setSource(iCompilationUnit);
      char[] content = new String(modifiableResource.getContent()).toCharArray();

      // TODO
      // if (projectContent.getProvider() instanceof JdtProjectContentProvider) {
      // IJavaProject javaProject = ((JdtProjectContentProvider) projectContent.getProvider()).getJavaProject();
      // _parser.setProject(javaProject);
      // } else {
      _parser.setProject(_javaProject);
      // }

      _parser.setSource(content);
      // TODO
      _parser.setUnitName("/" + _javaProject.getProject().getName() + "/" + modifiableResource.getPath());
      _parser.setCompilerOptions(CoreParserJdt.getCompilerOptionsWithComplianceLevel(null));
      _parser.setResolveBindings(true);

      CompilationUnit compilationUnit = (CompilationUnit) _parser.createAST(null);

      // for (org.eclipse.jdt.core.compiler.IProblem problem : compilationUnit.getProblems()) {
      // if (problem.isError()) {
      //
      //
      // // _parser.setSource(iCompilationUnit);
      // content = new String(modifiableResource.getContent()).toCharArray();
      // _parser.setProject(_javaProject);
      // _parser.setSource(content);
      // // TODO
      // _parser.setUnitName("/" + _javaProject.getProject().getName() + "/" + modifiableResource.getPath());
      // _parser.setCompilerOptions(CoreParserJdt.getCompilerOptionsWithComplianceLevel(null));
      // _parser.setResolveBindings(true);
      // CompilationUnit cu = (CompilationUnit) _parser.createAST(null);
      // cu.getProblems();
      // }
      // }

      analyzeCompilationUnit(modifiableResource, compilationUnit);

      // set the primary type
      String primaryTypeName = JavaTypeUtils.convertToFullyQualifiedName(modifiableResource.getPath(), ".java");
      IType primaryType = modifiableResource.getType(primaryTypeName);
      modifiableResource.setPrimaryType(primaryType);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean canParse(IResourceKey resourceKey) {
    return resourceKey.getPath().endsWith(".java");
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootMap
   * @param progressMonitor
   * 
   * @param entry
   * @param content
   * @throws JavaModelException
   */
  private void analyzeCompilationUnit(IModifiableResource modifiableResource, CompilationUnit compilationUnit)
      throws CoreException {

    // step 1: set the directly referenced types
    JdtAstVisitor visitor = new JdtAstVisitor(modifiableResource);
    compilationUnit.accept(visitor);

    // org.eclipse.jdt.core.IType primaryType = compilationUnit.getTypeRoot().findPrimaryType();
    // if (primaryType != null) {
    // Type type = (Type) modifiableResource.getType(primaryType.getFullyQualifiedName());
    // modifiableResource.setMainType(type);
    // } else {
    // // TODO
    // throw new RuntimeException(compilationUnit.toString());
    // }

    // step 2:
    callSourceParserHooks(modifiableResource, compilationUnit);

    // step 4: add the errors to the error list
    for (IProblem problem : visitor.getProblems()) {

      // add errors
      if (problem.isError()) {
        System.out.println("JDT Parser Error: " + problem.getMessage());
        getProblems().add(problem);
      }
    }
  }
}
