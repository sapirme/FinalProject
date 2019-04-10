package Algorithms;

import Graph.*;
import Object3D.*;
import javafx.geometry.Point3D;

import java.math.BigDecimal;

import java.util.LinkedList;
import java.util.List;


public class CreationAlgorithm {

    private static double Alpha = (45 * Math.PI) /180;
    private static int Height = 5;

    public static Point3D cuttingPoint(Point3D p1, Point3D p2){
        double cosA = round( Math.cos(Alpha), 14);
        double sinA = round( Math.sin(Alpha), 14);

        double s = (p2.getZ() - p1.getZ() - ((cosA/sinA)*(p2.getY()-p1.getY())))
                /(-2*cosA);

        double x = p1.getX();
        double y = p2.getY() - (sinA*s);
        double z = p2.getZ() + (cosA*s);

        return new Point3D(x, y, z);
    }

    private static double round(double value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static String createOBJ(Graph g1, Graph g2, ObjectInteface modle3D){
        //System.out.println("fore : 4");
        String ans = "";
        double min = 0;
        boolean first =true ;
        List<LinkedList<Point3D>> allLists= new LinkedList<>();
        for (Edge e1 : g1.getEdges()){
            //System.out.println("edges : 1");
            for (Edge e2 : g2.getEdges()){
                //System.out.println("edegs : 2");
                LinkedList<Point3D> inersections = inersection2Edges(e1,e2);
                if (inersections.size()>0){
                    //System.out.println("edge1: "+e1+" edge2: "+e2);
                    allLists.add(inersections);
                    double minInList = findMinZ(inersections);
                    if (first){
                        min= minInList;
                        first =false;
                    }
                    else if (minInList < min){
                        min = minInList;
                    }
                }
            }

        }
        
        for (LinkedList<Point3D> l : allLists){
            //System.out.println("hey");
            ans = ans +"\n" + modle3D.listToText(l,min,Height);
        }
        modle3D.setText(ans);

        return ans;
    }

    public static double findMinZ(LinkedList<Point3D> list){
        if (list.isEmpty())
            throw new ArithmeticException("list is null");
        Point3D minP = list.getFirst();
        for (Point3D p: list ) {
            if(p.getZ() < minP.getZ() )
                minP = p;
        }
        return minP.getZ();
    }



    public static LinkedList<Point3D> inersection2Edges(Edge e1, Edge e2){
        LinkedList<Point3D> ans = new LinkedList();
        double xStart = Math.max(e1.getFrom().getX(),e2.getFrom().getX());
        double xEnd = Math.min(e1.getTo().getX(),e2.getTo().getX());
        double i= xStart;
        if (xStart>=xEnd) return ans;
        double x,y1,y2;
        Point3D p1,p2,intesect;
        while (i< xEnd){
            x=i;
            y1=(e1.getF()).getYbyX(x,e1.getFrom().getY(),e1.getTo().getY());
            y2=(e2.getF()).getYbyX(x,e2.getFrom().getY(),e2.getTo().getY());
            p1= Point2Dto3DVP1 (x,y1);
            p2= Point2Dto3DVP2 (x,y2);
            intesect = cuttingPoint(p1,p2);
            ans.add(intesect);
            i=i+0.1;
        }
        x=xEnd;
        y1=(e1.getF()).getYbyX(x,e1.getFrom().getY(),e1.getTo().getY());
        y2=(e2.getF()).getYbyX(x,e2.getFrom().getY(),e2.getTo().getY());
        p1= Point2Dto3DVP1 (x,y1);
        p2= Point2Dto3DVP2 (x,y2);
        intesect = cuttingPoint(p1,p2);
        ans.add(intesect);
        return ans;
    }

    public static Point3D Point2Dto3DVP1(double x, double y){
        return  new Point3D(x,0,y);
    }

    public static Point3D Point2Dto3DVP2(double x, double y){
        return  new Point3D(x,10,y);
    }


}
