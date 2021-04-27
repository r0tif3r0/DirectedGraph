package directedgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void addVertex(String name) {
        if (!hasVertex(name)) {
            List<Arc> arcs = new ArrayList<>();
            this.graph.put(new Vertex(name), arcs);
        }
    }

    private boolean hasVertex(String name) {
        return graph.containsKey(new Vertex(name));
    }

    private boolean hasArc(String from, String to) {
        if (hasVertex(from) && hasVertex(to)) {
            for (Arc arc : graph.get(new Vertex(from))) {
                if (arc.from.equals(new Vertex(from)) && arc.to.equals(new Vertex(to))) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addArc(String from, String to, int weight) {
        if (!hasVertex(from)) addVertex(from);
        if (!hasVertex(to)) addVertex(to);
        if (!hasArc(from, to)) {
            List<Arc> arcsF = graph.get(new Vertex(from));
            List<Arc> arcsT = graph.get(new Vertex(to));
            arcsF.add(new Arc(new Vertex(from), new Vertex(to), weight));
            arcsT.add(new Arc(new Vertex(from), new Vertex(to), weight));
            this.graph.put(new Vertex(from), arcsF);
            this.graph.put(new Vertex(to), arcsT);
        }
    }

    public void deleteArc(String from, String to) {
        if (hasArc(from, to)) {
            List<Arc> arcsF = graph.get(new Vertex(from));
            List<Arc> arcsT = graph.get(new Vertex(to));
            arcsF.remove(new Arc(new Vertex(from), new Vertex(to), 0));
            arcsT.remove(new Arc(new Vertex(from), new Vertex(to), 0));
            this.graph.put(new Vertex(from), arcsF);
            this.graph.put(new Vertex(to), arcsT);
        }
    }

    public void deleteVertex(String name) {
        if (hasVertex(name)) {
            List<Arc> arcs = new ArrayList<>(graph.get(new Vertex(name)));
            for (Arc arc : arcs) {
                deleteArc(arc.from.toString(), arc.to.toString());
            }
            graph.remove(new Vertex(name));
        }
    }

    public void editVertexName(String name, String new_name) {
        if (hasVertex(name)) {
            List<Arc> arcs = graph.get(new Vertex(name));
            this.graph.put(new Vertex(new_name), arcs);
            arcs = getOutgoingArcs(name);
            for (Arc arc : arcs) {
                editArcName(name, arc.to.toString(), new_name, arc.to.toString());
            }
            arcs = getEnteringArcs(name);
            for (Arc arc : arcs) {
                editArcName(arc.from.toString(), name, arc.from.toString(), new_name);
            }
            graph.remove(new Vertex(name));
        }
    }

    private void editArcName(String from, String to, String new_from, String new_to) {
        for (Arc arc : graph.get(new Vertex(from))) {
            if (arc.from.equals(new Vertex(from)) && arc.to.equals(new Vertex(to))) {
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
            for (Arc arc : graph.get(new Vertex(name))) {
                if (arc.to.equals(new Vertex(name)))
                    arcs.add(arc);
            }
            return arcs;
        } else return new ArrayList<>();
    }

    public List<Arc> getOutgoingArcs(String name) {
        if (hasVertex(name)) {
            List<Arc> arcs = new ArrayList<>();
            for (Arc arc : graph.get(new Vertex(name))) {
                if (arc.from.equals(new Vertex(name)))
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
