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
package org.bundlemaker.core.ui.projecteditor.filebased;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class FileBasedContentEditorUtils {

  public static String getProjectRelativePath(IResource resource) {
    IPath relativePath = resource.getProjectRelativePath();
    String projectName = resource.getProject().getName();

    String path = "${project_loc:" + projectName + "}/" + relativePath;

    return path;
  }

}
