package org.bundlemaker.core.itest.complex.core;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IModularizedSystemChangedListener;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.ModuleClassificationChangedEvent;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.ModuleMovedEvent;
import org.bundlemaker.core.modules.MovableUnitMovedEvent;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.modules.modifiable.MovableUnit;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Path;
import org.junit.Test;

/**
 * <p>
 * Tests if events are sent if a modularized system changes.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModularizedSystemChangedTest extends AbstractJeditTest {

  private final class TestListener implements IModularizedSystemChangedListener {
    /**
     * {@inheritDoc}
     */
    @Override
    public void movableUnitRemoved(MovableUnitMovedEvent event) {
      _events.add(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void movableUnitAdded(MovableUnitMovedEvent event) {
      _events.add(event);
    }

    @Override
    public void moduleAdded(ModuleMovedEvent event) {
      // TODO Auto-generated method stub

    }

    @Override
    public void moduleRemoved(ModuleMovedEvent event) {
      // TODO Auto-generated method stub

    }

    @Override
    public void moduleClassificationChanged(ModuleClassificationChangedEvent event) {
      _events.add(event);
    }
  }

  /** - */
  private List _events;

  /**
   * <p>
   * </p>
   * 
   * @throws AmbiguousElementException
   */
  @Test
  public void testModularizedSystemChanged() throws AmbiguousElementException {

    // prepare
    _events = new LinkedList();
    getModularizedSystem().addModularizedSystemChangedListener(new TestListener());

    // check the artifact tree
    IRootArtifact rootArtifact = getModularizedSystem().getArtifactModel(
        ArtifactModelConfiguration.HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION);
    Assert.assertNotNull(rootArtifact);

    IBundleMakerArtifact artifact = rootArtifact
        .getChild("group1|group2|jedit_1.0.0|org|gjt|sp|jedit|browser|VFSFileChooserDialog$WorkThreadHandler.class");
    Assert.assertNotNull(artifact);

    // TEST 1: remove a type
    IModifiableResourceModule resourceModule = getModularizedSystem().getModifiableResourceModule(
        new ModuleIdentifier("jedit", "1.0.0"));
    IType type = getModularizedSystem().getType("org.gjt.sp.jedit.browser.VFSFileChooserDialog$WorkThreadHandler");
    Assert.assertTrue(resourceModule.containsType(type.getFullyQualifiedName()));

    resourceModule.getModifiableSelfResourceContainer().removeMovableUnit(MovableUnit.createFromType(type));
    Assert.assertFalse(resourceModule.containsType(type.getFullyQualifiedName()));
    Assert.assertEquals(1, _events.size());

    // check the resource model
    assertModule(((MovableUnitMovedEvent) _events.get(0)).getMovableUnit(), null);

    // check the artifact tree model
    artifact = rootArtifact
        .getChild("group1|group2|jedit_1.0.0|org|gjt|sp|jedit|browser|VFSFileChooserDialog$WorkThreadHandler.class");
    Assert.assertNull(artifact);

    // resourceModule.getModifiableSelfResourceContainer().remove(type.getSourceResource(), ContentType.SOURCE);
    // Assert.assertFalse(resourceModule.containsType(type.getFullyQualifiedName()));
    // Assert.assertEquals(1, events.size());
    //
    // resourceModule.getModifiableSelfResourceContainer().remove(type);
    // Assert.assertFalse(resourceModule.containsType(type.getFullyQualifiedName()));
    // Assert.assertEquals(1, events.size());

    // TEST 2: add a type
    resourceModule.getModifiableSelfResourceContainer().addMovableUnit(MovableUnit.createFromType(type));
    Assert.assertTrue(resourceModule.containsType(type.getFullyQualifiedName()));
    Assert.assertEquals(2, _events.size());

    assertModule(((MovableUnitMovedEvent) _events.get(1)).getMovableUnit(), resourceModule);

    // TEST 3: change the classification (group)
    // TODO!!: "/foo/bar""
    resourceModule.setClassification(new Path("foo/bar/"));
    Assert.assertEquals(3, _events.size());

    // check the artifact tree model
    artifact = rootArtifact.getChild("foo|bar|jedit_1.0.0");
    Assert.assertNotNull(artifact);
  }

  /**
   * <p>
   * </p>
   * 
   * @param event
   * @param module
   */
  public void assertModule(IMovableUnit movableUnit, IModule module) {

    if (movableUnit.hasAssociatedSourceResource()) {
      for (IType t : movableUnit.getAssociatedTypes()) {
        Assert.assertEquals(module, t.getModule(getModularizedSystem()));
      }
    }

    if (movableUnit.hasAssociatedBinaryResources()) {
      for (IResource r : movableUnit.getAssociatedBinaryResources()) {
        Assert.assertEquals(module, r.getAssociatedResourceModule(getModularizedSystem()));
      }
    }

    //
    if (movableUnit.hasAssociatedTypes()) {
      Assert.assertEquals(module,
          movableUnit.getAssociatedSourceResource().getAssociatedResourceModule(getModularizedSystem()));
    }
  }
}
