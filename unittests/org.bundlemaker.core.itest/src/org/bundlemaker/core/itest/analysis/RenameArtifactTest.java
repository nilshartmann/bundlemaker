/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.itest.analysis;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class RenameArtifactTest extends AbstractModularizedSystemTest {

  @Override
  protected String computeTestProjectName() {
    return "BasicArtifactTest";
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void renameArtifactTest() throws Exception {

    // step 1: get the rootArtifact
    IRootArtifact rootArtifact = getModularizedSystem().getArtifactModel(
        ArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION);
    Assert.assertEquals(1, rootArtifact.getChildren(IModuleArtifact.class).size());

    //
    IModuleArtifact testModuleArtifact = rootArtifact.getOrCreateModule("testModule", "1.0.0");
    Assert.assertEquals(2, rootArtifact.getChildren(IModuleArtifact.class).size());

    //
    testModuleArtifact.setNameAndVersion("festModule", "2.0.0");
    Assert.assertEquals(2, rootArtifact.getChildren(IModuleArtifact.class).size());
    Assert.assertEquals("festModule_2.0.0", testModuleArtifact.getAssociatedModule().getModuleIdentifier().toString());
    Assert.assertEquals("festModule_2.0.0", testModuleArtifact.getName());

    //
    IPackageArtifact packageArtifact = ArtifactVisitorUtils.findPackageArtifact(rootArtifact, "de.test.basic");
    Assert.assertEquals(2, rootArtifact.getChildren(IModuleArtifact.class).size());
    testModuleArtifact.addArtifact(packageArtifact);
    Assert.assertEquals(2, rootArtifact.getChildren(IModuleArtifact.class).size());
  }
}
