package SystemObj;

import Algorithms.Enums;
import DAL.DAL_Interface;
import DAL.DAL_InterfaceImpl;
import Graph.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BLManagerImpl implements BLManager{
    private static BLManager single_instance = null;
    private static IllusionObj illusionobj;
    private PoolObj pool = new PoolObj();
    private DAL_Interface mydal = DAL_InterfaceImpl.getInstance();
    private BLManagerImpl(){

    }

    public static BLManager getInstance()
    {
        if(single_instance  == null)
        {
            single_instance  = new BLManagerImpl();
            illusionobj = new IllusionObj();
        }
        return single_instance ;
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
        boolean connected = mydal.InsertObject(D3,illusionobj.getSvgObj().getSvg(), illusionobj.getSvgObj().getXml(),
                                v1.getCircleNum(),v1.getLineNum(),
                                v2.getCircleNum(),v2.getLineNum(),
                            "adarrrr"
                                );

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
    public List<Integer> getAllobjects(){
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
        List<String> next8 = pool.next8();
        List<String> files = mydal.getNext8(next8);
        if (files == null) return null;
        List<Integer> index = pool.saveFiles(files);
        return index;
    }

    public List<Integer> getPrevObjects(){
        List<String> prev8 = pool.prev8();
        List<String> files = mydal.getNext8(prev8);
        if (files == null) return null;
        List<Integer> index = pool.saveFiles(files);
        return index;
    }

    @Override
    public String loadObject(int index) {
        String name = pool.getIndexName(index);
        if (name == null) return null;
        System.out.println("index: "+index);
        System.out.println("name: "+name);
        String xml = mydal.getObjectXml(name);
        if (xml == null) return null;
        return xml;
    }

    @Override
    public List<Integer> getSimilarObjects(String xml,String svg){
        illusionobj.Decide(xml,svg);
        System.out.println("1");
        Map<String, Pair<Integer, Integer>> viewPoints = mydal.getAllViewPoints();
        System.out.println("2");
        List<String> similarVP = illusionobj.similarObj(viewPoints);

        System.out.println("similar vp: "+similarVP);

        List<String> ids=mydal.getObjIDByViewPointID(similarVP);

        System.out.println("the ids: "+ids);
        if (ids == null) return null;
        pool.setAllID(ids);

        List<String> next8 = pool.next8();
        List<String> files = mydal.getNext8(next8);
        if (files == null) return null;
        List<Integer> index = pool.saveFiles(files);
        return index;
    }
}
