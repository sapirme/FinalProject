package Algorithms;

import Graph.*;
import Object3D.*;
import javafx.geometry.Point3D;

import java.awt.*;
import java.math.BigDecimal;

import java.util.*;
import java.util.List;


public class CreationAlgorithm {

    private static double Alpha = 45 * (Math.PI/180);
    private static int Height = 30;

    public static Point3D cuttingPoint(Point3D p1, Point3D p2){
        double cosA = round( Math.cos(Alpha), 14);
        double sinA = round( Math.sin(Alpha), 14);

        //double s = (p2.getZ() - p1.getZ() - ((cosA/sinA)*(p2.getY()-p1.getY())))
         //       /(-2*cosA);
        double s = (p2.getY()-p1.getY())/cosA + (p2.getZ()-p1.getZ())/sinA;

        double x = p2.getX();
        double y = p2.getY() - s*cosA;
        double z = p2.getZ() - (sinA*s);
///////////////////////////////////////////////////////////////////
        return new Point3D(x, y, z);
        //return new Point3D(x, z, y);
    }

/*
    private static Map<Pair<Vertex,Vertex>,List<Edge>> makeMapPath(Set<List<Edge>> paths){
        Map<Pair<Vertex,Vertex>,List<Edge>> map = new HashMap<Pair<Vertex, Vertex>, List<Edge>>();
        for (List<Edge> l: paths){
            if (l.size()>0){
                Vertex start = l.get(0).getFrom();
                Vertex end = l.get(l.size()-1).getFrom();
                map.put(new Pair<Vertex, Vertex>(start,end),l);
            }
        }
        return map;
    }*/
    private static void addSortedPaths(List<List<Edge>> ans,List<Edge> path){
        int i=0;
        for (List<Edge> p : ans){
            if (path.get(0).getTo().getX() < p.get(0).getTo().getX()){
                double x = path.get(0).getFrom().getX() + (path.get(0).getTo().getX() - path.get(0).getFrom().getX())/2;
                double pathY = path.get(0).getF().getYbyX(x,path.get(0).getFrom().getY(),path.get(0).getTo().getY());
                double pY = p.get(0).getF().getYbyX(x,p.get(0).getFrom().getY(),p.get(0).getTo().getY());
                if (pY >= pathY){
                    ans.add(i,path);
                    return;
                }
            }
            else{
                double x = p.get(0).getFrom().getX() + (p.get(0).getTo().getX() - p.get(0).getFrom().getX())/2;
                double pathY = path.get(0).getF().getYbyX(x,path.get(0).getFrom().getY(),path.get(0).getTo().getY());
                double pY = p.get(0).getF().getYbyX(x,p.get(0).getFrom().getY(),p.get(0).getTo().getY());
                if (pY >= pathY){
                    ans.add(i,path);
                    return;
                }
            }
            i++;
        }
        ans.add(i,path);
    }

    private static List<List<Edge>> findPossibleMatchPath (List<Edge> path,Set<List<Edge>> paths){
        List<List<Edge>> ans = new LinkedList<List<Edge>>() ;
        if (path.size()==0){
            return ans;
        }
        double pathXstart = path.get(0).getFrom().getX();
        double pathXend = path.get(path.size()-1).getTo().getX();
        for (List<Edge> p : paths){
            if (p.size()>0){
                if ((p.get(0).getFrom().getX() < pathXstart - 1) || (p.get(p.size()-1).getTo().getX() > pathXend + 1)){
                    continue;
                }
                else{
                    addSortedPaths(ans,p);
                }
            }
        }
        return ans;
    }

    private static Pair<List<List<Edge>>,List<List<Edge>>> sortByMatch(Set<List<Edge>> paths1,Set<List<Edge>> paths2){
        Pair <List<List<Edge>>,List<List<Edge>>> p;
        LinkedList<List<Edge>> paths1Ans = new LinkedList<List<Edge>>();
        LinkedList<List<Edge>> paths2Ans = new LinkedList<List<Edge>>();
        p=new Pair<List<List<Edge>>, List<List<Edge>>>(paths1Ans,paths2Ans);
        Set<List<Edge>> paths1Copy = new HashSet<List<Edge>>(paths1);
        Set<List<Edge>> paths2Copy = new HashSet<List<Edge>>(paths2);
        for (List<Edge> path : paths1){
            if (paths1Ans.contains(path)) continue;
            List<List<Edge>> match = findPossibleMatchPath(path,paths2Copy);
            if (match.size()==1){
                System.out.println("one match");
                paths1Ans.add(path);
                paths2Ans.add(match.get(0));
                paths1Copy.remove(path);
                paths2Copy.remove(match.get(0));
            }
            else if (match.size()>1){
                System.out.println("mor than one");
                List<List<Edge>> matchTo2 = findPossibleMatchPath(match.get(0),paths1Copy);
                for (List<Edge> l:matchTo2){
                    paths1Ans.add(l);
                    paths1Copy.remove(l);
                }
                for (List<Edge> l:match){
                    paths2Ans.add(l);
                    paths2Copy.remove(l);
                }
            }
            else{
                System.out.println("no match");
            }
        }


        return p;
    }

    private static double round(double value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }
/*
    public static List<List<Edge>> sortToList(Set<List<Edge>> paths){
        List<List<Edge>> ans= new LinkedList<List<Edge>>();
        for (List<Edge> path1 : paths){
            boolean inset = false;
            for (int i=0 ; i<ans.size(); i++){
                if (isP1Bigger(path1,ans.get(i))){
                    ans.add(i,path1);
                    inset = true;
                    break;
                }
            }
            if (!inset){
                ((LinkedList) ans).addLast(path1);
            }
        }
        System.out.println("sorted:");
        System.out.println(ans);
        return ans;
    }

    public static boolean isP1Bigger(List<Edge> p1,List<Edge>p2){
        for (Edge e1 : p1 ){
            double xStartE1 = e1.getFrom().getX();
            double xEndE1 = e1.getTo().getX();
            for (Edge e2 :p2){
                double xStartE2 = e2.getFrom().getX();
                double xEndE2 = e2.getTo().getX();
                if (xEndE1<=xStartE2 || xEndE2<=xStartE1) continue;
                if (xStartE1 > xStartE2 && xStartE1 < xEndE2){
                    double x = xStartE1 + (xEndE2 - xStartE1)/2;
                    double yOnE1 = e1.getF().getYbyX(x,e1.getFrom().getY(),e1.getTo().getY());
                    double yOnE2 = e2.getF().getYbyX(x,e2.getFrom().getY(),e2.getTo().getY());
                    ////
                    if (yOnE1 == yOnE2) continue;
                    ////
                    if (yOnE1 > yOnE2) return  true;
                    else return false;
                }
                else if ((xStartE1 == xStartE2 && xEndE1 <= xEndE2) ||
                        (xStartE1 > xStartE2 && xEndE1 <= xEndE2) ){
                    double x = xStartE1 + (xEndE1 - xStartE1)/2;
                    double yOnE1 = e1.getF().getYbyX(x,e1.getFrom().getY(),e1.getTo().getY());
                    double yOnE2 = e2.getF().getYbyX(x,e2.getFrom().getY(),e2.getTo().getY());
                    ////
                    if (yOnE1 == yOnE2) continue;
                    ////
                    if (yOnE1 > yOnE2) return  true;
                    else return false;
                }
                else if (xStartE1 < xStartE2 && xEndE1 > xStartE2){
                    double x = xStartE2 + (xEndE1 - xStartE2)/2;
                    double yOnE1 = e1.getF().getYbyX(x,e1.getFrom().getY(),e1.getTo().getY());
                    double yOnE2 = e2.getF().getYbyX(x,e2.getFrom().getY(),e2.getTo().getY());
                    ////
                    if (yOnE1 == yOnE2) continue;
                    ////
                    if (yOnE1 > yOnE2) return  true;
                    else return false;
                }
                else if ((xStartE1 < xStartE2 && xEndE1 >= xEndE2) ||
                        (xStartE1 == xStartE2 && xEndE1 > xEndE2) ){
                    double x = xStartE2 + (xEndE2 - xStartE2)/2;
                    double yOnE1 = e1.getF().getYbyX(x,e1.getFrom().getY(),e1.getTo().getY());
                    double yOnE2 = e2.getF().getYbyX(x,e2.getFrom().getY(),e2.getTo().getY());
                    ////
                    if (yOnE1 == yOnE2) continue;
                    ////
                    if (yOnE1 > yOnE2) return  true;
                    else return false;
                }
            }
        }
        double xStart = Math.max(p1.get(0).getFrom().getX(),p2.get(0).getFrom().getX());
        double xEnd = Math.min(p1.get(p1.size()-1).getTo().getX(),p2.get(p2.size()-1).getTo().getX());
        if (p1.get(0).getFrom().getX() > p2.get(0).getFrom().getX()) return true;
        else return false;
    }
*/
    private static void fixPoints(Set<List<Edge>> paths){
        for (List<Edge> l : paths){
            for (Edge e : l){
                e.negY();
            }
        }
    }

    public static String createObject(Set<List<Edge>> pathsG1, Set<List<Edge>> pathsG2, ObjectInteface modle3D){
        //System.out.println("fore : 4");
        //List<List<Edge>> sortedPathsG1 = sortToList(pathsG1);
        //List<List<Edge>> sortedPathsG2 = sortToList(pathsG2);

        //fixPoints(pathsG1);
        Pair<List<List<Edge>>,List<List<Edge>>> pair = sortByMatch(pathsG1,pathsG2);
        List<List<Edge>> sortedPathsG1 = pair.getFirst();
        List<List<Edge>> sortedPathsG2 = pair.getSecond();

        System.out.println("sorted g1");
        System.out.println(sortedPathsG1);
        System.out.println("sorted g2");
        System.out.println(sortedPathsG2);

        String ans = "";
        List<LinkedList<Point3D>> allLists= new LinkedList<>();
        Double min = findPoint(sortedPathsG1,sortedPathsG2,allLists);
        //System.out.println("number of lists: "+allLists.size());
        for (LinkedList<Point3D> l : allLists){
            ans = ans +"\n" + modle3D.listToText(l,min,Height);
        }
        modle3D.setText(ans);
        return ans;
    }

    private static double findPoint(List<List<Edge>> pathsG1, List<List<Edge>> pathsG2,List<LinkedList<Point3D>> allLists){
        double min = 0;
        boolean first =true ;
        //System.out.println(pathsG1.size());
        //System.out.println(pathsG2.size());
        for (int i=0; i<pathsG1.size(); i++){
            Map<Integer,List<LinkedList<Point3D>>> map = new HashMap<Integer,List<LinkedList<Point3D>>> ();
            for (int j=0; j<pathsG2.size(); j++){
                if (i!=j) continue;
                //System.out.println("path1: "+pathsG1.get(i));
                //System.out.println("path2: "+pathsG2.get(j));
                //System.out.println();
                List<LinkedList<Point3D>> inter2Paths=new LinkedList<LinkedList<Point3D>>();
                for (Edge e1 : pathsG1.get(i)){
                    for (Edge e2 : pathsG2.get(j)){
                        LinkedList<Point3D> intersections = inersection2Edges(e1,e2);
                        if (intersections.size()>0){
                            //inter2Paths.addAll(intersections);
                            inter2Paths.add(intersections);
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
                if (inter2Paths.size()>0){
                    map.put(j,inter2Paths);
                }
            }
            /*if (map.size()>1){
                System.out.println("remove");
                for (Integer key : map.keySet()){
                    if (key.intValue()!=i){
                        //allLists.add(map.get(key));
                        for (LinkedList<Point3D> l : map.get(key) ){
                            allLists.add(l);
                        }
                    }
                }
            }
            else {*/
                for (Integer key : map.keySet()){
                    //allLists.add(map.get(key));
                    for (LinkedList<Point3D> l : map.get(key) ){
                        allLists.add(l);
                    }
                }
            //}
        }
        return min;
    }




/*
    public static String createOBJ(Graph g1, Graph g2, ObjectInteface modle3D){
        //System.out.println("fore : 4");
        String ans = "";
        List<LinkedList<Point3D>> allLists= new LinkedList<>();
        Double min = findPoint(g1,g2,allLists);
        System.out.println("number of lists: "+allLists.size());
        for (LinkedList<Point3D> l : allLists){
            ans = ans +"\n" + modle3D.listToText(l,min,Height);
        }
        modle3D.setText(ans);
        return ans;
    }

    private static void removePoints(List<LinkedList<Point3D>> allLists){
        for (LinkedList<Point3D> lst : allLists){
            if (!lst.isEmpty()) {
                //Point3D p = lst.get(lst.size() / 2);
                //findOnStraight
            }
        }
    }

    private static void findOnStraight(List<LinkedList<Point3D>> allLists,LinkedList<Point3D> lst){
        List<LinkedList<Point3D>> temp= new LinkedList<>(allLists);
        for (LinkedList<Point3D> fromAll : temp){
            if (!fromAll.isEmpty() && lst != fromAll) {
                double xStartFromAll = fromAll.getFirst().getX();
                double xEndFromAll = fromAll.getLast().getX();
                double xStartLst = lst.getFirst().getX();
                double xEndLst = lst.getLast().getX();
                if (Math.max(xStartFromAll,xStartLst)<Math.min(xEndFromAll,xEndLst)) {
                    if (xStartFromAll == xStartLst){

                    }
                }
            }
        }
    }
    private static void startEqStart(List<LinkedList<Point3D>> allLists,LinkedList<Point3D> fromAll,LinkedList<Point3D> lst){
        double xEndFromAll = fromAll.getLast().getX();
        double xEndLst = lst.getLast().getX();
        if (xEndFromAll == xEndLst){

        }
    }
    private static boolean isOnSameStraight(Point3D N, Point3D p, Point3D toCheck){
        double temp = toCheck.getX()*N.getX() - p.getX()*N.getX() +
                   toCheck.getY()*N.getY() - p.getY()*N.getY() +
                   toCheck.getZ()*N.getZ() - p.getZ()*N.getZ();
        double t = temp / (N.getX()*N.getX() + N.getY()*N.getY() + N.getZ()*N.getZ());
        double x = toCheck.getX() - (p.getX() + N.getX()*t);
        double y = toCheck.getY() - (p.getY() + N.getY()*t);
        double z = toCheck.getZ() - (p.getZ() + N.getZ()*t);
        double d = Math.sqrt(x*x + y*y + z*z);
        if (d <= 1) return true;
        return false;
    }

    private static double findPoint(Graph g1, Graph g2,List<LinkedList<Point3D>> allLists){
        double min = 0;
        boolean first =true ;
        for (Edge e1 : g1.getEdges()){
            for (Edge e2 : g2.getEdges()){
                LinkedList<Point3D> intersections = inersection2Edges(e1,e2);
                if (intersections.size()>0){
                    allLists.add(intersections);
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
        return min;
    }*/
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
    public static String createOBJ(Graph g1, Graph g2, ObjectInteface modle3D){
        //System.out.println("fore : 4");
        String ans = "";
        List<LinkedList<Point3D>> allListsG1= new LinkedList<>();
        Double minG1 = findPoint(g1,g2,allListsG1,false);
        //List<LinkedList<Point3D>> allListsG2= new LinkedList<>();
        //Double minG2 = findPoint(g2,g1,allListsG2,true);
        double min=minG1;// Math.min(minG1,minG2);
        List<LinkedList<Point3D>> allLists= new LinkedList<>();
        allLists.addAll(allListsG1);
        //allLists.addAll(allListsG2);
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
                    System.out.println("e1,e2");
                    //System.out.println(e1+ " and "+e2+" ");
                    //System.out.println("intersections: "+ intersections);
                    System.out.println();
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
                        System.out.println("lst: "+lst);
                        System.out.println("inetrsections: "+inetrsections);
                        double maxLst=findMaxY(lst);
                        double maxInter = findMaxY(inetrsections);
                        if (maxLst > maxInter){
                            System.out.println("lst");
                            return; }// take lst
                        else if (maxLst == maxInter){
                            if (findMinY(lst)>findMinY(inetrsections)){
                                System.out.println("lst");
                                return;
                            }
                            else{
                                System.out.println("inter");
                                allListsForE1.remove(lst);
                                allListsForE1.add(inetrsections);
                                return;
                            }
                        }
                        else { // take inetrsections
                            System.out.println("inter");
                            allListsForE1.remove(lst);
                            allListsForE1.add(inetrsections);
                            return;
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
        System.out.println("insert : "+inetrsections);
        System.out.println("to lst : "+allListsForE1);
        System.out.println();
        allListsForE1.add(inetrsections);

    }*/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        //return  new Point3D(x,-5,y);
        //return  new Point3D(x,-y,0);
        return  new Point3D(x,y,-5);
    }

    public static Point3D Point2Dto3DVP2(double x, double y){
        //return  new Point3D(x,5,y);
        //return  new Point3D(x,y,0);
        return  new Point3D(x,y,5);
    }


}
