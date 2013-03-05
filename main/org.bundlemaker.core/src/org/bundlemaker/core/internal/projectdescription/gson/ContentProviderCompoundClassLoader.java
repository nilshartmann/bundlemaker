package org.bundlemaker.core.internal.projectdescription.gson;

import org.bundlemaker.core.internal.projectdescription.contentprovider.ProjectContentProviderExtension;
import org.bundlemaker.core.internal.util.BundleDelegatingClassLoader;
import org.bundlemaker.core.internal.util.CompoundClassLoader;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class ContentProviderCompoundClassLoader {

  /** - */
  private CompoundClassLoader _compoundClassLoader;

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
    for (ProjectContentProviderExtension extension : ProjectContentProviderExtension
        .getAllProjectContentProviderExtension()) {

      bundle = Platform.getBundle(extension.getExtension().getContributor().getName());
      _compoundClassLoader.add(new BundleDelegatingClassLoader(bundle));
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
}
