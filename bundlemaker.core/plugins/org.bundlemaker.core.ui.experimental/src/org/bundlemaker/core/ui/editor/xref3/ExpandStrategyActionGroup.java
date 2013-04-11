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

package org.bundlemaker.core.ui.editor.xref3;

import static org.bundlemaker.core.ui.view.dependencytree.UIDependencyTreeImages.AUTO_EXPAND_FROM_GROUPS;
import static org.bundlemaker.core.ui.view.dependencytree.UIDependencyTreeImages.AUTO_EXPAND_FROM_MODULES;
import static org.bundlemaker.core.ui.view.dependencytree.UIDependencyTreeImages.AUTO_EXPAND_FROM_PACKAGES;
import static org.bundlemaker.core.ui.view.dependencytree.UIDependencyTreeImages.AUTO_EXPAND_FROM_RESOURCES;
import static org.bundlemaker.core.ui.view.dependencytree.UIDependencyTreeImages.AUTO_EXPAND_TO_GROUPS;
import static org.bundlemaker.core.ui.view.dependencytree.UIDependencyTreeImages.AUTO_EXPAND_TO_MODULES;
import static org.bundlemaker.core.ui.view.dependencytree.UIDependencyTreeImages.AUTO_EXPAND_TO_PACKAGES;
import static org.bundlemaker.core.ui.view.dependencytree.UIDependencyTreeImages.AUTO_EXPAND_TO_RESOURCES;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.ui.view.dependencytree.DefaultExpandStrategy;
import org.bundlemaker.core.ui.view.dependencytree.UIDependencyTreeImages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ExpandStrategyActionGroup {

  private final DefaultExpandStrategy _expandStrategy;

  private final boolean               _to;

  private ExpandStrategyAction        _groupsAction;

  private ExpandStrategyAction        _modulesAction;

  private ExpandStrategyAction        _packagesAction;

  private ExpandStrategyAction        _resourcesAction;

  public ExpandStrategyActionGroup(DefaultExpandStrategy expandStrategy, boolean to) {
    _expandStrategy = expandStrategy;
    _to = to;
    _groupsAction = new ExpandStrategyAction("Expand to Groups",
        (to ? AUTO_EXPAND_TO_GROUPS : AUTO_EXPAND_FROM_GROUPS), IGroupArtifact.class);
    _modulesAction = new ExpandStrategyAction("Expand to Modules", (to ? AUTO_EXPAND_TO_MODULES
        : AUTO_EXPAND_FROM_MODULES), IModuleArtifact.class);
    _packagesAction = new ExpandStrategyAction("Expand to Packages", (to ? AUTO_EXPAND_TO_PACKAGES
        : AUTO_EXPAND_FROM_PACKAGES), IPackageArtifact.class);
    _resourcesAction = new ExpandStrategyAction("Expand to Resources", (to ? AUTO_EXPAND_TO_RESOURCES
        : AUTO_EXPAND_FROM_RESOURCES), IResourceArtifact.class);

    updateActions();
  }

  public void fill(ToolBarManager mgr) {
    mgr.add(_groupsAction);
    mgr.add(_modulesAction);
    mgr.add(_packagesAction);
    mgr.add(_resourcesAction);

  }

  protected void setExpandLevel(Class<? extends IBundleMakerArtifact> expandLevel) {
    if (_to) {
      _expandStrategy.setToTreeViewerAutoExpandType(expandLevel);
    } else {
      _expandStrategy.setFromTreeViewerAutoExpandType(expandLevel);
    }

    updateActions();
  }

  protected void updateActions() {
    Class<? extends IBundleMakerArtifact> expandLevel;
    if (_to) {
      expandLevel = _expandStrategy.getToViewerExpandToType();
    } else {
      expandLevel = _expandStrategy.getFromViewerExpandToType();
    }

    _groupsAction.update(expandLevel);
    _modulesAction.update(expandLevel);
    _packagesAction.update(expandLevel);
    _resourcesAction.update(expandLevel);
  }

  class ExpandStrategyAction extends Action {

    private final Class<? extends IBundleMakerArtifact> _expandLevel;

    ExpandStrategyAction(String label, UIDependencyTreeImages image, Class<? extends IBundleMakerArtifact> expandLevel) {
      super(label, IAction.AS_CHECK_BOX);

      _expandLevel = expandLevel;
      setImageDescriptor(image.getImageDescriptor());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
      boolean checked = isChecked();
      setExpandLevel((checked ? _expandLevel : null));
    }

    protected void update(Class<? extends IBundleMakerArtifact> currentExpandLevel) {
      setChecked(_expandLevel.equals(currentExpandLevel));
    }
  }
}
