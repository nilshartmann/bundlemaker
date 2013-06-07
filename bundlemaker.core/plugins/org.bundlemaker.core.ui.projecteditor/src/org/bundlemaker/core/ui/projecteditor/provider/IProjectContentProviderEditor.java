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
package org.bundlemaker.core.ui.projecteditor.provider;

import java.util.List;

import org.bundlemaker.core.project.AnalyzeMode;
import org.bundlemaker.core.project.IProjectContentProvider;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 *         TODO rename IProjectContentTreeProvider
 */
public interface IProjectContentProviderEditor {

  public boolean canHandle(IProjectContentProvider provider);

  public Object getRootElement(IProjectDescriptionAwareBundleMakerProject project, IProjectContentProvider provider);

  /**
   * @param provider
   *          the provider instance that has been passed to
   *          {@link #getRootElement(IProjectDescriptionAwareBundleMakerProject, IProjectContentProvider)} or this method, which in turn has
   *          returned the given root element
   * 
   * @throws Exception
   */
  public List<? extends Object> getChildren(IProjectDescriptionAwareBundleMakerProject project, IProjectContentProvider provider,
      Object rootElement) throws Exception;

  /**
   * Returns the image for left ("Resource") column
   * 
   * @param element
   * @return The Image. Might be null.
   */
  public Image getImage(Object element);

  /**
   * Returns the label for left ("Resource") column
   * 
   * @param element
   * @return the label. Might be null.
   */
  public String getLabel(Object element);

  /**
   * Returns the {@link AnalyzeMode} of the object or null if no analyze mode is available on object that doesn't
   * support AnalyzeMode
   * 
   * @param element
   * @return
   */
  public AnalyzeMode getAnalyzeMode(Object element);

  public boolean canChangeAnalyzeMode(IProjectContentProvider projectContentProvider, Object element);

  public void setAnalyzeMode(IProjectContentProvider projectContentProvider, Object element, AnalyzeMode analyzeMode);

  /**
   * Determines if the given object can be edited by this provider.
   * 
   * <p>
   * This method is invoked to check the enablement of the Edit button on the ProjectEditorPage
   * 
   * @param selectedObject
   * @return
   */
  public boolean canEdit(Object selectedObject);

  /**
   * @param shell
   * @param project
   * @param provider
   * @param selectedObject
   * @return true if the BundleMaker project (description) has been changed during the edit or false if not
   */
  public boolean edit(Shell shell, IProjectDescriptionAwareBundleMakerProject project, IProjectContentProvider provider, Object selectedObject);

  /**
   * Determines if the given object can be removed by this provider.
   * 
   * <p>
   * This method is only invoked for childs of the IProjectContentProvider not for the IProjectContentProvider itself.
   * 
   * 
   * <p>
   * This method is invoked to check the enablement of the Remove button on the ProjectEditorPage
   * 
   * @param selectedObject
   * @return
   */
  public boolean canRemove(Object selectedObject);

  /**
   * Remove the selectedObject from the specified {@link IProjectContentProvider}
   * <p>
   * This method is only invoked for childs of the IProjectContentProvider not for the IProjectContentProvider itself.
   * 
   * @param shell
   * @param project
   * @param provider
   * @param selectedObject
   *          The object that should be removed
   */
  public void remove(Shell shell, IProjectDescriptionAwareBundleMakerProject project, IProjectContentProvider provider, Object selectedObject);

  /**
   * Returns a list of actions that should be contributed to the project editor tree.
   * 
   * <p>
   * This method should return <b>all</b> context menu actions having set their <b>enabled</b> state according to the
   * currently selected elements
   * 
   * <p>
   * This method is invoked <b>each time</b> the context menu is shown.
   * 
   * 
   * @param project
   *          the bundlemaker project
   * @param selectedElements
   *          all elements that are currently selected in the project editor tree viewer.
   * @return
   */
  public List<IAction> getContextMenuActions(IProjectDescriptionAwareBundleMakerProject project,
      List<IProjectContentProviderEditorElement> selectedElements);

}
