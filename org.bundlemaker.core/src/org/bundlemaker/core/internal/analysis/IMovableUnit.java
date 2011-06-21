package org.bundlemaker.core.internal.analysis;

import java.util.List;

import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public interface IMovableUnit {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  // TODO
  List<IType> getAssociatedTypes();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  List<IResource> getAssociatedBinaryResources();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IResource getAssociatedSourceResource();
}
