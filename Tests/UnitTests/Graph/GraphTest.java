package Graph;
import org.junit.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class GraphTest {

    Graph g1;
    Vertex v1,v2,v3,v4,v5,v6,v7,v8,v9,v10;
    Edge e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12,e13;

    Graph g2;
    Vertex v21,v22,v23;
    Edge e21,e22,e23,e24;

    @Before
    public void SetUp1(){
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

        g1=new Graph(vertex, edges);
    }

    @Before
    public void SetUp2(){
        Set<Vertex> vertex=new HashSet<Vertex>();
        v21=new Vertex(0, 0);
        v22=new Vertex(2, 0);
        v23=new Vertex(4, 0);
        vertex.add(v21);
        vertex.add(v22);
        vertex.add(v23);

        Set<Edge> edges =new HashSet<Edge>();

        e21 = new Edge(null, v21,  v22);
        e22 = new Edge(null, v21,  v22);
        e23 = new Edge(null, v22,  v23);
        e24 = new Edge(null, v22,  v23);

        edges.add(e21);
        edges.add(e22);
        edges.add(e23);
        edges.add(e24);

        g2=new Graph(vertex, edges);
    }

    @Test
    public void isVertexExistTest() {
        Assert.assertEquals(v1, g1.isVertexExist(v1));
        Set<Vertex> v=new HashSet<Vertex>();
        Vertex v111=new Vertex(55, 11);
        v.add(v111);
        Assert.assertEquals(null, g1.isVertexExist(v111));
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
        Set<List<Edge>> allPaths = g1.allPaths(v1, v6);
        Assert.assertEquals(allPathsTrue, allPaths);
    }


    @Test
    public void allPaths2Test(){ // more then 1 path between vertexs
        Set<List<Edge>> allPathsTrue = new HashSet<List<Edge>>();
        List<Edge> path1 = new LinkedList<Edge>();
        path1.add(e21);
        path1.add(e23);
        allPathsTrue.add(path1);
        List<Edge> path2 = new LinkedList<Edge>();
        path2.add(e21);
        path2.add(e24);
        allPathsTrue.add(path2);
        List<Edge> path3 = new LinkedList<Edge>();
        path3.add(e22);
        path3.add(e23);
        allPathsTrue.add(path3);
        List<Edge> path4 = new LinkedList<Edge>();
        path4.add(e22);
        path4.add(e24);
        allPathsTrue.add(path4);
        Set<List<Edge>> allPaths = g2.allPaths(v21, v23);
        Assert.assertEquals(allPathsTrue, allPaths);
    }
}