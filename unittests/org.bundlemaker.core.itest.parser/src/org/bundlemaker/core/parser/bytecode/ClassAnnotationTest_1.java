package org.bundlemaker.core.parser.bytecode;

import static org.junit.Assert.assertNotNull;

import org.bundlemaker.core.itestframework.ExpectedReference;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.parser.AbstractParserTest;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
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

    // get 'de/test/Klasse.java'
    IResource resource = resourceModule.getResource("de/test/Klasse.class", ProjectContentType.BINARY);
    assertNotNull(resource);

    IType type = resource.getContainedType();
    assertNotNull(type);

    //
    assertAllReferences(type, new ExpectedReference("de.test.Test", ReferenceType.TYPE_REFERENCE, false, false, true));
  }
}
