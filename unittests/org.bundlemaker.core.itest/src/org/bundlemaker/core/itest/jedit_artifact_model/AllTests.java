package org.bundlemaker.core.itest.jedit_artifact_model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    org.bundlemaker.core.itest.jedit_artifact_model.analysis.AllTests.class,
    org.bundlemaker.core.itest.jedit_artifact_model.analysis.algorithm.AllTests.class })
public class AllTests {

}
