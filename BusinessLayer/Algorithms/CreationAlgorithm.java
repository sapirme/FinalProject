package Algorithms;

import Graph.*;
import Object3D.*;
import javafx.geometry.Point3D;

import java.math.BigDecimal;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;


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
        List<LinkedList<Point3D>> allListsG1= new LinkedList<>();
        Double minG1 = findPoint(g1,g2,allListsG1,false);
        List<LinkedList<Point3D>> allListsG2= new LinkedList<>();
        Double minG2 = findPoint(g2,g1,allListsG2,true);
        double min= Math.min(minG1,minG2);
        List<LinkedList<Point3D>> allLists= new LinkedList<>();
        allLists.addAll(allListsG1);
        allLists.addAll(allListsG2);
        for (LinkedList<Point3D> l : allLists){
            //System.out.println(l.getFirst() +" "+l.getLast());
            ans = ans +"\n" + modle3D.listToText(l,min,Height);
        }
        modle3D.setText(ans);

        return ans;
    }

    private static double findPoint(Graph g1, Graph g2,List<LinkedList<Point3D>> allLists,boolean isOff){
        double min = 0;
        boolean first =true ;
        for (Edge e1 : g1.getEdges()){
            List<LinkedList<Point3D>> temp = new LinkedList<>();
            for (Edge e2 : g2.getEdges()){
                LinkedList<Point3D> intersections = inersection2Edges(e1,e2);
                if (intersections.size()>0){
                    if (!isOff)
                        myAddE1(temp,intersections);//myAddE1(allLists,intersections);
                    else myAddE2(temp,intersections);//myAddE2(allLists,intersections);
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
            if (temp.size()>0)
                allLists.addAll(temp);
        }
        return min;
    }


    private static void myAddE2(List<LinkedList<Point3D>> allListsForE2,LinkedList<Point3D> inetrsections){
        List<LinkedList<Point3D>> temp= new LinkedList<>(allListsForE2);
        for (LinkedList<Point3D> lst : temp){
            if (lst.size()==0) continue;
            double XLowLst = lst.getFirst().getX();
            double XHighLst = lst.getLast().getX();
            double XLowInter = inetrsections.getFirst().getX();
            double XHighInter = inetrsections.getLast().getX();
            if (Math.max(XLowLst,XLowInter)<Math.min(XHighLst,XHighInter)){
                if (XLowLst == XLowInter){
                    if (XHighLst == XHighInter){ //no split
                        if (findMinY(lst) < findMinY(inetrsections)){ return; }// take lst
                        else { // take inetrsections
                            allListsForE2.remove(lst);
                            allListsForE2.add(inetrsections);
                        }
                    }
                    else if (XHighLst < XHighInter) { // split intersection
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pair= split(inetrsections,XHighLst);
                        if (findMinY(lst) < findMinY(inetrsections)){ // take lst
                            inetrsections=pair.getSecond();
                        }
                        else{ // take inetrsections
                            allListsForE2.remove(lst);
                            allListsForE2.add(pair.getFirst());
                            inetrsections=pair.getSecond();
                        }
                    }
                    else { // split lst
                        if (findMinY(lst) > findMinY(inetrsections)){ // take inetrsections
                            Pair<LinkedList<Point3D>,LinkedList<Point3D>> pair= split(lst,XHighInter);
                            allListsForE2.remove(lst);
                            allListsForE2.add(inetrsections);
                            allListsForE2.add(pair.getSecond());
                        }
                        return;
                    }
                }
                else if (XLowLst < XLowInter){
                    if (XHighLst == XHighInter) { // split lst
                        if (findMinY(lst) > findMinY(inetrsections)){ //take intersection
                            Pair<LinkedList<Point3D>,LinkedList<Point3D>> pair= split(lst,XLowInter);
                            allListsForE2.remove(lst);
                            allListsForE2.add(inetrsections);
                            allListsForE2.add(pair.getFirst());
                        }
                        return;
                    }
                    else if (XHighLst < XHighInter){ // split lst + intersection
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pairLst= split(lst,XLowInter);
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pairInter= split(inetrsections,XHighLst);
                        if (findMinY(pairLst.getSecond()) < findMinY(pairInter.getFirst())){ // take lst
                            inetrsections = pairInter.getSecond();
                        }
                        else{ // take intersection
                            allListsForE2.remove(lst);
                            allListsForE2.add(pairLst.getFirst());
                            allListsForE2.add(pairInter.getFirst());
                            inetrsections = pairInter.getSecond();
                        }
                    }
                    else if (XHighLst > XHighInter) { // split lst x 2
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pairLst= split(lst,XLowInter);
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pairLst2= split(pairLst.getFirst(),XHighInter);
                        if (findMinY(pairLst2.getFirst()) > findMinY(inetrsections)) { //take intersection
                            allListsForE2.remove(lst);
                            allListsForE2.add(pairLst.getFirst());
                            allListsForE2.add(inetrsections);
                            allListsForE2.add(pairLst2.getSecond());
                        }
                        return;
                    }
                }
                else{//(XLowLst > XLowInter)
                    if (XHighLst == XHighInter){ //split intersection
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pair= split(inetrsections,XLowLst);
                        if (findMinY(lst) < findMinY(pair.getSecond())){ //take lst
                            inetrsections=pair.getFirst();
                        }
                        else{
                            allListsForE2.remove(lst);
                            allListsForE2.add(pair.getSecond());
                            inetrsections=pair.getFirst();
                        }
                    }
                    else if (XHighLst < XHighInter) { //split intersection x 2
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pair= split(inetrsections,XLowLst);
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pair2= split(pair.getSecond(),XHighLst);
                        if (findMinY(lst) < findMinY(pair2.getFirst())){//take lst
                            myAddE1(allListsForE2,pair.getFirst());
                            myAddE1(allListsForE2,pair2.getSecond());
                            return;
                        }
                        else{ // take intersection
                            allListsForE2.remove(lst);
                            allListsForE2.add(pair2.getFirst());
                            myAddE1(allListsForE2,pair.getFirst());
                            myAddE1(allListsForE2,pair2.getSecond());
                        }
                    }
                    else{//(XHighLst > XHighInter) split intersection + lst
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pairLst= split(lst,XHighInter);
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pairInter= split(inetrsections,XLowLst);
                        if (findMinY(pairLst.getFirst()) < findMinY(pairInter.getSecond())) {//take lst
                            allListsForE2.remove(lst);
                            allListsForE2.remove(pairLst.getSecond());
                        }
                        inetrsections=pairInter.getFirst();
                    }
                }
            }
        }
        allListsForE2.add(inetrsections);
    }

    private static void myAddE1(List<LinkedList<Point3D>> allListsForE1,LinkedList<Point3D> inetrsections){
        List<LinkedList<Point3D>> temp= new LinkedList<>(allListsForE1);
        for (LinkedList<Point3D> lst : temp){
            if (lst.size()==0) continue;
            double XLowLst = lst.getFirst().getX();
            double XHighLst = lst.getLast().getX();
            double XLowInter = inetrsections.getFirst().getX();
            double XHighInter = inetrsections.getLast().getX();
            if (Math.max(XLowLst,XLowInter)<Math.min(XHighLst,XHighInter)){
                if (XLowLst == XLowInter){
                    if (XHighLst == XHighInter){ //no split
                        if (findMaxY(lst) > findMaxY(inetrsections)){ return; }// take lst
                        else { // take inetrsections
                            allListsForE1.remove(lst);
                            allListsForE1.add(inetrsections);
                        }
                    }
                    else if (XHighLst < XHighInter) { // split intersection
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pair= split(inetrsections,XHighLst);
                        if (findMaxY(lst) > findMaxY(inetrsections)){ // take lst
                            inetrsections=pair.getSecond();
                        }
                        else{ // take inetrsections
                            allListsForE1.remove(lst);
                            allListsForE1.add(pair.getFirst());
                            inetrsections=pair.getSecond();
                        }
                    }
                    else { // split lst
                        if (findMaxY(lst) < findMaxY(inetrsections)){ // take inetrsections
                            Pair<LinkedList<Point3D>,LinkedList<Point3D>> pair= split(lst,XHighInter);
                            allListsForE1.remove(lst);
                            allListsForE1.add(inetrsections);
                            allListsForE1.add(pair.getSecond());
                        }
                        return;
                    }
                }
                else if (XLowLst < XLowInter){
                    if (XHighLst == XHighInter) { // split lst
                        if (findMaxY(lst) < findMaxY(inetrsections)){ //take intersection
                            Pair<LinkedList<Point3D>,LinkedList<Point3D>> pair= split(lst,XLowInter);
                            allListsForE1.remove(lst);
                            allListsForE1.add(inetrsections);
                            allListsForE1.add(pair.getFirst());
                        }
                        return;
                    }
                    else if (XHighLst < XHighInter){ // split lst + intersection
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pairLst= split(lst,XLowInter);
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pairInter= split(inetrsections,XHighLst);
                        if (findMaxY(pairLst.getSecond()) > findMaxY(pairInter.getFirst())){ // take lst
                            inetrsections = pairInter.getSecond();
                        }
                        else{ // take intersection
                            allListsForE1.remove(lst);
                            allListsForE1.add(pairLst.getFirst());
                            allListsForE1.add(pairInter.getFirst());
                            inetrsections = pairInter.getSecond();
                        }
                    }
                    else if (XHighLst > XHighInter) { // split lst x 2
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pairLst= split(lst,XLowInter);
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pairLst2= split(pairLst.getFirst(),XHighInter);
                        if (findMaxY(pairLst2.getFirst()) < findMaxY(inetrsections)) { //take intersection
                            allListsForE1.remove(lst);
                            allListsForE1.add(pairLst.getFirst());
                            allListsForE1.add(inetrsections);
                            allListsForE1.add(pairLst2.getSecond());
                        }
                        return;
                    }
                }
                else{//(XLowLst > XLowInter)
                    if (XHighLst == XHighInter){ //split intersection
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pair= split(inetrsections,XLowLst);
                        if (findMaxY(lst) > findMaxY(pair.getSecond())){ //take lst
                            inetrsections=pair.getFirst();
                        }
                        else{
                            allListsForE1.remove(lst);
                            allListsForE1.add(pair.getSecond());
                            inetrsections=pair.getFirst();
                        }
                    }
                    else if (XHighLst < XHighInter) { //split intersection x 2
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pair= split(inetrsections,XLowLst);
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pair2= split(pair.getSecond(),XHighLst);
                        if (findMaxY(lst) > findMaxY(pair2.getFirst())){//take lst
                            myAddE1(allListsForE1,pair.getFirst());
                            myAddE1(allListsForE1,pair2.getSecond());
                            return;
                        }
                        else{ // take intersection
                            allListsForE1.remove(lst);
                            allListsForE1.add(pair2.getFirst());
                            myAddE1(allListsForE1,pair.getFirst());
                            myAddE1(allListsForE1,pair2.getSecond());
                        }
                    }
                    else{//(XHighLst > XHighInter) split intersection + lst
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pairLst= split(lst,XHighInter);
                        Pair<LinkedList<Point3D>,LinkedList<Point3D>> pairInter= split(inetrsections,XLowLst);
                        if (findMaxY(pairLst.getFirst()) > findMaxY(pairInter.getSecond())) {//take lst
                            allListsForE1.remove(lst);
                            allListsForE1.remove(pairLst.getSecond());
                        }
                        inetrsections=pairInter.getFirst();
                    }
                }
            }
        }
        System.out.println("!!!!");
        allListsForE1.add(inetrsections);
    }

    private static double findMaxY(LinkedList<Point3D> lst){
        double max=lst.getFirst().getY();
        for (Point3D p : lst){
            if (p.getY()>max){
                max=p.getY();
            }
        }
        return max;
    }

    private static double findMinY(LinkedList<Point3D> lst){
        double min=lst.getFirst().getY();
        for (Point3D p : lst){
            if (p.getY()<min){
                min=p.getY();
            }
        }
        return min;
    }

    private static Pair<LinkedList<Point3D>,LinkedList<Point3D>> split(LinkedList<Point3D> list,double x){
        LinkedList<Point3D> lst1=new LinkedList<>();
        LinkedList<Point3D> lst2=new LinkedList<>();
        //boolean afterX = false;
        for (Point3D p : list){
            if (p.getX() < x) {
                lst1.add(p);
            }
            if (p.getX() == x){
                lst1.add(p);
                //afterX=true;
            }
            if (p.getX() > x){
                lst2.add(p);
                /*if (afterX){
                    lst2.add(p);
                }
                else{
                    lst2.add(p);
                    afterX = true;
                }*/
            }
        }
        return new Pair<LinkedList<Point3D>, LinkedList<Point3D>>(lst1,lst2);
    }

    /*
    public static String createOBJ(Graph g1, Graph g2, ObjectInteface modle3D){
        //System.out.println("fore : 4");
        String ans = "";
        double min = 0;
        boolean first =true ;
        List<LinkedList<Point3D>> allLists= new LinkedList<>();
        for (Edge e1 : g1.getEdges()){
            //System.out.println("edges : 1");
            //List<LinkedList<Point3D>> allListsForE1= new LinkedList<>();
            for (Edge e2 : g2.getEdges()){
                //System.out.println("edegs : 2");
                LinkedList<Point3D> inersections = inersection2Edges(e1,e2);
                if (inersections.size()>0){
                    //System.out.println("edge1: "+e1+" edge2: "+e2);
                    allLists.add(inersections);
                    double minZInList = findMinZ(inersections);
                    if (first){
                        min= minZInList;
                        first =false;
                    }
                    else if (minZInList < min){
                        min = minZInList;
                    }
                }
                //allListsForE1.add(inersections);
            }


        }
        for (LinkedList<Point3D> l : allLists){
            //System.out.println("hey");
            ans = ans +"\n" + modle3D.listToText(l,min,Height);
        }
        modle3D.setText(ans);

        return ans;
    }*/

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
