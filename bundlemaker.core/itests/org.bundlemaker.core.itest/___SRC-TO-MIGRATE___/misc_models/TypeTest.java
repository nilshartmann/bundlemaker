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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.itest._framework.AbstractModularizedSystemTest;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.TypeEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
@RunWith(Parameterized.class)
public class TypeTest extends AbstractModularizedSystemTest {
  
  private final static String IGNORE_MISSING_BINARIES_PROPERTY = "org.bundlemaker.ignoreMissingBinaries";

  @Parameters
  public static List<Object[]> getConfigurations() {
    Object[][] arr = { //
        // {parseBinary, parseSource} //
        { true, true }, //
        { true, false }, //
    };
    return Arrays.asList(arr);

  }

  private IRootArtifact _rootArtifact;

  private boolean       _parseSource;

  private boolean       _parseBinary;

  public TypeTest(boolean parseBinary, boolean parseSource) {
    this._parseBinary = parseBinary;
    this._parseSource = parseSource;
    
    if (!_parseBinary) {
      System.setProperty(IGNORE_MISSING_BINARIES_PROPERTY, "true");
    }
    
  }

  @Before
  public void setupRootArtifact() {
    _rootArtifact = getModularizedSystem().getAnalysisModel(AnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION);

  }
  
  @After
  public void setFailOnMissingBinaries() {
    System.getProperties().remove(IGNORE_MISSING_BINARIES_PROPERTY);
  }

  @Test
  public void test_typeClass() {
    ITypeArtifact typeArtifact = getTypeArtifact("TypeClass");
    assertEquals(TypeEnum.CLASS, typeArtifact.getAssociatedType().getType());
  }

  @Test
  public void test_typeInterface() {
    ITypeArtifact typeArtifact = getTypeArtifact("TypeInterface");
    assertEquals(TypeEnum.INTERFACE, typeArtifact.getAssociatedType().getType());
  }

  @Test
  public void test_typeAnnotation() {
    ITypeArtifact typeArtifact = getTypeArtifact("TypeAnnotation");
    assertEquals(TypeEnum.ANNOTATION, typeArtifact.getAssociatedType().getType());
  }

  @Test
  public void test_typeEnum() {
    ITypeArtifact typeArtifact = getTypeArtifact("TypeEnum");
    assertEquals(TypeEnum.ENUM, typeArtifact.getAssociatedType().getType());
  }
  
  @Test
  public void test_AbstractType() {
    assertAbstractType("AbstractClass");
    assertAbstractType("TypeInterface");
    // Annotations are considered abstract
    assertAbstractType("TypeAnnotation");
    
    assertConcreteType("TypeClass");
    assertConcreteType("TypeEnum");
       
  }

  protected void assertAbstractType(String name) {
    ITypeArtifact typeArtifact = getTypeArtifact(name);
    assertTrue("Type " + typeArtifact.getQualifiedTypeName() + " should be abstract but is not.", typeArtifact.getAssociatedType().isAbstractType());
  }
  
  protected void assertConcreteType(String name) {
    ITypeArtifact typeArtifact = getTypeArtifact(name);
    assertFalse("Type " + typeArtifact.getQualifiedTypeName() + " should not be abstract but it is.", typeArtifact.getAssociatedType().isAbstractType());
  }
  
  protected ITypeArtifact getTypeArtifact(String name) {
    String expectedName = "org.typetest." + name;
    ITypeArtifact typeArtifact = AnalysisModelQueries.findTypeArtifactByQualifiedName(_rootArtifact, expectedName);
    assertNotNull("Type '" + expectedName + "' not found", typeArtifact);
    return typeArtifact;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.itestframework.AbstractBundleMakerProjectTest#getClassesPath(java.io.File)
   */
  @Override
  protected String getClassesPath(File directory) {
    if (_parseBinary) {
      return super.getClassesPath(directory);
    }
    return new File(directory, "empty-classes").getAbsolutePath();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.itestframework.AbstractBundleMakerProjectTest#getSourcesPath(java.io.File)
   */
  @Override
  protected String getSourcesPath(File directory) {
    if (_parseSource) {
      String sourcesPath = super.getSourcesPath(directory);
      assertNotNull(sourcesPath);
      return sourcesPath;
    }
    return null;
  }

}
