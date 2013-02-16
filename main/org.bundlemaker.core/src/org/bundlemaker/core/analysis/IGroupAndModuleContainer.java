package org.bundlemaker.core.analysis;

import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Defines the method to create new modules or groups for all {@link IBundleMakerArtifact IBundleMakerArtifacts} that
 * can contain {@link IModuleArtifact IModuleArtifacts} or {@link IGroupArtifact IGroupArtifacts}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface IGroupAndModuleContainer extends IBundleMakerArtifact {

  /**
   * <p>
   * Creates a new group with the given path. If a group with the given path already exists, the existing group will be
   * returned.
   * </p>
   * 
   * @param path
   *          the path of the group
   * @return the {@link IGroupArtifact} with the specified path
   */
  IGroupArtifact getOrCreateGroup(String path);

  /**
   * <p>
   * Creates a new group with the given path. If a group with the given path already exists, the existing group will be
   * returned.
   * </p>
   * 
   * @param path
   *          the path of the group
   * @return the {@link IGroupArtifact} with the specified path
   */
  IGroupArtifact getOrCreateGroup(IPath path);

  /**
   * <p>
   * </p>
   * Given this is a RootArtifact
   * <ul>
   * <li>...when qualifiedModuleName contains "/" last segment is interpreted as Module name, segments before as GROUP
   * names</li>
   * <li>...when qualifiedModuleName starts with "/": see above</li>
   * <li>...when qualifiedModuleName does not contain any "/", the whole qMN is interpreted as Module name</li>
   * </ul>
   * 
   * <ul>
   * Given this is a GroupArtifact
   * <li>...when qualifiedModuleName contains "/" last segment is interpreted as Module name, segments before as GROUP
   * names. Group path starts with this group (relative path)</li>
   * <li>...when qualifedModuleName starts "/"last segment is interpreted as Module name, segments before as GROUP
   * names. Group path starts from root container (absolute path)</li>
   * <li>...when qualifiedModuleName does not contain any "/", the whole qMN is interpreted as Module name. The module
   * is created in this Group</li>
   * </ul>
   * 
   * @param qualifiedModuleName
   * @param moduleVersion
   * @return the {@link IModuleArtifact} with the specified name and version
   */
  IModuleArtifact getOrCreateModule(String qualifiedModuleName, String moduleVersion);
}
