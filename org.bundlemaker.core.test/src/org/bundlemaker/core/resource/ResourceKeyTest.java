package org.bundlemaker.core.resource;

import junit.framework.Assert;

import org.bundlemaker.core.spi.resource.ArchiveFileCache;
import org.bundlemaker.core.spi.resource.Resource;
import org.bundlemaker.core.spi.resource.ResourceStandin;
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
		IModifiableResource resource_1 = new Resource("1", "path", "root");
		IModifiableResource resource_2 = new Resource("1", "path", "root");
		ResourceStandin resourceStandin_1 = new ResourceStandin("1", "path",
				"root", new ArchiveFileCache());
		ResourceStandin resourceStandin_2 = new ResourceStandin("1", "path",
				"root", new ArchiveFileCache());

		Assert.assertEquals(key_1, key_2);
		Assert.assertEquals(key_1, resource_1);
		Assert.assertEquals(key_1, resource_2);
		Assert.assertEquals(key_1, resourceStandin_1);
		Assert.assertEquals(key_1, resourceStandin_2);

		Assert.assertEquals(key_2, key_1);
		Assert.assertEquals(key_2, resource_1);
		Assert.assertEquals(key_2, resource_2);
		Assert.assertEquals(key_2, resourceStandin_1);
		Assert.assertEquals(key_2, resourceStandin_2);

		Assert.assertEquals(resource_1, key_1);
		Assert.assertEquals(resource_1, key_2);
		Assert.assertEquals(resource_1, resource_2);
		Assert.assertEquals(resource_1, resourceStandin_1);
		Assert.assertEquals(resource_1, resourceStandin_2);

		Assert.assertEquals(resource_2, key_1);
		Assert.assertEquals(resource_2, key_2);
		Assert.assertEquals(resource_2, resource_2);
		Assert.assertEquals(resource_2, resourceStandin_1);
		Assert.assertEquals(resource_2, resourceStandin_2);

		Assert.assertEquals(resourceStandin_1, key_1);
		Assert.assertEquals(resourceStandin_1, key_2);
		Assert.assertEquals(resourceStandin_1, resource_1);
		Assert.assertEquals(resourceStandin_1, resource_2);
		Assert.assertEquals(resourceStandin_1, resourceStandin_2);

		Assert.assertEquals(resourceStandin_2, key_1);
		Assert.assertEquals(resourceStandin_2, key_2);
		Assert.assertEquals(resourceStandin_2, resource_1);
		Assert.assertEquals(resourceStandin_2, resource_2);
		Assert.assertEquals(resourceStandin_2, resourceStandin_1);

		/** **/

		Assert.assertEquals(key_1.hashCode(), key_2.hashCode());
		Assert.assertEquals(key_1.hashCode(), resource_1.hashCode());
		Assert.assertEquals(key_1.hashCode(), resource_2.hashCode());
		Assert.assertEquals(key_1.hashCode(), resourceStandin_1.hashCode());
		Assert.assertEquals(key_1.hashCode(), resourceStandin_2.hashCode());

		Assert.assertEquals(key_2.hashCode(), key_1.hashCode());
		Assert.assertEquals(key_2.hashCode(), resource_1.hashCode());
		Assert.assertEquals(key_2.hashCode(), resource_2.hashCode());
		Assert.assertEquals(key_2.hashCode(), resourceStandin_1.hashCode());
		Assert.assertEquals(key_2.hashCode(), resourceStandin_2.hashCode());

		Assert.assertEquals(resource_1.hashCode(), key_1.hashCode());
		Assert.assertEquals(resource_1.hashCode(), key_2.hashCode());
		Assert.assertEquals(resource_1.hashCode(), resource_2.hashCode());
		Assert.assertEquals(resource_1.hashCode(), resourceStandin_1.hashCode());
		Assert.assertEquals(resource_1.hashCode(), resourceStandin_2.hashCode());

		Assert.assertEquals(resource_2.hashCode(), key_1.hashCode());
		Assert.assertEquals(resource_2.hashCode(), key_2.hashCode());
		Assert.assertEquals(resource_2.hashCode(), resource_2.hashCode());
		Assert.assertEquals(resource_2.hashCode(), resourceStandin_1.hashCode());
		Assert.assertEquals(resource_2.hashCode(), resourceStandin_2.hashCode());

		Assert.assertEquals(resourceStandin_1.hashCode(), key_1.hashCode());
		Assert.assertEquals(resourceStandin_1.hashCode(), key_2.hashCode());
		Assert.assertEquals(resourceStandin_1.hashCode(), resource_1.hashCode());
		Assert.assertEquals(resourceStandin_1.hashCode(), resource_2.hashCode());
		Assert.assertEquals(resourceStandin_1.hashCode(),
				resourceStandin_2.hashCode());

		Assert.assertEquals(resourceStandin_2.hashCode(), key_1.hashCode());
		Assert.assertEquals(resourceStandin_2.hashCode(), key_2.hashCode());
		Assert.assertEquals(resourceStandin_2.hashCode(), resource_1.hashCode());
		Assert.assertEquals(resourceStandin_2.hashCode(), resource_2.hashCode());
		Assert.assertEquals(resourceStandin_2.hashCode(),
				resourceStandin_1.hashCode());
	}
}
