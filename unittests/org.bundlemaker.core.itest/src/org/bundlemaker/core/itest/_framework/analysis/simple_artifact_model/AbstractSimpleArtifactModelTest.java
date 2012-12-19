package org.bundlemaker.core.itest._framework.analysis.simple_artifact_model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.algorithms.AdjacencyMatrix;
import org.bundlemaker.core.itest._framework.AbstractModularizedSystemTest;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;

/**
 * <pre>
 * Group : group1
 *   Group : group2
 *     Module : group1/group2/SimpleArtifactModelTest_1.0.0
 *       Package : de.test
 *         Resource : Test.class
 *           Type : de.test.Test
 *         Resource : Klasse.class
 *           Type : de.test.Klasse
 * Module : jdk16_jdk16
 * </pre>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractSimpleArtifactModelTest extends AbstractModularizedSystemTest {

  /** - */
  protected Model _binModel;

  /** - */
  protected Model _srcModel;

  /**
   * {@inheritDoc}
   */
  @Override
  public void before() throws CoreException {
    super.before();

    _binModel = new Model(getModularizedSystem(),
        IAnalysisModelConfiguration.HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION);
    _srcModel = new Model(getModularizedSystem(),
        IAnalysisModelConfiguration.HIERARCHICAL_SOURCE_RESOURCES_CONFIGURATION);

    initialAsserts(_binModel, _srcModel);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void after() throws CoreException {
    super.after();

    _binModel = null;
    _srcModel = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return "SimpleArtifactModelTest";
  }

  /**
   * <p>
   * </p>
   * 
   * @param model
   * @return
   */
  protected void assertGroupCount(Model model, int expectedCount) {

    // create the result list
    final List<IGroupArtifact> result = new LinkedList<IGroupArtifact>();

    // accept the visitor
    model.getRootArtifact().accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean visit(IGroupArtifact groupArtifact) {
        result.add(groupArtifact);
        return super.visit(groupArtifact);
      }
    });

    //
    if (result.size() != expectedCount) {

      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder
          .append(String.format("Expected %s groups, but found %s: %s\n", expectedCount, result.size(), result));

      for (Iterator iterator = result.iterator(); iterator.hasNext();) {
        IGroupArtifact iGroupArtifact = (IGroupArtifact) iterator.next();
        stringBuilder.append(String.format("%s", iGroupArtifact.getQualifiedName()));
        if (iterator.hasNext()) {
          stringBuilder.append("\n");
        }
      }

      Assert.fail(stringBuilder.toString());
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param model
   * @param expectedCount
   */
  protected void assertResourceModuleCount(Model model, int expectedCount) {

    // create the result list
    final List<IModuleArtifact> result = new LinkedList<IModuleArtifact>();

    // accept the visitor
    model.getRootArtifact().accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {
        if (moduleArtifact.isResourceModule()) {
          result.add(moduleArtifact);
        }
        return super.visit(moduleArtifact);
      }
    });

    //
    if (result.size() != expectedCount) {

      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder
          .append(String.format("Expected %s modules, but found %s: %s\n", expectedCount, result.size(), result));

      for (Iterator iterator = result.iterator(); iterator.hasNext();) {
        IModuleArtifact moduleArtifact = (IModuleArtifact) iterator.next();
        stringBuilder.append(String.format("%s", moduleArtifact.getQualifiedName()));
        if (iterator.hasNext()) {
          stringBuilder.append("\n");
        }
      }

      Assert.fail(stringBuilder.toString());
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param module
   * @param expectedCount
   */
  protected void assertResourcesCount(IModuleArtifact module, int expectedCount) {

    // create the result list
    final List<IResourceArtifact> result = new LinkedList<IResourceArtifact>();

    // accept the visitor
    module.accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean visit(IResourceArtifact resourceArtifact) {
        result.add(resourceArtifact);
        return super.visit(resourceArtifact);
      }
    });

    //
    if (result.size() != expectedCount) {

      //
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder
          .append(String.format("Expected %s resources, but found %s: %s\n", expectedCount, result.size(), result));

      //
      for (Iterator<IResourceArtifact> iterator = result.iterator(); iterator.hasNext();) {
        stringBuilder.append(String.format("%s", iterator.next().getQualifiedName()));
        if (iterator.hasNext()) {
          stringBuilder.append("\n");
        }
      }

      //
      Assert.fail(stringBuilder.toString());
    }
  }

  /**
   * <p>
   * </p>
   * 
   */
  protected void assertGroupCountInModularizedSystem(int count) {
    Assert.assertEquals(String.format("%s.", getModularizedSystem().getGroups()), count, getModularizedSystem()
        .getGroups()
        .size());
  }

  /**
   * <p>
   * </p>
   * 
   * @param count
   */
  protected void assertResourceModuleCountInModularizedSystem(int count) {
    Assert.assertEquals(String.format("%s.", getModularizedSystem().getAllModules()), count, getModularizedSystem()
        .getResourceModules()
        .size());
  }

  /**
   * <p>
   * </p>
   * 
   * @param binModel
   * @param srcModel
   */
  private void initialAsserts(Model binModel, Model srcModel) {

    // assert that the underlying resource model is the same
    Assert.assertSame(binModel.getMainModuleArtifact().getAssociatedModule(), srcModel.getMainModuleArtifact()
        .getAssociatedModule());

    // initial classification has to be 'group1/group2'
    Assert.assertEquals(new Path("group1/group2"), binModel.getMainModuleArtifact().getAssociatedModule()
        .getClassification());

    // assert module parent in src model
    Assert.assertEquals(srcModel.getGroup2Artifact(), srcModel.getMainModuleArtifact().getParent());
  }

  /**
   * <p>
   * </p>
   * 
   * @param binModel
   * @param srcModel
   */
  protected void assert_Main_Jre_G1_G2_Dependencies(int[][] binModel, int[][] srcModel) {
    Assert.assertArrayEquals(binModel,
        AdjacencyMatrix.computeAdjacencyMatrix(null, _binModel.getMainModuleArtifact(),
            _binModel.getJreArtifact(), _binModel.getGroup1Artifact(), _binModel.getGroup2Artifact()));
    Assert.assertArrayEquals(srcModel,
        AdjacencyMatrix.computeAdjacencyMatrix(null, _srcModel.getMainModuleArtifact(),
            _srcModel.getJreArtifact(), _srcModel.getGroup1Artifact(), _srcModel.getGroup2Artifact()));
  }

  /**
   * <p>
   * </p>
   * 
   * @param name
   * @param version
   */
  protected void assertMainModuleNameAndVersion(String name, String version) {

    // assert resource module count == 1
    assertResourceModuleCountInModularizedSystem(1);
    assertResourceModuleCount(_binModel, 1);
    assertResourceModuleCount(_srcModel, 1);

    //
    Assert.assertEquals(name + "_" + version, _binModel.getMainModuleArtifact().getName());
    Assert.assertTrue(_binModel.getMainModuleArtifact().getQualifiedName().endsWith(name + "_" + version));
    Assert.assertEquals(name, _binModel.getMainModuleArtifact().getModuleName());
    Assert.assertEquals(version, _binModel.getMainModuleArtifact().getModuleVersion());

    //
    Assert.assertEquals(name, _srcModel.getMainModuleArtifact().getModuleName());
    Assert.assertEquals(version, _srcModel.getMainModuleArtifact().getModuleVersion());

    //
    Assert.assertEquals(name, _srcModel.getMainModuleArtifact().getAssociatedModule()
        .getModuleIdentifier().getName());
    Assert.assertEquals(version, _srcModel.getMainModuleArtifact().getAssociatedModule().getModuleIdentifier()
        .getVersion());

    //
    Assert.assertNotNull(getModularizedSystem().getResourceModule(name, version));
  }
}
