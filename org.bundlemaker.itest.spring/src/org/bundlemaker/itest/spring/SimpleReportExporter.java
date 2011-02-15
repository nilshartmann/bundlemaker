package org.bundlemaker.itest.spring;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.bundlemaker.core.exporter.AbstractExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IReferencedModulesQueryResult;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;

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

		for (IResource resource : asSortedList(module
				.getResources(ContentType.SOURCE))) {
			builder.append(resource.getPath() + "\n");
		}

		builder.append("\n");
		builder.append("Binary-Content: \n");
		for (IResource resource : asSortedList(module
				.getResources(ContentType.BINARY))) {
			builder.append(resource.getPath() + "\n");
		}

		builder.append("\n");
		builder.append("Referenced Types: \n");
		Set<String> referencedTypes = module.getReferencedTypeNames(true, true,
				false);
		for (String referencedType : asSortedList(referencedTypes)) {
			builder.append(referencedType + "\n");
		}

		builder.append("\n");
		builder.append("Referenced Modules: \n");
		IReferencedModulesQueryResult queryResult = modularizedSystem
				.getReferencedModules(module, true, true);

		for (IModule referencedModule : queryResult.getReferencedModules()) {
			builder.append(referencedModule.getModuleIdentifier().toString()
					+ "\n");
		}

		builder.append("\n");
		builder.append("Missing Types: \n");
		for (String missingType : queryResult.getUnsatisfiedReferencedTypes()) {
			builder.append(missingType + "\n");
		}

		builder.append("\n");
		builder.append("Types with ambigious modules: \n");
		for (Entry<String, Set<IModule>> missingType : queryResult
				.getReferencedTypesWithAmbiguousModules().entrySet()) {

			builder.append(missingType.getKey() + ":\n");
			for (IModule typeModule : missingType.getValue()) {
				builder.append(" - "
						+ typeModule.getModuleIdentifier().toString() + "\n");
			}
		}

		//
		File outFile = new File(context.getDestinationDirectory(), module
				.getModuleIdentifier().toString() + ".txt");

		FileWriter fileWriter = new FileWriter(outFile);
		fileWriter.write(builder.toString());
		fileWriter.flush();
		fileWriter.close();

	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param <T>
	 * @param set
	 * @return
	 */
	private static <T extends Comparable<T>> List<T> asSortedList(Set<T> set) {

		//
		List<T> arrayList = new ArrayList<T>(set);

		//
		Collections.sort(arrayList);

		//
		return arrayList;
	}
}
