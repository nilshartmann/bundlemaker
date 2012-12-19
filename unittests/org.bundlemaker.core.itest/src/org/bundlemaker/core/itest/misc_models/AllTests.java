package org.bundlemaker.core.itest.misc_models;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AmbiguousTypesTest.class, BasicArtifactTest.class, InnerClassTest.class, NoPrimaryTypeTest.class,
    REFACTOR_ME_Test.class, SimpleArtifact_BINARY_RESOURCES_CONFIGURATION_Test.class, SimpleRemoveTest.class })
public class AllTests {

}
