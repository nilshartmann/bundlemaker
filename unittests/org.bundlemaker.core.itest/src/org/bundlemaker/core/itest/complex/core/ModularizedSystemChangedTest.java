package org.bundlemaker.core.itest.complex.core;

import junit.framework.Assert;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.MovableUnitMovedEvent;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.modules.modifiable.MovableUnit;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Tests if events are sent if a modularized system changes.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModularizedSystemChangedTest extends AbstractModularizedSystemChangedTest {

  /** - */
  private IRootArtifact             _rootArtifact;

  /** - */
  private IModifiableResourceModule _resourceModule;

  /**
   * {@inheritDoc}
   */
  @Before
  public void init() throws CoreException {

    //
    super.init();

    //
    _rootArtifact = getModularizedSystem().getArtifactModel(
        ArtifactModelConfiguration.HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION);
    Assert.assertNotNull(_rootArtifact);

    //
    _resourceModule = getModularizedSystem().getModifiableResourceModule(new ModuleIdentifier("jedit", "1.0.0"));
    Assert.assertNotNull(_resourceModule);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws AmbiguousElementException
   */
  @Test
  public void testModularizedSystemChanged() throws AmbiguousElementException {

    IBundleMakerArtifact artifact = _rootArtifact
        .getChild("group1|group2|jedit_1.0.0|org|gjt|sp|jedit|browser|VFSFileChooserDialog$WorkThreadHandler.class");
    Assert.assertNotNull(artifact);

    // TEST 1: remove a type
    IModifiableResourceModule resourceModule = getModularizedSystem().getModifiableResourceModule(
        new ModuleIdentifier("jedit", "1.0.0"));
    IType type = getModularizedSystem().getType("org.gjt.sp.jedit.browser.VFSFileChooserDialog$WorkThreadHandler");
    Assert.assertTrue(resourceModule.containsType(type.getFullyQualifiedName()));

    resourceModule.getModifiableSelfResourceContainer().removeMovableUnit(
        MovableUnit.createFromType(type, getModularizedSystem()));
    Assert.assertFalse(resourceModule.containsType(type.getFullyQualifiedName()));
    Assert.assertEquals(1, getEvents().size());

    // check the resource model
    assertModule(((MovableUnitMovedEvent) getEvents().get(0)).getMovableUnit(), null);

    // check the artifact tree model
    artifact = _rootArtifact
        .getChild("group1|group2|jedit_1.0.0|org|gjt|sp|jedit|browser|VFSFileChooserDialog$WorkThreadHandler.class");
    Assert.assertNull(artifact);

    // TEST 2: add a type
    resourceModule.getModifiableSelfResourceContainer().addMovableUnit(
        MovableUnit.createFromType(type, getModularizedSystem()));
    Assert.assertTrue(resourceModule.containsType(type.getFullyQualifiedName()));
    Assert.assertEquals(2, getEvents().size());

    assertModule(((MovableUnitMovedEvent) getEvents().get(1)).getMovableUnit(), resourceModule);

  }

  @Test
  public void testClassificationChanged() throws AmbiguousElementException {

    // TEST 3: change the classification (group)
    // TODO!!: "/foo/bar""
    _resourceModule.setClassification(new Path("foo/bar/"));
    Assert.assertEquals(3, getEvents().size());

    // check the artifact tree model
    Assert.assertNotNull(_rootArtifact.getChild("foo|bar|jedit_1.0.0"));
  }
}
