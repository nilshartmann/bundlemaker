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
public class CircularDependencyMotifTest extends AbstractBundleMakerBlueprintsTest {
  WrappingCache cache = new WrappingCache();

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.analysis.tinkerpop.test.AbstractBundleMakerBlueprintsTest#assertCorrectTestModel()
   */
  @Override
  protected void assertCorrectTestModel() throws Exception {
    assertPackageExists("a");
    assertPackageExists("b");
    assertPackageExists("c");
    assertPackageExists("d");
    assertPackageExists("e");
    assertPackageExists("e.ee");

    ITypeArtifact a = getType("a.A");
    ITypeArtifact b = getType("b.B");
    ITypeArtifact c = getType("c.C");
    ITypeArtifact d = getType("d.D");
    ITypeArtifact e = getType("e.E");
    ITypeArtifact ee = getType("e.ee.EE");

    getDependency(a, b, DependencyKind.USES);
    getDependency(b, c, DependencyKind.USES);
    getDependency(c, a, DependencyKind.USES);
    getDependency(d, a, DependencyKind.USES);
    getDependency(e, ee, DependencyKind.USES);
    getDependency(ee, e, DependencyKind.USES);

  }

  // @Test
  public void test_NamespaceProperty() {
    ITypeArtifact a = getType("a.A");
    assertEquals("a", a.getProperty("namespace"));

    ITypeArtifact ee = getType("e.ee.EE");
    assertEquals("e.ee", ee.getProperty("namespace"));
  }

  @Test
  public void test_findCircularDependencies() throws Exception {

    Assume.assumeTrue(cache.ensuresReferentialIntegrity());

    String CD = "motif cd\n"
        + "select inside1,inside2,outside1,outside2\n"
        + "where \"inside1.getProperty('namespace')==inside2.getProperty('namespace')\" and \"inside1.getProperty('namespace')!=outside1.getProperty('namespace')\" and \"inside1.getProperty('namespace')!=outside2.getProperty('namespace')\"\n"
        + "connected by outgoing(inside1>outside1)[1,1] and incoming(outside2>inside2)[1,1] and path(outside1>outside2)[0,*]\n"
        + "group by \"inside1.namespace\"\n";

    List<MotifInstance<Vertex, Edge>> results = Utilities.query(getBlueprintsAdapter(), CD);

    System.out.println(" ################ RESULT: ############");
    System.out.println("   SIZE: " + results.size());
    for (MotifInstance<Vertex, Edge> motifInstance : results) {
      System.out.println("  * INSTANCE: " + motifInstance);
      Vertex inside1 = motifInstance.getVertex("inside1");
      System.out.println("    inside1 : " + inside1);
      Vertex inside2 = motifInstance.getVertex("inside2");
      // inside namespaces must be equal since they are start- and end-point of the circle
      assertEquals(inside1.getProperty("namespace"), inside2.getProperty("namespace"));
      System.out.println("    inside2 : " + inside2);
      System.out.println("    outside1: " + motifInstance.getVertex("outside1"));
      System.out.println("    outside2: " + motifInstance.getVertex("outside2"));

      dumpPath(motifInstance, "outgoing");
      dumpPath(motifInstance, "incoming");
      dumpPath(motifInstance, "path");

    }

    // A-B-C,B-C-A,C-A-B,E-EE,EE-e
    assertEquals(5, results.size());

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
    return "antipattern.cd";
  }

}
