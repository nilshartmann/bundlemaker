package org.bundlemaker.core.ui.selection;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
  public static final String               MAIN_ARTIFACT_SELECTION_ID     = Selection.class.getPackage().getName()
                                                                              + ".MAIN_ARTIFACT_SELECTION_ID";

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

    // add MAIN_DEPENDENCY_SELECTION_ID to DETAIL_DEPENDENCY_SELECTION_ID forwarder
    _dependencySelectionService.addDependencySelectionListener(Selection.MAIN_DEPENDENCY_SELECTION_ID,
        new IDependencySelectionListener() {
          @Override
          public void dependencySelectionChanged(IDependencySelectionChangedEvent event) {
            _dependencySelectionService.setSelection(Selection.DETAIL_DEPENDENCY_SELECTION_ID, event.getProviderId(),
                getAllLeafDependencies(event.getSelectedDependencies()));
          }
        });
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
   * Returns the {@link IArtifactSelectionService} instance.
   * 
   * @return the {@link IArtifactSelectionService}. Never null.
   */
  public IRootArtifactSelectionService getRootArtifactSelectionService() {
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

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private static List<IDependency> getAllLeafDependencies(Collection<IDependency> dependencies) {

    //
    final List<IDependency> result = new LinkedList<IDependency>();

    for (IDependency dependency : dependencies) {
      for (IDependency leafDependency : dependency.getLeafDependencies()) {
        result.add(leafDependency);
      }
    }

    return result;
  }
}
