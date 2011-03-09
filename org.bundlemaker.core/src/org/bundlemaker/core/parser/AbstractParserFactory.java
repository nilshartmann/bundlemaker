package org.bundlemaker.core.parser;

import org.bundlemaker.core.IBundleMakerProject;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractParserFactory implements IParserFactory {

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(IBundleMakerProject bundleMakerProject) throws CoreException {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInitialized(IBundleMakerProject bundleMakerProject) {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose(IBundleMakerProject bundleMakerProject) {
  }
}
