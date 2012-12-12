package org.bundlemaker.core.itest.analysis.jedit_artifact_model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ArtifactTreeVisitorTest.class, ComplexArtifactTreeTest.class, GetOrCreateNewModule.class,
    MissingTypesTest.class, RemodularizeJEditTestModule.class, TransitiveClosureTest.class })
public class AllTests {

}
