package Algorithms;

import Graph.Edge;
import Graph.Graph;
import Graph.Vertex;
import Object3D.Obj3DFile;
import Shapes.Circle;
import Shapes.Line;
import javafx.geometry.Point3D;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CreationAlgorithmTest {

    CreationAlgorithm ca = new CreationAlgorithm();
    Graph g1, g2;
    Vertex v1,v2,v3,v4;
    Edge e1,e2;

    Graph g3, g4;
    Vertex v13,v23,v33,v43,v14,v24,v34,v44;
    Edge e13,e23,e33,e43,e14,e24,e34,e44;

    @Before
    public void SetUp1(){
        Set<Vertex> vertex1=new HashSet<Vertex>();
        Set<Edge> edges1 =new HashSet<Edge>();
        v1=new Vertex(0, 0);
        v2=new Vertex(2, 0);
        vertex1.add(v1);
        vertex1.add(v2);
        e1 = new Edge(new Line(0,0,2,0), v1,  v2);
        edges1.add(e1);
        g1=new Graph(vertex1, edges1);

        Set<Vertex> vertex2=new HashSet<Vertex>();
        Set<Edge> edges2 =new HashSet<Edge>();
        v3=new Vertex(0, 0);
        v4=new Vertex(2, 0);
        vertex2.add(v3);
        vertex2.add(v4);
        e2 = new Edge(new Line(0,0,2,0), v3,  v4);
        edges2.add(e2);
        g2=new Graph(vertex2, edges2);
    }

    @Before
    public void SetUp2(){
        Set<Vertex> vertex1=new HashSet<Vertex>();
        Set<Edge> edges1 =new HashSet<Edge>();
        v13=new Vertex(0, 2);
        v23=new Vertex(2, 4);
        v33=new Vertex(4, 2);
        v43=new Vertex(2, 0);
        vertex1.add(v13);
        vertex1.add(v23);
        vertex1.add(v33);
        vertex1.add(v43);
        e13 = new Edge(new Line(0,2,2,4), v13,  v23);
        e23 = new Edge(new Line(0,2,2,0), v13,  v43);
        e33 = new Edge(new Line(2,4,4,2), v23,  v33);
        e43 = new Edge(new Line(2,0,4,2), v43,  v33);
        edges1.add(e13);
        edges1.add(e23);
        edges1.add(e33);
        edges1.add(e43);
        g3=new Graph(vertex1, edges1);

        Set<Vertex> vertex2=new HashSet<Vertex>();
        Set<Edge> edges2 =new HashSet<Edge>();
        v14=new Vertex(0, 2);
        v24=new Vertex(2, 4);
        v34=new Vertex(4, 2);
        v44=new Vertex(2, 0);
        vertex2.add(v14);
        vertex2.add(v24);
        vertex2.add(v34);
        vertex2.add(v44);
        e14 = new Edge(new Circle(2,2,2), v14,  v24);
        e24 = new Edge(new Circle(2,2,2), v14,  v44);
        e34 = new Edge(new Circle(2,2,2), v24,  v34);
        e44 = new Edge(new Circle(2,2,2), v44,  v34);
        edges2.add(e14);
        edges2.add(e24);
        edges2.add(e34);
        edges2.add(e44);
        g4=new Graph(vertex2, edges2);
    }

    @Test
    public void createOBJTest() {
        String text = ca.createOBJ(g3,g4,new Obj3DFile());//"Hello world";
        BufferedWriter output = null;

        try {
            File file = new File("C:\\Users\\sapir\\Desktop\\example2.obj");
            output = new BufferedWriter(new FileWriter(file));
            output.write(text);
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) {
                try {
                    output.close();
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
        //System.out.println(ca.createOBJ(g1,g2));// 2 lines
        //System.out.println(ca.createOBJ(g3,g4));// Diamond and circle
        //System.out.println(ca.createOBJ(g4,g4));// 2 circle
        //System.out.println(ca.createOBJ(g3,g3));// 2 Diamond
        //ca.createOBJ(g4,g4);

       // Assert.assertEquals("12", ca.createOBJ(g1,g2));

    }

    @Test
    public void cuttingPointTest() {
        Assert.assertEquals(ca.cuttingPoint(new Point3D(1,1,1), new Point3D(1,1,1)),new Point3D(1,1,1));
        Assert.assertEquals(ca.cuttingPoint(new Point3D(1,1,1), new Point3D(1,5,5)),new Point3D(1,5,5));
        Assert.assertEquals(ca.cuttingPoint(new Point3D(7,5,9), new Point3D(7,4,3)),new Point3D(7,1.5,5.5));

    }


}
