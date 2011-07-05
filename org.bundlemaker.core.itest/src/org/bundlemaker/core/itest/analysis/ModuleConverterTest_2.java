package org.bundlemaker.core.itest.analysis;

import java.io.IOException;
import java.io.InputStream;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.itestframework.util.Util;
import org.bundlemaker.core.projectdescription.ContentType;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleConverterTest_2 extends AbstractModularizedSystemTest {

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * @throws IOException
   */
  @Test
  public void testSourcesAndTypes() throws CoreException, IOException {

    // Step 1: transform the model
    IAdvancedArtifact rootArtifact = (IAdvancedArtifact) ModelTransformer.transform(getModularizedSystem());
    Assert.assertNotNull(rootArtifact);

    //
    IArtifact artifact = rootArtifact.getChild("bla/blub/jedit_1.0.0");
    Assert.assertNotNull(artifact);

    //
    InputStream inputstream = getClass().getResourceAsStream("results/ModuleConverterTest_2-SourceAndTypes.txt");
    String fresult = ArtifactUtils.artifactToString(artifact);
    Util.equalsIgnoreWhitespace(Util.convertStreamToString(inputstream), fresult);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * @throws IOException
   */
  @Test
  public void testBinariesAndTypes() throws CoreException, IOException {

    // Step 1: transform the model
    IAdvancedArtifact rootArtifact = (IAdvancedArtifact) ModelTransformer.transform(getModularizedSystem(),
        ContentType.BINARY);
    Assert.assertNotNull(rootArtifact);

    //
    IArtifact artifact = rootArtifact.getChild("bla/blub/jedit_1.0.0");
    Assert.assertNotNull(artifact);

    //
    String fresult = ArtifactUtils.artifactToString(artifact);
    // FileWriter fileWriter = new
    // FileWriter("R:\\environments\\bundlemaker64-environment\\git-repository\\org.bundlemaker.core.itest\\src\\org\\bundlemaker\\core\\itest\\analysis\\results\\ModuleConverterTest_2-BinariesAndTypes.txt");
    // fileWriter.write(fresult);
    // fileWriter.flush();
    // fileWriter.close();

    //
    InputStream inputstream = getClass().getResourceAsStream("results/ModuleConverterTest_2-BinariesAndTypes.txt");
    Assert.assertTrue(Util.equalsIgnoreWhitespace(Util.convertStreamToString(inputstream), fresult));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return "jedit";
  }
}
