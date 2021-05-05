package directedgraph;

import java.util.*;

public class DirectedGraph {

    final Map<Vertex, List<Arc>> graph = new HashMap<>();

    public static class Vertex {
        String name;

        public Vertex(String value) {
            this.name = value;
        }

        @Override
        public String toString(){
            return name;
        }

        @Override
        public boolean equals(Object o){
            if (!(o instanceof Vertex)) return false;
            return ((Vertex) o).name.equals(this.name) || o == this;
        }

        @Override
        public int hashCode(){
            return this.name.hashCode();
        }
    }

    public static class Arc {
        final Vertex from;
        final Vertex to;
        final int weight;

        public Arc(Vertex from, Vertex to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString(){
            return from + "->" + to + "\t" + weight;
        }

        @Override
        public boolean equals(Object o){
            if (!(o instanceof Arc)) return false;
            return (((Arc) o).from.equals(this.from) && ((Arc) o).to.equals(this.to)) || o == this;
        }

        @Override
        public int hashCode(){
            return this.from.hashCode()+this.to.hashCode();
        }
    }
    Map<String,Vertex> vertexCache = new HashMap<>();
    private Vertex mkVertex(String name){
        Vertex vertex = new Vertex(name);
        if (!hasVertex(vertex.name))
            vertexCache.put(name, vertex);
        return vertex;
    }

    Map<String,Arc> arcCache = new HashMap<>();
    private Arc mkArc(String from, String to, int weight){
        Arc arc = new Arc(mkVertex(from), mkVertex(to), weight);
        if (!hasArc(arc.from.name,arc.to.name))
            arcCache.put(from + "->" + to, arc);
        return arc;
    }

    public void addVertex(String name) {
        if (!hasVertex(name)) {
            List<Arc> arcs = new ArrayList<>();
            this.graph.put(mkVertex(name), arcs);
        }
    }

    public void  addVertex(Vertex vertex) {
        addVertex(vertex.name);
    }

    private boolean hasVertex(String name) {
        return vertexCache.containsKey(name);
    }

    private boolean hasArc(String from, String to) {
        return arcCache.containsKey(from + "->" + to);
    }

    public void addArc(String from, String to, int weight) {
        if (!hasVertex(from)) addVertex(from);
        if (!hasVertex(to)) addVertex(to);
        if (!hasArc(from, to)) {
            List<Arc> arcsF = graph.get(mkVertex(from));
            List<Arc> arcsT = graph.get(mkVertex(to));
            arcsF.add(mkArc(from, to, weight));
            arcsT.add(mkArc(from, to, weight));
            this.graph.put(mkVertex(from), arcsF);
            this.graph.put(mkVertex(to), arcsT);
        }
    }

    public void addArc(Arc arc) {
        addArc(arc.from.name,arc.to.name,arc.weight);
    }

    public void deleteArc(String from, String to) {
        if (hasArc(from, to)) {
            List<Arc> arcsF = graph.get(mkVertex(from));
            List<Arc> arcsT = graph.get(mkVertex(to));
            arcsF.remove(mkArc(from, to,0));
            arcsT.remove(mkArc(from, to,0));
            this.graph.put(mkVertex(from), arcsF);
            this.graph.put(mkVertex(to), arcsT);
            arcCache.remove(from + "->" + to);
        }
    }

    public void  deleteArc(Arc arc) {
        deleteArc(arc.from.name, arc.to.name);
    }

    public void deleteVertex(String name) {
        if (hasVertex(name)) {
            List<Arc> arcs = new ArrayList<>(graph.get(mkVertex(name)));
            for (Arc arc : arcs) {
                deleteArc(arc.from.toString(), arc.to.toString());
            }
            graph.remove(mkVertex(name));
            vertexCache.remove(name);
        }
    }

    public void  deleteVertex(Vertex vertex) {
        deleteVertex(vertex.name);
    }

    public void editVertexName(String name, String newName) {
        if (hasVertex(name)) {
            List<Arc> arcs = graph.get(mkVertex(name));
            this.graph.put(mkVertex(newName), arcs);
            arcs = getOutgoingArcs(name);
            for (Arc arc : arcs) {
                editArcName(name, arc.to.toString(), newName, arc.to.toString());
            }
            arcs = getEnteringArcs(name);
            for (Arc arc : arcs) {
                editArcName(arc.from.toString(), name, arc.from.toString(), newName);
            }
            graph.remove(vertexCache.get(name));
        }
    }

    public void editVertexName(Vertex vertex, Vertex newVertex) {
        editVertexName(vertex.name, newVertex.name);
    }

    private void editArcName(String from, String to, String new_from, String new_to) {
        for (Arc arc : graph.get(mkVertex(from))) {
            if (arc.from.equals(mkVertex(from)) && arc.to.equals(mkVertex(to))) {
                deleteArc(from, to);
                addArc(new_from, new_to, arc.weight);
                break;
            }
        }
    }

    public void editArcWeight(String from, String to, int newWeight) {
        if (hasArc(from, to)) {
            deleteArc(from, to);
            addArc(from, to, newWeight);
        }
    }

    public void editArcWeight(Arc arc, int newWeight) {
        editArcWeight(arc.from.name, arc.to.name, newWeight);
    }

    public List<Arc> getEnteringArcs(String name) {
        if (hasVertex(name)) {
            List<Arc> arcs = new ArrayList<>();
            for (Arc arc : graph.get(mkVertex(name))) {
                if (arc.to.equals(mkVertex(name)))
                    arcs.add(arc);
            }
            return arcs;
        } else return new ArrayList<>();
    }

    public List<Arc> getEnteringArcs(Vertex vertex) {
        return getEnteringArcs(vertex.name);
    }

    public List<Arc> getOutgoingArcs(String name) {
        if (hasVertex(name)) {
            List<Arc> arcs = new ArrayList<>();
            for (Arc arc : graph.get(mkVertex(name))) {
                if (arc.from.equals(mkVertex(name)))
                    arcs.add(arc);
            }
            return arcs;
        } else return new ArrayList<>();
    }

    public List<Arc> getOutgoingArcs(Vertex vertex) {
        return getOutgoingArcs(vertex.name);
    }

    public List<String> getVertexes(){
        List<String> res = new ArrayList<>();
        for(Map.Entry<Vertex, List<Arc>> item : graph.entrySet()) {
            res.add(item.getKey().toString());
        }
        return res;
    }

    public List<String> getArcs(){
        List<String> res = new ArrayList<>();
        for(Map.Entry<Vertex, List<Arc>> item : graph.entrySet()) {
            for (Arc arc : item.getValue()){
                if (!res.contains(arc.toString())){
                    res.add(arc.toString());
                }
            }
        }
        return res;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(Map.Entry<Vertex, List<Arc>> item : graph.entrySet()) {
            s.append("Vertex: ").append(item.getKey()).append("\t\tArcs: ").append(item.getValue()).append("\n");
        }
        return s.toString();
    }
}
