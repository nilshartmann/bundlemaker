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
package org.bundlemaker.core.ui.projecteditor.jdt;

import java.util.Iterator;
import java.util.List;

import org.bundlemaker.core.jdt.content.JdtProjectContentProvider;
import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropEvent;
import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropProvider;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Transfer;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class JdtProjectContentProviderDropAdapter implements IProjectEditorDropProvider {

  private final Transfer[] SUPPORTED_TYPES = new Transfer[] { //
                                           LocalSelectionTransfer.getTransfer() //
                                           };

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropProvider#getSupportedDropTypes()
   */
  @Override
  public Transfer[] getSupportedDropTypes() {
    return SUPPORTED_TYPES;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropProvider#canDrop(org.bundlemaker.core.ui.projecteditor
   * .dnd.IProjectEditorDropEvent)
   */
  @Override
  public boolean canDrop(IProjectEditorDropEvent dropEvent) throws Exception {

    Object target = dropEvent.getTarget();

    // Only drop to root or on an exisiting JdtProjectContentProvider
    if (target != null && !(target instanceof JdtProjectContentProvider)) {
      return false;
    }

    // if (dropEvent.hasTarget()) {
    // // only drop to root
    // return false;
    // }

    ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
    List<?> selectedObject = getSelectedObjects(selection);

    if (selectedObject == null) {
      return false;
    }

    Iterator<?> iterator = selectedObject.iterator();
    while (iterator.hasNext()) {
      if (getJavaProject(iterator.next()) == null) {
        // no java project selected
        return false;
      }
    }

    return true;
  }

  protected IJavaProject getJavaProject(Object object) throws CoreException {

    if (object instanceof IProject) {
      IProject project = (IProject) object;

      if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
        IJavaProject javaProject = JavaCore.create(project);
        return javaProject;
      }
    }

    if (object instanceof IJavaProject) {
      return ((IJavaProject) object);
    }

    return null;
  }

  /**
   * Returns the selected objects or null if object is not a selection or empty
   * 
   * @param object
   * @return
   */
  protected List<?> getSelectedObjects(Object object) {
    if (!(object instanceof IStructuredSelection)) {
      return null;
    }

    IStructuredSelection structuredSelection = (IStructuredSelection) object;

    if (structuredSelection.isEmpty()) {
      return null;
    }

    return structuredSelection.toList();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropProvider#performDrop(org.bundlemaker.core.ui.projecteditor
   * .dnd.IProjectEditorDropEvent)
   */
  @Override
  public boolean performDrop(IProjectEditorDropEvent dropEvent) throws Exception {
    List<?> selectedObjects = getSelectedObjects(dropEvent.getData());
    if (selectedObjects == null) {
      return false;
    }

    Object target = dropEvent.getTarget();
    System.out.println("target: " + target);

    JdtProjectContentProvider jdtContentProvider;
    if (target == null) {
      // Create New Project Content Provider
      jdtContentProvider = new JdtProjectContentProvider();
    } else {
      // Add to exisiting provider
      jdtContentProvider = (JdtProjectContentProvider) target;
    }
    boolean changesMade = false;

    for (Object object : selectedObjects) {
      IJavaProject javaProject = getJavaProject(object);

      if (javaProject != null) {
        if (jdtContentProvider.addJavaProject(javaProject)) {
          changesMade = true;
        }
      }
    }
    if (changesMade && target == null) {
      // add new provider
      dropEvent.getBundleMakerProject().getModifiableProjectDescription().addContentProvider(jdtContentProvider);
    }

    return changesMade;
  }
}
