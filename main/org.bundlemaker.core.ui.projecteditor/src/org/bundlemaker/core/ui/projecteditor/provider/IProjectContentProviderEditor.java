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

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public interface IProjectContentProviderEditor {

  public boolean canHandle(IProjectContentProvider provider);

  /** TODO: Allow more than one root element? */
  public Object getRootElement(IBundleMakerProject project, IProjectContentProvider provider);

  /** TODO: Allow children of children? */
  public List<Object> getChildren(IBundleMakerProject project, IProjectContentProvider provider, Object rootElement);

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

  // ---- TODO: ---- //

  // edit is only available on a single selection
  // public boolean canEdit(Object selectedObject);
  // public void edit(Object selectedObject);

  // HOW TO HANDLE SELECTED OBJECTS FROM DIFFERENT PROVIDERS?
  // Maybe we pass in all selected object - regardless whether
  // they came from this provder or not - and let
  // the provider return true only if it can handle *all*
  // selected objects

  // public boolean canRemove(Object[] selectedObjects);
  // public void remove(Object[] selectedObjects);

  // -- move up and down only at Provider level? --
  // public boolean canMoveUp(Object[] selectedObjects);
  // public void moveUp(Object[] selectedObjects);

  // public boolean canMoveDown(Object[] selectedObjects);
  // public void moveDown(Object[] selectedObjects);

}
