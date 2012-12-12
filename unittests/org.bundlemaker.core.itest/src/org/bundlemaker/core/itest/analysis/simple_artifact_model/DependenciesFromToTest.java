package org.bundlemaker.core.itest.analysis.simple_artifact_model;

import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.bundlemaker.core.analysis.ArtifactHelper;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.itest._framework.analysis.ArtifactVisitorUtils;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.itestframework.ArtifactTestUtil;
import org.bundlemaker.core.resource.IType;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DependenciesFromToTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void testModularizedSystem_getReferencedTypes() throws Exception {

    //
    Map<String, Set<IType>> referencedTypes = getModularizedSystem().getReferencedTypes();

    //
    Assert.assertEquals(2, referencedTypes.size());

    //
    Set<IType> referencedBy = referencedTypes.get("de.test.Test");
    Assert.assertEquals(1, referencedBy.size());
    Assert.assertEquals("de.test.Klasse", referencedBy.toArray(new IType[0])[0].getFullyQualifiedName());

    referencedBy = referencedTypes.get("javax.activation.DataHandler");
    Assert.assertEquals(1, referencedBy.size());
    Assert.assertEquals("de.test.Klasse", referencedBy.toArray(new IType[0])[0].getFullyQualifiedName());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void dependenciesFromTo() throws Exception {

    //
    ITypeArtifact klasse = ArtifactVisitorUtils.findTypeArtifact(_binModel.getRootArtifact(), "de.test.Klasse");
    ITypeArtifact test = ArtifactVisitorUtils.findTypeArtifact(_binModel.getRootArtifact(), "de.test.Test");

    //
    Assert.assertNotNull(klasse.getDependencyTo(test));
    Assert.assertNotNull(test.getDependencyFrom(klasse));

    //
    Assert.assertEquals("Dependency( de.test.Klasse USES de.test.Test: 1 )", klasse.getDependencyTo(test)
        .toString());
    Assert.assertEquals("Dependency( de.test.Klasse USES de.test.Test: 1 )", test.getDependencyFrom(klasse)
        .toString());

    //
    Assert.assertEquals(klasse.getDependencyTo(test), test.getDependencyFrom(klasse));
    
    
    //
    IResourceArtifact klasseRes = _binModel.getKlasseResource();
    IResourceArtifact testRes = _binModel.getTestResource();
    
    //
    System.out.println(klasseRes.getDependencyTo(testRes));
    
    IDependency dependency = testRes.getDependencyFrom(klasseRes);
    System.out.println(dependency);
    for (IDependency coreDependency : dependency.getCoreDependencies()) {
      System.out.println(" - " + coreDependency);
    }
    
    //
    Assert.assertNotNull(klasseRes.getDependencyTo(testRes));
    Assert.assertNotNull(testRes.getDependencyFrom(klasseRes));

    //
    Assert.assertEquals("Dependency( de/test/Klasse.class USES de/test/Test.class: 1 )", klasseRes.getDependencyTo(testRes)
        .toString());
    Assert.assertEquals("Dependency( de/test/Klasse.class USES de/test/Test.class: 1 )", testRes.getDependencyFrom(klasseRes)
        .toString());

    //
    Assert.assertEquals(klasseRes.getDependencyTo(testRes), testRes.getDependencyFrom(klasseRes));

  }
}
