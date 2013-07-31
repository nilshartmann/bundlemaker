package org.bundlemaker.core.mvn.content;

import static org.bundlemaker.core.mvn.content.MockHelper.mockBundleMakerPreferences_configuredRepositories;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.List;

import org.bundlemaker.core.common.prefs.IBundleMakerPreferences;
import org.bundlemaker.core.internal.api.project.IInternalProjectDescription;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectDescription;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

public class MvnContentProviderTest {

  @Test
  public void test() throws CoreException {

    //
    IProjectDescriptionAwareBundleMakerProject bundleMakerProject = mock(IProjectDescriptionAwareBundleMakerProject.class);
    IInternalProjectDescription projectDescription = mock(IInternalProjectDescription.class);
    when(bundleMakerProject.getProjectDescription()).thenReturn(projectDescription);

    final IBundleMakerPreferences preferences = mockBundleMakerPreferences_configuredRepositories(
        System.getProperty("user.home") + File.separator + ".m2", "http://repo1.maven.org/maven2");

    //
    MvnContentProvider contentProvider = new MvnContentProvider() {
      @Override
      protected IBundleMakerPreferences getBundleMakerPreferences() {
        return preferences;
      }
    };
    contentProvider.addMvnArtifact("org.apache.cxf", "cxf-api", "2.7.2");
    contentProvider.prepare();

    //
    List<IProjectContentEntry> entries = contentProvider.getBundleMakerProjectContent();
    assertThat(entries.size(), is(119));
    
    for (IProjectContentEntry iProjectContentEntry : entries) {
      System.out.println(iProjectContentEntry.getName() + " : " + iProjectContentEntry.getVersion());
    }
  }
}
