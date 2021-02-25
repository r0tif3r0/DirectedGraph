package directedgraph;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class DirectedGraphTest {

    DirectedGraph graph = new DirectedGraph();

    @Before
    public void setUp() {
        graph = new DirectedGraph();
        graph.addVertex("x1");
        graph.addVertex("x2");
        graph.addVertex("x3");
        graph.addArc("x4", "x5", 5);
        graph.addArc("x1", "x3", 4);
        graph.addArc("x2", "x4", 3);
        graph.addArc("x1", "x4", 2);
        graph.addArc("x5", "x1", 1);
    }

    @Test
    public void addVertex() {
        Map<DirectedGraph.Vertex, List<DirectedGraph.Arc>> expected = new HashMap<>();
        expected.put(new DirectedGraph.Vertex("x1"),new ArrayList<>());
        expected.put(new DirectedGraph.Vertex("x2"),new ArrayList<>());
        expected.put(new DirectedGraph.Vertex("x3"),new ArrayList<>());
        expected.put(new DirectedGraph.Vertex("x4"),new ArrayList<>());
        expected.put(new DirectedGraph.Vertex("x5"),new ArrayList<>());
        Assert.assertEquals(expected.keySet(),graph.getMap().keySet());
    }

    @Test
    public void addArc() {
        Map<DirectedGraph.Vertex, List<DirectedGraph.Arc>> expected = new HashMap<>();
        List<DirectedGraph.Arc> arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x1"), new DirectedGraph.Vertex("x3"), 4));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x1"), new DirectedGraph.Vertex("x4"), 2));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x5"), new DirectedGraph.Vertex("x1"), 1));
        expected.put(new DirectedGraph.Vertex("x1"),arcs);
        arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x2"), new DirectedGraph.Vertex("x4"), 3));
        expected.put(new DirectedGraph.Vertex("x2"),arcs);
        arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x1"), new DirectedGraph.Vertex("x3"), 4));
        expected.put(new DirectedGraph.Vertex("x3"),arcs);
        arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x4"), new DirectedGraph.Vertex("x5"), 5));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x2"), new DirectedGraph.Vertex("x4"), 3));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x1"), new DirectedGraph.Vertex("x4"), 2));
        expected.put(new DirectedGraph.Vertex("x4"),arcs);
        arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x4"), new DirectedGraph.Vertex("x5"), 5));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x5"), new DirectedGraph.Vertex("x1"), 1));
        expected.put(new DirectedGraph.Vertex("x5"),arcs);
        Assert.assertEquals(expected,graph.getMap());
    }

    @Test
    public void deleteVertex() {
        graph.deleteVertex("x0");
        graph.deleteVertex("x1");
        graph.deleteVertex("x4");
        Map<DirectedGraph.Vertex, List<DirectedGraph.Arc>> expected = new HashMap<>();
        expected.put(new DirectedGraph.Vertex("x2"),new ArrayList<>());
        expected.put(new DirectedGraph.Vertex("x3"),new ArrayList<>());
        expected.put(new DirectedGraph.Vertex("x5"),new ArrayList<>());
        Assert.assertEquals(expected,graph.getMap());
    }

    @Test
    public void deleteArc() {
        graph.deleteArc("x1", "x4");
        graph.deleteArc("x5", "x1");
        graph.deleteArc("x0","x01");
        Map<DirectedGraph.Vertex, List<DirectedGraph.Arc>> expected = new HashMap<>();
        List<DirectedGraph.Arc> arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x1"), new DirectedGraph.Vertex("x3"), 4));
        expected.put(new DirectedGraph.Vertex("x1"),arcs);
        arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x2"), new DirectedGraph.Vertex("x4"), 3));
        expected.put(new DirectedGraph.Vertex("x2"),arcs);
        arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x1"), new DirectedGraph.Vertex("x3"), 4));
        expected.put(new DirectedGraph.Vertex("x3"),arcs);
        arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x4"), new DirectedGraph.Vertex("x5"), 5));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x2"), new DirectedGraph.Vertex("x4"), 3));
        expected.put(new DirectedGraph.Vertex("x4"),arcs);
        arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x4"), new DirectedGraph.Vertex("x5"), 5));
        expected.put(new DirectedGraph.Vertex("x5"),arcs);
        Assert.assertEquals(expected,graph.getMap());
    }

    @Test
    public void editVertexName() {
        graph.editVertexName("x1", "new_x1");
        Map<DirectedGraph.Vertex, List<DirectedGraph.Arc>> expected = new HashMap<>();
        List<DirectedGraph.Arc> arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("new_x1"), new DirectedGraph.Vertex("x3"), 4));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("new_x1"), new DirectedGraph.Vertex("x4"), 2));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x5"), new DirectedGraph.Vertex("new_x1"), 1));
        expected.put(new DirectedGraph.Vertex("new_x1"),arcs);
        arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x2"), new DirectedGraph.Vertex("x4"), 3));
        expected.put(new DirectedGraph.Vertex("x2"),arcs);
        arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("new_x1"), new DirectedGraph.Vertex("x3"), 4));
        expected.put(new DirectedGraph.Vertex("x3"),arcs);
        arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x4"), new DirectedGraph.Vertex("x5"), 5));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x2"), new DirectedGraph.Vertex("x4"), 3));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("new_x1"), new DirectedGraph.Vertex("x4"), 2));
        expected.put(new DirectedGraph.Vertex("x4"),arcs);
        arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x4"), new DirectedGraph.Vertex("x5"), 5));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x5"), new DirectedGraph.Vertex("new_x1"), 1));
        expected.put(new DirectedGraph.Vertex("x5"),arcs);
        Assert.assertEquals(expected,graph.getMap());
    }

    @Test
    public void editArcWeight() {
        graph.editArcWeight("x1", "x3", 44);
        graph.editArcWeight("x4","x5", 55);
        Map<DirectedGraph.Vertex, List<DirectedGraph.Arc>> expected = new HashMap<>();
        List<DirectedGraph.Arc> arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x1"), new DirectedGraph.Vertex("x4"), 2));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x5"), new DirectedGraph.Vertex("x1"), 1));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x1"), new DirectedGraph.Vertex("x3"), 44));
        expected.put(new DirectedGraph.Vertex("x1"),arcs);
        arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x2"), new DirectedGraph.Vertex("x4"), 3));
        expected.put(new DirectedGraph.Vertex("x2"),arcs);
        arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x1"), new DirectedGraph.Vertex("x3"), 44));
        expected.put(new DirectedGraph.Vertex("x3"),arcs);
        arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x2"), new DirectedGraph.Vertex("x4"), 3));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x1"), new DirectedGraph.Vertex("x4"), 2));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x4"), new DirectedGraph.Vertex("x5"), 55));
        expected.put(new DirectedGraph.Vertex("x4"),arcs);
        arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x5"), new DirectedGraph.Vertex("x1"), 1));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x4"), new DirectedGraph.Vertex("x5"), 55));
        expected.put(new DirectedGraph.Vertex("x5"),arcs);
        Assert.assertEquals(expected,graph.getMap());
    }

    @Test
    public void getEnteringArcs() {
        List<DirectedGraph.Arc> arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x5"), new DirectedGraph.Vertex("x1"), 1));
        Assert.assertEquals(arcs, graph.getEnteringArcs("x1"));
    }

    @Test
    public void getOutgoingArcs() {
        List<DirectedGraph.Arc> arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x1"), new DirectedGraph.Vertex("x3"), 4));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x1"), new DirectedGraph.Vertex("x4"), 2));
        Assert.assertEquals(arcs, graph.getOutgoingArcs("x1"));
    }
}