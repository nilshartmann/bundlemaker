package org.bundlemaker.core.mvn.exporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.apache.maven.model.merge.ModelMerger;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

public class PomBuilder {

  public static void main(String[] args) throws FileNotFoundException, IOException, XmlPullParserException {

    //
    File templateFile = new File("D:/development/bundlemaker/temp/template.xml");
    Model template = new MavenXpp3Reader().read(new FileInputStream(templateFile));
    
    //
    Model model = new Model();
    
    new ModelMerger().merge(model, template, true, null);

    
    
    
    Parent parent = new Parent();
    parent.setGroupId("asd");
    parent.setArtifactId("asdad");
    parent.setVersion("1.2.3");
    model.setParent(parent);

    //
    File resultFile = new File("D:/development/bundlemaker/temp/pom.xml");
    resultFile.getParentFile().mkdirs();
    new MavenXpp3Writer().write(new FileOutputStream(resultFile), model);
  }
}
