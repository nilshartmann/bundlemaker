package org.bundlemaker.core.parser.bytecode;

import org.bundlemaker.core.itestframework.ExpectedReference;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.parser.AbstractParserTest;
import org.bundlemaker.core.projectdescription.ProjectContentType;
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

    assertResourceWithSingleTypeAndAllReferences(resourceModule, "de/test/Klasse.class", ProjectContentType.BINARY,
        expectedReferences);

    assertResourceWithSingleTypeAndAllReferences(resourceModule, "de/test/Test.class", ProjectContentType.BINARY);
  }
}
