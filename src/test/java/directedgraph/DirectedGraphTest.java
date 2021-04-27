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
        List<String> expected = Arrays.asList("x1","x2","x3","x4","x5");
        Assert.assertEquals(expected,graph.getVertexes()); //testing .addVertex and .addArc (creating vertex)
        graph.addVertex("x1");
        Assert.assertEquals(expected,graph.getVertexes()); //testing .addVertex (adding existing vertex)
    }

    @Test
    public void deleteVertex() {
        List<String> expected = Arrays.asList("x1","x2","x4","x5");
        graph.deleteVertex("x3");
        Assert.assertEquals(expected,graph.getVertexes()); //testing .deleteVertex
        graph.deleteVertex("x0");
        Assert.assertEquals(expected,graph.getVertexes()); //testing .deleteVertex (deleting non-existing vertex)
    }

    @Test
    public void addArc() {
        List<String> expected = Arrays.asList("x1->x3\t4","x1->x4\t2","x5->x1\t1","x2->x4\t3","x4->x5\t5");
        Assert.assertEquals(expected,graph.getArcs()); //testing .addArc and .addVertex (creating non-existing vertex)
        graph.addArc("x1", "x3", 4);
        Assert.assertEquals(expected,graph.getArcs()); //testing .addArc (adding existing arc)
    }

    @Test
    public void deleteArc() {
        List<String> expected = Arrays.asList("x1->x3\t4","x5->x1\t1","x2->x4\t3","x4->x5\t5");
        graph.deleteArc("x1","x4");
        Assert.assertEquals(expected,graph.getArcs()); //testing .deleteArc
        graph.deleteArc("x0","x01");
        Assert.assertEquals(expected,graph.getArcs()); //testing .deleteArc (deleting non-existing arc)
    }

    @Test
    public void editVertexName() {
        List<String> expected = Arrays.asList("new_x1","x2","x3","x4","x5");
        graph.editVertexName("x1","new_x1");
        Assert.assertEquals(expected,graph.getVertexes()); //testing .editVertexName
        graph.editVertexName("x0","new_x0");
        Assert.assertEquals(expected,graph.getVertexes()); //testing .editVertexName (editing non-existing vertex)
    }

    @Test
    public void editArcWeight() {
        List<String> expected = Arrays.asList("x1->x4\t2","x5->x1\t1","x1->x3\t123","x2->x4\t3","x4->x5\t5");
        graph.editArcWeight("x1","x3",123);
        Assert.assertEquals(expected,graph.getArcs()); //testing .editArcWeight
        graph.editArcWeight("x0","x01",0);
        Assert.assertEquals(expected,graph.getArcs()); //testing .editArcWeight (editing non-existing arc)
    }

    @Test
    public void getEnteringArcs() {
        List<DirectedGraph.Arc> arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x5"), new DirectedGraph.Vertex("x1"), 1));
        Assert.assertEquals(arcs, graph.getEnteringArcs("x1")); //testing .getEnteringArcs
    }

    @Test
    public void getOutgoingArcs() {
        List<DirectedGraph.Arc> arcs = new ArrayList<>();
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x1"), new DirectedGraph.Vertex("x3"), 4));
        arcs.add(new DirectedGraph.Arc(new DirectedGraph.Vertex("x1"), new DirectedGraph.Vertex("x4"), 2));
        Assert.assertEquals(arcs, graph.getOutgoingArcs("x1")); //testing .getOutgoingArcs
    }
}