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
import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactHelperTest extends AbstractModularizedSystemTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void testNameAndPath_BINARY() throws Exception {

    // step 1: get the rootArtifact
    IBundleMakerArtifact rootArtifact = getModularizedSystem().getAnalysisModel(
        AnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION);

    // assert the qualified name && the name
    Assert.assertEquals(new Path("BasicArtifactTest"), rootArtifact.getFullPath());
    Assert.assertEquals("BasicArtifactTest", rootArtifact.getUniquePathIdentifier());
    Assert.assertEquals("BasicArtifactTest", rootArtifact.getName());
    Assert.assertEquals("BasicArtifactTest", rootArtifact.getQualifiedName());

    //
    IGroupArtifact group1Artifact = findArtifact(rootArtifact, "group1", IGroupArtifact.class);
    Assert.assertEquals(new Path("BasicArtifactTest/group1"), group1Artifact.getFullPath());
    Assert.assertEquals("group1", group1Artifact.getUniquePathIdentifier());
    Assert.assertEquals("group1", group1Artifact.getName());
    Assert.assertEquals("group1", group1Artifact.getQualifiedName());
    Assert.assertEquals(group1Artifact, ArtifactHelper.getChildByPath(rootArtifact, group1Artifact.getFullPath()
        .removeFirstSegments(1), IGroupArtifact.class));

    //
    IGroupArtifact group2Artifact = findArtifact(rootArtifact, "group2", IGroupArtifact.class);
    Assert.assertEquals(new Path("BasicArtifactTest/group1/group2"), group2Artifact.getFullPath());
    Assert.assertEquals("group2", group2Artifact.getUniquePathIdentifier());
    Assert.assertEquals("group2", group2Artifact.getName());
    Assert.assertEquals("group1/group2", group2Artifact.getQualifiedName());
    Assert.assertEquals(group2Artifact, ArtifactHelper.getChildByPath(rootArtifact, group2Artifact.getFullPath()
        .removeFirstSegments(1), IGroupArtifact.class));

    //
    IModuleArtifact moduleArtifact = findArtifact(rootArtifact, "BasicArtifactTest_1.0.0", IModuleArtifact.class);
    Assert.assertEquals(new Path("BasicArtifactTest/group1/group2/BasicArtifactTest_1.0.0"),
        moduleArtifact.getFullPath());
    Assert.assertEquals("BasicArtifactTest_1.0.0", moduleArtifact.getUniquePathIdentifier());
    Assert.assertEquals("BasicArtifactTest_1.0.0", moduleArtifact.getName());
    Assert.assertEquals("group1/group2/BasicArtifactTest_1.0.0", moduleArtifact.getQualifiedName());
    Assert.assertEquals(moduleArtifact, ArtifactHelper.getChildByPath(rootArtifact, moduleArtifact.getFullPath()
        .removeFirstSegments(1), IModuleArtifact.class));

    //
    IPackageArtifact packageArtifact = findArtifact(rootArtifact, "de.test.basic", IPackageArtifact.class);
    Assert.assertEquals(new Path("BasicArtifactTest/group1/group2/BasicArtifactTest_1.0.0/de.test.basic"),
        packageArtifact.getFullPath());
    Assert.assertEquals("de.test.basic", packageArtifact.getUniquePathIdentifier());
    Assert.assertEquals("basic", packageArtifact.getName());
    Assert.assertEquals("de.test.basic", packageArtifact.getQualifiedName());
    Assert.assertEquals(packageArtifact, ArtifactHelper.getChildByPath(rootArtifact, packageArtifact.getFullPath()
        .removeFirstSegments(1), IPackageArtifact.class));

    //
    IResourceArtifact resourceArtifact = findArtifact(rootArtifact, "TestClass.class", IResourceArtifact.class);
    Assert.assertEquals(new Path(
        "BasicArtifactTest/group1/group2/BasicArtifactTest_1.0.0/de.test.basic/TestClass.class"), resourceArtifact
        .getFullPath());
    Assert.assertEquals("TestClass.class", resourceArtifact.getUniquePathIdentifier());
    Assert.assertEquals("TestClass.class", resourceArtifact.getName());
    Assert.assertEquals("de/test/basic/TestClass.class", resourceArtifact.getQualifiedName());
    Assert.assertEquals(resourceArtifact, ArtifactHelper.getChildByPath(rootArtifact, resourceArtifact.getFullPath()
        .removeFirstSegments(1), IResourceArtifact.class));

    //
    ITypeArtifact typeArtifact = findArtifact(rootArtifact, "de.test.basic.TestClass", ITypeArtifact.class);
    Assert.assertEquals(new Path(
        "BasicArtifactTest/group1/group2/BasicArtifactTest_1.0.0/de.test.basic/TestClass.class/TestClass"),
        typeArtifact.getFullPath());
    Assert.assertEquals("TestClass", typeArtifact.getUniquePathIdentifier());
    Assert.assertEquals("TestClass", typeArtifact.getName());
    Assert.assertEquals("de.test.basic.TestClass", typeArtifact.getQualifiedName());
    Assert.assertEquals(typeArtifact, ArtifactHelper.getChildByPath(rootArtifact, typeArtifact.getFullPath()
        .removeFirstSegments(1), ITypeArtifact.class));
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void testNameAndPath_HIERARCHICAL_BINARY() throws Exception {

    // step 1: get the rootArtifact
    IBundleMakerArtifact rootArtifact = getModularizedSystem().getAnalysisModel(
        AnalysisModelConfiguration.HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION);

    // assert the qualified name && the name
    Assert.assertEquals(new Path("BasicArtifactTest"), rootArtifact.getFullPath());
    Assert.assertEquals("BasicArtifactTest", rootArtifact.getUniquePathIdentifier());
    Assert.assertEquals("BasicArtifactTest", rootArtifact.getName());
    Assert.assertEquals("BasicArtifactTest", rootArtifact.getQualifiedName());

    //
    IGroupArtifact group1Artifact = findArtifact(rootArtifact, "group1", IGroupArtifact.class);
    Assert.assertEquals(new Path("BasicArtifactTest/group1"), group1Artifact.getFullPath());
    Assert.assertEquals("group1", group1Artifact.getUniquePathIdentifier());
    Assert.assertEquals("group1", group1Artifact.getName());
    Assert.assertEquals("group1", group1Artifact.getQualifiedName());
    Assert.assertEquals(group1Artifact, ArtifactHelper.getChildByPath(rootArtifact, group1Artifact.getFullPath()
        .removeFirstSegments(1), IGroupArtifact.class));

    //
    IGroupArtifact group2Artifact = findArtifact(rootArtifact, "group2", IGroupArtifact.class);
    Assert.assertEquals(new Path("BasicArtifactTest/group1/group2"), group2Artifact.getFullPath());
    Assert.assertEquals("group2", group2Artifact.getUniquePathIdentifier());
    Assert.assertEquals("group2", group2Artifact.getName());
    Assert.assertEquals("group1/group2", group2Artifact.getQualifiedName());
    Assert.assertEquals(group2Artifact, ArtifactHelper.getChildByPath(rootArtifact, group2Artifact.getFullPath()
        .removeFirstSegments(1), IGroupArtifact.class));

    //
    IModuleArtifact moduleArtifact = findArtifact(rootArtifact, "BasicArtifactTest_1.0.0", IModuleArtifact.class);
    Assert.assertEquals(new Path("BasicArtifactTest/group1/group2/BasicArtifactTest_1.0.0"),
        moduleArtifact.getFullPath());
    Assert.assertEquals("BasicArtifactTest_1.0.0", moduleArtifact.getUniquePathIdentifier());
    Assert.assertEquals("BasicArtifactTest_1.0.0", moduleArtifact.getName());
    Assert.assertEquals("group1/group2/BasicArtifactTest_1.0.0", moduleArtifact.getQualifiedName());
    Assert.assertEquals(moduleArtifact, ArtifactHelper.getChildByPath(rootArtifact, moduleArtifact.getFullPath()
        .removeFirstSegments(1), IModuleArtifact.class));

    //
    IPackageArtifact packageArtifact = findArtifact(rootArtifact, "de.test.basic", IPackageArtifact.class);
    Assert.assertEquals(new Path("BasicArtifactTest/group1/group2/BasicArtifactTest_1.0.0/de/test/basic"),
        packageArtifact.getFullPath());
    Assert.assertEquals("basic", packageArtifact.getUniquePathIdentifier());
    Assert.assertEquals("basic", packageArtifact.getName());
    Assert.assertEquals("de.test.basic", packageArtifact.getQualifiedName());
    Assert.assertEquals(packageArtifact, ArtifactHelper.getChildByPath(rootArtifact, packageArtifact.getFullPath()
        .removeFirstSegments(1), IPackageArtifact.class));

    //
    IResourceArtifact resourceArtifact = findArtifact(rootArtifact, "TestClass.class", IResourceArtifact.class);
    Assert.assertEquals(new Path(
        "BasicArtifactTest/group1/group2/BasicArtifactTest_1.0.0/de/test/basic/TestClass.class"), resourceArtifact
        .getFullPath());
    Assert.assertEquals("TestClass.class", resourceArtifact.getUniquePathIdentifier());
    Assert.assertEquals("TestClass.class", resourceArtifact.getName());
    Assert.assertEquals("de/test/basic/TestClass.class", resourceArtifact.getQualifiedName());
    Assert.assertEquals(resourceArtifact, ArtifactHelper.getChildByPath(rootArtifact, resourceArtifact.getFullPath()
        .removeFirstSegments(1), IResourceArtifact.class));

    //
    ITypeArtifact typeArtifact = findArtifact(rootArtifact, "de.test.basic.TestClass", ITypeArtifact.class);
    Assert.assertEquals(new Path(
        "BasicArtifactTest/group1/group2/BasicArtifactTest_1.0.0/de/test/basic/TestClass.class/TestClass"),
        typeArtifact.getFullPath());
    Assert.assertEquals("TestClass", typeArtifact.getUniquePathIdentifier());
    Assert.assertEquals("TestClass", typeArtifact.getName());
    Assert.assertEquals("de.test.basic.TestClass", typeArtifact.getQualifiedName());
    Assert.assertEquals(typeArtifact, ArtifactHelper.getChildByPath(rootArtifact, typeArtifact.getFullPath()
        .removeFirstSegments(1), ITypeArtifact.class));
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void testNameAndPath_AGGREGATE_INNER_TYPES_NO_RESOURCES() throws Exception {

    // step 1: get the rootArtifact
    IBundleMakerArtifact rootArtifact = getModularizedSystem().getAnalysisModel(
        AnalysisModelConfiguration.SOURCE_RESOURCES_CONFIGURATION);

    // assert the qualified name && the name
    Assert.assertEquals(new Path("BasicArtifactTest"), rootArtifact.getFullPath());
    Assert.assertEquals("BasicArtifactTest", rootArtifact.getUniquePathIdentifier());
    Assert.assertEquals("BasicArtifactTest", rootArtifact.getName());
    Assert.assertEquals("BasicArtifactTest", rootArtifact.getQualifiedName());

    //
    IGroupArtifact group1Artifact = findArtifact(rootArtifact, "group1", IGroupArtifact.class);
    Assert.assertEquals(new Path("BasicArtifactTest/group1"), group1Artifact.getFullPath());
    Assert.assertEquals("group1", group1Artifact.getUniquePathIdentifier());
    Assert.assertEquals("group1", group1Artifact.getName());
    Assert.assertEquals("group1", group1Artifact.getQualifiedName());
    Assert.assertEquals(group1Artifact, ArtifactHelper.getChildByPath(rootArtifact, group1Artifact.getFullPath()
        .removeFirstSegments(1), IGroupArtifact.class));

    //
    IGroupArtifact group2Artifact = findArtifact(rootArtifact, "group2", IGroupArtifact.class);
    Assert.assertEquals(new Path("BasicArtifactTest/group1/group2"), group2Artifact.getFullPath());
    Assert.assertEquals("group2", group2Artifact.getUniquePathIdentifier());
    Assert.assertEquals("group2", group2Artifact.getName());
    Assert.assertEquals("group1/group2", group2Artifact.getQualifiedName());
    Assert.assertEquals(group2Artifact, ArtifactHelper.getChildByPath(rootArtifact, group2Artifact.getFullPath()
        .removeFirstSegments(1), IGroupArtifact.class));

    //
    IModuleArtifact moduleArtifact = findArtifact(rootArtifact, "BasicArtifactTest_1.0.0", IModuleArtifact.class);
    Assert.assertEquals(new Path("BasicArtifactTest/group1/group2/BasicArtifactTest_1.0.0"),
        moduleArtifact.getFullPath());
    Assert.assertEquals("BasicArtifactTest_1.0.0", moduleArtifact.getUniquePathIdentifier());
    Assert.assertEquals("BasicArtifactTest_1.0.0", moduleArtifact.getName());
    Assert.assertEquals("group1/group2/BasicArtifactTest_1.0.0", moduleArtifact.getQualifiedName());
    Assert.assertEquals(moduleArtifact, ArtifactHelper.getChildByPath(rootArtifact, moduleArtifact.getFullPath()
        .removeFirstSegments(1), IModuleArtifact.class));

    //
    IPackageArtifact packageArtifact = findArtifact(rootArtifact, "de.test.basic", IPackageArtifact.class);
    Assert.assertEquals(new Path("BasicArtifactTest/group1/group2/BasicArtifactTest_1.0.0/de.test.basic"),
        packageArtifact.getFullPath());
    Assert.assertEquals("de.test.basic", packageArtifact.getUniquePathIdentifier());
    Assert.assertEquals("basic", packageArtifact.getName());
    Assert.assertEquals("de.test.basic", packageArtifact.getQualifiedName());
    Assert.assertEquals(packageArtifact, ArtifactHelper.getChildByPath(rootArtifact, packageArtifact.getFullPath()
        .removeFirstSegments(1), IPackageArtifact.class));

    //
    ITypeArtifact typeArtifact = findArtifact(rootArtifact, "de.test.basic.TestClass", ITypeArtifact.class);
    Assert.assertEquals(new Path("BasicArtifactTest/group1/group2/BasicArtifactTest_1.0.0/de.test.basic/TestClass.java/TestClass"),
        typeArtifact.getFullPath());
    Assert.assertEquals("TestClass", typeArtifact.getUniquePathIdentifier());
    Assert.assertEquals("TestClass", typeArtifact.getName());
    Assert.assertEquals("de.test.basic.TestClass", typeArtifact.getQualifiedName());
    Assert.assertEquals(typeArtifact, ArtifactHelper.getChildByPath(rootArtifact, typeArtifact.getFullPath()
        .removeFirstSegments(1), ITypeArtifact.class));
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void testNameAndPath_WildCards() throws Exception {

    // step 1: get the rootArtifact
    IBundleMakerArtifact rootArtifact = getModularizedSystem().getAnalysisModel(
        AnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION);

    //
    IGroupArtifact group1Artifact = findArtifact(rootArtifact, "g*1", IGroupArtifact.class);
    Assert.assertNotNull(group1Artifact);

    //
    IGroupArtifact group2Artifact = findArtifact(rootArtifact, "g*2", IGroupArtifact.class);
    Assert.assertNotNull(group2Artifact);

    //
    IModuleArtifact moduleArtifact = findArtifact(rootArtifact, "BasicArtifactTest*", IModuleArtifact.class);
    Assert.assertNotNull(moduleArtifact);

    //
    IPackageArtifact packageArtifact = findArtifact(rootArtifact, "de.*.basic", IPackageArtifact.class);
    Assert.assertNotNull(packageArtifact);

    //
    IResourceArtifact resourceArtifact = findArtifact(rootArtifact, "Test*.class", IResourceArtifact.class);
    Assert.assertNotNull(resourceArtifact);

    //
    ITypeArtifact typeArtifact = findArtifact(rootArtifact, "de.**.TestClass", ITypeArtifact.class);
    Assert.assertNotNull(typeArtifact);
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
  public <T extends IBundleMakerArtifact> T findArtifact(IBundleMakerArtifact rootArtifact, String name, Class<T> clazz) {
    List<T> moduleArtifacts = ArtifactHelper.findChildren(rootArtifact, name, clazz);
    Assert.assertEquals(1, moduleArtifacts.size());
    return moduleArtifacts.get(0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return BasicArtifactTest.class.getSimpleName();
  }
}
