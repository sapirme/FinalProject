package Graph;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Shapes.*;
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
            if(list2!=null && list1.size()!=list2.size()) {
                if (list2!=null) {
                    for (Vertex v1 : list1) {
                        double min = Math.abs(v1.getY()-list2.get(0).getY());
                        Vertex matchV=list2.get(0);
                        for (Vertex v2 : list2) {
                            double abs = Math.abs(v1.getY()-v2.getY());
                            if (abs<min) {
                                min = Math.abs(v1.getY()-v2.getY());
                                matchV = v2;
                            }
                        }
                        ans.add(new Pair<Vertex,Vertex> (v1,matchV));
                    }
                }
            }
            else if(list2!=null){
                Vertex[] l1 = new Vertex[list1.size()];
                list1.toArray(l1); // fill the array
                Vertex[] l2 = new Vertex[list1.size()];
                list2.toArray(l2); // fill the array
                bubbleSort(l1);
                //for (int i=0; i<l1.length; i++)System.out.println(l1[i]);
                bubbleSort(l2);
                //for (int i=0; i<l2.length; i++)System.out.println(l2[i]);
                for (int i=0; i<l1.length ; i++) {
                    ans.add(new Pair<Vertex,Vertex> (l1[i],l2[i]));
                }
            }

        }
        System.out.println("match:");
        System.out.println(ans);
        return ans;
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


    public static boolean isPathIntersect(List<Edge> path1,List<Edge> path2) {
        Edge[] p1 = new Edge[path1.size()];
        path1.toArray(p1); // fill the array
        Edge[] p2 = new Edge[path2.size()];
        path2.toArray(p2);

        for (int i=0; i<p1.length-1; i++) {
            for (int j=0; j<p2.length-1; j++) {
                if (p1[i].getTo().equals(p2[j].getTo())) {
                    if ((p1[i].getFrom().getY() > p2[j].getFrom().getY()) &&
                            (p1[i+1].getTo().getY() < p2[j+1].getTo().getY()) )
                        return true;
                    else {
                        if (p1[i].getFrom().getY() < p2[j].getFrom().getY() &&
                                p1[i+1].getTo().getY() < p2[j+1].getTo().getY())
                            return true;
                    }
                }

            }
        }

        return false;
    }

    public static boolean isPathsIntersect(Set<List<Edge>> pathsList, List<Edge> path) {
        for (List<Edge> p : pathsList) {
            if (isPathIntersect(p,path)) return true;
        }
        return false;
    }

    public static Pair<Set<List<Edge>>,Set<List<Edge>>> checkAlgorithem(Graph g1,Graph g2) {
        List<Pair<Vertex,Vertex>> matchVertex = findMatch(g1,g2);
        if (matchVertex.isEmpty()) return null;
        Set<List<Edge>> pathsListG1=new HashSet<List<Edge>> ();
        Set<List<Edge>> pathsListG2=new HashSet<List<Edge>> ();
        System.out.println("here!");
        if (checkAlgorithem(g1, g2, matchVertex, pathsListG1, pathsListG2)) {
            return new Pair<Set<List<Edge>>,Set<List<Edge>>>(pathsListG1,pathsListG2);
        }
        return null;
    }

    public static boolean checkAlgorithem(Graph g1,Graph g2,List<Pair<Vertex,Vertex>> matchVertex,
                                           Set<List<Edge>> pathsListG1,Set<List<Edge>> pathsListG2) {
        if (g1.getEdges().isEmpty() && g2.getEdges().isEmpty()) return true;
        if (g1.getEdges().isEmpty() || g2.getEdges().isEmpty()) return false;
        //System.out.println("g1: "+g1.getEdges().size()+" g2: "+g2.getEdges().size());
        for (Pair<Vertex,Vertex> p1 : matchVertex) {
            for (Pair<Vertex,Vertex> p2 : matchVertex) {
                if (p1.equals(p2)) continue;
                Set<List<Edge>> paths1 = g1.allPaths(p1.getFirst(),p2.getFirst());
                Set<List<Edge>> paths2 = g2.allPaths(p1.getSecond(),p2.getSecond());
                for (List<Edge> path1 : paths1) {
                    if (isPathsIntersect(pathsListG1,path1)) {
                        continue;
                    }
                    g1.removeEdges(path1);
                    pathsListG1.add(path1);
                    for (List<Edge> path2 : paths2) {
                        if (isPathsIntersect(pathsListG2,path2)) {
                            continue;
                        }
                        g2.removeEdges(path2);
                        pathsListG2.add(path2);
                        if (checkAlgorithem(g1,g2,matchVertex,pathsListG1,pathsListG2)) {
                            return true;
                        }
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


}
