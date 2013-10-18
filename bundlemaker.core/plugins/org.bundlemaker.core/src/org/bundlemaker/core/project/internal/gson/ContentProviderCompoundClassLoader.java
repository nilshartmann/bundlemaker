package org.bundlemaker.core.project.internal.gson;

import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.common.classloading.BundleDelegatingClassLoader;
import org.bundlemaker.core.common.classloading.CompoundClassLoader;
import org.bundlemaker.core.project.internal.ProjectContentProviderExtension;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ContentProviderCompoundClassLoader {

  /** - */
  private CompoundClassLoader                          _compoundClassLoader;

  /** - */
  private Map<String, ProjectContentProviderExtension> _idToExtensionMap;

  /** - */
  private Map<String, String>                          _classnameToIdMap;

  /**
   * <p>
   * Creates a new instance of type {@link ContentProviderCompoundClassLoader}.
   * </p>
   * 
   * @throws CoreException
   */
  public ContentProviderCompoundClassLoader() throws CoreException {

    //
    _compoundClassLoader = new CompoundClassLoader();

    // put self
    Bundle bundle = Platform.getBundle("org.bundlemaker.core");
    _compoundClassLoader.add(new BundleDelegatingClassLoader(bundle));

    //
    _idToExtensionMap = ProjectContentProviderExtension
        .getAllProjectContentProviderExtension();

    //
    for (ProjectContentProviderExtension extension : _idToExtensionMap.values()) {
      bundle = Platform.getBundle(extension.getExtension().getContributor().getName());
      _compoundClassLoader.add(new BundleDelegatingClassLoader(bundle));
    }

    //
    _classnameToIdMap = new HashMap<String, String>();
    for (ProjectContentProviderExtension extension : _idToExtensionMap.values()) {
      _classnameToIdMap.put(extension.getClassName(), extension.getId());
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public CompoundClassLoader getCompoundClassLoader() {
    return _compoundClassLoader;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Map<String, ProjectContentProviderExtension> getIdToExtensionMap() {
    return _idToExtensionMap;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Map<String, String> getClassnameToIdMap() {
    return _classnameToIdMap;
  }
}
