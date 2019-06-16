package SystemObj;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class BLManagerImplTest {
    private BLManager blmi = new BLManagerImpl("132.72.23.63",0);

    @Test
    public void removeDuplicatesTest(){
        List<String> l = new LinkedList<>();
        l.add("1");
        l.add("1");
        l.add("2");
        List<String> lRemoveDup = blmi.removeDuplicates(l);
        List<String> ans = new LinkedList<>();
        ans.add("1");
        ans.add("2");
        Assert.assertEquals(lRemoveDup, ans);
    }
}
