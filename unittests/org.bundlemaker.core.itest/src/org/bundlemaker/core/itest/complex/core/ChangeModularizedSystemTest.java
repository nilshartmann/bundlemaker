package org.bundlemaker.core.itest.complex.core;

import junit.framework.Assert;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.MovableUnitMovedEvent;
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
public class ChangeModularizedSystemTest extends AbstractModularizedSystemChangedTest {

  /**
   * <p>
   * </p>
   * 
   * @throws AmbiguousElementException
   */
  @Test
  public void testModularizedSystemChanged() throws AmbiguousElementException {

    // test that "org.gjt.sp.jedit.browser.VFSFileChooserDialog$WorkThreadHandler.class" is contained
    IBundleMakerArtifact artifact = _rootArtifact
        .getChild("group1|group2|jedit_1.0.0|org|gjt|sp|jedit|browser|VFSFileChooserDialog$WorkThreadHandler.class");
    Assert.assertNotNull(artifact);

    // TEST 1: remove the type "org.gjt.sp.jedit.browser.VFSFileChooserDialog$WorkThreadHandler"
    IType type = getModularizedSystem().getType("org.gjt.sp.jedit.browser.VFSFileChooserDialog$WorkThreadHandler");
    Assert.assertTrue(_resourceModule.containsType(type.getFullyQualifiedName()));
    _resourceModule.getModifiableSelfResourceContainer().removeMovableUnit(
        MovableUnit.createFromType(type, getModularizedSystem()));

    // - check the resource model
    Assert.assertFalse(_resourceModule.containsType(type.getFullyQualifiedName()));
    Assert.assertEquals(1, getEvents().size());

    // - check the event list
    assertModule(((MovableUnitMovedEvent) getEvents().get(0)).getMovableUnit(), null);

    // - check the artifact tree model
    artifact = _rootArtifact
        .getChild("group1|group2|jedit_1.0.0|org|gjt|sp|jedit|browser|VFSFileChooserDialog$WorkThreadHandler.class");
    Assert.assertNull(artifact);

    // TEST 2: add a type
    _resourceModule.getModifiableSelfResourceContainer().addMovableUnit(
        MovableUnit.createFromType(type, getModularizedSystem()));
    Assert.assertTrue(_resourceModule.containsType(type.getFullyQualifiedName()));
    Assert.assertEquals(2, getEvents().size());

    // - check the event list
    assertModule(((MovableUnitMovedEvent) getEvents().get(1)).getMovableUnit(), _resourceModule);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws AmbiguousElementException
   */
  @Test
  public void testClassificationChanged() throws AmbiguousElementException {

    // TEST 3: change the classification (group)
    // TODO!!: "/foo/bar""
    _resourceModule.setClassification(new Path("foo/bar/"));
    Assert.assertEquals(1, getEvents().size());

    // check the artifact tree model
    Assert.assertNotNull(_rootArtifact.getChild("foo|bar|jedit_1.0.0"));
  }
}
