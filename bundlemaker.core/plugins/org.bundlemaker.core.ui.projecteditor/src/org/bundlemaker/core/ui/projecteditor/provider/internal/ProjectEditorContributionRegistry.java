package org.bundlemaker.core.ui.projecteditor.provider.internal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropProvider;
import org.bundlemaker.core.ui.projecteditor.provider.INewProjectContentProviderWizardContribution;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;

/**
 * <p>
 * </p>
 * 
 */
public class ProjectEditorContributionRegistry implements IExtensionChangeHandler {

  /** - */
  public static final String                    EXTENSION_POINT_ID          = "org.bundlemaker.core.ui.projecteditor";

  /** - */
  private ExtensionTracker                      _tracker;

  /** - */
  private final List<ProjectEditorContribution> _projectEditorContributions = new CopyOnWriteArrayList<ProjectEditorContribution>();

  /**
   * <p>
   * </p>
   */
  public ProjectEditorContributionRegistry() {

    // get the extension registry
    IExtensionRegistry registry = RegistryFactory.getRegistry();

    // get the extension points
    IExtensionPoint extensionPoint = registry.getExtensionPoint(EXTENSION_POINT_ID);

    // get the extension tracker
    _tracker = new ExtensionTracker(registry);

    //
    for (IExtension extension : extensionPoint.getExtensions()) {
      addExtension(_tracker, extension);
    }

    // register IExtensionChangeHandler
    _tracker.registerHandler(this, ExtensionTracker.createExtensionPointFilter(extensionPoint));
  }

  /**
   * <p>
   * </p>
   */
  public void dispose() {
    _tracker.unregisterHandler(this);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Set<IProjectContentProviderEditor> getContentProviderEditors() {

    Set<IProjectContentProviderEditor> result = new HashSet<IProjectContentProviderEditor>();

    for (ProjectEditorContribution projectEditorContribution : _projectEditorContributions) {
      if (projectEditorContribution.hasContentProviderEditor()) {
        result.add(projectEditorContribution.getContentProviderEditor());
      }
    }

    return result;
  }

  public Set<IProjectEditorDropProvider> getDropProviders() {

    Set<IProjectEditorDropProvider> result = new HashSet<IProjectEditorDropProvider>();

    for (ProjectEditorContribution projectEditorContribution : _projectEditorContributions) {
      if (projectEditorContribution.hasDropProvider()) {
        result.add(projectEditorContribution.getDropProvider());
      }
    }

    return result;
  }

  public Set<INewProjectContentProviderWizardContribution> getNewProjectContentProviderWizardContributions() {

    Set<INewProjectContentProviderWizardContribution> result = new HashSet<INewProjectContentProviderWizardContribution>();

    for (ProjectEditorContribution projectEditorContribution : _projectEditorContributions) {
      if (projectEditorContribution.hasNewProjectContentProviderWizardContribution()) {
        result.add(projectEditorContribution.getNewProjectContentProviderWizardContribution());
      }
    }

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addExtension(IExtensionTracker tracker, IExtension extension) {

    try {

      // instantiate
      ProjectEditorContribution contribution = createProjectEditorContributionFromExtension(extension);

      // register
      _tracker.registerObject(extension, contribution, IExtensionTracker.REF_STRONG);

      // add
      _projectEditorContributions.add(contribution);

    } catch (CoreException e) {
      //
    }
  }

  @Override
  public void removeExtension(IExtension extension, Object[] objects) {

    for (Object object : objects) {
      ProjectEditorContribution contribution = (ProjectEditorContribution) object;
      _projectEditorContributions.remove(contribution);
      _tracker.unregisterObject(extension, contribution);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param extension
   * @return
   * @throws CoreException
   */
  private ProjectEditorContribution createProjectEditorContributionFromExtension(IExtension extension)
      throws CoreException {

    //
    IConfigurationElement[] configurationElements = extension.getConfigurationElements();

    IProjectContentProviderEditor projectContentProviderEditor = null;
    IProjectEditorDropProvider dropProvider = null;
    INewProjectContentProviderWizardContribution newProjectContentProviderWizardContribution = null;

    // read contributions
    for (IConfigurationElement iConfigurationElement : configurationElements) {

      if ("provider-editor".equals(iConfigurationElement.getName())) {
        projectContentProviderEditor = (IProjectContentProviderEditor) iConfigurationElement
            .createExecutableExtension("class");
      } else if ("drop-provider".equals(iConfigurationElement.getName())) {
        dropProvider = (IProjectEditorDropProvider) iConfigurationElement.createExecutableExtension("class");
      }
      if ("new-wizard-contribution".equals(iConfigurationElement.getName())) {
        newProjectContentProviderWizardContribution = (INewProjectContentProviderWizardContribution) iConfigurationElement
            .createExecutableExtension("class");
      }
    }

    // create contribution
    ProjectEditorContribution projectEditorContribution = new ProjectEditorContribution(projectContentProviderEditor,
        dropProvider, newProjectContentProviderWizardContribution);

    return projectEditorContribution;

  }

  private static final class ProjectEditorContribution {

    private final IProjectContentProviderEditor                _contentProviderEditor;

    private final IProjectEditorDropProvider                   _dropProvider;

    private final INewProjectContentProviderWizardContribution _newProjectContentProviderWizardContribution;

    /**
     * @param contentProviderEditor
     * @param dropProvider
     * @param newProjectContentProviderWizardContribution
     */
    public ProjectEditorContribution(IProjectContentProviderEditor contentProviderEditor,
        IProjectEditorDropProvider dropProvider,
        INewProjectContentProviderWizardContribution newProjectContentProviderWizardContribution) {
      super();
      _contentProviderEditor = contentProviderEditor;
      _dropProvider = dropProvider;
      _newProjectContentProviderWizardContribution = newProjectContentProviderWizardContribution;
    }

    /**
     * @return the contentProviderEditor
     */
    public IProjectContentProviderEditor getContentProviderEditor() {
      return _contentProviderEditor;
    }

    /**
     * @return the dropProvider
     */
    public IProjectEditorDropProvider getDropProvider() {
      return _dropProvider;
    }

    /**
     * @return the newProjectContentProviderWizardContribution
     */
    public INewProjectContentProviderWizardContribution getNewProjectContentProviderWizardContribution() {
      return _newProjectContentProviderWizardContribution;
    }

    public boolean hasContentProviderEditor() {
      return _contentProviderEditor != null;
    }

    public boolean hasDropProvider() {
      return _dropProvider != null;
    }

    public boolean hasNewProjectContentProviderWizardContribution() {
      return _newProjectContentProviderWizardContribution != null;
    }

  }
}
