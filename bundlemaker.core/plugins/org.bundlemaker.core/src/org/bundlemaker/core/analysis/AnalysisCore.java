package org.bundlemaker.core.analysis;

import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.eclipse.core.runtime.IProgressMonitor;

public class AnalysisCore {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static IRootArtifact getAnalysisModel(IModularizedSystem modularizedSystem,
      IAnalysisModelConfiguration configuration) {
    return ((ModularizedSystem) modularizedSystem).getAnalysisModel(configuration);
  }

  /**
   * <p>
   * </p>
   * 
   * @param configuration
   * @param progressMonitor
   * @return
   */
  public static IRootArtifact getAnalysisModel(IModularizedSystem modularizedSystem,
      IAnalysisModelConfiguration configuration,
      IProgressMonitor progressMonitor) {
    return ((ModularizedSystem) modularizedSystem).getAnalysisModel(configuration, progressMonitor);
  }
}
