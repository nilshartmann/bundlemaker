package org.bundlemaker.core.internal.modules.modifiable;

import java.util.List;

import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.IMovableUnit;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.IResource;
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

  void addAll(List<? extends IResource> binaryResources, ProjectContentType binary);

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
