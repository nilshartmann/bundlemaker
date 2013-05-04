package org.bundlemaker.core.itestframework.simple_artifact_model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.algorithms.AdjacencyMatrix;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;

public class SimpleArtifactModelAssert {

  /**
   * <p>
   * </p>
   * 
   * @param model
   * @return
   */
  // public static void assertGroupCount(SimpleArtifactModel model, int expectedCount) {
  //
  // // create the result list
  // final List<IGroupArtifact> result = new LinkedList<IGroupArtifact>();
  //
  // // accept the visitor
  // model.getRootArtifact().accept(new IAnalysisModelVisitor.Adapter() {
  // @Override
  // public boolean visit(IGroupArtifact groupArtifact) {
  // result.add(groupArtifact);
  // return super.visit(groupArtifact);
  // }
  // });
  //
  // //
  // if (result.size() != expectedCount) {
  //
  // StringBuilder stringBuilder = new StringBuilder();
  // stringBuilder
  // .append(String.format("Expected %s groups, but found %s: %s\n", expectedCount, result.size(), result));
  //
  // for (Iterator iterator = result.iterator(); iterator.hasNext();) {
  // IGroupArtifact iGroupArtifact = (IGroupArtifact) iterator.next();
  // stringBuilder.append(String.format("%s", iGroupArtifact.getQualifiedName()));
  // if (iterator.hasNext()) {
  // stringBuilder.append("\n");
  // }
  // }
  //
  // Assert.fail(stringBuilder.toString());
  // }
  // }

  /**
   * <p>
   * </p>
   * 
   * @param model
   * @param expectedCount
   */
  public static void assertResourceModuleCount(SimpleArtifactModel model, int expectedCount) {

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
      stringBuilder.append(String.format("Expected %s modules, but found %s: %s\n", expectedCount, result.size(),
          result));

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
   * @param binModel
   * @param srcModel
   */
  public static void initialAsserts(SimpleArtifactModel binModel, SimpleArtifactModel srcModel) {

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
  public static void assert_Main_Jre_G1_G2_Dependencies(SimpleArtifactModel model, int[][] matrix) {

    //
    Assert.assertArrayEquals(
        matrix,
        AdjacencyMatrix.computeAdjacencyMatrix(null, model.getMainModuleArtifact(), model.getJreArtifact(),
            model.getGroup1Artifact(), model.getGroup2Artifact()));
  }

  /**
   * <p>
   * </p>
   * 
   * @param name
   * @param version
   */
  public static void assertMainModuleNameAndVersion(SimpleArtifactModel model, String name, String version) {

    // assert resource module count == 1
    assertResourceModuleCount(model, 2);

    //
    Assert.assertEquals(name + "_" + version, model.getMainModuleArtifact().getName());
    Assert.assertTrue(model.getMainModuleArtifact().getQualifiedName().endsWith(name + "_" + version));
    Assert.assertEquals(name, model.getMainModuleArtifact().getModuleName());
    Assert.assertEquals(version, model.getMainModuleArtifact().getModuleVersion());
  }
}
