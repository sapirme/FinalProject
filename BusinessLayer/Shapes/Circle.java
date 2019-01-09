package Shapes;

import Graph.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class Circle extends Shape{
    private double x;
    private double y;
    private double r;

    public Circle (double x,double y,double r) {
        this.x=x;
        this.y=y;
        this.r=r;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    @Override
    public List<Point2D.Double> getIntersections_accept(Shape s) {
        return s.getIntersections_visit(this);
    }

    @Override
    public List<Double> getIntersections_visit(Circle s) {
        List<Point2D.Double> intesection=new LinkedList<>();
        double x1,x2,y1,y2,r1,r2,R;
        x1=x;x2=s.getX();
        y1=y;y2=s.getY();
        r1=r;r2=s.getR();

        boolean isIntersects = Math.hypot(x1-x2, y1-y2) <= (r1 + r2);
        if (!isIntersects) return intesection;

        R=Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
        double part1x=0.5*(x1+x2)+((r1*r1-r2*r2)/(2*R*R))*(x2-x1) ;
        double part1y=0.5*(y1+y2)+((r1*r1-r2*r2)/(2*R*R))*(y2-y1) ;

        double part2x=0.5*Math.sqrt(2*((r1*r1 + r2*r2)/(R*R)) - (((r1*r1-r2*r2)*(r1*r1-r2*r2))/(R*R*R*R))-1)*(y2-y1) ;
        double part2y=0.5*Math.sqrt(2*((r1*r1 + r2*r2)/(R*R)) - (((r1*r1-r2*r2)*(r1*r1-r2*r2))/(R*R*R*R))-1)*(x2-x1) ;

        Point2D.Double p1 = new Point2D.Double((part1x+part2x),(part1y+part2y));
        Point2D.Double p2 = new Point2D.Double((part1x-part2x),(part1y-part2y));
        intesection.add(p1);
        if(!p1.equals(p2)) {
            intesection.add(p2);
        }
        return intesection;
    }


    @Override
    public void createShapeGraph(List<Shape> shapes,Graph g) {
        Set<Point2D.Double> points=new HashSet<Point2D.Double>();
        for (Shape s : shapes) {
            if (s!=this)
                points.addAll(this.getIntersections_accept(s));
        }

        Vertex right=new Vertex(r+x,y);
        Vertex left=new Vertex(x-r,y);
        Vertex up=new Vertex(x,y+r);
        Vertex down=new Vertex(x,y-r);
        Vertex v=g.isVertexExist(right);
        if (v==null) g.addVertex(right);
        else right=v;
        v=g.isVertexExist(left);
        if (v==null) g.addVertex(left);
        else left=v;
        v=g.isVertexExist(up);
        if (v==null) g.addVertex(up);
        else up=v;
        v=g.isVertexExist(down);
        if (v==null) g.addVertex(down);
        else down=v;

        g.addEdge(new Edge(this,left,up));
        g.addEdge(new Edge(this,left,down));
        g.addEdge(new Edge(this,up,right));
        g.addEdge(new Edge(this,down,right));
        this.splitEdges(g,points);
        /*
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
        }*/
    }
    /*
    private boolean isPointOnCircle (Point2D.Double p){
        double distance=Math.sqrt((x-p.getX())*(x-p.getX()) + (y-p.getY())*(y-p.getY()));
        return distance == r;
    }*/
/*
    private boolean isBetween(Vertex v1,Vertex v2, Vertex v) {
        if (v1.getX() < v.getX() && v.getX() < v2.getX()) {
            if (Math.min(v1.getY(), v2.getY()) < v.getY() && v.getY() < Math.max(v1.getY(), v2.getY()))
                return true;

        }
        return false;
    }
*/
    @Override
    public List<Point2D.Double> getIntersections_visit(Line s) {
        List<Point2D.Double> ans=new LinkedList<Point2D.Double>();
        double a = s.getM()*s.getM() + 1;
        double b = 2 * ((s.getB()-y)*s.getM()-x);
        double c = (s.getB()-y)*(s.getB()-y) + x*x - r*r;
        double[] intesectionX = QuadraticEquationSol(a,b,c);
        for (int i=0;i<intesectionX.length;i++) {
            if (s.getXStart()<=intesectionX[i] && intesectionX[i]<=s.getXEnd()) {
                ans.add(new Point2D.Double(intesectionX[i],s.getM()*intesectionX[i]+s.getB()));
            }
        }

        return ans;
    }

    private double[] QuadraticEquationSol(double a,double b, double c) {
        double result = b * b - 4.0 * a * c;
        double[] array;
        if (result > 0.0) {
            double r1 = (-b + Math.pow(result, 0.5)) / (2.0 * a);
            double r2 = (-b - Math.pow(result, 0.5)) / (2.0 * a);
            array=new double[2];
            array[0]=r1;
            array[1]=r2;
        }
        else if (result == 0.0) {
            double r1 = -b / (2.0 * a);
            array=new double[1];
            array[0]=r1;
        }
        else {
            array=new double[0];
        }
        return array;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Circle)) {
            return false;
        }
        Circle other = (Circle) o;
        if(this.x==other.getX() && this.y==other.getY() && this.r==other.getR()) return true;
        else return false;
    }

    public double getMinY(){
        return this.y - this.r;
    }

    public double getMinX(){
        return this.x - this.r;
    }

    public void moveYbyH(double h){
        this.y = this.y - h;
    }

    public void moveXbyH(double h){
        this.x = this.x - h;
    }

}
