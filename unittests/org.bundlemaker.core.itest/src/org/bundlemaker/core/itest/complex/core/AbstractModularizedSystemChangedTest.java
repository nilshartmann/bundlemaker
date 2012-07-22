package org.bundlemaker.core.itest.complex.core;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.event.ClassificationChangedEvent;
import org.bundlemaker.core.modules.event.GroupChangedEvent;
import org.bundlemaker.core.modules.event.IModularizedSystemChangedListener;
import org.bundlemaker.core.modules.event.ModuleClassificationChangedEvent;
import org.bundlemaker.core.modules.event.ModuleMovedEvent;
import org.bundlemaker.core.modules.event.MovableUnitMovedEvent;
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
public abstract class AbstractModularizedSystemChangedTest extends AbstractJeditTest {

  /** - */
  private List                        _events;

  /** - */
  protected IRootArtifact             _rootArtifact;

  /** - */
  protected IModifiableResourceModule _resourceModule;

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private final class TestListener implements IModularizedSystemChangedListener {

    @Override
    public void movableUnitRemoved(MovableUnitMovedEvent event) {
      _events.add(event);
    }

    @Override
    public void movableUnitAdded(MovableUnitMovedEvent event) {
      _events.add(event);
    }

    @Override
    public void moduleAdded(ModuleMovedEvent event) {
      _events.add(event);
    }

    @Override
    public void moduleRemoved(ModuleMovedEvent event) {
      _events.add(event);
    }

    @Override
    public void moduleClassificationChanged(ModuleClassificationChangedEvent event) {
      _events.add(event);
    }

    @Override
    public void classificationChanged(ClassificationChangedEvent event) {
      // TODO Auto-generated method stub
    }

    @Override
    public void groupAdded(GroupChangedEvent event) {
      // TODO Auto-generated method stub
    }

    @Override
    public void groupRemoved(GroupChangedEvent event) {
      // TODO Auto-generated method stub
    }
  }

  /**
   * {@inheritDoc}
   */
  @Before
  public void before() throws CoreException {

    super.before();

    // prepare
    getModularizedSystem().addModularizedSystemChangedListener(new TestListener());

    // prepare
    _events = new LinkedList();

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
   * @return
   */
  public IRootArtifact getRootArtifact() {
    return _rootArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IModifiableResourceModule getResourceModule() {
    return _resourceModule;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List getEvents() {
    return _events;
  }

  /**
   * <p>
   * </p>
   * 
   * @param event
   * @param module
   */
  public final void assertModule(IMovableUnit movableUnit, IModule module) {

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
