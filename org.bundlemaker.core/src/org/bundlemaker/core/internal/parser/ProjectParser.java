package org.bundlemaker.core.internal.parser;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.FutureTask;

import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.internal.Activator;
import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.model.projectdescription.IFileBasedContent;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent;
import org.bundlemaker.core.parser.IDirectory;
import org.bundlemaker.core.parser.IParser;
import org.bundlemaker.core.parser.IParserFactory;
import org.bundlemaker.core.resource.StringCache;
import org.bundlemaker.core.store.IPersistentDependencyStore;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ProjectParser {

	/** THREAD_COUNT */
	private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

	/** the list of all errors */
	public List<IProblem> _errors;

	/** the bundle maker project */
	private BundleMakerProject _bundleMakerProject;

	/** the progress monitor */
	private IProgressMonitor _progressMonitor;

	/** the parser array: the first index is the parser, the second the thread */
	private IParser[][] _parsers;

	/**
	 * <p>
	 * Creates a new instance of type {@link ProjectParser}.
	 * </p>
	 * 
	 * @param bundleMakerProject
	 *            the bundle maker project
	 */
	public ProjectParser(BundleMakerProject bundleMakerProject) {
		Assert.isNotNull(bundleMakerProject);

		// set the project
		_bundleMakerProject = bundleMakerProject;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param progressMonitor
	 * @return
	 * @throws CoreException
	 */
	public final List<IProblem> parseBundleMakerProject(
			IProgressMonitor progressMonitor) throws CoreException {

		// reset the store
		Activator.getDefault().getPersistentInfoStoreFactory()
				.resetPersistentDependencyStore(_bundleMakerProject);

		// create the error list
		_errors = new LinkedList<IProblem>();

		// set the progress monitor
		_progressMonitor = progressMonitor == null ? new NullProgressMonitor()
				: progressMonitor;

		// compute the artifact count
		// TODO
		int count = computeArtifactCount();
		_progressMonitor.beginTask("parsing project: files to analyze ", count);

		// set up the parser
		setupParsers();

		//
		notifyParseStart();

		// create the string cache
		StringCache stringCache = new StringCache();

		// iterate over the project content
		for (ModifiableFileBasedContent fileBasedContent : _bundleMakerProject
				.getProjectDescription().getModifiableFileBasedContent()) {

			// parse the content
			parseContent(fileBasedContent, stringCache);
		}

		//
		notifyParseStop();

		// return the result
		return _errors;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param content
	 * @throws CoreException
	 */
	@SuppressWarnings("unchecked")
	private void parseContent(IFileBasedContent content, StringCache stringCache)
			throws CoreException {

		// return if content is no resource content
		if (!content.isResourceContent()) {
			return;
		}

		//
		List<IDirectory> allDirectories = FileContentReader.getDirectories(
				content, true);

		// compute the part size
		float partSizeAsFloat = allDirectories.size() / (float) THREAD_COUNT;
		int partSize = (int) Math.ceil(partSizeAsFloat);

		// split the package list in n sublist (one for each thread)
		List<IDirectory>[] packageFragmentsParts = new List[THREAD_COUNT];
		for (int i = 0; i < THREAD_COUNT; i++) {
			if ((i + 1) * partSize <= allDirectories.size()) {
				packageFragmentsParts[i] = allDirectories.subList(i * partSize,
						(i + 1) * partSize);
			} else if ((i) * partSize <= allDirectories.size()) {
				packageFragmentsParts[i] = allDirectories.subList(i * partSize,
						allDirectories.size());
			} else {
				packageFragmentsParts[i] = Collections.EMPTY_LIST;
			}
		}

		// create the resource cache
		ModifiableResourceCache cache = new ModifiableResourceCache(
				(IPersistentDependencyStore) _bundleMakerProject
						.getDependencyStore(null),
				stringCache);

		// create parser callables
		for (int i = 0; i < _parsers.length; i++) {

			//
			IParser[] parser4Thread = _parsers[i];

			// create the parser callables array
			ParserCallable[] parserCallables = new ParserCallable[THREAD_COUNT];

			// create the parser callables
			for (int threadIndex = 0; threadIndex < THREAD_COUNT; threadIndex++) {
				parserCallables[threadIndex] = new ParserCallable(content,
						packageFragmentsParts[threadIndex],
						parser4Thread[threadIndex], cache);
			}

			// execute the callables
			FutureTask<List<IProblem>>[] futureTasks = new FutureTask[THREAD_COUNT];
			for (int threadIndex = 0; threadIndex < THREAD_COUNT; threadIndex++) {
				futureTasks[threadIndex] = new FutureTask<List<IProblem>>(
						parserCallables[threadIndex]);
				new Thread(futureTasks[threadIndex]).start();
			}

			// collect the result
			// TODO
			try {
				for (int threadIndex = 0; threadIndex < THREAD_COUNT; threadIndex++) {
					List<IProblem> errors = futureTasks[threadIndex].get();
					_errors.addAll(errors);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		//
		cache.commit();
		cache.clear();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws CoreException
	 */
	private void notifyParseStart() throws CoreException {

		//
		for (IParser[] parsers : _parsers) {
			for (IParser parser : parsers) {

				// notify 'start'
				parser.parseBundleMakerProjectStart(_bundleMakerProject);
			}
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws CoreException
	 */
	private void notifyParseStop() throws CoreException {

		//
		for (IParser[] parsers : _parsers) {
			for (IParser parser : parsers) {

				// notify 'stop'
				parser.parseBundleMakerProjectStop(_bundleMakerProject);
			}
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws CoreException
	 */
	private void setupParsers() throws CoreException {

		// get the registered parser factories
		List<IParserFactory> parserFactories = Activator.getDefault()
				.getParserFactoryRegistry().getParserFactories();

		// no parsers defined
		if (parserFactories.isEmpty()) {
			throw new RuntimeException("No parserFactories defined...");
		}

		// create one parser for each thread...
		_parsers = new IParser[parserFactories.size()][THREAD_COUNT];

		// ... setup
		for (int i = 0; i < parserFactories.size(); i++) {
			for (int j = 0; j < THREAD_COUNT; j++) {
				_parsers[i][j] = parserFactories.get(i).createParser(
						_bundleMakerProject);
			}
		}
	}

	/**
	 * <p>
	 * Helper method.
	 * </p>
	 * 
	 * @return
	 */
	private int computeArtifactCount() {

		//
		int count = 0;

		for (IFileBasedContent fileBasedContent : _bundleMakerProject
				.getProjectDescription().getFileBasedContent()) {

			if (fileBasedContent.isResourceContent()) {

				count += fileBasedContent.getResourceContent()
						.getBinaryResources().size()
						+ fileBasedContent.getResourceContent()
								.getSourceResources().size();
			}
		}

		//
		return count;
	}
}
