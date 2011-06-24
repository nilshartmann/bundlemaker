package org.bundlemaker.core.itest.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.itest.framework.AbstractModuleConverterTest;
import org.bundlemaker.core.itest.framework.ArtifactTransformationProcessor;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IReferencedModulesQueryResult;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.query.ReferenceQueryFilters;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependency;
import org.bundlemaker.dependencyanalysis.base.model.IDependencyModel;
import org.bundlemaker.dependencyanalysis.base.transformations.BundlePathName;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class InnerClassTest extends AbstractModuleConverterTest {

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * @throws AmbiguousElementException
   */
  @Test
  public void testInnerClass() throws CoreException, AmbiguousElementException {

    //
    IResourceModule resourceModule = getModularizedSystem().getResourceModule("InnerClassTest", "1.0.0");
    IType aType = resourceModule.getType("de.test.innertypes.A");
    IType bType = resourceModule.getType("de.test.innertypes.B");

    // test resources
    Assert.assertNotNull(aType);
    Assert.assertTrue(aType.hasBinaryResource());
    Assert.assertTrue(aType.hasSourceResource());
    IResource aSourceResource = aType.getSourceResource();
    Assert.assertTrue(aSourceResource.isPrimaryType(aType));
    Assert.assertFalse(aSourceResource.isPrimaryType(bType));
    Assert.assertNotNull(aSourceResource.getContainedTypes());
    Assert.assertEquals(aSourceResource.getContainedTypes().size(), 4);

    Assert.assertNotNull(bType);
    Assert.assertTrue(bType.hasBinaryResource());
    Assert.assertTrue(bType.hasSourceResource());

    IResource binaryResource = bType.getBinaryResource();
    Assert.assertNotNull(binaryResource.getStickyResources());
    Assert.assertEquals(1, binaryResource.getStickyResources().size());

    IResource bSourceResource = bType.getSourceResource();
    Assert.assertSame(aSourceResource, bSourceResource);

    // transform the model
    IDependencyModel dependencyModel = ModelTransformer.getDependencyModel(getBundleMakerProject(),
        getModularizedSystem());

    IArtifact aArtifact = ((IAdvancedArtifact) dependencyModel.getRoot())
        .getChild("bla/blub/InnerClassTest_1.0.0/de.test.innertypes/A");
    Assert.assertNotNull(aArtifact);
    IArtifact bArtifact = ((IAdvancedArtifact) dependencyModel.getRoot())
        .getChild("bla/blub/InnerClassTest_1.0.0/de.test.innertypes/B");
    Assert.assertNull(bArtifact);

    Collection<IDependency> dependencies = aArtifact.getDependencies();
    Assert.assertEquals(8, dependencies.size());
    for (IDependency iDependency : dependencies) {
      System.out.println(iDependency);
    }

    // //
    //
    //
    // //
    // ArtifactTransformationProcessor.moveArtifact(artifact, new BundlePathName("hunz"), dependencyModel);

    // // Test 1: assert module artifact != null
    // IArtifact anonymousInnerClassTestModuleArtifact = ((IAdvancedArtifact) dependencyModel.getRoot())
    // .getChild("bla/blub/AnonymousInnerClassTest_1.0.0");
    // Assert.assertNotNull(anonymousInnerClassTestModuleArtifact);
    // ArtifactUtils.dumpArtifact(anonymousInnerClassTestModuleArtifact);
    //
    // IArtifact hunzModuleArtifact = ((IAdvancedArtifact) dependencyModel.getRoot()).getChild("hunz_0.0.0");
    // Assert.assertNotNull(hunzModuleArtifact);
    // ArtifactUtils.dumpArtifact(hunzModuleArtifact);
    //
    // // Test 2: assert resources
    // IResourceModule anonymousResourceModule = getModularizedSystem().getResourceModule("AnonymousInnerClassTest",
    // "1.0.0");
    // dump(anonymousResourceModule, getModularizedSystem());
    // Assert.assertNull(anonymousResourceModule.getResource("de/test/anonymous/Test.class", ContentType.BINARY));
    // Assert.assertNull(anonymousResourceModule.getResource("de/test/anonymous/Test$1.class", ContentType.BINARY));
    // Assert.assertNull(anonymousResourceModule.getResource("de/test/anonymous/Test.java", ContentType.SOURCE));
    //
    // IResourceModule hunzResourceModule = getModularizedSystem().getResourceModule("hunz", "0.0.0");
    // dump(hunzResourceModule, getModularizedSystem());
    // Assert.assertNotNull(hunzResourceModule.getResource("de/test/anonymous/Test.class", ContentType.BINARY));
    // Assert.assertNotNull(hunzResourceModule.getResource("de/test/anonymous/Test$1.class", ContentType.BINARY));
    // Assert.assertNotNull(hunzResourceModule.getResource("de/test/anonymous/Test.java", ContentType.SOURCE));
    //
    // // Test 3:
    // IType type = getModularizedSystem().getType("de.test.anonymous.Test");
    // Assert.assertNotNull(type);
    // System.out.println(type.getModule(getModularizedSystem()));
  }

  /**
   * <p>
   * </p>
   * 
   * @param module
   * @return
   */
  public static void dump(IResourceModule module, IModularizedSystem modularizedSystem) {

    StringBuilder builder = new StringBuilder();
    builder.append(module.getModuleIdentifier().toString() + "\n");

    builder.append("\n");
    builder.append("Source-Content: \n");

    for (IResource resource : asSortedList(module.getResources(ContentType.SOURCE))) {
      builder.append(resource.getPath() + "\n");

      for (IReference reference : resource.getReferences()) {
        builder.append(" * " + reference.toString() + "\n");
      }

      for (IType type : resource.getContainedTypes()) {
        builder.append(" - " + type.getFullyQualifiedName() + "\n");

        for (IReference reference : type.getReferences()) {
          builder.append("   * " + reference.toString() + "\n");
        }
      }
    }

    builder.append("\n");
    builder.append("Binary-Content: \n");
    for (IResource resource : asSortedList(module.getResources(ContentType.BINARY))) {
      builder.append(resource.getPath() + "\n");

      for (IReference reference : resource.getReferences()) {
        builder.append(" * " + reference.toString() + "\n");
      }

      for (IResource stickyResources : resource.getStickyResources()) {
        builder.append(" ~sticky~ " + stickyResources.getPath() + "\n");
      }

      for (IType type : resource.getContainedTypes()) {
        builder.append(" - " + type.getFullyQualifiedName() + "\n");

        for (IReference reference : type.getReferences()) {
          builder.append("   * " + reference.toString() + "\n");
        }
      }
    }

    builder.append("\n");
    builder.append("Referenced Types: \n");
    Set<String> referencedTypes = module
        .getReferencedTypeNames(ReferenceQueryFilters.ALL_DIRECT_EXTERNAL_REFERENCES_QUERY_FILTER);
    for (String referencedType : asSortedList(referencedTypes)) {
      builder.append(referencedType + "\n");
    }

    builder.append("\n");
    builder.append("Indirectly referenced Types: \n");
    Set<String> indirectlyReferencedTypes = module
        .getReferencedTypeNames(ReferenceQueryFilters.ALL_DIRECT_EXTERNAL_REFERENCES_QUERY_FILTER);
    for (String referencedType : asSortedList(indirectlyReferencedTypes)) {
      if (!referencedTypes.contains(referencedType)) {
        builder.append(referencedType + "\n");
      }
    }

    builder.append("\n");
    builder.append("Referenced Modules: \n");
    IReferencedModulesQueryResult queryResult = modularizedSystem.getReferencedModules(module, null);

    for (IModule referencedModule : queryResult.getReferencedModules()) {
      builder.append(referencedModule.getModuleIdentifier().toString() + "\n");
    }

    // TODO
    builder.append("\n");
    builder.append("Missing Types: \n");
    for (IReference missingType : queryResult.getUnsatisfiedReferences()) {
      builder.append(missingType + "\n");
    }

    //
    System.out.println(builder.toString());
  }

  /**
   * <p>
   * </p>
   * 
   * @param <T>
   * @param set
   * @return
   */
  private static <T extends Comparable<T>> List<T> asSortedList(Set<T> set) {

    //
    List<T> arrayList = new ArrayList<T>(set);

    //
    Collections.sort(arrayList);

    //
    return arrayList;
  }
}
