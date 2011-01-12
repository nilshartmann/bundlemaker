package org.bundlemaker.core.internal.projectdescription;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JarInfoService {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param file
	 * @return
	 */
	/**
	 * <p>
	 * </p>
	 * 
	 * @param file
	 * @return
	 */
	public static JarInfo extractJarInfo(File file) {

		String name = null;
		String version = null;

		try {

			// TRY TO RESOLVE THE NAME
			name = extractName(file);

			// TRY TO RESOLVE THE VERSION
			version = extractVersion(file);

			// return the result
			return new JarInfo(name, version);
		}

		//
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private static String extractName(File file) throws IOException {

		String version = null;

		version = LogicalJarNameResolver.extractName(file.getName());

		if (version == null) {

			// try to extract the name from the root directory
			JarFile jarFile = new JarFile(file);

			version = LogicalJarNameResolver
					.extractNameFromRootDirectory(jarFile);

			// Try to analyze the jar file
			Manifest manifest = jarFile.getManifest();

			if (manifest != null) {

				// try to extract the name from the main class attribute
				version = LogicalJarNameResolver
						.extractNameFromMainClassAttribute(manifest);

				// try to extract the name from the implementation title
				// attribute
				version = LogicalJarNameResolver
						.extractNameFromImplementationTitle(manifest);
			}
		}
		return version;
	}

	private static String extractVersion(File file) throws IOException {

		String version = null;

		File fileToParse = file;
		while (version == null && fileToParse != null) {
			version = LogicalJarVersionResolver
					.extractVersionFromName(fileToParse.getName());
			if (version == null) {
				fileToParse = fileToParse.getParentFile();
			}
		}

		// Try to analyze the jar file
		if (version == null) {

			// get the manifest file
			JarFile jarFile = new JarFile(file);
			Manifest manifest = jarFile.getManifest();

			if (manifest != null) {

				version = LogicalJarVersionResolver
						.extractNameFromImplementationVersion(manifest);

				if (version == null) {
					version = LogicalJarVersionResolver
							.extractNameFromSpecificationVersion(manifest);
				}
			}
		}

		if (version == null) {

			version = "0.0.0";
		}

		return version;
	}
}
