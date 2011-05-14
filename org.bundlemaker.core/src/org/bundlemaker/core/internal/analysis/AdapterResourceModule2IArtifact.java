package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterResourceModule2IArtifact extends AdapterModule2IArtifact {

  /**
   * <p>
   * Creates a new instance of type {@link AdapterResourceModule2IArtifact}.
   * </p>
   */
  public AdapterResourceModule2IArtifact(IResourceModule resourceModule, IArtifact parent) {
    super(resourceModule, parent);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private IResourceModule getResourceModule() {
    return (IResourceModule) getModule();
  }
}
