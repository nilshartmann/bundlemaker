package org.bundlemaker.core.exporter.manifest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.JarFileCreator;
import org.bundlemaker.core.exporter.StandardBundlorBasedBinaryBundleExporter;
import org.bundlemaker.core.resource.IResourceStandin;
import org.eclipse.core.runtime.Assert;

import com.springsource.bundlor.util.SimpleManifestContents;
import com.springsource.util.osgi.manifest.BundleManifest;
import com.springsource.util.osgi.manifest.BundleManifestFactory;
import com.springsource.util.parser.manifest.ManifestContents;
import com.springsource.util.parser.manifest.RecoveringManifestParser;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ManifestUtils {

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public static File getTemplateDirectory(IModuleExporterContext context) {

		//
		if (!context
				.containsAttribute(StandardBundlorBasedBinaryBundleExporter.TEMPLATE_DIRECTORY)) {
			return null;
		}

		//
		Object attribute = context
				.getAttribute(StandardBundlorBasedBinaryBundleExporter.TEMPLATE_DIRECTORY);

		// type check
		if (!(attribute instanceof File)) {

			//
			throw new RuntimeException("Wrong type: " + attribute.getClass());
		}

		//
		return (File) attribute;
	}

	public static ManifestContents readManifestContents(
			IResourceStandin manifestResource) throws IOException {

		ManifestContents originalManifestContents;

		if (manifestResource != null) {

			InputStream inputStream = new JarFileCreator()
					.getInputStream(manifestResource);
			RecoveringManifestParser parser = new RecoveringManifestParser();
			originalManifestContents = parser.parse(new InputStreamReader(
					inputStream));

		} else {
			originalManifestContents = new SimpleManifestContents();
		}
		return originalManifestContents;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param file
	 * @return
	 */
	public static ManifestContents readManifestContents(File file) {

		//
		Assert.isNotNull(file);

		if (!file.exists()) {
			return null;
		}

		// read the bundleManifest
		BundleManifest bundleManifest;

		try {
			bundleManifest = BundleManifestFactory
					.createBundleManifest(new FileReader(file));
			//
			ManifestContents result = ManifestUtils
					.toManifestContents(bundleManifest);

			return result;

		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param jarFile
	 * @return
	 * @throws IOException
	 */
	public static ManifestContents readManifestContents(JarFile jarFile)
			throws IOException {

		ZipEntry zipEntry = jarFile.getEntry("META-INF/MANIFEST.MF");

		if (zipEntry != null) {

			InputStream inputStream = jarFile.getInputStream(zipEntry);
			RecoveringManifestParser recoveringManifestParser = new RecoveringManifestParser();
			recoveringManifestParser.parse(new InputStreamReader(inputStream));
			return recoveringManifestParser.getManifestContents();

		} else {
			return toManifestContents(BundleManifestFactory
					.createBundleManifest());
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleManifest
	 * @return
	 */
	public static ManifestContents toManifestContents(
			BundleManifest bundleManifest) {

		// COPY!!

		Dictionary<String, String> headers = bundleManifest.toDictionary();
		ManifestContents manifest = new SimpleManifestContents(
				headers.get("Manifest-Version"));
		Map<String, String> attributes = manifest.getMainAttributes();

		Enumeration<String> headerNames = headers.keys();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			attributes.put(headerName, headers.get(headerName));
		}

		//
		return manifest;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param manifestContents
	 * @return
	 */
	public static Manifest toManifest(ManifestContents manifestContents) {

		Manifest manifest = new Manifest();

		for (Entry<String, String> entry : manifestContents.getMainAttributes()
				.entrySet()) {

			//
			manifest.getMainAttributes().putValue(entry.getKey(),
					entry.getValue());
		}

		for (String sectionName : manifestContents.getSectionNames()) {

			Attributes attributes = new Attributes();

			for (Entry<String, String> entry : manifestContents
					.getAttributesForSection(sectionName).entrySet()) {

				//
				attributes.putValue(entry.getKey(), entry.getValue());
			}

			manifest.getEntries().put(sectionName, attributes);
		}

		//
		return manifest;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param base
	 * @param add
	 */
	public static void mergeManifests(ManifestContents base,
			ManifestContents add) {

		// COPY!!

		base.getMainAttributes().putAll(add.getMainAttributes());
		for (String sectionName : add.getSectionNames()) {
			base.getAttributesForSection(sectionName).putAll(
					add.getAttributesForSection(sectionName));
		}
	}
}
