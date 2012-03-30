package org.bundlemaker.core.analysis;

import org.bundlemaker.core.modules.IModule;

/**
 * <p>
 * Defines an {@link IArtifact} that holds an {@link IModule} instance.
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
   * </p>
   * 
   * @return
   */
  String getModuleName();

  /**
   * <p>
   * </p>
   * 
   * @return
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
   * </p>
   * 
   * @return
   */
  boolean isResourceModule();
}
