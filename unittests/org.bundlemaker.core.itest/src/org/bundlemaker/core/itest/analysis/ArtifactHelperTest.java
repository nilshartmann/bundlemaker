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

import java.util.List;

import org.bundlemaker.core.analysis.ArtifactHelper;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactHelperTest extends AbstractModularizedSystemTest {

  @Test
  public void getQualifiedName() throws Exception {

    // step 1: get the rootArtifact
    IBundleMakerArtifact rootArtifact = getModularizedSystem().getArtifactModel(
        ArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION);

    // assert the qualified name && the name
    Assert.assertEquals("BasicArtifactTest", rootArtifact.getQualifiedName());
    Assert.assertEquals("BasicArtifactTest", rootArtifact.getName());

    //
    assertArtifact(rootArtifact, "[group1]", "group1", IGroupArtifact.class);

    //
    assertArtifact(rootArtifact, "[group2]", "group2", IGroupArtifact.class);
    assertArtifact(rootArtifact, "[group2]", "group1/group2", IGroupArtifact.class);

    //
    assertArtifact(rootArtifact, "[BasicArtifactTest_1.0.0]", "group1/group2/BasicArtifactTest_1.0.0",
        IModuleArtifact.class);
    assertArtifact(rootArtifact, "[BasicArtifactTest_1.0.0]", "BasicArtifactTest_1.0.0", IModuleArtifact.class);
    assertArtifact(rootArtifact, "[BasicArtifactTest_1.0.0]", "BasicArtifactTest", IModuleArtifact.class);

    //
    assertArtifact(rootArtifact, "[BasicArtifactTest_1.0.0]", "group1/group2/BasicArtifactTest_1.0.0",
        IModuleArtifact.class);
    assertArtifact(rootArtifact, "[BasicArtifactTest_1.0.0]", "BasicArtifactTest_1.0.0", IModuleArtifact.class);
    assertArtifact(rootArtifact, "[BasicArtifactTest_1.0.0]", "BasicArtifactTest", IModuleArtifact.class);

    //
    assertArtifact(rootArtifact, "[basic]", "de.test.basic", IPackageArtifact.class);
    assertArtifact(rootArtifact, "[basic]", "basic", IPackageArtifact.class);

    Assert
        .assertEquals(1, ArtifactHelper.findChildren(rootArtifact, "TestClass.class", IResourceArtifact.class).size());
    Assert.assertEquals(1, ArtifactHelper.findChildren(rootArtifact, "de.test.basic.TestClass", ITypeArtifact.class)
        .size());
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootArtifact
   * @param result
   * @param name
   * @param clazz
   */
  public void assertArtifact(IBundleMakerArtifact rootArtifact, String result, String name,
      Class<? extends IBundleMakerArtifact> clazz) {

    //
    List<? extends IBundleMakerArtifact> moduleArtifacts = ArtifactHelper.findChildren(rootArtifact, name, clazz);

    // assert
    for (IBundleMakerArtifact iBundleMakerArtifact : moduleArtifacts) {
      System.out.println(iBundleMakerArtifact.getQualifiedName());
    }

    Assert.assertEquals(1, moduleArtifacts.size());
    Assert.assertEquals(result, moduleArtifacts.toString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return BasicArtifactTest.class.getSimpleName();
  }

  // @Test
  // public void getChildren() throws Exception {
  //
  // // step 1: get the rootArtifact
  // IBundleMakerArtifact rootArtifact = getModularizedSystem().getArtifactModel(
  // ArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION);
  //
  // // step 2: get the package child
  // List<? extends IModuleArtifact> artifacts = rootArtifact.findChildren(".*BasicArtifactTest.*",
  // IModuleArtifact.class);
  // for (IModuleArtifact artifact : artifacts) {
  // System.out.println(artifact.getQualifiedName());
  // }
  //
  // //
  // Assert.assertNotNull(artifacts);
  // assertEquals(1, artifacts.size());
  // }

}
