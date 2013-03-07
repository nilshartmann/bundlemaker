package org.bundlemaker.core.itest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    org.bundlemaker.core.itest.misc_models.AllTests.class,
    org.bundlemaker.core.itest.simple_artifact_model.AllTests.class,
    org.bundlemaker.core.itest.jedit_artifact_model.AllTests.class })
public class AllTests {

}
