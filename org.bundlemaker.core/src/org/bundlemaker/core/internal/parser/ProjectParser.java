package org.bundlemaker.core.internal.parser;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.FutureTask;

import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.internal.Activator;
import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.internal.projectdescription.FileBasedContent;
import org.bundlemaker.core.parser.IDirectory;
import org.bundlemaker.core.parser.IParser;
import org.bundlemaker.core.parser.IParser.ParserType;
import org.bundlemaker.core.parser.IParserFactory;
import org.bundlemaker.core.parser.IResourceCache;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.spi.store.IPersistentDependencyStore;
import org.bundlemaker.core.util.ProgressMonitor;
import org.bundlemaker.core.util.StopWatch;
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
	private static final int THREAD_COUNT = Runtime.getRuntime()
			.availableProcessors();

	/** the list of all errors */
	public List<IProblem> _errors;

	/** the bundle maker project */
	private BundleMakerProject _bundleMakerProject;

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
		progressMonitor = progressMonitor == null ? new NullProgressMonitor()
				: progressMonitor;

		// set up the parser
		setupParsers();

		// compute the artifact count
		// TODO
		progressMonitor.beginTask("parsing project: files to analyze ",
				computeArtifactCount());

		//
		notifyParseStart();

		// create the resource cache
		ResourceCache cache = new ResourceCache(
				(IPersistentDependencyStore) _bundleMakerProject
						.getDependencyStore(null));

		if (progressMonitor instanceof ProgressMonitor) {
			ProgressMonitor monitor = (ProgressMonitor) progressMonitor;
			monitor.setResourceCache(cache);
		}

		// iterate over the project content
		for (IFileBasedContent fileBasedContent : _bundleMakerProject
				.getProjectDescription().getFileBasedContent()) {

			progressMonitor.setTaskName(String.format("Parsing '%s'...",
					fileBasedContent.getName()));

			// parse the content
			parseContent(fileBasedContent, progressMonitor, cache);

			// reset the type cache
			cache.resetTypeCache();
		}

		// TODO
		System.out.println("Write to disc");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		cache.commit(new ProgressMonitor());
		cache.clear();

		// TODO
		stopWatch.stop();
		System.out.println("Done: " + stopWatch.getElapsedTime());

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
	private void parseContent(IFileBasedContent content,
			IProgressMonitor progressMonitor, IResourceCache cache)
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

		// create parser callables
		for (int i = 0; i < _parsers.length; i++) {

			//
			IParser[] parser4Thread = _parsers[i];

			ParserType parserType = parser4Thread[0].getParserType();

			if (matches(parserType, content)) {

				ProgressMonitorWrapper monitorWrapper = new ProgressMonitorWrapper(
						progressMonitor);

				// create the parser callables array
				ParserCallable[] parserCallables = new ParserCallable[THREAD_COUNT];

				// create the parser callables
				for (int threadIndex = 0; threadIndex < THREAD_COUNT; threadIndex++) {
					parserCallables[threadIndex] = new ParserCallable(content,
							packageFragmentsParts[threadIndex],
							parser4Thread[threadIndex], cache, monitorWrapper);
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

				// correct the tick count if necessary
				int[] count = getResourcesToParseCount(content,
						parser4Thread[0]);
				int ticksToAdd = (count[0] + count[1])
						- monitorWrapper.getWorked();
				if (ticksToAdd > 0) {
					progressMonitor.worked(ticksToAdd);
				}
			}
		}

	}

	private boolean matches(ParserType parserType, IFileBasedContent content) {
		return ((parserType.equals(ParserType.BINARY) || parserType
				.equals(ParserType.BINARY_AND_SOURCE)) && !content
				.getResourceContent().getBinaryResources().isEmpty())
				|| ((parserType.equals(ParserType.SOURCE) || parserType
						.equals(ParserType.BINARY_AND_SOURCE))
						&& !content.getResourceContent().getSourceResources()
								.isEmpty() && content.getResourceContent()
						.isAnalyzeSourceResources());
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
		IParser[][] parsers = new IParser[parserFactories.size()][THREAD_COUNT];
		_parsers = new IParser[parserFactories.size()][THREAD_COUNT];

		// ... setup
		for (int i = 0; i < parserFactories.size(); i++) {
			for (int j = 0; j < THREAD_COUNT; j++) {
				parsers[i][j] = parserFactories.get(i).createParser(
						_bundleMakerProject);
			}
		}

		// first the source parsers
		int position = 0;
		for (int i = 0; i < parserFactories.size(); i++) {
			if (!parsers[i][0].getParserType().equals(ParserType.BINARY)) {
				for (int j = 0; j < THREAD_COUNT; j++) {
					_parsers[position][j] = parsers[i][j];
				}
				position++;
			}
		}

		// then the binary parsers
		for (int i = 0; i < parserFactories.size(); i++) {
			if (parsers[i][0].getParserType().equals(ParserType.BINARY)) {
				for (int j = 0; j < THREAD_COUNT; j++) {
					_parsers[position][j] = parsers[i][j];
				}
				position++;
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
		int binaryResourcesToParse = 0;
		int sourceResourcesToParse = 0;

		for (IFileBasedContent fileBasedContent : _bundleMakerProject
				.getProjectDescription().getFileBasedContent()) {

			int[] resourcesToParse = countResourcesToParse(fileBasedContent);

			binaryResourcesToParse += resourcesToParse[0];
			sourceResourcesToParse += resourcesToParse[1];
		}

		//
		return binaryResourcesToParse + sourceResourcesToParse;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 */
	private int[] countResourcesToParse(IFileBasedContent content) {

		//
		int binaryResourcesToParse = 0;
		int sourceResourcesToParse = 0;

		if (content.isResourceContent()) {

			//
			for (IParser[] parsers4Thread : _parsers) {

				int[] count = getResourcesToParseCount(content,
						parsers4Thread[0]);

				binaryResourcesToParse += count[0];
				sourceResourcesToParse += count[1];
			}
		}

		return new int[] { binaryResourcesToParse, sourceResourcesToParse };
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param content
	 * @param parser
	 * 
	 * @return
	 */
	private int[] getResourcesToParseCount(IFileBasedContent content,
			IParser parser) {

		//
		if (parser.getParserType().equals(ParserType.BINARY)) {

			return new int[] {
					content.getResourceContent().getBinaryResources().size(), 0 };

		} else if (parser.getParserType().equals(ParserType.SOURCE)) {

			return new int[] { 0,
					content.getResourceContent().getSourceResources().size() };

		} else if (parser.getParserType().equals(ParserType.BINARY_AND_SOURCE)) {

			return new int[] {
					content.getResourceContent().getBinaryResources().size(),
					content.getResourceContent().getSourceResources().size() };
		}

		return new int[] { 0, 0 };
	}
}
