package SystemObj;
import Shapes.*;
import Graph.*;
import java.util.List;
import java.util.Set;

public class ViewPoint {
    private Set<List<Edge>> paths;
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

    public void setPaths(Set<List<Edge>> paths){
        this.paths = paths;
    }

    public void setSapes(List<Shape> shapes){
        this.shapes = shapes;
    }

    public void setGraph(Graph g){
        this.graph = g;
    }

    public Graph getGraph(){return graph;}

    public  List<Shape> getShapes(){
        return shapes;
    }


}

