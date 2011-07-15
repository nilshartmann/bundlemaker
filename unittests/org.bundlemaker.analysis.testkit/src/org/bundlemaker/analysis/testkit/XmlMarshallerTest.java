package org.bundlemaker.analysis.testkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.bundlemaker.analysis.testkit.framework.AbstractTestKitTest;
import org.bundlemaker.analysis.xml.ArtifactMarshallerUnmarshallerFactory;
import org.bundlemaker.analysis.xml.IArtifactMarshaller;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class XmlMarshallerTest extends AbstractTestKitTest {

  /**
   * <p>
   * </p>
   * 
   * @throws FileNotFoundException
   */
  @Test
  public void testMarshalling() throws Exception {

    //
    IArtifactMarshaller artifactMarshaller = ArtifactMarshallerUnmarshallerFactory.createMarshaller();

    // actual
    File xmlFile = new File(System.getProperty("user.dir"), "result" + File.separatorChar + "ArtifactModel_"
        + getTimestamp() + ".xml");

    //
    artifactMarshaller.marshal(getRoot(), new FileOutputStream(xmlFile));
  }
}
