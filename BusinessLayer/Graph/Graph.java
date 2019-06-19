package Graph;


import java.util.*;

public class Graph {

    /*private Set<Vertex> vertexSet;
    private Set<Edge> edgesSet;*/
    private List<Vertex> vertex;
    private List<Edge> edges;

    public Graph (Set<Vertex> vertex, Set<Edge> edges){
        this.vertex = sortedVer(vertex);//(vertex);
        this.edges = getEdgesSorted(edges);//new HashSet<Edge>(edges);
    }

    public Graph (Graph g){
        this.vertex=new LinkedList<Vertex>(g.getVertex());
        this.edges=new LinkedList<Edge>(g.getEdges());
        /*this.vertex = new HashSet<Vertex>();
        for (Vertex v : g.getVertex()){
            vertex.add(new Vertex(v));
        }
        this.edges = new HashSet<Edge>();
        for (Edge e : g.getEdges()){
            edges.add(new Edge(e));
        }*/
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
        if (!vertex.contains(v)) {
            int index = 0;
            boolean insert = false;
            for (Vertex v1: vertex){
                if (v1.compareTo(v)>0){
                    vertex.add(index,v);
                    insert = true;
                    break;
                }
                index++;
            }
            if (!insert)
                vertex.add(index,v);
        }
    }

    public void addEdge(Edge e) {
        if (!edges.contains(e)) {
            int index = 0;
            boolean insert = false;
            for (Edge e1: edges){
                if (e1.compareTo(e)>0){
                    edges.add(index,e);
                    insert = true;
                    break;
                }
                index++;
            }
            if (!insert)
                edges.add(index,e);
        }
    }

    public void removeEdge(Edge e) {
        edges.remove(e);
    }

    /*public Set<Vertex> getVertex(){
        return this.vertex;
    }*/
    public List<Vertex> getVertex(){
        return this.vertex;
    }


    private void bubbleSort(Vertex[] array) {
        boolean swapped = true;
        int j = 0;
        Vertex tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < array.length - j; i++) {
                if (array[i].compareTo(array[i + 1])>0) {
                    tmp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = tmp;
                    swapped = true;
                }
            }
        }
    }

    public List<Vertex> sortedVer(Set<Vertex> vertex){
        int i=0;
        Vertex[] ver = new Vertex[vertex.size()];
        for (Vertex v : vertex){
            ver[i] = v;
            i++;
        }
        bubbleSort(ver);
        //List<Vertex> list = Arrays.asList(ver);
        List<Vertex> list = new LinkedList<Vertex>();
        for (i=0; i<ver.length; i++){
            list.add(ver[i]);
        }
        return list;
    }

    private void bubbleSort(Edge[] array) {
        boolean swapped = true;
        int j = 0;
        Edge tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < array.length - j; i++) {
                if (array[i].compareTo(array[i + 1])>0) {
                    tmp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = tmp;
                    swapped = true;
                }
            }
        }
    }

    public List<Edge> getEdgesSorted(Set<Edge> edges){
        int i=0;
        Edge[] ed = new Edge[edges.size()];
        for (Edge v : edges){
            ed[i] = v;
            i++;
        }
        bubbleSort(ed);
        List<Edge> list = new LinkedList<Edge>();
        for (i=0; i<ed.length; i++){
            list.add(ed[i]);
        }
        //List<Edge> list = Arrays.asList(ver);
        return list;
    }

    /*public Set<Edge> getEdges(){
        return this.edges;
    }*/

    public List<Edge> getEdges(){
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
