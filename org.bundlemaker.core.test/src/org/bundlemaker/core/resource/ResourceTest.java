package org.bundlemaker.core.resource;

import junit.framework.Assert;

import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceTest {

	/**
	 * <p>
	 * </p>
	 */
	@Test
	public void testResource() {

		Resource resource = new Resource("contentId", "root", "path");

		Assert.assertEquals("contentId", resource.getContentId());
		Assert.assertEquals("root", resource.getRoot());
		Assert.assertEquals("path", resource.getPath());

		Assert.assertTrue(resource.getAssociatedResources().isEmpty());
		Assert.assertTrue(resource.getReferences().isEmpty());
		Assert.assertTrue(resource.getContainedTypes().isEmpty());
	}

	/**
	 * <p>
	 * </p>
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testUnmodifiableAssociatedResources() {
		Resource resource = new Resource("contentId", "root", "path");
		resource.getAssociatedResources().add(null);
	}

	/**
	 * <p>
	 * </p>
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testUnmodifiableReferences() {
		Resource resource = new Resource("contentId", "root", "path");
		resource.getReferences().add(null);
	}

	/**
	 * <p>
	 * </p>
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testUnmodifiableContainedTypes() {
		Resource resource = new Resource("contentId", "root", "path");
		resource.getContainedTypes().add(null);
	}
}
