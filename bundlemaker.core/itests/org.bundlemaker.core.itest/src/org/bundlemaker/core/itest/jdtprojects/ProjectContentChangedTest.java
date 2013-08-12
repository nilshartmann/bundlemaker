package org.bundlemaker.core.itest.jdtprojects;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.itestframework.AbstractJdtProjectTest;
import org.bundlemaker.core.project.ContentChangedEvent;
import org.bundlemaker.core.project.IBundleMakerProjectChangedListener;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ProjectContentChangedTest extends AbstractJdtProjectTest {

  //
  private List<ContentChangedEvent>          _contentChangedEvents;

  //
  private IBundleMakerProjectChangedListener _changedListener;

  /**
   * @throws CoreException
   */
  @Before
  public void before() throws CoreException {
    super.before();

    //
    _contentChangedEvents = new LinkedList<ContentChangedEvent>();
    _changedListener = new IBundleMakerProjectChangedListener.Adapter() {
      @Override
      public void projectContentChanged(ContentChangedEvent event) {
        _contentChangedEvents.add(event);
      }
    };
    getBundleMakerProject().addBundleMakerProjectChangedListener(_changedListener);
  }

  /**
   * @throws CoreException
   */
  @After
  public void after() throws CoreException {

    //
    getBundleMakerProject().removeBundleMakerProjectChangedListener(_changedListener);

    //
    super.after();
  }

  /**
   * @throws Exception
   */
  @Test
  @Ignore
  public void testSourceFileChanged() throws Exception {

    //
    modifyClassKlasse();

    //
    Assert.assertEquals(1, _contentChangedEvents.size());
    Assert.assertEquals(ContentChangedEvent.Type.MODIFIED, _contentChangedEvents.get(0).getType());

  }

  /**
   * @throws Exception
   */
  @Test
  @Ignore
  public void testSourceFileAdded() throws Exception {

    //
    addSource();

    //
    Assert.assertEquals(1, _contentChangedEvents.size());
    Assert.assertEquals(ContentChangedEvent.Type.ADDED, _contentChangedEvents.get(0).getType());
  }

  @Test
  @Ignore
  public void testSourceFileRemoved() throws Exception {

    //
    removeClassKlasse();

    //
    Assert.assertEquals(1, _contentChangedEvents.size());
    Assert.assertEquals(ContentChangedEvent.Type.REMOVED, _contentChangedEvents.get(0).getType());
  }
}
