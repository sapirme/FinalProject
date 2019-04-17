package SystemObj;

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
    public Boolean Decide(String xml){
        boolean ans = illusionobj.Decide(xml);
        if (ans) {
            System.out.println("first : 1");
            String text = this.CreateObject();

            BufferedWriter output = null;

            try {
                System.out.println("hey 222222222222222222222");
                File file = new File("C:\\Users\\hp\\Desktop\\example2.stl");
                output = new BufferedWriter(new FileWriter(file));
                output.write(text);
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
        }
        return ans;
    }

    @Override
    public String CreateObject() {
        DAL_Interface mydal = DAL_InterfaceImpl.getInstance();
        String D3 = illusionobj.createObject();
        ViewPoint v1 = illusionobj.getViewPoint1();
        ViewPoint v2 = illusionobj.getViewPoint2();
        mydal.InsertViewPoints(D3,illusionobj.getSvgObj().getSvg(),
                                v1.getShapes(),v2.getShapes(),
                                v1.getGraph(),v2.getGraph(),
                                v1.getPaths(),v2.getPaths(),
                            "adarrrr"
                                );
        return D3;
    }
}
