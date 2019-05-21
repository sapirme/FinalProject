package SystemObj;

import Algorithms.Enums;
import DAL.DAL_Interface;
import DAL.DAL_InterfaceImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class BLManagerImpl implements BLManager{
    private static BLManager single_instance = null;
    private static IllusionObj illusionobj;

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
    public Enums.checkingAns Decide(String xml){
        Enums.checkingAns ans = illusionobj.Decide(xml);
        ////////
        if(ans ==  Enums.checkingAns.CAN) {
            boolean connected = this.CreateObject();
            if(!connected){
               ans =  Enums.checkingAns.CAN_NO_DB;
            }
        }
        ////////
        return ans;
    }

    @Override
    public boolean CreateObject() {
        System.out.println("creating the object");
        DAL_Interface mydal = DAL_InterfaceImpl.getInstance();
        String D3 = illusionobj.createObject();
        ViewPoint v1 = illusionobj.getViewPoint1();
        ViewPoint v2 = illusionobj.getViewPoint2();
        boolean connected = mydal.InsertObject(D3,illusionobj.getSvgObj().getSvg(),
                                v1.getShapes(),v2.getShapes(),
                                v1.getGraph(),v2.getGraph(),
                                v1.getPaths(),v2.getPaths(),
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
}
