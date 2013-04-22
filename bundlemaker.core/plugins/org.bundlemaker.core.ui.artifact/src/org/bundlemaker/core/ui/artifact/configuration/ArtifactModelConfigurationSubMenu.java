/*******************************************************************************
 * Copyright (c) 2013 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/

package org.bundlemaker.core.ui.artifact.configuration;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.ui.artifact.Activator;
import org.bundlemaker.core.ui.artifact.ArtifactImages;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.navigator.CommonNavigator;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactModelConfigurationSubMenu extends CompoundContributionItem {

  public final static String SUBMENU_ID = "org.bundlemaker.core.ui.artifact.configuration.ArtifactModelConfigurationSubMenu";

  public ArtifactModelConfigurationSubMenu() {
    super(SUBMENU_ID);
  }

  private boolean                             _initialized = false;

  private HierachicalPackagesAction           _hierachicalPackagesAction;

  private HierachicalPackagesAction           _flatPackagesAction;

  private VirtualModuleAction                 _virtualModuleAction;

  private ContentTypeAction                   _sourceContentTypeAction;

  private ContentTypeAction                   _binaryContentTypeAction;

  private IArtifactModelConfigurationProvider _artifactModelConfigurationProvider;

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.actions.CompoundContributionItem#getContributionItems()
   */
  @Override
  protected IContributionItem[] getContributionItems() {
    if (!_initialized) {
      initialize();
    }

    // Refresh enablement and state
    _hierachicalPackagesAction.update();
    _flatPackagesAction.update();
    _virtualModuleAction.update();
    _sourceContentTypeAction.update();
    _binaryContentTypeAction.update();

    IContributionItem[] result = new IContributionItem[7];
    result[0] = new ActionContributionItem(_hierachicalPackagesAction);
    result[1] = new ActionContributionItem(_flatPackagesAction);
    result[2] = new Separator();
    result[3] = new ActionContributionItem(_virtualModuleAction);
    result[4] = new Separator();
    result[5] = new ActionContributionItem(_sourceContentTypeAction);
    result[6] = new ActionContributionItem(_binaryContentTypeAction);

    return result;

  }

  protected void initialize() {
    _artifactModelConfigurationProvider = Activator.getDefault()
        .getArtifactModelConfigurationProvider();

    _hierachicalPackagesAction = new HierachicalPackagesAction(true, "Hierarchical Packages",
        ArtifactImages.ARTIFACT_TREE_CONFIGURATION_HIERARCHICAL_PACKAGES.getImageDescriptor());
    _flatPackagesAction = new HierachicalPackagesAction(false, "Flat Packages",
        ArtifactImages.ARTIFACT_TREE_CONFIGURATION_FLAT_PACKAGES.getImageDescriptor());

    _virtualModuleAction = new VirtualModuleAction();
    _sourceContentTypeAction = new ContentTypeAction(ProjectContentType.SOURCE, "Sources");
    _binaryContentTypeAction = new ContentTypeAction(ProjectContentType.BINARY, "Binaries");

    _initialized = true;

  }

  protected void saveConfiguration(boolean hierarchical,
      ProjectContentType contentType, boolean includeVirtualModuleForMissingTypes) {

    //
    if (_artifactModelConfigurationProvider instanceof ArtifactModelConfigurationProvider) {
      ((ArtifactModelConfigurationProvider) _artifactModelConfigurationProvider).store(hierarchical, contentType,
          includeVirtualModuleForMissingTypes);
    } else {
      // "Should not happen"...
      System.err.printf(
          "ArtifactModelConfigurationProvider '%s' is not instanceof IArtifactModelConfigurationProvider?!%n",
          _artifactModelConfigurationProvider);
    }

    // update navigator
    // TODO reveal selection and expansion state

    CommonNavigator commonNavigator = CommonNavigatorUtils
        .findCommonNavigator("org.eclipse.ui.navigator.ProjectExplorer");
    if (commonNavigator != null) {
      // Remember expanded and selected objects
      Object[] expandedObjects = commonNavigator.getCommonViewer().getExpandedElements();
      IStructuredSelection currentSelection = (IStructuredSelection) commonNavigator.getCommonViewer().getSelection();

      CommonNavigatorUtils.update("org.eclipse.ui.navigator.ProjectExplorer");

      // BundleMaker object instances might have changed due to configuration change,
      // so we have to map the old expanded and selected object instances to the new ones
      // (whereever possible)
      Set<Object> objectsToExpand = new HashSet<Object>();

      // determine new objects to expand
      for (Object expandedObject : expandedObjects) {
        addObjectsFromNewModel(expandedObject, objectsToExpand);
      }

      // determine new selection
      Iterator<?> iterator = currentSelection.iterator();
      List<Object> objectsToSelect = new LinkedList<Object>();
      while (iterator.hasNext()) {
        Object selectedElement = iterator.next();
        addObjectsFromNewModel(selectedElement, objectsToSelect);

      }

      commonNavigator.getCommonViewer().setExpandedElements(objectsToExpand.toArray());
      commonNavigator.getCommonViewer().setSelection(new StructuredSelection(objectsToSelect), false);

    }

  }

  protected void addObjectsFromNewModel(final Object objectInTree, Collection<Object> target) {
    if (!(objectInTree instanceof IBundleMakerArtifact)) {
      target.add(objectInTree);
    } else {
      // find artifact in new AnalysisModel created by the modified configuration
      IBundleMakerArtifact artifact = (IBundleMakerArtifact) objectInTree;
      IRootArtifact newRootArtifact = artifact.getModularizedSystem().getAnalysisModel(
          _artifactModelConfigurationProvider.getArtifactModelConfiguration());
      List<IBundleMakerArtifact> result = AnalysisModelQueries.findArtifactsByQualifiedName(
          IBundleMakerArtifact.class, newRootArtifact, artifact.getQualifiedName());
      target.addAll(result);
    }

  }

  class HierachicalPackagesAction extends Action {

    private final boolean _hierarchicalPackages;

    HierachicalPackagesAction(boolean hierarchicalPackages, String title, ImageDescriptor imageDescriptor) {
      super(title, Action.AS_CHECK_BOX);
      this._hierarchicalPackages = hierarchicalPackages;

      setImageDescriptor(imageDescriptor);
    }

    protected void update() {
      setChecked(_artifactModelConfigurationProvider.getArtifactModelConfiguration().isHierarchicalPackages() == _hierarchicalPackages);
    }

    @Override
    public void run() {

      // persist change
      saveConfiguration(_hierarchicalPackages, _artifactModelConfigurationProvider.getArtifactModelConfiguration()
          .getContentType(),
          _artifactModelConfigurationProvider.getArtifactModelConfiguration().isIncludeVirtualModuleForMissingTypes());
    }

  }

  class VirtualModuleAction extends Action {
    VirtualModuleAction() {
      super("Virtual Module for missing Types", Action.AS_CHECK_BOX);
    }

    protected void update() {
      setChecked(_artifactModelConfigurationProvider.getArtifactModelConfiguration()
          .isIncludeVirtualModuleForMissingTypes());
    }

    @Override
    public void run() {

      // persist change
      saveConfiguration(_artifactModelConfigurationProvider.getArtifactModelConfiguration().isHierarchicalPackages(),
          _artifactModelConfigurationProvider.getArtifactModelConfiguration()
              .getContentType(),
          isChecked());
    }
  }

  class ContentTypeAction extends Action {
    private final ProjectContentType _projectContentType;

    ContentTypeAction(ProjectContentType type, String typeString) {
      super("Show " + typeString, Action.AS_CHECK_BOX);
      this._projectContentType = type;
    }

    protected void update() {
      setChecked(_projectContentType.equals(
          _artifactModelConfigurationProvider.getArtifactModelConfiguration().getContentType()));
    }

    @Override
    public void run() {

      // persist change
      saveConfiguration(_artifactModelConfigurationProvider.getArtifactModelConfiguration().isHierarchicalPackages(),
          _projectContentType,
          _artifactModelConfigurationProvider.getArtifactModelConfiguration().isIncludeVirtualModuleForMissingTypes());
    }
  }
}
