package org.bundlemaker.core.analysis;

import org.bundlemaker.core.modules.IModule;

/**
 * <p>
 * Defines an {@link IBundleMakerArtifact} that holds an {@link IModule} instance.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModuleArtifact extends IBundleMakerArtifact {

  /**
   * <p>
   * Returns the associated module.
   * </p>
   * 
   * @return the associated module.
   */
  IModule getAssociatedModule();

  /**
   * <p>
   * Returns the module name.
   * </p>
   * 
   * @return the name of the module.
   */
  String getModuleName();

  /**
   * <p>
   * Returns the module version.
   * </p>
   * 
   * @return the module version.
   */
  String getModuleVersion();

  /**
   * <p>
   * Sets the name and the version of this {@link IModuleArtifact}.
   * </p>
   * 
   * @param name
   *          the name
   * @param version
   *          the version
   */
  void setNameAndVersion(String name, String version);

  /**
   * <p>
   * Returns <code>true</code> if this {@link IModuleArtifact} represents a resource module, <code>false</code>
   * otherwise.
   * </p>
   * 
   * @return <code>true</code> if this {@link IModuleArtifact} represents a resource module, <code>false</code>
   *         otherwise.
   */
  boolean isResourceModule();
}
