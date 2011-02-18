package org.bundlemaker.core.parser.jdt.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.parser.IDirectory;
import org.bundlemaker.core.parser.IDirectoryFragment;
import org.bundlemaker.core.parser.IResourceCache;
import org.bundlemaker.core.parser.jdt.IJdtSourceParserHook;
import org.bundlemaker.core.parser.jdt.internal.ecj.IndirectlyReferencesAnalyzer;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.modifiable.IModifiableResource;
import org.bundlemaker.core.util.ExtensionRegistryTracker;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
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
	private ASTParser _parser;

	/** the associated java project */
	private IJavaProject _javaProject;

	/** the indirectly references analyzer **/
	private IndirectlyReferencesAnalyzer _indirectlyReferencesAnalyzer;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleMakerProject
	 * @throws CoreException
	 */
	public JdtParser(IBundleMakerProject bundleMakerProject,
			ExtensionRegistryTracker<IJdtSourceParserHook> hookRegistry)
			throws CoreException {

		super(hookRegistry);

		Assert.isNotNull(bundleMakerProject);

		// create the AST parser
		_parser = ASTParser.newParser(AST.JLS3);

		// the associated java project
		_javaProject = JdtProjectHelper
				.getAssociatedJavaProject(bundleMakerProject);

		//
		_indirectlyReferencesAnalyzer = new IndirectlyReferencesAnalyzer(
				_javaProject);
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
	public List<IProblem> parse(IFileBasedContent content,
			List<IDirectory> directoryList, IResourceCache cache,
			IProgressMonitor progressMonitor) throws CoreException {

		// create the error list
		List<IProblem> _errors = new LinkedList<IProblem>();

		// parse the compilation units
		if (content.isResourceContent()
				&& !content.getResourceContent().getSourceResources().isEmpty()
				&& content.getResourceContent().isAnalyzeSourceResources()) {

			_errors.addAll(parseCompilationUnits(directoryList, cache, content,
					progressMonitor));
		}

		// return the errors
		return _errors;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param progressMonitor
	 * 
	 * @param compilationUnits
	 * @throws JavaModelException
	 */
	private List<IProblem> parseCompilationUnits(
			List<IDirectory> directoryList, IResourceCache cache,
			IFileBasedContent fileBasedContent, IProgressMonitor progressMonitor)
			throws CoreException {

		//
		List<IProblem> problems = new LinkedList<IProblem>();

		//
		for (IDirectory directory : directoryList) {

			//
			if (!directory.getDirectoryName().equals(new Path("META-INF"))
					&& directory.hasSourceContent()) {

				//
				for (IDirectoryFragment directoryFragment : directory
						.getSourceDirectoryFragments()) {

					//
					for (IResourceKey resourceKey : directoryFragment
							.getResourceKeys()) {

						//
						parseResource(resourceKey, cache, problems);

						progressMonitor.worked(1);
					}
				}
			}
		}

		//
		return problems;
	}

	/**
	 * @param resourceKey
	 * @param cache
	 */
	private void parseResource(IResourceKey resourceKey, IResourceCache cache,
			List<IProblem> problems) {

		//
		if (!resourceKey.getPath().endsWith(".java")) {
			return;
		}

		// get the modifiable resource
		IModifiableResource modifiableResource = cache
				.getOrCreateResource(resourceKey);

		try {

			// TODO configurable
			Map options = new HashMap();
			options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);
			options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM,
					JavaCore.VERSION_1_6);
			options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_6);

			// _parser.setSource(iCompilationUnit);
			char[] content = getCharsFromInputStream(modifiableResource
					.getInputStream());
			_parser.setProject(_javaProject);
			_parser.setSource(content);

			// TODO
			_parser.setUnitName("/" + _javaProject.getProject().getName() + "/"
					+ modifiableResource.getPath());
			_parser.setCompilerOptions(options);
			_parser.setResolveBindings(true);

			CompilationUnit compilationUnit = (CompilationUnit) _parser
					.createAST(null);

			analyzeCompilationUnit(modifiableResource, compilationUnit,
					problems);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	private List<IProblem> analyzeCompilationUnit(
			IModifiableResource modifiableResource,
			CompilationUnit compilationUnit, List<IProblem> problems)
			throws CoreException {

		// step 1: set the directly referenced types
		JdtAstVisitor visitor = new JdtAstVisitor(modifiableResource);
		compilationUnit.accept(visitor);

		// step 2:
		callSourceParserHooks(modifiableResource, compilationUnit);

		// step 3: compute the indirectly referenced types
		// Set<String> directlyAndIndirectlyReferencedTypes =
		// _indirectlyReferencesAnalyzer
		// .getAllReferencedTypes((IFile) iCompilationUnit
		// .getCorrespondingResource());
		//
		// for (String type : directlyAndIndirectlyReferencedTypes) {
		//
		// resource.recordReference(type, new ReferenceAttributes(
		// ReferenceType.TYPE_REFERENCE, false, false, false, false,
		// false, false, true));
		//
		// }

		// step 4: add the errors to the error list
		for (IProblem problem : visitor.getProblems()) {

			// add errors
			if (problem.isError()) {
				System.out.println(problem.getMessage());
				problems.add(problem);
			}
		}

		// step 5: finally return
		return problems;
	}

	/**
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private static char[] getCharsFromInputStream(InputStream is)
			throws IOException {

		Reader reader = new InputStreamReader(is);
		StringWriter result = new StringWriter();

		int data = reader.read();
		while (data != -1) {
			char theChar = (char) data;
			result.append(theChar);
			data = reader.read();
		}

		reader.close();
		return result.toString().toCharArray();
	}
}
