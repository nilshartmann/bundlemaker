package org.bundlemaker.core.mvn.internal;

import org.apache.maven.settings.Settings;
import org.bundlemaker.core.mvn.internal.config.AetherUtils;
import org.bundlemaker.core.mvn.internal.config.DispatchingRepositoryAdapter;
import org.bundlemaker.core.mvn.internal.config.IAetherRepositoryAdapter;
import org.bundlemaker.core.mvn.internal.config.MvnSettingsBasedRepositoryAdapter;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.sonatype.aether.RepositorySystemSession;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnCoreActivator implements BundleActivator {

  /** - */
  private static MvnCoreActivator _instance;

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(BundleContext context) throws Exception {

//    System.out.println("*** PLING ***");
//
//    // use M2E settings
//    IAetherRepositoryAdapter resolver = new DispatchingRepositoryAdapter();
//
//    RepositorySystemSession session = resolver.newSession();
//    System.out.println(session.getLocalRepository());
//
//    System.out.println("*** PLONG ***");

    //
    _instance = this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    _instance = null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static MvnCoreActivator getDefault() {
    return _instance;
  }
}
