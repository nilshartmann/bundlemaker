package org.bundlemaker.core.resource;

import org.bundlemaker.core.projectdescription.ResourceContent;
import org.bundlemaker.core.spi.resource.ArchiveFileCache;
import org.bundlemaker.core.spi.resource.ResourceStandin;
import org.junit.Assert;
import org.junit.Test;

public class ResourceContentTest {

	/**
	 * <p>
	 * </p>
	 */
	@Test(timeout = 500)
	public void testResourceContent() {

		ResourceContent content = new ResourceContent();

		for (int i = 0; i < 100000; i++) {

			content.getModifiableBinaryResources().add(
					new ResourceStandin("id", "root", "path" + i, new ArchiveFileCache()));
		}

		Assert.assertEquals(100000, content.getModifiableBinaryResources()
				.size());
	}
}
