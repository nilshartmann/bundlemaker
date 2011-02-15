package org.bundlemaker.core.modules;

import org.bundlemaker.core.internal.modules.ResourceContainer;
import org.bundlemaker.core.projectdescription.ContentType;
import org.junit.Test;

public class ResourceContainerTest {

	@Test(expected = UnsupportedOperationException.class)
	public void testUnmodifiableGetContainedPackages() {
		new ResourceContainer().getContainedPackageNames().add("");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUnmodifiableGetContainedTypes() {
		new ResourceContainer().getContainedTypeNames().add("");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUnmodifiableGetReferencedPackages() {
		new ResourceContainer().getReferencedPackageNames(false, false, false).add("");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUnmodifiableGetReferencedTypes() {
		new ResourceContainer().getReferencedTypeNames(false, false, false).add("");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUnmodifiableGetResources() {
		new ResourceContainer().getResources(ContentType.BINARY).add(null);
	}
}
