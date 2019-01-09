package Graph;


import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class Graph {

    private Set<Vertex> vertex;
    private Set<Edge> edges;

    public Graph (Set<Vertex> vertex, Set<Edge> edges){
        this.vertex = new HashSet<Vertex>(vertex);
        this.edges = new HashSet<Edge>(edges);
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

    public Map<Vertex,Set<Vertex>> allAdjacents(){
        Map<Vertex,Set<Vertex>> allAdjacents = new HashMap<Vertex,Set<Vertex>>();
        for (Vertex v : vertex){
            Set<Vertex> adjacents = new HashSet<Vertex>();
            for(Edge e : edges){
                if(e.getFrom().equals(v)){
                    adjacents.add(e.getTo());
                }
            }
            allAdjacents.put(v, adjacents);
        }
        return allAdjacents;
    }

	/*private Set<List<Edge>> edgesFromVertex(Set<List<Vertex>> vertexPaths){
		for (List<Vertex> p : vertexPaths){
			System.out.println("path:");
			for (Vertex v : p)
				System.out.println(v);
		}
		Set<List<Edge>> edgesPaths = new HashSet<List<Edge>>();
		List<List<Edge>> allEdges = new LinkedList<List<Edge>>();
		int totalNumOfPaths = 1;
		for(List<Vertex> vertexPath : vertexPaths){
			int numOfEdges = 0;
			// find out how many different paths there are
			for(int i=0;i<vertexPath.size()-1;i++){
				List<Edge> allEdgesForVertex = new LinkedList<Edge>();
				for(Edge e : this.edges){
					if(e.getFrom().equals(vertexPath.get(i)) && e.getTo().equals(vertexPath.get(i+1))){
						allEdgesForVertex.add(e);
						System.out.println(e);
						numOfEdges++;
					}
				}
				allEdges.add(allEdgesForVertex);
				totalNumOfPaths = totalNumOfPaths * numOfEdges;
				System.out.println(numOfEdges);
				numOfEdges = 0;
			}
		}
		for (List<Edge> e : allEdges){
			System.out.println("edges:");
			for (Edge v : e)
				System.out.println(v);
		}
		// add all different paths
		for(int j=0;j<totalNumOfPaths;j++){
			List<Edge> edgesPath = new LinkedList<Edge>();
			for(int i=0;i<allEdges.size();i++){
				edgesPath.add(allEdges.get(i).get(0));
				Edge e = allEdges.get(i).remove(0);
				allEdges.get(i).add(e);
			}
			edgesPaths.add(edgesPath);
		}
		return edgesPaths;
	}*/

    public Set<List<Edge>> edgesFromVertex(Set<List<Vertex>> vertexPaths){
        Set<List<Edge>> edgesPaths = new HashSet<List<Edge>>();
        for(List<Vertex> vertexPath : vertexPaths){
            List<Edge> edgesPath = new LinkedList<Edge>();
            for(int i=0;i<vertexPath.size()-1;i++){
                for(Edge e : this.edges){
                    if(e.getFrom().equals(vertexPath.get(i)) && e.getTo().equals(vertexPath.get(i+1))){
                        edgesPath.add(e);
                    }
                }
            }
            edgesPaths.add(edgesPath);
        }
        return edgesPaths;
    }

    public Set<List<Edge>> allPaths(Vertex from, Vertex to){
        Set<List<Vertex>> paths = new HashSet<List<Vertex>>();
        List<Vertex> path = new LinkedList<Vertex>();
        Set<Vertex> visited = new HashSet<Vertex>();
        Map<Vertex,Set<Vertex>> allAdjacents = allAdjacents();
        path.add(from);
        allPathsRecurs(from,to,visited,path,paths,allAdjacents);
        Set<List<Edge>> edgesPaths = edgesFromVertex(paths);
        return edgesPaths;
    }

    private Set<List<Vertex>> allPathsRecurs(Vertex from, Vertex to, Set<Vertex> visited, List<Vertex> path, Set<List<Vertex>> paths, Map<Vertex,Set<Vertex>> allAdjacents){
        visited.add(from);
        List<Vertex> localPath = new LinkedList<Vertex>();
        for (Vertex v : path)
            localPath.add(v);
        if(from.equals(to)){
            paths.add(localPath);
            visited.remove(from);
            return paths;
        }
        for(Vertex v : allAdjacents.get(from)){
            if(!visited.contains(v)){
                localPath.add(v);
                allPathsRecurs(v,to,visited,localPath,paths,allAdjacents);
                localPath.remove(v);
            }
        }
        visited.remove(from);
        return paths;
    }

    public void removeEdges(List<Edge> path) {
        edges.removeAll(path);
    }

    public void addEdges(List<Edge> path) {
        edges.addAll(path);
    }
}
