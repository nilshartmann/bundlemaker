package org.bundlemaker.core.itest.coreapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.bundlemaker.core.itestframework.internal.TestProjectCreator;
import org.bundlemaker.core.project.BundleMakerProjectState;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ProjectDescriptionAwareBundleMakerProject_CREATED_Test {

  /** - */
  private IProjectDescriptionAwareBundleMakerProject _bmProject;

  @Before
  public void setUp() {
    if (_bmProject == null) {
      _bmProject = TestProjectCreator.getBundleMakerProject("BasicArtifactTest");
    }
  }

  @Test
  public void testState() {
    assertEquals(BundleMakerProjectState.CREATED, _bmProject.getState());
  }

  @Test
  public void testProjectDescriptionNotNull() {
    assertNotNull(_bmProject.getProjectDescription());
  }

  @Test
  public void testGetBundleMakerProject() {
    assertNotNull(_bmProject.getProjectDescription().getBundleMakerProject());
    assertEquals(_bmProject, _bmProject.getProjectDescription().getBundleMakerProject());
  }

  @Test
  public void testContentProvidersNotNullAndEmpty() {
    assertNotNull(_bmProject.getProjectDescription().getContentProviders());
    assertTrue(_bmProject.getProjectDescription().getContentProviders().isEmpty());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testContentProvidersUnmodifiable() {
    _bmProject.getProjectDescription().getContentProviders().remove(0);
  }
  
  @Test
  public void testContentNotNullAndEmpty() {
    assertNotNull(_bmProject.getProjectDescription().getContent());
    assertTrue(_bmProject.getProjectDescription().getContent().isEmpty());
  }

  @Test
  public void testGetProjectContentEntry() {
    assertNull(_bmProject.getProjectDescription().getProjectContentEntry("notThere"));
  }

  @Test(expected = Exception.class)
  public void testGetProjectContentEntryWithNull() {
    _bmProject.getProjectDescription().getProjectContentEntry(null);
    // // TODO
    // System.out.println(projectDescription.getJRE());
    // // TestProjectCreator.addProjectDescription(bmProject, "BasicArtifactTest");
  }
}
