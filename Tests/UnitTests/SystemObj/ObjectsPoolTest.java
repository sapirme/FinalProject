package UnitTests.SystemObj;

import org.junit.Assert;
import org.junit.Test;
import IllusionSystem.*;

import java.util.LinkedList;
import java.util.List;

public class ObjectsPoolTest {

    ObjectsPool pool = new ObjectsPool();
    List<String> allID = new LinkedList<String>();

    public void setUp(int size){
        for (int i=0; i<size; i++){
            allID.add(i+"");
        }
        pool.setAllID(allID);
    }

    @Test
    public void test1() {
        setUp(9);
        List<String> expected = new LinkedList<String>();
        Assert.assertEquals(expected,pool.prev8());

        for (int i=0; i<8; i++){
            expected.add(i+"");
        }
        Assert.assertEquals(expected,pool.next8());

        expected = new LinkedList<String>();
        for (int i=8; i<9; i++){
            expected.add(i+"");
        }
        Assert.assertEquals(expected,pool.next8());

        expected = new LinkedList<String>();
        Assert.assertEquals(expected,pool.next8());

        for (int i=0; i<8; i++){
            expected.add(i+"");
        }
        Assert.assertEquals(expected,pool.prev8());
    }

    @Test
    public void test2() {
        setUp(16);
        List<String> expected = new LinkedList<String>();
        Assert.assertEquals(expected,pool.prev8());

        for (int i=0; i<8; i++){
            expected.add(i+"");
        }
        Assert.assertEquals(expected,pool.next8());

        expected = new LinkedList<String>();
        for (int i=8; i<16; i++){
            expected.add(i+"");
        }
        Assert.assertEquals(expected,pool.next8());

        expected = new LinkedList<String>();
        Assert.assertEquals(expected,pool.next8());

        for (int i=0; i<8; i++){
            expected.add(i+"");
        }
        Assert.assertEquals(expected,pool.prev8());

        expected = new LinkedList<String>();
        for (int i=8; i<16; i++){
            expected.add(i+"");
        }
        Assert.assertEquals(expected,pool.next8());
    }
}
