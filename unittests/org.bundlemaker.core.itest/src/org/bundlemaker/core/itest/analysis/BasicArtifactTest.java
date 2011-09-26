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

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration.ResourcePresentation;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.projectdescription.ContentType;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BasicArtifactTest extends AbstractModularizedSystemTest {
  
  @Test
  public void qualifiedNameWithFlatPackages() throws Exception {
    IBundleMakerArtifact rootArtifact = getRootArtifact(false);

    IArtifact artifact = rootArtifact.getChild("group1|group2|BasicArtifactTest_1.0.0|de.test.basic");

    Assert.assertNotNull(artifact);
    
    assertEquals("de.test.basic", artifact.getQualifiedName());
  }
  
  @Test
  public void qualifiedNameWithHierarchicalPackages() throws Exception {
    IBundleMakerArtifact rootArtifact = getRootArtifact(true);

    IArtifact artifact = rootArtifact.getChild("group1|group2|BasicArtifactTest_1.0.0|de|test|basic");

    Assert.assertNotNull(artifact);
    
    assertEquals("de.test.basic", artifact.getQualifiedName());
  }

  protected IBundleMakerArtifact getRootArtifact(boolean hierarchical) {
    
    ArtifactModelConfiguration artifactModelConfiguration = new ArtifactModelConfiguration();
    artifactModelConfiguration.setHierarchicalPackages(hierarchical);
    artifactModelConfiguration.setResourcePresentation(
        ResourcePresentation.ALL_RESOURCES);
    artifactModelConfiguration.setContentType(ContentType.BINARY);
    artifactModelConfiguration.setAggregateInnerTypes(false);
    artifactModelConfiguration.setIncludeVirtualModuleForMissingTypes(true);
    
    IBundleMakerArtifact rootArtifact = (IBundleMakerArtifact) ModelTransformer.getDependencyModel(getModularizedSystem(),
        artifactModelConfiguration).getRoot();
    
    Assert.assertNotNull(rootArtifact);
    
    return rootArtifact;
    
  }

}
