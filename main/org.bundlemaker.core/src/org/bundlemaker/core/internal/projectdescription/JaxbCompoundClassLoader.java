package org.bundlemaker.core.internal.projectdescription;

import java.util.Iterator;

import org.bundlemaker.core.internal.extensionpoints.projectcontentprovider.ProjectContentProviderExtension;
import org.bundlemaker.core.model.internal.projectdescription.xml.ObjectFactory;
import org.bundlemaker.core.util.BundleDelegatingClassLoader;
import org.bundlemaker.core.util.CompoundClassLoader;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class JaxbCompoundClassLoader {

  /** - */
  private CompoundClassLoader _compoundClassLoader;

  /**
   * <p>
   * Creates a new instance of type {@link JaxbCompoundClassLoader}.
   * </p>
   * 
   * @throws CoreException
   */
  public JaxbCompoundClassLoader() throws CoreException {

    //
    _compoundClassLoader = new CompoundClassLoader();

    // put self
    Bundle bundle = Platform.getBundle("org.bundlemaker.core");
    _compoundClassLoader.getMap().put(ObjectFactory.class.getPackage().getName(),
        new BundleDelegatingClassLoader(bundle));

    //
    for (ProjectContentProviderExtension extension : ProjectContentProviderExtension
        .getAllProjectContentProviderExtension()) {

      String packageName = extension.createJaxbObjectFactory().getClass().getPackage().getName();
      bundle = Platform.getBundle(extension.getExtension().getContributor().getName());
      _compoundClassLoader.getMap().put(packageName, new BundleDelegatingClassLoader(bundle));
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
  public String getPackageString() {

    //
    StringBuffer buffer = new StringBuffer();

    //
    for (Iterator<String> iterator = _compoundClassLoader.getMap().keySet().iterator(); iterator.hasNext();) {
      String packageName = iterator.next();
      buffer.append(packageName);
      if (iterator.hasNext()) {
        buffer.append(":");
      }
    }

    //
    return buffer.toString();
  }
}
