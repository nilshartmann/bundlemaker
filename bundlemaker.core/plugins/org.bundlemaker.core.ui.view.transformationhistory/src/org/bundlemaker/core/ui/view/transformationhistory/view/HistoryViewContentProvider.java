/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.view.transformationhistory.view;

import java.util.List;

import org.bundlemaker.core.analysis.IAnalysisModelModifiedListener;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.transformation.ITransformation;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class HistoryViewContentProvider implements ITreeContentProvider {

  private final RefreshViewerAnalysisModelModifiedListener _refreshViewerAnalysisModelModifiedListener = new RefreshViewerAnalysisModelModifiedListener();

  private Viewer                                           _viewer;

  private final String[]                                   NO_TRANSFORMATION_HAS_BEEN_RUN              = new String[] { "No transformation has been run yet" };

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.IContentProvider#dispose()
   */
  @Override
  public void dispose() {

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object,
   * java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

    _viewer = viewer;

    if (oldInput != null) {
      List<IRootArtifact> oldRoots = (List<IRootArtifact>) oldInput;

      for (IRootArtifact iRootArtifact : oldRoots) {
        iRootArtifact.removeAnalysisModelModifiedListener(_refreshViewerAnalysisModelModifiedListener);
      }
    }

    if (newInput != null) {
      List<IRootArtifact> newRoots = (List<IRootArtifact>) newInput;

      for (IRootArtifact iRootArtifact : newRoots) {
        iRootArtifact.addAnalysisModelModifiedListener(_refreshViewerAnalysisModelModifiedListener);
      }
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
   */
  @Override
  public Object[] getElements(Object inputElement) {

    List<IRootArtifact> systems = (List<IRootArtifact>) inputElement;

    return systems.toArray(new IRootArtifact[0]);

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
   */
  @Override
  public Object[] getChildren(Object parentElement) {
    if (parentElement instanceof IRootArtifact) {
      IRootArtifact rootArtifact = (IRootArtifact) parentElement;
      IModularizedSystem modularizedSystem = rootArtifact.getModularizedSystem();
      ITransformation[] transformations = modularizedSystem.getTransformations().toArray(new ITransformation[0]);
      if (transformations.length == 0) {
        return NO_TRANSFORMATION_HAS_BEEN_RUN;
      }
      return transformations;
    }

    System.out.println("Hae??? " + parentElement);
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
   */
  @Override
  public Object getParent(Object element) {

    if (element instanceof ITransformation) {
      ITransformation transformation = (ITransformation) element;
    }

    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
   */
  @Override
  public boolean hasChildren(Object element) {

    return element instanceof IRootArtifact;

  }

  class RefreshViewerAnalysisModelModifiedListener implements IAnalysisModelModifiedListener {

    /*
     * (non-Javadoc)
     * 
     * @see org.bundlemaker.core.analysis.IAnalysisModelModifiedListener#analysisModelModified()
     */
    @Override
    public void analysisModelModified() {

      // async refresh
      Display.getDefault().asyncExec(new Runnable() {
        @Override
        public void run() {
          if (_viewer != null && !_viewer.getControl().isDisposed()) {
            _viewer.refresh();
          }
        }
      });

    }

  }

}
