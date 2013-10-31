package org.bundlemaker.core.project.internal.gson;

import java.lang.reflect.Type;

import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.bundlemaker.core.project.internal.BundleMakerProjectDescription;
import org.eclipse.core.runtime.Assert;

import com.google.gson.InstanceCreator;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerProjectDescriptionInstanceCreator implements InstanceCreator<BundleMakerProjectDescription> {

  /** - */
  private IProjectDescriptionAwareBundleMakerProject _bundleMakerProject;

  /**
   * <p>
   * Creates a new instance of type {@link BundleMakerProjectDescriptionInstanceCreator}.
   * </p>
   * 
   * @param bundleMakerProject
   */
  public BundleMakerProjectDescriptionInstanceCreator(IProjectDescriptionAwareBundleMakerProject bundleMakerProject) {
    Assert.isNotNull(bundleMakerProject);

    _bundleMakerProject = bundleMakerProject;
  }

  /**
   * {@inheritDoc}
   */
  public BundleMakerProjectDescription createInstance(Type type) {
    return new BundleMakerProjectDescription(_bundleMakerProject);
  }
}