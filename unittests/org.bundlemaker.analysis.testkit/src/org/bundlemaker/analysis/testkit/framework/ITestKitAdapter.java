package org.bundlemaker.analysis.testkit.framework;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ITestKitAdapter {

  /**
   * <p>
   * </p>
   */
  void init() throws Exception;

  /**
   * <p>
   * </p>
   */
  void dispose() throws Exception;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IDependencyModel getDependencyModel();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IArtifact getRoot();
}
