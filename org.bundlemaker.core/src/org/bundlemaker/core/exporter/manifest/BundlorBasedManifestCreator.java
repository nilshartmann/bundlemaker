package org.bundlemaker.core.exporter.manifest;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.eclipse.core.runtime.Assert;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;

import com.springsource.bundlor.support.partialmanifest.ReadablePartialManifest;
import com.springsource.bundlor.support.partialmanifest.StandardPartialManifestResolver;
import com.springsource.bundlor.support.partialmanifest.StandardReadablePartialManifest;
import com.springsource.bundlor.util.SimpleManifestContents;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundlorBasedManifestCreator {

	/** - */
	public static final String MANIFEST_HEADER_BUNDLE_CREATOR = "Bundle-Creator";

	/** - */
	public static final String MANIFEST_HEADER_CREATED_BY = "Created-By";

	/** - */
	private IResourceModule _resourceModule;

	/** - */
	private IModuleExporterContext _context;

	/**
	 * <p>
	 * Creates a new instance of type {@link BundlorBasedManifestCreator}.
	 * </p>
	 * 
	 * @param resourceModule
	 * @param context
	 */
	public BundlorBasedManifestCreator(IResourceModule resourceModule,
			IModuleExporterContext context) {

		Assert.isNotNull(resourceModule);
		Assert.isNotNull(context);

		_resourceModule = resourceModule;
		_context = context;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param module
	 * @return
	 * @throws IOException
	 */
	public ManifestContents createManifestContents(
			boolean includeSourceReferences) throws IOException {

		//
		ManifestContents originalManifestContents = ManifestUtils
				.readManifestContents(_resourceModule.getResource(
						"META-INF/MANIFEST.MF", ContentType.BINARY));

		// create the partial manifest
		ReadablePartialManifest partialManifest = createPartialManifest(includeSourceReferences);

		ManifestContents result = originalManifestContents;

		// read the template file
		File templateDirectory = ManifestUtils.getTemplateDirectory(_context);
		File templateFile = new File(templateDirectory, _resourceModule
				.getModuleIdentifier().getName() + ".template");

		System.out.println(" Try to read template file '"
				+ templateFile.getAbsolutePath() + "' (exists: "
				+ templateFile.exists() + ")");

		ManifestContents templateManifestContents = ManifestUtils
				.readManifestContents(templateFile);

		if (templateManifestContents == null) {
			templateManifestContents = new SimpleManifestContents();
		}

		// set the 'Import-Template' header
		final Set<String> optionalPackages = _context.getModularizedSystem()
				.getUnsatisfiedReferencedPackages(_resourceModule, true,
						includeSourceReferences);
		
		StringBuilder stringBuilder = new StringBuilder();
		for (Iterator<String> iterator = optionalPackages.iterator(); iterator
				.hasNext();) {
			String optionalPackage = (String) iterator.next();
			stringBuilder.append(optionalPackage);
			if (optionalPackages.contains(optionalPackage)) {
				stringBuilder.append(";resolution:=optional");
			}
			if (iterator.hasNext()) {
				stringBuilder.append(",");
			}
		}
		templateManifestContents.getMainAttributes().put("Import-Template",
				stringBuilder.toString());

		// create the merged template
		// WORKAROUND
		String tmpBundleVersion = templateManifestContents.getMainAttributes()
				.get(Constants.BUNDLE_VERSION);
		templateManifestContents.getMainAttributes().remove(
				Constants.BUNDLE_VERSION);
		ManifestContents mergedTemplateBundleManifestContents = ManifestUtils
				.toManifestContents(new StandardPartialManifestResolver()
						.resolve(templateManifestContents, partialManifest));
		// WORKAROUND
		templateManifestContents.getMainAttributes().put(
				Constants.BUNDLE_VERSION, tmpBundleVersion);

		// set the create headers
		mergedTemplateBundleManifestContents.getMainAttributes().put(
				MANIFEST_HEADER_CREATED_BY, "BundleMaker 1.0.0");
		mergedTemplateBundleManifestContents.getMainAttributes().put(
				MANIFEST_HEADER_BUNDLE_CREATOR, "BundleMaker 1.0.0");

		// set the 'BUNDLE_SYMBOLICNAME' header
		String bundleSymbolicName = templateManifestContents
				.getMainAttributes().get(Constants.BUNDLE_SYMBOLICNAME) != null ? templateManifestContents
				.getMainAttributes().get(Constants.BUNDLE_SYMBOLICNAME)
				: _resourceModule.getModuleIdentifier().getName();
		originalManifestContents.getMainAttributes().put(
				Constants.BUNDLE_SYMBOLICNAME, bundleSymbolicName);

		// set the 'BUNDLE_VERSION' header
		String bundleVersion = templateManifestContents.getMainAttributes()
				.get(Constants.BUNDLE_VERSION) != null ? templateManifestContents
				.getMainAttributes().get(Constants.BUNDLE_VERSION)
				: _resourceModule.getModuleIdentifier().getVersion();

		// TODO:
		try {
			Version version = new Version(bundleVersion);
		} catch (Exception e) {
			bundleVersion = "0.0.0";
		}

		originalManifestContents.getMainAttributes().put(
				Constants.BUNDLE_VERSION, bundleVersion);

		// set the 'BUNDLE_MANIFESTVERSION' header
		originalManifestContents.getMainAttributes().put(
				Constants.BUNDLE_MANIFESTVERSION, "2");

		// TODO: Copy all additional headers from the template!!

		// merge
		ManifestUtils.mergeManifests(originalManifestContents,
				mergedTemplateBundleManifestContents);

		// return the result
		return result;
	}

	/**
	 * @param includeSourceReferences
	 * @return
	 */
	private ReadablePartialManifest createPartialManifest(
			boolean includeSourceReferences) {

		StandardReadablePartialManifest partialManifest = new StandardReadablePartialManifest();
		for (String containedPackage : _resourceModule.getContainedPackages()) {
			partialManifest.recordExportPackage(containedPackage);
		}
		for (String referencedType : _resourceModule.getReferencedTypes(true,
				includeSourceReferences)) {
			partialManifest.recordReferencedType(referencedType);
		}
		return partialManifest;
	}
}
