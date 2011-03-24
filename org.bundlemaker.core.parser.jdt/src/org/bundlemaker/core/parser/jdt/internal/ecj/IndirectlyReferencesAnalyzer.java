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
package org.bundlemaker.core.parser.jdt.internal.ecj;

import java.io.IOException;
import java.util.Set;

import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.modifiable.IModifiableResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.Compiler;
import org.eclipse.jdt.internal.compiler.ICompilerRequestor;
import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
import org.eclipse.jdt.internal.compiler.IProblemFactory;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;
import org.eclipse.jdt.internal.core.builder.NameEnvironment;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
@SuppressWarnings("restriction")
public class IndirectlyReferencesAnalyzer {

  /** - **/
  private IJavaProject                      _javaProject;

  /** - **/
  private Compiler                          _compiler;

  /** - **/
  private TracingNameEnvironmentProxy       _environment;

  /** - */
  private ResourceAwareNameEnvironmentProxy _resourceAwareNameEnvironment;

  /**
   * <p>
   * </p>
   * 
   * @param javaProject
   */
  public IndirectlyReferencesAnalyzer(IJavaProject javaProject,
      IBundleMakerProjectDescription bundleMakerProjectDescription) {
    Assert.isNotNull(javaProject);
    Assert.isNotNull(bundleMakerProjectDescription);

    // the java project
    _javaProject = javaProject;

    _resourceAwareNameEnvironment = new ResourceAwareNameEnvironmentProxy(new NameEnvironment(_javaProject),
        bundleMakerProjectDescription);
    _environment = new TracingNameEnvironmentProxy(_resourceAwareNameEnvironment);

    // the error handling policy
    IErrorHandlingPolicy errorHandlingPolicy = new IErrorHandlingPolicy() {
      public boolean proceedOnErrors() {
        return false; // stop if there are some errors
      }

      public boolean stopOnFirstError() {
        return false;
      }
    };

    // the compiler options
    CompilerOptions compilerOptions = new CompilerOptions(_javaProject.getOptions(true));

    // TODO: make configurable...
    compilerOptions.docCommentSupport = false;

    // the compiler requestor
    ICompilerRequestor compilerRequestor = new ICompilerRequestor() {

      public void acceptResult(CompilationResult result) {
        if (result.hasErrors()) {
          // TODO...
          // System.err.println(result);
        }
      }
    };

    // the problem factory
    IProblemFactory problemFactory = new DefaultProblemFactory();

    // create the compiler
    _compiler = new Compiler(_environment, errorHandlingPolicy, compilerOptions, compilerRequestor, problemFactory);
  }

  /**
   * <p>
   * </p>
   * 
   * @param modifiableResource
   * @return
   * @throws IOException
   */
  public Set<String> getAllReferencedTypes(IModifiableResource modifiableResource) throws IOException {

    //
    return getAllReferencedTypes(modifiableResource, new String(modifiableResource.getContent()).toCharArray());
  }

  /**
   * <p>
   * </p>
   * 
   * @param modifiableResource
   * @param content
   * @return
   * @throws IOException
   */
  public Set<String> getAllReferencedTypes(IResource resource, char[] content) throws IOException {

    ICompilationUnit[] units = new ICompilationUnit[] { _resourceAwareNameEnvironment.getCompilationUnit(resource) };
    _environment.resetRequestedTypes();
    _compiler.compile(units);

    return _environment.getRequestedTypes();
  }

}
