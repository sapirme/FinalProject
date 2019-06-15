package SystemObj;
import Shapes.*;
import Graph.*;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;

public class ViewPoint {
    private LinkedList<List<Edge>> paths;

    private int circleNum;
    private int lineNum;
    private Graph graph;

    public ViewPoint(){
        paths = null;
        graph = null;
        circleNum = 0;
        lineNum = 0;
    }

    public boolean hasPaths(){
        if (paths!=null) return true;
        return false;
    }

    public String toString(){
        return "paths: " + paths + "\n" +
                " circle num: "+ circleNum +"\n"+
                " line num: "+ lineNum +"\n"+
                " graph: "+graph;
    }


    public void setPaths(LinkedList<List<Edge>> paths){
        this.paths = paths;
    }

    public void setCircleNum(int num){
        this.circleNum = num;
    }
    public void setLineNum (int num){
        this.lineNum = num;
    }

    public void setGraph(Graph g){
        this.graph = g;
    }

    public Graph getGraph(){return graph;}

    public int getCircleNum(){
        return circleNum;
    }
    public int getLineNum(){
        return lineNum;
    }

    public  LinkedList<List<Edge>> getPaths(){
        return paths;
    }


}

