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

import static org.junit.Assert.assertEquals;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BasicArtifactTest extends AbstractModularizedSystemTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void qualifiedNameWithFlatPackages() throws Exception {

    // step 1: get the rootArtifact
    IBundleMakerArtifact rootArtifact = getModularizedSystem().getArtifactModel(
        ArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION);

    // step 2: get the package child 
    IBundleMakerArtifact artifact = rootArtifact.getChild("group1|group2|BasicArtifactTest_1.0.0|de.test.basic");
    Assert.assertNotNull(artifact);

    // step 3: assert result
    assertEquals("de.test.basic", artifact.getQualifiedName());
  }

  /**
   * <p>
   * </p>
   *
   * @throws Exception
   */
  @Test
  public void qualifiedNameWithHierarchicalPackages() throws Exception {

    // step 1: get the rootArtifact
    IBundleMakerArtifact rootArtifact = getModularizedSystem().getArtifactModel(
        ArtifactModelConfiguration.HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION);

    // step 2: get the package child 
    IBundleMakerArtifact artifact = rootArtifact.getChild("group1|group2|BasicArtifactTest_1.0.0|de|test|basic");
    Assert.assertNotNull(artifact);

    // step 3: assert result
    assertEquals("de.test.basic", artifact.getQualifiedName());
  }
}
