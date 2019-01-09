package Graph;
import org.junit.*;
import Shapes.*;
import org.junit.Before;
import org.junit.Test;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CheckingAlgorithmTest {
    Graph G1;
    Graph G2;
    Circle c1G1;
    Circle c2G1;
    Circle c1G2;
    Circle c2G2;

    @Before
    public void setUp() {
        c1G1=new Circle(3, 3, 3);
        c2G1=new Circle(3, 8, 3);

        c1G2=new Circle(3, 3, 3);
        c2G2=new Circle(3, 10, 3);
        makeG1();
        makeG2();
    }


    public void makeG1() {
        Set<Vertex> vertex1=new HashSet<Vertex>();
        Vertex v1=new Vertex(3, 0);
        Vertex v2=new Vertex(0, 3);
        Vertex v3=new Vertex(6, 3);
        Vertex v4=new Vertex(3, 6);

        Vertex v5=new Vertex(3, 5);
        Vertex v6=new Vertex(0, 8);
        Vertex v7=new Vertex(6, 8);
        Vertex v8=new Vertex(3, 11);

        Vertex v9=new Vertex(4.6583123951777, 5.5);
        Vertex v10=new Vertex(1.3416876048223, 5.5);

        vertex1.add(v1);
        vertex1.add(v2);
        vertex1.add(v3);
        vertex1.add(v4);
        vertex1.add(v5);
        vertex1.add(v6);
        vertex1.add(v7);
        vertex1.add(v8);
        vertex1.add(v9);
        vertex1.add(v10);
        Set<Edge> edges =new HashSet<Edge>();
        edges.add(new Edge(c1G1, v2,  v1));
        edges.add(new Edge(c1G1, v1,  v3));
        edges.add(new Edge(c1G1, v2,  v10));
        edges.add(new Edge(c2G1, v10,  v5));
        edges.add(new Edge(c2G1, v5,  v9));
        edges.add(new Edge(c1G1, v9,  v3));
        edges.add(new Edge(c1G1, v10,  v4));
        edges.add(new Edge(c1G1, v4,  v9));
        edges.add(new Edge(c2G1, v6,  v10));
        edges.add(new Edge(c2G1, v6,  v8));
        edges.add(new Edge(c2G1, v9,  v7));
        edges.add(new Edge(c2G1, v8,  v7));
        G1=new Graph(vertex1, edges);
    }

    public void makeG2() {
        Set<Vertex> vertex1=new HashSet<Vertex>();
        Vertex v1=new Vertex(3, 0);
        Vertex v2=new Vertex(0, 3);
        Vertex v3=new Vertex(6, 3);
        Vertex v4=new Vertex(3, 6);

        Vertex v5=new Vertex(3, 7);
        Vertex v6=new Vertex(0, 10);
        Vertex v7=new Vertex(6, 10);
        Vertex v8=new Vertex(3, 13);

        vertex1.add(v1);
        vertex1.add(v2);
        vertex1.add(v3);
        vertex1.add(v4);
        vertex1.add(v5);
        vertex1.add(v6);
        vertex1.add(v7);
        vertex1.add(v8);

        Set<Edge> edges =new HashSet<Edge>();
        edges.add(new Edge(c1G2, v2,  v1));
        edges.add(new Edge(c1G2, v1,  v3));
        edges.add(new Edge(c1G2, v2,  v4));
        edges.add(new Edge(c1G2, v4,  v3));
        edges.add(new Edge(c2G2, v6,  v5));
        edges.add(new Edge(c2G2, v6,  v8));
        edges.add(new Edge(c2G2, v8,  v7));
        edges.add(new Edge(c2G2, v5,  v7));

        G2=new Graph(vertex1, edges);
    }

    /*@Test
    public void createGraphTest() {
        List<Shape> shapes=new LinkedList<>();
        shapes.add(c2G1);
        shapes.add(c1G1);
        Graph g=CheckingAlgorithm.createGraph(shapes);
        Assert.assertEquals(G1.getEdges(), g.getEdges());
    }*/

    @Test
    public void isPathsIntersectTest() {
        Set<List<Edge>> paths=new HashSet<List<Edge>>();
        List<Edge> path1=new LinkedList<Edge>();
        List<Edge> path2=new LinkedList<Edge>();
        Vertex v2=new Vertex(0, 3);
        Vertex v3=new Vertex(6, 3);
        Vertex v4=new Vertex(3, 6);
        Vertex v5=new Vertex(3, 5);
        Vertex v6=new Vertex(0, 8);
        Vertex v7=new Vertex(6, 8);
        Vertex v9=new Vertex(4.6583123951777, 5.5);
        Vertex v10=new Vertex(1.3416876048223, 5.5);
        path1.add(new Edge(c2G1, v6,  v10));
        path1.add(new Edge(c2G1, v10,  v5));
        path1.add(new Edge(c2G1, v5,  v9));
        path1.add(new Edge(c2G1, v9,  v7));

        path2.add(new Edge(c1G1, v2,  v10));
        path2.add(new Edge(c1G1, v10,  v4));
        path2.add(new Edge(c1G1, v4,  v9));
        path2.add(new Edge(c1G1, v9,  v3));

        paths.add(path1);
        Assert.assertEquals(true,CheckingAlgorithm.isPathsIntersect(paths,path2));
    }

    @Test
    public void checkAlgorithemTest() {
        Pair<Set<List<Edge>>,Set<List<Edge>>> ans=CheckingAlgorithm.checkAlgorithem(G1,G2);
        Assert.assertNotNull(ans);

    }
}