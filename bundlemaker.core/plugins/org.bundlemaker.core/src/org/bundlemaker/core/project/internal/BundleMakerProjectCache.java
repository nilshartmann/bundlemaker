package org.bundlemaker.core.project.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.parser.IParserAwareBundleMakerProject;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerProjectCache {

  private static BundleMakerProjectCache _instance;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static BundleMakerProjectCache instance() {

    //
    if (_instance == null) {
      _instance = new BundleMakerProjectCache();
    }

    //
    return _instance;
  }

  /** the project cache */
  private Map<IProject, IProjectDescriptionAwareBundleMakerProject> _projectCache;

  /**
   * <p>
   * </p>
   * 
   * @param project
   * @return
   */
  public IProjectDescriptionAwareBundleMakerProject getBundleMakerProject(IProject project) {
    return _projectCache.get(project);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Collection<? extends IProjectDescriptionAwareBundleMakerProject> getBundleMakerProjects() {

    //
    return Collections.unmodifiableCollection(_projectCache.values());
  }

  /**
   * <p>
   * </p>
   * 
   * @param project
   * @param bundleMakerProject
   */
  public void cacheBundleMakerProject(IProject project, IProjectDescriptionAwareBundleMakerProject bundleMakerProject) {
    _projectCache.put(project, bundleMakerProject.adaptAs(IParserAwareBundleMakerProject.class));
  }

  /**
   * <p>
   * </p>
   * 
   * @param project
   */
  public void removeCachedBundleMakerProject(IProject project) {
    _projectCache.remove(project);
  }

  /**
   * <p>
   * Creates a new instance of type {@link BundleMakerProjectCache}.
   * </p>
   * 
   */
  private BundleMakerProjectCache() {

    // create the maps and caches
    _projectCache = new HashMap<IProject, IProjectDescriptionAwareBundleMakerProject>();

    //
    ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {

      @Override
      public void resourceChanged(IResourceChangeEvent event) {
        if (event.getType() == IResourceChangeEvent.PRE_DELETE && event.getResource() instanceof IProject
            && _projectCache.containsKey(event.getResource())) {

          IProjectDescriptionAwareBundleMakerProject iBundleMakerProject = _projectCache.get(event.getResource());

          // notifies listeners and removes itself from the cache
          iBundleMakerProject.dispose();

        }
      }
    }, IResourceChangeEvent.PRE_DELETE);
  }
}
