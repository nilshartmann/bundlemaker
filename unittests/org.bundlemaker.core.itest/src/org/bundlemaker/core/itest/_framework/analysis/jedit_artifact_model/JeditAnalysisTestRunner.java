package org.bundlemaker.core.itest._framework.analysis.jedit_artifact_model;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JeditAnalysisTestRunner extends BlockJUnit4ClassRunner {

  /**
   * <p>
   * Creates a new instance of type {@link JeditAnalysisTestRunner}.
   * </p>
   *
   * @param klass
   * @throws InitializationError
   */
  public JeditAnalysisTestRunner(Class<?> klass) throws InitializationError {
    super(klass);
  }
  
  
}
