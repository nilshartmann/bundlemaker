package org.bundlemaker.core.modules;

import javax.swing.JFrame;

import junit.framework.Assert;

import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeModuleTest {

	/**
	 * <p>
	 * </p>
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testUnmodifiableGetContainedPackages() {

		//
		TypeModule module = new TypeModule(new ModuleIdentifier("name",
				"version"));

		module.getContainedPackages().add("");
	}

	/**
	 * <p>
	 * </p>
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testUnmodifiableGetContainedTypes() {

		//
		TypeModule module = new TypeModule(new ModuleIdentifier("name",
				"version"));

		module.getContainedTypes().add("");
	}

	/**
	 * <p>
	 * </p>
	 */
	public void testUnmodifiableUserAttributes() {

		//
		ITypeModule module = new TypeModule(new ModuleIdentifier("name",
				"version"));

		module.getUserAttributes().put("test", new JFrame());
	}

	/**
	 * <p>
	 * </p>
	 */
	@Test
	public void testGetContainedTypes() {

		//
		TypeModule module = new TypeModule(new ModuleIdentifier("name",
				"version"));

		//
		for (int i = 0; i < 100000; i++) {
			module.getSelfContainer().getModifiableContainedTypes()
					.add("a.b.c" + i);
		}

		//
		Assert.assertEquals(100000, module.getSelfContainer()
				.getModifiableContainedTypes().size());

		Assert.assertEquals(100000, module.getContainedTypes().size());
	}
}
