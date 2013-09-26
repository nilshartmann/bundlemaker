package org.bundlemaker.core.itest.jdtprojects;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.itestframework.AbstractJdtProjectTest;
import org.bundlemaker.core.project.BundleMakerProjectContentChangedEvent;
import org.bundlemaker.core.project.BundleMakerProjectContentChangedEvent.Type;
import org.bundlemaker.core.project.IBundleMakerProjectChangedListener;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ProjectContentChangedTest extends AbstractJdtProjectTest {

  //
  private List<BundleMakerProjectContentChangedEvent> _contentChangedEvents;

  //
  private IBundleMakerProjectChangedListener          _changedListener;

  /**
   * @throws CoreException
   */
  @Before
  public void before() throws CoreException {
    
    System.out.println("**********************************************************");
    System.out.println("");
    System.out.println("**********************************************************");
    
    super.before();

    //
    _contentChangedEvents = new LinkedList<BundleMakerProjectContentChangedEvent>();
    _changedListener = new IBundleMakerProjectChangedListener.Adapter() {
      @Override
      public void projectContentChanged(BundleMakerProjectContentChangedEvent event) {
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
  public void testSourceFileAdded() throws Exception {

    //
    addSource();

    //
    Assert.assertEquals(1, _contentChangedEvents.size());

    BundleMakerProjectContentChangedEvent event = _contentChangedEvents.get(0);
    Assert.assertEquals(Type.ADDED, event.getType());
    Assert.assertEquals(getBundleMakerProject(), event.getBundleMakerProject());
    System.out.println(event.getContentResource());
    Assert.assertEquals("newPack/NewClass.java", event.getContentResource().getPath());
  }

  @Test
  public void testSourceFileRemoved() throws Exception {

    //
    removeClassKlasse();

    //
    Assert.assertEquals(1, _contentChangedEvents.size());

    BundleMakerProjectContentChangedEvent event = _contentChangedEvents.get(0);
    Assert.assertEquals(Type.REMOVED, event.getType());
    Assert.assertEquals(getBundleMakerProject(), event.getBundleMakerProject());
    System.out.println(event.getContentResource());
    Assert.assertEquals("de/test/Klasse.java", event.getContentResource().getPath());
  }

  /**
   * @throws Exception
   */
  @Test
  public void testSourceFileChanged() throws Exception {

    //
    IProjectContentEntry contentEntry = getBundleMakerProject().getProjectDescription().getProjectContentEntry("0000000");
    Assert.assertEquals(2, contentEntry.getSourceResources().size());
    System.out.println(getBundleMakerProject().getProjectDescription().getProjectContentEntry("0000000").getSourceResources());
    
    //
    modifyClassKlasse();

    //
    Assert.assertEquals(1, _contentChangedEvents.size());

    BundleMakerProjectContentChangedEvent event = _contentChangedEvents.get(0);
    Assert.assertEquals(Type.MODIFIED, event.getType());
    Assert.assertEquals(getBundleMakerProject(), event.getBundleMakerProject());
    System.out.println(event.getContentResource());
    Assert.assertEquals("de/test/Klasse.java", event.getContentResource().getPath());
  }

}
