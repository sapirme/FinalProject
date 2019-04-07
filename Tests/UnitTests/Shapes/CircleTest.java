package Shapes;
import Graph.*;
import org.junit.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.geom.Point2D;
import java.util.*;


public class CircleTest {
    Shape c1;
    Shape c2;
    Shape l;
    List<Shape> allShapes;

    @Before
    public void SetUp(){
        c1 = new Circle(3,3,3);
        c2 = new Circle(3,8,3);
        l = new Line(0,3,6,3);
        allShapes = new LinkedList<Shape>();
        allShapes.add(c1);
        allShapes.add(c2);
        allShapes.add(l);
    }

    @Test
    public void createShapeGraphTest() {
        Set<Vertex> vertex=new HashSet<Vertex>();
        Set<Edge> edges =new HashSet<Edge>();
        Graph g = new Graph(vertex,edges);
        int vertexSize = g.getVertex().size();
        int edgeSize = g.getEdges().size();
        c1.createShapeGraph(allShapes,g);
        Assert.assertTrue(g.getVertex().size() >= vertexSize);
        Assert.assertTrue(g.getEdges().size() > edgeSize);

        vertexSize = g.getVertex().size();
        edgeSize = g.getEdges().size();
        c2.createShapeGraph(allShapes,g);
        Assert.assertTrue(g.getVertex().size() >= vertexSize);
        Assert.assertTrue(g.getEdges().size() > edgeSize);
    }

    public Graph makeG1() {
        Set<Vertex> vertex1=new HashSet<Vertex>();
        Vertex v1=new Vertex(3, 0);
        Vertex v2=new Vertex(0, 3);
        Vertex v3=new Vertex(6, 3);
        Vertex v4=new Vertex(3, 6);

        Vertex v9=new Vertex(4.6583123951777, 5.5);
        Vertex v10=new Vertex(1.3416876048223003, 5.5);

        vertex1.add(v1);
        vertex1.add(v2);
        vertex1.add(v3);
        vertex1.add(v4);
        vertex1.add(v9);
        vertex1.add(v10);
        Set<Edge> edges =new HashSet<Edge>();
        edges.add(new Edge(c1, v2,  v1));
        edges.add(new Edge(c1, v1,  v3));
        edges.add(new Edge(c1, v2,  v10));
        edges.add(new Edge(c1, v9,  v3));
        edges.add(new Edge(c1, v10,  v4));
        edges.add(new Edge(c1, v4,  v9));

        Graph G1=new Graph(vertex1, edges);
        return G1;
    }
    @Test
    public void twoCirclesIntersectionsTest() {
        List<Point2D.Double> intersections = new LinkedList<>();
        Point2D.Double p1 = new Point2D.Double(4.6583123951777, 5.5);
        Point2D.Double p2 = new Point2D.Double(1.3416876048223003, 5.5);
        intersections.add(p2);
        intersections.add(p1);
        Assert.assertEquals(intersections, c1.getIntersections_accept(c2));
    }

    @Test
    public void circleAndLineIntersectionsTest() {
        List<Point2D.Double> intersections = new LinkedList<>();
        Point2D.Double p1 = new Point2D.Double(0, 3);
        Point2D.Double p2 = new Point2D.Double(6, 3);
        intersections.add(p2);
        intersections.add(p1);
        Assert.assertEquals(intersections, c1.getIntersections_accept(l));
    }

    @Test
    public void circleAndLineIntersectionsTest2() {
        Shape c_1 = new Circle(80,40,40);
        Shape c_2 = new Circle(40,80,40);
        List<Point2D.Double> intersections = new LinkedList<>();
        Point2D.Double p1 = new Point2D.Double(40, 40);
        Point2D.Double p2 = new Point2D.Double(80, 80);
        intersections.add(p1);
        intersections.add(p2);
        Assert.assertEquals(intersections, c_1.getIntersections_accept(c_2));
    }

    @Test
    public void noIntersectionsTowCirclesTest() {
        List<Point2D.Double> intersections = new LinkedList<>();
        Assert.assertEquals(intersections, c1.getIntersections_accept(new Circle(3,10,3)));
    }

    @Test
    public void twoCircleTangentsTest() {
        List<Point2D.Double> intersections = new LinkedList<>();
        Point2D.Double p = new Point2D.Double(3, 6);
        intersections.add(p);
        Assert.assertEquals(intersections, c1.getIntersections_accept(new Circle(3,9,3)));
    }

    @Test
    public void oneIntersectionsLineAndCircleTest() {
        List<Point2D.Double> intersections = new LinkedList<>();
        Point2D.Double p = new Point2D.Double(6, 3);
        intersections.add(p);
        Assert.assertEquals(intersections, c1.getIntersections_accept(new Line(3,3,7,3)));
    }

    @Test
    public void lineTangentToCircleTest() {
        List<Point2D.Double> intersections = new LinkedList<>();
        Point2D.Double p = new Point2D.Double(3, 6);
        intersections.add(p);
        Assert.assertEquals(intersections, c1.getIntersections_accept(new Line(1,6,4,6)));
    }

    @Test
    public void lineInCircleTest() {
        List<Point2D.Double> intersections = new LinkedList<>();
        Assert.assertEquals(intersections, c1.getIntersections_accept(new Line(2,3,4,3)));
    }

    @Test
    public void getYbyXTest() {
        Assert.assertEquals(6, c1.getYbyX(3,6,3),0);
        Assert.assertEquals(0, c1.getYbyX(3,0,3),0);

        Assert.assertEquals(3, c1.getYbyX(0,6,3),0);
        Assert.assertEquals(3, c1.getYbyX(6,0,3),0);

        Assert.assertEquals(5.828427125, c1.getYbyX(2,3,6),0.0000001);
        Assert.assertEquals(0.1715728753, c1.getYbyX(2,3,0),0.0000001);

        Assert.assertEquals(5.828427125, c1.getYbyX(4,6,3),0.0000001);
        Assert.assertEquals(0.1715728753, c1.getYbyX(4,0,3),0.0000001);
    }

}