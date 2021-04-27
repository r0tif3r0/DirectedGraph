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
            return ((Vertex) o).name.equals(this.name);
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
            return (((Arc) o).from.equals(this.from) && ((Arc) o).to.equals(this.to));
        }

        @Override
        public int hashCode(){
            return this.from.hashCode()+this.to.hashCode();
        }
    }
    Map<String,Vertex> vertexCache = new HashMap<>();
    private Vertex mkVertex(String name){
        Vertex vertex = new Vertex(name);
        vertexCache.put(name, vertex);
        return vertex;
    }

    Map<String,Arc> arcCache = new HashMap<>();
    private Arc mkArc(String from, String to, int weight){
        Arc arc = new Arc(vertexCache.get(from), vertexCache.get(to), weight);
        arcCache.put(from + "->" + to, arc);
        return arc;
    }

    public void addVertex(String name) {
        if (!hasVertex(name)) {
            List<Arc> arcs = new ArrayList<>();
            this.graph.put(mkVertex(name), arcs);
        }
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
            List<Arc> arcsF = graph.get(vertexCache.get(from));
            List<Arc> arcsT = graph.get(vertexCache.get(to));
            arcsF.add(mkArc(from, to, weight));
            arcsT.add(mkArc(from, to, weight));
            this.graph.put(vertexCache.get(from), arcsF);
            this.graph.put(vertexCache.get(to), arcsT);
        }
    }

    public void deleteArc(String from, String to) {
        if (hasArc(from, to)) {
            List<Arc> arcsF = graph.get(vertexCache.get(from));
            List<Arc> arcsT = graph.get(vertexCache.get(to));
            arcsF.remove(arcCache.get(from + "->" + to));
            arcsT.remove(arcCache.get(from + "->" + to));
            this.graph.put(vertexCache.get(from), arcsF);
            this.graph.put(vertexCache.get(to), arcsT);
            arcCache.remove(from + "->" + to);
        }
    }

    public void deleteVertex(String name) {
        if (hasVertex(name)) {
            List<Arc> arcs = new ArrayList<>(graph.get(vertexCache.get(name)));
            for (Arc arc : arcs) {
                deleteArc(arc.from.toString(), arc.to.toString());
            }
            graph.remove(vertexCache.get(name));
            vertexCache.remove(name);
        }
    }

    public void editVertexName(String name, String new_name) {
        if (hasVertex(name)) {
            List<Arc> arcs = graph.get(vertexCache.get(name));
            this.graph.put(mkVertex(new_name), arcs);
            arcs = getOutgoingArcs(name);
            for (Arc arc : arcs) {
                editArcName(name, arc.to.toString(), new_name, arc.to.toString());
            }
            arcs = getEnteringArcs(name);
            for (Arc arc : arcs) {
                editArcName(arc.from.toString(), name, arc.from.toString(), new_name);
            }
            graph.remove(vertexCache.get(name));
        }
    }

    private void editArcName(String from, String to, String new_from, String new_to) {
        for (Arc arc : graph.get(vertexCache.get(from))) {
            if (arc.from.equals(vertexCache.get(from)) && arc.to.equals(vertexCache.get(to))) {
                deleteArc(from, to);
                addArc(new_from, new_to, arc.weight);
                break;
            }
        }
    }

    public void editArcWeight(String from, String to, int new_weight) {
        if (hasArc(from, to)) {
            deleteArc(from, to);
            addArc(from, to, new_weight);
        }
    }

    public List<Arc> getEnteringArcs(String name) {
        if (hasVertex(name)) {
            List<Arc> arcs = new ArrayList<>();
            for (Arc arc : graph.get(vertexCache.get(name))) {
                if (arc.to.equals(vertexCache.get(name)))
                    arcs.add(arc);
            }
            return arcs;
        } else return new ArrayList<>();
    }

    public List<Arc> getOutgoingArcs(String name) {
        if (hasVertex(name)) {
            List<Arc> arcs = new ArrayList<>();
            for (Arc arc : graph.get(vertexCache.get(name))) {
                if (arc.from.equals(vertexCache.get(name)))
                    arcs.add(arc);
            }
            return arcs;
        } else return new ArrayList<>();
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
