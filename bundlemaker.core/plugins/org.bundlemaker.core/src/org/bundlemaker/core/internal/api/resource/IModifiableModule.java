package org.bundlemaker.core.internal.api.resource;

import java.util.Set;

import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleIdentifier;
import org.bundlemaker.core.resource.IModuleResource;
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

  void addAll(Set<IModuleResource> resources, ResourceType binary);

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
