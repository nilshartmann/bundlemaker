/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.analysis.ui.dependencyview;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactHelper {

  private ArtifactHelper() {
    // avoid construction
  }

  public static String getArtifactPath(IArtifact fromArtifact, IArtifact artifact) {
    String path = "";
    boolean inPackage = false;
    while (artifact != null && !artifact.equals(fromArtifact)) {
      if (artifact.getType() == ArtifactType.Package) {
        if (!inPackage) {
          inPackage = true;
          path = artifact.getQualifiedName() + "/" + path;
        }
      } else {
        path = artifact.getName() + "/" + path;
        inPackage = false;
      }
      artifact = artifact.getParent();
    }
    if (path.endsWith("/")) {
      path = path.substring(0, path.length() - 1);
    }
    return path;
  }
}
