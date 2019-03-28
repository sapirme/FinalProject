package SystemObj;

import Shapes.Circle;
import Shapes.Line;
import Shapes.Shape;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class IllusionObjTest {

    IllusionObj ill = new IllusionObj();
    List<Shape> s1;
    List<Shape> s2;


    @Test
    public void imaginationPercentage100Test() {
        s1= new LinkedList<>();
        s2 = new LinkedList<>();
        Shape c11 = new Circle(3,3,3);
        Shape c12 = new Circle(3,8,3);
        s1.add(c11);
        s1.add(c12);

        Shape c21 = new Circle(3,3,3);
        Shape c22 = new Circle(3,8,3);
        s2.add(c21);
        s2.add(c22);
        double p = ill.imaginationPercentage(s1,s2);
        Assert.assertEquals(100,p,0);
    }

    @Test
    public void imaginationPercentage50Test() {
        s1= new LinkedList<>();
        s2 = new LinkedList<>();
        Shape c11 = new Circle(3,3,3);
        Shape c12 = new Circle(3,8,3);
        s1.add(c11);
        s1.add(c12);

        Shape c21 = new Circle(3,3,3);
        s2.add(c21);
        double p = ill.imaginationPercentage(s1,s2);
        Assert.assertEquals(50,p,0);
    }

    @Test
    public void imaginationPercentage0Test() {
        s1= new LinkedList<>();
        s2 = new LinkedList<>();
        Shape l1 = new Line(0,3,6,3);
        Shape l2 = new Line(0,3,6,3);
        s1.add(l1);
        s1.add(l2);

        Shape c21 = new Circle(3,3,3);
        Shape c22 = new Circle(3,8,3);
        s2.add(c21);
        s2.add(c22);

        double p = ill.imaginationPercentage(s1,s2);
        Assert.assertEquals(0,p,0);
    }
}
