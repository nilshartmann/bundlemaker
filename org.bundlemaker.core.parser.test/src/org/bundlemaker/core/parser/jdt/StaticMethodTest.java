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
public class StaticMethodTest extends AbstractParserTest {

  /**
   * {@inheritDoc}
   * 
   * @throws CoreException
   */
  @Override
  protected void testResult(IModularizedSystem modularizedSystem, IResourceModule resourceModule) throws CoreException {

    //
    ExpectedReference[] expectedReferences = new ExpectedReference[] {
        new ExpectedReference("de.test.Test", ReferenceType.TYPE_REFERENCE, false, false, false),
        new ExpectedReference("javax.activation.DataHandler", ReferenceType.TYPE_REFERENCE, false, false, false) };

    assertResourceWithSingleTypeAndAllReferences(resourceModule, "de/test/Klasse.java", ContentType.SOURCE,
        expectedReferences);

    assertResourceWithSingleTypeAndAllReferences(resourceModule, "de/test/Test.java", ContentType.SOURCE);
  }
}
