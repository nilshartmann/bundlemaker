package org.bundlemaker.core.itest.jedit_artifact_model.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AdditionalContentTest.class, ChangeArtifactModelTest.class, ChangeModularizedSystemTest.class,
    LifecycleTest.class })
public class AllTests {

}