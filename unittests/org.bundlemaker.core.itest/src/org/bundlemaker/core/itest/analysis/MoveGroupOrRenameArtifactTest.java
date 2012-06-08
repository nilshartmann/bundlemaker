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
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MoveGroupOrRenameArtifactTest extends AbstractModularizedSystemTest {

  /** - */
  private IRootArtifact   _rootArtifact;

  /** - */
  private IModuleArtifact _testModuleArtifact;

  /** - */
  private IGroupArtifact  _group_1_Artifact;

  /** - */
  private IGroupArtifact  _group_2_Artifact;

  /** - */
  private IGroupArtifact  _group_3_Artifact;

  @Override
  protected String computeTestProjectName() {
    return "BasicArtifactTest";
  }

  @Override
  public void before() throws CoreException {
    super.before();

    _rootArtifact = getModularizedSystem().getArtifactModel(
        ArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION);
    Assert.assertEquals(1, _rootArtifact.getChildren(IModuleArtifact.class).size());

    _testModuleArtifact = _rootArtifact.getOrCreateModule("test", "1.0.0");
    Assert.assertEquals(2, _rootArtifact.getChildren(IModuleArtifact.class).size());

    _group_1_Artifact = _rootArtifact.getOrCreateGroup(new Path("new_group_1"));
    Assert.assertEquals(2, _rootArtifact.getChildren(IGroupArtifact.class).size());

    _group_2_Artifact = _rootArtifact.getOrCreateGroup(new Path("new_group_2"));
    Assert.assertEquals(3, _rootArtifact.getChildren(IGroupArtifact.class).size());

    _group_3_Artifact = _rootArtifact.getOrCreateGroup(new Path("new_group_3"));
    Assert.assertEquals(4, _rootArtifact.getChildren(IGroupArtifact.class).size());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void moveGroupArtifactTest() throws Exception {

    // 'move' around
    Assert.assertNull(_testModuleArtifact.getAssociatedModule().getClassification());

    _group_1_Artifact.addArtifact(_testModuleArtifact);
    Assert.assertEquals(new Path("new_group_1"), _testModuleArtifact.getAssociatedModule().getClassification());

    _group_2_Artifact.addArtifact(_testModuleArtifact);
    Assert.assertEquals(new Path("new_group_2"), _testModuleArtifact.getAssociatedModule().getClassification());

    _group_1_Artifact.addArtifact(_group_2_Artifact);
    Assert.assertEquals(new Path("new_group_1/new_group_2"), _testModuleArtifact.getAssociatedModule()
        .getClassification());

    _rootArtifact.addArtifact(_group_2_Artifact);
    Assert.assertEquals(new Path("new_group_2"), _testModuleArtifact.getAssociatedModule().getClassification());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void renameGroupArtifactTest() throws Exception {

    // 'move' around
    Assert.assertNull(_testModuleArtifact.getAssociatedModule().getClassification());
    _group_2_Artifact.addArtifact(_testModuleArtifact);
    Assert.assertEquals(new Path("new_group_2"), _testModuleArtifact.getAssociatedModule().getClassification());
    _group_1_Artifact.addArtifact(_group_2_Artifact);
    Assert.assertEquals(new Path("new_group_1/new_group_2"), _testModuleArtifact.getAssociatedModule()
        .getClassification());

    _group_2_Artifact.setName("renamed");
    Assert.assertEquals(new Path("new_group_1/renamed"), _testModuleArtifact.getAssociatedModule().getClassification());
  }
  
  /**
   * <p>
   * </p>
   *
   * @throws Exception
   */
  @Test(expected=RuntimeException.class)
  public void addParentGroupAsChildArtifactTest() throws Exception {

    // 'move' around
    Assert.assertNull(_testModuleArtifact.getAssociatedModule().getClassification());
    _group_2_Artifact.addArtifact(_testModuleArtifact);
    Assert.assertEquals(new Path("new_group_2"), _testModuleArtifact.getAssociatedModule().getClassification());
    _group_1_Artifact.addArtifact(_group_2_Artifact);
    Assert.assertEquals(new Path("new_group_1/new_group_2"), _testModuleArtifact.getAssociatedModule()
        .getClassification());

    _group_2_Artifact.addArtifact(_group_1_Artifact);
  }
}
