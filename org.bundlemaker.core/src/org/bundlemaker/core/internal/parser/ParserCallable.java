package org.bundlemaker.core.internal.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.model.projectdescription.IFileBasedContent;
import org.bundlemaker.core.parser.IDirectory;
import org.bundlemaker.core.parser.IParser;
import org.bundlemaker.core.parser.IResourceCache;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ParserCallable implements Callable<List<IProblem>> {

	/** the block size **/
	// TODO: MOVE
	private static final int BLOCKSIZE = 2000;

	private IFileBasedContent _content;

	/** the package fragments */
	private List<IDirectory> _directories;

	/** the list of all errors */
	private List<IProblem> _errors;

	/** - */
	private IParser _parser;

	/** - */
	private IResourceCache _resourceCache;

	/** - */
	private IProgressMonitor _progressMonitor;

	/**
	 * <p>
	 * Creates a new instance of type {@link ParserCallable}.
	 * </p>
	 * 
	 * @param content
	 * @param directories
	 * @param parser
	 * @param resourceCache
	 */
	public ParserCallable(IFileBasedContent content,
			List<IDirectory> directories, IParser parser,
			IResourceCache resourceCache, IProgressMonitor progressMonitor) {

		//
		Assert.isNotNull(content);
		Assert.isNotNull(directories);
		Assert.isNotNull(parser);
		Assert.isNotNull(resourceCache);

		//
		_content = content;

		// set the directories to parse
		_directories = directories;

		//
		_parser = parser;

		//
		_resourceCache = resourceCache;
		
		//
		_progressMonitor = progressMonitor;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<IProblem> call() throws Exception {

		//
		_errors = new LinkedList<IProblem>();

		// iterate
		for (int i = 0, resourceCount = 0, fromIndex = 0; i < _directories
				.size(); i++) {

			// class file count
			// TODO
			resourceCount = resourceCount
					+ _directories.get(i).getBinaryContentCount();

			if (resourceCount > BLOCKSIZE || i + 1 == _directories.size()) {

				// parse
				_parser.parse(_content, _directories.subList(fromIndex, i + 1),
						_resourceCache, _progressMonitor);

				// set index
				resourceCount = 0;
				fromIndex = i + 1;
			}
		}

		// return the errors
		return _errors;
	}
}
