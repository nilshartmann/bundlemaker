package org.bundlemaker.core.internal.extensionpoints.projectcontentprovider;

import java.util.LinkedList;
import java.util.List;

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

  /** - */
  private IConfigurationElement _jaxbObjectFactoryElement;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IExtension getExtension() {
    return _extension;
  }

  public String getId() {
    // TODO
    return _jaxbObjectFactoryElement.getAttribute("class");
  }

  public Object createJaxbObjectFactory() throws CoreException {
    return _jaxbObjectFactoryElement.createExecutableExtension("class");
  }

  public Object createProjectContentProvider() throws CoreException {
    return _projectContentProviderElement.createExecutableExtension("class");
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws CoreException
   */
  public static List<ProjectContentProviderExtension> getAllProjectContentProviderExtension() throws CoreException {

    //
    List<ProjectContentProviderExtension> result = new LinkedList<ProjectContentProviderExtension>();

    //
    IExtensionRegistry reg = Platform.getExtensionRegistry();
    IExtension[] extensions = reg.getExtensionPoint("org.bundlemaker.core.projectcontentprovider").getExtensions();

    for (IExtension extension : extensions) {

      //
      ProjectContentProviderExtension providerExtension = new ProjectContentProviderExtension();

      //
      providerExtension._extension = extension;

      for (IConfigurationElement configurationElement : extension.getConfigurationElements()) {

        if (configurationElement.getName().equals("projectContentProvider")) {
          providerExtension._projectContentProviderElement = configurationElement;

          for (IConfigurationElement child : configurationElement.getChildren()) {
            if (child.getName().equals("jaxbObjectFactory")) {
              providerExtension._jaxbObjectFactoryElement = child;
            }
          }
        }
      }

      result.add(providerExtension);
    }

    //
    return result;
  }
}
