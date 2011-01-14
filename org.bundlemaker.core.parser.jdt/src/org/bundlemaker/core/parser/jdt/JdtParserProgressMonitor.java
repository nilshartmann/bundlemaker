package org.bundlemaker.core.parser.jdt;

import org.eclipse.core.runtime.IProgressMonitor;

public class JdtParserProgressMonitor implements IProgressMonitor {

	private IProgressMonitor _progressMonitor;

	private int _ticks;

	private int _totalWork;

	private int _remainder;

	private double _factor;

	/**
	 * <p>
	 * Creates a new instance of type {@link JdtParserProgressMonitor}.
	 * </p>
	 * 
	 * @param progressMonitor
	 */
	public JdtParserProgressMonitor(IProgressMonitor progressMonitor, int ticks) {
		super();

		// TODO: assert

		_progressMonitor = progressMonitor;
		_ticks = ticks;
		_totalWork = _ticks;

		_factor = (double) _ticks / (double) _totalWork;
	}

	public JdtParserProgressMonitor(IProgressMonitor progressMonitor,
			int ticks, int totalWork) {
		super();

		// TODO: assert

		_progressMonitor = progressMonitor;
		_ticks = ticks;
		_totalWork = totalWork;

		_factor = (double) _ticks / (double) _totalWork;
	}

	public void beginTask(String name, int totalWork) {
		_totalWork = totalWork;
		_factor = (double) _ticks / (double) _totalWork;
	}

	public void done() {
	}

	public void internalWorked(double work) {
	}

	public boolean isCanceled() {
		return _progressMonitor.isCanceled();
	}

	public void setCanceled(boolean value) {
		_progressMonitor.setCanceled(value);
	}

	public void setTaskName(String name) {
	}

	public void subTask(String name) {
	}

	public void worked(int work) {
		_remainder = _remainder + work;

		double count = _factor * _remainder;

		if (count > 1) {
			_progressMonitor.worked((int) Math.floor(count));
			_remainder = (int) Math.floor((count - Math.floor(count))
					* (_totalWork / _ticks));
		}

	}
}
