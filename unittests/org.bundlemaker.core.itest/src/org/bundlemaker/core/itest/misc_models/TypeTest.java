/*******************************************************************************
 * Copyright (c) 2013 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.itest.misc_models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.itest._framework.AbstractModularizedSystemTest;
import org.bundlemaker.core.resource.TypeEnum;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public class TypeTest extends AbstractModularizedSystemTest {
  
  private IRootArtifact _rootArtifact;
  
  @Before
  public void setupRootArtifact() {
    _rootArtifact = getModularizedSystem().getAnalysisModel(
        AnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION);

  }
  
  @Test
  public void test_typeClass() {
    ITypeArtifact typeArtifact = getTypeArtifact("TypeClass");
    assertEquals(
        TypeEnum.CLASS,
        typeArtifact.getAssociatedType().getType() );
  }
  
  @Test
  public void test_typeInterface() {
    ITypeArtifact typeArtifact = getTypeArtifact("TypeInterface");
    assertEquals(
        TypeEnum.INTERFACE,
        typeArtifact.getAssociatedType().getType() );
  }
  
  @Test
  public void test_typeAnnotation() {
    ITypeArtifact typeArtifact = getTypeArtifact("TypeAnnotation");
    assertEquals(
        TypeEnum.ANNOTATION,
        typeArtifact.getAssociatedType().getType() );
  }
  
  @Test
  public void test_typeEnum() {
    ITypeArtifact typeArtifact = getTypeArtifact("TypeEnum");
    assertEquals(
        TypeEnum.ENUM,
        typeArtifact.getAssociatedType().getType() );
  }
  
  protected ITypeArtifact getTypeArtifact(String name) {
    String expectedName = "org.typetest." + name;
    ITypeArtifact typeArtifact = AnalysisModelQueries.findTypeArtifact(_rootArtifact, expectedName);
    assertNotNull("Type '" + expectedName + "' not found", typeArtifact);
    return typeArtifact;
    
    
  }

}
