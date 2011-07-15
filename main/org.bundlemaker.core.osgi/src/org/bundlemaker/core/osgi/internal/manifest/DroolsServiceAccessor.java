package org.bundlemaker.core.osgi.internal.manifest;

import org.bundlemaker.core.osgi.Activator;
import org.drools.KnowledgeBaseFactoryService;
import org.drools.builder.KnowledgeBuilderFactoryService;
import org.drools.io.ResourceFactoryService;
import org.eclipse.core.runtime.Assert;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DroolsServiceAccessor {

  /** - */
  private BundleContext                  _bundleContext;

  //
  private KnowledgeBaseFactoryService    _knowledgeBaseFactoryService;

  //
  private ResourceFactoryService         _resourceFactoryService;

  //
  private KnowledgeBuilderFactoryService _knowledgeBuilderFactoryService;

  private ServiceTracker                 _tracker_KnowledgeBaseFactoryService;

  private ServiceTracker                 _tracker_KnowledgeBuilderFactoryService;

  private ServiceTracker                 _tracker_ResourceFactoryService;

  /**
   * <p>
   * Creates a new instance of type {@link DroolsServiceAccessor}.
   * </p>
   * 
   * @param bundleContext
   */
  public DroolsServiceAccessor() {

    // the bundle context
    _bundleContext = Activator.getContext();
    
    //
    Assert.isNotNull(_bundleContext);

    // the service tracker
    _tracker_KnowledgeBaseFactoryService = new ServiceTracker(_bundleContext,
        KnowledgeBaseFactoryService.class.getName(), null);
    _tracker_KnowledgeBaseFactoryService.open();

    //
    _tracker_ResourceFactoryService = new ServiceTracker(_bundleContext,
        ResourceFactoryService.class.getName(), null);
    _tracker_ResourceFactoryService.open();

    //
    _tracker_KnowledgeBuilderFactoryService = new ServiceTracker(_bundleContext,
        KnowledgeBuilderFactoryService.class.getName(), null);
    _tracker_KnowledgeBuilderFactoryService.open();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final KnowledgeBaseFactoryService getKnowledgeBaseFactoryService() {

    if (_knowledgeBaseFactoryService == null) {
      try {
        _knowledgeBaseFactoryService = (KnowledgeBaseFactoryService) _tracker_KnowledgeBaseFactoryService.waitForService(5000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    return _knowledgeBaseFactoryService;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final ResourceFactoryService getResourceFactoryService() {

    if (_resourceFactoryService == null) {
      try {
        _resourceFactoryService = (ResourceFactoryService) _tracker_ResourceFactoryService.waitForService(5000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    return _resourceFactoryService;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final KnowledgeBuilderFactoryService getKnowledgeBuilderFactoryService() {

    if (_knowledgeBuilderFactoryService == null) {
      try {
        _knowledgeBuilderFactoryService = (KnowledgeBuilderFactoryService) _tracker_KnowledgeBuilderFactoryService
        .waitForService(5000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    return _knowledgeBuilderFactoryService;
  }
}
