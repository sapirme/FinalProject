package Illusion;

import Algorithms.CreationAlgorithm;
import Algorithms.Enums;
import Algorithms.SVGParser;
import Graph.Pair;
import Object3D.ObjectInteface;
import Object3D.Stl3DFile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class IllusionObject {

    private  ViewPoint v1;
    private  ViewPoint v2;
    private Svg svg;
    private ObjectInteface model3D;

    public IllusionObject(){
        v1 = new ViewPoint();
        v2 = new ViewPoint();
        svg = new Svg();
        model3D = new Stl3DFile();//Obj3DFile();
    }



    public ViewPoint getViewPoint1(){return v1;}

    public ViewPoint getViewPoint2(){return v2;}

    public Svg getSvg(){return svg;}

    public ObjectInteface getObjectInteface(){return model3D;}

    public Enums.checkingAns Decide(String xml,String svg){
        this.svg.setSvg(svg);
        this.svg.setXml(xml);
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

    public List<String> similarObj(Map<String,Pair<Integer, Integer>> allViewPoints){//return list of ID of the similar objects
        List<String> TopIDs = new LinkedList<>();
        
        Map <String, Double> allVPimaginationPercentage = new HashMap<>();
        for (Map.Entry<String, Pair<Integer, Integer>> vp: allViewPoints.entrySet()) {
            if(imaginationPercentage(v1.getCircleNum(), vp.getValue().getFirst(), v1.getLineNum(), vp.getValue().getSecond())
                > imaginationPercentage(v2.getCircleNum(), vp.getValue().getFirst(), v2.getLineNum(), vp.getValue().getSecond())) {
                allVPimaginationPercentage.put(vp.getKey(), imaginationPercentage(v1.getCircleNum(), vp.getValue().getFirst(), v1.getLineNum(), vp.getValue().getSecond()));
            }
            else allVPimaginationPercentage.put(vp.getKey(), imaginationPercentage(v2.getCircleNum(), vp.getValue().getFirst(), v2.getLineNum(), vp.getValue().getSecond()));
        }

        for (Map.Entry<String, Double> vp :allVPimaginationPercentage.entrySet()) {
            if(vp.getValue() >= 80)
                TopIDs.add(vp.getKey());
        }

        //if(TopIDs.size() < 5){
            for (Map.Entry<String, Double> vp :allVPimaginationPercentage.entrySet()) {
                if(vp.getValue() < 80 && vp.getValue() >= 60)
                    TopIDs.add(vp.getKey());
            }
        //}

        //if(TopIDs.size() < 5){
            for (Map.Entry<String, Double> vp :allVPimaginationPercentage.entrySet()) {
                if(vp.getValue() < 60 && vp.getValue() >= 40)
                    TopIDs.add(vp.getKey());
            }
        //}

        return TopIDs;
    }

    public double imaginationPercentage(int s1Circles,int s2Circles,int s1Lines,int s2Lines){
        if (s1Circles == 0 && s1Lines == 0)
            return 0;
        return  (sameNumOfCircles(s1Circles, s2Circles) + sameNumOfLines(s1Lines, s2Lines))/2;
    }

    public double sameNumOfCircles(int c1,int c2){

        if (c1 < c2)
            return (c1/c2)*100;
        if (c1 == 0 && c2 == 0)
            return 100;
        if (c1 == 0)
            return 0;
        return  (c2/c1)*100;
    }

    public double sameNumOfLines(int l1,int l2){
        if (l1 < l2)
            return (l1/l2)*100;
        if (l1 == 0 && l2 == 0)
            return 100;
        if (l1 == 0)
            return 0;
        return  (l2/l1)*100;

    }

}
