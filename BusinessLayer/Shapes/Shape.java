package Shapes;
import Graph.*;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public abstract class Shape {

    abstract List<Point2D.Double> getIntersections_accept(Shape s);//accept function

    abstract List<Point2D.Double> getIntersections_visit(Circle s);//visit function

    abstract List<Point2D.Double> getIntersections_visit(Line s);//visit function

    public abstract void createShapeGraph(List<Shape> shapes,Graph g);

    public void splitEdges(Graph g,Set<Point2D.Double> points) {
        Vertex v;
        for (Point2D.Double p : points) {
            Vertex v1=new Vertex(p.getX(), p.getY());
            v=g.isVertexExist(v1);
            if (v==null) {
                v=v1;
                g.addVertex(v);
            }

            Set<Edge> temp=new HashSet<Edge>(g.getEdges());
            for (Edge e: temp) {
                if (e.getF().equals(this) && v.isBetween(e.getFrom(),e.getTo())){
                    //isBetween(e.getFrom(),e.getTo(), v)) {
                    g.removeEdge(e);
                    g.addEdge(new Edge(this,e.getFrom(),v));
                    g.addEdge(new Edge(this,v,e.getTo()));

                }
            }
        }
    }

    abstract public double getMinY();

    abstract public void moveYbyH(double h);

    abstract public double getMinX();

    abstract public void moveXbyH(double h);

}
