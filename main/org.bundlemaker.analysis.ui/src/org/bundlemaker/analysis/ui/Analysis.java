package org.bundlemaker.analysis.ui;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.ui.internal.Activator;
import org.bundlemaker.analysis.ui.internal.AnalysisContext;
import org.eclipse.swt.graphics.Image;

/**
 * Common functionality used by BundleMaker analysis features
 * 
 * @author Nils Hartmann
 * 
 */
public class Analysis {

  private static Analysis        _instance;

  private final IAnalysisContext _analysisContext;

  /**
   * Returns the singleton instnace of analysis.
   * 
   * <p>
   * There is exactly one Analysis instance per Eclispe instance
   * 
   * @return
   */
  public static Analysis instance() {
    if (_instance == null) {
      _instance = new Analysis();
    }

    return _instance;
  }

  /**
   * Use {@link #instance()} to retrieve the singleton instance of this class
   */
  private Analysis() {
    _analysisContext = new AnalysisContext();
  }

  /**
   * Returns the {@link IAnalysisContext} instance
   * 
   * @return
   */
  public IAnalysisContext getContext() {
    return _analysisContext;
  }

  /**
   * Returns the Icon-Image that should be used for the given artifact.
   * 
   * <p>
   * If no artifact is given, <tt>null</tt> is returned.
   * 
   * @param artifact
   * @return
   */
  public Image getIconForArtifact(IArtifact artifact) {
    if (artifact == null) {
      return null;
    }

    return Activator.getDefault().getIcon(artifact.getType().getKuerzel());
  }

}
