package org.bundlemaker.core.util;

import org.bundlemaker.core.internal.Activator;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ProgressMonitor extends NullProgressMonitor {

	private static final String MSG = "Processed %s/%s types (%s) [ %s sec, %s sec, %s sec] %s";

	/** - */
	private int _totalWork;

	/** - */
	private int _done = 0;

	/** - */
	private int _doneInProzent = 0;

	private StopWatch _stopWatch;

	/**
	 * @see org.eclipse.core.runtime.NullProgressMonitor#beginTask(java.lang.String,
	 *      int)
	 */
	public void beginTask(String name, int totalWork) {
		Assert.isNotNull(name);
		Assert.isTrue(totalWork > 0);

		_totalWork = totalWork;

		_stopWatch = new StopWatch();
		_stopWatch.start();
	}

	public void setTaskName(String name) {
		// System.err.println(name);
	}

	public void worked(int work) {
		_done = _done + work;

		if ((_done * 100) / _totalWork > _doneInProzent) {

			_doneInProzent = (_done * 100) / _totalWork;
			long elapsedTime = _stopWatch.getElapsedTime();
			long estimatedOverallTime = (elapsedTime / _doneInProzent * 100);
			long estimatedTimeLeft = estimatedOverallTime - elapsedTime;
			estimatedTimeLeft = estimatedTimeLeft > 0 ? estimatedTimeLeft : 0;

			System.err.println(String.format(MSG, _done, _totalWork,
					_doneInProzent, elapsedTime / 1000,
					estimatedOverallTime / 1000, estimatedTimeLeft / 1000,
					Activator.getDefault().getMemoryUsage()));
		}
	}

	@Override
	public void done() {
		_stopWatch.stop();
	}
}
