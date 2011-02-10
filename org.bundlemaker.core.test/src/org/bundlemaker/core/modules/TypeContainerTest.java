package org.bundlemaker.core.modules;

import java.util.Set;

import junit.framework.Assert;

import org.bundlemaker.core.internal.resource.Type;
import org.bundlemaker.core.resource.TypeEnum;
import org.bundlemaker.core.util.StopWatch;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeContainerTest {

	/**
	 * <p>
	 * </p>
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testUnmodifiableGetContainedPackages() {
		new TypeContainer().getContainedPackageNames().add("");
	}

	/**
	 * <p>
	 * </p>
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testUnmodifiableGetContainedTypes() {
		new TypeContainer().getContainedTypeNames().add("");
	}

	@Test
	public void testPerformance() {

		TypeContainer typeContainer = new TypeContainer();

		//
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for (int i = 0; i < 100000; i++) {
			typeContainer.getModifiableContainedTypesMap().put("a.b.c" + i,
					new Type("a.b.c" + i, TypeEnum.CLASS));
		}
		stopWatch.stop();
		Assert.assertTrue(
				String.format("Elapsed time for creating: %s ms",
						stopWatch.getElapsedTime()),
				stopWatch.getElapsedTime() < 500);

		Assert.assertEquals(100000, typeContainer
				.getModifiableContainedTypesMap().size());

		//
		stopWatch = new StopWatch();
		stopWatch.start();

		Set<String> containedTypes = typeContainer
				.getContainedTypeNames(new IQueryFilter<String>() {

					@Override
					public boolean matches(String content) {
						return content.matches(".*3");
					}
				});

		stopWatch.stop();
		Assert.assertTrue(String.format("Elapsed time: %s ms",
				stopWatch.getElapsedTime()), stopWatch.getElapsedTime() < 500);

		Assert.assertEquals(10000, containedTypes.size());
	}
}
