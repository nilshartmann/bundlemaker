package org.bundlemaker.core.itest.selectors;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SuperTypeSelectorTest.class, SubtypesAndImplementorsSelectorTest.class, TransitiveClosureSelectorTest.class })
public class AllTests {

}
