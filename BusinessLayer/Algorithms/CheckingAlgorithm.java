package Algorithms;


import Graph.Edge;
import Graph.Graph;
import Graph.Pair;
import Graph.Vertex;
import Shapes.Shape;

import java.util.*;
public class CheckingAlgorithm {



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
/*

    public static void findAllMatchInLists(List<Vertex> list1, List<Vertex> list2,
                                            List<Pair<Vertex,Vertex>> current,Set<List<Pair<Vertex,Vertex>>>ans){
        List<Pair<Vertex,Vertex>> localcurrent = new LinkedList<Pair<Vertex,Vertex>>();
        for (Pair<Vertex,Vertex> p : current)
            localcurrent.add(p);
        if(list1.isEmpty() || list2.isEmpty()){
            ans.add(localcurrent);
            return;
        }
        Vertex v1= list1.get(0);
        list1.remove(v1);
        List<Vertex> temp= new LinkedList<Vertex>(list2);
        for (Vertex v2 : temp){
            localcurrent.add(new Pair<Vertex,Vertex>(v1,v2));
            list2.remove(v2);
            findAllMatchInLists(list1,list2,localcurrent,ans);
            list2.add(v2);
        }
        list1.add(v1);
    }*/


    /*
    public static void findMatchReursiv(Map<Double,List<Vertex>> mapG1, Map<Double,List<Vertex>> mapG2,
                                        List<Pair<Vertex,Vertex>> currentLst,Set<List<Pair<Vertex,Vertex>>> ans){
        List<Pair<Vertex,Vertex>> localLst = new LinkedList<Pair<Vertex,Vertex>>();
        for (Pair<Vertex,Vertex> p : localLst)
            localLst.add(p);
        if (mapG1.isEmpty() || mapG2.isEmpty()){
            if (!localLst.isEmpty())
                ans.add(localLst);
            return;
        }
        for (Map.Entry<Double,List<Vertex>> entry1 : mapG1.entrySet()) {
            Double x = entry1.getKey();
            List<Vertex> list1 = entry1.getValue();
            List<Vertex> list2 = new LinkedList<Vertex>();
            Set<Double> keys= mapG2.keySet();
            for (Double key : keys ){
                if (Math.abs(key-x) < 1){
                    list2.addAll(mapG2.get(key));
                }
            }

            if (list2!=null){
                findAllMatchInLists(list1,list2,ans);
            }
            else{
                if (list2.size()>0)
                    findAllMatchInLists(list1,list2,ans);
            }
            break;

        }
    }*/

    /**
     * @param g1 - Graph
     * @param g2 - Graph
     * @return list of maching pairs of vertex from the graphs.
     */

    public static List<Pair<Vertex,Vertex>> findMatch(Graph g1, Graph g2){
        List<Pair<Vertex,Vertex>> ans= new LinkedList<Pair<Vertex,Vertex>>();
        Map<Double,List<Vertex>> map1=makeVertexMap(g1);
        Map<Double,List<Vertex>> map2=makeVertexMap(g2);

        for (Map.Entry<Double,List<Vertex>> entry1 : map1.entrySet()) {
            Double x = entry1.getKey();
            List<Vertex> list1 = entry1.getValue();
            List<Vertex> list2 = map2.get(x);
            if (list2!=null){
                findMatchInLists(list1,list2,ans);
            }
            else{
                list2=new LinkedList<Vertex>();
                Set<Double> keys= map2.keySet();
                for (Double key : keys ){
                    if (Math.abs(key-x) < 1){
                        list2.addAll(map2.get(key));
                    }
                }
                if (list2.size()>0)
                    findMatchInLists(list1,list2,ans);
            }

        }
        //System.out.println("match:");
        //System.out.println(ans);
        return ans;
    }

    private static void findMatchInLists(List<Vertex> list1, List<Vertex> list2, List<Pair<Vertex,Vertex>> ans){
        if(list1.size()!=list2.size()) {
            for (Vertex v1 : list1) {
                for (Vertex v2 : list2) {
                    ans.add(new Pair<Vertex, Vertex>(v1, v2));
                }
                /*
                double min = Math.abs(v1.getY() - list2.get(0).getY());
                Vertex matchV = list2.get(0);
                for (Vertex v2 : list2) {
                    double abs = Math.abs(v1.getY() - v2.getY());
                    if (abs < min) {
                        min = Math.abs(v1.getY() - v2.getY());
                        matchV = v2;
                    }
                }
                ans.add(new Pair<Vertex, Vertex>(v1, matchV));*/
           }
        }
        else {
            Vertex[] l1 = new Vertex[list1.size()];
            list1.toArray(l1); // fill the array
            Vertex[] l2 = new Vertex[list1.size()];
            list2.toArray(l2); // fill the array
            bubbleSort(l1);
            //for (int i=0; i<l1.length; i++)System.out.println(l1[i]);
            bubbleSort(l2);
            //for (int i=0; i<l2.length; i++)System.out.println(l2[i]);
            for (int i=0; i<l1.length ; i++) {
                ans.add(new Pair<Vertex,Vertex>(l1[i],l2[i]));
            }
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
                    if (Math.abs(p1[i].getFrom().getY() - p2[j].getFrom().getY())<1 ||
                            Math.abs(p1[i+1].getTo().getY() - p2[j+1].getTo().getY())<1)
                        continue;
                    if ((p1[i].getFrom().getY() > p2[j].getFrom().getY()) &&
                            (p1[i+1].getTo().getY() < p2[j+1].getTo().getY()) )
                    {
                        //System.out.println("hey 1");
                        return true;
                    }
                    else {
                        if (p1[i].getFrom().getY() < p2[j].getFrom().getY() &&
                                p1[i+1].getTo().getY() < p2[j+1].getTo().getY()) {
                            //System.out.println("hey 2");
                            return true;
                        }
                    }
                }

            }
        }

        return false;
    }
/*
    public static boolean isPathsIntersect(Set<List<Edge>> pathsList, List<Edge> path) {
        for (List<Edge> p : pathsList) {
            if (isPathIntersect(p,path)) return true;
        }
        return false;
    }

    public static boolean checkAlgorithem(Graph g1, Graph g2, List<Pair<Vertex,Vertex>> matchVertex,
                                          Set<List<Edge>> pathsListG1, Set<List<Edge>> pathsListG2) {
        if (g1.getEdges().isEmpty() && g2.getEdges().isEmpty()) return true;
        if (g1.getEdges().isEmpty() || g2.getEdges().isEmpty()){
            return false;
        }
        //System.out.println("g1: "+g1.getEdges().size()+" g2: "+g2.getEdges().size());
        for (Pair<Vertex,Vertex> p1 : matchVertex) {
            for (Pair<Vertex,Vertex> p2 : matchVertex) {
                if (p1.equals(p2)) continue;
                Set<List<Edge>> paths1 = g1.allPaths(p1.getFirst(),p2.getFirst());
                Set<List<Edge>> paths2 = g2.allPaths(p1.getSecond(),p2.getSecond());
                if (paths1.size()==0 || paths2.size()==0) continue;
                for (List<Edge> path1 : paths1) {
                    //System.out.println("path 1");
                    //System.out.println(path1);
                    if (isPathsIntersect(pathsListG1,path1)) {
                        continue;
                    }
                    g1.removeEdges(path1);
                    pathsListG1.add(path1);
                    for (List<Edge> path2 : paths2) {
                        //System.out.println("path 2");
                        //System.out.println(path2);
                        if (isPathsIntersect(pathsListG2,path2)) {
                            continue;
                        }

                        g2.removeEdges(path2);
                        pathsListG2.add(path2);
                        if (checkAlgorithem(g1,g2,matchVertex,pathsListG1,pathsListG2)) {
                            return true;
                        }
                        //System.out.println("not good paths");
                        pathsListG2.remove(path2);
                        g2.addEdges(path2);
                    }
                    pathsListG1.remove(path1);
                    g1.addEdges(path1);
                }
            }
        }
        //System.out.println("///////////////////////////////////////////////////////////////////");
        return false;
    }

    public static Pair<Set<List<Edge>>,Set<List<Edge>>> checkAlgorithem(Graph g1, Graph g2) {
        List<Pair<Vertex,Vertex>> matchVertex = findMatch(g1,g2);
        if (matchVertex.isEmpty()) return null;
        Set<List<Edge>> pathsListG1=new HashSet<List<Edge>>();
        Set<List<Edge>> pathsListG2=new HashSet<List<Edge>> ();
        //System.out.println("here!");
        if (checkAlgorithem(g1, g2, matchVertex, pathsListG1, pathsListG2)) {
            return new Pair<Set<List<Edge>>,Set<List<Edge>>>(pathsListG1,pathsListG2);
        }
        return null;
    }
    */



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

    public static boolean checkAlgorithem(Graph g1, Graph g2, List<Pair<Vertex,Vertex>> matchVertex,
                                          LinkedList<List<Edge>> pathsListG1, LinkedList<List<Edge>> pathsListG2) {
        if (g1.getEdges().isEmpty() && g2.getEdges().isEmpty()) return true;
        if (g1.getEdges().isEmpty() || g2.getEdges().isEmpty()){
            return false;
        }
        //System.out.println("g1: "+g1.getEdges().size()+" g2: "+g2.getEdges().size());
        for (Pair<Vertex,Vertex> p1 : matchVertex) {
            for (Pair<Vertex,Vertex> p2 : matchVertex) {
                if (p1.equals(p2)) continue;
                Set<List<Edge>> paths1 = g1.allPaths(p1.getFirst(),p2.getFirst());
                Set<List<Edge>> paths2 = g2.allPaths(p1.getSecond(),p2.getSecond());
                if (paths1.size()==0 || paths2.size()==0) continue;
                for (List<Edge> path1 : paths1) {
                    //System.out.println("path 1");
                    //System.out.println(path1);
                    if (isPathsIntersect(pathsListG1,path1)) {
                        continue;
                    }
                    g1.removeEdges(path1);
                    //pathsListG1.add(path1);
                    int locP1=addSortedListPath(pathsListG1,path1);
                    for (List<Edge> path2 : paths2) {
                        //System.out.println("path 2");
                        //System.out.println(path2);
                        if (isPathsIntersect(pathsListG2,path2)) {
                            continue;
                        }
                        int locP2=addSortedListPath(pathsListG2,path2);
                        if (locP1!=locP2){
                            pathsListG2.remove(path2);
                            continue;
                        }
                        g2.removeEdges(path2);
                        //pathsListG2.add(path2);


                        if (checkAlgorithem(g1,g2,matchVertex,pathsListG1,pathsListG2)) {
                            return true;
                        }
                        //System.out.println("not good paths");
                        pathsListG2.remove(path2);
                        g2.addEdges(path2);
                    }
                    pathsListG1.remove(path1);
                    g1.addEdges(path1);
                }
            }
        }
        //System.out.println("///////////////////////////////////////////////////////////////////");
        return false;
    }




    public static Pair<LinkedList<List<Edge>>,LinkedList<List<Edge>>> checkAlgorithem(Graph g1, Graph g2) {
        List<Pair<Vertex,Vertex>> matchVertex = findMatch(g1,g2);
        if (matchVertex.isEmpty()) return null;
        LinkedList<List<Edge>> pathsListG1=new LinkedList<List<Edge>>();
        LinkedList<List<Edge>> pathsListG2=new LinkedList<List<Edge>> ();
        //System.out.println("here!");
        if (checkAlgorithem(g1, g2, matchVertex, pathsListG1, pathsListG2)) {
            System.out.println("paths 1: ");
            System.out.println(pathsListG1);
            System.out.println("paths 2: ");
            System.out.println(pathsListG2);
            //Set<List<Edge>> set1 = new HashSet<List<Edge>>(pathsListG1);
            //Set<List<Edge>> set2 = new HashSet<List<Edge>>(pathsListG2);
            return new Pair<LinkedList<List<Edge>>,LinkedList<List<Edge>>>(pathsListG1,pathsListG2);
        }
        return null;
    }



}
