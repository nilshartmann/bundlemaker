/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/

package org.bundlemaker.core.ui.handler;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.ErrorDialogUtil;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.internal.Activator;
import org.bundlemaker.core.ui.internal.BundleMakerUiUtils;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;

/**
 * @author Nils Hartmann
 * 
 */
public abstract class AbstractArtifactSelectorHandler extends AbstractArtifactBasedHandler {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler#execute(org.eclipse.core.commands.ExecutionEvent,
   * java.util.List)
   */
  @Override
  protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts) throws Exception {
    try {

      // Create the selector
      IArtifactSelector selector = createArtifactSelector(selectedArtifacts);

      if (selector == null) {
        return;
      }

      // Select the artifacts (might be a long running operation)
      List<IBundleMakerArtifact> selectorResult = runSelector(selector);

      if (selectorResult == null) {
        // Operation has been canceled
        return;
      }

      // select artifacts in the tree
      selectArtifactsInTree(selectorResult);

    } catch (Exception ex) {
      Throwable cause = (ex instanceof InvocationTargetException) ? ((InvocationTargetException) ex).getCause() : ex;

      // Report Error to error log
      BundleMakerUiUtils.logError("Error while selecting artifacts: " + cause, cause);

      // Report error to user
      Throwable throwable = ErrorDialogUtil.getNestedNonCoreThrowable(cause);
      ErrorDialogUtil.errorDialogWithStackTrace("Error while selecting artifacts", throwable.getMessage(),
          Activator.PLUGIN_ID,
          throwable);

    }

  }

  /**
   * @param selectedArtifacts
   * @return
   */
  protected List<IBundleMakerArtifact> runSelector(final IArtifactSelector artifactSelector) throws Exception {
    // Create the runnable that collects the result
    ExecuteArtifactSelectorRunnable runnable = new ExecuteArtifactSelectorRunnable(artifactSelector);

    // Execute runnable via IProgressService
    try {
      PlatformUI.getWorkbench().getProgressService().busyCursorWhile(runnable);

      return runnable.getResult();

    } catch (InterruptedException ex) {
      return null;
    }

  }

  protected abstract IArtifactSelector createArtifactSelector(List<IBundleMakerArtifact> selectedArtifacts)
      throws Exception;

  /**
   * Selects the specified artifacts in the common navigator tree.
   * 
   * 
   * @param artifacts
   */
  protected void selectArtifactsInTree(List<IBundleMakerArtifact> artifacts) {
    CommonNavigator navigator = CommonNavigatorUtils.findCommonNavigator(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID);

    if (navigator != null) {
      CommonViewer commonViewer = navigator.getCommonViewer();

      if (commonViewer != null) {

        StructuredSelection selection = new StructuredSelection(artifacts);

        commonViewer.setSelection(selection, false);
      }
    }

  }

  class ExecuteArtifactSelectorRunnable implements IRunnableWithProgress {

    private final IArtifactSelector    _artifactSelector;

    private List<IBundleMakerArtifact> _result;

    ExecuteArtifactSelectorRunnable(IArtifactSelector selector) {
      _artifactSelector = selector;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
      monitor.setTaskName("Selecting Artifacts");
      List<? extends IBundleMakerArtifact> bundleMakerArtifacts = _artifactSelector.getBundleMakerArtifacts();

      _result = (List<IBundleMakerArtifact>) bundleMakerArtifacts;
    }

    /**
     * @return the result
     */
    public List<IBundleMakerArtifact> getResult() {
      return _result;
    }

  }

}
