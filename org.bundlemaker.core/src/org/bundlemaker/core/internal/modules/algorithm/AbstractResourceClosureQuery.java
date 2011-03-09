package org.bundlemaker.core.internal.modules.algorithm;

import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.internal.modules.ModularizedSystem;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 */
public abstract class AbstractResourceClosureQuery {

  /** - */
  private Set<IResource>    _resources;

  /** - */
  private ModularizedSystem _modularizedSystem;

  /**
   * <p>
   * </p>
   */
  public AbstractResourceClosureQuery(ModularizedSystem modularizedSystem) {

    Assert.isNotNull(modularizedSystem);

    _modularizedSystem = modularizedSystem;

    _resources = new HashSet<IResource>();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected ModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Set<IResource> getResources() {
    return _resources;
  }
}
