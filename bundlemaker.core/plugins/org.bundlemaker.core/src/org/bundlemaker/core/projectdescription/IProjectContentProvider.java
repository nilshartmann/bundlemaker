package org.bundlemaker.core.projectdescription;

import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.spi.AbstractProjectContentProvider;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * <p>
 * Note: Implementations of this class must be subclasses of {@link AbstractProjectContentProvider}.
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
  List<IProjectContentEntry> getBundleMakerProjectContent(
      IProgressMonitor progressMonitor,
      IBundleMakerProject bundleMakerProject) throws CoreException;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  List<IProjectContentProblem> getProblems();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean hasProblems();
}
