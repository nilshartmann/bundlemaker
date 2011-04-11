package org.bundlemaker.core.parser.jdt;

import static org.junit.Assert.assertNotNull;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.parser.test.AbstractParserTest;
import org.bundlemaker.core.parser.test.ExpectedReference;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
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
public class ClassAnnotationTest_2 extends AbstractParserTest {

  /**
   * {@inheritDoc}
   * 
   * @throws CoreException
   */
  @Override
  protected void testResult(IModularizedSystem modularizedSystem, IResourceModule resourceModule) throws CoreException {

    // // IResource resource = resourceModule.getResource("de/test/Klasse.java", ContentType.SOURCE);
    // // IType type = resource.getContainedType();
    // // for (IReference reference : type.getReferences()) {
    // // System.out.println(reference);
    // // }
    //
    // //
    // IResource resource = resourceModule.getResource("de/test/TestInterface.java", ContentType.SOURCE);
    //
    // //
    // System.out.println(" + " + resource.getPath());
    //
    // // check the resource
    // for (IReference reference : resource.getReferences()) {
    // System.out.println(" - " + reference);
    // }
    //
    // // check the types
    // IType type = resource.getContainedType();
    // for (IReference reference : type.getReferences()) {
    // System.out.println(" ~ " + reference);
    // }
    //
    // //
    // resource = resourceModule.getResource("de/test/Klasse.java", ContentType.SOURCE);
    //
    // //
    // System.out.println(" + " + resource.getPath());
    //
    // // check the resource
    // for (IReference reference : resource.getReferences()) {
    // System.out.println(" - " + reference);
    // }
    //
    // // check the types
    // type = resource.getContainedType();
    // for (IReference reference : type.getReferences()) {
    // System.out.println(" ~ " + reference);
    // }
    //
    // //
    // resource = resourceModule.getResource("de/test/test.java", ContentType.SOURCE);
    //
    // //
    // System.out.println(" + " + resource.getPath());
    //
    // // check the resource
    // for (IReference reference : resource.getReferences()) {
    // System.out.println(" - " + reference);
    // }
    //
    // // check the types
    // type = resource.getContainedType();
    // for (IReference reference : type.getReferences()) {
    // System.out.println(" ~ " + reference);
    // }

    IResource resource = resourceModule.getResource("de/test/Klasse.java", ContentType.SOURCE);
    assertNotNull(resource);

    IType type = resource.getContainedType();
    assertNotNull(type);

    ExpectedReference[] expectedReferences = new ExpectedReference[] {
        new ExpectedReference("de.test.Test", ReferenceType.TYPE_REFERENCE, false, false, true),
        new ExpectedReference("javax.activation.DataHandler", ReferenceType.TYPE_REFERENCE, false, false, false),
        new ExpectedReference("javax.activation.CommandObject", ReferenceType.TYPE_REFERENCE, false, true, false),
        new ExpectedReference("de.test.TestInterface", ReferenceType.TYPE_REFERENCE, false, true, false) };

    //
    assertAllReferences(type, expectedReferences);
  }
}
