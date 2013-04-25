package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.bundlemaker.core._type.IType;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DependenciesFromToTest extends AbstractSimpleArtifactModelTest {

  // /**
  // * <p>
  // * </p>
  // *
  // * @throws Exception
  // */
  // @Test
  // public void testModularizedSystem_getReferencedTypes() throws Exception {
  //
  // //
  // Map<String, Set<IType>> referencedTypes = getModularizedSystem().getReferencedTypes();
  //
  // //
  // Assert.assertEquals(2, referencedTypes.size());
  //
  // //
  // Set<IType> referencedBy = referencedTypes.get("de.test.Test");
  // Assert.assertEquals(1, referencedBy.size());
  // Assert.assertEquals("de.test.Klasse", referencedBy.toArray(new IType[0])[0].getFullyQualifiedName());
  //
  // referencedBy = referencedTypes.get("javax.activation.DataHandler");
  // Assert.assertEquals(1, referencedBy.size());
  // Assert.assertEquals("de.test.Klasse", referencedBy.toArray(new IType[0])[0].getFullyQualifiedName());
  // }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void dependenciesFromTo() throws Exception {

    //
    ITypeArtifact klasse = AnalysisModelQueries.findTypeArtifactByQualifiedName(getBinModel().getRootArtifact(), "de.test.Klasse");
    ITypeArtifact test = AnalysisModelQueries.findTypeArtifactByQualifiedName(getBinModel().getRootArtifact(), "de.test.Test");

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
    IResourceArtifact klasseRes = getBinModel().getKlasseResource();
    IResourceArtifact testRes = getBinModel().getTestResource();
    
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
