package org.bundlemaker.core.internal.parser;

import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public interface LoggableAction<T> {

  /**
   * <p>
   * </p>
   * 
   */
  public T execute() throws CoreException;
}