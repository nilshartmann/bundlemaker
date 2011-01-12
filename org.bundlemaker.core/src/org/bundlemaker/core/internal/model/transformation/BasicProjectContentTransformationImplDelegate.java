package org.bundlemaker.core.internal.model.transformation;

import org.bundlemaker.core.model.projectdescription.ContentType;
import org.bundlemaker.core.model.projectdescription.IFileBasedContent;
import org.bundlemaker.core.model.transformation.BasicProjectContentTransformation;
import org.bundlemaker.core.modules.ModularizedSystem;
import org.bundlemaker.core.modules.ResourceModule;
import org.bundlemaker.core.util.ModelUtils;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BasicProjectContentTransformationImplDelegate {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param description
	 * @param modules
	 */
	public static void apply(BasicProjectContentTransformation transformation,
			ModularizedSystem modularizedSystem) {

		System.out.println("BasicProjectContentTransformation start");

		// iterate over the file based content
		for (IFileBasedContent fileBasedContent : modularizedSystem
				.getProjectDescription().getFileBasedContent()) {

			if (fileBasedContent.isResourceContent()) {

				// create new module
				ResourceModule module = modularizedSystem
						.createResourceModule(ModelUtils
								.createModuleIdentifier(
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

		System.out.println("BasicProjectContentTransformation stop");
	}
}
