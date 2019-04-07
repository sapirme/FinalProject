package Algorithms;

import Graph.Edge;
import Graph.Graph;
import javafx.geometry.Point3D;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CreationAlgorithm {

    private double Alpha = (45 * Math.PI) /180;
    private int Height = 5;

    public Point3D cuttingPoint(Point3D p1, Point3D p2){
        double cosA = round( Math.cos(Alpha), 14);
        double sinA = round( Math.sin(Alpha), 14);

        double s = (p2.getZ() - p1.getZ() - ((cosA/sinA)*(p2.getY()-p1.getY())))
                /(-2*cosA);

        double x = p1.getX();
        double y = p2.getY() - (sinA*s);
        double z = p2.getZ() + (cosA*s);

        return new Point3D(x, y, z);
    }

    private double round(double value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    public String createOBJ(Graph g1, Graph g2){
        String ans = "";
        double min = 0;
        boolean first =true ;
        List<LinkedList<Point3D>> allLists= new LinkedList<>();
        for (Edge e1 : g1.getEdges()){
            for (Edge e2 : g2.getEdges()){
                LinkedList<Point3D> inersections = inersection2Edges(e1,e2);
                if (inersections.size()>0){
                    System.out.println("edge1: "+e1+" edge2: "+e2);
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
                else{

                }
            }
        }
        for (LinkedList<Point3D> l : allLists){
            ans = ans +"\n" + listToObj(l,min);
        }
        return ans;
    }

    public double findMinZ(LinkedList<Point3D> list){
        if (list.isEmpty())
            throw new ArithmeticException("list is null");
        Point3D minP = list.getFirst();
        for (Point3D p: list ) {
            if(p.getZ() < minP.getZ() )
                minP = p;
        }
        return minP.getZ();
    }

    public String listToObj (LinkedList<Point3D> list,double minZ){
        String ans = "";
        if (list.size() <= 1){
            return ans;
        }
        Point3D p1, p2;
        for(int i=0; i < list.size() - 1; i++){
            p1 = list.get(i);
            p2 = list.get(i+1);
            ans = ans + "v " +  p1.getX()+ " " + p1.getY() + " " + p1.getZ() + "\n";
            ans = ans + "v " +  p2.getX()+ " " + p2.getY() + " " + p2.getZ() + "\n";

            ans = ans + "v " +  p2.getX()+ " " + p2.getY() + " " + (minZ - Height) + "\n";
            ans = ans + "v " +  p1.getX()+ " " + p1.getY() + " " + (minZ - Height) + "\n";

            ans = ans + "f -1 -2 -3 -4 \n";

        }
        return ans;
    }

    public LinkedList<Point3D> inersection2Edges(Edge e1, Edge e2){
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
            i=i+0.01;
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

    public Point3D Point2Dto3DVP1(double x, double y){
        return  new Point3D(x,0,y);
    }

    public Point3D Point2Dto3DVP2(double x, double y){
        return  new Point3D(x,10,y);
    }


}
