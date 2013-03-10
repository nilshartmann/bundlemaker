/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.itest.simple_artifact_model.schrott_analysis_tmp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.itest._framework.AbstractModularizedSystemTest;
import org.bundlemaker.core.itest._framework.analysis.ArtifactVisitorUtils;
import org.bundlemaker.core.projectdescription.ProjectContentType;
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
public class ArtifactNamingTest extends AbstractModularizedSystemTest {

  private IModuleArtifact             _moduleArtifact;

  private IAnalysisModelConfiguration _configuration;

  @Parameters
  public static List<Object[]> getConfigurations() {
    Object[][] arr = {
        {
            //
            new AnalysisModelConfiguration(true, ProjectContentType.BINARY, false)
    },
        {
            //
            new AnalysisModelConfiguration(false, ProjectContentType.BINARY, false)
    },
        {
            //
            new AnalysisModelConfiguration(true, ProjectContentType.SOURCE, false)
    },
        {
            //
            new AnalysisModelConfiguration(false, ProjectContentType.BINARY, false)
    },
    };
    return Arrays.asList(arr);

  }

  public ArtifactNamingTest(IAnalysisModelConfiguration configuration) {
    this._configuration = configuration;
  }

  @Before
  public void setupArtifactModel() {
    IRootArtifact rootArtifact = getModularizedSystem().getAnalysisModel(_configuration);

    _moduleArtifact = AnalysisModelQueries.getModuleArtifact(rootArtifact, "com.example");
  }

  @Test
  public void test_getQualifiedTypeName() {
    List<String> allTypeNames = getAllTypeNames();

    List<String> expectedTypeNames = Arrays.asList("ClassInDefaultPackage", //
        "com.ClassInCom", //
        "com.example.Class1", //
        "com.example.Class2", "com.example.Class2$InnerClass2", "com.example.Class2$StaticInnerClass2", //

        "com.example.Interface1");

    assertEquals(expectedTypeNames, allTypeNames);
  }

  @Test
  public void test_getPackageNames() {
    List<String> expectedPackageNames = Arrays.asList("", "com", "com.example");
    List<String> allPackageNames = getAllPackageNames();

    assertEquals(expectedPackageNames, allPackageNames);
  }

  @Override
  protected String computeTestProjectName() {
    return "com.example";
  }

  protected List<String> getAllTypeNames() {
    final List<String> result = new LinkedList<String>();
    _moduleArtifact.accept(new IAnalysisModelVisitor.Adapter() {

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.bundlemaker.core.analysis.IAnalysisModelVisitor.Adapter#visit(org.bundlemaker.core.analysis.ITypeArtifact)
       */
      @Override
      public boolean visit(ITypeArtifact typeArtifact) {
        String qualifiedTypeName = typeArtifact.getQualifiedName();
        assertNotNull(qualifiedTypeName);
        result.add(qualifiedTypeName);
        return true;
      }

    });

    Collections.sort(result);

    return result;

  }

  protected List<String> getAllPackageNames() {
    final List<String> result = new LinkedList<String>();
    _moduleArtifact.accept(new IAnalysisModelVisitor.Adapter() {

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.bundlemaker.core.analysis.IAnalysisModelVisitor.Adapter#visit(org.bundlemaker.core.analysis.IPackageArtifact
       * )
       */
      @Override
      public boolean visit(IPackageArtifact packageArtifact) {
        String packageName = packageArtifact.getPackageName();
        assertNotNull(packageName);
        result.add(packageName);
        return true;
      }

    });

    Collections.sort(result);

    return result;
  }

}
