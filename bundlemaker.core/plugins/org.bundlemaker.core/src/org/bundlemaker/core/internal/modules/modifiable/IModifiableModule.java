package org.bundlemaker.core.internal.modules.modifiable;

import java.util.Set;

import org.bundlemaker.core.internal.projectdescription.IResourceStandin;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleIdentifier;
import org.bundlemaker.core.resource.IMovableUnit;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModifiableModule extends IModule {

  /**
   * <p>
   * </p>
   * 
   * @param classification
   */
  void setClassification(IPath classification);

  void setModuleIdentifier(String name, String version);

  void setModuleIdentifier(IModuleIdentifier moduleIdentifier);

  void addAll(Set<IResourceStandin> resources, ProjectContentType binary);

  /**
   * <p>
   * </p>
   * 
   * @param movableUnit
   */
  void addMovableUnit(IMovableUnit movableUnit);

  /**
   * <p>
   * </p>
   * 
   * @param movableUnit
   */
  void removeMovableUnit(IMovableUnit movableUnit);
}
