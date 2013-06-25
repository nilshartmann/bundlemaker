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
package org.bundlemaker.core.jdt.internal.parser;

import org.bundlemaker.core._type.IParsableTypeResource;
import org.bundlemaker.core._type.IType;
import org.bundlemaker.core._type.utils.JavaTypeUtils;
import org.bundlemaker.core.jdt.content.JdtProjectContentProvider;
import org.bundlemaker.core.jdt.internal.ExtensionRegistryTracker;
import org.bundlemaker.core.jdt.parser.CoreParserJdt;
import org.bundlemaker.core.jdt.parser.IJdtSourceParserHook;
import org.bundlemaker.core.parser.IProblem;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.bundlemaker.core.spi.parser.IParsableResource;
import org.bundlemaker.core.spi.parser.IParserContext;
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
  public JdtParser(IProjectDescriptionAwareBundleMakerProject bundleMakerProject, ExtensionRegistryTracker<IJdtSourceParserHook> hookRegistry)
      throws CoreException {

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
  protected synchronized void doParseResource(IProjectContentEntry projectContent, IParsableResource resource, IParserContext cache, boolean parseReferences) {

    //
    if (!canParse(resource)) {
      return;
    }

    try {

      // _parser.setSource(iCompilationUnit);


      // TODO
//      if (projectContent.getProvider() instanceof JdtProjectContentProvider) {
//        String root = resource.getRoot();
//        IJavaProject javaProject = ((JdtProjectContentProvider) projectContent.getProvider()).getSourceJavaProject(
//            projectContent, root);
//        _parser.setProject(javaProject);
//        _parser.setUnitName("/" + _javaProject.getProject().getName() + "/" + resource.getPath());
//      } else {

//      }
      
      _parser.setSource(new String(resource.getContent()).toCharArray());
      _parser.setProject(_javaProject);
      _parser.setUnitName("/" + _javaProject.getProject().getName() + "/" + resource.getPath());
      _parser.setCompilerOptions(CoreParserJdt.getCompilerOptionsWithComplianceLevel(null));
      _parser.setResolveBindings(true);

      //
      analyzeCompilationUnit(resource, (CompilationUnit) _parser.createAST(null));

      // set the primary type
      String primaryTypeName = JavaTypeUtils.convertToFullyQualifiedName(resource.getPath(), ".java");
      IType primaryType = resource.adaptAs(IParsableTypeResource.class).getType(primaryTypeName);
      resource.adaptAs(IParsableTypeResource.class).setPrimaryType(primaryType);

    } catch (Exception e) {
      getProblems().add(new IProblem.DefaultProblem(resource, "Error while parsing: " + e));
      e.printStackTrace();
    }
  }

  @Override
  public boolean canParse(IParsableResource resource) {
    return resource.getPath().endsWith(".java");
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
  private void analyzeCompilationUnit(IParsableResource modifiableResource, CompilationUnit compilationUnit)
      throws CoreException {

    // step 1: set the directly referenced types
    JdtAstVisitor visitor = new JdtAstVisitor(modifiableResource);
    compilationUnit.accept(visitor);

    // step 2:
    callSourceParserHooks(modifiableResource, compilationUnit);

    // step 4: add the errors to the error list
    for (IProblem problem : visitor.getProblems()) {

      // add errors
      if (problem.isError()) {
        getProblems().add(problem);
      }
    }
  }
}
