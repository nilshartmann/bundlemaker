package org.bundlemaker.core.itest.core;

import org.bundlemaker.core.itest._framework.AbstractModularizedSystemTest;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractJeditTest extends AbstractModularizedSystemTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return "jedit";
  }
}
