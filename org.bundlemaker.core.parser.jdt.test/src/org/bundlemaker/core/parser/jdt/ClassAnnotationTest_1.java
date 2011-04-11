package org.bundlemaker.core.parser.jdt;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
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
  protected void testResult(IModularizedSystem modularizedSystem) throws CoreException {

    modularizedSystem.applyTransformations();

    //
    IResourceModule resourceModule = modularizedSystem.getResourceModule(TEST_PROJECT_NAME, "1.0.0");

    IResource resource = resourceModule.getResource("de/test/Klasse.java", ContentType.SOURCE);

    IType type = resource.getContainedType();

    for (IReference reference : type.getReferences()) {
      System.out.println(reference);
    }
  }
}
