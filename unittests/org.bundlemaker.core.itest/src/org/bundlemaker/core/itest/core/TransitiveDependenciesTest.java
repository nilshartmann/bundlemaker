package org.bundlemaker.core.itest.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Set;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.transformations.resourceset.ResourceSetBasedModuleDefinition;
import org.bundlemaker.core.transformations.resourceset.ResourceSetBasedTransformation;
import org.junit.Before;
import org.junit.Test;

public class TransitiveDependenciesTest extends AbstractModularizedSystemTest {

  private ResourceSetBasedTransformation _transformation;

  @Before
  public void createTransformation() {
    _transformation = new ResourceSetBasedTransformation();
  }

  @Test
  public void test_eins() {

    addModule("controller", "org/bm/test/control/**"); // A.execute()
    addModule("domain", "org/bm/test/domain/**"); // B
    addModule("app", "org/bm/test/app/**"); // C -> b.execute()

    getModularizedSystem().applyTransformations(null, _transformation);

    Set<IModule> referencedModules = getTransitiveReferencesModules("app");

    assertEquals(2, referencedModules.size());
  }

  @Test
  public void test_withArtifactTransformation() {

    addModule("controlAndDomain", "org/bm/test/control/**", "org/bm/test/domain/**");
    addModule("app", "org/bm/test/app/**");
    getModularizedSystem().applyTransformations(null, _transformation);

    moveType("org.bm.test.control.Controller", "controlType");

    Set<IModule> referencedModules = getTransitiveReferencesModules("app");

    assertEquals(2, referencedModules.size());

  }

  protected void moveType(String typeName, String targetModuleName) {

    // run artifact-based transformation
    IArtifact root = getModularizedSystem().getArtifactModel(
        ArtifactModelConfiguration.AGGREGATE_INNER_TYPES_NO_RESOURCES_CONFIGURATION);
    Collection<IArtifact> leafs = root.getLeafs();

    for (IArtifact artifact : leafs) {
      if (typeName.equals(artifact.getQualifiedName())) {
        moveArtifact(artifact, targetModuleName, ((IBundleMakerArtifact)root).getRoot());
      }
    }
  }

  protected void addModule(String name, String... content) {
    ResourceSetBasedModuleDefinition moduleDefinition = _transformation.addModuleDefinition(name, "1.0.0");
    moduleDefinition.addResourceSet(new ModuleIdentifier(getTestProjectName(), "1.0.0"), content, new String[] {});
  }

  protected IResourceModule getResourceModule(String name) {
    IResourceModule resourceModule = getModularizedSystem().getResourceModule(name, "1.0.0");
    assertNotNull(resourceModule);
    return resourceModule;
  }

  protected Set<IModule> getTransitiveReferencesModules(String moduleName) {
    Set<IModule> referencedModules = getModularizedSystem().getTransitiveReferencedModules(
        getResourceModule(moduleName)).getReferencedModules();
    assertNotNull(referencedModules);
    return referencedModules;
  }

  private void moveArtifact(final IArtifact typeArtifact, String moduleName, IRootArtifact rootArtifact) {

    // das root object
    IModuleArtifact moduleArtifact = rootArtifact.getOrCreateModule(moduleName, "1.0.0");
    moduleArtifact.addArtifact(typeArtifact);
  }

}
