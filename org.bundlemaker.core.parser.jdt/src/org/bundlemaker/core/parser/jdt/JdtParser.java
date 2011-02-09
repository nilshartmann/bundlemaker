package org.bundlemaker.core.parser.jdt;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.parser.IDirectory;
import org.bundlemaker.core.parser.IDirectoryFragment;
import org.bundlemaker.core.parser.IParser;
import org.bundlemaker.core.parser.IResourceCache;
import org.bundlemaker.core.parser.jdt.JavaElementIdentifier.FileType;
import org.bundlemaker.core.parser.jdt.ast.JdtAstVisitor;
import org.bundlemaker.core.parser.jdt.ecj.IndirectlyReferencesAnalyzer;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.resource.ReferenceType;
import org.bundlemaker.core.resource.ResourceKey;
import org.bundlemaker.core.resource.modifiable.IModifiableResource;
import org.bundlemaker.core.resource.modifiable.ReferenceAttributes;
import org.bundlemaker.core.util.ExtensionRegistryTracker;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
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
public class JdtParser implements IParser {

	/** the AST parser */
	private ASTParser _parser;

	/** the associated java project */
	private IJavaProject _javaProject;

	/** the indirectly references analyzer **/
	private IndirectlyReferencesAnalyzer _indirectlyReferencesAnalyzer;

	/** - */
	private ExtensionRegistryTracker<IJdtSourceParserHook> _hookRegistry;

	/** - */
	private List<IJdtSourceParserHook> _currentHooks;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ParserType getParserType() {
		return ParserType.SOURCE;
	}

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

		// create the AST parser
		_parser = ASTParser.newParser(AST.JLS3);

		// the associated java project
		_javaProject = JdtProjectHelper
				.getAssociatedJavaProject(bundleMakerProject);

		//
		_indirectlyReferencesAnalyzer = new IndirectlyReferencesAnalyzer(
				_javaProject);

		//
		_hookRegistry = hookRegistry;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void parseBundleMakerProjectStart(
			IBundleMakerProject bundleMakerProject) {

		// initialize current hooks
		_currentHooks = _hookRegistry.getExtensionObjects();

		// notify 'start'
		for (IJdtSourceParserHook sourceParserHook : _currentHooks) {
			sourceParserHook.parseBundleMakerProjectStart(bundleMakerProject);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void parseBundleMakerProjectStop(
			IBundleMakerProject bundleMakerProject) {

		// notify 'stop'
		for (IJdtSourceParserHook sourceParserHook : _currentHooks) {
			sourceParserHook.parseBundleMakerProjectStop(bundleMakerProject);
		}
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

		List<IProblem> problems = new LinkedList<IProblem>();

		// // log
		// _progressMonitor.subTask("Parsing source files...");

		Map<ICompilationUnit, IDirectory> units = new HashMap<ICompilationUnit, IDirectory>();

		Map<String, String> rootMap = new HashMap<String, String>();

		/******** START *******/
		/** extract ICompilationUnits **/

		for (IDirectory directory : directoryList) {

			if (!directory.getDirectoryName().equals(new Path("META-INF"))
					&& directory.hasSourceContent()) {

				/** do for all directoryFragments **/
				for (IDirectoryFragment directoryFragment : directory
						.getSourceDirectoryFragments()) {

					IPath path = JdtProjectHelper.makeCanonical(new Path(
							directoryFragment.getDirectoryFragmentRoot()
									.getAbsolutePath()));

					rootMap.put(path.toPortableString(), directoryFragment
							.getDirectoryFragmentRoot().getAbsolutePath());

					IResource iResource = _javaProject.getProject().findMember(
							path);

					if (iResource == null) {
						throw new RuntimeException(path.toOSString());
					}

					IPackageFragmentRoot root = _javaProject
							.getPackageFragmentRoot(iResource);

					root.open(null);

					IPackageFragment fragment = root
							.getPackageFragment(directory.getDirectoryName()
									.toString().replace('/', '.'));

					if (fragment != null && fragment.exists()) {

						ICompilationUnit[] compilationUnits = fragment
								.getCompilationUnits();

						// set the diff
						progressMonitor.worked(directory
								.getSourceContentCount()
								- compilationUnits.length);

						for (ICompilationUnit iCompilationUnit : compilationUnits) {
							units.put(iCompilationUnit, directory);
						}
					}

				}
				/** do for all directoryFragments **/
			}
		}

		/******** STOP *******/

		for (ICompilationUnit iCompilationUnit : units.keySet()) {

			_parser.setSource(iCompilationUnit);

			_parser.setResolveBindings(true);
			_parser.setProject(_javaProject);

			CompilationUnit compilationUnit = (CompilationUnit) _parser
					.createAST(null);

			// analyze
			analyzeCompilationUnit(iCompilationUnit, compilationUnit,
					units.get(iCompilationUnit), rootMap, cache,
					fileBasedContent, problems);

			progressMonitor.worked(1);
		}

		//
		return problems;
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
			ICompilationUnit iCompilationUnit, CompilationUnit compilationUnit,
			IDirectory iDirectory, Map<String, String> rootMap,
			IResourceCache cache, IFileBasedContent fileBasedContent,
			List<IProblem> problems) throws CoreException {

		// step 3: compute the 'real' root (the one that was specified in the
		// bundlemaker project description)
		IPath parent = iCompilationUnit.getParent().getParent().getPath();
		parent = parent.removeFirstSegments(1);
		String root = parent.toPortableString();
		root = rootMap.get(root);

		// step 4: create the JavaElementIdentifier
		String name = iCompilationUnit.getElementName();
		name = name.substring(0, name.length() - ".java".length());

		JavaElementIdentifier elementID = new JavaElementIdentifier(iDirectory
				.getFileBasedContent().getId(), root, iDirectory
				.getDirectoryName().toString().replace('/', '.')
				+ "." + name, FileType.JAVA_SOURCE_FILE);

		// step 5: get the resource
		ResourceKey key = new ResourceKey(elementID.getContentId(),
				elementID.getRoot(), elementID.getPath());

		IModifiableResource resource = (IModifiableResource) cache
				.getOrCreateResource(key);

		// step 6: set the directly referenced types
		JdtAstVisitor visitor = new JdtAstVisitor(resource);
		compilationUnit.accept(visitor);

		// step 7:
		for (IJdtSourceParserHook sourceParserHook : _currentHooks) {

			//
			sourceParserHook.analyzeCompilationUnit(iCompilationUnit,
					compilationUnit);
		}

		// step 8: compute the indirectly referenced types
		Set<String> directlyAndIndirectlyReferencedTypes = _indirectlyReferencesAnalyzer
				.getAllReferencedTypes((IFile) iCompilationUnit
						.getCorrespondingResource());

		for (String type : directlyAndIndirectlyReferencedTypes) {

			resource.recordReference(type, new ReferenceAttributes(
					ReferenceType.TYPE_REFERENCE, false, false, false, false,
					false, false, true));

		}

		// step 9: add the errors to the error list
		for (IProblem problem : visitor.getProblems()) {

			// add errors
			if (problem.isError()) {
				problems.add(problem);
			}
		}

		// step 11: finally return
		return problems;
	}
}
