package Graph;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class VertexTest {
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
    public void isBetweenTest() {
        Assert.assertEquals(true, v2.isBetween(v1,v3));
        Assert.assertEquals(false, v2.isBetween(v3,v4));
    }

}