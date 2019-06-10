package Shapes;

import Graph.*;
//import com.sun.deploy.security.ruleset.ExceptionRule;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class Line  extends Shape {

    private double xStart;
    private double yStart;
    private double xEnd;
    private double yEnd;

    public Line (double xStart, double yStart, double xEnd, double yEnd) {
        if (xStart>xEnd){
            this.xStart=xEnd;
            this.yStart=yEnd;
            this.xEnd=xStart;
            this.yEnd=yStart;
        }
        else{
            this.xStart=xStart;
            this.yStart=yStart;
            this.xEnd=xEnd;
            this.yEnd=yEnd;
        }

    }

    public double getXStart() {
        return xStart;
    }

    public double getYStart() {
        return yStart;
    }

    public double getXEnd() {
        return xEnd;
    }

    public double getYEnd() {
        return yEnd;
    }

    public double getM() {
        return (yEnd-yStart)/(xEnd-xStart);
    }

    public double getB() {
        return yStart-xStart*getM();
    }

    @Override
    public double getYbyX(double x, double yFrom, double yTo) {
        return getM()*x + getB();
    }

    @Override
    public void createShapeGraph(List<Shape> shapes, Graph g) {
        Set<Double> points=new HashSet<Double>();
        for (Shape s : shapes) {
            if (s!=this) {
                this.mergeIfNotIn(points,this.getIntersections_accept(s));
            }
        }

        Vertex start=new Vertex(xStart,yStart);
        Vertex end=new Vertex(xEnd,yEnd);
        Vertex v=g.isVertexExist(start);
        if (v==null) g.addVertex(start);
        else start=v;
        v=g.isVertexExist(end);
        if (v==null) g.addVertex(end);
        else end=v;

        g.addEdge(new Edge(this,start,end));

        this.splitEdges(g,points);
    }

    @Override
    public List<Point2D.Double> getIntersections_accept(Shape s) {
        return s.getIntersections_visit(this);
    }

    @Override
    public List<Point2D.Double> getIntersections_visit(Circle s) {
        return s.getIntersections_visit(this);
    }

    @Override
    public List<Point2D.Double> getIntersections_visit(Line s) {
        List<Point2D.Double> ans=new LinkedList<>();
        double ms=this.getM()-s.getM();
        if (ms == 0) return ans;
        double xInter= (s.getB()-this.getB())/ms;
        if (this.xStart<=xInter && xInter<=this.xEnd && s.getXStart()<=xInter && xInter<=s.getXEnd()){
            ans.add(new Point2D.Double(xInter,this.getM()*xInter+this.getB()));
        }
        return ans;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Line)) {
            return false;
        }
        Line other = (Line) o;
        if(this.xStart==other.getXStart() && this.yStart==other.getYStart() &&
                this.xEnd==other.getXEnd() && this.yEnd==other.getYEnd()) return true;
        else return false;
    }

    public double getMinY(){
        if(this.yStart<this.yEnd)
            return this.yStart;
        else
            return this.yEnd;
    }

    public double getMinX(){
        if(this.xStart<this.xEnd)
            return this.xStart;
        else
            return this.xEnd;
    }

    public void moveYbyH(double h){
        this.yStart = this.yStart - h;
        this.yEnd = this.yEnd - h;
    }

    public void moveXbyH(double h){
        this.xStart = this.xStart - h;
        this.xEnd = this.xEnd - h;
    }

    @Override
    public String toString() {
        return "xStart: " + this.xStart + " yStart: "+ this.yStart + " xEnd: " + this.xEnd + " yEnd: "+ this.yEnd;
    }
}

