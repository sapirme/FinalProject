package UnitTests.Algorithms;
import Graph.*;
import Algorithms.*;
import Object3D.Stl3DFile;
import Shapes.*;
import javafx.geometry.Point3D;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
        v2=new Vertex(20, 0);
        vertex1.add(v1);
        vertex1.add(v2);
        e1 = new Edge(new Line(0,0,20,0), v1,  v2);
        edges1.add(e1);
        g1=new Graph(vertex1, edges1);

        Set<Vertex> vertex2=new HashSet<Vertex>();
        Set<Edge> edges2 =new HashSet<Edge>();
        v3=new Vertex(0, 0);
        v4=new Vertex(20, 0);
        vertex2.add(v3);
        vertex2.add(v4);
        e2 = new Edge(new Line(0,0,20,0), v3,  v4);
        edges2.add(e2);
        g2=new Graph(vertex2, edges2);
    }

    @Before
    public void SetUp2(){
        Set<Vertex> vertex1=new HashSet<Vertex>();
        Set<Edge> edges1 =new HashSet<Edge>();
        v13=new Vertex(0, 20);
        v23=new Vertex(20, 40);
        v33=new Vertex(40, 20);
        v43=new Vertex(20, 0);
        vertex1.add(v13);
        vertex1.add(v23);
        vertex1.add(v33);
        vertex1.add(v43);
        e13 = new Edge(new Line(0,20,20,40), v13,  v23);
        e23 = new Edge(new Line(0,20,20,00), v13,  v43);
        e33 = new Edge(new Line(20,40,40,20), v23,  v33);
        e43 = new Edge(new Line(20,0,40,20), v43,  v33);
        edges1.add(e13);
        edges1.add(e23);
        edges1.add(e33);
        edges1.add(e43);
        g3=new Graph(vertex1, edges1);

        Set<Vertex> vertex2=new HashSet<Vertex>();
        Set<Edge> edges2 =new HashSet<Edge>();
        v14=new Vertex(0, 20);
        v24=new Vertex(20, 40);
        v34=new Vertex(40, 20);
        v44=new Vertex(20, 0);
        vertex2.add(v14);
        vertex2.add(v24);
        vertex2.add(v34);
        vertex2.add(v44);
        e14 = new Edge(new Circle(20,20,20), v14,  v24);
        e24 = new Edge(new Circle(20,20,20), v14,  v44);
        e34 = new Edge(new Circle(20,20,20), v24,  v34);
        e44 = new Edge(new Circle(20,20,20), v44,  v34);
        edges2.add(e14);
        edges2.add(e24);
        edges2.add(e34);
        edges2.add(e44);
        g4=new Graph(vertex2, edges2);
    }

    @Test
    public void createOBJTest() {
        List<Shape> l1 = new LinkedList<>();
        l1.add(new Circle(20,20,20));
        List<Shape> l2 = new LinkedList<>();
        l2.add(new Line(0,20,20,40));
        l2.add(new Line(0,20,20,00));
        l2.add(new Line(20,40,40,20));
        l2.add(new Line(20,0,40,20));


        Pair<LinkedList<List<Edge>>,LinkedList<List<Edge>>> pair = CheckingAlgorithm.checkAlgorithem(CheckingAlgorithm.createGraph(l1),CheckingAlgorithm.createGraph(l2));
        System.out.println(pair.getFirst());
        System.out.println(pair.getSecond());
        String text = ca.createObject(pair.getFirst(),pair.getSecond(),new Stl3DFile());//"Hello world";
        BufferedWriter output = null;

        try {
            File file = new File(".\\files\\example.stl");
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

    }

    @Test
    public void createOBJTest2() {
        /*
        List<Shape> l1 = new LinkedList<>();
        l1.add(new Circle(20,20,20));
        l1.add(new Circle(20,50,20));
        List<Shape> l2 = new LinkedList<>();
        l2.add(new Circle(20,20,20));
        l2.add(new Circle(20,70,20));

        String text = ca.createObject(CheckingAlgorithm.createGraph(l1),CheckingAlgorithm.createGraph(l2),new Obj3DFile());//"Hello world";
        BufferedWriter output = null;

        try {
            File file = new File("C:\\Users\\טליה\\Desktop\\exampleOBJ.obj");
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
        }*/

    }
    @Test
    public void createSTLTest1() {
        List<Shape> l1 = new LinkedList<>();
        //l1.add(new Line(0,20,20,40));
        //l1.add(new Line(20,40,40,20));
        l1.add(new Circle(20,20,20));

        List<Shape> l2 = new LinkedList<>();
        l2.add(new Line(0,20,20,40));
        l2.add(new Line(0,20,20,0));
        l2.add(new Line(20,0,40,20));
        l2.add(new Line(20,40,40,20));

        Pair<LinkedList<List<Edge>>,LinkedList<List<Edge>>> pair = CheckingAlgorithm.checkAlgorithem
                (CheckingAlgorithm.createGraph(l1),CheckingAlgorithm.createGraph(l2));
        //System.out.println(pair.getFirst());

        String text = ca.createObject(pair.getFirst(),pair.getSecond(),new Stl3DFile());
        BufferedWriter output = null;

        try {
            File file = new File(".\\files\\diamondAndCircle.stl");
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
        Assert.assertEquals(new Point3D(1,1,1),ca.cuttingPoint(new Point3D(1,1,1), new Point3D(1,1,1)));
        Assert.assertEquals(new Point3D(1,1,1),ca.cuttingPoint(new Point3D(1,1,1), new Point3D(1,5,5)));
        Assert.assertEquals(new Point3D(7,7.5,6.5),ca.cuttingPoint(new Point3D(7,5,9), new Point3D(7,4,3)));

    }


}
