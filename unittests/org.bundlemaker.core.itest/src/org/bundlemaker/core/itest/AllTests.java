package org.bundlemaker.core.itest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ org.bundlemaker.core.itest.exporter.AllTests.class, org.bundlemaker.core.itest.modules.AllTests.class,
    org.bundlemaker.core.itest.core.AllTests.class, org.bundlemaker.core.itest.analysis.AllTests.class })
public class AllTests {

}
