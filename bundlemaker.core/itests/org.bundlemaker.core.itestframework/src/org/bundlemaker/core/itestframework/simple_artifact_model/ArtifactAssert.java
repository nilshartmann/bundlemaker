package org.bundlemaker.core.itestframework.simple_artifact_model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.resource.IModularizedSystem;
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
public class ArtifactAssert {

  /**
   * <p>
   * </p>
   * 
   * @param model
   * @return
   */
  public static void assertGroupCount(SimpleArtifactModel model, int expectedCount) {

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
   * @param module
   * @param expectedCount
   */
  public static void assertResourcesCount(IModuleArtifact module, int expectedCount) {

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
      stringBuilder.append(String.format("Expected %s resources, but found %s: %s\n", expectedCount, result.size(),
          result));

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
  public static void assertGroupCountInModularizedSystem(IModularizedSystem modularizedSystem, int count) {
    Assert.assertEquals(String.format("%s.", modularizedSystem.getGroups()), count, modularizedSystem.getGroups()
        .size());
  }

  /**
   * <p>
   * </p>
   * 
   * @param count
   */
  public static void assertResourceModuleCountInModularizedSystem(IModularizedSystem modularizedSystem, int count) {
    Assert.assertEquals(String.format("%s.", modularizedSystem.getModules()), count, modularizedSystem
        .getModules().size());
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

}
