package org.bundlemaker.core.itest.analysis;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ org.bundlemaker.core.itest.analysis.simple_artifact_model.AllTests.class,
    org.bundlemaker.core.itest.analysis.jedit_artifact_model.AllTests.class,
    org.bundlemaker.core.itest.analysis.misc_models.AllTests.class })
public class AllTests {

}
