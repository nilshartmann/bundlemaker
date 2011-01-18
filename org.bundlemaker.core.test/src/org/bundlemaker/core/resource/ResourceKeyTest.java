package org.bundlemaker.core.resource;

import junit.framework.Assert;

import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceKeyTest {

	/**
	 * <p>
	 * </p>
	 */
	@Test
	public void testEquals() {

		ResourceKey key_1 = new ResourceKey("1", "path", "root");
		ResourceKey key_2 = new ResourceKey("1", "path", "root");
		Resource resource_1 = new Resource("1", "path", "root");
		Resource resource_2 = new Resource("1", "path", "root");

		Assert.assertEquals(key_1, key_2);
		Assert.assertEquals(key_2, key_1);

		Assert.assertEquals(key_1, resource_1);
		Assert.assertEquals(resource_1, key_1);

		Assert.assertEquals(key_1, resource_2);
		Assert.assertEquals(resource_2, key_1);

		Assert.assertEquals(key_2, resource_1);
		Assert.assertEquals(resource_1, key_2);

		Assert.assertEquals(key_2, resource_2);
		Assert.assertEquals(resource_2, key_2);

		Assert.assertEquals(resource_1, resource_2);
		Assert.assertEquals(resource_2, resource_1);
	}
}
