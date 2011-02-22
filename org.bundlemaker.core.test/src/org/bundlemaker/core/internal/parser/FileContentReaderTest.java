package org.bundlemaker.core.internal.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;
import org.bundlemaker.core.internal.resource.ArchiveFileCache;
import org.bundlemaker.core.parser.IDirectory;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

public class FileContentReaderTest {

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws CoreException
	 */
	@Test
	public void testBinaryDirectoryBasedContent() throws CoreException {

		//
		IBundleMakerProjectDescription projectDescription = createProjectDescription(
				"bundlor", "1.0.0", new String[] { "classes" }, new String[] {});

		// get the directories
		List<IDirectory> directories = FileContentReader.getDirectories(
				projectDescription.getFileBasedContent("000001"), false);

		assertBinaryContent(directories);

	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws CoreException
	 */
	@Test
	public void testBinaryJarFileBasedContent() throws CoreException {

		//
		IBundleMakerProjectDescription projectDescription = createProjectDescription(
				"bundlor", "1.0.0",
				new String[] { "com.springsource.bundlor-1.0.0.RELEASE.jar" },
				new String[] {});

		// get the directories
		List<IDirectory> directories = FileContentReader.getDirectories(
				projectDescription.getFileBasedContent("000001"), false);

		assertBinaryContent(directories);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws CoreException
	 */
	@Test
	public void testBinaryMixedContent() throws CoreException {

		//
		IBundleMakerProjectDescription projectDescription = createProjectDescription(
				"bundlor", "1.0.0", new String[] { "classes-partial_1.jar",
						"classes-partial_2" }, new String[] {});

		// get the directories
		List<IDirectory> directories = FileContentReader.getDirectories(
				projectDescription.getFileBasedContent("000001"), false);

		assertBinaryContent(directories);
	}

	@Test
	public void testSourceSplitPackage() throws CoreException {

		//
		IBundleMakerProjectDescription projectDescription = createProjectDescription(
				"bundlor", "1.0.0", new String[] { "classes" }, new String[] {
						"src-1", "src-2" });

		// get the directories
		List<IDirectory> directories = FileContentReader.getDirectories(
				projectDescription.getFileBasedContent("000001"), false);

		// assert
		assertSourceContent(directories);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param name
	 * @param version
	 * @param binaryPaths
	 * @param sourcePaths
	 * @return
	 * @throws CoreException
	 */
	private IBundleMakerProjectDescription createProjectDescription(
			String name, String version, String[] binaryPaths,
			String[] sourcePaths) throws CoreException {

		// step 1: create project description
		File testEnvironment = new File(System.getProperty("user.dir"),
				"test-environment");

		//
		BundleMakerProjectDescription projectDescription = new BundleMakerProjectDescription(
				null, new ArchiveFileCache());

		//
		projectDescription.setJre("jre");

		List<String> binaryPathsList = new LinkedList<String>();
		List<String> sourcePathsList = new LinkedList<String>();

		for (String binaryPath : binaryPaths) {
			File file = new File(testEnvironment, binaryPath);
			binaryPathsList.add(file.getAbsolutePath());
		}

		for (String sourcePath : sourcePaths) {
			File file = new File(testEnvironment, sourcePath);
			sourcePathsList.add(file.getAbsolutePath());
		}

		//
		projectDescription.addResourceContent(name, version, binaryPathsList,
				sourcePathsList);

		projectDescription.initialize(null);

		//
		return projectDescription;
	}

	private void assertSourceContent(List<IDirectory> directories) {
		assertEquals(12, directories.size());

		Directory[] directoryArray = (Directory[]) directories
				.toArray(new Directory[0]);

		//
		assertEquals("com/springsource/bundlor/support/properties",
				directoryArray[0].getDirectoryName().toString());
		assertEquals(6, directoryArray[0].getBinaryContentCount());
		assertEquals(6, directoryArray[0].getSourceContentCount());

		assertEquals("com/springsource/bundlor/util", directoryArray[1]
				.getDirectoryName().toString());
		assertEquals(5, directoryArray[1].getBinaryContentCount());
		assertEquals(5, directoryArray[1].getSourceContentCount());

		assertEquals("com/springsource/bundlor/support/classpath",
				directoryArray[2].getDirectoryName().toString());
		assertEquals(8, directoryArray[2].getBinaryContentCount());
		assertEquals(6, directoryArray[2].getSourceContentCount());

		assertEquals("org/springframework/util", directoryArray[3]
				.getDirectoryName().toString());
		assertEquals(10, directoryArray[3].getBinaryContentCount());
		assertEquals(8, directoryArray[3].getSourceContentCount());

		assertEquals("com/springsource/bundlor", directoryArray[4]
				.getDirectoryName().toString());
		assertEquals(5, directoryArray[4].getBinaryContentCount());
		assertEquals(5, directoryArray[4].getSourceContentCount());

		assertEquals("com/springsource/bundlor/support/partialmanifest",
				directoryArray[5].getDirectoryName().toString());
		assertEquals(4, directoryArray[5].getBinaryContentCount());
		assertEquals(4, directoryArray[5].getSourceContentCount());

		assertEquals("com/springsource/bundlor/support/propertysubstitution",
				directoryArray[6].getDirectoryName().toString());
		assertEquals(11, directoryArray[6].getBinaryContentCount());
		assertEquals(10, directoryArray[6].getSourceContentCount());

		assertEquals("com/springsource/bundlor/support/contributors",
				directoryArray[7].getDirectoryName().toString());
		assertEquals(22, directoryArray[7].getBinaryContentCount());
		assertEquals(16, directoryArray[7].getSourceContentCount());

		assertEquals("com/springsource/bundlor/support/asm", directoryArray[8]
				.getDirectoryName().toString());
		assertEquals(5, directoryArray[8].getBinaryContentCount());
		assertEquals(5, directoryArray[8].getSourceContentCount());

		assertEquals("com/springsource/bundlor/support/manifestwriter",
				directoryArray[9].getDirectoryName().toString());
		assertEquals(6, directoryArray[9].getBinaryContentCount());
		assertEquals(5, directoryArray[9].getSourceContentCount());

		assertEquals("META-INF", directoryArray[10].getDirectoryName()
				.toString());
		assertEquals(2, directoryArray[10].getBinaryContentCount());
		assertEquals(2, directoryArray[10].getSourceContentCount());

		assertEquals("com/springsource/bundlor/support", directoryArray[11]
				.getDirectoryName().toString());
		assertEquals(14, directoryArray[11].getBinaryContentCount());
		assertEquals(13, directoryArray[11].getSourceContentCount());
	}

	private void assertBinaryContent(List<IDirectory> directories) {
		assertEquals(12, directories.size());

		Directory[] directoryArray = (Directory[]) directories
				.toArray(new Directory[0]);

		//
		assertEquals("com/springsource/bundlor/support/properties",
				directoryArray[0].getDirectoryName().toString());
		assertEquals(6, directoryArray[0].getBinaryContentCount());
		assertEquals(0, directoryArray[0].getSourceContentCount());

		assertEquals("com/springsource/bundlor/util", directoryArray[1]
				.getDirectoryName().toString());
		assertEquals(5, directoryArray[1].getBinaryContentCount());
		assertEquals(0, directoryArray[1].getSourceContentCount());

		assertEquals("com/springsource/bundlor/support/classpath",
				directoryArray[2].getDirectoryName().toString());
		assertEquals(8, directoryArray[2].getBinaryContentCount());
		assertEquals(0, directoryArray[2].getSourceContentCount());

		assertEquals("org/springframework/util", directoryArray[3]
				.getDirectoryName().toString());
		assertEquals(10, directoryArray[3].getBinaryContentCount());
		assertEquals(0, directoryArray[3].getSourceContentCount());

		assertEquals("com/springsource/bundlor", directoryArray[4]
				.getDirectoryName().toString());
		assertEquals(5, directoryArray[4].getBinaryContentCount());
		assertEquals(0, directoryArray[4].getSourceContentCount());

		assertEquals("com/springsource/bundlor/support/partialmanifest",
				directoryArray[5].getDirectoryName().toString());
		assertEquals(4, directoryArray[5].getBinaryContentCount());
		assertEquals(0, directoryArray[5].getSourceContentCount());

		assertEquals("com/springsource/bundlor/support/propertysubstitution",
				directoryArray[6].getDirectoryName().toString());
		assertEquals(11, directoryArray[6].getBinaryContentCount());
		assertEquals(0, directoryArray[6].getSourceContentCount());

		assertEquals("com/springsource/bundlor/support/contributors",
				directoryArray[7].getDirectoryName().toString());
		assertEquals(22, directoryArray[7].getBinaryContentCount());
		assertEquals(0, directoryArray[7].getSourceContentCount());

		assertEquals("com/springsource/bundlor/support/asm", directoryArray[8]
				.getDirectoryName().toString());
		assertEquals(5, directoryArray[8].getBinaryContentCount());
		assertEquals(0, directoryArray[8].getSourceContentCount());

		assertEquals("com/springsource/bundlor/support/manifestwriter",
				directoryArray[9].getDirectoryName().toString());
		assertEquals(6, directoryArray[9].getBinaryContentCount());
		assertEquals(0, directoryArray[9].getSourceContentCount());

		assertEquals("META-INF", directoryArray[10].getDirectoryName()
				.toString());
		assertEquals(2, directoryArray[10].getBinaryContentCount());
		assertEquals(0, directoryArray[10].getSourceContentCount());

		assertEquals("com/springsource/bundlor/support", directoryArray[11]
				.getDirectoryName().toString());
		assertEquals(14, directoryArray[11].getBinaryContentCount());
		assertEquals(0, directoryArray[11].getSourceContentCount());
	}

	// @Test
	// public void test() throws CoreException {
	//
	// //
	// ModifiableBundleMakerProjectDescription projectDescription =
	// ModifiableprojectdescriptionFactory.eINSTANCE
	// .createModifiableBundleMakerProjectDescription();
	//
	// projectDescription
	// .addResourceContent(
	// "R:/environments/bundlemaker2-environment/workspace/target.platform/bundlor-1.0.0.RELEASE/com.springsource.bundlor-1.0.0.RELEASE.jar",
	// "R:/environments/bundlemaker2-environment/workspace/target.platform/bundlor-1.0.0.RELEASE/com.springsource.bundlor-sources-1.0.0.RELEASE.jar");
	//
	// projectDescription.setJRE("jre");
	//
	// projectDescription.initialize();
	//
	// Collection<Directory> directories = FileContentReader.getDirectories(
	// projectDescription.getFileBasedContent().get(0), false);
	//
	// assertEquals(12, directories.size());
	//
	// for (Directory directory : directories) {
	// assertNotNull(directory.getBinaryContent());
	// assertNotNull(directory.getSourceContent());
	// assertTrue(directory.getBinaryContent().getResourceCount() > 0);
	// assertTrue(directory.getSourceContent().getResourceCount() > 0);
	// }
	//
	// // second run
	// directories = FileContentReader.getDirectories(
	// projectDescription.getFileBasedContent("000000"), true);
	//
	// assertEquals(12, directories.size());
	//
	// for (Directory directory : directories) {
	// assertNotNull(directory.getBinaryContent());
	// assertNull(directory.getSourceContent());
	// assertTrue(directory.getBinaryContent().getResourceCount() > 0);
	// }
	// }
}
