package Graph;
import org.junit.*;
import org.junit.Before;
import org.junit.Test;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class GraphTest {

    Graph g;
    Vertex v1,v2,v3,v4,v5,v6,v7,v8,v9,v10;
    Edge e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12,e13;

    @Before
    public void SetUp(){
        Set<Vertex> vertex=new HashSet<Vertex>();
        v1=new Vertex(0, 2);
        v2=new Vertex(1, 2);
        v3=new Vertex(2, 2);
        v4=new Vertex(4, 2);
        v5=new Vertex(5, 2);
        v6=new Vertex(6, 2);
        v7=new Vertex(3, 4);
        v8=new Vertex(3, 3);
        v9=new Vertex(3, 1);
        v10=new Vertex(3, 0);
        vertex.add(v1);
        vertex.add(v2);
        vertex.add(v3);
        vertex.add(v4);
        vertex.add(v5);
        vertex.add(v6);
        vertex.add(v7);
        vertex.add(v8);
        vertex.add(v9);
        vertex.add(v10);
        Set<Edge> edges =new HashSet<Edge>();

        e1 = new Edge(null, v1,  v2);
        e2 = new Edge(null, v2,  v3);
        e3 = new Edge(null, v3,  v4);
        e4 = new Edge(null, v4,  v5);
        e5 = new Edge(null, v5,  v6);
        e6 = new Edge(null, v2,  v7);
        e7 = new Edge(null, v7,  v5);
        e8 = new Edge(null, v2,  v10);
        e9 = new Edge(null, v10,  v5);
        e10 = new Edge(null, v3,  v8);
        e11 = new Edge(null, v8,  v4);
        e12= new Edge(null, v3,  v9);
        e13 = new Edge(null, v9,  v4);

        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);
        edges.add(e6);
        edges.add(e7);
        edges.add(e8);
        edges.add(e9);
        edges.add(e10);
        edges.add(e11);
        edges.add(e12);
        edges.add(e13);

        g=new Graph(vertex, edges);
    }

    @Test
    public void isVertexExistTest() {
        Assert.assertEquals(v1, g.isVertexExist(v1));
        Set<Vertex> v=new HashSet<Vertex>();
        Vertex v111=new Vertex(55, 11);
        v.add(v111);
        Assert.assertEquals(null, g.isVertexExist(v111));
    }

    @Test
    public void allAdjacentsTest() {
        Map<Vertex,Set<Vertex>> allAdjacentsTrue = new HashMap<Vertex,Set<Vertex>>();
        Set<Vertex> sv1 = new HashSet<Vertex>();
        sv1.add(v2);
        allAdjacentsTrue.put(v1, sv1);

        Set<Vertex> sv2 = new HashSet<Vertex>();
        sv2.add(v7);
        sv2.add(v3);
        sv2.add(v10);
        allAdjacentsTrue.put(v2, sv2);

        Set<Vertex> sv3 = new HashSet<Vertex>();
        sv3.add(v8);
        sv3.add(v4);
        sv3.add(v9);
        allAdjacentsTrue.put(v3, sv3);

        Set<Vertex> sv4 = new HashSet<Vertex>();
        sv4.add(v5);
        allAdjacentsTrue.put(v4, sv4);

        Set<Vertex> sv5 = new HashSet<Vertex>();
        sv5.add(v6);
        allAdjacentsTrue.put(v5, sv5);

        Set<Vertex> sv6 = new HashSet<Vertex>();
        allAdjacentsTrue.put(v6, sv6);

        Set<Vertex> sv7 = new HashSet<Vertex>();
        sv7.add(v5);
        allAdjacentsTrue.put(v7, sv7);

        Set<Vertex> sv8 = new HashSet<Vertex>();
        sv8.add(v4);
        allAdjacentsTrue.put(v8, sv8);

        Set<Vertex> sv9 = new HashSet<Vertex>();
        sv9.add(v4);
        allAdjacentsTrue.put(v9, sv9);

        Set<Vertex> sv10 = new HashSet<Vertex>();
        sv10.add(v5);
        allAdjacentsTrue.put(v10, sv10);

        Assert.assertEquals(allAdjacentsTrue, g.allAdjacents());
    }

    @Test
    public void edgesFromVertexTest() {
        Set<List<Edge>> allPathsEdgesTrue = new HashSet<List<Edge>>();
        List<Edge> edgesTrue = new LinkedList<Edge>();
        edgesTrue.add(e1);
        edgesTrue.add(e2);
        allPathsEdgesTrue.add(edgesTrue);
        Set<List<Vertex>> allPathsVertexs = new HashSet<List<Vertex>>();
        List<Vertex> vertexs = new LinkedList<Vertex>();
        vertexs.add(v1);
        vertexs.add(v2);
        vertexs.add(v3);
        allPathsVertexs.add(vertexs);
        Assert.assertEquals(allPathsEdgesTrue, g.edgesFromVertex(allPathsVertexs));
    }

    @Test
    public void allPathsTest() {
        Set<List<Edge>> allPathsTrue = new HashSet<List<Edge>>();
        List<Edge> path1 = new LinkedList<Edge>();
        path1.add(e1);
        path1.add(e2);
        path1.add(e12);
        path1.add(e13);
        path1.add(e4);
        path1.add(e5);
        allPathsTrue.add(path1);
        List<Edge> path2 = new LinkedList<Edge>();
        path2.add(e1);
        path2.add(e2);
        path2.add(e10);
        path2.add(e11);
        path2.add(e4);
        path2.add(e5);
        allPathsTrue.add(path2);
        List<Edge> path3 = new LinkedList<Edge>();
        path3.add(e1);
        path3.add(e6);
        path3.add(e7);
        path3.add(e5);
        allPathsTrue.add(path3);
        List<Edge> path4 = new LinkedList<Edge>();
        path4.add(e1);
        path4.add(e8);
        path4.add(e9);
        path4.add(e5);
        allPathsTrue.add(path4);
        List<Edge> path5 = new LinkedList<Edge>();
        path5.add(e1);
        path5.add(e2);
        path5.add(e3);
        path5.add(e4);
        path5.add(e5);
        allPathsTrue.add(path5);
        Set<List<Edge>> allPaths = g.allPaths(v1, v6);
        Assert.assertEquals(allPathsTrue, allPaths);
    }
}