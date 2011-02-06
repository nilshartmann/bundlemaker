package org.bundlemaker.core.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;

import com.springsource.bundlor.util.MatchUtils;
import com.springsource.bundlor.util.SimpleManifestContents;
import com.springsource.bundlor.util.SimpleParserLogger;
import com.springsource.util.osgi.manifest.BundleManifest;
import com.springsource.util.osgi.manifest.BundleManifestFactory;
import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;
import com.springsource.util.osgi.manifest.parse.HeaderParserFactory;
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
	 * @param template
	 * @return
	 */
	public static List<HeaderDeclaration> parseManifestValue(String template) {

		if (template != null && !template.isEmpty()) {
			return HeaderParserFactory
					.newHeaderParser(new SimpleParserLogger()).parseHeader(
							template);
		} else {
			return new ArrayList<HeaderDeclaration>(0);
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param declarations
	 * @param packageName
	 * @return
	 */
	public static HeaderDeclaration findMostSpecificDeclaration(
			List<HeaderDeclaration> declarations, String packageName) {

		HeaderDeclaration match = null;
		int matchSpecificity = -1;

		for (HeaderDeclaration headerDeclaration : declarations) {
			for (String stem : headerDeclaration.getNames()) {
				int m = MatchUtils.rankedMatch(packageName, stem);
				if (m > matchSpecificity) {
					match = headerDeclaration;
					matchSpecificity = m;
				}
			}
		}
		return match;
	}

	public static ManifestContents readManifestContents(
			IResource manifestResource) throws IOException {

		ManifestContents originalManifestContents;

		if (manifestResource != null) {

			RecoveringManifestParser parser = new RecoveringManifestParser();
			originalManifestContents = parser.parse(new InputStreamReader(
					manifestResource.getInputStream()));

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
