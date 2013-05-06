package org.bundlemaker.core.mvn.content;

import static org.bundlemaker.core.mvn.content.MockHelper.mockBundleMakerPreferences_configuredRepositories;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.internal.projectdescription.api.IInternalProjectDescription;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectDescription;
import org.bundlemaker.core.util.prefs.IBundleMakerPreferences;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

public class MvnContentProviderTest {

  @Test
  public void test() throws CoreException {

    //
    IBundleMakerProject bundleMakerProject = mock(IBundleMakerProject.class);
    IInternalProjectDescription projectDescription = mock(IInternalProjectDescription.class);
    when(bundleMakerProject.getProjectDescription()).thenReturn(projectDescription);

    final IBundleMakerPreferences preferences = mockBundleMakerPreferences_configuredRepositories(
        System.getProperty("user.home") + File.separator + ".m2", "http://repo1.maven.org/maven2");

    //
    MvnContentProvider contentProvider = new MvnContentProvider() {
      @Override
      protected IBundleMakerPreferences getBundleMakerPreferences(IProjectDescription description) {
        return preferences;
      }
    };
    contentProvider.addMvnArtifact("org.apache.cxf", "cxf-api", "2.7.2");
    contentProvider.init(projectDescription);

    //
    List<IProjectContentEntry> entries = contentProvider.getBundleMakerProjectContent(null, bundleMakerProject);
    assertThat(entries.size(), is(119));
    
    for (IProjectContentEntry iProjectContentEntry : entries) {
      System.out.println(iProjectContentEntry.getName() + " : " + iProjectContentEntry.getVersion());
    }
  }
}
