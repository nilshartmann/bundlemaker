package org.bundlemaker.core.parser.jdt.ecj;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.Compiler;
import org.eclipse.jdt.internal.compiler.ICompilerRequestor;
import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
import org.eclipse.jdt.internal.compiler.IProblemFactory;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;
import org.eclipse.jdt.internal.core.builder.NameEnvironment;
import org.eclipse.jdt.internal.core.util.ResourceCompilationUnit;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
@SuppressWarnings("restriction")
public class IndirectlyReferencesAnalyzer {

	/** - **/
	private IJavaProject _javaProject;

	/** - **/
	private Compiler _compiler;

	/** - **/
	private NameEnvironmentProxy _environment;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param javaProject
	 */
	public IndirectlyReferencesAnalyzer(IJavaProject javaProject) {
		Assert.isNotNull(javaProject);

		// the java project
		_javaProject = javaProject;

		// the name Environment
		_environment = new NameEnvironmentProxy(new NameEnvironment(
				_javaProject));

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
		CompilerOptions compilerOptions = new CompilerOptions(_javaProject
				.getOptions(true));

		// the compiler requestor
		ICompilerRequestor compilerRequestor = new ICompilerRequestor() {

			public void acceptResult(CompilationResult result) {
				if (result.hasErrors()) {
					// TODO...
					System.err.println(result);
				}
			}
		};

		// the problem factory
		IProblemFactory problemFactory = new DefaultProblemFactory();

		// create the compiler
		_compiler = new Compiler(_environment, errorHandlingPolicy,
				compilerOptions, compilerRequestor, problemFactory);
	}

	/**
	 * <p>
	 * Returns all referenced files for the given source file.
	 * </p>
	 * 
	 * @param sourceFile
	 * @return
	 */
	public Set<String> getAllReferencedTypes(IFile sourceFile) {
		Assert.isNotNull(sourceFile);

		ICompilationUnit[] units = new ICompilationUnit[] { new ResourceCompilationUnit(
				sourceFile, null) };
		_environment.resetRequestedTypes();
		_compiler.compile(units);

		return _environment.getRequestedTypes();
	}

}
