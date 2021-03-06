package UnitTests.SystemObj;
import IllusionSystem.*;

import Illusion.IllusionObject;
import Shapes.Shape;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class IllusionObjTest {

    IllusionObject ill = new IllusionObject();
    List<Shape> s1;
    List<Shape> s2;


    @Test
    public void imaginationPercentage100Test() {
        Assert.assertEquals(100,ill.imaginationPercentage(5,5,5,5),0);
        Assert.assertEquals(100,ill.imaginationPercentage(5,5,15,15),0);
    }

    @Test
    public void imaginationPercentage50Test() {
        Assert.assertEquals(50,ill.imaginationPercentage(2,1,0,0),0);
        Assert.assertEquals(50,ill.imaginationPercentage(0,0,1,2),0);
    }

    @Test
    public void imaginationPercentage0Test() {
        Assert.assertEquals(0,ill.imaginationPercentage(2,0,2,0),0);
        Assert.assertEquals(0,ill.imaginationPercentage(0,1,0,1),0);

        Assert.assertEquals(0,ill.imaginationPercentage(0,1,0,0),0);
    }
}
