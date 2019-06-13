package Algorithms;


import Graph.Edge;
import Graph.Graph;
import Graph.Pair;
import Graph.Vertex;
import Shapes.Shape;

import java.util.*;
public class CheckingAlgorithm {

    private static int iter = 0;

    public static Pair<List<Vertex>,List<Edge>> createGraphForMatch (List<Vertex> l1, List<Vertex> l2){
        List<Vertex> vertex = new LinkedList<Vertex>();
        List<Edge> edges = new LinkedList<Edge>();
        vertex.addAll(l1);
        vertex.addAll(l2);
        for (Vertex v1 : l1){
            for (Vertex v2 : l2){
                edges.add(new Edge(null,v1,v2));
            }
        }

        Pair<List<Vertex>,List<Edge>> p = new Pair<List<Vertex>, List<Edge>>(vertex,edges);
        return p;
    }

    public static void removeAllWithV1V2 (List<Edge> edges, Vertex v1,Vertex v2){
        List<Edge> temp = new LinkedList<Edge>(edges);
        for (Edge e : temp){
            if (e.getFrom().equals(v1) || e.getTo().equals(v2)){
                edges.remove(e);
            }
        }
    }

    public static void addIfNotIn (List<List<Pair<Vertex,Vertex>>> lst,List<Pair<Vertex,Vertex>> toAdd){
        for (List<Pair<Vertex,Vertex>> l : lst){
            boolean found = true;
            for (Pair<Vertex,Vertex> p : toAdd){
                if (!l.contains(p)){
                    found = false;
                }
            }
            if (found){
                return;
            }
        }
        //System.out.println("added");
        lst.add(new LinkedList<Pair<Vertex, Vertex>>(toAdd));
    }

    public static void findAllPossibleMatchRecursiv(List<List<Pair<Vertex,Vertex>>> ans,List<Edge> edges, List<Pair<Vertex,Vertex>> current){
        if (edges.isEmpty() && !current.isEmpty()){
            addIfNotIn(ans,current);
            return;
        }
        else if (edges.isEmpty()) return;

        List<Pair<Vertex,Vertex>> localCurrent = new LinkedList<Pair<Vertex, Vertex>>(current);

        for (Edge e :edges){
            List<Edge> copy = new LinkedList<Edge>(edges);
            removeAllWithV1V2(copy,e.getFrom(),e.getTo());
            Pair<Vertex, Vertex> p = new Pair<Vertex, Vertex>(e.getFrom(),e.getTo());
            localCurrent.add(p);
            findAllPossibleMatchRecursiv(ans,copy,localCurrent);
            localCurrent.remove(p);
        }

        if (!current.isEmpty()){
            addIfNotIn(ans,current);
        }

    }

    public static List<List<Pair<Vertex,Vertex>>> findAllPossibleMatch(List<Vertex> l1, List<Vertex> l2){
        Pair<List<Vertex>,List<Edge>> p = createGraphForMatch(l1,l2);

        List<List<Pair<Vertex,Vertex>>> ans = new LinkedList<List<Pair<Vertex,Vertex>>>();

        findAllPossibleMatchRecursiv(ans,p.getSecond(),new LinkedList<Pair<Vertex, Vertex>>());

        return ans;
    }

    public static Graph createGraph(List<Shape> shapes) {
        Graph ans=new Graph(new HashSet<Vertex>(),new HashSet<Edge>());
        for (Shape s: shapes) {
            s.createShapeGraph(shapes,ans);
        }
        return ans;
    }

    /**
     * @param g - Graph
     * @return return map of x-value of g vertex, to list of vertex with the same x-value
     */
    public static Map<Double,List<Vertex>> makeVertexMap (Graph g){
        Map<Double,List<Vertex>> map=new HashMap<Double,List<Vertex>>();
        for (Vertex v : g.getVertex()) {
            List<Vertex> l=map.get(v.getX());
            if (l==null) {
                l=new LinkedList<Vertex>();
                l.add(v);
                map.put(v.getX(), l);
            }
            else {
                l.add(v);
            }
        }

        return map;
    }

    public static List<Vertex> getAllWithSameX( Map<Double,List<Vertex>> map,double x){
        List<Vertex> ans = new LinkedList<Vertex>();
        Map<Double,List<Vertex>> mapCopy = new HashMap<Double,List<Vertex>>(map);
        for (Map.Entry<Double,List<Vertex>> entry : mapCopy.entrySet()) {
            Double key = entry.getKey();
            if (Math.abs(x - key)<1){
                ans.addAll(entry.getValue());
                map.remove(key);
            }
        }
        return ans;
    }

    /**
     * @param g1 - Graph
     * @param g2 - Graph
     * @return list of maching pairs of vertex from the graphs.
     */
    public static List<List<Pair<Vertex,Vertex>>> findMatch(Graph g1, Graph g2){
        List<List<Pair<Vertex,Vertex>>> ans= new LinkedList<List<Pair<Vertex,Vertex>>>();
        Map<Double,List<Vertex>> map1=makeVertexMap(g1);
        Map<Double,List<Vertex>> map2=makeVertexMap(g2);
        while (!map1.isEmpty()){
            Double x = -1.0;
            for (Map.Entry<Double,List<Vertex>> entry1 : map1.entrySet()) {
                x = entry1.getKey();
                break;
            }
            List<Vertex> list1 = getAllWithSameX(map1,x);
            List<Vertex> list2 = getAllWithSameX(map2,x);
            if (list2.size()>0 && list1.size()>0) {
                List<List<Pair<Vertex,Vertex>>> matchForCurrentX = findMatchInLists(list1, list2);
                addAllOptionsToList(ans,matchForCurrentX);
            }

        }
        return ans;
    }

    public static List<Pair<Vertex,Vertex>> superDeepCopy(List<Pair<Vertex,Vertex>> lst){
        List<Pair<Vertex,Vertex>> ans= new LinkedList<Pair<Vertex,Vertex>>();
        for (Pair<Vertex,Vertex> p : lst){
            Pair<Vertex,Vertex> p2= new Pair<Vertex, Vertex>(p.getFirst(),p.getSecond());
            ans.add(p2);
        }
        return ans;
    }

    public static void addAllOptionsToList(List<List<Pair<Vertex,Vertex>>> ans,List<List<Pair<Vertex,Vertex>>> matchForCurrentX){
        if (ans.size()==0) ans.addAll(matchForCurrentX);
        else{
            if (matchForCurrentX.size()==0) return;
            if (matchForCurrentX.size()==1) {
                for (List<Pair<Vertex,Vertex>> inLst : ans){
                    List<Pair<Vertex,Vertex>> matchCopy = superDeepCopy(matchForCurrentX.get(0));
                    inLst.addAll(matchCopy);
                }
            }
            else {
                List<List<Pair<Vertex,Vertex>>> tempAns = new LinkedList<List<Pair<Vertex,Vertex>>>(ans);
                for (int i=0; i<matchForCurrentX.size()-1; i++){
                    for (List<Pair<Vertex,Vertex>> inLst : tempAns){
                        List<Pair<Vertex,Vertex>> inLstCopy = superDeepCopy(inLst);
                        ans.add(inLstCopy);
                    }
                }
                int j=0;
                for (List<Pair<Vertex,Vertex>> inCurrentX : matchForCurrentX){
                    List<Pair<Vertex,Vertex>> matchCopy = superDeepCopy(inCurrentX);
                    for (int i=0; i<tempAns.size(); i++){
                        ans.get(j).addAll(matchCopy);
                        j++;
                    }
                }
            }
        }
    }

    private static List<List<Pair<Vertex,Vertex>>> findMatchInLists(List<Vertex> list1, List<Vertex> list2){

        if(list1.size()!=list2.size()) {
            Vertex[] l1 = new Vertex[list1.size()];
            list1.toArray(l1); // fill the array
            Vertex[] l2 = new Vertex[list2.size()];
            list2.toArray(l2); // fill the array
            bubbleSort(l1);
            bubbleSort(l2);
            list1 = Arrays.asList(l1);
            list2 = Arrays.asList(l2);
            List<List<Pair<Vertex,Vertex>>> ans = findAllPossibleMatch(list1,list2);
            return ans;
        }
        else {
            List<List<Pair<Vertex,Vertex>>> ans = new LinkedList<List<Pair<Vertex,Vertex>>>();
            List<Pair<Vertex,Vertex>> temp = new LinkedList<Pair<Vertex, Vertex>>();
            ans.add(temp);
            Vertex[] l1 = new Vertex[list1.size()];
            list1.toArray(l1); // fill the array
            Vertex[] l2 = new Vertex[list1.size()];
            list2.toArray(l2); // fill the array
            bubbleSort(l1);
            bubbleSort(l2);
            //System.out.println("l1 sorted: "+l1);
            //System.out.println("l2 sorted: "+l2);
            for (int i=0; i<l1.length ; i++) {
                temp.add(new Pair<Vertex,Vertex>(l1[i],l2[i]));
            }
            return  ans;
        }
    }

    public static void bubbleSort(Vertex[] array) {
        boolean swapped = true;
        int j = 0;
        Vertex tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < array.length - j; i++) {
                if (array[i].getY() > array[i + 1].getY()) {
                    tmp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = tmp;
                    swapped = true;
                }
            }
        }
    }

    public static boolean isPathIntersect(List<Edge> path1, List<Edge> path2) {
        Edge[] p1 = new Edge[path1.size()];
        path1.toArray(p1); // fill the array
        Edge[] p2 = new Edge[path2.size()];
        path2.toArray(p2);

        for (int i=0; i<p1.length-1; i++) {
            for (int j=0; j<p2.length-1; j++) {
                if (p1[i].getTo().equals(p2[j].getTo())) {
                    //double yFromP1,yFromP2,yToP1,yToP2;
                    double xFirst,xSecond;
                    if (Math.abs(p1[i].getFrom().getX() - p2[j].getFrom().getX()) < 1){
                        xFirst = p1[i].getFrom().getX() + (Math.abs(p1[i].getFrom().getX() - p1[i].getTo().getX()))/2;
                    }
                    else if (p1[i].getFrom().getX() > p2[j].getFrom().getX()){
                        xFirst = p1[i].getFrom().getX() + (Math.abs(p1[i].getFrom().getX() - p1[i].getTo().getX()))/2;
                    }
                    else {
                        xFirst = p2[j].getFrom().getX() + (Math.abs(p2[j].getFrom().getX() - p2[j].getTo().getX()))/2;
                    }

                    if (Math.abs(p1[i+1].getFrom().getX() - p2[j+1].getFrom().getX()) < 1){
                        xSecond = p1[i+1].getFrom().getX() + (Math.abs(p1[i+1].getFrom().getX() - p1[i+1].getTo().getX()))/2;
                    }
                    else if (p1[i+1].getFrom().getX() > p2[j+1].getFrom().getX()){
                        xSecond = p1[i+1].getFrom().getX() + (Math.abs(p1[i+1].getFrom().getX() - p1[i+1].getTo().getX()))/2;
                    }
                    else {
                        xSecond = p2[j+1].getFrom().getX() + (Math.abs(p2[j+1].getFrom().getX() - p2[j+1].getTo().getX()))/2;
                    }

                    double yFirstP1,yFirstP2,ySecondP1,ySecondP2;
                    //System.out.println("xFirst: "+xFirst+ " xSecond: "+xSecond);
                    yFirstP1 = p1[i].getF().getYbyX(xFirst,p1[i].getFrom().getY(),p1[i].getTo().getY());
                    yFirstP2 = p2[j].getF().getYbyX(xFirst,p2[j].getFrom().getY(),p2[j].getTo().getY());

                    ySecondP1 = p1[i+1].getF().getYbyX(xSecond,p1[i+1].getFrom().getY(),p1[i+1].getTo().getY());
                    ySecondP2 = p2[j+1].getF().getYbyX(xSecond,p2[j+1].getFrom().getY(),p2[j+1].getTo().getY());
                    //System.out.println("yFirstP1: "+yFirstP1+ " yFirstP2: "+yFirstP2);
                    //System.out.println("ySecondP1: "+ySecondP1+ " ySecondP2: "+ySecondP2);

                    if (yFirstP1 > yFirstP2 && ySecondP1 < ySecondP2) return true;

                    if (yFirstP1 < yFirstP2 && ySecondP1 > ySecondP2) return true;





                    /*if (Math.abs(p1[i].getFrom().getY() - p2[j].getFrom().getY())<1 ||
                            Math.abs(p1[i+1].getTo().getY() - p2[j+1].getTo().getY())<1)
                        continue;
                    if ((p1[i].getFrom().getY() > p2[j].getFrom().getY()) &&
                            (p1[i+1].getTo().getY() < p2[j+1].getTo().getY()) ) {
                        return true;
                    }
                    else {
                        if (p1[i].getFrom().getY() < p2[j].getFrom().getY() &&
                                p1[i+1].getTo().getY() < p2[j+1].getTo().getY()) {
                            return true;
                        }
                    }*/
                }

            }
        }

        return false;
    }

    public static boolean isPathsIntersect(LinkedList<List<Edge>> pathsList, List<Edge> path) {
        for (List<Edge> p : pathsList) {
            if (isPathIntersect(p,path)) return true;
        }
        return false;
    }

    public static int addSortedListPath ( LinkedList<List<Edge>> pathsList, List<Edge> path){
        if (pathsList.size()==0) {
            pathsList.add(path);
            return 0;
        }
        int i=0;
        for (List<Edge> p : pathsList){
            if (isP1BiggerThenP2(path,p)){
                pathsList.add(i,path);
                return i;
            }
            i++;
        }
        pathsList.addLast(path);
        return pathsList.size()-1;
    }

    public static boolean isP1BiggerThenP2 (List<Edge> p1,List<Edge> p2){
        for (Edge e1 : p1){
            for (Edge e2 :p2){
                if (e1.getFrom().getX() >= e2.getTo().getX() - 1) continue;
                if (e2.getFrom().getX() >= e1.getTo().getX() - 1) continue;
                double xStart,xEnd;
                if ((e1.getFrom().getX() >= e2.getFrom().getX()) &&
                        (e1.getTo().getX() <= e2.getTo().getX())) //e1 boundries
                {
                    xStart = e1.getFrom().getX();
                    xEnd = e1.getTo().getX();
                }
                else if (e2.getFrom().getX() >= e1.getFrom().getX() &&
                        (e2.getTo().getX() <= e1.getTo().getX())) //e2 boundries
                {
                    xStart = e2.getFrom().getX();
                    xEnd = e2.getTo().getX();
                }
                else if (e1.getFrom().getX() <= e2.getFrom().getX() &&
                        (e1.getTo().getX() <= e2.getTo().getX())) //s-e2,e - e1
                {
                    xStart = e2.getFrom().getX();
                    xEnd = e1.getTo().getX();
                }
                else  //s-e2,e - e1
                {
                    xStart = e1.getFrom().getX();
                    xEnd = e2.getTo().getX();
                }
                double x = (xEnd - xStart)/2 + xStart;
                double yE1 = e1.getF().getYbyX(x,e1.getFrom().getY(),e1.getTo().getY());
                double yE2 = e2.getF().getYbyX(x,e2.getFrom().getY(),e2.getTo().getY());
                if (yE1 > yE2) return true;
                else return false;
            }
        }
        return false;
    }

    public static boolean isPathChecked(LinkedList<List<Edge>> pathsChecked,List<Edge> path){

        for (List<Edge> p : pathsChecked){
            if (p.size()!=path.size()) continue;
            boolean found = true;
            for (int i=0; i<p.size(); i++){
                if (!p.get(i).equals(path.get(i))){
                    found=false;
                    break;
                }
            }
            if (found) return true;
        }
        return false;
    }

    public static boolean checkAlgorithem(Graph g1, Graph g2, List<Pair<Vertex,Vertex>> matchVertex,
                                          LinkedList<List<Edge>> pathsListG1, LinkedList<List<Edge>> pathsListG2,
                                          Pair<LinkedList<List<Edge>>,LinkedList<List<Edge>>> pathsChecked) {
        if (g1.getEdges().isEmpty() && g2.getEdges().isEmpty()) return true;
        if (g1.getEdges().isEmpty() || g2.getEdges().isEmpty()){
            return false;
        }
        /*if (iter > 1000) {
            //System.out.println("iterrrr");
            return false;
        }*/
        iter ++;
        Pair<LinkedList<List<Edge>>,LinkedList<List<Edge>>> pathsCheckedLocal = new Pair<LinkedList<List<Edge>>, LinkedList<List<Edge>>>
                (new LinkedList<List<Edge>>(pathsChecked.getFirst()),new LinkedList<List<Edge>>(pathsChecked.getSecond()));
        for (Pair<Vertex,Vertex> p1 : matchVertex) {
            for (Pair<Vertex,Vertex> p2 : matchVertex) {
                if (p1.equals(p2)) continue;
                Set<List<Edge>> paths1 = g1.allPaths(p1.getFirst(),p2.getFirst());
                Set<List<Edge>> paths2 = g2.allPaths(p1.getSecond(),p2.getSecond());
                if (paths1.size()==0 || paths2.size()==0) continue;
                //System.out.println("between: "+ p1+ "and: "+p2);
                for (List<Edge> path1 : paths1) {
                    if (path1.size()==4){
                        System.out.println("pp");
                    }
                    ///////////////////////////
                    if (isPathChecked(pathsCheckedLocal.getFirst(),path1))////
                    {
                        continue;
                    }
                    pathsCheckedLocal.getFirst().add(path1);
                    ///////////////////////////////

                    if (isPathsIntersect(pathsListG1,path1)) {
                        continue;
                    }

                    g1.removeEdges(path1);
                    int locP1=addSortedListPath(pathsListG1,path1);
                    for (List<Edge> path2 : paths2) {
                        ///////////////////////////
                        if (isPathChecked(pathsCheckedLocal.getSecond(),path2))////
                        {
                            continue;
                        }
                        pathsCheckedLocal.getSecond().add(path2);
                        ///////////////////////////////

                        if (isPathsIntersect(pathsListG2,path2)) {
                            continue;
                        }
                        int locP2=addSortedListPath(pathsListG2,path2);
                        if (locP1!=locP2){
                            pathsListG2.remove(path2);
                            continue;
                        }

                        g2.removeEdges(path2);
                        //Pair<LinkedList<List<Edge>>,LinkedList<List<Edge>>> pathsCheckedLocal = new Pair<LinkedList<List<Edge>>, LinkedList<List<Edge>>>
                        //        (new LinkedList<List<Edge>>(pathsChecked.getFirst()),new LinkedList<List<Edge>>(pathsChecked.getSecond()));
                        if (checkAlgorithem(g1,g2,matchVertex,pathsListG1,pathsListG2,pathsCheckedLocal)) {
                            return true;
                        }
                        //System.out.println("not good 1:\n"+pathsListG1);
                        //System.out.println("not good 2:\n"+pathsListG2);
                        pathsListG2.remove(path2);
                        g2.addEdges(path2);
                    }
                    pathsListG1.remove(path1);
                    g1.addEdges(path1);
                }
            }
        }
        return false;
    }

    public static void sortMatch(Pair<Vertex,Vertex>[] array){
        boolean swapped = true;
        int j = 0;
        Pair<Vertex,Vertex> tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < array.length - j; i++) {
                if (array[i].getFirst().getX() > array[i + 1].getFirst().getX()) {
                    tmp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = tmp;
                    swapped = true;
                }
                /*if (array[i].getY() > array[i + 1].getY()) {
                    tmp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = tmp;
                    swapped = true;
                }*/
            }
        }
    }

    public static Pair<LinkedList<List<Edge>>,LinkedList<List<Edge>>> checkAlgorithem(Graph g1, Graph g2) {
        List<List<Pair<Vertex,Vertex>>> matchVertex = findMatch(g1,g2);
        if (matchVertex.isEmpty()) return null;

        for (List<Pair<Vertex,Vertex>> match : matchVertex){
            iter = 0;
            /*Pair<Vertex,Vertex>[] l1 = new Pair[match.size()];
            match.toArray(l1); // fill the array
            sortMatch(l1);
            System.out.println("cheacking match: "+Arrays.asList(l1));*/
            System.out.println(match);
            LinkedList<List<Edge>> pathsListG1=new LinkedList<List<Edge>>();
            LinkedList<List<Edge>> pathsListG2=new LinkedList<List<Edge>> ();
            Pair<LinkedList<List<Edge>>, LinkedList<List<Edge>>> p = new Pair<LinkedList<List<Edge>>, LinkedList<List<Edge>>>
                    (new LinkedList<List<Edge>>(),new LinkedList<List<Edge>>());
            if (checkAlgorithem(g1, g2, /*Arrays.asList(l1)*/match, pathsListG1, pathsListG2,p)) {
                System.out.println("paths 1: ");
                System.out.println(pathsListG1);
                System.out.println("paths 2: ");
                System.out.println(pathsListG2);
                return new Pair<LinkedList<List<Edge>>,LinkedList<List<Edge>>>(pathsListG1,pathsListG2);
            }
        }

        return null;
    }



}
