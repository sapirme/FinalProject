package SystemObj;

import Algorithms.CreationAlgorithm;
import Algorithms.Enums;
import Algorithms.SVGParser;
import Shapes.*;
import Object3D.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class IllusionObj {

    private  ViewPoint v1;
    private  ViewPoint v2;
    private SvgObj svgObj;
    private ObjectInteface model3D;

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

    public List<String> similarObj(){//return list of ID of the similar objects
        List<String> TopIDs = new LinkedList<>();
        Map <String, Double> allVPimaginationPercentage = new HashMap<>();

        for (Map.Entry<String,List<Shape>> vp: getAllViewPoints().entrySet()) {
            if(imaginationPercentage(v1.getShapes(), vp.getValue()) > imaginationPercentage(v2.getShapes(), vp.getValue()) )
                allVPimaginationPercentage.put(vp.getKey(), imaginationPercentage(v1.getShapes(), vp.getValue()));
            else allVPimaginationPercentage.put(vp.getKey(), imaginationPercentage(v2.getShapes(), vp.getValue()));
        }

        for (Map.Entry<String, Double> vp :allVPimaginationPercentage.entrySet()) {
            if(vp.getValue() >= 80)
                TopIDs.add(vp.getKey());
        }

        if(TopIDs.size() < 5){
            for (Map.Entry<String, Double> vp :allVPimaginationPercentage.entrySet()) {
                if(vp.getValue() < 80 && vp.getValue() >= 60)
                    TopIDs.add(vp.getKey());
            }
        }

        if(TopIDs.size() < 5){
            for (Map.Entry<String, Double> vp :allVPimaginationPercentage.entrySet()) {
                if(vp.getValue() < 60 && vp.getValue() >= 40)
                    TopIDs.add(vp.getKey());
            }
        }

        return TopIDs;
    }

    public double imaginationPercentage(List<Shape> s1,List<Shape> s2){
        return  (sameNumOfCircles(s1, s2) + sameNumOfLines(s1, s2))/2;
    }

    public double sameNumOfCircles(List<Shape> s1,List<Shape> s2){
        int c1=0, c2=0;
        for (Shape s: s1) {
            if (s instanceof Circle)
                c1++;
        }
        for (Shape s: s2) {
            if (s instanceof Circle)
                c2++;
        }

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
    public Map<String,List<Shape>>  getAllViewPoints(){return null;} // return map of vp's id and vp's shapes
    public String  getObjIDByViewPointID(String vpID){return null;}
}
