package org.bundlemaker.core.internal.parser;

import java.util.Arrays;

import org.bundlemaker.core.common.Activator;
import org.bundlemaker.core.spi.store.IPersistentDependencyStoreFactory;
import org.osgi.util.tracker.ServiceTracker;

public class XYZService {

  /** - */
  private static XYZService _instance;

  /**
   * <p>
   * </p>
   */
  public static XYZService instance() {

    if (_instance == null) {
      _instance = new XYZService();
    }

    return _instance;
  }

  /** Bundles that need to be in ACTIVE state in order to make Dependency Store work correctly */
  public static final String[]  REQUIRED_ACTIVE_BUNDLES = new String[] {
                                                        "org.eclipse.equinox.ds",
                                                        "org.bundlemaker.com.db4o.osgi"
                                                        };

  /** the factory tracker */
  private ServiceTracker        _factoryTracker;

  /** - */
  private ParserFactoryRegistry _parserFactoryRegistry;

  public XYZService() {

    // create the factory tracker
    _factoryTracker = new ServiceTracker(Activator.getDefault().getContext(),
        IPersistentDependencyStoreFactory.class.getName(), null);
    _factoryTracker.open();

    //
    _parserFactoryRegistry = new ParserFactoryRegistry();

  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ParserFactoryRegistry getParserFactoryRegistry() {

    // lazy initialization to prevent alzheimer's workspace [https://bundlemaker.jira.com/browse/BM-246]
    if (!_parserFactoryRegistry.isInitalized()) {
      _parserFactoryRegistry.initialize();
    }

    // return the parser factory
    return _parserFactoryRegistry;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IPersistentDependencyStoreFactory getPersistentDependencyStoreFactory() {

    try {

      // Make sure required bundles are ACTIVE
      for (String bundleName : REQUIRED_ACTIVE_BUNDLES) {
        Activator.getDefault().activateBundleIfNeeded(bundleName);
      }

      //
      IPersistentDependencyStoreFactory result = (IPersistentDependencyStoreFactory) _factoryTracker
          .waitForService(5000);

      //
      if (result == null) {
        // TODO
        throw new RuntimeException(
            "No IPersistentDependencyStoreFactory available. Please make sure that the following bundles are active: "
                + Arrays.asList(REQUIRED_ACTIVE_BUNDLES));
      }

      //
      return result;

    } catch (InterruptedException e) {
      e.printStackTrace();
      return null;
    }
  }

}
