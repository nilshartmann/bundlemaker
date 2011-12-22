package org.bundlemaker.core.projectdescription;

import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IProjectContentProvider {

  /**
   * <p>
   * Sets the identifier.
   * </p>
   * 
   * @param identifier
   */
  void setId(String identifier);

  /**
   * <p>
   * Returns the internal identifier of this content entry provider.
   * </p>
   * 
   * @return the internal identifier of this content entry provider.
   */
  String getId();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  List<IProjectContentEntry> getBundleMakerProjectContent(IBundleMakerProject bundleMakerProject) throws CoreException;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Object getConfiguration();

  /**
   * <p>
   * </p>
   * 
   * @param configuration
   */
  void setConfiguration(Object configuration);
}
