/*******************************************************************************
 * Copyright (c) 2013 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/

package org.bundlemaker.core.itest.jedit_artifact_model;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itestframework.AbstractBundleMakerModelTest;
import org.bundlemaker.core.itestframework.utils.ArtifactTestUtil;
import org.bundlemaker.core.osgi.utils.ArtifactUtils;
import org.junit.Test;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public class CreateModuleFromFlatPackageTest extends AbstractBundleMakerModelTest {
  
  @Test
  public void createNewModuleFromFlatPackage() throws Exception {
    
    IRootArtifact flatModel = getModularizedSystem().getAnalysisModel(AnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION);
    IRootArtifact hierModel = getModularizedSystem().getAnalysisModel(AnalysisModelConfiguration.HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION);
    
    Collection<IBundleMakerArtifact> children = flatModel.getChildren();
    IPackageArtifact originalFlatGuiPackage = null;
    IModuleArtifact jeditModule = null;
    
    Iterator<IBundleMakerArtifact> iterator = children.iterator();
    while (iterator.hasNext()) {
      IBundleMakerArtifact next = iterator.next();
      System.out.println("next: " + next.getName());
      if (next.isInstanceOf(IModuleArtifact.class) && "jedit_1.0.0".equals(next.getName())) {
        jeditModule = (IModuleArtifact) next;
        break;
      }
    }
    assertNotNull(jeditModule);
    
   iterator = jeditModule.getChildren().iterator();
    while (iterator.hasNext()) {
      IBundleMakerArtifact next = iterator.next();
      if (next.isInstanceOf(IPackageArtifact.class) && "org.gjt.sp.jedit.gui".equals(next.getQualifiedName())) {
        originalFlatGuiPackage = (IPackageArtifact) next;
        break;
      }
    }

    //
    assertNotNull(originalFlatGuiPackage);
    
    assertFalse(originalFlatGuiPackage.getConfiguration().isHierarchicalPackages());
    List<IBundleMakerArtifact> artifacts = new LinkedList<IBundleMakerArtifact>();
    artifacts.add(originalFlatGuiPackage);
    
    // Create new module
    IModuleArtifact utilModule = flatModel.getOrCreateModule("org.jedit.util", "0.0.0");
    
    utilModule.addArtifacts(artifacts);
    Collection<IBundleMakerArtifact> utilChildren = utilModule.getChildren();
    assertEquals(1, utilChildren.size());
    assertEquals("org.gjt.sp.jedit.gui", utilChildren.iterator().next().getQualifiedName());
    assertEquals("gui", utilChildren.iterator().next().getName());
    
    
    IModuleArtifact utilHierModule = AnalysisModelQueries.findChild(hierModel, "org.jedit.util", IModuleArtifact.class);
    
    AnalysisModelQueries.dumpArtifact(utilHierModule);
    
    assertNotSame(utilHierModule, utilModule);
    assertNotSame(flatModel, hierModel);
    assertEquals(utilHierModule.getRoot(), hierModel);
    
   children = utilHierModule.getChildren();
    assertNotNull(children);
    assertEquals(1, children.size());
    IBundleMakerArtifact a = children.iterator().next();
    assertEquals("org", a.getName());
    assertEquals("org", a.getQualifiedName());
    
    AnalysisModelQueries.dumpArtifact(utilHierModule);
  }
  
  @Override
  protected String getTestProjectName() {
    return "jedit";
  }

  
  

}
