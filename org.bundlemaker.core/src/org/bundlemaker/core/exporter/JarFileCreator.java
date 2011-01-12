package org.bundlemaker.core.exporter;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.IResourceStandin;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public final class JarFileCreator {

	/** the internal ZipFile cache */
	private Map<String, ZipFile> _cache = new HashMap<String, ZipFile>();

	/**
	 * <p>
	 * Creates a jar archive for the given list of {@link IResourceStandin
	 * IResourceStandins}.
	 * </p>
	 * 
	 * @param resources
	 *            the list of resources
	 * @param manifest
	 *            the manifest file
	 * @param archiveFile
	 *            the archive file to create
	 */
	public void createJarArchive(Set<IResourceStandin> resources,
			Manifest manifest, File archiveFile) {

		Assert.isNotNull(resources);
		Assert.isNotNull(manifest);
		Assert.isNotNull(archiveFile);

		// create the input and output streams
		FileOutputStream fileOutputStream = null;
		JarOutputStream jarOutputStream = null;
		InputStream inputStream = null;

		try {

			// open the archive file
			fileOutputStream = new FileOutputStream(archiveFile);
			jarOutputStream = new JarOutputStream(fileOutputStream, manifest);

			// add all the entries
			for (IResourceStandin resourceStandin : resources) {

				if (!"META-INF/MANIFEST.MF".equalsIgnoreCase(resourceStandin
						.getPath())) {

					// add archive entry
					JarEntry newEntry = new JarEntry(resourceStandin.getPath());
					jarOutputStream.putNextEntry(newEntry);

					// get the input stream
					inputStream = getInputStream(resourceStandin);

					// copy
					copy(inputStream, jarOutputStream);

					// close the input stream
					inputStream.close();
				}
			}

		} catch (Exception ex) {
			// TODO
			ex.printStackTrace();

		} finally {
			close(jarOutputStream);
			close(fileOutputStream);
			close(inputStream);
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceStandin
	 * @return
	 * @throws IOException
	 */
	public InputStream getInputStream(IResourceKey resourceStandin)
			throws IOException {

		// get the root
		String root = resourceStandin.getRoot();

		// get the root file
		File rootFile = new File(root);

		//
		if (rootFile.isDirectory()) {

			// TODO
			return new BufferedInputStream(new FileInputStream(new File(
					rootFile, resourceStandin.getPath())));

		} else if (rootFile.isFile()) {

			if (!_cache.containsKey(root)) {
				_cache.put(root, new ZipFile(rootFile));
			}

			ZipFile zipFile = _cache.get(root);
			ZipEntry zipEntry = zipFile.getEntry(resourceStandin.getPath());
			return new BufferedInputStream(zipFile.getInputStream(zipEntry));

		} else {
			throw new RuntimeException("FEHLER");
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	private void copy(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[8192];
		int bytesRead = -1;
		while ((bytesRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
		}
		out.flush();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param closeable
	 */
	private void close(Closeable closeable) {

		if (closeable != null) {

			try {
				closeable.close();
			} catch (IOException e) {
				//
			}
		}
	}

}