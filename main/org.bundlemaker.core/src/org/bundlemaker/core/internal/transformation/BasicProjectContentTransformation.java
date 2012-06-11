/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.internal.transformation;

import org.bundlemaker.core.internal.modules.ResourceContainer;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.core.runtime.IProgressMonitor;

public class BasicProjectContentTransformation implements ITransformation {

  @Override
  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor progressMonitor) {

    // iterate over the file based content
    for (IProjectContentEntry fileBasedContent : modularizedSystem.getProjectDescription().getContent()) {

      if (fileBasedContent.isAnalyze()) {

        // create new module
        IModifiableResourceModule module = modularizedSystem.createResourceModule(new ModuleIdentifier(fileBasedContent
            .getName(), fileBasedContent.getVersion()));

        // put the user attributes
        module.getUserAttributes().putAll(fileBasedContent.getUserAttributes());

        // add all the binary content
        ((ResourceContainer) module.getModifiableSelfResourceContainer()).addAll(fileBasedContent.getBinaryResources(),
            ContentType.BINARY);

        // add all the source content
        ((ResourceContainer) module.getModifiableSelfResourceContainer()).addAll(fileBasedContent.getSourceResources(),
            ContentType.SOURCE);
      }
    }
  }
}
