package org.bundlemaker.core.transformation;

import org.bundlemaker.core.internal.modules.ResourceModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IFileBasedContent;

public class BasicProjectContentTransformation implements ITransformation {

	@Override
	public void apply(IModifiableModularizedSystem modularizedSystem) {

		// iterate over the file based content
		for (IFileBasedContent fileBasedContent : modularizedSystem
				.getProjectDescription().getFileBasedContent()) {

			if (fileBasedContent.isResourceContent()) {

				// create new module
				IModifiableResourceModule module = modularizedSystem
						.createResourceModule(new ModuleIdentifier(
								fileBasedContent.getName(), fileBasedContent
										.getVersion()));

				// add all the binary content
				module.getModifiableSelfResourceContainer()
						.getModifiableResourcesSet(ContentType.BINARY)
						.addAll(fileBasedContent.getResourceContent()
								.getBinaryResources());

				// add all the source content
				module.getModifiableSelfResourceContainer()
						.getModifiableResourcesSet(ContentType.SOURCE)
						.addAll(fileBasedContent.getResourceContent()
								.getSourceResources());
			}
		}
	}
}
