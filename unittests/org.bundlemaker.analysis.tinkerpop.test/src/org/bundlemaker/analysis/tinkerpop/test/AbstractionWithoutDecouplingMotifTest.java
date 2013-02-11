/*******************************************************************************
 * Copyright (c) 2013 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.analysis.tinkerpop.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import nz.ac.massey.cs.guery.MotifInstance;
import nz.ac.massey.cs.guery.Path;
import nz.ac.massey.cs.guery.adapters.blueprints.ElementCache;
import nz.ac.massey.cs.guery.adapters.blueprints.WrappingCache;

import org.bundlemaker.core.analysis.DependencyKind;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.junit.Assume;
import org.junit.Test;

import test.nz.ac.massey.cs.guery.adapters.blueprints.Utilities;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class AbstractionWithoutDecouplingMotifTest extends AbstractBundleMakerBlueprintsTest {
  WrappingCache cache = new WrappingCache();

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.analysis.tinkerpop.test.AbstractBundleMakerBlueprintsTest#assertCorrectTestModel()
   */
  @Override
  protected void assertCorrectTestModel() throws Exception {
    assertPackageExists("a.api");
    assertPackageExists("a.impl");
    assertPackageExists("client");

    ITypeArtifact aservice = getType("a.api.AService");
    assertTrue(aservice.getAssociatedType().isAbstractType());

    ITypeArtifact aserviceImpl = getType("a.impl.AServiceImpl");
    assertFalse(aserviceImpl.getAssociatedType().isAbstractType());

    ITypeArtifact client = getType("client.Client");
    ITypeArtifact brokenClient = getType("client.BrokenClient");

    getDependency(aserviceImpl, aservice, DependencyKind.IMPLEMENTS);

    getDependency(client, aservice, DependencyKind.USES);

    getDependency(brokenClient, aservice, DependencyKind.USES);
    getDependency(brokenClient, aserviceImpl, DependencyKind.USES);

  }

  @Test
  public void test_AbstractProperty() {
    ITypeArtifact a = getType("a.api.AService");
    assertEquals(true, a.getProperty("abstract"));

    ITypeArtifact ee = getType("a.impl.AServiceImpl");
    assertEquals(false, ee.getProperty("abstract"));
  }

  @Test
  public void test_ArtifactTypeProperty() {
    ITypeArtifact a = getType("a.api.AService");
    assertEquals("type", a.getProperty("artifacttype"));

  }

  @Test
  public void test_findAbstractionWithoutDecoupling() throws Exception {

    Assume.assumeTrue(cache.ensuresReferentialIntegrity());

    String AWD = "motif awd\n"
        + "select client, service, service_impl\n"
        + "where \"client.getProperty('artifacttype')=='type'\" and \"service.getProperty('artifacttype')=='type'\" and \"service_impl.getProperty('artifacttype')=='type'\"\n"
        + "and \"!client.getProperty('abstract')\" and \"service.getProperty('abstract')\" and \"!service_impl.getProperty('abstract')\"\n"
        + "connected by inherits(service_impl>service) and service_invocation(client>service)[1,1] and implementation_dependency(client>service_impl)\n"
        + "where \"inherits.getProperty('type')=='extends' || inherits.getProperty('type')=='implements'\" and \"service_invocation.getProperty('type')=='uses'\" and \"implementation_dependency.getProperty('type')=='uses'\"\n"
        + "group by \"client\" and \"service\"";

    List<MotifInstance<Vertex, Edge>> results = Utilities.query(getBlueprintsAdapter(), AWD);

    System.out.println(" ################ RESULT: ############");
    System.out.println("   SIZE: " + results.size());
    for (MotifInstance<Vertex, Edge> motifInstance : results) {
      System.out.println("  * INSTANCE: " + motifInstance);
      Vertex client = motifInstance.getVertex("client");
      System.out.println("    client : " + client);
      System.out.println("    service: " + motifInstance.getVertex("service"));
      System.out.println("    service_impl: " + motifInstance.getVertex("service_impl"));

      dumpPath(motifInstance, "inherits");
      dumpPath(motifInstance, "service_invocation");
      dumpPath(motifInstance, "implementation_dependency");

    }

    assertEquals(1, results.size());

  }

  private void dumpPath(MotifInstance<Vertex, Edge> motifInstance, String pathName) {
    Path<Vertex, Edge> path = motifInstance.getPath(pathName);
    System.out.println("    path   : '" + pathName + "': " + path);
    Vertex start = path.getStart();
    Vertex end = path.getEnd();
    System.out.println("      Start: " + start + " (" + start.getProperty("qname") + ")");
    System.out.println("      End  : " + end + " (" + end.getProperty("qname") + ")");
    Collection<Vertex> vertices = path.getVertices();
    if (vertices != null) {
      for (Vertex vertex : vertices) {
        System.out.println("        Vertex  : " + vertex + " (" + vertex.getProperty("qname") + ")");
      }
    } else {
      System.out.println("        No vertices");
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.analysis.tinkerpop.test.AbstractBundleMakerBlueprintsTest#getCache()
   */
  @Override
  protected ElementCache getCache() {
    return cache;
  }

  @Override
  protected String computeTestProjectName() {
    return "antipattern.awd";
  }

}
