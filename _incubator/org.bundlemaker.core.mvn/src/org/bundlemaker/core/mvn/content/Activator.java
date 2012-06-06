package org.bundlemaker.core.mvn.content;

import java.io.File;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Activator implements BundleActivator {

  /** - */
  private static BundleContext   _bundleContext;

  /** - */
  private static MvnRepositories _mvnRepositories;

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(BundleContext context) throws Exception {

    //
    _bundleContext = context;

    //
    _mvnRepositories = new MvnRepositories();

    // TODO
    _mvnRepositories.setMvnRepositories(new File("D:/development/_save/o/Repository"), "http://repo1.maven.org/maven2/");

    //
    _bundleContext.registerService(IMvnRepositories.class, _mvnRepositories, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    _bundleContext = null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static BundleContext getBundleContext() {
    return _bundleContext;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static IMvnRepositories getMvnRepositories() {

    //
    if (_bundleContext == null || _mvnRepositories == null) {
      throw new RuntimeException("Not started yet!");
    }

    //
    return _mvnRepositories;
  }
}
