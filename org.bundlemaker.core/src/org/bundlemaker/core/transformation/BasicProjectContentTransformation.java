package org.bundlemaker.core.transformation;

import org.bundlemaker.core.internal.modules.ModularizedSystem;
import org.bundlemaker.core.internal.modules.ResourceModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IFileBasedContent;

public class BasicProjectContentTransformation implements ITransformation{

	@Override
	public void apply(ModularizedSystem modularizedSystem) {

		// iterate over the file based content
		for (IFileBasedContent fileBasedContent : modularizedSystem
				.getProjectDescription().getFileBasedContent()) {

			if (fileBasedContent.isResourceContent()) {

				// create new module
				ResourceModule module = modularizedSystem
						.createResourceModule(new ModuleIdentifier(
										fileBasedContent.getName(),
										fileBasedContent.getVersion()));

				// add all the binary content
				module.getSelfContainer()
						.getModifiableResourcesSet(ContentType.BINARY)
						.addAll(fileBasedContent.getResourceContent()
								.getBinaryResources());

				// add all the source content
				module.getSelfContainer()
						.getModifiableResourcesSet(ContentType.SOURCE)
						.addAll(fileBasedContent.getResourceContent()
								.getSourceResources());
			}
		}
	}
}
