package Algorithms;

import Graph.Edge;
import Graph.Graph;
import Graph.Pair;
import Graph.Vertex;
import Shapes.Circle;
import Shapes.Line;
import Shapes.Shape;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CheckingAlgorithmTest2 {

    List<Vertex> l1;
    List<Vertex> l2;
    List<Vertex> ver;
    List<Edge> edges;
    Vertex v1,v2,v3,v4,v5;
    Edge e1,e2,e3,e4,e5,e6;

    private void setUp(){
        l1 = new LinkedList<Vertex>();
        l2 = new LinkedList<Vertex>();
        v1 = new Vertex(1,1);
        v2 = new Vertex(1,2);
        v3 = new Vertex(1,3);
        v4 = new Vertex(1,1);
        v5 = new Vertex(1,2);

        l1.add(v1);
        l1.add(v2);
        l1.add(v3);

        l2.add(v4);
        l2.add(v5);

        ver = new LinkedList<Vertex>();
        edges = new LinkedList<Edge>();

        ver.add(v1);
        ver.add(v2);
        ver.add(v3);
        ver.add(v4);
        ver.add(v5);

        e1 = new Edge(null,v1,v4);
        e2 = new Edge(null,v1,v5);
        e3 = new Edge(null,v2,v4);
        e4 = new Edge(null,v2,v5);
        e5 = new Edge(null,v3,v4);
        e6 = new Edge(null,v3,v5);

        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);
        edges.add(e6);
    }

    @Test
    public void createGraphForMatchTest() {
        setUp();

        Pair<List<Vertex>,List<Edge>> ans = CheckingAlgorithm.createGraphForMatch(l1,l2);
        Pair<List<Vertex>,List<Edge>> expected = new Pair<List<Vertex>,List<Edge>>(ver,edges);
        Assert.assertEquals(expected.toString(),ans.toString());
    }

    @Test
    public void removeAllWithV1V2Test(){
        setUp();

        List<Edge> expected = new LinkedList<Edge>(edges);
        expected.remove(e1);
        expected.remove(e2);
        expected.remove(e3);
        expected.remove(e5);

        CheckingAlgorithm.removeAllWithV1V2(edges,v1,v4);
        Assert.assertEquals(expected.toString(),edges.toString());

    }

    @Test
    public void removeAllWithV1V2Test2(){
        setUp();
        List<Edge> expected = new LinkedList<Edge>(edges);
        expected.remove(e1);
        expected.remove(e2);
        expected.remove(e4);
        expected.remove(e6);

        CheckingAlgorithm.removeAllWithV1V2(edges,v1,v5);
        //System.out.println("ans\n"+edges.toString());
        Assert.assertEquals(expected.toString(),edges.toString());

    }

    @Test
    public void findAllPossibleMatchTest(){
        setUp();

        List<List<Pair<Vertex,Vertex>>> ans = CheckingAlgorithm.findAllPossibleMatch(l1,l2);

        List<List<Pair<Vertex,Vertex>>> expected = new LinkedList<List<Pair<Vertex,Vertex>>>();
        List<Pair<Vertex,Vertex>> lst1 = new LinkedList<Pair<Vertex, Vertex>>();
        lst1.add(new Pair<Vertex, Vertex>(v1,v4));
        lst1.add(new Pair<Vertex, Vertex>(v2,v5));
        List<Pair<Vertex,Vertex>> lst2 = new LinkedList<Pair<Vertex, Vertex>>();
        lst2.add(new Pair<Vertex, Vertex>(v1,v4));
        lst2.add(new Pair<Vertex, Vertex>(v3,v5));
        List<Pair<Vertex,Vertex>> lst3 = new LinkedList<Pair<Vertex, Vertex>>();
        lst3.add(new Pair<Vertex, Vertex>(v1,v5));
        lst3.add(new Pair<Vertex, Vertex>(v2,v4));
        List<Pair<Vertex,Vertex>> lst4 = new LinkedList<Pair<Vertex, Vertex>>();
        lst4.add(new Pair<Vertex, Vertex>(v1,v5));
        lst4.add(new Pair<Vertex, Vertex>(v3,v4));
        List<Pair<Vertex,Vertex>> lst5 = new LinkedList<Pair<Vertex, Vertex>>();
        lst5.add(new Pair<Vertex, Vertex>(v2,v4));
        lst5.add(new Pair<Vertex, Vertex>(v3,v5));
        List<Pair<Vertex,Vertex>> lst6 = new LinkedList<Pair<Vertex, Vertex>>();
        lst6.add(new Pair<Vertex, Vertex>(v2,v5));
        lst6.add(new Pair<Vertex, Vertex>(v3,v4));

        expected.add(lst1);
        expected.add(lst2);
        expected.add(lst3);
        expected.add(lst4);
        expected.add(lst5);
        expected.add(lst6);

        Assert.assertEquals(expected.toString(),ans.toString());

    }


    @Test
    public void addIfNotInTest(){
        setUp();
        List<List<Pair<Vertex,Vertex>>> lst = new LinkedList<List<Pair<Vertex,Vertex>>>();
        List<Pair<Vertex,Vertex>> l = new LinkedList<Pair<Vertex,Vertex>>();
        l.add(new Pair<Vertex, Vertex>(v1,v2));
        l.add(new Pair<Vertex, Vertex>(v2,v3));
        l.add(new Pair<Vertex, Vertex>(v1,v3));
        List<List<Pair<Vertex,Vertex>>> expected = new LinkedList<List<Pair<Vertex,Vertex>>>();
        expected.add(l);
        CheckingAlgorithm.addIfNotIn(lst,l);
        Assert.assertEquals(expected.toString(),lst.toString());

        List<Pair<Vertex,Vertex>> toAdd = new LinkedList<Pair<Vertex,Vertex>>();
        toAdd.add(new Pair<Vertex, Vertex>(v2,v3));
        toAdd.add(new Pair<Vertex, Vertex>(v1,v3));
        toAdd.add(new Pair<Vertex, Vertex>(v1,v2));
        CheckingAlgorithm.addIfNotIn(lst,toAdd);
        Assert.assertEquals(expected.toString(),lst.toString());

    }

    @Test
    public void addAllOptionsToListTest(){
        setUp();
        List<List<Pair<Vertex,Vertex>>> addInTo = new LinkedList<List<Pair<Vertex,Vertex>>>();
        List<Pair<Vertex,Vertex>> temp = new LinkedList<Pair<Vertex, Vertex>>();
        temp.add(new Pair<Vertex, Vertex>(v1,v4));
        temp.add(new Pair<Vertex, Vertex>(v2,v5));
        addInTo.add(temp);
        List<List<Pair<Vertex,Vertex>>> toAdd = new LinkedList<List<Pair<Vertex,Vertex>>>();
        List<Pair<Vertex,Vertex>> temp2 = new LinkedList<Pair<Vertex, Vertex>>();
        temp2.add(new Pair<Vertex, Vertex>(v3,v4));
        temp2.add(new Pair<Vertex, Vertex>(v2,v5));
        toAdd.add(temp2);

        List<List<Pair<Vertex,Vertex>>> expected = new LinkedList<List<Pair<Vertex,Vertex>>>();
        List<Pair<Vertex,Vertex>> temp3 = new LinkedList<Pair<Vertex, Vertex>>();
        temp3.add(new Pair<Vertex, Vertex>(v1,v4));
        temp3.add(new Pair<Vertex, Vertex>(v2,v5));
        temp3.add(new Pair<Vertex, Vertex>(v3,v4));
        temp3.add(new Pair<Vertex, Vertex>(v2,v5));
        expected.add(temp3);

        CheckingAlgorithm.addAllOptionsToList(addInTo,toAdd);

        Assert.assertEquals(expected.toString(),addInTo.toString());

    }



    @Test
    public void addAllOptionsToListTest2(){
        setUp();
        List<List<Pair<Vertex,Vertex>>> addInTo = new LinkedList<List<Pair<Vertex,Vertex>>>();
        List<Pair<Vertex,Vertex>> temp = new LinkedList<Pair<Vertex, Vertex>>();
        temp.add(new Pair<Vertex, Vertex>(v1,v4));
        temp.add(new Pair<Vertex, Vertex>(v2,v5));
        addInTo.add(temp);

        List<List<Pair<Vertex,Vertex>>> toAdd = new LinkedList<List<Pair<Vertex,Vertex>>>();
        List<Pair<Vertex,Vertex>> temp2 = new LinkedList<Pair<Vertex, Vertex>>();
        temp2.add(new Pair<Vertex, Vertex>(v3,v4));
        temp2.add(new Pair<Vertex, Vertex>(v3,v4));
        List<Pair<Vertex,Vertex>> temp1 = new LinkedList<Pair<Vertex, Vertex>>();
        temp1.add(new Pair<Vertex, Vertex>(v2,v5));
        temp1.add(new Pair<Vertex, Vertex>(v2,v5));
        toAdd.add(temp2);
        toAdd.add(temp1);

        List<List<Pair<Vertex,Vertex>>> expected = new LinkedList<List<Pair<Vertex,Vertex>>>();
        List<Pair<Vertex,Vertex>> temp3 = new LinkedList<Pair<Vertex, Vertex>>();
        temp3.add(new Pair<Vertex, Vertex>(v1,v4));
        temp3.add(new Pair<Vertex, Vertex>(v2,v5));
        temp3.add(new Pair<Vertex, Vertex>(v3,v4));
        temp3.add(new Pair<Vertex, Vertex>(v3,v4));
        List<Pair<Vertex,Vertex>> temp4 = new LinkedList<Pair<Vertex, Vertex>>();
        temp4.add(new Pair<Vertex, Vertex>(v1,v4));
        temp4.add(new Pair<Vertex, Vertex>(v2,v5));
        temp4.add(new Pair<Vertex, Vertex>(v2,v5));
        temp4.add(new Pair<Vertex, Vertex>(v2,v5));
        expected.add(temp3);
        expected.add(temp4);

        CheckingAlgorithm.addAllOptionsToList(addInTo,toAdd);

        Assert.assertEquals(expected.toString(),addInTo.toString());


    }



    @Test
    public void isPathCheckedTest(){
        setUp();

        LinkedList<List<Edge>> pathsChecked = new LinkedList<List<Edge>>();
        List<Edge> path = new LinkedList<Edge>();
        path.add(e1);
        path.add(e2);
        path.add(e3);
        Assert.assertFalse(CheckingAlgorithm.isPathChecked(pathsChecked,path));

        List<Edge> path2 = new LinkedList<Edge>();
        path2.add(new Edge(e1));
        path2.add(new Edge(e2));
        path2.add(new Edge(e3));

        pathsChecked.add(path2);

        Assert.assertTrue(CheckingAlgorithm.isPathChecked(pathsChecked,path));


    }


    @Test
    public void isPathIntersectTest(){
        v1 = new Vertex(0,0);
        v2 = new Vertex(80,50);
        v3 = new Vertex(0,50);
        v4 = new Vertex(80,0);
        v5 = new Vertex(40,25);

        Line line1 = new Line(v1.getX(),v1.getY(),v2.getX(),v2.getY());
        Line line2 = new Line(v3.getX(),v3.getY(),v4.getX(),v4.getY());


        e1 = new Edge(line1,v1,v5);
        e2 = new Edge(line2,v5,v4);

        e3 = new Edge(line2,v3,v5);
        e4 = new Edge(line1,v5,v2);

        List<Edge> path1 = new LinkedList<Edge>();
        path1.add(e1);
        path1.add(e2);
        List<Edge> path2 = new LinkedList<Edge>();
        path2.add(e3);
        path2.add(e4);

        Assert.assertFalse(CheckingAlgorithm.isPathIntersect(path1,path2));

        path1 = new LinkedList<Edge>();
        path1.add(e1);
        path1.add(e4);
        path2 = new LinkedList<Edge>();
        path2.add(e3);
        path2.add(e2);

        Assert.assertTrue(CheckingAlgorithm.isPathIntersect(path1,path2));


    }

    @Test
    public void isPathIntersectTest2(){
        v1 = new Vertex(0,2);
        v2 = new Vertex(2,4);
        v3 = new Vertex(4,6);

        Line line1 = new Line(v1.getX(),v1.getY(),v3.getX(),v3.getY());
        Circle circle1 = new Circle(2,2,2);
        Circle circle2 = new Circle(4,4,2);


        e1 = new Edge(line1,v1,v2);
        e2 = new Edge(line1,v2,v3);

        e3 = new Edge(circle1,v1,v2);
        e4 = new Edge(circle2,v2,v3);

        List<Edge> path1 = new LinkedList<Edge>();
        path1.add(e1);
        path1.add(e2);
        List<Edge> path2 = new LinkedList<Edge>();
        path2.add(e3);
        path2.add(e4);

        Assert.assertFalse(CheckingAlgorithm.isPathIntersect(path1,path2));

        path1 = new LinkedList<Edge>();
        path1.add(e1);
        path1.add(e4);
        path2 = new LinkedList<Edge>();
        path2.add(e3);
        path2.add(e2);

        Assert.assertTrue(CheckingAlgorithm.isPathIntersect(path1,path2));


    }


}