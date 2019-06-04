package SystemObj;

import Algorithms.CreationAlgorithm;
import Algorithms.Enums;
import Algorithms.SVGParser;
import Object3D.ObjectInteface;
import Object3D.Stl3DFile;
import Shapes.Circle;
import Shapes.Line;
import Shapes.Shape;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class IllusionObj {

    private  ViewPoint v1;
    private  ViewPoint v2;
    private SvgObj svgObj;
    private ObjectInteface model3D;

    private Gson gson = new Gson();

    public IllusionObj(){
        v1 = new ViewPoint();
        v2 = new ViewPoint();
        svgObj = new SvgObj();
        model3D = new Stl3DFile();//Obj3DFile();
    }



    public ViewPoint getViewPoint1(){return v1;}

    public ViewPoint getViewPoint2(){return v2;}

    public SvgObj getSvgObj(){return svgObj;}

    public ObjectInteface getObjectInteface(){return model3D;}

    public Enums.checkingAns Decide(String xml,String svg){
        //String svg = svgObj.xml2svg(xml);
        this.svgObj.setSvg(svg);
        this.svgObj.setXml(xml);
        return SVGParser.decide(svg,v1,v2);
    }

    public String createObject (){
        if (v1.hasPaths() && v2.hasPaths()){
            String m = CreationAlgorithm.createObject(v1.getPaths(),v2.getPaths(),model3D);
            return m;
        }
        else
            return null;
    }

    public List<String> similarObj(Map<String,List<Shape>> allViewPoints){//return list of ID of the similar objects
        System.out.println(allViewPoints);
        List<String> TopIDs = new LinkedList<>();
        Map <String, Double> allVPimaginationPercentage = new HashMap<>();
        System.out.println("all 1");
        for (Map.Entry<String,List<Shape>> vp: allViewPoints.entrySet()) {
            System.out.println("in  for");
            double imaginationV1 = imaginationPercentage(v1.getShapes(), vp.getValue());
            double imaginationV2 = imaginationPercentage(v2.getShapes(), vp.getValue());
            if(imaginationV1 > imaginationV2 )
                allVPimaginationPercentage.put(vp.getKey(), imaginationV1);
            else allVPimaginationPercentage.put(vp.getKey(), imaginationV2);
        }
        System.out.println("all 2");
        for (Map.Entry<String, Double> vp :allVPimaginationPercentage.entrySet()) {
            if(vp.getValue() >= 80)
                TopIDs.add(vp.getKey());
        }
        System.out.println("all 3");
        if(TopIDs.size() < 5){
            for (Map.Entry<String, Double> vp :allVPimaginationPercentage.entrySet()) {
                if(vp.getValue() < 80 && vp.getValue() >= 60)
                    TopIDs.add(vp.getKey());
            }
        }
        System.out.println("all 4");
        if(TopIDs.size() < 5){
            for (Map.Entry<String, Double> vp :allVPimaginationPercentage.entrySet()) {
                if(vp.getValue() < 60 && vp.getValue() >= 40)
                    TopIDs.add(vp.getKey());
            }
        }
        System.out.println("all 5");
        return TopIDs;
    }

    public double imaginationPercentage(List<Shape> s1,List<Shape> s2){
        System.out.println("befor num c");
        double numC = sameNumOfCircles(s1, s2);
        System.out.println("befor num L");
        double numL = sameNumOfLines(s1, s2);
        System.out.println("befor return");
        return  (numC + numL)/2;
    }

    public double sameNumOfCircles(List<Shape> s1,List<Shape> s2){
        System.out.println("same c 1");
        int c1=0, c2=0;
        System.out.println("s1 size: "+s1.size());
        for (Shape s : s1) {
            if (s instanceof Circle)
                c1++;
        }
        System.out.println("same c 2");
        System.out.println("s2 size: "+s2.size());
        //Shape ans = gson.fromJson(s2.get(0), Shape.class);
        //System.out.println("s2 size: "+List ans = gson.fromJson(out.toString(), List.class););
        for (Shape shape : s2) {
            System.out.println("in loop s: ");
            if (shape instanceof Circle)
                c2++;
        }
        System.out.println("same c 3");
        if (c1 < c2)
            return (c1/c2)*100;
        if (c1 == 0 && c2 == 0)
            return 100;
        if (c1 == 0)
            return 0;
        return  (c2/c1)*100;
    }

    public double sameNumOfLines(List<Shape> s1,List<Shape> s2){
        int l1=0, l2=0;
        for (Shape s: s1) {
            if (s instanceof Line)
                l1++;
        }
        for (Shape s: s2) {
            if (s instanceof Line)
                l2++;
        }

        if (l1 < l2)
            return (l1/l2)*100;
        if (l1 == 0 && l2 == 0)
            return 100;
        if (l1 == 0)
            return 0;
        return  (l2/l1)*100;
    }

    //function DB
   // public Map<String,List<Shape>>  getAllViewPoints(){return null;} // return map of vp's id and vp's shapes
    //public String  getObjIDByViewPointID(String vpID){return null;}
}
