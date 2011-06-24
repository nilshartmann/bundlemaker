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
package org.bundlemaker.core.itestframework.ecj;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
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
  private IJavaProject                _javaProject;

  /** - **/
  private Compiler                    _compiler;

  /** - **/
  private TracingNameEnvironmentProxy _environment;

  /** - */
  private static final boolean        USE_RESOURCE_AWARE_NAME_ENVIRONMENT_PROXY = false;

  /**
   * <p>
   * </p>
   * 
   * @param javaProject
   */
  @SuppressWarnings("unchecked")
  public IndirectlyReferencesAnalyzer(IJavaProject javaProject,
      IBundleMakerProjectDescription bundleMakerProjectDescription) {
    Assert.isNotNull(javaProject);
    Assert.isNotNull(bundleMakerProjectDescription);

    // the java project
    _javaProject = javaProject;

    INameEnvironment iNameEnvironment = new NameEnvironment(_javaProject);

    if (USE_RESOURCE_AWARE_NAME_ENVIRONMENT_PROXY) {
      _environment = new TracingNameEnvironmentProxy(new ResourceAwareNameEnvironmentProxy(iNameEnvironment,
          bundleMakerProjectDescription));
    } else {
      _environment = new TracingNameEnvironmentProxy(iNameEnvironment);
    }

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
    @SuppressWarnings("rawtypes")
    Map options = CoreParserJdt.getCompilerOptionsWithComplianceLevel(_javaProject.getOptions(true));
    CompilerOptions compilerOptions = new CompilerOptions(options);

    // TODO: make configurable...
    compilerOptions.docCommentSupport = false;

    // the compiler requestor
    ICompilerRequestor compilerRequestor = new ICompilerRequestor() {

      public void acceptResult(CompilationResult result) {
        if (result.hasErrors()) {

          System.out.println(result.getFileName());

          for (CategorizedProblem problem : result.getErrors()) {
            // TODO...
            System.out.println(problem);
          }
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
   * @param content
   * @return
   * @throws IOException
   */
  public Set<String> getAllReferencedTypes(IResource resource, char[] content) throws IOException {

    ICompilationUnit[] units = new ICompilationUnit[] { new CompilationUnitImpl(resource, content) };
    _environment.resetRequestedTypes();
    _compiler.compile(units);
    return _environment.getRequestedTypes();
  }
}
