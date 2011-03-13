package org.bundlemaker.core.internal.parser;

import org.eclipse.core.runtime.IProgressMonitor;

public class ProgressMonitorWrapper implements IProgressMonitor {

  /** - */
  private IProgressMonitor _progressMonitor;

  /** - */
  private int              _worked;

  /**
   * <p>
   * Creates a new instance of type {@link ProgressMonitorWrapper}.
   * </p>
   * 
   * @param progressMonitor
   */
  public ProgressMonitorWrapper(IProgressMonitor progressMonitor) {
    super();

    _progressMonitor = progressMonitor;
    _worked = 0;
  }

  public void beginTask(String name, int totalWork) {
    _progressMonitor.beginTask(name, totalWork);
  }

  public void done() {
    _progressMonitor.done();
  }

  public void internalWorked(double work) {
    _progressMonitor.internalWorked(work);
  }

  public boolean isCanceled() {
    return _progressMonitor.isCanceled();
  }

  public void setCanceled(boolean value) {
    _progressMonitor.setCanceled(value);
  }

  public void setTaskName(String name) {
    _progressMonitor.setTaskName(name);
  }

  public void subTask(String name) {
    _progressMonitor.subTask(name);
  }

  public void worked(int work) {
    //
    _worked += work;
    _progressMonitor.worked(work);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public int getWorked() {
    return _worked;
  }
}
