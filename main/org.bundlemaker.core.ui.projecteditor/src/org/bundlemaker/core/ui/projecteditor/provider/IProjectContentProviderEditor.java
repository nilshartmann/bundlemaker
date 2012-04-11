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
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public interface IProjectContentProviderEditor {

  public boolean canHandle(IProjectContentProvider provider);

  public Object getRootElement(IBundleMakerProject project, IProjectContentProvider provider);

  public List<Object> getChildren(IBundleMakerProject project, IProjectContentProvider provider, Object rootElement);

  public Image getImage(Object element);

  public String getLabel(Object element);

  public Boolean isAnalyze(Object element);

  public Boolean isAnalyzeSources(Object element);

}
