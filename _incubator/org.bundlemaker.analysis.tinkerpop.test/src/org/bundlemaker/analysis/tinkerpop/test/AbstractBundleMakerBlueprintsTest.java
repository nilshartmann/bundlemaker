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
package org.bundlemaker.analysis.tinkerpop.test;

import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itestframework.AbstractBundleMakerProjectTest;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.util.ProgressMonitor;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Before;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class AbstractBundleMakerBlueprintsTest extends AbstractBundleMakerProjectTest {
  /** - */
  private IModifiableModularizedSystem _modularizedSystem;

  @Before
  @Override
  public void before() throws CoreException {

    super.before();

    // add the project description
    addProjectDescription();

    // initialize
    getBundleMakerProject().initialize(new ProgressMonitor());

    // parse and open the project
    getBundleMakerProject().parseAndOpen(new ProgressMonitor());

    // assert no parse errors
    if (getBundleMakerProject().getProblems().size() > 0) {
      StringBuilder builder = new StringBuilder();
      for (IProblem problem : getBundleMakerProject().getProblems()) {
        builder.append(problem.getMessage());
        builder.append("\n");
      }
      Assert.fail(builder.toString());
    }

    // get the modularized system
    _modularizedSystem = (IModifiableModularizedSystem) getBundleMakerProject().getModularizedSystemWorkingCopy(
        getTestProjectName());

    // apply the basic group transformation
    // applyBasicTransformation(_modularizedSystem);

    // assert the test module
    Assert.assertNotNull(getModularizedSystem().getModule(getTestProjectName(), "1.0.0"));
  }

  // @Test
  // public void basicTest() throws Exception {
  // IRootArtifact model = createArtifactModel(_modularizedSystem, new AnalysisModelConfiguration(false,
  // ProjectContentType.BINARY, false));
  //
  // model.accept(new IAnalysisModelVisitor.Adapter() {
  //
  // /*
  // * (non-Javadoc)
  // *
  // * @see
  // * org.bundlemaker.core.analysis.IAnalysisModelVisitor.Adapter#visit(org.bundlemaker.core.analysis.ITypeArtifact)
  // */
  // @Override
  // public boolean visit(ITypeArtifact typeArtifact) {
  //
  // System.out.println("  Type: " + typeArtifact.getName());
  //
  // return super.visit(typeArtifact);
  // }
  //
  // /*
  // * (non-Javadoc)
  // *
  // * @see
  // * org.bundlemaker.core.analysis.IAnalysisModelVisitor.Adapter#visit(org.bundlemaker.core.analysis.IPackageArtifact
  // * )
  // */
  // @Override
  // public boolean visit(IPackageArtifact packageArtifact) {
  //
  // System.out.println("Package: " + packageArtifact.getName());
  //
  // return super.visit(packageArtifact);
  // }
  //
  // });
  // }

  private IRootArtifact _rootArtifact;

  protected IRootArtifact getRootArtifact() {
    if (_rootArtifact == null) {
      IRootArtifact model = createArtifactModel(_modularizedSystem, new AnalysisModelConfiguration(false,
          ProjectContentType.BINARY, false));
      _rootArtifact = model;
    }
    return _rootArtifact;
  }

  @Override
  public void after() throws CoreException {

    //
    super.after();

    //
    _modularizedSystem = null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private IRootArtifact createArtifactModel(IModularizedSystem modularizedSystem,
      IAnalysisModelConfiguration configuration) {

    IRootArtifact rootArtifact = modularizedSystem.getAnalysisModel(configuration).getRoot();

    Assert.assertNotNull(rootArtifact);
    // Assert.assertEquals(4, rootArtifact.getChildren().size());

    // assert JRE
    // Assert.assertNotNull(ArtifactVisitorUtils.findJreModuleArtifact(rootArtifact));

    // return the result
    return rootArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IModifiableModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  @Override
  protected String computeTestProjectName() {
    return "com.example";
  }

}
