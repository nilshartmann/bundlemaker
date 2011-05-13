package org.bundlemaker.core.analysis.internal;

import java.util.List;

import org.bundlemaker.core.resource.IResource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public interface IResourceHolder {

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
  List<IResource> getAssociatedSourceResources();
}
