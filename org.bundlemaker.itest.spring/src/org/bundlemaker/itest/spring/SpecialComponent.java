package org.bundlemaker.itest.spring;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.service.resolver.ExportPackageDescription;
import org.eclipse.osgi.service.resolver.ResolverError;
import org.eclipse.pde.api.tools.internal.ApiDescription;
import org.eclipse.pde.api.tools.internal.model.ArchiveApiTypeContainer;
import org.eclipse.pde.api.tools.internal.model.Component;
import org.eclipse.pde.api.tools.internal.model.DirectoryApiTypeContainer;
import org.eclipse.pde.api.tools.internal.provisional.ApiPlugin;
import org.eclipse.pde.api.tools.internal.provisional.Factory;
import org.eclipse.pde.api.tools.internal.provisional.IApiAccess;
import org.eclipse.pde.api.tools.internal.provisional.IApiDescription;
import org.eclipse.pde.api.tools.internal.provisional.IApiFilterStore;
import org.eclipse.pde.api.tools.internal.provisional.IRequiredComponentDescription;
import org.eclipse.pde.api.tools.internal.provisional.VisibilityModifiers;
import org.eclipse.pde.api.tools.internal.provisional.descriptors.IPackageDescriptor;
import org.eclipse.pde.api.tools.internal.provisional.descriptors.IReferenceTypeDescriptor;
import org.eclipse.pde.api.tools.internal.provisional.model.IApiBaseline;
import org.eclipse.pde.api.tools.internal.provisional.model.IApiTypeContainer;
import org.eclipse.pde.api.tools.internal.util.FileManager;
import org.eclipse.pde.api.tools.internal.util.Util;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SpecialComponent extends Component {

	//
	private File _file;
	private ApiDescription _apiDescription;

	/**
	 * <p>
	 * Creates a new instance of type {@link SpecialComponent}.
	 * </p>
	 * 
	 * @param baseline
	 */
	public SpecialComponent(IApiBaseline baseline, File file) {
		super(baseline);

		//
		_file = file;
	}

	public String getSymbolicName() {
		return "test";
	}

	public boolean hasApiDescription() {
		return true;
	}

	public String getVersion() {
		return "1.2.3";
	}

	public String[] getExecutionEnvironments() throws CoreException {
		return null;
	}

	public IRequiredComponentDescription[] getRequiredComponents()
			throws CoreException {
		return new IRequiredComponentDescription[0];
	}

	public String getLocation() {
		try {
			return _file.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public boolean isSystemComponent() {
		return false;
	}

	public boolean isSourceComponent() throws CoreException {
		return false;
	}

	public boolean isFragment() throws CoreException {
		return false;
	}

	public boolean hasFragments() throws CoreException {
		return false;
	}

	public String[] getLowestEEs() throws CoreException {
		return null;
	}

	public ResolverError[] getErrors() throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IApiDescription createApiDescription() throws CoreException {

		if (_apiDescription == null) {

			_apiDescription = new ApiDescription(getSymbolicName());

			annotateExportedPackages(_apiDescription, this.getPackageNames());
		}

		return _apiDescription;
	}

	@Override
	protected IApiFilterStore createApiFilterStore() throws CoreException {
		return null;
	}

	@Override
	protected List createApiTypeContainers() throws CoreException {

		try {
			List<IApiTypeContainer> containers = new LinkedList<IApiTypeContainer>();
			containers.add(createApiTypeContainer(_file));
			return containers;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CoreException(new Status(IStatus.ERROR, "asdads",
					"Fehler"));
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param location
	 * @return
	 * @throws IOException
	 * @throws CoreException
	 */
	protected IApiTypeContainer createApiTypeContainer(File location)
			throws IOException, CoreException {
		if (location.exists()) {
			if (location.isFile()) {
				return new ArchiveApiTypeContainer(this,
						location.getCanonicalPath());
			} else {
				return new DirectoryApiTypeContainer(this,
						location.getCanonicalPath());
			}
		}

		return null;
	}

	/**
	 * Extracts a directory from the archive given a path prefix for entries to
	 * retrieve. <code>null</code> can be passed in as a prefix, causing all
	 * entries to be be extracted from the archive.
	 * 
	 * @param zip
	 *            the {@link ZipFile} to extract from
	 * @param pathprefix
	 *            the prefix'ing path to include for extraction
	 * @param parent
	 *            the parent directory to extract to
	 * @throws IOException
	 *             if the {@link ZipFile} cannot be read or extraction fails to
	 *             write the file(s)
	 */
	void extractDirectory(ZipFile zip, String pathprefix, File parent)
			throws IOException {
		Enumeration entries = zip.entries();
		String prefix = (pathprefix == null ? Util.EMPTY_STRING : pathprefix);
		ZipEntry entry = null;
		File file = null;
		while (entries.hasMoreElements()) {
			entry = (ZipEntry) entries.nextElement();
			if (entry.getName().startsWith(prefix)) {
				file = new File(parent, entry.getName());
				if (entry.isDirectory()) {
					file.mkdir();
					continue;
				}
				extractEntry(zip, entry, parent);
			}
		}
	}

	/**
	 * Extracts a non-directory entry from a zip file and returns the File
	 * handle
	 * 
	 * @param zip
	 *            the zip to extract from
	 * @param entry
	 *            the entry to extract
	 * @param parent
	 *            the parent directory to add the extracted entry to
	 * @return the file handle to the extracted entry, <code>null</code>
	 *         otherwise
	 * @throws IOException
	 */
	File extractEntry(ZipFile zip, ZipEntry entry, File parent)
			throws IOException {
		InputStream inputStream = null;
		File file;
		FileOutputStream outputStream = null;
		try {
			inputStream = zip.getInputStream(entry);
			file = new File(parent, entry.getName());
			File lparent = file.getParentFile();
			if (!lparent.exists()) {
				lparent.mkdirs();
			}
			outputStream = new FileOutputStream(file);
			byte[] bytes = new byte[8096];
			while (inputStream.available() > 0) {
				int read = inputStream.read(bytes);
				if (read > 0) {
					outputStream.write(bytes, 0, read);
				}
			}
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					ApiPlugin.log(e);
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					ApiPlugin.log(e);
				}
			}
		}
		return file;
	}

	protected static void annotateExportedPackages(IApiDescription apiDesc,
			String[] packageNames) {

		//
		for (String pkgName : packageNames) {

			//
			IPackageDescriptor pkgDesc = Factory.packageDescriptor(pkgName);

			// there could have been directives that have nothing to do with
			// visibility, so we need to add the package as API in that case
			apiDesc.setVisibility(pkgDesc, VisibilityModifiers.API);
		}
	}
}
