/*******************************************************************************
 * Copyright (c) 2013 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.projecteditor.provider.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditorElement;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Abstract base class for IProjectContentProviderEditor implementors
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractProjectContentProviderEditor implements IProjectContentProviderEditor {

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canHandle(IProjectContentProvider provider) {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getRootElement(IBundleMakerProject project, IProjectContentProvider provider) {
    return provider;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canChangeAnalyzeMode(IProjectContentProvider projectContentProvider, Object element) {

    // can't edit analyze mode
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAnalyzeMode(IProjectContentProvider projectContentProvider, Object element, AnalyzeMode analyzeMode) {
    // default: do nothing
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canEdit(Object selectedObject) {

    // can't edit anything
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean edit(Shell shell, IBundleMakerProject project, IProjectContentProvider provider, Object
      selectedObject) {

    // can't edit anything
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canRemove(Object selectedObject) {

    // can't remove anything
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void remove(Shell shell, IBundleMakerProject project, IProjectContentProvider provider, Object
      selectedObject) {

    // default: do nothing
  }

  @Override
  public List<IAction> getContextMenuActions(IBundleMakerProject project,
      List<IProjectContentProviderEditorElement> selectedElements) {

    // default: no context menu contribution
    return null;
  }

  /**
   * Can be used to retrieve content from a {@link IProjectContentProvider}. This method opens a Progress Monitor Dialog
   * in case retrieving took some time
   * 
   * @param bundleMakerProject
   * @param projectContentProvider
   * @return
   */
  protected List<IProjectContentEntry> getContentFromProvider(
      IBundleMakerProject bundleMakerProject,
      IProjectContentProvider projectContentProvider) {

    GetBundleMakerProjectContentRunnable runnable = new GetBundleMakerProjectContentRunnable(bundleMakerProject,
        projectContentProvider);
    try {
      PlatformUI.getWorkbench().getProgressService().busyCursorWhile(runnable);
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      // ignore
    }

    return runnable.getBundleMakerProjectContent();

  }

  class GetBundleMakerProjectContentRunnable implements IRunnableWithProgress {

    private final IBundleMakerProject     _bundleMakerProject;

    private final IProjectContentProvider _projectContentProvider;

    List<IProjectContentEntry>            _bundleMakerProjectContent;

    private GetBundleMakerProjectContentRunnable(IBundleMakerProject bundleMakerProject,
        IProjectContentProvider provider) {
      this._bundleMakerProject = bundleMakerProject;
      this._projectContentProvider = provider;
    }

    @Override
    public void run(IProgressMonitor monitor)
        throws InvocationTargetException, InterruptedException {
      try {
        _bundleMakerProjectContent = _projectContentProvider.getBundleMakerProjectContent(monitor,
            _bundleMakerProject);
      } catch (CoreException e) {
        throw new InvocationTargetException(e);
      }
    }

    public List<IProjectContentEntry> getBundleMakerProjectContent() {
      return _bundleMakerProjectContent;
    }

  }

}
