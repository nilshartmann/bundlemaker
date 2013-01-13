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
import nz.ac.massey.cs.guery.adapters.blueprints.BlueprintsAdapter;
import nz.ac.massey.cs.guery.adapters.blueprints.ElementCache;
import nz.ac.massey.cs.guery.adapters.blueprints.WrappingCache;

import org.bundlemaker.analysis.tinkerpop.impl.BundleMakerBlueprintsGraph;
import org.bundlemaker.core.analysis.DependencyKind;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assume;
import org.junit.Before;
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

  private BlueprintsAdapter          graph   = null;

  private BundleMakerBlueprintsGraph bmGraph = null;

  private ElementCache               cache   = null;

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
    } } /*
         * , { new CacheFactory() {
         * 
         * @Override public ElementCache createCache() { return new AlwaysCheckCache(); } } }
         */};
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

    //
    bmGraph = new BundleMakerBlueprintsGraph(getRootArtifact());

    graph = new BlueprintsAdapter(bmGraph, cache);
  }

  private void assertCorrectTestModel() {
    assertPackageExists("com.example");
    ITypeArtifact cl1 = getType("com.example.Class1");
    ITypeArtifact cl2 = getType("com.example.Class2");
    ITypeArtifact if1 = getType("com.example.Interface1");

    // r1
    IDependency r1 = getDependency(if1, cl1, DependencyKind.USES);
    r1.setProperty("name", "r1");

    // r2
    IDependency r2 = getDependency(cl1, cl2, DependencyKind.USES);
    r2.setProperty("name", "r2");

    // r3
    IDependency r3 = getDependency(cl2, if1, DependencyKind.IMPLEMENTS);
    r3.setProperty("name", "r3");

    // r4
    IDependency r4 = getDependency(cl2, cl1, DependencyKind.USES);
    r4.setProperty("name", "r4");
  }

  @Test
  public void testVertexId() {
    Vertex v = getVertex("com.example.Class1");
    assertNotNull(v.getId());
  }

  @Test
  public void testVertexIdsInBaseGraph() {
    for (Vertex v : bmGraph.getVertices()) {
      assertNotNull(v.getId());
    }
  }

  @Test
  public void testEdgeIdsInBaseGraph() {
    for (Edge e : bmGraph.getEdges()) {
      assertNotNull(e.getId());
    }
  }

  @Test
  public void testEdgeId() {
    Edge e = getEdge("r1");
    assertNotNull(e.getId());
  }

  // ---------
  // note that virtual start node n[0] is also counted
  @Test
  public void testVertexCount() {
    assertEquals(4, graph.getVertexCount());
  }

  @Test
  public void testSizeOfVertices() {
    assertEquals(4, count(graph.getVertices()));
  }

  @Test
  public void testEdgeCount() {
    assertEquals(4, graph.getEdgeCount());
  }

  @Test
  public void testSizeOfEdges() {
    assertEquals(4, count(graph.getEdges()));
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
    assertNotNull(getEdge("r1"));
  }

  @Test
  public void testEdge2() {
    assertNotNull(getEdge("r2"));
  }

  @Test
  public void testEdge3() {
    assertNotNull(getEdge("r3"));
  }

  @Test
  public void testEdge4() {
    assertNotNull(getEdge("r4"));
  }

  @Test
  public void testReferentialIntegrity1() {

    Assume.assumeTrue(cache.ensuresReferentialIntegrity());

    Vertex cl1 = getVertex("com.example.Class1");
    Vertex cl2 = getVertex("com.example.Class2");
    Edge r2 = getEdge("r2");
    assertNotNull(r2);

    // System.out.println(r2);
    // System.out.println(graph.getStart(r2));
    // System.out.println(graph.getEnd(r2));

    assertEquals(cl1, graph.getStart(r2));
    assertEquals(cl2, graph.getEnd(r2));

    // the cache must also ensure identity!!
    // this will fail when using the NullCache and an underlying db like neo4j that
    // does not support referential integrity
    assertTrue(cl1 == graph.getStart(r2));
    assertTrue(cl2 == graph.getEnd(r2));
  }

  @Test
  public void testInEdges1() {
    Vertex cl1 = getVertex("com.example.Class1");
    Iterator<Edge> in = graph.getInEdges(cl1);
    assertEquals(2, count(in));
  }

  @Test
  public void testInEdges2() {
    Vertex if1 = getVertex("com.example.Interface1");
    Iterator<Edge> in = graph.getInEdges(if1);
    assertEquals(1, count(in));
  }

  @Test
  public void testInEdges3() {
    // check containment using ==
    Assume.assumeTrue(cache.ensuresReferentialIntegrity());

    Vertex cl1 = getVertex("com.example.Class1");
    Iterator<Edge> in = graph.getInEdges(cl1);
    Edge r1 = getEdge("r1");
    Edge r4 = getEdge("r4");
    assertTrue(contains(in, r1, r4));
  }

  @Test
  public void testInEdges4() {
    // check containment using ==
    Assume.assumeTrue(cache.ensuresReferentialIntegrity());

    Vertex if1 = getVertex("com.example.Interface1");
    Iterator<Edge> in = graph.getInEdges(if1);
    Edge r3 = getEdge("r3");
    assertTrue(contains(in, r3));
  }

  @Test
  public void testOutEdges1() {
    Vertex cl2 = getVertex("com.example.Class2");
    Iterator<Edge> out = graph.getOutEdges(cl2);
    assertEquals(2, count(out));
  }

  @Test
  public void testOutEdges2() {
    Vertex if1 = getVertex("com.example.Interface1");
    Iterator<Edge> out = graph.getInEdges(if1);
    assertEquals(1, count(out));
  }

  @Test
  public void testOutEdges3() {
    // check containment using ==
    Assume.assumeTrue(cache.ensuresReferentialIntegrity());

    Vertex cl2 = getVertex("com.example.Class2");
    Iterator<Edge> out = graph.getOutEdges(cl2);
    Edge r3 = getEdge("r3");
    Edge r4 = getEdge("r4");
    assertTrue(contains(out, r3, r4));
  }

  @Test
  public void testOutEdges4() {
    // check containment using ==
    Assume.assumeTrue(cache.ensuresReferentialIntegrity());

    Vertex if1 = getVertex("com.example.Interface1");
    Iterator<Edge> out = graph.getOutEdges(if1);
    Edge r1 = getEdge("r1");
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
    assertTrue(getEdge("r1") instanceof WrappingCache.GEdge);
    assertTrue(getEdge("r2") instanceof WrappingCache.GEdge);
    assertTrue(getEdge("r3") instanceof WrappingCache.GEdge);
    assertTrue(getEdge("r4") instanceof WrappingCache.GEdge);
  }

  @Test
  public void testVertexTypes2() {
    Assume.assumeTrue(cache instanceof WrappingCache);
    assertTrue(graph.getStart(getEdge("r1")) instanceof WrappingCache.GVertex);
    assertTrue(graph.getEnd(getEdge("r1")) instanceof WrappingCache.GVertex);
    assertTrue(graph.getStart(getEdge("r2")) instanceof WrappingCache.GVertex);
    assertTrue(graph.getEnd(getEdge("r2")) instanceof WrappingCache.GVertex);
    assertTrue(graph.getStart(getEdge("r3")) instanceof WrappingCache.GVertex);
    assertTrue(graph.getEnd(getEdge("r3")) instanceof WrappingCache.GVertex);
    assertTrue(graph.getStart(getEdge("r4")) instanceof WrappingCache.GVertex);
    assertTrue(graph.getEnd(getEdge("r4")) instanceof WrappingCache.GVertex);
  }

  @Test
  public void testEdgeTypes2() {
    Assume.assumeTrue(cache instanceof WrappingCache);
    Vertex v = getVertex("com.example.Interface1");
    Iterator<Edge> in = graph.getInEdges(v);
    while (in.hasNext()) {
      assertTrue(in.next() instanceof WrappingCache.GEdge);
    }
    Iterator<Edge> out = graph.getInEdges(v);
    while (out.hasNext()) {
      assertTrue(out.next() instanceof WrappingCache.GEdge);
    }

    v = getVertex("com.example.Class1");
    in = graph.getInEdges(v);
    while (in.hasNext()) {
      assertTrue(in.next() instanceof WrappingCache.GEdge);
    }
    out = graph.getInEdges(v);
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

    List<MotifInstance<Vertex, Edge>> results = Utilities.query(graph, STK);
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

    assertEquals(inherits.get(0), getEdge("r3"));
    assertEquals(uses.get(0), getEdge("r1"));
    assertEquals(uses.get(1), getEdge("r2"));
  }

  @Test
  public void bm_testTypeEdge1() {
    Edge edge = getEdge("r1");
    assertEquals("uses", edge.getProperty("type"));
  }

  @Test
  public void bm_testTypeEdge2() {
    Edge edge = getEdge("r2");
    assertEquals("uses", edge.getProperty("type"));
  }

  @Test
  public void bm_testTypeEdge3() {
    Edge edge = getEdge("r3");
    assertEquals("implements", edge.getProperty("type"));
  }

  @Test
  public void bm_testTypeEdge4() {
    Edge edge = getEdge("r4");
    assertEquals("uses", edge.getProperty("type"));
  }

  // ---------- * ---------- * ---------- * ---------- * ---------- * ---------- * ----------
  /**
   * @param if1
   * @param cl1
   * @param string
   */
  private IDependency getDependency(ITypeArtifact from, ITypeArtifact to, DependencyKind expectedKind) {

    IDependency dependency = from.getDependencyTo(to);
    assertNotNull(dependency);
    assertEquals(expectedKind, dependency.getDependencyKind());

    return dependency;

  }

  /**
   * @param string
   * @param string2
   * @param string3
   * @return
   */
  private ITypeArtifact getType(final String expectedTypeName) {
    final List<ITypeArtifact> typeArtifactsFound = new LinkedList<ITypeArtifact>();

    getRootArtifact().accept(new IAnalysisModelVisitor.Adapter() {

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.bundlemaker.core.analysis.IAnalysisModelVisitor.Adapter#visit(org.bundlemaker.core.analysis.ITypeArtifact)
       */
      @Override
      public boolean visit(ITypeArtifact typeArtifact) {

        String qualifiedTypeName = typeArtifact.getParent(IPackageArtifact.class).getQualifiedName() + "."
            + typeArtifact.getName();

        if (qualifiedTypeName.equals(expectedTypeName)) {
          typeArtifactsFound.add(typeArtifact);
        }
        return false;
      }

    });

    assertEquals(1, typeArtifactsFound.size());

    return typeArtifactsFound.get(0);
  }

  /**
   * @param string
   */
  private void assertPackageExists(final String packageName) {

    final List<IPackageArtifact> packageArtifactsFound = new LinkedList<IPackageArtifact>();

    getRootArtifact().accept(new IAnalysisModelVisitor.Adapter() {

      @Override
      public boolean visit(IPackageArtifact packageArtifact) {
        System.out.println("package: " + packageArtifact.getQualifiedName());
        if (packageArtifact.getQualifiedName().equals(packageName)) {
          packageArtifactsFound.add(packageArtifact);
        }
        return false;
      }

    });

    assertEquals(1, packageArtifactsFound.size());

  }

  private Vertex getVertex(String name) {
    Iterator<Vertex> iter = graph.getVertices();
    while (iter.hasNext()) {
      Vertex next = iter.next();
      if (name.equals(next.getProperty("qname"))) {
        return next;
      }
    }
    return null;
  }

  private Edge getEdge(String name) {
    Iterator<Edge> iter = graph.getEdges();
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

}
