package SystemObj;

import Algorithms.Enums;

import java.util.List;

public interface BLManager {
    public Enums.checkingAns Decide(String xml,String svg);
    public boolean CreateObject ();
    public List<Integer> getAllobjects();
    public List<Integer> getNextObjects();
    public List<Integer> getPrevObjects();
    public String loadObject(int index);

    public boolean login (String id_token,String email);
    public boolean logout ();

    public List<Integer> getSimilarObjects(String xml,String svg);
}
