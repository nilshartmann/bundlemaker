package org.bundlemaker.core.itestframework.jedit_model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.jtype.IType;
import org.bundlemaker.core.jtype.ITypeModularizedSystem;
import org.bundlemaker.core.jtype.ITypeModule;
import org.bundlemaker.core.resource.IModularizedSystem;
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

    // //
    // ITypeModularizedSystem tms = modularizedSystem.adaptAs(ITypeModularizedSystem.class);
    // ITypeModule exeTyMo = modularizedSystem.getExecutionEnvironment().adaptAs(ITypeModule.class);

    // //
    // List<IType> types = new LinkedList<IType>(tms.getTypes());
    // Collections.sort(types, new Comparator<IType>() {
    // @Override
    // public int compare(IType o1, IType o2) {
    // return o1.getFullyQualifiedName().compareTo(o2.getFullyQualifiedName());
    // }
    // });
    // System.out.println("*************************************************");
    // for (IType iType : types) {
    // System.out.println(" - " + iType.getFullyQualifiedName());
    // }
    // System.out.println("*************************************************");
    
    
//    System.out.println("All types: " + tms.getTypes().size());
//    System.out.println("All execution types: " + exeTyMo.getContainedTypes().size());
//
//    // assert the specified number of types
//    Assert.assertEquals("Expected: " + typeCountWithoutJdkTypes + ", actual: "
//        + (tms.getTypes().size() - exeTyMo.getContainedTypes().size()), exeTyMo.getContainedTypes().size()
//        + typeCountWithoutJdkTypes, tms.getTypes().size());
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
