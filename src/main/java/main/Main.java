package main;

import directedgraph.DirectedGraph;

public class Main {
    public static void main(String[] args){
        DirectedGraph first = new DirectedGraph();
        first.addVertex("x1");
        first.addVertex("x2");
        first.addVertex("x3");
        first.addArc("x4", "x5", 5);
        first.addArc("x1", "x3", 4);
        first.addArc("x2", "x4", 3);
        first.addArc("x1", "x4", 2);
        first.addArc("x5", "x1", 1);
        first.info();
        System.out.println();
        first.deleteArc("x1", "x4");
        first.deleteArc("x5", "x1");
        first.info();
        System.out.println();
        first.deleteVertex("x1");
        first.deleteVertex("x4");
        first.info();
        System.out.println();
        first.deleteArc("x2", "x4");
        first.info();
        System.out.println();
        first.deleteVertex("x4");
        first.info();
        System.out.println();
        first.editVertexName("x1", "new_x1");
        first.editArcWeight("new_x1", "x3", 33);
        first.info();
        System.out.println();
        System.out.println(first.getOutgoingArcs("new_x1"));
        System.out.println(first.getEnteringArcs("new_x1"));
    }
}
