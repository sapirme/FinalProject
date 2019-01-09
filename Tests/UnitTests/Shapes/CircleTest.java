package Shapes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.*;

import static org.junit.Assert.*;

public class CircleTest {
    Shape c1;
    Shape c2;
    Shape l;

    @Before
    public void SetUp(){
        c1 = new Circle(3,3,3);
        c2 = new Circle(3,8,3);
        l = new Line(0,3,6,3);
    }

    @Test
    public void towCirclesIntersectionsTest() {
        List<Point2D.Double> intersections = new LinkedList<>();
        Point2D.Double p1 = new Point2D.Double(4.6583123951777, 5.5);
        Point2D.Double p2 = new Point2D.Double(1.3416876048223, 5.5);
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
    public void noIntersectionsTowCirclesTest() {
        List<Point2D.Double> intersections = new LinkedList<>();
        Assert.assertEquals(intersections, c1.getIntersections_accept(new Circle(3,10,3)));
    }

    @Test
    public void oneIntersectionsTowCirclesTest() {
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

}