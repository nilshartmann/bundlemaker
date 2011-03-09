package org.bundlemaker.core.utils;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.util.FileUtils;
import org.bundlemaker.core.util.StopWatch;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FileUtilsTest {

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws CoreException
	 */
	@Test
	public void testFileUtils() throws CoreException {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<String> children = FileUtils.getAllChildren(new File(System
				.getProperty("user.dir"), "test-environment"));

		stopWatch.stop();
		System.out.println(stopWatch.getElapsedTime());

		Assert.assertEquals(4749, children.size());
	}
}
