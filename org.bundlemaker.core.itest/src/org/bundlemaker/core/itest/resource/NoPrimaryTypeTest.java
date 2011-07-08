package org.bundlemaker.core.itest.resource;

import java.io.IOException;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class NoPrimaryTypeTest extends AbstractModularizedSystemTest {

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * @throws IOException
   */
  @Test
  public void testNoPrimaryType() throws CoreException, IOException {

    // step 1: get the test module
    IResourceModule resourceModule = getModularizedSystem().getResourceModule(NoPrimaryTypeTest.class.getSimpleName(),
        "1.0.0");
    Assert.assertNotNull(resourceModule);

    // get the non-primary type
    IType noPrimaryType = resourceModule.getType("org.bundlemaker.noprimarytype.NoPrimaryTestInterface");
    Assert.assertNotNull(noPrimaryType);

    //
    Assert.assertFalse(noPrimaryType.isPrimaryType());

    IType type = noPrimaryType.getSourceResource().getPrimaryType();
    Assert.assertNull(type);

    Assert.assertFalse(noPrimaryType.getSourceResource().hasPrimaryType());
  }
}
