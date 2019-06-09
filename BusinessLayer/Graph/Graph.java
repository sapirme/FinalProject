package Graph;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Graph {

    private Set<Vertex> vertex;
    private Set<Edge> edges;

    public Graph (Set<Vertex> vertex, Set<Edge> edges){
        this.vertex = new HashSet<Vertex>(vertex);
        this.edges = new HashSet<Edge>(edges);
    }

    public Graph (Graph g){
        this.vertex = new HashSet<Vertex>();
        for (Vertex v : g.getVertex()){
            vertex.add(new Vertex(v));
        }
        this.edges = new HashSet<Edge>();
        for (Edge e : g.getEdges()){
            edges.add(new Edge(e));
        }
    }

    public Vertex isVertexExist(Vertex v) {
        for (Vertex v1 :  vertex) {
            if (v1.equals(v)) return v1;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (!(o instanceof Graph)) {
            return false;
        }
        Graph other = (Graph) o;
        if (this.vertex.equals(other.getVertex()) && this.edges.equals(other.getEdges()))
            return true;
        else return false;
    }

    public String toString() {
        return vertex.toString()+"\n"+edges.toString();
    }

    public void addVertex(Vertex v) {
        vertex.add(v);
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }

    public void removeEdge(Edge e) {
        edges.remove(e);
    }

    public Set<Vertex> getVertex(){
        return this.vertex;
    }

    public Set<Edge> getEdges(){
        return this.edges;
    }

    /**
     * finde all the edges that start from vertex v
     * @param v
     * @return
     */
    public Set<Edge> allEdegsFromV(Vertex v){
        Set<Edge> ans = new HashSet<Edge>();
        for(Edge e : edges){
            if(e.getFrom().equals(v)){
                ans.add(e);
            }
        }
        return ans;
    }

    /**
     * find all paths between 2 vertex in recursive function
     * @param from
     * @param to
     * @param visited
     * @param path
     * @param paths
     */
    private void allPathsRecurs(Vertex from, Vertex to, Set<Vertex> visited, List<Edge> path, Set<List<Edge>> paths){
        Set<Edge> allEdegsFrom = allEdegsFromV(from);
        visited.add(from);
        List<Edge> localPath = new LinkedList<Edge>();
        for (Edge e : path)
            localPath.add(e);
        if(from.equals(to)){
            paths.add(localPath);
            visited.remove(from);
            return;
        }
        for(Edge e : allEdegsFrom){
            if(!visited.contains(e.getTo())){
                localPath.add(e);
                allPathsRecurs(e.getTo(),to,visited,localPath,paths);
                localPath.remove(e);
            }
        }
        visited.remove(from);
    }

    /**
     * find all paths between 2 vertex
     * @param from
     * @param to
     * @return
     */
    public Set<List<Edge>> allPaths(Vertex from, Vertex to){
        Set<List<Edge>> paths = new HashSet<List<Edge>>();
        List<Edge> path = new LinkedList<Edge>();
        Set<Vertex> visited = new HashSet<Vertex>();
        allPathsRecurs(from,to,visited,path,paths);
        return paths;
    }

    public void removeEdges(List<Edge> path) {
        edges.removeAll(path);
    }

    public void addEdges(List<Edge> path) {
        edges.addAll(path);
    }
}
