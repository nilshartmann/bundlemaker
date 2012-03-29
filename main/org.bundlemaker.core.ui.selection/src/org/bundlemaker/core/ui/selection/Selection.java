package org.bundlemaker.core.ui.selection;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.ui.selection.internal.ArtifactSelectionService;
import org.bundlemaker.core.ui.selection.internal.DependencySelectionService;

/**
 * <p>
 * </p>
 * 
 * @author Nils Hartmann
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Selection {

  /** - */
  public static final String               MAIN_SELECTION_PROVIDER_ID                      = Selection.class
                                                                                               .getPackage().getName()
                                                                                               + ".MAIN_SELECTION_PROVIDER_ID";

  /** the id for the Project-Explorer Artifact selection provider */
  public static final String               PROJECT_EXPLORER_ARTIFACT_SELECTION_PROVIDER_ID = "org.bundlemaker.ui.navigator.selectionprovider";

  /** the id of the Eclipse project explorer */
  public static final String               PROJECT_EXPLORER_VIEW_ID                        = "org.eclipse.ui.navigator.ProjectExplorer";

  /** - */
  private static Selection                 _instance;

  /** - */
  private final ArtifactSelectionService   _artifactSelectionService;

  /**
   * The {@link IDependencySelectionService} that manages selections of {@link IDependency} objects in the IDE
   */
  private final DependencySelectionService _dependencySelectionService;

  /**
   * Returns the singleton instance of analysis.
   * 
   * <p>
   * There is exactly one Analysis instance per Eclipse instance
   * 
   * @return
   */
  public static Selection instance() {

    //
    if (_instance == null) {
      _instance = new Selection();
    }

    //
    return _instance;
  }

  /**
   * Use {@link #instance()} to retrieve the singleton instance of this class
   */
  private Selection() {

    // Create the ArtifactSelectionService
    _artifactSelectionService = new ArtifactSelectionService();

    // Create the DependencySelectionService
    _dependencySelectionService = new DependencySelectionService();
  }

  /**
   * Return the {@link IArtifactSelectionService} instance.
   * 
   * @return the {@link IArtifactSelectionService}. Never null
   */
  public IArtifactSelectionService getArtifactSelectionService() {
    return _artifactSelectionService;
  }

  /**
   * Returns the single {@link IDependencySelectionService} instance
   * 
   * @return {@link IDependencySelectionService}. Never null.
   */
  public IDependencySelectionService getDependencySelectionService() {
    return _dependencySelectionService;
  }
}
