package SystemObj;
import Graph.Edge;
import Graph.Graph;
import Shapes.Shape;

import java.util.LinkedList;
import java.util.List;

public class ViewPoint {
    private LinkedList<List<Edge>> paths;
    private List<Shape> shapes;
    private Graph graph;

    public ViewPoint(){
        paths = null;
        shapes = null;
        graph = null;
    }

    public boolean hasPaths(){
        if (paths!=null) return true;
        return false;
    }

    public String toString(){
        return paths + "\n" +
                shapes + "\n" +
                graph;
    }


    public void setPaths(LinkedList<List<Edge>> paths){
        this.paths = paths;
    }

    public void setShapes(List<Shape> shapes){
        this.shapes = shapes;
    }

    public void setGraph(Graph g){
        this.graph = g;
    }

    public Graph getGraph(){return graph;}

    public  List<Shape> getShapes(){
        return shapes;
    }

    public  LinkedList<List<Edge>> getPaths(){
        return paths;
    }


}

