package org.bundlemaker.core.store.db4o.ptest;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.internal.Activator;
import org.bundlemaker.core.internal.parser.ResourceCache;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.resource.IModifiableResource;
import org.bundlemaker.core.resource.ReferenceType;
import org.bundlemaker.core.resource.ResourceKey;
import org.bundlemaker.core.store.IPersistentDependencyStore;
import org.bundlemaker.core.util.ProgressMonitor;
import org.bundlemaker.core.util.StopWatch;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;
import org.osgi.util.tracker.ServiceTracker;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DBTest {

	/** - */
	private static final String FILE_NAME = String.format("%s/database",
			System.getProperty("user.dir"));

	// /** - */
	// private ObjectContainer _objectContainer;

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws CoreException
	 */
	@Test
	public void testDatabase() throws CoreException {

		IProject project = BundleMakerCore.getOrCreateSimpleProjectWithBundleMakerNature("dbtest");
		IBundleMakerProject bundleMakerProject = BundleMakerCore.getBundleMakerProject(project, null);
		IPersistentDependencyStore store = Activator.getDefault().getPersistentDependencyStore(bundleMakerProject);
		Assert.assertNotNull(store);
		
		// step 2: delete the existing '.bundlemaker/db4o.store' file
		File file = new File(FILE_NAME);
		if (file.exists()) {
			Assert.assertTrue(file.delete());
		}

		Assert.assertNotNull(store.getResources());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		ResourceCache cache = new ResourceCache(store);
		createTestDatabase(50000, 30, cache);

		stopWatch.stop();

		System.out.println("Time: " + stopWatch.getElapsedTime());
		System.out.println("Size: " + new File(FILE_NAME).length() / 1024);

		// store.dispose();
		//
		// store = new PersistentDependencyStoreImpl(
		// Activator.getDb4oService(), FILE_NAME);
		//
		// store.init();

		List<Resource> resources = store.getResources();

		Assert.assertEquals(50000, resources.size());
		for (Resource resource : resources) {
			Assert.assertFalse(resource.getReferences().isEmpty());
			Assert.assertEquals(30, resource.getReferences().size());
		}

		// List<Resource> result = _objectContainer.query(Resource.class);
		//
		// stopWatch = new StopWatch();
		// stopWatch.start();
		//
		// for (Resource myResource : result) {
		// myResource.getPath();
		// }
		//
		// stopWatch.stop();
		//
		// System.out.println("Time: " + stopWatch.getElapsedTime());
		// System.out.println("Size: " + new File(FILE_NAME).length() / 1024);
		//
		// for (Resource myResource : result) {
		// Assert.assertNotNull(myResource.getRoot());
		// }
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceCount
	 * @param referenceCount
	 * @param store
	 * @throws CoreException
	 */
	private void createTestDatabase(int resourceCount, int referenceCount,
			ResourceCache cache) throws CoreException {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		System.out.println("Creating content...");

		for (int i = 0; i < resourceCount; i++) {

			IModifiableResource resource = cache.getOrCreateResource(new ResourceKey(
					"0000001",
					"aksjdhkajshdkajshdkajs/kajshdkjashd/kajhsdkajshd/KJKJKJ",
					"aksjdhkajshdkajshdkajs/kajshdkjashd/kajhsdkajshd/" + i));

			// Resource resource = new Resource("", "", "" + i);

			for (int j = 0; j < referenceCount; j++) {

				resource.recordReference("referencedElement" + j,
						ReferenceType.TYPE_REFERENCE, true, true, false, false,
						false);
			}

			// flyweight

		}

		System.out.println("Content done: " + stopWatch.getElapsedTime());

		cache.commit(new ProgressMonitor());

		System.out.println("Cache commit: " + stopWatch.getElapsedTime());
	}
}
