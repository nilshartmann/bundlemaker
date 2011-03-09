package org.bundlemaker.core.osgi.exporter;

import org.bundlemaker.core.exporter.AbstractExporter;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.osgi.manifest.ManifestUtils;
import org.bundlemaker.core.util.GenericCache;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.service.resolver.StateObjectFactory;
import org.osgi.framework.BundleException;

import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public abstract class AbstractManifestAwareExporter extends AbstractExporter {

	// TODO
	public static final String OSGI_FRAGMENT_HOST = "OSGI_FRAGMENT_HOST";

	/** - */
	private GenericCache<IModule, ManifestContents> _manifestCache;

	/** - */
	private ManifestContents _manifestContents;

	/** - */
	private ManifestContents _hostManifestContents;

	/**
	 * <p>
	 * Creates a new instance of type {@link AbstractManifestAwareExporter}.
	 * </p>
	 */
	public AbstractManifestAwareExporter() {

		//
		_manifestCache = new GenericCache<IModule, ManifestContents>() {

			@Override
			protected ManifestContents create(IModule key) {

				try {

					//
					ManifestContents manifestContents = createManifest();
					Assert.isNotNull(
							manifestContents,
							String.format(
									"The method createManifest(IModularizedSystem, "
											+ "IResourceModule, IModuleExporterContext) of class "
											+ "'%s' returned 'null'.", this
											.getClass().getName()));

					return manifestContents;

				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("");
				}
			}

		};
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected final ManifestContents getHostManifestContents() {

		//
		return _hostManifestContents;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected final boolean isFragment() {

		//
		return _hostManifestContents != null;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected ManifestContents getCurrentManifest() {
		Assert.isNotNull(_manifestContents, String.format(
				"No manifest set. The method createManifest(IModularizedSystem, "
						+ "IResourceModule, IModuleExporterContext) of class "
						+ "'%s' has not been called yet.", this.getClass()
						.getName()));

		return _manifestContents;
	}

	@Override
	protected void preExportModule() throws CoreException {

		// call super
		super.preExportModule();

		// get the manifest contents
		_manifestContents = _manifestCache.getOrCreate(getCurrentModule());

		// check the manifest
		try {
			StateObjectFactory.defaultFactory.createBundleDescription(null,
					ManifestUtils.convertManifest(ManifestUtils
							.toManifest(_manifestContents)), "internal", 1);
		} catch (BundleException e) {
			// TODO
			e.printStackTrace();
			throw new CoreException(new Status(IStatus.ERROR, "", ""));
		}

		// get the host manifest
		if (getCurrentModule().getUserAttributes().containsKey(
				OSGI_FRAGMENT_HOST)) {

			IModule hostModule = (IModule) getCurrentModule()
					.getUserAttributes().get(OSGI_FRAGMENT_HOST);

			// if () {
			// IModule host = getHostModule()
			// _hostManifestContents = _manifestCache.get(host);
			// }
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modularizedSystem
	 * @param module
	 * @param context
	 * @return
	 * @throws Exception
	 */
	protected abstract ManifestContents createManifest() throws CoreException;
}
