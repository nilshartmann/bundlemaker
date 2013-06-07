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

import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.internal.api.resource.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.api.resource.IModifiableModule;
import org.bundlemaker.core.internal.projectdescription.ProjectContentEntry;
import org.bundlemaker.core.internal.resource.ModuleIdentifier;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.eclipse.core.runtime.IProgressMonitor;

public class BasicProjectContentTransformation implements IInternalTransformation {

  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor progressMonitor) {

    // iterate over the file based content
    for (IProjectContentEntry fileBasedContent : modularizedSystem.getBundleMakerProject().getProjectDescription()
        .getContent()) {

      if (fileBasedContent.isAnalyze()) {

        // create new module
        IModifiableModule module = modularizedSystem.createResourceModule(new ModuleIdentifier(fileBasedContent
            .getName(), fileBasedContent.getVersion()));

        // put the user attributes
        module.getUserAttributes().putAll(fileBasedContent.getUserAttributes());

        // add all the binary content
        module.addAll(((ProjectContentEntry) fileBasedContent).getBinaryResourceStandins(),
            ResourceType.BINARY);

        // add all the source content
        module.addAll(((ProjectContentEntry) fileBasedContent).getSourceResourceStandins(),
            ResourceType.SOURCE);
      }
    }
  }
}
