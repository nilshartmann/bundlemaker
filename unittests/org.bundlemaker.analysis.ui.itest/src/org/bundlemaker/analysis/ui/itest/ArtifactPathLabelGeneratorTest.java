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
package org.bundlemaker.analysis.ui.itest;

import static org.junit.Assert.assertEquals;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.ui.dependencyview.ArtifactPathLabelGenerator;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration.ResourcePresentation;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.projectdescription.ContentType;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactPathLabelGeneratorTest extends AbstractModularizedSystemTest {

  // /*<code>
  // *
  // * Bsp 1:
  // * Selection: Root|GROUP1|GROUP2|bundle1
  // * Artifact: Root|GROUP1|GROUP2|bundle1|de|test|Main
  // *
  // * => Titel: GROUP1|GROUP2|bundle1
  // * => Label: de.test.Main (Achtung! Packagename, nicht Pfad!)
  // *
  // * Bsp 2:
  // * Selection: Root|GROUP1|GROUP2|bundle1|de|test
  // * Artifact: Root|GROUP1|GROUP2|bundle1|de|test|Main
  // *
  // * => Titel: GROUP1|GROUP2|bundle1
  // * => Label: de.test.Main (Achtung! Packagename, nicht Pfad!)
  // *
  // * Bsp 2a:
  // * Selection: Root|GROUP1|GROUP2|bundle1|de
  // * Artifact: Root|GROUP1|GROUP2|bundle1|de|test|Main
  // *
  // * => Titel: GROUP1/GROUP2|bundle1
  // * => Label: de.test.Main (Achtung! Packagename, nicht Pfad!)
  // *
  // * Bsp 3:
  // * Selection: Root|GROUP1|GROUP2
  // * Artifact1: Root|GROUP1|GROUP2|bundle1|de|test|Main
  // * Artifact2: Root|GROUP1|GROUP2|bundle2|de|test|Zwei
  // * => Titel: GROUP1/GROUP2
  // * => Label1: bundle1/de.test.Main
  // * => Label2: bundle2/de.test.Main
  // *
  // * Bsp 3:
  // * Selection: Root|GROUP1|GROUP2
  // * Artifact1: Root|GROUP1|GROUP2|bundle1|de|test|Main
  // * Artifact2: Root|GROUP1|GROUP2|GROUP3|bundle2|de|test|Zwei
  // * => Titel: GROUP1|GROUP2
  // * => Label1: bundle1/de.test.Main
  // * => Label2: GROUP3/bundle2/de.test.Main
  // * </code>
  // * Bsp 3:
  // * Selection: Root
  // * Artifact1: Root|GROUP1|GROUP2|bundle1|de|test|Main
  // * Artifact2: Root|GROUP1|GROUP2|GROUP3|bundle2|de|test|Zwei
  // * => Titel: Root|GROUP1|GROUP2
  // * => Label1: bundle1/de.test.Main
  // * => Label2: GROUP3/bundle2/de.test.Main
  // * </code>

  // */

  @Test
  public void test_generateTitlesForFlatPackages_NoResources() {
    IAdvancedArtifact rootArtifact = getRootArtifact(false);

    IArtifact typeArtifact = rootArtifact
        .getChild("group1|group2|ArtifactPathLabelGeneratorTest_1.0.0|de.test.basic|TestClass");
    Assert.assertNotNull(typeArtifact);

    ArtifactPathLabelGenerator generator = new ArtifactPathLabelGenerator();

    // Artifact is Type -> Title ends with bundle name, Label starts with package
    generator.setBaseArtifact(typeArtifact);
    String title = generator.getTitle();
    assertEquals("group1/group2/ArtifactPathLabelGeneratorTest_1.0.0", title);
    String label = generator.getLabel(typeArtifact);
    assertEquals("de.test.basic.TestClass", label);

    // Artifact is a package -> Title ends with bundle name, Label starts with package
    IArtifact packageArtifact = typeArtifact.getParent();
    generator.setBaseArtifact(packageArtifact);
    title = generator.getTitle();
    assertEquals("group1/group2/ArtifactPathLabelGeneratorTest_1.0.0", title);
    label = generator.getLabel(typeArtifact);
    assertEquals("de.test.basic.TestClass", label);

    // Artifact is a bundle -> Title ends with bundle name, Label starts with package
    IArtifact bundleArtifact = packageArtifact.getParent(ArtifactType.Module);
    generator.setBaseArtifact(bundleArtifact);
    title = generator.getTitle();
    assertEquals("group1/group2/ArtifactPathLabelGeneratorTest_1.0.0", title);
    label = generator.getLabel(typeArtifact);
    assertEquals("de.test.basic.TestClass", label);

    // Artifact is a group -> Title ends with group name, label starts with bundle name
    IArtifact groupArtifact = bundleArtifact.getParent();
    generator.setBaseArtifact(groupArtifact);
    title = generator.getTitle();
    assertEquals("group1/group2", title);
    label = generator.getLabel(typeArtifact);
    assertEquals("ArtifactPathLabelGeneratorTest_1.0.0/de.test.basic.TestClass", label);

    // Artifact is first group -> Title ends with first group name, label starts with second group name
    IArtifact firstGroupArtifact = groupArtifact.getParent();
    generator.setBaseArtifact(firstGroupArtifact);
    title = generator.getTitle();
    assertEquals("group1", title);
    label = generator.getLabel(typeArtifact);
    assertEquals("group2/ArtifactPathLabelGeneratorTest_1.0.0/de.test.basic.TestClass", label);

    // Artifact is root -> Title is typeArtifact name, label starts with first group
    generator.setBaseArtifact(rootArtifact);
    title = generator.getTitle();
    assertEquals(rootArtifact.getName(), title);
    label = generator.getLabel(typeArtifact);
    assertEquals("group1/group2/ArtifactPathLabelGeneratorTest_1.0.0/de.test.basic.TestClass", label);
  }

  @Test
  public void test_generateTitlesForHierarchicalPackages_NoResources() {
    IAdvancedArtifact rootArtifact = getRootArtifact(true);

    IArtifact typeArtifact = rootArtifact
        .getChild("group1|group2|ArtifactPathLabelGeneratorTest_1.0.0|de|test|basic|TestClass");
    Assert.assertNotNull(typeArtifact);

    ArtifactPathLabelGenerator generator = new ArtifactPathLabelGenerator();

    // Artifact is Type -> Title ends with bundle name, Label starts with package
    generator.setBaseArtifact(typeArtifact);
    String title = generator.getTitle();
    assertEquals("group1/group2/ArtifactPathLabelGeneratorTest_1.0.0", title);
    String label = generator.getLabel(typeArtifact);
    assertEquals("de.test.basic.TestClass", label);

    // Artifact is a package -> Title ends with bundle name, Label starts with package
    IArtifact packageArtifact = typeArtifact.getParent();
    generator.setBaseArtifact(packageArtifact);
    title = generator.getTitle();
    assertEquals("group1/group2/ArtifactPathLabelGeneratorTest_1.0.0", title);
    label = generator.getLabel(typeArtifact);
    assertEquals("de.test.basic.TestClass", label);

    // Artifact is first package -> Title ends with bundle name, Label starts with package
    IArtifact testPackageArtifact = packageArtifact.getParent();
    assertEquals("test", testPackageArtifact.getName());
    generator.setBaseArtifact(testPackageArtifact);
    title = generator.getTitle();
    assertEquals("group1/group2/ArtifactPathLabelGeneratorTest_1.0.0", title);
    label = generator.getLabel(typeArtifact);
    assertEquals("de.test.basic.TestClass", label);

    // Artifact is a bundle -> Title ends with bundle name, Label starts with package
    IArtifact bundleArtifact = testPackageArtifact.getParent(ArtifactType.Module);
    generator.setBaseArtifact(bundleArtifact);
    title = generator.getTitle();
    assertEquals("group1/group2/ArtifactPathLabelGeneratorTest_1.0.0", title);
    label = generator.getLabel(typeArtifact);
    assertEquals("de.test.basic.TestClass", label);

    // Artifact is a group -> Title ends with group name, label starts with bundle name
    IArtifact groupArtifact = bundleArtifact.getParent();
    generator.setBaseArtifact(groupArtifact);
    title = generator.getTitle();
    assertEquals("group1/group2", title);
    label = generator.getLabel(typeArtifact);
    assertEquals("ArtifactPathLabelGeneratorTest_1.0.0/de.test.basic.TestClass", label);

    // Artifact is first group -> Title ends with first group name, label starts with second group name
    IArtifact firstGroupArtifact = groupArtifact.getParent();
    generator.setBaseArtifact(firstGroupArtifact);
    title = generator.getTitle();
    assertEquals("group1", title);
    label = generator.getLabel(typeArtifact);
    assertEquals("group2/ArtifactPathLabelGeneratorTest_1.0.0/de.test.basic.TestClass", label);

    // Artifact is root -> Title is typeArtifact name, label starts with first group
    generator.setBaseArtifact(rootArtifact);
    title = generator.getTitle();
    assertEquals(rootArtifact.getName(), title);
    label = generator.getLabel(typeArtifact);
    assertEquals("group1/group2/ArtifactPathLabelGeneratorTest_1.0.0/de.test.basic.TestClass", label);
  }

  protected IAdvancedArtifact getRootArtifact(boolean hierarchical) {

    ArtifactModelConfiguration artifactModelConfiguration = new ArtifactModelConfiguration();
    artifactModelConfiguration.setHierarchicalPackages(hierarchical);
    artifactModelConfiguration.setResourcePresentation(ResourcePresentation.NO_RESOURCE);
    artifactModelConfiguration.setContentType(ContentType.BINARY);
    artifactModelConfiguration.setAggregateInnerTypes(false);
    artifactModelConfiguration.setIncludeVirtualModuleForMissingTypes(true);

    IAdvancedArtifact rootArtifact = (IAdvancedArtifact) ModelTransformer.getDependencyModel(getModularizedSystem(),
        artifactModelConfiguration).getRoot();

    Assert.assertNotNull(rootArtifact);

    return rootArtifact;

  }

}
