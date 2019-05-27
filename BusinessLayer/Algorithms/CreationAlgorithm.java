package Algorithms;

import Graph.Edge;
import Graph.Pair;
import Object3D.ObjectInteface;
import javafx.geometry.Point3D;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class CreationAlgorithm {

    private static double Alpha = 45 * (Math.PI/180);
    private static int Height = 50;

    public static Point3D cuttingPoint(Point3D p1, Point3D p2){
        double cosA = round( Math.cos(Alpha), 14);
        double sinA = round( Math.sin(Alpha), 14);

        double s = ((p2.getY()-p1.getY())/cosA + (p2.getZ()-p1.getZ())/sinA)/2;

        double x = p2.getX();
        double y = p2.getY() - s*cosA;
        double z = p2.getZ() - (sinA*s);
        return new Point3D(x/2, y/2, z/2);
    }

    private static double round(double value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    private static void fixPoints(Set<List<Edge>> paths){
        for (List<Edge> l : paths){
            for (Edge e : l){
                e.negY();
            }
        }
    }

    public static String createObject(LinkedList<List<Edge>> pathsG1, LinkedList<List<Edge>> pathsG2, ObjectInteface modle3D){
        List<List<Edge>> sortedPathsG1 = pathsG1;
        List<List<Edge>> sortedPathsG2 = pathsG2;

        System.out.println("sorted g1");
        System.out.println(sortedPathsG1);
        System.out.println("sorted g2");
        System.out.println(sortedPathsG2);

        String ans = "";
        List<LinkedList<Point3D>> allLists= new LinkedList<>();
        Double minZ = findPoint(sortedPathsG1,sortedPathsG2,allLists);
        //System.out.println("number of lists: "+allLists.size());
        for (LinkedList<Point3D> l : allLists){
            ans = ans +"\n" + modle3D.listToText(l,minZ,Height);
        }
        Pair<Double,Double> min = findMinXMinY(allLists);
        Pair<Double,Double> max = findMaxXMinY(allLists);
        ans=ans + modle3D.addButtomCube(min.getFirst(),max.getFirst(),min.getSecond(),max.getSecond(),minZ - Height);
        modle3D.setText(ans);
        return ans;
    }

    private static Pair<Double,Double> findMinXMinY(List<LinkedList<Point3D>> allLists){
        double minX = 0;
        double minY = 0;
        boolean first = true;
        for (LinkedList<Point3D> l : allLists){
            for (Point3D p : l){
                if (first){
                    minX = p.getX();
                    minY = p.getY();
                    first=false;
                }
                else{
                    if (p.getX() < minX) minX = p.getX();
                    if (p.getY() < minY) minY = p.getY();
                }
            }
        }
        Pair<Double,Double> ans=new Pair<Double, Double>(minX,minY);
        return ans;
    }

    private static Pair<Double,Double> findMaxXMinY(List<LinkedList<Point3D>> allLists){
        double maxX = 0;
        double maxY = 0;
        boolean first = true;
        for (LinkedList<Point3D> l : allLists){
            for (Point3D p : l){
                if (first){
                    maxX = p.getX();
                    maxY = p.getY();
                    first=false;
                }
                else{
                    if (p.getX() > maxX) maxX = p.getX();
                    if (p.getY() > maxY) maxY = p.getY();
                }
            }
        }
        Pair<Double,Double> ans=new Pair<Double, Double>(maxX,maxY);
        return ans;
    }

    private static double findPoint(List<List<Edge>> pathsG1, List<List<Edge>> pathsG2,List<LinkedList<Point3D>> allLists){
        double min = 0;
        boolean first =true ;
        //System.out.println(pathsG1.size());
        //System.out.println(pathsG2.size());
        for (int i=0; i<pathsG1.size(); i++){
            //Map<Integer,List<LinkedList<Point3D>>> map = new HashMap<Integer,List<LinkedList<Point3D>>> ();
            for (int j=0; j<pathsG2.size(); j++){
                if (i!=j) continue;
                //List<LinkedList<Point3D>> inter2Paths=new LinkedList<LinkedList<Point3D>>();
                for (Edge e1 : pathsG1.get(i)){
                    for (Edge e2 : pathsG2.get(j)){
                        LinkedList<Point3D> intersections = inersection2Edges(e1,e2);
                        if (intersections.size()>0){
                            allLists.add(intersections);
                            //inter2Paths.addAll(intersections);
                            //inter2Paths.add(intersections);
                            double minZInList = findMinZ(intersections);
                            if (first){
                                min= minZInList;
                                first =false;
                            }
                            else if (minZInList < min){
                                min = minZInList;
                            }
                        }
                    }
                }
            }

        }
        return min;
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
        //return  new Point3D(x,-5,y);
        return  new Point3D(x,y,0);
        //return  new Point3D(x,y,-5);
    }

    public static Point3D Point2Dto3DVP2(double x, double y){
        //return  new Point3D(x,5,y);
        return  new Point3D(x,y,0);
        //return  new Point3D(x,y,5);
    }


}
