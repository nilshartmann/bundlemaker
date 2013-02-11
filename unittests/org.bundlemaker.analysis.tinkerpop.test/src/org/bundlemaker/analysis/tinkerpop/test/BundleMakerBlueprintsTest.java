/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nz.ac.massey.cs.guery.MotifInstance;
import nz.ac.massey.cs.guery.adapters.blueprints.AlwaysCheckCache;
import nz.ac.massey.cs.guery.adapters.blueprints.ElementCache;
import nz.ac.massey.cs.guery.adapters.blueprints.WrappingCache;

import org.bundlemaker.core.analysis.DependencyKind;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import test.nz.ac.massey.cs.guery.adapters.blueprints.Utilities;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
@RunWith(Parameterized.class)
public class BundleMakerBlueprintsTest extends AbstractBundleMakerBlueprintsTest {

  final String         R1    = "Interface1-Class1";

  final String         R2    = "Class1-Class2";

  final String         R3    = "Class2-Interface1";

  final String         R4    = "Class2-Class1";

  private ElementCache cache = null;

  interface CacheFactory {
    ElementCache createCache();
  }

  @Parameters
  public static List<Object[]> getCaches() {
    Object[][] arr = { { new CacheFactory() {
      @Override
      public ElementCache createCache() {
        return new WrappingCache();
      }
    } }, { new CacheFactory() {

      @Override
      public ElementCache createCache() {
        return new AlwaysCheckCache();
      }
    } } };
    return Arrays.asList(arr);

  }

  public BundleMakerBlueprintsTest(CacheFactory cacheFactory) {
    cache = cacheFactory.createCache();
  }

  @Override
  @Before
  public void before() throws CoreException {
    super.before();

    //
    assertCorrectTestModel();

  }

  /**
   * @return the cache
   */
  @Override
  public ElementCache getCache() {
    return cache;
  }

  @Override
  protected void assertCorrectTestModel() {
    assertPackageExists("com.example");
    ITypeArtifact cl1 = getType("com.example.Class1");
    ITypeArtifact cl2 = getType("com.example.Class2");
    ITypeArtifact if1 = getType("com.example.Interface1");

    // r1
    IDependency r1 = getDependency(if1, cl1, DependencyKind.USES);
    assertEquals(R1, r1.getProperty("name"));

    // r2
    IDependency r2 = getDependency(cl1, cl2, DependencyKind.USES);
    assertEquals(R2, r2.getProperty("name"));

    // r3
    IDependency r3 = getDependency(cl2, if1, DependencyKind.IMPLEMENTS);
    assertEquals(R3, r3.getProperty("name"));

    // r4
    IDependency r4 = getDependency(cl2, cl1, DependencyKind.USES);
    assertEquals(R4, r4.getProperty("name"));
  }

  @Test
  public void testVertexId() {
    Vertex v = getVertex("com.example.Class1");
    assertNotNull(v.getId());
  }

  @Test
  public void testVertexIdsInBaseGraph() {
    for (Vertex v : getGraph().getVertices()) {
      assertNotNull(v.getId());
    }
  }

  @Test
  public void testEdgeIdsInBaseGraph() {
    for (Edge e : getGraph().getEdges()) {
      assertNotNull(e.getId());
    }
  }

  @Test
  public void testEdgeId() {
    Edge e = getEdge(R1);
    assertNotNull(e.getId());
  }

  // ---------
  // note that virtual start node n[0] is also counted
  @Test
  @Ignore("does not work correctly, as our model contains the JRE in addition to the three test classes")
  public void testVertexCount() {
    assertEquals(4, getBlueprintsAdapter().getVertexCount());
  }

  @Test
  @Ignore("does not work correctly, as our model contains the JRE in addition to the three test classes")
  public void testSizeOfVertices() {
    assertEquals(4, count(getBlueprintsAdapter().getVertices()));
  }

  @Test
  public void testEdgeCount() {
    assertEquals(4, getBlueprintsAdapter().getEdgeCount());
  }

  @Test
  public void testSizeOfEdges() {
    assertEquals(4, count(getBlueprintsAdapter().getEdges()));
  }

  @Test
  public void testVertex1() {
    assertNotNull(getVertex("com.example.Class1"));
  }

  @Test
  public void testVertex2() {
    assertNotNull(getVertex("com.example.Class2"));
  }

  @Test
  public void testVertex3() {
    assertNotNull(getVertex("com.example.Interface1"));
  }

  @Test
  public void testEdge1() {
    assertNotNull(getEdge(R1));
  }

  @Test
  public void testEdge2() {
    assertNotNull(getEdge(R2));
  }

  @Test
  public void testEdge3() {
    assertNotNull(getEdge(R3));
  }

  @Test
  public void testEdge4() {
    assertNotNull(getEdge(R4));
  }

  @Test
  public void testReferentialIntegrity1() {

    Assume.assumeTrue(cache.ensuresReferentialIntegrity());

    Vertex cl1 = getVertex("com.example.Class1");
    Vertex cl2 = getVertex("com.example.Class2");
    Edge r2 = getEdge(R2);
    assertNotNull(r2);

    // System.out.println(r2);
    // System.out.println(graph.getStart(r2));
    // System.out.println(graph.getEnd(r2));

    assertEquals(cl1, getBlueprintsAdapter().getStart(r2));
    assertEquals(cl2, getBlueprintsAdapter().getEnd(r2));

    // the cache must also ensure identity!!
    // this will fail when using the NullCache and an underlying db like neo4j that
    // does not support referential integrity
    assertTrue(cl1 == getBlueprintsAdapter().getStart(r2));
    assertTrue(cl2 == getBlueprintsAdapter().getEnd(r2));
  }

  @Test
  public void testInEdges1() {
    Vertex cl1 = getVertex("com.example.Class1");
    Iterator<Edge> in = getBlueprintsAdapter().getInEdges(cl1);
    assertEquals(2, count(in));
  }

  @Test
  public void testInEdges2() {
    Vertex if1 = getVertex("com.example.Interface1");
    Iterator<Edge> in = getBlueprintsAdapter().getInEdges(if1);
    assertEquals(1, count(in));
  }

  @Test
  public void testInEdges3() {
    // check containment using ==
    Assume.assumeTrue(cache.ensuresReferentialIntegrity());

    Vertex cl1 = getVertex("com.example.Class1");
    Iterator<Edge> in = getBlueprintsAdapter().getInEdges(cl1);
    Edge r1 = getEdge(R1);
    Edge r4 = getEdge(R4);
    assertTrue(contains(in, r1, r4));
  }

  @Test
  public void testInEdges4() {
    // check containment using ==
    Assume.assumeTrue(cache.ensuresReferentialIntegrity());

    Vertex if1 = getVertex("com.example.Interface1");
    Iterator<Edge> in = getBlueprintsAdapter().getInEdges(if1);
    Edge r3 = getEdge(R3);
    assertTrue(contains(in, r3));
  }

  @Test
  public void testOutEdges1() {
    Vertex cl2 = getVertex("com.example.Class2");
    Iterator<Edge> out = getBlueprintsAdapter().getOutEdges(cl2);
    assertEquals(2, count(out));
  }

  @Test
  public void testOutEdges2() {
    Vertex if1 = getVertex("com.example.Interface1");
    Iterator<Edge> out = getBlueprintsAdapter().getInEdges(if1);
    assertEquals(1, count(out));
  }

  @Test
  public void testOutEdges3() {
    // check containment using ==
    Assume.assumeTrue(cache.ensuresReferentialIntegrity());

    Vertex cl2 = getVertex("com.example.Class2");
    Iterator<Edge> out = getBlueprintsAdapter().getOutEdges(cl2);
    Edge r3 = getEdge(R3);
    Edge r4 = getEdge(R4);
    assertTrue(contains(out, r3, r4));
  }

  @Test
  public void testOutEdges4() {
    // check containment using ==
    Assume.assumeTrue(cache.ensuresReferentialIntegrity());

    Vertex if1 = getVertex("com.example.Interface1");
    Iterator<Edge> out = getBlueprintsAdapter().getOutEdges(if1);
    Edge r1 = getEdge(R1);
    assertTrue(contains(out, r1));
  }

  // tests by type whether all vertices and edges are cached
  @Test
  public void testVertexTypes1() {
    Assume.assumeTrue(cache instanceof WrappingCache);
    assertTrue(getVertex("com.example.Interface1") instanceof WrappingCache.GVertex);
    assertTrue(getVertex("com.example.Class1") instanceof WrappingCache.GVertex);
    assertTrue(getVertex("com.example.Class2") instanceof WrappingCache.GVertex);
  }

  @Test
  public void testEdgeTypes1() {
    Assume.assumeTrue(cache instanceof WrappingCache);
    assertTrue(getEdge(R1) instanceof WrappingCache.GEdge);
    assertTrue(getEdge(R2) instanceof WrappingCache.GEdge);
    assertTrue(getEdge(R3) instanceof WrappingCache.GEdge);
    assertTrue(getEdge(R4) instanceof WrappingCache.GEdge);
  }

  @Test
  public void testVertexTypes2() {
    Assume.assumeTrue(cache instanceof WrappingCache);
    assertTrue(getBlueprintsAdapter().getStart(getEdge(R1)) instanceof WrappingCache.GVertex);
    assertTrue(getBlueprintsAdapter().getEnd(getEdge(R1)) instanceof WrappingCache.GVertex);
    assertTrue(getBlueprintsAdapter().getStart(getEdge(R2)) instanceof WrappingCache.GVertex);
    assertTrue(getBlueprintsAdapter().getEnd(getEdge(R2)) instanceof WrappingCache.GVertex);
    assertTrue(getBlueprintsAdapter().getStart(getEdge(R3)) instanceof WrappingCache.GVertex);
    assertTrue(getBlueprintsAdapter().getEnd(getEdge(R3)) instanceof WrappingCache.GVertex);
    assertTrue(getBlueprintsAdapter().getStart(getEdge(R4)) instanceof WrappingCache.GVertex);
    assertTrue(getBlueprintsAdapter().getEnd(getEdge(R4)) instanceof WrappingCache.GVertex);
  }

  @Test
  public void testEdgeTypes2() {
    Assume.assumeTrue(cache instanceof WrappingCache);
    Vertex v = getVertex("com.example.Interface1");
    Iterator<Edge> in = getBlueprintsAdapter().getInEdges(v);
    while (in.hasNext()) {
      assertTrue(in.next() instanceof WrappingCache.GEdge);
    }
    Iterator<Edge> out = getBlueprintsAdapter().getInEdges(v);
    while (out.hasNext()) {
      assertTrue(out.next() instanceof WrappingCache.GEdge);
    }

    v = getVertex("com.example.Class1");
    in = getBlueprintsAdapter().getInEdges(v);
    while (in.hasNext()) {
      assertTrue(in.next() instanceof WrappingCache.GEdge);
    }
    out = getBlueprintsAdapter().getInEdges(v);
    while (out.hasNext()) {
      assertTrue(out.next() instanceof WrappingCache.GEdge);
    }
  }

  @Test
  public void testGuerySTK() throws Exception {

    Assume.assumeTrue(cache.ensuresReferentialIntegrity());

    String STK = "motif stk\n"
        + "select type,supertype\n"
        + "connected by inherits(type>supertype) and uses(supertype>type)\n"
        + "where \"inherits.getProperty('type')==\'extends\' || inherits.getProperty('type')==\'implements\'\" and \"uses.getProperty('type')==\'uses\'\"\n";

    // String STK =
    // "motif stk\n"+
    // "select type,supertype\n"+
    // "connected by inherits(type>supertype) and uses(supertype>type)\n"+
    // "where \"inherits.label==\'extends\' || inherits.label==\'implements\'\" and \"uses.label==\'uses\'\"\n";

    List<MotifInstance<Vertex, Edge>> results = Utilities.query(getBlueprintsAdapter(), STK);
    assertEquals(1, results.size());

    MotifInstance<Vertex, Edge> instance = results.get(0);

    assertEquals(instance.getVertex("supertype"), getVertex("com.example.Interface1"));
    assertEquals(instance.getVertex("type"), getVertex("com.example.Class2"));

    List<Edge> inherits = instance.getPath("inherits").getEdges();
    List<Edge> uses = instance.getPath("uses").getEdges();

    assertEquals(1, inherits.size());
    assertEquals(2, uses.size());

    // Iterator <Vertex> nodes = graph.getVertices();
    // while (nodes.hasNext()) {
    // Vertex n = nodes.next();
    // System.out.println(n.getId() + " " + n.getProperty("qname"));
    // }
    //
    // System.out.println("inherits");
    // for (Edge e:inherits) {
    // System.out.print(e.getProperty("name") + " - ");
    // System.out.println(e);
    // }
    //
    // System.out.println("uses");
    // for (Edge e:uses) {
    // System.out.print(e.getProperty("name") + " - ");
    // System.out.println(e);
    // }

    assertEquals(inherits.get(0), getEdge(R3));
    assertEquals(uses.get(0), getEdge(R1));
    assertEquals(uses.get(1), getEdge(R2));
  }

  @Test
  public void bm_testTypeEdge1() {
    Edge edge = getEdge(R1);
    assertEquals("uses", edge.getProperty("type"));
  }

  @Test
  public void bm_testTypeEdge2() {
    Edge edge = getEdge(R2);
    assertEquals("uses", edge.getProperty("type"));
  }

  @Test
  public void bm_testTypeEdge3() {
    Edge edge = getEdge(R3);
    assertEquals("implements", edge.getProperty("type"));
  }

  @Test
  public void bm_testTypeEdge4() {
    Edge edge = getEdge(R4);
    assertEquals("uses", edge.getProperty("type"));
  }

  // ---------- * ---------- * ---------- * ---------- * ---------- * ---------- * ----------

  private Vertex getVertex(String name) {
    Iterator<Vertex> iter = getBlueprintsAdapter().getVertices();
    while (iter.hasNext()) {
      Vertex next = iter.next();
      if (name.equals(next.getProperty("qname"))) {
        return next;
      }
    }
    return null;
  }

  private Edge getEdge(String name) {
    Iterator<Edge> iter = getBlueprintsAdapter().getEdges();
    while (iter.hasNext()) {
      Edge next = iter.next();
      if (name.equals(next.getProperty("name"))) {
        return next;
      }
    }
    return null;
  }

  private int count(Iterator<?> iter) {
    int c = 0;
    while (iter.hasNext()) {
      iter.next();
      c = c + 1;
    }
    return c;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private boolean contains(Iterator iter, Object... objs) {
    Map objects = new IdentityHashMap();
    for (Object o : objs) {
      objects.put(o, "");
    }
    while (iter.hasNext()) {
      objects.remove(iter.next());
    }
    return objects.isEmpty();
  }

  private int countJreClasses() {
    final List<ITypeArtifact> typeArtifacts = new LinkedList<ITypeArtifact>();

    getRootArtifact().accept(new IAnalysisModelVisitor.Adapter() {

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.bundlemaker.core.analysis.IAnalysisModelVisitor.Adapter#visit(org.bundlemaker.core.analysis.IModuleArtifact
       * )
       */
      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {
        return !"com.example".equals(moduleArtifact.getQualifiedName());
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.bundlemaker.core.analysis.IAnalysisModelVisitor.Adapter#visit(org.bundlemaker.core.analysis.ITypeArtifact)
       */
      @Override
      public boolean visit(ITypeArtifact typeArtifact) {
        typeArtifacts.add(typeArtifact);
        return true;
      }

    });

    return typeArtifacts.size();

  }

  @Override
  protected String computeTestProjectName() {
    return "com.example";
  }

}
