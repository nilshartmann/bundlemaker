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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import nz.ac.massey.cs.guery.adapters.blueprints.BlueprintsAdapter;
import nz.ac.massey.cs.guery.adapters.blueprints.ElementCache;

import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.DependencyKind;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.itestframework.AbstractBundleMakerProjectTest;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.util.ProgressMonitor;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Before;

import com.tinkerpop.blueprints.Graph;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractBundleMakerBlueprintsTest extends AbstractBundleMakerProjectTest {
  /** - */
  private IModifiableModularizedSystem _modularizedSystem;

  private BlueprintsAdapter            _blueprintsAdapter = null;

  private Graph                        _graph             = null;

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

    try {
      assertCorrectTestModel();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }

    //
    _graph = getRootArtifact();

    _blueprintsAdapter = new BlueprintsAdapter(_graph, getCache());

  }

  abstract protected void assertCorrectTestModel() throws Exception;

  abstract protected ElementCache getCache();

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

    Collection<IModuleArtifact> modules = rootArtifact.getChildren(IModuleArtifact.class);

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

  /**
   * @return the graph
   */
  public Graph getGraph() {
    return _graph;
  }

  /**
   * @return the blueprintsAdapter
   */
  public BlueprintsAdapter getBlueprintsAdapter() {
    return _blueprintsAdapter;
  }

  /**
   * @param string
   */
  protected void assertPackageExists(final String packageName) {

    final List<IPackageArtifact> packageArtifactsFound = new LinkedList<IPackageArtifact>();

    getRootArtifact().accept(new IAnalysisModelVisitor.Adapter() {

      @Override
      public boolean visit(IPackageArtifact packageArtifact) {
        if (packageArtifact.getQualifiedName().equals(packageName)) {
          packageArtifactsFound.add(packageArtifact);
        }
        return false;
      }

    });

    assertEquals(1, packageArtifactsFound.size());

  }

  /**
   * @param string
   * @param string2
   * @param string3
   * @return
   */
  protected ITypeArtifact getType(final String expectedTypeName) {
    final List<ITypeArtifact> typeArtifactsFound = new LinkedList<ITypeArtifact>();

    getRootArtifact().accept(new IAnalysisModelVisitor.Adapter() {

      @Override
      public boolean visit(ITypeArtifact typeArtifact) {

        String qualifiedTypeName = typeArtifact.getParent(IPackageArtifact.class).getQualifiedName() + "."
            + typeArtifact.getName();

        if (qualifiedTypeName.equals(expectedTypeName)) {
          typeArtifactsFound.add(typeArtifact);
        }
        return false;
      }

    });

    assertEquals(1, typeArtifactsFound.size());

    return typeArtifactsFound.get(0);
  }

  /**
   * @param if1
   * @param cl1
   * @param string
   */
  protected IDependency getDependency(ITypeArtifact from, ITypeArtifact to, DependencyKind expectedKind) {

    IDependency dependency = from.getDependencyTo(to);
    assertNotNull(dependency);
    assertEquals(expectedKind, dependency.getDependencyKind());

    return dependency;

  }

}
