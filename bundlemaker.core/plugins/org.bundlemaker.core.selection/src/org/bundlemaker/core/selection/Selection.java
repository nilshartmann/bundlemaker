package org.bundlemaker.core.selection;

import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.selection.internal.ArtifactSelection;
import org.bundlemaker.core.selection.internal.ArtifactSelectionService;
import org.bundlemaker.core.selection.internal.DependencySelection;
import org.bundlemaker.core.selection.internal.DependencySelectionService;
import org.bundlemaker.core.selection.internal.stage.ArtifactStage;
import org.bundlemaker.core.selection.stage.IArtifactStage;

/**
 * <p>
 * </p>
 * 
 * @author Nils Hartmann
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Selection {

  /**
   * The (unchanged) selection currently made in the Project Explorer Tree
   * 
   * Clients should listen to {@link #ARTIFACT_STAGE_SELECTION_ID} instead.
   */
  public static final String               PROJECT_EXLPORER_SELECTION_ID  = Selection.class.getPackage().getName()
                                                                              + ".PROJECT_EXPLORER_SELECTION_ID";

  /** - */
  public static final String               ARTIFACT_STAGE_SELECTION_ID    = Selection.class.getPackage().getName()
                                                                              + ".ARTIFACT_STAGE_SELECTION_ID";

  /** - */
  public static final String               MAIN_DEPENDENCY_SELECTION_ID   = Selection.class.getPackage().getName()
                                                                              + ".MAIN_DEPENDENCY_SELECTION_ID";

  /** - */
  public static final String               DETAIL_DEPENDENCY_SELECTION_ID = Selection.class.getPackage().getName()
                                                                              + ".DETAIL_DEPENDENCY_SELECTION_ID";

  /** the id of the Eclipse project explorer */
  public static final String               PROJECT_EXPLORER_VIEW_ID       = "org.eclipse.ui.navigator.ProjectExplorer";

  /** - */
  private static Selection                 _instance;

  /** - */
  private final ArtifactSelectionService   _artifactSelectionService;

  /**
   * The {@link IDependencySelectionService} that manages selections of {@link IDependency} objects in the IDE
   */
  private final DependencySelectionService _dependencySelectionService;

  /** - */
  private final ArtifactStage              _artifactStage;

  /**
   * <p>
   * </p>
   * 
   * @param selectionId
   * @param providerId
   * @return
   */
  public static IArtifactSelection emptyArtifactSelection(String selectionId, String providerId) {
    return new ArtifactSelection(selectionId, providerId);
  }

  /**
   * <p>
   * </p>
   * 
   * @param selectionId
   * @param providerId
   * @return
   */
  public static IDependencySelection emptyDependencySelection(String selectionId, String providerId) {
    return new DependencySelection(selectionId, providerId);
  }

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

    // Create the artifact stage
    _artifactStage = new ArtifactStage();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IArtifactStage getArtifactStage() {
    _artifactStage.init();
    return _artifactStage;
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
