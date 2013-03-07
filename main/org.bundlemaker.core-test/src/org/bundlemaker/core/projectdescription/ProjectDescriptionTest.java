package org.bundlemaker.core.projectdescription;

import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;
import org.bundlemaker.core.internal.projectdescription.gson.GsonProjectDescriptionHelper;
import org.bundlemaker.core.projectdescription.file.FileBasedProjectContentProvider;
import org.bundlemaker.core.projectdescription.spi.IModifiableProjectDescription;
import org.junit.Test;

public class ProjectDescriptionTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testProjectDescription() {

    FileBasedProjectContentProvider provider = new FileBasedProjectContentProvider();
    provider.setId("honk");
    provider.setName("name");
    provider.addRootPath(new VariablePath("BLa7BLa"), ProjectContentType.SOURCE);
    provider.addRootPath(new VariablePath("BLa7BLa23"), ProjectContentType.SOURCE);
    provider.addRootPath(new VariablePath("BinBin"), ProjectContentType.BINARY);

    IModifiableProjectDescription description = new BundleMakerProjectDescription(null);
    description.addContentProvider(provider);

    //
    String gsonString = GsonProjectDescriptionHelper.gson().toJson(description);
    System.out.println(gsonString);

    //
    BundleMakerProjectDescription descriptionNeu = GsonProjectDescriptionHelper.gson().fromJson(gsonString,
        BundleMakerProjectDescription.class);

    System.out.println(descriptionNeu.getContentProviders());
  }
}
