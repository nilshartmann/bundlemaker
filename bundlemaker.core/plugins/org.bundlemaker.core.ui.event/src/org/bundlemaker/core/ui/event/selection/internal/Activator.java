package org.bundlemaker.core.ui.event.selection.internal;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.selection.IDependencySelectionChangedEvent;
import org.bundlemaker.core.selection.IDependencySelectionListener;
import org.bundlemaker.core.selection.Selection;
import org.bundlemaker.core.ui.event.Events;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {

  // The shared instance
  private static Activator             plugin;

  /** - */
  private IDependencySelectionListener _mainDependencySelectionToDetailDependencySelectionForwarder;

  // /** - */
  // private IArtifactSelectionListener _mainDependencySelectionCleaner;

  private Events                       _events;

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(final BundleContext context) throws Exception {
    super.start(context);
    plugin = this;

    this._events = new Events() {

      /*
       * (non-Javadoc)
       * 
       * @see org.bundlemaker.core.ui.event.Events#getBundleContext()
       */
      @Override
      protected BundleContext getBundleContext() {
        return context;
      }

    };

    //
    _mainDependencySelectionToDetailDependencySelectionForwarder = new IDependencySelectionListener() {
      @Override
      public void dependencySelectionChanged(IDependencySelectionChangedEvent event) {
        Selection
            .instance()
            .getDependencySelectionService()
            .setSelection(Selection.DETAIL_DEPENDENCY_SELECTION_ID, event.getProviderId(),
                getAllLeafDependencies(event.getSelectedDependencies()));
      }
    };

    Selection
        .instance()
        .getDependencySelectionService()
        .addDependencySelectionListener(Selection.MAIN_DEPENDENCY_SELECTION_ID,
            _mainDependencySelectionToDetailDependencySelectionForwarder);

    // //
    // _mainDependencySelectionCleaner = new IArtifactSelectionListener() {
    // @Override
    // public void artifactSelectionChanged(IArtifactSelectionChangedEvent event) {
    // Collection<IDependency> EMPTY_DEPENDENCIES = Collections.emptyList();
    // Selection.instance().getDependencySelectionService()
    // .setSelection(Selection.MAIN_DEPENDENCY_SELECTION_ID, event.getProviderId(), EMPTY_DEPENDENCIES);
    // }
    // };
    //
    // Selection.instance().getArtifactSelectionService()
    // .addArtifactSelectionListener(Selection.MAIN_ARTIFACT_SELECTION_ID, _mainDependencySelectionCleaner);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    plugin = null;
    this._events = null;
    super.stop(context);

    Selection.instance().getDependencySelectionService()
        .removeDependencySelectionListener(_mainDependencySelectionToDetailDependencySelectionForwarder);
    // Selection.instance().getArtifactSelectionService().removeArtifactSelectionListener(_mainDependencySelectionCleaner);
  }

  /**
   * Returns the shared instance
   * 
   * @return the shared instance
   */
  public static Activator getDefault() {
    return plugin;
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
      for (IDependency leafDependency : dependency.getCoreDependencies()) {
        result.add(leafDependency);
      }
    }

    return result;
  }

  /**
   * @return
   */
  public Events getEvents() {
    return this._events;
  }
}
