package org.bundlemaker.core.internal.projectdescription.contentprovider;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ProjectContentProviderExtension {

  /** - */
  private IExtension            _extension;

  /** - */
  private IConfigurationElement _projectContentProviderElement;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IExtension getExtension() {
    return _extension;
  }

  public Object createProjectContentProvider() throws CoreException {
    return _projectContentProviderElement.createExecutableExtension("class");
  }

  public String getClassName() {
    return _projectContentProviderElement.getAttribute("class");
  }

  public String getId() {
    return _projectContentProviderElement.getAttribute("id");
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws CoreException
   */
  public static Map<String, ProjectContentProviderExtension> getAllProjectContentProviderExtension()
      throws CoreException {

    //
    Map<String, ProjectContentProviderExtension> result = new HashMap<String, ProjectContentProviderExtension>();

    //
    IExtensionRegistry reg = Platform.getExtensionRegistry();
    IExtension[] extensions = reg.getExtensionPoint("org.bundlemaker.core.projectcontentprovider").getExtensions();

    //
    for (IExtension extension : extensions) {

      //
      ProjectContentProviderExtension providerExtension = new ProjectContentProviderExtension();

      //
      providerExtension._extension = extension;

      for (IConfigurationElement configurationElement : extension.getConfigurationElements()) {
        if (configurationElement.getName().equals("projectContentProvider")) {
          providerExtension._projectContentProviderElement = configurationElement;
        }
      }

      //
      if (result.put(providerExtension.getId(), providerExtension) != null) {
        // TODO!!
        throw new RuntimeException("");
      }
    }

    //
    return result;
  }
}
