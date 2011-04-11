package org.bundlemaker.core.parser.jdt;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.parser.test.AbstractParserTest;
import org.bundlemaker.core.parser.test.ExpectedReference;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.ReferenceType;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ClassAnnotationTest_2 extends AbstractParserTest {

  /**
   * {@inheritDoc}
   * 
   * @throws CoreException
   */
  @Override
  protected void testResult(IModularizedSystem modularizedSystem, IResourceModule resourceModule) throws CoreException {

    //
    ExpectedReference[] expectedReferences = new ExpectedReference[] {
        // class annotation
        new ExpectedReference("de.test.Test", ReferenceType.TYPE_REFERENCE, false, false, true),
        // implements
        new ExpectedReference("javax.activation.CommandObject", ReferenceType.TYPE_REFERENCE, false, true, false),
        // implements
        new ExpectedReference("de.test.TestInterface", ReferenceType.TYPE_REFERENCE, false, true, false),
        new ExpectedReference("javax.activation.DataHandler", ReferenceType.TYPE_REFERENCE, false, false, false) };

    assertResourceWithSingleTypeAndAllReferences(resourceModule, "de/test/Klasse.java", ContentType.SOURCE,
        expectedReferences);

    //
    assertResourceWithSingleTypeAndAllReferences(resourceModule, "de/test/TestInterface.java", ContentType.SOURCE,
        new ExpectedReference("de.test.Test", ReferenceType.TYPE_REFERENCE, false, false, true));

    //
    assertResourceWithSingleTypeAndAllReferences(resourceModule, "de/test/Test.java", ContentType.SOURCE);
  }
}
