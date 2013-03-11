package org.bundlemaker.core.projectdescription.file;

import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.projectdescription.VariablePath;
import org.bundlemaker.core.projectdescription.spi.AbstractProjectContentProvider;
import org.junit.Assert;
import org.junit.Test;

public class FileBasedProjectContentProviderTest {

  @Test
  public void test() {

    //
    FileBasedProjectContentProvider provider = new FileBasedProjectContentProvider();
    provider.setId("honk");
    provider.setName("name");
    provider.addRootPath(new VariablePath("${eclipse_home}"), ProjectContentType.SOURCE);

    String gsonString = provider.toJson();

    FileBasedProjectContentProvider newProvider = AbstractProjectContentProvider.fromJson(gsonString, FileBasedProjectContentProvider.class);

    Assert.assertEquals(provider, newProvider);
  }
}
