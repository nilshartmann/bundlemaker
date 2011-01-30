package org.bundlemaker.core.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

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
public final class JarFileUtils {

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
	public static void createJarArchive(Set<IResourceStandin> resources,
			Manifest manifest, OutputStream outputStream) {

		Assert.isNotNull(resources);
		Assert.isNotNull(manifest);

		// create the input and output streams

		JarOutputStream jarOutputStream = null;
		InputStream inputStream = null;

		try {

			// open the archive file
			jarOutputStream = new JarOutputStream(outputStream, manifest);

			// add all the entries
			for (IResourceStandin resourceStandin : resources) {

				// add everything but the manifest
				if (!"META-INF/MANIFEST.MF".equalsIgnoreCase(resourceStandin
						.getPath())) {

					// add archive entry
					JarEntry newEntry = new JarEntry(resourceStandin.getPath());
					jarOutputStream.putNextEntry(newEntry);

					// copy
					inputStream = resourceStandin.getInputStream();
					copy(inputStream, jarOutputStream);
					inputStream.close();
				}
			}

		} catch (Exception ex) {
			// TODO
			ex.printStackTrace();

		} finally {
			close(jarOutputStream);
			close(inputStream);
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
	private static void copy(InputStream in, OutputStream out)
			throws IOException {
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
	private static void close(Closeable closeable) {

		if (closeable != null) {

			try {
				closeable.close();
			} catch (IOException e) {
				//
			}
		}
	}

}