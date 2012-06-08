package org.bundlemaker.core.analysis;

import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IGroupAndModuleContainer extends IBundleMakerArtifact {

  /**
   * <p>
   * </p>
   * 
   * @param path
   * @return
   */
  IGroupArtifact getOrCreateGroup(IPath path);

  /**
   * <p>
   * </p>
   * 
   * @param path
   * @return
   */
  IGroupArtifact getOrCreateGroup(String path);

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
   * Give this is a GroupArtifact
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
   * @return
   */
  IModuleArtifact getOrCreateModule(String qualifiedModuleName, String moduleVersion);
}
