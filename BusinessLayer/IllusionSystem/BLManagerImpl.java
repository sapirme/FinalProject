package IllusionSystem;

import Algorithms.Enums;
import DAL.DAL_Interface;
import DAL.DAL_InterfaceImpl;
import Graph.Pair;
import Illusion.IllusionObject;
import Illusion.ViewPoint;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BLManagerImpl implements BLManager{
    private static IllusionObject illusionobj;
    private ObjectsPool pool = new ObjectsPool();
    private DAL_Interface mydal ;
    private User user = new User();

    public BLManagerImpl(String address, int port){
        illusionobj = new IllusionObject();
        if (address!=null && address!="")
            mydal = new DAL_InterfaceImpl(address,port);
        else mydal =null;
    }

    public boolean isConnected(){
        if (user.getEmail() == null) return false;
        return true;
    }

    @Override
    public Enums.checkingAns Decide(String xml,String svg){
        Enums.checkingAns ans = illusionobj.Decide(xml,svg);
        return ans;
    }

    @Override
    public boolean CreateObject() {
        System.out.println("creating the object");
        String D3 = illusionobj.createObject();
        ViewPoint v1 = illusionobj.getViewPoint1();
        ViewPoint v2 = illusionobj.getViewPoint2();
        boolean connected = true;
        if (mydal == null)  connected = false;
        if (user.getEmail() != null && mydal!=null) {
            connected = mydal.InsertObject(D3, illusionobj.getSvg().getSvg(), illusionobj.getSvg().getXml(),
                    v1.getCircleNum(), v1.getLineNum(),
                    v2.getCircleNum(), v2.getLineNum(),
                    user.getToken()
            );
        }

        BufferedWriter output = null;
        try {
            System.out.println("create file");
            File file = new File(".\\files\\tempSTL.stl");
            output = new BufferedWriter(new FileWriter(file));
            output.write(D3);
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
        return connected;
    }

    public List<Integer> getAllObjects(){
        if (mydal == null) return null;
        List<String> ids = mydal.getAllIDs();
        if (ids == null) return null;
        System.out.println(ids.toString());
        pool.setAllID(ids);
        List<String> next8 = pool.next8();
        List<String> files = mydal.getNext8(next8);
        if (files == null) return null;
        List<Integer> index = pool.saveFiles(files);
        return index;
    }

    public List<Integer> getNextObjects(){
        if (mydal == null) return null;
        List<String> next8 = pool.next8();
        List<String> files = mydal.getNext8(next8);
        if (files == null) return null;
        List<Integer> index = pool.saveFiles(files);
        return index;
    }

    public List<Integer> getPrevObjects(){
        if (mydal == null) return null;
        List<String> prev8 = pool.prev8();
        List<String> files = mydal.getNext8(prev8);
        if (files == null) return null;
        List<Integer> index = pool.saveFiles(files);
        return index;
    }

    @Override
    public String loadObject(int index) {
        if (mydal == null) return null;
        String name = pool.getIndexName(index);
        if (name == null) return null;
        String xml = mydal.getObjectXml(name);
        if (xml == null) return null;
        return xml;
    }

    public List<Integer> getMyObjects(){
        if (mydal == null) return null;
        String idToken = user.getToken();
        if (idToken==null) return null;
        List<String> myObjects = mydal.getMyIDs(idToken);

        if (myObjects == null) return null;
        pool.setAllID(myObjects);

        List<String> next8 = pool.next8();
        List<String> files = mydal.getNext8(next8);
        if (files == null) return null;
        List<Integer> index = pool.saveFiles(files);
        return index;
    }

    @Override
    public List<Integer> getSimilarObjects(String xml,String svg){
        if (mydal == null) return null;
        illusionobj.Decide(xml,svg);
        try {
            Map<String, Pair<Integer, Integer>> viewPoints = mydal.getAllViewPoints();
            List<String> similarVP = illusionobj.similarObj(viewPoints);

            List<String> ids=mydal.getObjIDByViewPointID(similarVP);
            ids = removeDuplicates ( ids);

            if (ids == null) return null;
            pool.setAllID(ids);

            List<String> next8 = pool.next8();
            List<String> files = mydal.getNext8(next8);
            if (files == null) return null;
            List<Integer> index = pool.saveFiles(files);
            return index;
        }catch (Exception e){
            return null;
        }

    }

    public boolean login (String id_token,String email){
        user.setEmail(email);
        user.setToken(id_token);

        return true;
    }

    public boolean logout (){
        user.setEmail(null);
        user.setToken(null);

        return true;
    }

    public List<String> removeDuplicates (List<String> ids){
        List<String> ans= new LinkedList<>();
        for (String id: ids) {
            boolean f = false;
            for (String idAns: ans) {
                if (id.equals(idAns))
                    f = true;
            }
            if(!f)
                ans.add(id);
        }

        return ans;
    }
}
