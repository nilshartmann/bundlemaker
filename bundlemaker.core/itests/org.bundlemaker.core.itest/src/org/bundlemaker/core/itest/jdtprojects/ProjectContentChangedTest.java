package org.bundlemaker.core.itest.jdtprojects;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.itestframework.AbstractJdtProjectTest;
import org.bundlemaker.core.project.ContentChangedEvent;
import org.bundlemaker.core.project.IBundleMakerProjectChangedListener;
import org.junit.Ignore;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ProjectContentChangedTest extends AbstractJdtProjectTest {

  @Test
  @Ignore
  public void test() throws Exception {
    
    //
    final List<ContentChangedEvent> contentChangedEvents = new LinkedList<ContentChangedEvent>();
    
    //
    IBundleMakerProjectChangedListener changedListener = new IBundleMakerProjectChangedListener.Adapter() {
      @Override
      public void projectContentChanged(ContentChangedEvent event) {
        contentChangedEvents.add(event);
      }
    };
    
    //
    getBundleMakerProject().addBundleMakerProjectChangedListener(changedListener);
    
    //
    modifyClassKlasse();
    
    //
    Assert.assertEquals(1, contentChangedEvents.size());
    
    //
    getBundleMakerProject().removeBundleMakerProjectChangedListener(changedListener);
  }
}
