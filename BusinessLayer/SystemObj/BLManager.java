package SystemObj;

import Algorithms.Enums;

import java.util.List;

public interface BLManager {
    public Enums.checkingAns Decide(String xml);
    public boolean CreateObject ();
    public List<Integer> getAllobjects();
    public List<Integer> getNextObjects();
    public List<Integer> getPrevObjects();
    public String loadObject(int index);
}
