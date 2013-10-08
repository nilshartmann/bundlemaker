package org.bundlemaker.core.itest.jdtprojects;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.AnalysisCore;
import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact.IResourceArtifactContent;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.itestframework.AbstractJdtProjectTest;
import org.bundlemaker.core.jtype.ITypeResource;
import org.bundlemaker.core.project.BundleMakerProjectContentChangedEvent;
import org.bundlemaker.core.project.BundleMakerProjectContentChangedEvent.Type;
import org.bundlemaker.core.project.IBundleMakerProjectChangedListener;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
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
    IRootArtifact rootArtifact = AnalysisCore.getAnalysisModel(getModularizedSystem(),
        AnalysisModelConfiguration.HIERARCHICAL_SOURCE_RESOURCES_CONFIGURATION);

    //
    IModuleArtifact moduleArtifact = AnalysisModelQueries.getModuleArtifact(rootArtifact,
        "SimpleArtifactModelTest-JDT <bin>");
    Assert.assertNotNull(moduleArtifact);

    //
    assertEquals(2, AnalysisModelQueries.findSuccessors(moduleArtifact, IResourceArtifact.class).size());

    //
    removeClassKlasse();

    //
    Assert.assertEquals(1, _contentChangedEvents.size());

    //
    BundleMakerProjectContentChangedEvent event = _contentChangedEvents.get(0);
    Assert.assertEquals(Type.REMOVED, event.getType());
    Assert.assertEquals(getBundleMakerProject(), event.getBundleMakerProject());
    Assert.assertEquals("de/test/Klasse.java", event.getContentResource().getPath());

    //
    assertEquals(1, AnalysisModelQueries.findSuccessors(moduleArtifact, IResourceArtifact.class).size());
  }

  /**
   * @throws Exception
   */
  @Test
  public void testSourceFileChanged() throws Exception {

    //
    IProjectContentEntry contentEntry = getBundleMakerProject().getProjectDescription().getProjectContentEntry(
        "0000000");
    Assert.assertEquals(2, contentEntry.getSourceResources().size());

    //
    IProjectContentResource contentResource = contentEntry.getResource("de/test/Klasse.java", ResourceType.SOURCE);

    ITypeResource typeResource = contentResource.adaptAs(ITypeResource.class);
    Assert.assertNotNull(typeResource);
    Assert.assertNotNull(typeResource.getContainedType());
    Assert.assertEquals(2, typeResource.getContainedType().getReferences().size());
    Assert.assertEquals(1, typeResource.getReferences().size());

    //
    modifyClassKlasse();

    //
    Assert.assertEquals(1, _contentChangedEvents.size());

    BundleMakerProjectContentChangedEvent event = _contentChangedEvents.get(0);
    Assert.assertEquals(Type.MODIFIED, event.getType());
    Assert.assertEquals(getBundleMakerProject(), event.getBundleMakerProject());
    Assert.assertEquals("de/test/Klasse.java", event.getContentResource().getPath());

    //
    typeResource = event.getContentResource().adaptAs(ITypeResource.class);
    Assert.assertNotNull(typeResource);
    Assert.assertNotNull(typeResource.getContainedType());
    Assert.assertEquals(3, typeResource.getContainedType().getReferences().size());
    Assert.assertEquals(1, typeResource.getReferences().size());
  }

}
