package org.bundlemaker.itest.spring;

import java.io.File;
import java.io.FileWriter;

import org.bundlemaker.core.exporter.AbstractExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IResourceStandin;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SimpleReportExporter extends AbstractExporter {

	/**
	 * {@inheritDoc}
	 */
	public boolean canExport(IModularizedSystem modularizedSystem,
			IResourceModule module, IModuleExporterContext context) {

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void export(IModularizedSystem modularizedSystem,
			IResourceModule module, IModuleExporterContext context)
			throws Exception {

		StringBuilder builder = new StringBuilder();
		builder.append(module.getModuleIdentifier().toString() + "\n");

		builder.append("\n");
		builder.append("Source-Content: \n");
		for (IResourceStandin resource : module
				.getResources(ContentType.SOURCE)) {
			builder.append(resource.getPath() + "\n");
		}

		builder.append("\n");
		builder.append("Binary-Content: \n");
		for (IResourceStandin resource : module
				.getResources(ContentType.BINARY)) {
			builder.append(resource.getPath() + "\n");
		}

		//
		File outFile = new File(context.getDestinationDirectory(), module
				.getModuleIdentifier().toString() + ".txt");

		FileWriter fileWriter = new FileWriter(outFile);
		fileWriter.write(builder.toString());
		fileWriter.flush();
		fileWriter.close();

	}
}
