package org.bundlemaker.core.parser.jdt;

import org.bundlemaker.core.itestframework.ExpectedReference;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.parser.AbstractParserTest;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.ReferenceType;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ClassAnnotationTest_1 extends AbstractParserTest {

  /**
   * {@inheritDoc}
   * 
   * @throws CoreException
   */
  @Override
  protected void testResult(IModularizedSystem modularizedSystem, IResourceModule resourceModule) throws CoreException {

    //
    assertResourceWithSingleTypeAndAllReferences(resourceModule, "de/test/Klasse.java", ContentType.SOURCE,
        new ExpectedReference("de.test.Test", ReferenceType.TYPE_REFERENCE, false, false, true));

    //
    assertResourceWithSingleTypeAndAllReferences(resourceModule, "de/test/test.java", ContentType.SOURCE);
  }
}
