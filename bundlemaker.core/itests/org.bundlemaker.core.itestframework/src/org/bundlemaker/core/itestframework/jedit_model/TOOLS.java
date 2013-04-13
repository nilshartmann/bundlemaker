package org.bundlemaker.core.itestframework.jedit_model;

import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.ITypeModule;
import org.bundlemaker.core.resource.IType;
import org.junit.Assert;

public class TOOLS {

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param count
   */
  public void assertArtifactChildrenCount(IBundleMakerArtifact artifact, int count) {
    Assert.assertEquals(artifact.getChildren().toString(), count, artifact.getChildren().size());
  }

  /**
   * <p>
   * </p>
   * 
   * @param from
   * @param to
   * @param weight
   */
  public void assertDependencyWeight(IBundleMakerArtifact from, IBundleMakerArtifact to, int weight) {
    IDependency dependency = from.getDependencyTo(to);

    if (weight == 0) {
      Assert.assertNull(dependency);
    } else {
      Assert.assertEquals(weight, dependency.getWeight());
    }
  }

  public void assertArtifactHasParent(IBundleMakerArtifact child, IBundleMakerArtifact parent) {
    Assert.assertEquals(parent, child.getParent());
  }

  /**
   * <p>
   * </p>
   * 
   * @param typeCountWithoutJdkTypes
   */
  public static void assertTypeCount(IModularizedSystem modularizedSystem, int typeCountWithoutJdkTypes) {

    Set<IType> s1 = new HashSet<IType>();

    for (IModule module : modularizedSystem.getAllModules()) {
      System.out.println(module);
      System.out.println(module.adaptAs(ITypeModule.class).getContainedTypes().size());
      s1.addAll(module.adaptAs(ITypeModule.class).getContainedTypes());
    }

    System.out.println(modularizedSystem.getTypes().size());
    System.out.println(s1.size());

    Set<IType> intersection = new HashSet<IType>(s1);
    intersection.retainAll(modularizedSystem.getTypes());

    System.out.println();
    
    Set<IType> types = new HashSet<IType>(modularizedSystem.getTypes());
    types.removeAll(intersection);

    for (IType iType : types) {
      System.out.println(" - " + iType);
    }

    // assert the specified number of types
    Assert.assertEquals("Expected: "
        + typeCountWithoutJdkTypes
        + ", actual: "
        + (modularizedSystem.getTypes().size() - modularizedSystem.getExecutionEnvironment().adaptAs(ITypeModule.class)
            .getContainedTypes().size()), modularizedSystem.getExecutionEnvironment().adaptAs(ITypeModule.class)
        .getContainedTypes().size()
        + typeCountWithoutJdkTypes, modularizedSystem.getTypes().size());
  }

  /**
   * <p>
   * </p>
   * 
   * @param count
   */
  public static void assertModificationCount(AbstractJeditAnalysisModelTest test, int count) {
    Assert.assertEquals(count, test.getSrcHierarchicalModel().getModifiedNotificationCount());
    Assert.assertEquals(count, test.getSrcFlatModel().getModifiedNotificationCount());
    Assert.assertEquals(count, test.getBinHierarchicalModel().getModifiedNotificationCount());
    Assert.assertEquals(count, test.getBinFlatModel().getModifiedNotificationCount());
  }
}
