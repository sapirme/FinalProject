package Shapes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class LineTest {
    Shape l1;
    Shape l2;

    @Before
    public void SetUp(){
        l1 = new Line(0,0,2,2);
        l2 = new Line(0,2,2,0);
    }
    @Test
    public void towLinesIntersectionsTest() {
        List<Point2D.Double> intersections = new LinkedList<>();
        Point2D.Double p = new Point2D.Double(1, 1);
        intersections.add(p);
        Assert.assertEquals(intersections, l1.getIntersections_accept(l2));
    }
    @Test
    public void noIntersectionsTest() {
        List<Point2D.Double> intersections = new LinkedList<>();
        Assert.assertEquals(intersections, l1.getIntersections_accept(new Line(1,0,3,2)));
    }


}