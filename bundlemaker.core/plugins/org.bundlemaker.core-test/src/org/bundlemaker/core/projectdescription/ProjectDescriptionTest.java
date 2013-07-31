package org.bundlemaker.core.projectdescription;

import junit.framework.Assert;

import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;
import org.bundlemaker.core.internal.projectdescription.gson.GsonProjectDescriptionHelper;
import org.bundlemaker.core.project.IModifiableProjectDescription;
import org.bundlemaker.core.project.VariablePath;
import org.bundlemaker.core.project.filecontent.FileBasedProjectContentProvider;
import org.junit.Test;

public class ProjectDescriptionTest {

  /**
   * <p>
   * </p>
   */
  @Test
  @Ignore
  public void testJsonSerialization() {

    FileBasedProjectContentProvider provider = new FileBasedProjectContentProvider();
    provider.setId("honk");
    provider.setName("name");
    provider.addRootPath(new VariablePath("BLa7BLa"), ResourceType.SOURCE);
    provider.addRootPath(new VariablePath("BLa7BLa23"), ResourceType.SOURCE);
    provider.addRootPath(new VariablePath("BinBin"), ResourceType.BINARY);

    IModifiableProjectDescription description = new BundleMakerProjectDescription(null);
    description.addContentProvider(provider);

    //
    String gsonString = GsonProjectDescriptionHelper.gson().toJson(description);

    //
    BundleMakerProjectDescription descriptionNeu = GsonProjectDescriptionHelper.gson().fromJson(gsonString,
        BundleMakerProjectDescription.class);
    String gsonStringNeu = GsonProjectDescriptionHelper.gson().toJson(descriptionNeu);
    
    //
    Assert.assertEquals(gsonString, gsonStringNeu);
  }
}
